package com.angkorteam.fintech.pages.admin.system.role;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.MifosDataContextManager;
import com.angkorteam.fintech.client.FineractClient;
import com.angkorteam.fintech.client.Function;
import com.angkorteam.fintech.client.dto.RoleRequest;
import com.angkorteam.fintech.meta.tenant.MRole;
import com.angkorteam.webui.frmk.common.Bookmark;
import com.angkorteam.webui.frmk.common.WicketFactory;
import com.angkorteam.webui.frmk.wicket.layout.Size;
import com.angkorteam.webui.frmk.wicket.layout.UIColumn;
import com.angkorteam.webui.frmk.wicket.layout.UIContainer;
import com.angkorteam.webui.frmk.wicket.layout.UIRow;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.data.DataSet;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.springframework.context.ApplicationContext;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
@Bookmark("/admin/system/role/modify")
public class RoleModifyPage extends MasterPage {

    protected String roleId;

    protected Form<Void> modifyForm;

    protected UIRow row1;

    protected UIColumn nameColumn;
    protected UIContainer nameContainer;
    protected TextField<String> nameField;
    protected String nameValue;

    protected UIRow row2;

    protected UIColumn descriptionColumn;
    protected UIContainer descriptionContainer;
    protected TextField<String> descriptionField;
    protected String descriptionValue;

    protected Button updateButton;
    protected BookmarkablePageLink<Void> cancelButton;

    @Override
    protected void onInitData() {
        super.onInitData();
        this.roleId = getPageParameters().get("roleId").toString();
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
        MRole mRole = MRole.staticInitialize(dataContext);

        try (DataSet rows = dataContext.query().from(mRole).selectAll().where(mRole.ID).eq(this.roleId).execute()) {
            rows.next();
            this.nameValue = (String) rows.getRow().getValue(mRole.NAME);
            this.descriptionValue = (String) rows.getRow().getValue(mRole.DESCRIPTION);
        }
    }

    @Override
    protected void onInitHtml(MarkupContainer body) {
        this.modifyForm = new Form<>("modifyForm");
        body.add(this.modifyForm);

        this.row1 = UIRow.newUIRow("row1", this.modifyForm);

        this.nameColumn = this.row1.newUIColumn("nameColumn", Size.Four_4);
        this.nameContainer = this.nameColumn.newUIContainer("nameContainer");
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setLabel(Model.of("Name"));
        this.nameField.setRequired(true);
        this.nameField.add(new NameValidator(Long.valueOf(this.roleId)));
        this.nameContainer.add(this.nameField);
        this.nameContainer.newFeedback("nameFeedback", this.nameField);

        this.row1.newUIColumn("lastColumn");

        this.row2 = UIRow.newUIRow("row2", this.modifyForm);

        this.descriptionColumn = this.row2.newUIColumn("descriptionColumn", Size.Four_4);
        this.descriptionContainer = this.descriptionColumn.newUIContainer("descriptionContainer");
        this.descriptionField = new TextField<>("descriptionField", new PropertyModel<>(this, "descriptionValue"));
        this.descriptionField.setLabel(Model.of("Description"));
        this.descriptionField.setRequired(true);
        this.descriptionContainer.add(this.descriptionField);
        this.descriptionContainer.newFeedback("descriptionFeedback", this.descriptionField);

        this.row2.newUIColumn("lastColumn");

        this.updateButton = new Button("updateButton") {
            @Override
            public void onSubmit() {
                updateButtonSubmit();
            }
        };
        this.modifyForm.add(this.updateButton);

        this.cancelButton = new BookmarkablePageLink<>("cancelButton", RoleBrowsePage.class);
        this.modifyForm.add(this.cancelButton);
    }

    protected void updateButtonSubmit() {
        ApplicationContext context = WicketFactory.getApplicationContext();
        FineractClient client = context.getBean(FineractClient.class);
        RoleRequest request = new RoleRequest();
        request.setDescription(this.descriptionValue);
        request.setName(this.nameValue);
        client.roleUpdate(getSession().getIdentifier(), getSession().getToken(), Long.valueOf(this.roleId), request);
        setResponsePage(RoleBrowsePage.class);
    }

}
