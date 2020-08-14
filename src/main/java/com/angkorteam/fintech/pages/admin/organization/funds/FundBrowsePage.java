package com.angkorteam.fintech.pages.admin.organization.funds;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.MifosDataContextManager;
import com.angkorteam.fintech.data.MySQLDataProvider;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.meta.tenant.MFund;
import com.angkorteam.webui.frmk.common.WicketFactory;
import com.angkorteam.webui.frmk.provider.QueryDataProvider;
import com.angkorteam.webui.frmk.wicket.extensions.markup.html.repeater.data.table.AbstractDataTable;
import com.angkorteam.webui.frmk.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.webui.frmk.wicket.extensions.markup.html.repeater.data.table.filter.*;
import com.angkorteam.webui.frmk.wicket.layout.Size;
import com.angkorteam.webui.frmk.wicket.layout.UIColumn;
import com.angkorteam.webui.frmk.wicket.layout.UIContainer;
import com.angkorteam.webui.frmk.wicket.layout.UIRow;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.metamodel.DataContext;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class FundBrowsePage extends MasterPage {

    protected Form<Void> filterForm;

    protected UIRow row1;

    protected UIColumn nameColumn;
    protected UIContainer nameContainer;
    protected TextField<String> nameField;
    protected String nameValue;

    protected Button searchButton;
    protected BookmarkablePageLink<Void> clearButton;

    protected BookmarkablePageLink<Void> createLink;

    protected FilterForm<Map<String, Expression>> fundBrowseForm;
    protected QueryDataProvider fundBrowseProvider;
    protected List<IColumn<Map<String, Object>, String>> fundBrowseColumn;
    protected AbstractDataTable<Map<String, Object>, String> fundBrowseTable;

    @Override
    protected void onInitData() {
        super.onInitData();
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
        MFund mFund = MFund.staticInitialize(dataContext);

        this.fundBrowseProvider = new MySQLDataProvider(mFund.getName());

        this.fundBrowseProvider.setCountField(mFund.getName() + "." + mFund.ID.getName());
        this.fundBrowseProvider.selectField(mFund.getName() + "." + mFund.ID.getName(), "fundId");

        this.fundBrowseColumn = new ArrayList<>();
        this.fundBrowseColumn.add(Column.text(Model.of("External ID"), mFund.getName() + "." + mFund.EXTERNAL_ID.getName(), "externalId", this.fundBrowseProvider));
        this.fundBrowseColumn.add(Column.text(Model.of("Name"), mFund.getName() + "." + mFund.NAME.getName(), "name", this.fundBrowseProvider));
        this.fundBrowseColumn.add(new ActionFilteredColumn<>(Model.of("Action"), this::fundBrowseActionLink, this::fundBrowseActionClick));
    }

    @Override
    protected void onInitHtml(MarkupContainer body) {
        this.filterForm = new Form<>("filterForm");
        body.add(this.filterForm);

        this.row1 = UIRow.newUIRow("row1", this.filterForm);

        this.nameColumn = this.row1.newUIColumn("nameColumn", Size.Three_3);
        this.nameContainer = this.nameColumn.newUIContainer("nameContainer");
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setLabel(Model.of("Name"));
        this.nameContainer.add(this.nameField);
        this.nameContainer.newFeedback("nameFeedback", this.nameField);

        this.row1.newUIColumn("lastColumn");

        this.searchButton = new Button("searchButton") {
            @Override
            public void onSubmit() {
                searchButtonSubmit();
            }
        };
        this.filterForm.add(this.searchButton);

        this.clearButton = new BookmarkablePageLink<>("clearButton", FundBrowsePage.class);
        this.filterForm.add(this.clearButton);

        this.createLink = new BookmarkablePageLink<>("createLink", FundCreatePage.class);
        body.add(this.createLink);

        this.fundBrowseForm = new FilterForm<>("fundBrowseForm", this.fundBrowseProvider);
        body.add(this.fundBrowseForm);

        this.fundBrowseTable = new DataTable<>("fundBrowseTable", this.fundBrowseColumn, this.fundBrowseProvider, 20);
        this.fundBrowseForm.add(this.fundBrowseTable);
    }

    protected List<ActionItem> fundBrowseActionLink(String link, Map<String, Object> model) {
        List<ActionItem> actions = new ArrayList<>();
        actions.add(new ActionItem("Edit", Model.of("Edit"), ItemCss.WARNING));
        return actions;
    }

    protected void fundBrowseActionClick(String link, Map<String, Object> model, AjaxRequestTarget target) {
        long fundId = (long) model.get("fundId");
        PageParameters parameters = new PageParameters();
        parameters.add("fundId", fundId);
        setResponsePage(FundModifyPage.class, parameters);
    }

    protected void searchButtonSubmit() {
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
        MFund mFund = MFund.staticInitialize(dataContext);

        this.fundBrowseProvider.removeWhere("name");
        if (this.nameValue != null && !"".equals(this.nameValue)) {
            this.fundBrowseProvider.applyWhere("name", mFund.getName() + "." + mFund.NAME.getName() + " LIKE '" + StringEscapeUtils.escapeSql(this.nameValue) + "%'");
        }
    }

}
