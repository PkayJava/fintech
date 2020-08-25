package com.angkorteam.fintech.pages.admin.system.code;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.client.FineractClient;
import com.angkorteam.fintech.client.Function;
import com.angkorteam.fintech.client.dto.CodeRequest;
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
@Bookmark("/admin/system/code/create")
public class CodeCreatePage extends MasterPage {

    protected Form<Void> createForm;

    protected UIRow row1;

    protected UIColumn nameColumn;
    protected UIContainer nameContainer;
    protected TextField<String> nameField;
    protected String nameValue;

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
        this.nameField.add(new CodeNameValidator());
        this.nameContainer.add(this.nameField);
        this.nameContainer.newFeedback("nameFeedback", this.nameField);

        this.row1.newUIColumn("lastColumn");

        this.createButton = new Button("createButton") {
            @Override
            public void onSubmit() {
                createButtonSubmit();
            }
        };
        this.createForm.add(this.createButton);

        this.cancelButton = new BookmarkablePageLink<>("cancelButton", CodeBrowsePage.class);
        this.createForm.add(this.cancelButton);
    }

    protected void createButtonSubmit() {
        ApplicationContext context = WicketFactory.getApplicationContext();
        FineractClient client = context.getBean(FineractClient.class);
        CodeRequest request = new CodeRequest();
        request.setName(this.nameValue);
        client.codeCreate(getSession().getIdentifier(), getSession().getToken(), request);

        setResponsePage(CodeBrowsePage.class);
    }

}
