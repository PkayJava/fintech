package com.angkorteam.fintech.pages.holiday;

import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.DeprecatedPage;
import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.HolidayBuilder;
import com.angkorteam.fintech.dto.enums.ReschedulingType;
import com.angkorteam.fintech.helper.HolidayHelper;
import com.angkorteam.fintech.provider.MultipleChoiceProvider;
import com.angkorteam.fintech.provider.ReschedulingTypeProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2MultipleChoice;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/26/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class HolidayCreatePage extends DeprecatedPage {

    protected String nameValue;
    protected TextField<String> nameField;
    protected TextFeedbackPanel nameFeedback;

    protected String descriptionValue;
    protected TextField<String> descriptionField;
    protected TextFeedbackPanel descriptionFeedback;

    protected MultipleChoiceProvider officeProvider;
    protected List<Option> officeValue;
    protected Select2MultipleChoice<Option> officeField;
    protected TextFeedbackPanel officeFeedback;

    protected Date fromDateValue;
    protected DateTextField fromDateField;
    protected TextFeedbackPanel fromDateFeedback;

    protected Date toDateValue;
    protected DateTextField toDateField;
    protected TextFeedbackPanel toDateFeedback;

    protected WebMarkupContainer repaymentsRescheduledToBlock;
    protected WebMarkupContainer repaymentsRescheduledToContainer;
    protected Date repaymentsRescheduledToValue;
    protected DateTextField repaymentsRescheduledToField;
    protected TextFeedbackPanel repaymentsRescheduledToFeedback;

    protected ReschedulingTypeProvider reschedulingTypeProvider;
    protected Option reschedulingTypeValue;
    protected Select2SingleChoice<Option> reschedulingTypeField;
    protected TextFeedbackPanel reschedulingTypeFeedback;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

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

        this.descriptionField = new TextField<>("descriptionField", new PropertyModel<>(this, "descriptionValue"));
        this.descriptionField.setRequired(true);
        this.form.add(this.descriptionField);
        this.descriptionFeedback = new TextFeedbackPanel("descriptionFeedback", this.descriptionField);
        this.form.add(this.descriptionFeedback);

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

        this.reschedulingTypeProvider = new ReschedulingTypeProvider();
        this.reschedulingTypeField = new Select2SingleChoice<>("reschedulingTypeField", new PropertyModel<>(this, "reschedulingTypeValue"), this.reschedulingTypeProvider);
        this.reschedulingTypeField.setRequired(true);
        this.reschedulingTypeField.add(new OnChangeAjaxBehavior(this::reschedulingTypeFieldUpdate));
        this.form.add(this.reschedulingTypeField);
        this.reschedulingTypeFeedback = new TextFeedbackPanel("reschedulingTypeFeedback", this.reschedulingTypeField);
        this.form.add(this.reschedulingTypeFeedback);

        this.repaymentsRescheduledToBlock = new WebMarkupContainer("repaymentsRescheduledToBlock");
        this.form.add(this.repaymentsRescheduledToBlock);
        this.repaymentsRescheduledToBlock.setOutputMarkupId(true);
        this.repaymentsRescheduledToContainer = new WebMarkupContainer("repaymentsRescheduledToContainer");
        this.repaymentsRescheduledToBlock.add(this.repaymentsRescheduledToContainer);
        this.repaymentsRescheduledToField = new DateTextField("repaymentsRescheduledToField", new PropertyModel<>(this, "repaymentsRescheduledToValue"));
        this.repaymentsRescheduledToField.setRequired(true);
        this.repaymentsRescheduledToContainer.add(this.repaymentsRescheduledToField);
        this.repaymentsRescheduledToFeedback = new TextFeedbackPanel("repaymentsRescheduledToFeedback", this.repaymentsRescheduledToField);
        this.repaymentsRescheduledToContainer.add(this.repaymentsRescheduledToFeedback);

        this.officeProvider = new MultipleChoiceProvider("m_office", "id", "name");
        this.officeField = new Select2MultipleChoice<>("officeField", new PropertyModel<>(this, "officeValue"), this.officeProvider);
        this.officeField.setRequired(true);
        this.form.add(this.officeField);
        this.officeFeedback = new TextFeedbackPanel("officeFeedback", this.officeField);
        this.form.add(this.officeFeedback);

        reschedulingTypeFieldUpdate(null);
    }

    protected boolean reschedulingTypeFieldUpdate(AjaxRequestTarget target) {
        ReschedulingType reschedulingType = null;
        if (this.reschedulingTypeValue != null) {
            reschedulingType = ReschedulingType.valueOf(reschedulingTypeValue.getId());
        }
        boolean visible = reschedulingType == null ? false : reschedulingType == ReschedulingType.SpecifiedDate;
        this.repaymentsRescheduledToContainer.setVisible(visible);
        if (target != null) {
            target.add(this.repaymentsRescheduledToBlock);
        }
        return false;
    }

    protected void saveButtonSubmit(Button button) {
        HolidayBuilder builder = new HolidayBuilder();
        if (this.officeValue != null) {
            for (Option office : this.officeValue) {
                builder.withOffice(office.getId());
            }
        }

        ReschedulingType reschedulingType = null;
        if (this.reschedulingTypeValue != null) {
            reschedulingType = ReschedulingType.valueOf(reschedulingTypeValue.getId());
        }

        if (reschedulingType != null && reschedulingType == ReschedulingType.SpecifiedDate) {
            builder.withRepaymentsRescheduledTo(this.repaymentsRescheduledToValue);
        }

        builder.withReschedulingType(reschedulingType);
        builder.withDescription(this.descriptionValue);
        builder.withFromDate(this.fromDateValue);
        builder.withToDate(this.toDateValue);
        builder.withName(this.nameValue);

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
