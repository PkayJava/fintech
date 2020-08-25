package com.angkorteam.fintech.pages.admin.system.code;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.MifosDataContextManager;
import com.angkorteam.fintech.client.FineractClient;
import com.angkorteam.fintech.client.Function;
import com.angkorteam.fintech.data.MySQLDataProvider;
import com.angkorteam.fintech.meta.tenant.MCode;
import com.angkorteam.webui.frmk.common.Bookmark;
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
@Bookmark("/admin/system/code/browse")
public class CodeBrowsePage extends MasterPage {

    protected Form<Void> filterForm;

    protected UIRow row1;

    protected UIColumn nameColumn;
    protected UIContainer nameContainer;
    protected TextField<String> nameField;
    protected String nameValue;

    protected Button searchButton;
    protected BookmarkablePageLink<Void> clearButton;

    protected BookmarkablePageLink<Void> createLink;

    protected FilterForm<Map<String, Expression>> codeBrowseForm;
    protected QueryDataProvider codeBrowseProvider;
    protected List<IColumn<Map<String, Object>, String>> codeBrowseColumn;
    protected AbstractDataTable<Map<String, Object>, String> codeBrowseTable;

    @Override
    protected void onInitData() {
        super.onInitData();
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
        MCode mCode = MCode.staticInitialize(dataContext);

        this.codeBrowseProvider = new MySQLDataProvider(mCode.getName());
        this.codeBrowseProvider.setCountField(mCode.getName() + "." + mCode.ID.getName());
        this.codeBrowseProvider.selectField(mCode.getName() + "." + mCode.ID.getName(), "codeId");

        this.codeBrowseColumn = new ArrayList<>();
        this.codeBrowseColumn.add(Column.text(Model.of("Name"), mCode.getName() + "." + mCode.CODE_NAME.getName(), "name", this.codeBrowseProvider));
        this.codeBrowseColumn.add(Column.yesno(Model.of("System"), mCode.getName() + "." + mCode.SYSTEM_DEFINED.getName(), "system", this.codeBrowseProvider));
        this.codeBrowseColumn.add(new ActionFilteredColumn<>(Model.of("Action"), this::codeBrowseActionLink, this::codeBrowseActionClick));
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

        this.clearButton = new BookmarkablePageLink<>("clearButton", CodeBrowsePage.class);
        this.filterForm.add(this.clearButton);

        this.createLink = new BookmarkablePageLink<>("createLink", CodeCreatePage.class);
        body.add(this.createLink);

        this.codeBrowseForm = new FilterForm<>("codeBrowseForm", this.codeBrowseProvider);
        body.add(this.codeBrowseForm);

        this.codeBrowseTable = new DataTable<>("codeBrowseTable", this.codeBrowseColumn, this.codeBrowseProvider, 20);
        this.codeBrowseForm.add(this.codeBrowseTable);
    }

    protected List<ActionItem> codeBrowseActionLink(String link, Map<String, Object> model) {
        int system = model.get("system") == null ? 0 : (int) model.get("system");
        List<ActionItem> actions = new ArrayList<>();
        actions.add(new ActionItem("Edit", Model.of("Edit"), ItemCss.WARNING));
        if (system == 0) {
            actions.add(new ActionItem("Delete", Model.of("Delete"), ItemCss.WARNING));
        }
        return actions;
    }

    protected void codeBrowseActionClick(String link, Map<String, Object> model, AjaxRequestTarget target) {
        int codeId = (int) model.get("codeId");
        if ("Edit".equals(link)) {
            PageParameters parameters = new PageParameters();
            parameters.add("codeId", codeId);
            setResponsePage(CodeModifyPage.class, parameters);
        } else if ("Delete".equals(link)) {
            ApplicationContext context = WicketFactory.getApplicationContext();
            FineractClient client = context.getBean(FineractClient.class);
            client.codeDelete(getSession().getIdentifier(), getSession().getToken(), codeId);
            target.add(this.codeBrowseTable);
        }
    }

    protected void searchButtonSubmit() {
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
        MCode mCode = MCode.staticInitialize(dataContext);

        this.codeBrowseProvider.removeWhere("name");
        if (this.nameValue != null && !"".equals(this.nameValue)) {
            this.codeBrowseProvider.applyWhere("name", mCode.getName() + "." + mCode.CODE_NAME.getName() + " LIKE '" + StringEscapeUtils.escapeSql(this.nameValue) + "%'");
        }
    }

}
