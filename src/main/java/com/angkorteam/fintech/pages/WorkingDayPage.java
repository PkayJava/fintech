package com.angkorteam.fintech.pages;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.RepaymentOption;
import com.angkorteam.fintech.dto.request.WorkingDayBuilder;
import com.angkorteam.fintech.helper.WorkingDayHelper;
import com.angkorteam.fintech.provider.RepaymentOptionProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/26/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
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

    private static final List<PageBreadcrumb> BREADCRUMB;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        return Model.ofList(BREADCRUMB);
    }

    static {
        BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Admin");
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Organization");
            breadcrumb.setPage(OrganizationDashboardPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Working Days");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        initData();

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", OrganizationDashboardPage.class);
        this.form.add(this.closeLink);

        this.mondayField = new CheckBox("mondayField", new PropertyModel<>(this, "mondayValue"));
        this.mondayField.setRequired(true);
        this.form.add(this.mondayField);
        this.mondayFeedback = new TextFeedbackPanel("mondayFeedback", this.mondayField);
        this.form.add(this.mondayFeedback);

        this.tuesdayField = new CheckBox("tuesdayField", new PropertyModel<>(this, "tuesdayValue"));
        this.tuesdayField.setRequired(true);
        this.form.add(this.tuesdayField);
        this.tuesdayFeedback = new TextFeedbackPanel("tuesdayFeedback", this.tuesdayField);
        this.form.add(this.tuesdayFeedback);

        this.wednesdayField = new CheckBox("wednesdayField", new PropertyModel<>(this, "wednesdayValue"));
        this.wednesdayField.setRequired(true);
        this.form.add(this.wednesdayField);
        this.wednesdayFeedback = new TextFeedbackPanel("wednesdayFeedback", this.wednesdayField);
        this.form.add(this.wednesdayFeedback);

        this.thursdayField = new CheckBox("thursdayField", new PropertyModel<>(this, "thursdayValue"));
        this.thursdayField.setRequired(true);
        this.form.add(this.thursdayField);
        this.thursdayFeedback = new TextFeedbackPanel("thursdayFeedback", this.thursdayField);
        this.form.add(this.thursdayFeedback);

        this.fridayField = new CheckBox("fridayField", new PropertyModel<>(this, "fridayValue"));
        this.fridayField.setRequired(true);
        this.form.add(this.fridayField);
        this.fridayFeedback = new TextFeedbackPanel("fridayFeedback", this.fridayField);
        this.form.add(this.fridayFeedback);

        this.saturdayField = new CheckBox("saturdayField", new PropertyModel<>(this, "saturdayValue"));
        this.saturdayField.setRequired(true);
        this.form.add(this.saturdayField);
        this.saturdayFeedback = new TextFeedbackPanel("saturdayFeedback", this.saturdayField);
        this.form.add(this.saturdayFeedback);

        this.sundayField = new CheckBox("sundayField", new PropertyModel<>(this, "sundayValue"));
        this.sundayField.setRequired(true);
        this.form.add(this.sundayField);
        this.sundayFeedback = new TextFeedbackPanel("sundayFeedback", this.sundayField);
        this.form.add(this.sundayFeedback);

        this.repaymentOptionProvider = new RepaymentOptionProvider();
        this.repaymentOptionField = new Select2SingleChoice<>("repaymentOptionField", new PropertyModel<>(this, "repaymentOptionValue"), this.repaymentOptionProvider);
        this.repaymentOptionField.setRequired(true);
        this.form.add(this.repaymentOptionField);
        this.repaymentOptionFeedback = new TextFeedbackPanel("repaymentOptionFeedback", this.repaymentOptionField);
        this.form.add(this.repaymentOptionFeedback);

        this.repaymentExtendTermField = new CheckBox("repaymentExtendTermField", new PropertyModel<>(this, "repaymentExtendTermValue"));
        this.repaymentExtendTermField.setRequired(true);
        this.form.add(this.repaymentExtendTermField);
        this.repaymentExtendTermFeedback = new TextFeedbackPanel("repaymentExtendTermFeedback", this.repaymentExtendTermField);
        this.form.add(this.repaymentExtendTermFeedback);
    }

    protected void initData() {
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);

        Map<String, Object> object = jdbcTemplate.queryForMap("select * from m_working_days limit 1");

        String recurrence = (String) object.get("recurrence");
        String[] recurrenceSegments = StringUtils.split(recurrence, ';');
        String days = recurrenceSegments[2];
        this.mondayValue = days.contains("MO");
        this.tuesdayValue = days.contains("TU");
        this.wednesdayValue = days.contains("WE");
        this.thursdayValue = days.contains("TH");
        this.fridayValue = days.contains("FR");
        this.saturdayValue = days.contains("ST");
        this.sundayValue = days.contains("SU");
        if (object.get("repayment_rescheduling_enum") != null) {
            for (RepaymentOption repayment : RepaymentOption.values()) {
                if (repayment.getLiteral().equals(object.get("repayment_rescheduling_enum"))) {
                    this.repaymentOptionValue = new Option(repayment.name(), repayment.getDescription());
                }
            }

        }
        this.repaymentExtendTermValue = (Boolean) object.get("extend_term_daily_repayments");
    }

    protected void saveButtonSubmit(Button button) {
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
            node = WorkingDayHelper.update((Session) getSession(), builder.build());
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
