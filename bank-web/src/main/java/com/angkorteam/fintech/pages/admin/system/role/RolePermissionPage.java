package com.angkorteam.fintech.pages.admin.system.role;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.MifosDataContextManager;
import com.angkorteam.fintech.client.FineractClient;
import com.angkorteam.fintech.client.Function;
import com.angkorteam.fintech.data.MultipleChoiceProvider;
import com.angkorteam.fintech.data.MySQLDataProvider;
import com.angkorteam.fintech.meta.tenant.MPermission;
import com.angkorteam.fintech.meta.tenant.MRolePermission;
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
import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;
import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Select2MultipleChoice;
import org.apache.metamodel.DataContext;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
@Bookmark("/admin/system/role/permission")
public class RolePermissionPage extends MasterPage {

    protected String roleId;

    protected Form<Void> permissionForm;

    protected UIRow row1;

    protected UIColumn accessColumn;
    protected UIContainer accessContainer;
    protected Select2MultipleChoice accessField;
    protected MultipleChoiceProvider accessProvider;
    protected List<Option> accessValue;

    protected Button grantButton;
    protected BookmarkablePageLink<Void> cancelButton;

    protected FilterForm<Map<String, Expression>> permissionBrowseForm;
    protected QueryDataProvider permissionBrowseProvider;
    protected List<IColumn<Map<String, Object>, String>> permissionBrowseColumn;
    protected AbstractDataTable<Map<String, Object>, String> permissionBrowseTable;

    @Override
    protected void onInitData() {
        super.onInitData();
        this.roleId = getPageParameters().get("roleId").toString();

        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
        MPermission mPermission = MPermission.staticInitialize(dataContext);
        MRolePermission mRolePermission = MRolePermission.staticInitialize(dataContext);

        this.accessProvider = new MultipleChoiceProvider(mPermission.getName(), mPermission.CODE.getName(), mPermission.CODE.getName(), "CONCAT(" + mPermission.CODE.getName() + ",' ', '[', `" + mPermission.GROUPING.getName() + "`,']',  ' ', '[', IF(" + mPermission.ENTITY_NAME.getName() + " is NULL , 'N/A', " + mPermission.ENTITY_NAME.getName() + "), ']',' ', '[' , IF(" + mPermission.ACTION_NAME.getName() + " is NULL , 'N/A', " + mPermission.ACTION_NAME.getName() + "),']')");
        this.accessProvider.applyWhere("id", mPermission.ID.getName() + " NOT IN (SELECT " + mRolePermission.PERMISSION_ID.getName() + " from " + mRolePermission.getName() + " where " + mRolePermission.ROLE_ID.getName() + " = " + this.roleId + ")");

        this.permissionBrowseProvider = new MySQLDataProvider(mRolePermission.getName());
        this.permissionBrowseProvider.applyJoin("m_permission", "INNER JOIN " + mPermission.getName() + " ON " + mRolePermission.getName() + "." + mRolePermission.PERMISSION_ID.getName() + " = " + mPermission.getName() + "." + mPermission.ID.getName());
        this.permissionBrowseProvider.applyWhere("role", mRolePermission.getName() + "." + mRolePermission.ROLE_ID.getName() + " = " + this.roleId);

        this.permissionBrowseProvider.setCountField(mRolePermission.getName() + "." + mRolePermission.PERMISSION_ID.getName());
        this.permissionBrowseProvider.selectField(mRolePermission.getName() + "." + mRolePermission.PERMISSION_ID.getName(), "permissionId");

        this.permissionBrowseColumn = new ArrayList<>();
        this.permissionBrowseColumn.add(Column.text(Model.of("Grouping"), mPermission.getName() + "." + mPermission.GROUPING.getName(), "grouping", this.permissionBrowseProvider));
        this.permissionBrowseColumn.add(Column.text(Model.of("Code"), mPermission.getName() + "." + mPermission.CODE.getName(), "code", this.permissionBrowseProvider));
        this.permissionBrowseColumn.add(Column.text(Model.of("Entity"), mPermission.getName() + "." + mPermission.ENTITY_NAME.getName(), "entity", this.permissionBrowseProvider));
        this.permissionBrowseColumn.add(Column.text(Model.of("Operation"), mPermission.getName() + "." + mPermission.ACTION_NAME.getName(), "operation", this.permissionBrowseProvider));
        this.permissionBrowseColumn.add(Column.yesno(Model.of("Maker & Checker"), mPermission.getName() + "." + mPermission.CAN_MAKER_CHECKER.getName(), "maker_checker", this.permissionBrowseProvider));
        this.permissionBrowseColumn.add(new ActionFilteredColumn<>(Model.of("Action"), this::roleBrowseActionLink, this::roleBrowseActionClick));
    }

