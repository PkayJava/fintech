package com.angkorteam.fintech.pages.admin.system.role;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.client.FineractClient;
import com.angkorteam.fintech.client.Function;
import com.angkorteam.fintech.client.dto.RoleRequest;
import com.angkorteam.webui.frmk.common.Bookmark;
import com.angkorteam.webui.frmk.common.WicketFactory;
import com.angkorteam.webui.frmk.wicket.layout.Size;
import com.angkorteam.webui.frmk.wicket.layout.UIColumn;
import com.angkorteam.webui.frmk.wicket.layout.UIContainer;
import com.angkorteam.webui.frmk.wicket.layout.UIRow;
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
@Bookmark("/admin/system/role/create")
public class RoleCreatePage extends MasterPage {

    protected Form<Void> createForm;

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

    protected Button createButton;
    protected BookmarkablePageLink<Void> cancelButton;

    @Override
    protected void onInitHtml(MarkupContainer body) {
        this.createForm = new Form<>("createForm");
        body.add(this.createForm);

        this.row1 = UIRow.newUIRow("row1", this.createForm);

        this.nameColumn = this.row1.newUIColumn("nameColumn", Size.Four_4);
        this.nameContainer = this.nameColumn.newUIContainer("nameContainer");
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setLabel(Model.of("Name"));
        this.nameField.setRequired(true);
        this.nameField.add(new NameValidator());
        this.nameContainer.add(this.nameField);
        this.nameContainer.newFeedback("nameFeedback", this.nameField);

        this.row1.newUIColumn("lastColumn");

        this.row2 = UIRow.newUIRow("row2", this.createForm);

        this.descriptionColumn = this.row2.newUIColumn("descriptionColumn", Size.Four_4);
        this.descriptionContainer = this.descriptionColumn.newUIContainer("descriptionContainer");
        this.descriptionField = new TextField<>("descriptionField", new PropertyModel<>(this, "descriptionValue"));
        this.descriptionField.setLabel(Model.of("Description"));
        this.descriptionField.setRequired(true);
        this.descriptionContainer.add(this.descriptionField);
        this.descriptionContainer.newFeedback("descriptionFeedback", this.descriptionField);

        this.row2.newUIColumn("lastColumn");

        this.createButton = new Button("createButton") {
            @Override
            public void onSubmit() {
                createButtonSubmit();
            }
        };
        this.createForm.add(this.createButton);

        this.cancelButton = new BookmarkablePageLink<>("cancelButton", RoleBrowsePage.class);
        this.createForm.add(this.cancelButton);
    }

    protected void createButtonSubmit() {
        ApplicationContext context = WicketFactory.getApplicationContext();
        FineractClient client = context.getBean(FineractClient.class);
        RoleRequest request = new RoleRequest();
        request.setDescription(this.descriptionValue);
        request.setName(this.nameValue);
        client.roleCreate(getSession().getIdentifier(), getSession().getToken(), request);
        setResponsePage(RoleBrowsePage.class);
    }

}
