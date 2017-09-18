package com.angkorteam.fintech.pages.holiday;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.request.HolidayBuilder;
import com.angkorteam.fintech.helper.HolidayHelper;
import com.angkorteam.fintech.provider.MultipleChoiceProvider;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2MultipleChoice;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;

import java.util.Date;
import java.util.List;

/**
 * Created by socheatkhauv on 6/26/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class HolidayCreatePage extends Page {

    private String nameValue;
    private TextField<String> nameField;
    private TextFeedbackPanel nameFeedback;

    private MultipleChoiceProvider officeProvider;
    private List<Option> officeValue;
    private Select2MultipleChoice<Option> officeField;
    private TextFeedbackPanel officeFeedback;

    private Date fromDateValue;
    private DateTextField fromDateField;
    private TextFeedbackPanel fromDateFeedback;

    private Date toDateValue;
    private DateTextField toDateField;
    private TextFeedbackPanel toDateFeedback;

    private Date repaymentsRescheduledToValue;
    private DateTextField repaymentsRescheduledToField;
    private TextFeedbackPanel repaymentsRescheduledToFeedback;

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", HolidayBrowsePage.class);
        this.form.add(this.closeLink);

        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setRequired(true);
        this.form.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.form.add(this.nameFeedback);

        this.fromDateField = new DateTextField("fromDateField", new PropertyModel<>(this, "fromDateValue"));
        this.fromDateField.setRequired(true);
        this.form.add(this.fromDateField);
        this.fromDateFeedback = new TextFeedbackPanel("fromDateFeedback", this.fromDateField);
        this.form.add(this.fromDateFeedback);

        this.toDateField = new DateTextField("toDateField", new PropertyModel<>(this, "toDateValue"));
        this.toDateField.setRequired(true);
        this.form.add(this.toDateField);
        this.toDateFeedback = new TextFeedbackPanel("toDateFeedback", this.toDateField);
        this.form.add(this.toDateFeedback);

        this.repaymentsRescheduledToField = new DateTextField("repaymentsRescheduledToField", new PropertyModel<>(this, "repaymentsRescheduledToValue"));
        this.repaymentsRescheduledToField.setRequired(true);
        this.form.add(this.repaymentsRescheduledToField);
        this.repaymentsRescheduledToFeedback = new TextFeedbackPanel("repaymentsRescheduledToFeedback", this.repaymentsRescheduledToField);
        this.form.add(this.repaymentsRescheduledToFeedback);

        this.officeProvider = new MultipleChoiceProvider("m_office", "id", "name");
        this.officeField = new Select2MultipleChoice<>("officeField", new PropertyModel<>(this, "officeValue"), this.officeProvider);
        this.officeField.setRequired(true);
        this.form.add(this.officeField);
        this.officeFeedback = new TextFeedbackPanel("officeFeedback", this.officeField);
        this.form.add(this.officeFeedback);
    }

    private void saveButtonSubmit(Button button) {
        HolidayBuilder builder = new HolidayBuilder();
        if (this.officeValue != null) {
            for (Option office : this.officeValue) {
                builder.withOffice(office.getId());
            }
        }
        builder.withFromDate(this.fromDateValue);
        builder.withToDate(this.toDateValue);
        builder.withName(this.nameValue);
        builder.withRepaymentsRescheduledTo(this.repaymentsRescheduledToValue);

        JsonNode node = null;
        try {
            node = HolidayHelper.create((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(HolidayBrowsePage.class);
    }

}
