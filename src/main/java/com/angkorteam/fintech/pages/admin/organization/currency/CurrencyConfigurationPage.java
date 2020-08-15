package com.angkorteam.fintech.pages.admin.organization.currency;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.MifosDataContextManager;
import com.angkorteam.fintech.data.MySQLDataProvider;
import com.angkorteam.fintech.data.SingleChoiceProvider;
import com.angkorteam.fintech.client.Function;
import com.angkorteam.fintech.helper.CurrencyHelper;
import com.angkorteam.fintech.meta.tenant.MCurrency;
import com.angkorteam.fintech.meta.tenant.MOrganisationCurrency;
import com.angkorteam.webui.frmk.common.WicketFactory;
import com.angkorteam.webui.frmk.provider.QueryDataProvider;
import com.angkorteam.webui.frmk.wicket.extensions.markup.html.repeater.data.table.AbstractDataTable;
import com.angkorteam.webui.frmk.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.webui.frmk.wicket.extensions.markup.html.repeater.data.table.filter.*;
import com.angkorteam.webui.frmk.wicket.layout.Size;
import com.angkorteam.webui.frmk.wicket.layout.UIColumn;
import com.angkorteam.webui.frmk.wicket.layout.UIContainer;
import com.angkorteam.webui.frmk.wicket.layout.UIRow;
import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;
import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import io.github.openunirest.http.JsonNode;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.data.DataSet;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class CurrencyConfigurationPage extends MasterPage {

    protected Form<Void> configurationForm;

    protected UIRow row1;

    protected UIColumn currencyColumn;
    protected UIContainer currencyContainer;
    protected Select2SingleChoice currencyField;
    protected SingleChoiceProvider currencyProvider;
    protected Option currencyValue;

    protected Button addButton;

    protected FilterForm<Map<String, Expression>> currencyBrowseForm;
    protected QueryDataProvider currencyBrowseProvider;
    protected List<IColumn<Map<String, Object>, String>> currencyBrowseColumn;
    protected AbstractDataTable<Map<String, Object>, String> currencyBrowseTable;

    @Override
    protected void onInitData() {
        super.onInitData();
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
        MCurrency mCurrency = MCurrency.staticInitialize(dataContext);
        MOrganisationCurrency mOrganisationCurrency = MOrganisationCurrency.staticInitialize(dataContext);

        this.currencyProvider = new SingleChoiceProvider(mCurrency.getName(), mCurrency.CODE.getName(), mCurrency.NAME.getName(), "CONCAT(" + mCurrency.NAME.getName() + ",' [', " + mCurrency.CODE.getName() + ",']')");
        this.currencyProvider.applyWhere("code", mCurrency.CODE.getName() + " NOT IN (SELECT " + mOrganisationCurrency.CODE.getName() + " FROM " + mOrganisationCurrency.getName() + ")");

        this.currencyBrowseProvider = new MySQLDataProvider(mOrganisationCurrency.getName());

        this.currencyBrowseProvider.setCountField(mOrganisationCurrency.getName() + "." + mOrganisationCurrency.ID.getName());
        this.currencyBrowseProvider.selectField(mOrganisationCurrency.getName() + "." + mOrganisationCurrency.ID.getName(), "currencyId");

        this.currencyBrowseColumn = new ArrayList<>();
        this.currencyBrowseColumn.add(Column.text(Model.of("Code"), mOrganisationCurrency.getName() + "." + mOrganisationCurrency.CODE.getName(), "code", this.currencyBrowseProvider));
        this.currencyBrowseColumn.add(Column.text(Model.of("Name"), mOrganisationCurrency.getName() + "." + mOrganisationCurrency.NAME.getName(), "name", this.currencyBrowseProvider));
        this.currencyBrowseColumn.add(Column.text(Model.of("Symbol"), mOrganisationCurrency.getName() + "." + mOrganisationCurrency.DISPLAY_SYMBOL.getName(), "symbol", this.currencyBrowseProvider));
        this.currencyBrowseColumn.add(new ActionFilteredColumn<>(Model.of("Action"), this::currencyBrowseActionLink, this::currencyBrowseActionClick));
    }

    @Override
    protected void onInitHtml(MarkupContainer body) {
        this.configurationForm = new Form<>("configurationForm");
        body.add(this.configurationForm);

        this.row1 = UIRow.newUIRow("row1", this.configurationForm);

        this.currencyColumn = this.row1.newUIColumn("currencyColumn", Size.Three_3);
        this.currencyContainer = this.currencyColumn.newUIContainer("currencyContainer");
        this.currencyField = new Select2SingleChoice("currencyField", new PropertyModel<>(this, "currencyValue"), this.currencyProvider);
        this.currencyField.setLabel(Model.of("Currency"));
        this.currencyField.setRequired(true);
        this.currencyContainer.add(this.currencyField);
        this.currencyContainer.newFeedback("currencyFeedback", this.currencyField);

        this.row1.newUIColumn("lastColumn");

        this.addButton = new Button("addButton") {
            @Override
            public void onSubmit() {
                addButtonSubmit();
            }
        };
        this.configurationForm.add(this.addButton);

        this.currencyBrowseForm = new FilterForm<>("currencyBrowseForm", this.currencyBrowseProvider);
        body.add(this.currencyBrowseForm);

        this.currencyBrowseTable = new DataTable<>("currencyBrowseTable", this.currencyBrowseColumn, this.currencyBrowseProvider, 20);
        this.currencyBrowseForm.add(this.currencyBrowseTable);
    }

    protected List<ActionItem> currencyBrowseActionLink(String link, Map<String, Object> model) {
        List<ActionItem> actions = new ArrayList<>();
        actions.add(new ActionItem("Delete", Model.of("Delete"), ItemCss.WARNING));
        return actions;
    }

    protected void currencyBrowseActionClick(String link, Map<String, Object> model, AjaxRequestTarget target) {
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
        MOrganisationCurrency mOrganisationCurrency = MOrganisationCurrency.staticInitialize(dataContext);
        List<String> codes = new ArrayList<>();
        try (DataSet rows = dataContext.query()
                .from(mOrganisationCurrency)
                .selectAll()
                .where(mOrganisationCurrency.CODE).notIn((String) model.get("code"))
                .execute()) {
            while (rows.next()) {
                codes.add((String) rows.getRow().getValue(mOrganisationCurrency.CODE));
            }
        }
        CurrencyHelper.update(getSession(), codes);
        setResponsePage(CurrencyConfigurationPage.class);
    }

    protected void addButtonSubmit() {
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
        MOrganisationCurrency mOrganisationCurrency = MOrganisationCurrency.staticInitialize(dataContext);

        List<String> temp = new ArrayList<>();
        try (DataSet rows = dataContext.query().from(mOrganisationCurrency).selectAll().execute()) {
            while (rows.next()) {
                temp.add((String) rows.getRow().getValue(mOrganisationCurrency.CODE));
            }
        }
        List<String> codes = Lists.newArrayList(temp);
        codes.add(this.currencyValue.getId());

        JsonNode node = CurrencyHelper.update(getSession(), codes);

        if (reportError(node)) {
            return;
        }
        setResponsePage(CurrencyConfigurationPage.class);
    }

}
