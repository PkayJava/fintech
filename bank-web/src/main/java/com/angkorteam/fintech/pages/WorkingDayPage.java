//package com.angkorteam.fintech.pages;
//
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
//import org.apache.wicket.markup.html.form.CheckBox;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.IModel;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//
//import com.angkorteam.fintech.Page;
//import com.angkorteam.fintech.Session;
//import com.angkorteam.fintech.ddl.MWorkingDays;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.dto.builder.WorkingDayBuilder;
//import com.angkorteam.fintech.dto.enums.RepaymentOption;
//import com.angkorteam.fintech.helper.WorkingDayHelper;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.provider.RepaymentOptionProvider;
//import com.angkorteam.framework.SpringBean;
//import com.angkorteam.framework.jdbc.SelectQuery;
//import com.angkorteam.framework.models.PageBreadcrumb;
//import com.angkorteam.framework.spring.JdbcNamed;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
//import com.google.common.collect.Lists;
//
//import io.github.openunirest.http.JsonNode;
//
///**
// * Created by socheatkhauv on 6/26/17.
// */
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class WorkingDayPage extends Page {
//
//    protected Form<Void> form;
//    protected Button saveButton;
//    protected BookmarkablePageLink<Void> closeLink;
//
//    protected UIRow row1;
//
//    protected UIBlock mondayBlock;
//    protected UIContainer mondayIContainer;
//    protected Boolean mondayValue;
//    protected CheckBox mondayField;
//
//    protected UIRow row2;
//
//    protected UIBlock tuesdayBlock;
//    protected UIContainer tuesdayIContainer;
//    protected Boolean tuesdayValue;
//    protected CheckBox tuesdayField;
//
//    protected UIRow row3;
//
//    protected UIBlock wednesdayBlock;
//    protected UIContainer wednesdayIContainer;
//    protected Boolean wednesdayValue;
//    protected CheckBox wednesdayField;
//
//    protected UIRow row4;
//
//    protected UIBlock thursdayBlock;
//    protected UIContainer thursdayIContainer;
//    protected Boolean thursdayValue;
//    protected CheckBox thursdayField;
//
//    protected UIRow row5;
//
//    protected UIBlock fridayBlock;
//    protected UIContainer fridayIContainer;
//    protected Boolean fridayValue;
//    protected CheckBox fridayField;
//
//    protected UIRow row6;
//
//    protected UIBlock saturdayBlock;
//    protected UIContainer saturdayIContainer;
//    protected Boolean saturdayValue;
//    protected CheckBox saturdayField;
//
//    protected UIRow row7;
//
//    protected UIBlock sundayBlock;
//    protected UIContainer sundayIContainer;
//    protected Boolean sundayValue;
//    protected CheckBox sundayField;
//
//    protected UIRow row8;
//
//    protected UIBlock repaymentOptionBlock;
//    protected UIContainer repaymentOptionIContainer;
//    protected RepaymentOptionProvider repaymentOptionProvider;
//    protected Option repaymentOptionValue;
//    protected Select2SingleChoice<Option> repaymentOptionField;
//
//    protected UIRow row9;
//
//    protected UIBlock repaymentExtendTermBlock;
//    protected UIContainer repaymentExtendTermIContainer;
//    protected Boolean repaymentExtendTermValue;
//    protected CheckBox repaymentExtendTermField;
//
//    @Override
//    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
//        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Admin");
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Organization");
//            breadcrumb.setPage(OrganizationDashboardPage.class);
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Working Days");
//            BREADCRUMB.add(breadcrumb);
//        }
//        return Model.ofList(BREADCRUMB);
//    }
//
//    @Override
//    protected void initComponent() {
//        this.form = new Form<>("form");
//        add(this.form);
//
//        this.saveButton = new Button("saveButton");
//        this.saveButton.setOnSubmit(this::saveButtonSubmit);
//        this.form.add(this.saveButton);
//
//        this.closeLink = new BookmarkablePageLink<>("closeLink", OrganizationDashboardPage.class);
//        this.form.add(this.closeLink);
//
//        this.row1 = UIRow.newUIRow("row1", this.form);
//
//        this.mondayBlock = this.row1.newUIBlock("mondayBlock", Size.Twelve_12);
//        this.mondayIContainer = this.mondayBlock.newUIContainer("mondayIContainer");
//        this.mondayField = new CheckBox("mondayField", new PropertyModel<>(this, "mondayValue"));
//        this.mondayIContainer.add(this.mondayField);
//        this.mondayIContainer.newFeedback("mondayFeedback", this.mondayField);
//
//        this.row2 = UIRow.newUIRow("row2", this.form);
//
//        this.tuesdayBlock = this.row2.newUIBlock("tuesdayBlock", Size.Twelve_12);
//        this.tuesdayIContainer = this.tuesdayBlock.newUIContainer("tuesdayIContainer");
//        this.tuesdayField = new CheckBox("tuesdayField", new PropertyModel<>(this, "tuesdayValue"));
//        this.tuesdayIContainer.add(this.tuesdayField);
//        this.tuesdayIContainer.newFeedback("tuesdayFeedback", this.tuesdayField);
//
//        this.row3 = UIRow.newUIRow("row3", this.form);
//
//        this.wednesdayBlock = this.row3.newUIBlock("wednesdayBlock", Size.Twelve_12);
//        this.wednesdayIContainer = this.wednesdayBlock.newUIContainer("wednesdayIContainer");
//        this.wednesdayField = new CheckBox("wednesdayField", new PropertyModel<>(this, "wednesdayValue"));
//        this.wednesdayIContainer.add(this.wednesdayField);
//        this.wednesdayIContainer.newFeedback("wednesdayFeedback", this.wednesdayField);
//
//        this.row4 = UIRow.newUIRow("row4", this.form);
//
//        this.thursdayBlock = this.row4.newUIBlock("thursdayBlock", Size.Twelve_12);
//        this.thursdayIContainer = this.thursdayBlock.newUIContainer("thursdayIContainer");
//        this.thursdayField = new CheckBox("thursdayField", new PropertyModel<>(this, "thursdayValue"));
//        this.thursdayIContainer.add(this.thursdayField);
//        this.thursdayIContainer.newFeedback("thursdayFeedback", this.thursdayField);
//
//        this.row5 = UIRow.newUIRow("row5", this.form);
//
//        this.fridayBlock = this.row5.newUIBlock("fridayBlock", Size.Twelve_12);
//        this.fridayIContainer = this.fridayBlock.newUIContainer("fridayIContainer");
//        this.fridayField = new CheckBox("fridayField", new PropertyModel<>(this, "fridayValue"));
//        this.fridayIContainer.add(this.fridayField);
//        this.fridayIContainer.newFeedback("fridayFeedback", this.fridayField);
//
//        this.row6 = UIRow.newUIRow("row6", this.form);
//
//        this.saturdayBlock = this.row6.newUIBlock("saturdayBlock", Size.Twelve_12);
//        this.saturdayIContainer = this.saturdayBlock.newUIContainer("saturdayIContainer");
//        this.saturdayField = new CheckBox("saturdayField", new PropertyModel<>(this, "saturdayValue"));
//        this.saturdayIContainer.add(this.saturdayField);
//        this.saturdayIContainer.newFeedback("saturdayFeedback", this.saturdayField);
//
//        this.row7 = UIRow.newUIRow("row7", this.form);
//
//        this.sundayBlock = this.row7.newUIBlock("sundayBlock", Size.Twelve_12);
//        this.sundayIContainer = this.sundayBlock.newUIContainer("sundayIContainer");
//        this.sundayField = new CheckBox("sundayField", new PropertyModel<>(this, "sundayValue"));
//        this.sundayIContainer.add(this.sundayField);
//        this.sundayIContainer.newFeedback("sundayFeedback", this.sundayField);
//
//        this.row8 = UIRow.newUIRow("row8", this.form);
//
//        this.repaymentOptionBlock = this.row8.newUIBlock("repaymentOptionBlock", Size.Twelve_12);
//        this.repaymentOptionIContainer = this.repaymentOptionBlock.newUIContainer("repaymentOptionIContainer");
//        this.repaymentOptionField = new Select2SingleChoice<>("repaymentOptionField", new PropertyModel<>(this, "repaymentOptionValue"), this.repaymentOptionProvider);
//        this.repaymentOptionIContainer.add(this.repaymentOptionField);
//        this.repaymentOptionIContainer.newFeedback("repaymentOptionFeedback", this.repaymentOptionField);
//
//        this.row9 = UIRow.newUIRow("row9", this.form);
//
//        this.repaymentExtendTermBlock = this.row9.newUIBlock("repaymentExtendTermBlock", Size.Twelve_12);
//        this.repaymentExtendTermIContainer = this.repaymentExtendTermBlock.newUIContainer("repaymentExtendTermIContainer");
//        this.repaymentExtendTermField = new CheckBox("repaymentExtendTermField", new PropertyModel<>(this, "repaymentExtendTermValue"));
//        this.repaymentExtendTermIContainer.add(this.repaymentExtendTermField);
//        this.repaymentExtendTermIContainer.newFeedback("repaymentExtendTermFeedback", this.repaymentExtendTermField);
//
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.mondayField.setRequired(true);
//        this.tuesdayField.setRequired(true);
//        this.wednesdayField.setRequired(true);
//        this.thursdayField.setRequired(true);
//        this.fridayField.setRequired(true);
//        this.saturdayField.setRequired(true);
//        this.sundayField.setRequired(true);
//        this.repaymentOptionField.setRequired(true);
//        this.repaymentExtendTermField.setRequired(true);
//    }
//
//    @Override
//    protected void initData() {
//        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
//
//        SelectQuery selectQuery = null;
//
//        selectQuery = new SelectQuery(MWorkingDays.NAME);
//        selectQuery.addField(MWorkingDays.Field.RECURRENCE);
//        selectQuery.addField(MWorkingDays.Field.REPAYMENT_RESCHEDULING_ENUM);
//        selectQuery.addField(MWorkingDays.Field.EXTEND_TERM_DAILY_REPAYMENTS);
//        selectQuery.setLimit(0l, 1l);
//
//        Map<String, Object> object = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
//
//        String recurrence = (String) object.get(MWorkingDays.Field.RECURRENCE);
//        String[] recurrenceSegments = StringUtils.split(recurrence, ';');
//        String days = recurrenceSegments[2];
//        this.mondayValue = days.contains("MO");
//        this.tuesdayValue = days.contains("TU");
//        this.wednesdayValue = days.contains("WE");
//        this.thursdayValue = days.contains("TH");
//        this.fridayValue = days.contains("FR");
//        this.saturdayValue = days.contains("SA");
//        this.sundayValue = days.contains("SU");
//        this.repaymentOptionValue = RepaymentOption.optionLiteral(String.valueOf(object.get(MWorkingDays.Field.REPAYMENT_RESCHEDULING_ENUM)));
//        this.repaymentExtendTermValue = (Boolean) object.get(MWorkingDays.Field.EXTEND_TERM_DAILY_REPAYMENTS);
//
//        this.repaymentOptionProvider = new RepaymentOptionProvider();
//    }
//
//    protected void saveButtonSubmit(Button button) {
//        WorkingDayBuilder builder = new WorkingDayBuilder();
//        builder.withExtendTermForDailyRepayments(this.repaymentExtendTermValue);
//        if (this.repaymentOptionValue != null) {
//            builder.withRepaymentRescheduleType(RepaymentOption.valueOf(this.repaymentOptionValue.getId()));
//        }
//        builder.withMonday(this.mondayValue);
//        builder.withTuesday(this.tuesdayValue);
//        builder.withWednesday(this.wednesdayValue);
//        builder.withThursday(this.thursdayValue);
//        builder.withFriday(this.fridayValue);
//        builder.withSaturday(this.saturdayValue);
//        builder.withSunday(this.sundayValue);
//
//        JsonNode node = WorkingDayHelper.update((Session) getSession(), builder.build());
//
//        if (reportError(node)) {
//            return;
//        }
//        setResponsePage(OrganizationDashboardPage.class);
//    }
//}