    @Override
    protected void onInitHtml(MarkupContainer body) {
        this.permissionForm = new Form<>("permissionForm");
        body.add(this.permissionForm);

        this.row1 = UIRow.newUIRow("row1", this.permissionForm);

        this.accessColumn = this.row1.newUIColumn("accessColumn", Size.Twelve_12);
        this.accessContainer = this.accessColumn.newUIContainer("accessContainer");
        this.accessField = new Select2MultipleChoice("accessField", new PropertyModel<>(this, "accessValue"), this.accessProvider);
        this.accessField.setLabel(Model.of("Permission"));
        this.accessField.setRequired(true);
        this.accessContainer.add(this.accessField);
        this.accessContainer.newFeedback("accessFeedback", this.accessField);

        this.row1.newUIColumn("lastColumn");

        this.grantButton = new Button("grantButton") {
            @Override
            public void onSubmit() {
                grantButtonSubmit();
            }
        };
        this.permissionForm.add(this.grantButton);

        this.cancelButton = new BookmarkablePageLink<>("cancelButton", RoleBrowsePage.class);
        this.permissionForm.add(this.cancelButton);

        this.permissionBrowseForm = new FilterForm<>("permissionBrowseForm", this.permissionBrowseProvider);
        body.add(this.permissionBrowseForm);

        this.permissionBrowseTable = new DataTable<>("permissionBrowseTable", this.permissionBrowseColumn, this.permissionBrowseProvider, 20);
        this.permissionBrowseForm.add(this.permissionBrowseTable);
    }

    protected List<ActionItem> roleBrowseActionLink(String link, Map<String, Object> model) {
        List<ActionItem> actions = new ArrayList<>();
        actions.add(new ActionItem("Revoke", Model.of("Revoke"), ItemCss.WARNING));
        return actions;
    }

    protected void roleBrowseActionClick(String link, Map<String, Object> model, AjaxRequestTarget target) {
        if ("Revoke".equals(link)) {
            ApplicationContext context = WicketFactory.getApplicationContext();
            FineractClient client = context.getBean(FineractClient.class);
            Map<String, Boolean> permissions = new HashMap<>();
            permissions.put((String) model.get("code"), false);
            client.rolePermissionUpdate(getSession().getIdentifier(), getSession().getToken(), Long.valueOf(this.roleId), permissions);
            target.add(this.permissionBrowseTable);
        }
    }

    protected void grantButtonSubmit() {
        ApplicationContext context = WicketFactory.getApplicationContext();
        if (this.accessValue != null && !this.accessValue.isEmpty()) {
            Map<String, Boolean> permissions = new HashMap<>();
            for (Option option : this.accessValue) {
                permissions.put(option.getId(), true);
            }
            FineractClient client = context.getBean(FineractClient.class);
            client.rolePermissionUpdate(getSession().getIdentifier(), getSession().getToken(), Long.valueOf(this.roleId), permissions);
        }
        PageParameters parameters = new PageParameters();
        parameters.add("roleId", this.roleId);
        setResponsePage(RolePermissionPage.class, parameters);
    }

}
