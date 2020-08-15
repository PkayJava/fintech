package com.angkorteam.fintech.pages.admin.system.configuration;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.client.Function;
import com.angkorteam.webui.frmk.wicket.layout.Size;
import com.angkorteam.webui.frmk.wicket.layout.UIColumn;
import com.angkorteam.webui.frmk.wicket.layout.UIContainer;
import com.angkorteam.webui.frmk.wicket.layout.UIRow;
import com.angkorteam.webui.frmk.wicket.markup.html.form.DateTextField;
import io.github.openunirest.http.JsonNode;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import java.util.Arrays;
import java.util.Date;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ConfigurationCreatePage extends MasterPage {

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

    protected UIColumn numberValueColumn;
    protected UIContainer numberValueContainer;
    protected TextField<Integer> numberValueField;
    protected Integer numberValueValue;

    protected UIColumn dateValueColumn;
    protected UIContainer dateValueContainer;
    protected DateTextField dateValueField;
    protected Date dateValueValue;

    protected UIRow row3;

    protected UIColumn enabledColumn;
    protected UIContainer enabledContainer;
    protected DropDownChoice<String> enabledField;
    protected String enabledValue;

    protected UIColumn trapDoorColumn;
    protected UIContainer trapDoorContainer;
    protected DropDownChoice<String> trapDoorField;
    protected String trapDoorValue;

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

        this.numberValueColumn = this.row2.newUIColumn("numberValueColumn", Size.Six_6);
        this.numberValueContainer = this.numberValueColumn.newUIContainer("numberValueContainer");
        this.numberValueField = new TextField<>("numberValueField", new PropertyModel<>(this, "numberValueValue"));
        this.numberValueField.setLabel(Model.of("Number Value"));
        this.numberValueField.setRequired(true);
        this.numberValueContainer.add(this.numberValueField);
        this.numberValueContainer.newFeedback("numberValueFeedback", this.numberValueField);

        this.dateValueColumn = this.row2.newUIColumn("dateValueColumn", Size.Six_6);
        this.dateValueContainer = this.dateValueColumn.newUIContainer("dateValueContainer");
        this.dateValueField = new DateTextField("dateValueField", new PropertyModel<>(this, "dateValueValue"));
        this.dateValueField.setLabel(Model.of("Date Value"));
        this.dateValueField.setRequired(true);
        this.dateValueContainer.add(this.dateValueField);
        this.dateValueContainer.newFeedback("dateValueFeedback", this.dateValueField);

        this.row2.newUIColumn("lastColumn");

        this.row3 = UIRow.newUIRow("row3", this.createForm);

        this.enabledColumn = this.row3.newUIColumn("enabledColumn", Size.Six_6);
        this.enabledContainer = this.enabledColumn.newUIContainer("enabledContainer");
        this.enabledField = new DropDownChoice<>("enabledField", new PropertyModel<>(this, "enabledValue"), Arrays.asList("Yes", "No"));
        this.enabledField.setLabel(Model.of("Enabled"));
        this.enabledField.setRequired(true);
        this.enabledContainer.add(this.enabledField);
        this.enabledContainer.newFeedback("enabledFeedback", this.enabledField);

        this.trapDoorColumn = this.row3.newUIColumn("trapDoorColumn", Size.Six_6);
        this.trapDoorContainer = this.trapDoorColumn.newUIContainer("trapDoorContainer");
        this.trapDoorField = new DropDownChoice<>("trapDoorField", new PropertyModel<>(this, "trapDoorValue"), Arrays.asList("Yes", "No"));
        this.trapDoorField.setLabel(Model.of("Trap Door"));
        this.trapDoorField.setRequired(true);
        this.trapDoorContainer.add(this.trapDoorField);
        this.trapDoorContainer.newFeedback("trapDoorFeedback", this.trapDoorField);

        this.row3.newUIColumn("lastColumn");

        this.createButton = new Button("createButton") {
            @Override
            public void onSubmit() {
                createButtonSubmit();
            }
        };
        this.createForm.add(this.createButton);

        this.cancelButton = new BookmarkablePageLink<>("cancelButton", ConfigurationBrowsePage.class);
        this.createForm.add(this.cancelButton);
    }

    protected void createButtonSubmit() {
        JsonNode node = null;
        // node = GlobalConfigurationHelper.updateValueForGlobalConfiguration(getSession(), this.nameValue.getId(), String.valueOf(this.valueValue));

        if (reportError(node)) {
            return;
        }
        setResponsePage(ConfigurationBrowsePage.class);
    }

}
