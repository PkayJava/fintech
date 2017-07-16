package com.angkorteam.fintech.pages;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.RepaymentOption;
import com.angkorteam.fintech.dto.request.WorkingDayBuilder;
import com.angkorteam.fintech.helper.WorkingDayHelper;
import com.angkorteam.fintech.provider.RepaymentOptionProvider;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;

import java.util.Map;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class WorkingDayPage extends Page {

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

    private Boolean mondayValue;
    private CheckBox mondayField;
    private TextFeedbackPanel mondayFeedback;

    private Boolean tuesdayValue;
    private CheckBox tuesdayField;
    private TextFeedbackPanel tuesdayFeedback;

    private Boolean wednesdayValue;
    private CheckBox wednesdayField;
    private TextFeedbackPanel wednesdayFeedback;

    private Boolean thursdayValue;
    private CheckBox thursdayField;
    private TextFeedbackPanel thursdayFeedback;

    private Boolean fridayValue;
    private CheckBox fridayField;
    private TextFeedbackPanel fridayFeedback;

    private Boolean saturdayValue;
    private CheckBox saturdayField;
    private TextFeedbackPanel saturdayFeedback;

    private Boolean sundayValue;
    private CheckBox sundayField;
    private TextFeedbackPanel sundayFeedback;

    private RepaymentOptionProvider repaymentOptionProvider;
    private Option repaymentOptionValue;
    private Select2SingleChoice<Option> repaymentOptionField;
    private TextFeedbackPanel repaymentOptionFeedback;

    private Boolean repaymentExtendTermValue;
    private CheckBox repaymentExtendTermField;
    private TextFeedbackPanel repaymentExtendTermFeedback;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);

        Map<String, Object> object = jdbcTemplate.queryForMap("select * from m_working_days limit 1");

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", OrganizationDashboardPage.class);
        this.form.add(this.closeLink);

        String recurrence = (String) object.get("recurrence");
        String[] recurrenceSegments = StringUtils.split(recurrence, ';');
        String days = recurrenceSegments[2];

        this.mondayValue = days.contains("MO");
        this.mondayField = new CheckBox("mondayField", new PropertyModel<>(this, "mondayValue"));
        this.mondayField.setRequired(true);
        this.form.add(this.mondayField);
        this.mondayFeedback = new TextFeedbackPanel("mondayFeedback", this.mondayField);
        this.form.add(this.mondayFeedback);

        this.tuesdayValue = days.contains("TU");
        this.tuesdayField = new CheckBox("tuesdayField", new PropertyModel<>(this, "tuesdayValue"));
        this.tuesdayField.setRequired(true);
        this.form.add(this.tuesdayField);
        this.tuesdayFeedback = new TextFeedbackPanel("tuesdayFeedback", this.tuesdayField);
        this.form.add(this.tuesdayFeedback);

        this.wednesdayValue = days.contains("WE");
        this.wednesdayField = new CheckBox("wednesdayField", new PropertyModel<>(this, "wednesdayValue"));
        this.wednesdayField.setRequired(true);
        this.form.add(this.wednesdayField);
        this.wednesdayFeedback = new TextFeedbackPanel("wednesdayFeedback", this.wednesdayField);
        this.form.add(this.wednesdayFeedback);

        this.thursdayValue = days.contains("TH");
        this.thursdayField = new CheckBox("thursdayField", new PropertyModel<>(this, "thursdayValue"));
        this.thursdayField.setRequired(true);
        this.form.add(this.thursdayField);
        this.thursdayFeedback = new TextFeedbackPanel("thursdayFeedback", this.thursdayField);
        this.form.add(this.thursdayFeedback);

        this.fridayValue = days.contains("FR");
        this.fridayField = new CheckBox("fridayField", new PropertyModel<>(this, "fridayValue"));
        this.fridayField.setRequired(true);
        this.form.add(this.fridayField);
        this.fridayFeedback = new TextFeedbackPanel("fridayFeedback", this.fridayField);
        this.form.add(this.fridayFeedback);

        this.saturdayValue = days.contains("ST");
        this.saturdayField = new CheckBox("saturdayField", new PropertyModel<>(this, "saturdayValue"));
        this.saturdayField.setRequired(true);
        this.form.add(this.saturdayField);
        this.saturdayFeedback = new TextFeedbackPanel("saturdayFeedback", this.saturdayField);
        this.form.add(this.saturdayFeedback);

        this.sundayValue = days.contains("SU");
        this.sundayField = new CheckBox("sundayField", new PropertyModel<>(this, "sundayValue"));
        this.sundayField.setRequired(true);
        this.form.add(this.sundayField);
        this.sundayFeedback = new TextFeedbackPanel("sundayFeedback", this.sundayField);
        this.form.add(this.sundayFeedback);

        this.repaymentOptionProvider = new RepaymentOptionProvider();
        if (object.get("repayment_rescheduling_enum") != null) {
            this.repaymentOptionValue = this.repaymentOptionProvider.toChoice(String.valueOf(object.get("repayment_rescheduling_enum")));
        }
        this.repaymentOptionField = new Select2SingleChoice<>("repaymentOptionField", new PropertyModel<>(this, "repaymentOptionValue"), this.repaymentOptionProvider);
        this.repaymentOptionField.setRequired(true);
        this.form.add(this.repaymentOptionField);
        this.repaymentOptionFeedback = new TextFeedbackPanel("repaymentOptionFeedback", this.repaymentOptionField);
        this.form.add(this.repaymentOptionFeedback);

        this.repaymentExtendTermValue = (Boolean) object.get("extend_term_daily_repayments");
        this.repaymentExtendTermField = new CheckBox("repaymentExtendTermField", new PropertyModel<>(this, "repaymentExtendTermValue"));
        this.repaymentExtendTermField.setRequired(true);
        this.form.add(this.repaymentExtendTermField);
        this.repaymentExtendTermFeedback = new TextFeedbackPanel("repaymentExtendTermFeedback", this.repaymentExtendTermField);
        this.form.add(this.repaymentExtendTermFeedback);
    }

    private void saveButtonSubmit(Button button) {
        WorkingDayBuilder builder = new WorkingDayBuilder();
        builder.withExtendTermForDailyRepayments(this.repaymentExtendTermValue);
        if (this.repaymentOptionValue != null) {
            builder.withRepaymentRescheduleType(RepaymentOption.valueOf(this.repaymentOptionValue.getId()));
        }
        builder.withMonday(this.mondayValue);
        builder.withTuesday(this.tuesdayValue);
        builder.withWednesday(this.wednesdayValue);
        builder.withThursday(this.thursdayValue);
        builder.withFriday(this.fridayValue);
        builder.withSaturday(this.saturdayValue);
        builder.withSunday(this.sundayValue);

        JsonNode node = null;
        try {
            node = WorkingDayHelper.updateWorkingDay(builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(OrganizationDashboardPage.class);
    }
}
