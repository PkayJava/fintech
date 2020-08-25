package com.angkorteam.fintech.pages.admin.system.makerchecker;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.MifosDataContextManager;
import com.angkorteam.fintech.client.FineractClient;
import com.angkorteam.fintech.client.Function;
import com.angkorteam.fintech.data.MySQLDataProvider;
import com.angkorteam.fintech.meta.tenant.MPermission;
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
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
@Bookmark("/admin/system/makerchecker/browse")
public class MakerCheckerBrowsePage extends MasterPage {

    protected Form<Void> filterForm;

    protected UIRow row1;

    protected UIColumn groupingColumn;
    protected UIContainer groupingContainer;
    protected TextField<String> groupingField;
    protected String groupingValue;

    protected UIColumn entityColumn;
    protected UIContainer entityContainer;
    protected TextField<String> entityField;
    protected String entityValue;

    protected Button searchButton;
    protected BookmarkablePageLink<Void> clearButton;

    protected BookmarkablePageLink<Void> createLink;

    protected FilterForm<Map<String, Expression>> makercheckerBrowseForm;
    protected QueryDataProvider makercheckerBrowseProvider;
    protected List<IColumn<Map<String, Object>, String>> makercheckerBrowseColumn;
    protected AbstractDataTable<Map<String, Object>, String> makercheckerBrowseTable;

    @Override
    protected void onInitData() {
        super.onInitData();
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
        MPermission mPermission = MPermission.staticInitialize(dataContext);

        this.makercheckerBrowseProvider = new MySQLDataProvider(mPermission.getName());

        this.makercheckerBrowseProvider.setCountField(mPermission.getName() + "." + mPermission.ID.getName());
        this.makercheckerBrowseProvider.selectField(mPermission.getName() + "." + mPermission.ID.getName(), "makercheckerId");

        this.makercheckerBrowseColumn = new ArrayList<>();
        this.makercheckerBrowseColumn.add(Column.text(Model.of("Grouping"), mPermission.getName() + "." + mPermission.GROUPING.getName(), "grouping", this.makercheckerBrowseProvider));
        this.makercheckerBrowseColumn.add(Column.text(Model.of("Code"), mPermission.getName() + "." + mPermission.CODE.getName(), "code", this.makercheckerBrowseProvider));
        this.makercheckerBrowseColumn.add(Column.text(Model.of("Entity"), mPermission.getName() + "." + mPermission.ENTITY_NAME.getName(), "Entity", this.makercheckerBrowseProvider));
        this.makercheckerBrowseColumn.add(Column.text(Model.of("Operation"), mPermission.getName() + "." + mPermission.ACTION_NAME.getName(), "Operation", this.makercheckerBrowseProvider));
        this.makercheckerBrowseColumn.add(Column.yesno(Model.of("Maker & Checker"), mPermission.getName() + "." + mPermission.CAN_MAKER_CHECKER.getName(), "makerchecker", this.makercheckerBrowseProvider));
        this.makercheckerBrowseColumn.add(new ActionFilteredColumn<>(Model.of("Action"), this::makercheckerBrowseActionLink, this::makercheckerBrowseActionClick));
    }

