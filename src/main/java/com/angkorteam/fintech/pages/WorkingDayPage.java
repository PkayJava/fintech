package com.angkorteam.fintech.pages;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.WorkingDayBuilder;
import com.angkorteam.fintech.dto.enums.RepaymentOption;
import com.angkorteam.fintech.helper.WorkingDayHelper;
import com.angkorteam.fintech.provider.RepaymentOptionProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
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

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock mondayBlock;
    protected WebMarkupContainer mondayIContainer;
    protected Boolean mondayValue;
    protected CheckBox mondayField;
    protected TextFeedbackPanel mondayFeedback;

    protected WebMarkupBlock tuesdayBlock;
    protected WebMarkupContainer tuesdayIContainer;
    protected Boolean tuesdayValue;
    protected CheckBox tuesdayField;
    protected TextFeedbackPanel tuesdayFeedback;

    protected WebMarkupBlock wednesdayBlock;
    protected WebMarkupContainer wednesdayIContainer;
    protected Boolean wednesdayValue;
    protected CheckBox wednesdayField;
    protected TextFeedbackPanel wednesdayFeedback;

    protected WebMarkupBlock thursdayBlock;
    protected WebMarkupContainer thursdayIContainer;
    protected Boolean thursdayValue;
    protected CheckBox thursdayField;
    protected TextFeedbackPanel thursdayFeedback;

    protected WebMarkupBlock fridayBlock;
    protected WebMarkupContainer fridayIContainer;
    protected Boolean fridayValue;
    protected CheckBox fridayField;
    protected TextFeedbackPanel fridayFeedback;

    protected WebMarkupBlock saturdayBlock;
    protected WebMarkupContainer saturdayIContainer;
    protected Boolean saturdayValue;
    protected CheckBox saturdayField;
    protected TextFeedbackPanel saturdayFeedback;

    protected WebMarkupBlock sundayBlock;
    protected WebMarkupContainer sundayIContainer;
    protected Boolean sundayValue;
    protected CheckBox sundayField;
    protected TextFeedbackPanel sundayFeedback;

    protected WebMarkupBlock repaymentOptionBlock;
    protected WebMarkupContainer repaymentOptionIContainer;
    protected RepaymentOptionProvider repaymentOptionProvider;
    protected Option repaymentOptionValue;
    protected Select2SingleChoice<Option> repaymentOptionField;
    protected TextFeedbackPanel repaymentOptionFeedback;

    protected WebMarkupBlock repaymentExtendTermBlock;
    protected WebMarkupContainer repaymentExtendTermIContainer;
    protected Boolean repaymentExtendTermValue;
    protected CheckBox repaymentExtendTermField;
    protected TextFeedbackPanel repaymentExtendTermFeedback;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
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
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", OrganizationDashboardPage.class);
        this.form.add(this.closeLink);

        initMondayBlock();

        initTuesdayBlock();

        initWednesdayBlock();

        initThursdayBlock();

        initFridayBlock();

        initSaturdayBlock();

        initSundayBlock();

        initRepaymentOptionBlock();

        initRepaymentExtendTermBlock();
    }

    protected void initRepaymentExtendTermBlock() {
        this.repaymentExtendTermBlock = new WebMarkupBlock("repaymentExtendTermBlock", Size.Twelve_12);
        this.form.add(this.repaymentExtendTermBlock);
        this.repaymentExtendTermIContainer = new WebMarkupContainer("repaymentExtendTermIContainer");
        this.repaymentExtendTermBlock.add(this.repaymentExtendTermIContainer);
        this.repaymentExtendTermField = new CheckBox("repaymentExtendTermField", new PropertyModel<>(this, "repaymentExtendTermValue"));
        this.repaymentExtendTermField.setRequired(true);
        this.repaymentExtendTermIContainer.add(this.repaymentExtendTermField);
        this.repaymentExtendTermFeedback = new TextFeedbackPanel("repaymentExtendTermFeedback", this.repaymentExtendTermField);
        this.repaymentExtendTermIContainer.add(this.repaymentExtendTermFeedback);
    }

    protected void initRepaymentOptionBlock() {
        this.repaymentOptionBlock = new WebMarkupBlock("repaymentOptionBlock", Size.Twelve_12);
        this.form.add(this.repaymentOptionBlock);
        this.repaymentOptionIContainer = new WebMarkupContainer("repaymentOptionIContainer");
        this.repaymentOptionBlock.add(this.repaymentOptionIContainer);
        this.repaymentOptionProvider = new RepaymentOptionProvider();
        this.repaymentOptionField = new Select2SingleChoice<>("repaymentOptionField", new PropertyModel<>(this, "repaymentOptionValue"), this.repaymentOptionProvider);
        this.repaymentOptionField.setRequired(true);
        this.repaymentOptionIContainer.add(this.repaymentOptionField);
        this.repaymentOptionFeedback = new TextFeedbackPanel("repaymentOptionFeedback", this.repaymentOptionField);
        this.repaymentOptionIContainer.add(this.repaymentOptionFeedback);
    }

    protected void initSundayBlock() {
        this.sundayBlock = new WebMarkupBlock("sundayBlock", Size.Twelve_12);
        this.form.add(this.sundayBlock);
        this.sundayIContainer = new WebMarkupContainer("sundayIContainer");
        this.sundayBlock.add(this.sundayIContainer);
        this.sundayField = new CheckBox("sundayField", new PropertyModel<>(this, "sundayValue"));
        this.sundayField.setRequired(true);
        this.sundayIContainer.add(this.sundayField);
        this.sundayFeedback = new TextFeedbackPanel("sundayFeedback", this.sundayField);
        this.sundayIContainer.add(this.sundayFeedback);
    }

    protected void initSaturdayBlock() {
        this.saturdayBlock = new WebMarkupBlock("saturdayBlock", Size.Twelve_12);
        this.form.add(this.saturdayBlock);
        this.saturdayIContainer = new WebMarkupContainer("saturdayIContainer");
        this.saturdayBlock.add(this.saturdayIContainer);
        this.saturdayField = new CheckBox("saturdayField", new PropertyModel<>(this, "saturdayValue"));
        this.saturdayField.setRequired(true);
        this.saturdayIContainer.add(this.saturdayField);
        this.saturdayFeedback = new TextFeedbackPanel("saturdayFeedback", this.saturdayField);
        this.saturdayIContainer.add(this.saturdayFeedback);
    }

    protected void initFridayBlock() {
        this.fridayBlock = new WebMarkupBlock("fridayBlock", Size.Twelve_12);
        this.form.add(this.fridayBlock);
        this.fridayIContainer = new WebMarkupContainer("fridayIContainer");
        this.fridayBlock.add(this.fridayIContainer);
        this.fridayField = new CheckBox("fridayField", new PropertyModel<>(this, "fridayValue"));
        this.fridayField.setRequired(true);
        this.fridayIContainer.add(this.fridayField);
        this.fridayFeedback = new TextFeedbackPanel("fridayFeedback", this.fridayField);
        this.fridayIContainer.add(this.fridayFeedback);
    }

    protected void initThursdayBlock() {
        this.thursdayBlock = new WebMarkupBlock("thursdayBlock", Size.Twelve_12);
        this.form.add(this.thursdayBlock);
        this.thursdayIContainer = new WebMarkupContainer("thursdayIContainer");
        this.thursdayBlock.add(this.thursdayIContainer);
        this.thursdayField = new CheckBox("thursdayField", new PropertyModel<>(this, "thursdayValue"));
        this.thursdayField.setRequired(true);
        this.thursdayIContainer.add(this.thursdayField);
        this.thursdayFeedback = new TextFeedbackPanel("thursdayFeedback", this.thursdayField);
        this.thursdayIContainer.add(this.thursdayFeedback);
    }

    protected void initWednesdayBlock() {
        this.wednesdayBlock = new WebMarkupBlock("wednesdayBlock", Size.Twelve_12);
        this.form.add(this.wednesdayBlock);
        this.wednesdayIContainer = new WebMarkupContainer("wednesdayIContainer");
        this.wednesdayBlock.add(this.wednesdayIContainer);
        this.wednesdayField = new CheckBox("wednesdayField", new PropertyModel<>(this, "wednesdayValue"));
        this.wednesdayField.setRequired(true);
        this.wednesdayIContainer.add(this.wednesdayField);
        this.wednesdayFeedback = new TextFeedbackPanel("wednesdayFeedback", this.wednesdayField);
        this.wednesdayIContainer.add(this.wednesdayFeedback);
    }

    protected void initTuesdayBlock() {
        this.tuesdayBlock = new WebMarkupBlock("tuesdayBlock", Size.Twelve_12);
        this.form.add(this.tuesdayBlock);
        this.tuesdayIContainer = new WebMarkupContainer("tuesdayIContainer");
        this.tuesdayBlock.add(this.tuesdayIContainer);
        this.tuesdayField = new CheckBox("tuesdayField", new PropertyModel<>(this, "tuesdayValue"));
        this.tuesdayField.setRequired(true);
        this.tuesdayIContainer.add(this.tuesdayField);
        this.tuesdayFeedback = new TextFeedbackPanel("tuesdayFeedback", this.tuesdayField);
        this.tuesdayIContainer.add(this.tuesdayFeedback);
    }

    protected void initMondayBlock() {
        this.mondayBlock = new WebMarkupBlock("mondayBlock", Size.Twelve_12);
        this.form.add(this.mondayBlock);
        this.mondayIContainer = new WebMarkupContainer("mondayIContainer");
        this.mondayBlock.add(this.mondayIContainer);
        this.mondayField = new CheckBox("mondayField", new PropertyModel<>(this, "mondayValue"));
        this.mondayField.setRequired(true);
        this.mondayIContainer.add(this.mondayField);
        this.mondayFeedback = new TextFeedbackPanel("mondayFeedback", this.mondayField);
        this.mondayIContainer.add(this.mondayFeedback);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    @Override
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
        this.repaymentOptionValue = RepaymentOption.optionLiteral(String.valueOf(object.get("repayment_rescheduling_enum")));
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
