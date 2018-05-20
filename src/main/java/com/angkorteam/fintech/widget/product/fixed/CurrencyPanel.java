package com.angkorteam.fintech.widget.product.fixed;

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
import com.angkorteam.fintech.pages.product.fixed.FixedBrowsePage;
import com.angkorteam.fintech.pages.product.fixed.FixedCreatePage;
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
    protected Select2SingleChoice<Option> currencyCodeField;
    protected CurrencyProvider currencyCodeProvider;

    protected UIBlock currencyDecimalPlaceBlock;
    protected UIContainer currencyDecimalPlaceIContainer;
    protected TextField<Long> currencyDecimalPlaceField;

    protected UIRow row2;

    protected UIBlock currencyMultipleOfBlock;
    protected UIContainer currencyMultipleOfIContainer;
    protected TextField<Long> currencyMultipleOfField;

    protected UIBlock row2Block1;

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

        this.closeLink = new BookmarkablePageLink<>("closeLink", FixedBrowsePage.class);
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

        this.currencyMultipleOfBlock = this.row2.newUIBlock("currencyMultipleOfBlock", Size.Six_6);
        this.currencyMultipleOfIContainer = this.currencyMultipleOfBlock.newUIContainer("currencyMultipleOfIContainer");
        this.currencyMultipleOfField = new TextField<>("currencyMultipleOfField", new PropertyModel<>(this.itemPage, "currencyMultipleOfValue"));
        this.currencyMultipleOfIContainer.add(this.currencyMultipleOfField);
        this.currencyMultipleOfIContainer.newFeedback("currencyMultipleOfFeedback", this.currencyMultipleOfField);

        this.row2Block1 = this.row2.newUIBlock("row2Block1", Size.Six_6);

    }

    @Override
    protected void configureMetaData() {
        this.currencyMultipleOfField.setLabel(Model.of("Multiples of"));
        this.currencyMultipleOfField.add(new OnChangeAjaxBehavior());
        this.currencyMultipleOfField.setRequired(true);
        this.currencyMultipleOfField.add(RangeValidator.minimum(1l));

        this.currencyDecimalPlaceField.setLabel(Model.of("Decimal places"));
        this.currencyDecimalPlaceField.add(new OnChangeAjaxBehavior());
        this.currencyDecimalPlaceField.setRequired(true);
        this.currencyDecimalPlaceField.add(RangeValidator.minimum(1l));

        this.currencyCodeField.setLabel(Model.of("Currency"));
        this.currencyCodeField.add(new OnChangeAjaxBehavior());
        this.currencyCodeField.setRequired(true);

    }

    protected void nextButtonSubmit(Button button) {
        this.errorCurrency.setObject(false);
        this.tab.getObject().setSelectedTab(FixedCreatePage.TAB_TERM);
    }

    protected void nextButtonError(Button button) {
        this.errorCurrency.setObject(true);
    }

    protected boolean backLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.tab.getObject().setSelectedTab(FixedCreatePage.TAB_DETAIL);
        if (target != null) {
            target.add(this.tab.getObject());
        }
        return false;
    }

}
