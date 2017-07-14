package com.angkorteam.fintech.pages.teller;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.TellerState;
import com.angkorteam.fintech.dto.request.TellerBuilder;
import com.angkorteam.fintech.helper.TellerHelper;
import com.angkorteam.fintech.provider.TellerStateProvider;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.OptionSingleChoiceProvider;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;

import java.util.Date;

/**
 * Created by socheatkhauv on 7/13/17.
 */
public class TellerCreatePage extends Page {

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

    private String nameValue;
    private TextField<String> nameField;
    private TextFeedbackPanel nameFeedback;

    private OptionSingleChoiceProvider officeProvider;
    private Option officeValue;
    private Select2SingleChoice<Option> officeField;
    private TextFeedbackPanel officeFeedback;

    private TellerStateProvider statusProvider;
    private Option statusValue;
    private Select2SingleChoice<Option> statusField;
    private TextFeedbackPanel statusFeedback;

    private Date startDateValue;
    private DateTextField startDateField;
    private TextFeedbackPanel startDateFeedback;

    private Date endDateValue;
    private DateTextField endDateField;
    private TextFeedbackPanel endDateFeedback;

    private String descriptionValue;
    private TextArea<String> descriptionField;
    private TextFeedbackPanel descriptionFeedback;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", TellerBrowsePage.class);
        this.form.add(this.closeLink);

        this.officeProvider = new OptionSingleChoiceProvider("m_office", "id", "name");
        this.officeField = new Select2SingleChoice<>("officeField", 0, new PropertyModel<>(this, "officeValue"), this.officeProvider);
        this.officeField.setRequired(true);
        this.form.add(this.officeField);
        this.officeFeedback = new TextFeedbackPanel("officeFeedback", this.officeField);
        this.form.add(this.officeFeedback);

        this.statusProvider = new TellerStateProvider();
        this.statusField = new Select2SingleChoice<>("statusField", 0, new PropertyModel<>(this, "statusValue"), this.statusProvider);
        this.form.add(this.statusField);
        this.statusFeedback = new TextFeedbackPanel("statusFeedback", this.statusField);
        this.form.add(this.statusFeedback);

        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setRequired(true);
        this.form.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.form.add(this.nameFeedback);

        this.startDateField = new DateTextField("startDateField", new PropertyModel<>(this, "startDateValue"));
        this.startDateField.setRequired(true);
        this.form.add(this.startDateField);
        this.startDateFeedback = new TextFeedbackPanel("startDateFeedback", this.startDateField);
        this.form.add(this.startDateFeedback);

        this.endDateField = new DateTextField("endDateField", new PropertyModel<>(this, "endDateValue"));
        this.endDateField.setRequired(true);
        this.form.add(this.endDateField);
        this.endDateFeedback = new TextFeedbackPanel("endDateFeedback", this.endDateField);
        this.form.add(this.endDateFeedback);

        this.descriptionField = new TextArea<>("descriptionField", new PropertyModel<>(this, "descriptionValue"));
        this.descriptionField.setRequired(true);
        this.form.add(this.descriptionField);
        this.descriptionFeedback = new TextFeedbackPanel("descriptionFeedback", this.descriptionField);
        this.form.add(this.descriptionFeedback);
    }

    private void saveButtonSubmit(Button button) {
        TellerBuilder builder = new TellerBuilder();
        builder.withDescription(this.descriptionValue);
        builder.withName(this.nameValue);
        builder.withEndDate(this.endDateValue);
        builder.withStartDate(this.startDateValue);
        builder.withStatus(TellerState.valueOf(this.statusValue.getId()));
        if (this.officeValue != null) {
            builder.withOfficeId(this.officeValue.getId());
        }

        JsonNode node = null;
        try {
            node = TellerHelper.createTeller(builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(TellerBrowsePage.class);

    }

}
