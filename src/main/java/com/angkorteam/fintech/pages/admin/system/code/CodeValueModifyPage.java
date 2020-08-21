package com.angkorteam.fintech.pages.admin.system.code;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.MifosDataContextManager;
import com.angkorteam.fintech.client.FineractClient;
import com.angkorteam.fintech.client.Function;
import com.angkorteam.fintech.client.dto.PutCodeValueRequest;
import com.angkorteam.fintech.meta.tenant.MCodeValue;
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
@Bookmark("/admin/system/code/value/modify")
public class CodeValueModifyPage extends MasterPage {

    protected String codeId;
    protected String codeValueId;

    protected Form<Void> modifyForm;

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
    protected TextField<String> mandatoryField;
    protected String mandatoryValue;

    protected Button updateButton;
    protected BookmarkablePageLink<Void> cancelButton;

    @Override
    protected void onInitData() {
        super.onInitData();
        this.codeId = getPageParameters().get("codeId").toString();
        this.codeValueId = getPageParameters().get("codeValueId").toString();

        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
        MCodeValue mCodeValue = MCodeValue.staticInitialize(dataContext);

        try (DataSet rows = dataContext.query().from(mCodeValue).selectAll().where(mCodeValue.ID).eq(this.codeValueId).execute()) {
            rows.next();
            this.nameValue = (String) rows.getRow().getValue(mCodeValue.CODE_VALUE);
            this.positionValue = (int) rows.getRow().getValue(mCodeValue.ORDER_POSITION);
            this.descriptionValue = (String) rows.getRow().getValue(mCodeValue.CODE_DESCRIPTION);
            if (rows.getRow().getValue(mCodeValue.ACTIVE) == null) {
                this.activeValue = "No";
            } else {
                this.activeValue = (int) rows.getRow().getValue(mCodeValue.ACTIVE) == 1 ? "Yes" : "No";
            }
            if (rows.getRow().getValue(mCodeValue.MANDATORY) == null) {
                this.mandatoryValue = "No";
            } else {
                this.mandatoryValue = (int) rows.getRow().getValue(mCodeValue.MANDATORY) == 1 ? "Yes" : "No";
            }
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
        this.nameField.add(new CodeValueNameValidator(Long.valueOf(this.codeId), Long.valueOf(this.codeValueId)));
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

        this.row2 = UIRow.newUIRow("row2", this.modifyForm);

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
        this.mandatoryField = new TextField<>("mandatoryField", new PropertyModel<>(this, "mandatoryValue"));
        this.mandatoryField.setLabel(Model.of("Mandatory"));
        this.mandatoryField.setEnabled(false);
        this.mandatoryContainer.add(this.mandatoryField);
        this.mandatoryContainer.newFeedback("mandatoryFeedback", this.mandatoryField);

        this.row2.newUIColumn("lastColumn");

        this.updateButton = new Button("updateButton") {
            @Override
            public void onSubmit() {
                updateButtonSubmit();
            }
        };
        this.modifyForm.add(this.updateButton);

        PageParameters parameters = new PageParameters();
        parameters.add("codeId", this.codeId);
        this.cancelButton = new BookmarkablePageLink<>("cancelButton", CodeModifyPage.class, parameters);
        this.modifyForm.add(this.cancelButton);
    }

    protected void updateButtonSubmit() {
        ApplicationContext context = WicketFactory.getApplicationContext();
        FineractClient client = context.getBean(FineractClient.class);
        PutCodeValueRequest request = new PutCodeValueRequest();
        request.setName(this.nameValue);
        request.setActive("Yes".equals(this.activeValue));
        request.setPosition(this.positionValue);
        request.setDescription(this.descriptionValue);
        client.codeValueUpdate(getSession().getIdentifier(), getSession().getToken(), Long.valueOf(this.codeId), Long.valueOf(this.codeValueId), request);

        PageParameters parameters = new PageParameters();
        parameters.add("codeId", this.codeId);
        setResponsePage(CodeModifyPage.class, parameters);
    }

}
