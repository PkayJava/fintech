package com.angkorteam.fintech.pages.staff;

import java.util.Date;
import java.util.List;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import com.angkorteam.fintech.widget.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.MOffice;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.StaffBuilder;
import com.angkorteam.fintech.helper.StaffHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.pages.OrganizationDashboardPage;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;

/**
 * Created by socheatkhauv on 6/26/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class StaffCreatePage extends Page {

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock firstNameBlock;
    protected WebMarkupContainer firstNameIContainer;
    protected String firstNameValue;
    protected TextField<String> firstNameField;
    protected TextFeedbackPanel firstNameFeedback;

    protected WebMarkupBlock lastNameBlock;
    protected WebMarkupContainer lastNameIContainer;
    protected String lastNameValue;
    protected TextField<String> lastNameField;
    protected TextFeedbackPanel lastNameFeedback;

    protected WebMarkupBlock mobileNoBlock;
    protected WebMarkupContainer mobileNoIContainer;
    protected String mobileNoValue;
    protected TextField<String> mobileNoField;
    protected TextFeedbackPanel mobileNoFeedback;

    protected WebMarkupBlock officeBlock;
    protected WebMarkupContainer officeIContainer;
    protected SingleChoiceProvider officeProvider;
    protected Option officeValue;
    protected Select2SingleChoice<Option> officeField;
    protected TextFeedbackPanel officeFeedback;

    protected WebMarkupBlock joinedDateBlock;
    protected WebMarkupContainer joinedDateIContainer;
    protected Date joinedDateValue;
    protected DateTextField joinedDateField;
    protected TextFeedbackPanel joinedDateFeedback;

    protected WebMarkupBlock loanOfficerBlock;
    protected WebMarkupContainer loanOfficerIContainer;
    protected Boolean loanOfficerValue;
    protected CheckBox loanOfficerField;
    protected TextFeedbackPanel loanOfficerFeedback;

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
            breadcrumb.setLabel("Employee");
            breadcrumb.setPage(StaffBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Employee Create");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", StaffBrowsePage.class);
        this.form.add(this.closeLink);

        initFirstNameBlock();

        initLastNameBlock();

        initJoinedDateBlock();

        initOfficeBlock();

        initMobileNoBlock();

        initLoanOfficerBlock();
    }

    protected void initLoanOfficerBlock() {
        this.loanOfficerBlock = new WebMarkupBlock("loanOfficerBlock", Size.Twelve_12);
        this.form.add(this.loanOfficerBlock);
        this.loanOfficerIContainer = new WebMarkupContainer("loanOfficerIContainer");
        this.loanOfficerBlock.add(this.loanOfficerIContainer);
        this.loanOfficerField = new CheckBox("loanOfficerField", new PropertyModel<>(this, "loanOfficerValue"));
        this.loanOfficerField.setRequired(true);
        this.loanOfficerIContainer.add(this.loanOfficerField);
        this.loanOfficerFeedback = new TextFeedbackPanel("loanOfficerFeedback", this.loanOfficerField);
        this.loanOfficerIContainer.add(this.loanOfficerFeedback);
    }

    protected void initMobileNoBlock() {
        this.mobileNoBlock = new WebMarkupBlock("mobileNoBlock", Size.Twelve_12);
        this.form.add(this.mobileNoBlock);
        this.mobileNoIContainer = new WebMarkupContainer("mobileNoIContainer");
        this.mobileNoBlock.add(this.mobileNoIContainer);
        this.mobileNoField = new TextField<>("mobileNoField", new PropertyModel<>(this, "mobileNoValue"));
        this.mobileNoField.setRequired(true);
        this.mobileNoIContainer.add(this.mobileNoField);
        this.mobileNoFeedback = new TextFeedbackPanel("mobileNoFeedback", this.mobileNoField);
        this.mobileNoIContainer.add(this.mobileNoFeedback);
    }

    protected void initOfficeBlock() {
        this.officeBlock = new WebMarkupBlock("officeBlock", Size.Twelve_12);
        this.form.add(this.officeBlock);
        this.officeIContainer = new WebMarkupContainer("officeIContainer");
        this.officeBlock.add(this.officeIContainer);
        this.officeProvider = new SingleChoiceProvider(MOffice.NAME, MOffice.Field.ID, MOffice.Field.NAME);
        this.officeField = new Select2SingleChoice<>("officeField", new PropertyModel<>(this, "officeValue"), this.officeProvider);
        this.officeField.setRequired(true);
        this.officeIContainer.add(this.officeField);
        this.officeFeedback = new TextFeedbackPanel("officeFeedback", this.officeField);
        this.officeIContainer.add(this.officeFeedback);
    }

    protected void initJoinedDateBlock() {
        this.joinedDateBlock = new WebMarkupBlock("joinedDateBlock", Size.Twelve_12);
        this.form.add(this.joinedDateBlock);
        this.joinedDateIContainer = new WebMarkupContainer("joinedDateIContainer");
        this.joinedDateBlock.add(this.joinedDateIContainer);
        this.joinedDateField = new DateTextField("joinedDateField", new PropertyModel<>(this, "joinedDateValue"));
        this.joinedDateField.setRequired(true);
        this.joinedDateIContainer.add(this.joinedDateField);
        this.joinedDateFeedback = new TextFeedbackPanel("joinedDateFeedback", this.joinedDateField);
        this.joinedDateIContainer.add(this.joinedDateFeedback);
    }

    protected void initLastNameBlock() {
        this.lastNameBlock = new WebMarkupBlock("lastNameBlock", Size.Twelve_12);
        this.form.add(this.lastNameBlock);
        this.lastNameIContainer = new WebMarkupContainer("lastNameIContainer");
        this.lastNameBlock.add(this.lastNameIContainer);
        this.lastNameField = new TextField<>("lastNameField", new PropertyModel<>(this, "lastNameValue"));
        this.lastNameField.setRequired(true);
        this.lastNameIContainer.add(this.lastNameField);
        this.lastNameFeedback = new TextFeedbackPanel("lastNameFeedback", this.lastNameField);
        this.lastNameIContainer.add(this.lastNameFeedback);
    }

    protected void initFirstNameBlock() {
        this.firstNameBlock = new WebMarkupBlock("firstNameBlock", Size.Twelve_12);
        this.form.add(this.firstNameBlock);
        this.firstNameIContainer = new WebMarkupContainer("firstNameIContainer");
        this.firstNameBlock.add(this.firstNameIContainer);
        this.firstNameField = new TextField<>("firstNameField", new PropertyModel<>(this, "firstNameValue"));
        this.firstNameField.setRequired(true);
        this.firstNameIContainer.add(this.firstNameField);
        this.firstNameFeedback = new TextFeedbackPanel("firstNameFeedback", this.firstNameField);
        this.firstNameIContainer.add(this.firstNameFeedback);
    }

    @Override
    protected void configureMetaData() {
    }

    protected void saveButtonSubmit(Button button) {
        StaffBuilder builder = new StaffBuilder();
        builder.withFirstName(this.firstNameValue);
        if (this.officeValue != null) {
            builder.withOfficeId(this.officeValue.getId());
        }
        builder.withLastName(this.lastNameValue);
        builder.withMobileNo(this.mobileNoValue);
        builder.withLoanOfficer(this.loanOfficerValue);
        builder.withJoiningDate(this.joinedDateValue);

        JsonNode node = StaffHelper.create((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(StaffBrowsePage.class);
    }

}
