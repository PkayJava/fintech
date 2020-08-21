package com.angkorteam.fintech.pages.admin.system.code;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.client.FineractClient;
import com.angkorteam.fintech.client.Function;
import com.angkorteam.fintech.client.dto.PostCodeValueRequest;
import com.angkorteam.webui.frmk.common.Bookmark;
import com.angkorteam.webui.frmk.common.WicketFactory;
import com.angkorteam.webui.frmk.wicket.layout.Size;
import com.angkorteam.webui.frmk.wicket.layout.UIColumn;
import com.angkorteam.webui.frmk.wicket.layout.UIContainer;
import com.angkorteam.webui.frmk.wicket.layout.UIRow;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
@Bookmark("/admin/system/code/value/create")
public class CodeValueCreatePage extends MasterPage {

    protected String codeId;

    protected Form<Void> createForm;

    protected UIRow row1;

    protected UIColumn nameColumn;
    protected UIContainer nameContainer;
    protected TextField<String> nameField;
    protected String nameValue;

    protected UIColumn descriptionColumn;
    protected UIContainer descriptionContainer;
    protected TextField<String> descriptionField;
    protected String descriptionValue;

    protected UIRow row2;

    protected UIColumn positionColumn;
    protected UIContainer positionContainer;
    protected TextField<Integer> positionField;
    protected int positionValue;

    protected UIColumn activeColumn;
    protected UIContainer activeContainer;
    protected DropDownChoice<String> activeField;
    protected String activeValue;

    protected UIColumn mandatoryColumn;
    protected UIContainer mandatoryContainer;
    protected DropDownChoice<String> mandatoryField;
    protected String mandatoryValue;

    protected Button createButton;
    protected BookmarkablePageLink<Void> cancelButton;

    @Override
    protected void onInitData() {
        super.onInitData();
        this.codeId = getPageParameters().get("codeId").toString();
    }

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
        this.nameField.add(new CodeValueNameValidator(Long.valueOf(this.codeId)));
        this.nameContainer.add(this.nameField);
        this.nameContainer.newFeedback("nameFeedback", this.nameField);

        this.descriptionColumn = this.row1.newUIColumn("descriptionColumn", Size.Eight_8);
        this.descriptionContainer = this.descriptionColumn.newUIContainer("descriptionContainer");
        this.descriptionField = new TextField<>("descriptionField", new PropertyModel<>(this, "descriptionValue"));
        this.descriptionField.setLabel(Model.of("Description"));
        this.descriptionField.setRequired(true);
        this.descriptionContainer.add(this.descriptionField);
        this.descriptionContainer.newFeedback("descriptionFeedback", this.descriptionField);

        this.row1.newUIColumn("lastColumn");

        this.row2 = UIRow.newUIRow("row2", this.createForm);

        this.positionColumn = this.row2.newUIColumn("positionColumn", Size.Four_4);
        this.positionContainer = this.positionColumn.newUIContainer("positionContainer");
        this.positionField = new TextField<>("positionField", new PropertyModel<>(this, "positionValue"));
        this.positionField.setLabel(Model.of("Position"));
        this.positionField.setRequired(true);
        this.positionContainer.add(this.positionField);
        this.positionContainer.newFeedback("positionFeedback", this.positionField);

        this.activeColumn = this.row2.newUIColumn("activeColumn", Size.Four_4);
        this.activeContainer = this.activeColumn.newUIContainer("activeContainer");
        this.activeField = new DropDownChoice<>("activeField", new PropertyModel<>(this, "activeValue"), Arrays.asList("Yes", "No"));
        this.activeField.setLabel(Model.of("Active"));
        this.activeField.setRequired(true);
        this.activeContainer.add(this.activeField);
        this.activeContainer.newFeedback("activeFeedback", this.activeField);

        this.mandatoryColumn = this.row2.newUIColumn("mandatoryColumn", Size.Four_4);
        this.mandatoryContainer = this.mandatoryColumn.newUIContainer("mandatoryContainer");
        this.mandatoryField = new DropDownChoice<>("mandatoryField", new PropertyModel<>(this, "mandatoryValue"), Arrays.asList("Yes", "No"));
        this.mandatoryField.setLabel(Model.of("Mandatory"));
        this.mandatoryField.setRequired(true);
        this.mandatoryContainer.add(this.mandatoryField);
        this.mandatoryContainer.newFeedback("mandatoryFeedback", this.mandatoryField);

        this.row2.newUIColumn("lastColumn");

        this.createButton = new Button("createButton") {
            @Override
            public void onSubmit() {
                createButtonSubmit();
            }
        };
        this.createForm.add(this.createButton);

        PageParameters parameters = new PageParameters();
        parameters.add("codeId", this.codeId);
        this.cancelButton = new BookmarkablePageLink<>("cancelButton", CodeModifyPage.class, parameters);
        this.createForm.add(this.cancelButton);
    }

    protected void createButtonSubmit() {
        ApplicationContext context = WicketFactory.getApplicationContext();
        FineractClient client = context.getBean(FineractClient.class);
        PostCodeValueRequest request = new PostCodeValueRequest();
        request.setName(this.nameValue);
        request.setActive("Yes".equals(this.activeValue));
        request.setMandatory("Yes".equals(this.mandatoryValue));
        request.setPosition(this.positionValue);
        request.setDescription(this.descriptionValue);
        client.codeValueCreate(getSession().getIdentifier(), getSession().getToken(), Long.valueOf(this.codeId), request);

        PageParameters parameters = new PageParameters();
        parameters.add("codeId", this.codeId);
        setResponsePage(CodeModifyPage.class, parameters);

    }

}
