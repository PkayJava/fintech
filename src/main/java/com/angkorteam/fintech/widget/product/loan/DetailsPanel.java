package com.angkorteam.fintech.widget.product.loan;

import org.apache.wicket.Page;
import com.angkorteam.fintech.widget.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.StringValidator;

import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.pages.product.loan.LoanBrowsePage;
import com.angkorteam.fintech.pages.product.loan.LoanCreatePage;
import com.angkorteam.fintech.provider.FundProvider;
import com.angkorteam.fintech.widget.Panel;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class DetailsPanel extends Panel {

    protected Page itemPage;
    protected PropertyModel<AjaxTabbedPanel<ITab>> tab;
    protected PropertyModel<Boolean> errorDetail;

    protected Form<Void> form;
    protected Button nextButton;
    protected BookmarkablePageLink<Void> closeLink;

    // Detail

    protected WebMarkupBlock detailProductNameBlock;
    protected WebMarkupContainer detailProductNameIContainer;
    protected TextField<String> detailProductNameField;
    protected TextFeedbackPanel detailProductNameFeedback;

    protected WebMarkupBlock detailShortNameBlock;
    protected WebMarkupContainer detailShortNameIContainer;
    protected TextField<String> detailShortNameField;
    protected TextFeedbackPanel detailShortNameFeedback;

    protected WebMarkupBlock detailDescriptionBlock;
    protected WebMarkupContainer detailDescriptionIContainer;
    protected TextField<String> detailDescriptionField;
    protected TextFeedbackPanel detailDescriptionFeedback;

    protected WebMarkupBlock detailFundBlock;
    protected WebMarkupContainer detailFundIContainer;
    protected FundProvider detailFundProvider;
    protected Select2SingleChoice<Option> detailFundField;
    protected TextFeedbackPanel detailFundFeedback;

    protected WebMarkupBlock detailStartDateBlock;
    protected WebMarkupContainer detailStartDateIContainer;
    protected DateTextField detailStartDateField;
    protected TextFeedbackPanel detailStartDateFeedback;

    protected WebMarkupBlock detailCloseDateBlock;
    protected WebMarkupContainer detailCloseDateIContainer;
    protected DateTextField detailCloseDateField;
    protected TextFeedbackPanel detailCloseDateFeedback;

    protected WebMarkupBlock detailIncludeInCustomerLoanCounterBlock;
    protected WebMarkupContainer detailIncludeInCustomerLoanCounterIContainer;
    protected CheckBox detailIncludeInCustomerLoanCounterField;
    protected TextFeedbackPanel detailIncludeInCustomerLoanCounterFeedback;

    public DetailsPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void initData() {
        this.tab = new PropertyModel<>(this.itemPage, "tab");
        this.errorDetail = new PropertyModel<>(this.itemPage, "errorDetail");
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.nextButton = new Button("nextButton");
        this.nextButton.setOnSubmit(this::nextButtonSubmit);
        this.nextButton.setOnError(this::nextButtonError);
        this.form.add(this.nextButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", LoanBrowsePage.class);
        this.form.add(this.closeLink);

        this.detailProductNameBlock = new WebMarkupBlock("detailProductNameBlock", Size.Six_6);
        this.form.add(this.detailProductNameBlock);
        this.detailProductNameIContainer = new WebMarkupContainer("detailProductNameIContainer");
        this.detailProductNameBlock.add(this.detailProductNameIContainer);
        this.detailProductNameField = new TextField<>("detailProductNameField", new PropertyModel<>(this.itemPage, "detailProductNameValue"));
        this.detailProductNameField.setLabel(Model.of("Product Name"));
        this.detailProductNameIContainer.add(this.detailProductNameField);
        this.detailProductNameFeedback = new TextFeedbackPanel("detailProductNameFeedback", this.detailProductNameField);
        this.detailProductNameIContainer.add(this.detailProductNameFeedback);

        this.detailShortNameBlock = new WebMarkupBlock("detailShortNameBlock", Size.Six_6);
        this.form.add(this.detailShortNameBlock);
        this.detailShortNameIContainer = new WebMarkupContainer("detailShortNameIContainer");
        this.detailShortNameBlock.add(this.detailShortNameIContainer);
        this.detailShortNameField = new TextField<>("detailShortNameField", new PropertyModel<>(this.itemPage, "detailShortNameValue"));
        this.detailShortNameField.setLabel(Model.of("Short Name"));
        this.detailShortNameIContainer.add(this.detailShortNameField);
        this.detailShortNameFeedback = new TextFeedbackPanel("detailShortNameFeedback", this.detailShortNameField);
        this.detailShortNameIContainer.add(this.detailShortNameFeedback);

        this.detailDescriptionBlock = new WebMarkupBlock("detailDescriptionBlock", Size.Six_6);
        this.form.add(this.detailDescriptionBlock);
        this.detailDescriptionIContainer = new WebMarkupContainer("detailDescriptionIContainer");
        this.detailDescriptionBlock.add(this.detailDescriptionIContainer);
        this.detailDescriptionField = new TextField<>("detailDescriptionField", new PropertyModel<>(this.itemPage, "detailDescriptionValue"));
        this.detailDescriptionField.setLabel(Model.of("Description"));
        this.detailDescriptionIContainer.add(this.detailDescriptionField);
        this.detailDescriptionFeedback = new TextFeedbackPanel("detailDescriptionFeedback", this.detailDescriptionField);
        this.detailDescriptionIContainer.add(this.detailDescriptionFeedback);

        this.detailFundProvider = new FundProvider();
        this.detailFundBlock = new WebMarkupBlock("detailFundBlock", Size.Six_6);
        this.form.add(this.detailFundBlock);
        this.detailFundIContainer = new WebMarkupContainer("detailFundIContainer");
        this.detailFundBlock.add(this.detailFundIContainer);
        this.detailFundField = new Select2SingleChoice<>("detailFundField", new PropertyModel<>(this.itemPage, "detailFundValue"), this.detailFundProvider);
        this.detailFundField.setLabel(Model.of("Fund"));
        this.detailFundIContainer.add(this.detailFundField);
        this.detailFundFeedback = new TextFeedbackPanel("detailFundFeedback", this.detailFundField);
        this.detailFundIContainer.add(this.detailFundFeedback);

        this.detailStartDateBlock = new WebMarkupBlock("detailStartDateBlock", Size.Six_6);
        this.form.add(this.detailStartDateBlock);
        this.detailStartDateIContainer = new WebMarkupContainer("detailStartDateIContainer");
        this.detailStartDateBlock.add(this.detailStartDateIContainer);
        this.detailStartDateField = new DateTextField("detailStartDateField", new PropertyModel<>(this.itemPage, "detailStartDateValue"));
        this.detailStartDateField.setLabel(Model.of("Start Date"));
        this.detailStartDateIContainer.add(this.detailStartDateField);
        this.detailStartDateFeedback = new TextFeedbackPanel("detailStartDateFeedback", this.detailStartDateField);
        this.detailStartDateIContainer.add(this.detailStartDateFeedback);

        this.detailCloseDateBlock = new WebMarkupBlock("detailCloseDateBlock", Size.Six_6);
        this.form.add(this.detailCloseDateBlock);
        this.detailCloseDateIContainer = new WebMarkupContainer("detailCloseDateIContainer");
        this.detailCloseDateBlock.add(this.detailCloseDateIContainer);
        this.detailCloseDateField = new DateTextField("detailCloseDateField", new PropertyModel<>(this.itemPage, "detailCloseDateValue"));
        this.detailCloseDateField.setLabel(Model.of("Close Date"));
        this.detailCloseDateIContainer.add(this.detailCloseDateField);
        this.detailCloseDateFeedback = new TextFeedbackPanel("detailCloseDateFeedback", this.detailCloseDateField);
        this.detailCloseDateIContainer.add(this.detailCloseDateFeedback);

        this.detailIncludeInCustomerLoanCounterBlock = new WebMarkupBlock("detailIncludeInCustomerLoanCounterBlock", Size.Twelve_12);
        this.form.add(this.detailIncludeInCustomerLoanCounterBlock);
        this.detailIncludeInCustomerLoanCounterIContainer = new WebMarkupContainer("detailIncludeInCustomerLoanCounterIContainer");
        this.detailIncludeInCustomerLoanCounterBlock.add(this.detailIncludeInCustomerLoanCounterIContainer);
        this.detailIncludeInCustomerLoanCounterField = new CheckBox("detailIncludeInCustomerLoanCounterField", new PropertyModel<>(this.itemPage, "detailIncludeInCustomerLoanCounterValue"));
        this.detailIncludeInCustomerLoanCounterIContainer.add(this.detailIncludeInCustomerLoanCounterField);
        this.detailIncludeInCustomerLoanCounterFeedback = new TextFeedbackPanel("detailIncludeInCustomerLoanCounterFeedback", this.detailIncludeInCustomerLoanCounterField);
        this.detailIncludeInCustomerLoanCounterIContainer.add(this.detailIncludeInCustomerLoanCounterFeedback);

    }

    @Override
    protected void configureMetaData() {
        this.detailProductNameField.setRequired(true);
        this.detailShortNameField.setRequired(true);
        this.detailShortNameField.add(StringValidator.exactLength(4));
    }

    protected void nextButtonSubmit(Button button) {
        this.tab.getObject().setSelectedTab(LoanCreatePage.TAB_CURRENCY);
        this.errorDetail.setObject(false);
    }

    protected void nextButtonError(Button button) {
        this.errorDetail.setObject(true);
    }

}