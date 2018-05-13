package com.angkorteam.fintech.widget.product.loan;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.RangeValidator;

import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.product.loan.LoanBrowsePage;
import com.angkorteam.fintech.pages.product.loan.LoanCreatePage;
import com.angkorteam.fintech.provider.CurrencyProvider;
import com.angkorteam.fintech.widget.Panel;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class CurrencyPanel extends Panel {

    protected Page itemPage;
    protected PropertyModel<AjaxTabbedPanel<ITab>> tab;
    protected PropertyModel<Boolean> errorCurrency;

    protected Form<Void> form;
    protected Button nextButton;
    protected AjaxLink<Void> backLink;
    protected BookmarkablePageLink<Void> closeLink;

    protected UIRow row1;

    protected UIBlock currencyCodeBlock;
    protected UIContainer currencyCodeIContainer;
    protected CurrencyProvider currencyCodeProvider;
    protected Select2SingleChoice<Option> currencyCodeField;

    protected UIBlock currencyDecimalPlaceBlock;
    protected UIContainer currencyDecimalPlaceIContainer;
    protected TextField<Long> currencyDecimalPlaceField;

    protected UIRow row2;

    protected UIBlock currencyInMultipleOfBlock;
    protected UIContainer currencyInMultipleOfIContainer;
    protected TextField<Long> currencyInMultipleOfField;

    protected UIBlock currencyInstallmentInMultipleOfBlock;
    protected UIContainer currencyInstallmentInMultipleOfIContainer;
    protected TextField<Long> currencyInstallmentInMultipleOfField;

    public CurrencyPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void initData() {
        this.errorCurrency = new PropertyModel<>(this.itemPage, "errorCurrency");
        this.tab = new PropertyModel<>(this.itemPage, "tab");

        this.currencyCodeProvider = new CurrencyProvider();
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.nextButton = new Button("nextButton");
        this.nextButton.setOnSubmit(this::nextButtonSubmit);
        this.nextButton.setOnError(this::nextButtonError);
        this.form.add(this.nextButton);

        this.backLink = new AjaxLink<>("backLink");
        this.backLink.setOnClick(this::backLinkClick);
        this.form.add(this.backLink);

        this.closeLink = new BookmarkablePageLink<>("closeLink", LoanBrowsePage.class);
        this.form.add(this.closeLink);

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.currencyCodeBlock = this.row1.newUIBlock("currencyCodeBlock", Size.Six_6);
        this.currencyCodeIContainer = this.currencyCodeBlock.newUIContainer("currencyCodeIContainer");
        this.currencyCodeField = new Select2SingleChoice<>("currencyCodeField", new PropertyModel<>(this.itemPage, "currencyCodeValue"), this.currencyCodeProvider);
        this.currencyCodeIContainer.add(this.currencyCodeField);
        this.currencyCodeIContainer.newFeedback("currencyCodeFeedback", this.currencyCodeField);

        this.currencyDecimalPlaceBlock = this.row1.newUIBlock("currencyDecimalPlaceBlock", Size.Six_6);
        this.currencyDecimalPlaceIContainer = this.currencyDecimalPlaceBlock.newUIContainer("currencyDecimalPlaceIContainer");
        this.currencyDecimalPlaceField = new TextField<>("currencyDecimalPlaceField", new PropertyModel<>(this.itemPage, "currencyDecimalPlaceValue"));
        this.currencyDecimalPlaceIContainer.add(this.currencyDecimalPlaceField);
        this.currencyDecimalPlaceIContainer.newFeedback("currencyDecimalPlaceFeedback", this.currencyDecimalPlaceField);

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.currencyInMultipleOfBlock = this.row2.newUIBlock("currencyInMultipleOfBlock", Size.Six_6);
        this.currencyInMultipleOfIContainer = this.currencyInMultipleOfBlock.newUIContainer("currencyInMultipleOfIContainer");
        this.currencyInMultipleOfField = new TextField<>("currencyInMultipleOfField", new PropertyModel<>(this.itemPage, "currencyInMultipleOfValue"));
        this.currencyInMultipleOfIContainer.add(this.currencyInMultipleOfField);
        this.currencyInMultipleOfIContainer.newFeedback("currencyInMultipleOfFeedback", this.currencyInMultipleOfField);

        this.currencyInstallmentInMultipleOfBlock = this.row2.newUIBlock("currencyInstallmentInMultipleOfBlock", Size.Six_6);
        this.currencyInstallmentInMultipleOfIContainer = this.currencyInstallmentInMultipleOfBlock.newUIContainer("currencyInstallmentInMultipleOfIContainer");
        this.currencyInstallmentInMultipleOfField = new TextField<>("currencyInstallmentInMultipleOfField", new PropertyModel<>(this.itemPage, "currencyInstallmentInMultipleOfValue"));
        this.currencyInstallmentInMultipleOfIContainer.add(this.currencyInstallmentInMultipleOfField);
        this.currencyInstallmentInMultipleOfIContainer.newFeedback("currencyInstallmentInMultipleOfFeedback", this.currencyInstallmentInMultipleOfField);

    }

    @Override
    protected void configureMetaData() {
        this.currencyCodeField.setRequired(true);
        this.currencyCodeField.add(new OnChangeAjaxBehavior());
        this.currencyCodeField.setLabel(Model.of("Currency"));

        this.currencyDecimalPlaceField.setRequired(true);
        this.currencyDecimalPlaceField.setLabel(Model.of("Decimal Places"));
        this.currencyDecimalPlaceField.add(new OnChangeAjaxBehavior());
        this.currencyDecimalPlaceField.add(RangeValidator.minimum(0l));

        this.currencyInMultipleOfField.setRequired(true);
        this.currencyInMultipleOfField.setLabel(Model.of("Currency in multiple of"));
        this.currencyInMultipleOfField.add(new OnChangeAjaxBehavior());
        this.currencyInMultipleOfField.add(RangeValidator.minimum(1l));

        this.currencyInstallmentInMultipleOfField.setRequired(true);
        this.currencyInstallmentInMultipleOfField.setLabel(Model.of("Installment in multiple of"));
        this.currencyInstallmentInMultipleOfField.add(new OnChangeAjaxBehavior());
        this.currencyInstallmentInMultipleOfField.add(RangeValidator.minimum(1l));

    }

    protected void nextButtonSubmit(Button button) {
        this.tab.getObject().setSelectedTab(LoanCreatePage.TAB_TERM);
        this.errorCurrency.setObject(false);
    }

    protected void nextButtonError(Button button) {
        this.errorCurrency.setObject(true);
    }

    protected boolean backLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.tab.getObject().setSelectedTab(LoanCreatePage.TAB_DETAIL);
        if (target != null) {
            target.add(this.tab.getObject());
        }
        return false;
    }

}
