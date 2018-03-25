package com.angkorteam.fintech.widget.product.recurring;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.pages.product.saving.SavingBrowsePage;
import com.angkorteam.fintech.pages.product.saving.SavingCreatePage;
import com.angkorteam.fintech.provider.CurrencyProvider;
import com.angkorteam.fintech.widget.Panel;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
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

    protected WebMarkupBlock currencyCodeBlock;
    protected WebMarkupContainer currencyCodeIContainer;
    protected Select2SingleChoice<Option> currencyCodeField;
    protected CurrencyProvider currencyCodeProvider;
    protected TextFeedbackPanel currencyCodeFeedback;

    protected WebMarkupBlock currencyDecimalPlaceBlock;
    protected WebMarkupContainer currencyDecimalPlaceIContainer;
    protected TextField<Long> currencyDecimalPlaceField;
    protected TextFeedbackPanel currencyDecimalPlaceFeedback;

    protected WebMarkupBlock currencyMultipleOfBlock;
    protected WebMarkupContainer currencyMultipleOfIContainer;
    protected TextField<Long> currencyMultipleOfField;
    protected TextFeedbackPanel currencyMultipleOfFeedback;

    public CurrencyPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void initData() {
        this.errorCurrency = new PropertyModel<>(this.itemPage, "errorCurrency");
        this.tab = new PropertyModel<>(this.itemPage, "tab");
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

        this.closeLink = new BookmarkablePageLink<>("closeLink", SavingBrowsePage.class);
        this.form.add(this.closeLink);

        this.currencyMultipleOfBlock = new WebMarkupBlock("currencyMultipleOfBlock", Size.Six_6);
        this.form.add(this.currencyMultipleOfBlock);
        this.currencyMultipleOfIContainer = new WebMarkupContainer("currencyMultipleOfIContainer");
        this.currencyMultipleOfBlock.add(this.currencyMultipleOfIContainer);
        this.currencyMultipleOfField = new TextField<>("currencyMultipleOfField", new PropertyModel<>(this.itemPage, "currencyMultipleOfValue"));
        this.currencyMultipleOfField.setLabel(Model.of("Multiples of"));
        this.currencyMultipleOfField.add(new OnChangeAjaxBehavior());
        this.currencyMultipleOfIContainer.add(this.currencyMultipleOfField);
        this.currencyMultipleOfFeedback = new TextFeedbackPanel("currencyMultipleOfFeedback", this.currencyMultipleOfField);
        this.currencyMultipleOfIContainer.add(this.currencyMultipleOfFeedback);

        this.currencyDecimalPlaceBlock = new WebMarkupBlock("currencyDecimalPlaceBlock", Size.Six_6);
        this.form.add(this.currencyDecimalPlaceBlock);
        this.currencyDecimalPlaceIContainer = new WebMarkupContainer("currencyDecimalPlaceIContainer");
        this.currencyDecimalPlaceBlock.add(this.currencyDecimalPlaceIContainer);
        this.currencyDecimalPlaceField = new TextField<>("currencyDecimalPlaceField", new PropertyModel<>(this.itemPage, "currencyDecimalPlaceValue"));
        this.currencyDecimalPlaceField.setLabel(Model.of("Decimal places"));
        this.currencyDecimalPlaceField.add(new OnChangeAjaxBehavior());
        this.currencyDecimalPlaceIContainer.add(this.currencyDecimalPlaceField);
        this.currencyDecimalPlaceFeedback = new TextFeedbackPanel("currencyDecimalPlaceFeedback", this.currencyDecimalPlaceField);
        this.currencyDecimalPlaceIContainer.add(this.currencyDecimalPlaceFeedback);

        this.currencyCodeBlock = new WebMarkupBlock("currencyCodeBlock", Size.Six_6);
        this.form.add(this.currencyCodeBlock);
        this.currencyCodeIContainer = new WebMarkupContainer("currencyCodeIContainer");
        this.currencyCodeBlock.add(this.currencyCodeIContainer);
        this.currencyCodeProvider = new CurrencyProvider();
        this.currencyCodeField = new Select2SingleChoice<>("currencyCodeField", new PropertyModel<>(this.itemPage, "currencyCodeValue"), this.currencyCodeProvider);
        this.currencyCodeField.setLabel(Model.of("Currency"));
        this.currencyCodeField.add(new OnChangeAjaxBehavior());
        this.currencyCodeIContainer.add(this.currencyCodeField);
        this.currencyCodeFeedback = new TextFeedbackPanel("currencyCodeFeedback", this.currencyCodeField);
        this.currencyCodeIContainer.add(this.currencyCodeFeedback);

    }

    @Override
    protected void configureMetaData() {
        this.currencyCodeField.setRequired(true);
        this.currencyDecimalPlaceField.setRequired(true);
        this.currencyMultipleOfField.setRequired(true);
    }

    protected void nextButtonSubmit(Button button) {
        this.tab.getObject().setSelectedTab(SavingCreatePage.TAB_TERM);
        this.errorCurrency.setObject(false);
    }

    protected void nextButtonError(Button button) {
        this.errorCurrency.setObject(true);
    }

    protected boolean backLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.tab.getObject().setSelectedTab(SavingCreatePage.TAB_DETAIL);
        if (target != null) {
            target.add(this.tab.getObject());
        }
        return false;
    }

}