    @Override
    protected void onInitHtml(MarkupContainer body) {
        this.filterForm = new Form<>("filterForm");
        body.add(this.filterForm);

        this.row1 = UIRow.newUIRow("row1", this.filterForm);

        this.groupingColumn = this.row1.newUIColumn("groupingColumn", Size.Four_4);
        this.groupingContainer = this.groupingColumn.newUIContainer("groupingContainer");
        this.groupingField = new TextField<>("groupingField", new PropertyModel<>(this, "groupingValue"));
        this.groupingField.setLabel(Model.of("Grouping"));
        this.groupingContainer.add(this.groupingField);
        this.groupingContainer.newFeedback("groupingFeedback", this.groupingField);

        this.entityColumn = this.row1.newUIColumn("entityColumn", Size.Four_4);
        this.entityContainer = this.entityColumn.newUIContainer("entityContainer");
        this.entityField = new TextField<>("entityField", new PropertyModel<>(this, "entityValue"));
        this.entityField.setLabel(Model.of("Entity"));
        this.entityContainer.add(this.entityField);
        this.entityContainer.newFeedback("entityFeedback", this.entityField);

        this.row1.newUIColumn("lastColumn");

        this.searchButton = new Button("searchButton") {
            @Override
            public void onSubmit() {
                searchButtonSubmit();
            }
        };
        this.filterForm.add(this.searchButton);

        this.clearButton = new BookmarkablePageLink<>("clearButton", MakerCheckerBrowsePage.class);
        this.filterForm.add(this.clearButton);

        this.createLink = new BookmarkablePageLink<>("createLink", MakerCheckerCreatePage.class);
        body.add(this.createLink);

        this.makercheckerBrowseForm = new FilterForm<>("makercheckerBrowseForm", this.makercheckerBrowseProvider);
        body.add(this.makercheckerBrowseForm);

        this.makercheckerBrowseTable = new DataTable<>("makercheckerBrowseTable", this.makercheckerBrowseColumn, this.makercheckerBrowseProvider, 20);
        this.makercheckerBrowseForm.add(this.makercheckerBrowseTable);
    }

    protected List<ActionItem> makercheckerBrowseActionLink(String link, Map<String, Object> model) {
        List<ActionItem> actions = new ArrayList<>();
        String code = (String) model.get("code");
        String grouping = (String) model.get("grouping");
        if (!"special".equalsIgnoreCase(grouping) & !code.startsWith("READ_") && !code.endsWith("_CHECKER")) {
            int makerchecker = model.get("makerchecker") == null ? 0 : (int) model.get("makerchecker");
            if (makerchecker == 0) {
                actions.add(new ActionItem("Enable", Model.of("Enable"), ItemCss.WARNING));
            } else {
                actions.add(new ActionItem("Disable", Model.of("Disable"), ItemCss.WARNING));
            }
        }
        return actions;
    }

    protected void makercheckerBrowseActionClick(String link, Map<String, Object> model, AjaxRequestTarget target) {
        String code = (String) model.get("code");
        if ("Enable".equals(link)) {
            ApplicationContext context = WicketFactory.getApplicationContext();
            FineractClient client = context.getBean(FineractClient.class);
            Map<String, Boolean> request = new HashMap<>();
            request.put(code, true);
            client.makerCheckerPermissionUpdate(getSession().getIdentifier(), getSession().getToken(), request);
            target.add(this.makercheckerBrowseTable);
        } else if ("Disable".equals(link)) {
            ApplicationContext context = WicketFactory.getApplicationContext();
            FineractClient client = context.getBean(FineractClient.class);
            Map<String, Boolean> request = new HashMap<>();
            request.put(code, false);
            client.makerCheckerPermissionUpdate(getSession().getIdentifier(), getSession().getToken(), request);
            target.add(this.makercheckerBrowseTable);
        }
    }

    protected void searchButtonSubmit() {
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
        MPermission mPermission = MPermission.staticInitialize(dataContext);
        this.makercheckerBrowseProvider.removeWhere("grouping");
        if (this.groupingValue != null && !"".equals(this.groupingValue)) {
            this.makercheckerBrowseProvider.applyWhere("grouping", mPermission.getName() + "." + mPermission.GROUPING.getName() + " LIKE '" + StringEscapeUtils.escapeSql(this.groupingValue) + "%'");
        }
        this.makercheckerBrowseProvider.removeWhere("entity");
        if (this.entityValue != null && !"".equals(this.entityValue)) {
            this.makercheckerBrowseProvider.applyWhere("entity", mPermission.getName() + "." + mPermission.ENTITY_NAME.getName() + " LIKE '" + StringEscapeUtils.escapeSql(this.entityValue) + "%'");
        }
    }

}
