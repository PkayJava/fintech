package com.angkorteam.fintech.widget.product.loan;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.StringValidator;

import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.product.loan.LoanBrowsePage;
import com.angkorteam.fintech.pages.product.loan.LoanCreatePage;
import com.angkorteam.fintech.provider.FundProvider;
import com.angkorteam.fintech.widget.Panel;
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

    protected UIRow row1;

    protected UIBlock detailProductNameBlock;
    protected UIContainer detailProductNameIContainer;
    protected TextField<String> detailProductNameField;

    protected UIBlock detailShortNameBlock;
    protected UIContainer detailShortNameIContainer;
    protected TextField<String> detailShortNameField;

    protected UIRow row2;

    protected UIBlock detailDescriptionBlock;
    protected UIContainer detailDescriptionIContainer;
    protected TextField<String> detailDescriptionField;

    protected UIBlock detailFundBlock;
    protected UIContainer detailFundIContainer;
    protected FundProvider detailFundProvider;
    protected Select2SingleChoice<Option> detailFundField;

    protected UIRow row3;

    protected UIBlock detailStartDateBlock;
    protected UIContainer detailStartDateIContainer;
    protected DateTextField detailStartDateField;

    protected UIBlock detailCloseDateBlock;
    protected UIContainer detailCloseDateIContainer;
    protected DateTextField detailCloseDateField;

    protected UIRow row4;

    protected UIBlock detailIncludeInCustomerLoanCounterBlock;
    protected UIContainer detailIncludeInCustomerLoanCounterIContainer;
    protected CheckBox detailIncludeInCustomerLoanCounterField;

    protected UIBlock row4Block1;

    public DetailsPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void initData() {
        this.tab = new PropertyModel<>(this.itemPage, "tab");
        this.errorDetail = new PropertyModel<>(this.itemPage, "errorDetail");

        this.detailFundProvider = new FundProvider();
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

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.detailProductNameBlock = this.row1.newUIBlock("detailProductNameBlock", Size.Six_6);
        this.detailProductNameIContainer = this.detailProductNameBlock.newUIContainer("detailProductNameIContainer");
        this.detailProductNameField = new TextField<>("detailProductNameField", new PropertyModel<>(this.itemPage, "detailProductNameValue"));
        this.detailProductNameIContainer.add(this.detailProductNameField);
        this.detailProductNameIContainer.newFeedback("detailProductNameFeedback", this.detailProductNameField);

        this.detailShortNameBlock = this.row1.newUIBlock("detailShortNameBlock", Size.Six_6);
        this.detailShortNameIContainer = this.detailShortNameBlock.newUIContainer("detailShortNameIContainer");
        this.detailShortNameField = new TextField<>("detailShortNameField", new PropertyModel<>(this.itemPage, "detailShortNameValue"));
        this.detailShortNameIContainer.add(this.detailShortNameField);
        this.detailShortNameIContainer.newFeedback("detailShortNameFeedback", this.detailShortNameField);

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.detailDescriptionBlock = this.row2.newUIBlock("detailDescriptionBlock", Size.Six_6);
        this.detailDescriptionIContainer = this.detailDescriptionBlock.newUIContainer("detailDescriptionIContainer");
        this.detailDescriptionField = new TextField<>("detailDescriptionField", new PropertyModel<>(this.itemPage, "detailDescriptionValue"));
        this.detailDescriptionIContainer.add(this.detailDescriptionField);
        this.detailDescriptionIContainer.newFeedback("detailDescriptionFeedback", this.detailDescriptionField);

        this.detailFundBlock = this.row2.newUIBlock("detailFundBlock", Size.Six_6);
        this.detailFundIContainer = this.detailFundBlock.newUIContainer("detailFundIContainer");
        this.detailFundField = new Select2SingleChoice<>("detailFundField", new PropertyModel<>(this.itemPage, "detailFundValue"), this.detailFundProvider);
        this.detailFundIContainer.add(this.detailFundField);
        this.detailFundIContainer.newFeedback("detailFundFeedback", this.detailFundField);

        this.row3 = UIRow.newUIRow("row3", this.form);

        this.detailStartDateBlock = this.row3.newUIBlock("detailStartDateBlock", Size.Six_6);
        this.detailStartDateIContainer = this.detailStartDateBlock.newUIContainer("detailStartDateIContainer");
        this.detailStartDateField = new DateTextField("detailStartDateField", new PropertyModel<>(this.itemPage, "detailStartDateValue"));
        this.detailStartDateIContainer.add(this.detailStartDateField);
        this.detailStartDateIContainer.newFeedback("detailStartDateFeedback", this.detailStartDateField);

        this.detailCloseDateBlock = this.row3.newUIBlock("detailCloseDateBlock", Size.Six_6);
        this.detailCloseDateIContainer = this.detailCloseDateBlock.newUIContainer("detailCloseDateIContainer");
        this.detailCloseDateField = new DateTextField("detailCloseDateField", new PropertyModel<>(this.itemPage, "detailCloseDateValue"));
        this.detailCloseDateIContainer.add(this.detailCloseDateField);
        this.detailCloseDateIContainer.newFeedback("detailCloseDateFeedback", this.detailCloseDateField);

        this.row4 = UIRow.newUIRow("row4", this.form);

        this.detailIncludeInCustomerLoanCounterBlock = this.row4.newUIBlock("detailIncludeInCustomerLoanCounterBlock", Size.Twelve_12);
        this.detailIncludeInCustomerLoanCounterIContainer = this.detailIncludeInCustomerLoanCounterBlock.newUIContainer("detailIncludeInCustomerLoanCounterIContainer");
        this.detailIncludeInCustomerLoanCounterField = new CheckBox("detailIncludeInCustomerLoanCounterField", new PropertyModel<>(this.itemPage, "detailIncludeInCustomerLoanCounterValue"));
        this.detailIncludeInCustomerLoanCounterIContainer.add(this.detailIncludeInCustomerLoanCounterField);
        this.detailIncludeInCustomerLoanCounterIContainer.newFeedback("detailIncludeInCustomerLoanCounterFeedback", this.detailIncludeInCustomerLoanCounterField);

    }

    @Override
    protected void configureMetaData() {
        this.detailProductNameField.setRequired(true);
        this.detailProductNameField.setLabel(Model.of("Product Name"));

        this.detailShortNameField.setRequired(true);
        this.detailShortNameField.add(StringValidator.exactLength(4));
        this.detailShortNameField.setLabel(Model.of("Short Name"));

        this.detailDescriptionField.setLabel(Model.of("Description"));
        this.detailFundField.setLabel(Model.of("Fund"));
        this.detailStartDateField.setLabel(Model.of("Start Date"));
        this.detailCloseDateField.setLabel(Model.of("Close Date"));
    }

    protected void nextButtonSubmit(Button button) {
        this.errorDetail.setObject(false);
        this.tab.getObject().setSelectedTab(LoanCreatePage.TAB_CURRENCY);
    }

    protected void nextButtonError(Button button) {
        this.errorDetail.setObject(true);
    }

}