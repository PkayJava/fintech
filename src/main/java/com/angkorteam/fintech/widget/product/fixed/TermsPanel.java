package com.angkorteam.fintech.widget.product.fixed;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.RangeValidator;

import com.angkorteam.fintech.dto.enums.DayInYear;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.product.fixed.FixedBrowsePage;
import com.angkorteam.fintech.pages.product.fixed.FixedCreatePage;
import com.angkorteam.fintech.provider.DayInYearProvider;
import com.angkorteam.fintech.provider.InterestCalculatedUsingProvider;
import com.angkorteam.fintech.provider.InterestCompoundingPeriodProvider;
import com.angkorteam.fintech.provider.InterestPostingPeriodProvider;
import com.angkorteam.fintech.widget.Panel;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.wicket.markup.html.form.validation.LamdaFormValidator;

public class TermsPanel extends Panel {

    protected Page itemPage;
    protected PropertyModel<AjaxTabbedPanel<ITab>> tab;
    protected PropertyModel<Boolean> errorTerm;

    protected Form<Void> form;
    protected Button nextButton;
    protected AjaxLink<Void> backLink;
    protected BookmarkablePageLink<Void> closeLink;

    protected UIRow row1;

    protected UIBlock termDefaultDepositAmountBlock;
    protected UIContainer termDefaultDepositAmountIContainer;
    protected TextField<Double> termDefaultDepositAmountField;
    protected PropertyModel<Double> termDefaultDepositAmountValue;

    protected UIBlock termMinimumDepositAmountBlock;
    protected UIContainer termMinimumDepositAmountIContainer;
    protected TextField<Double> termMinimumDepositAmountField;
    protected PropertyModel<Double> termMinimumDepositAmountValue;

    protected UIBlock termMaximumDepositAmountBlock;
    protected UIContainer termMaximumDepositAmountIContainer;
    protected TextField<Double> termMaximumDepositAmountField;
    protected PropertyModel<Double> termMaximumDepositAmountValue;

    protected UIRow row2;

    protected UIBlock termInterestCompoundingPeriodBlock;
    protected UIContainer termInterestCompoundingPeriodIContainer;
    protected InterestCompoundingPeriodProvider termInterestCompoundingPeriodProvider;
    protected Select2SingleChoice<Option> termInterestCompoundingPeriodField;

    protected UIBlock termInterestPostingPeriodBlock;
    protected UIContainer termInterestPostingPeriodIContainer;
    protected InterestPostingPeriodProvider termInterestPostingPeriodProvider;
    protected Select2SingleChoice<Option> termInterestPostingPeriodField;

    protected UIRow row3;

    protected UIBlock termInterestCalculatedUsingBlock;
    protected UIContainer termInterestCalculatedUsingIContainer;
    protected InterestCalculatedUsingProvider termInterestCalculatedUsingProvider;
    protected Select2SingleChoice<Option> termInterestCalculatedUsingField;

    protected UIBlock termDayInYearBlock;
    protected UIContainer termDayInYearIContainer;
    protected DayInYearProvider termDayInYearProvider;
    protected Select2SingleChoice<Option> termDayInYearField;

    public TermsPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void initData() {
        this.tab = new PropertyModel<>(this.itemPage, "tab");
        this.errorTerm = new PropertyModel<>(this.itemPage, "errorTerm");
        this.termInterestCompoundingPeriodProvider = new InterestCompoundingPeriodProvider();
        this.termInterestPostingPeriodProvider = new InterestPostingPeriodProvider();
        this.termInterestCalculatedUsingProvider = new InterestCalculatedUsingProvider();
        this.termDayInYearProvider = new DayInYearProvider(DayInYear.D365, DayInYear.D360);

        this.termDefaultDepositAmountValue = new PropertyModel<>(this.itemPage, "termDefaultDepositAmountValue");
        this.termMinimumDepositAmountValue = new PropertyModel<>(this.itemPage, "termMinimumDepositAmountValue");
        this.termMaximumDepositAmountValue = new PropertyModel<>(this.itemPage, "termMaximumDepositAmountValue");
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

        this.termDefaultDepositAmountBlock = this.row1.newUIBlock("termDefaultDepositAmountBlock", Size.Four_4);
        this.termDefaultDepositAmountIContainer = this.termDefaultDepositAmountBlock.newUIContainer("termDefaultDepositAmountIContainer");
        this.termDefaultDepositAmountField = new TextField<>("termDefaultDepositAmountField", this.termDefaultDepositAmountValue);
        this.termDefaultDepositAmountIContainer.add(this.termDefaultDepositAmountField);
        this.termDefaultDepositAmountIContainer.newFeedback("termDefaultDepositAmountFeedback", this.termDefaultDepositAmountField);

        this.termMinimumDepositAmountBlock = this.row1.newUIBlock("termMinimumDepositAmountBlock", Size.Four_4);
        this.termMinimumDepositAmountIContainer = this.termMinimumDepositAmountBlock.newUIContainer("termMinimumDepositAmountIContainer");
        this.termMinimumDepositAmountField = new TextField<>("termMinimumDepositAmountField", this.termMinimumDepositAmountValue);
        this.termMinimumDepositAmountIContainer.add(this.termMinimumDepositAmountField);
        this.termMinimumDepositAmountIContainer.newFeedback("termMinimumDepositAmountFeedback", this.termMinimumDepositAmountField);

        this.termMaximumDepositAmountBlock = this.row1.newUIBlock("termMaximumDepositAmountBlock", Size.Four_4);
        this.termMaximumDepositAmountIContainer = this.termMaximumDepositAmountBlock.newUIContainer("termMaximumDepositAmountIContainer");
        this.termMaximumDepositAmountField = new TextField<>("termMaximumDepositAmountField", this.termMaximumDepositAmountValue);
        this.termMaximumDepositAmountIContainer.add(this.termMaximumDepositAmountField);
        this.termMaximumDepositAmountIContainer.newFeedback("termMaximumDepositAmountFeedback", this.termMaximumDepositAmountField);

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.termInterestCompoundingPeriodBlock = this.row2.newUIBlock("termInterestCompoundingPeriodBlock", Size.Six_6);
        this.termInterestCompoundingPeriodIContainer = this.termInterestCompoundingPeriodBlock.newUIContainer("termInterestCompoundingPeriodIContainer");
        this.termInterestCompoundingPeriodField = new Select2SingleChoice<>("termInterestCompoundingPeriodField", new PropertyModel<>(this.itemPage, "termInterestCompoundingPeriodValue"), this.termInterestCompoundingPeriodProvider);
        this.termInterestCompoundingPeriodIContainer.add(this.termInterestCompoundingPeriodField);
        this.termInterestCompoundingPeriodIContainer.newFeedback("termInterestCompoundingPeriodFeedback", this.termInterestCompoundingPeriodField);

        this.termInterestPostingPeriodBlock = this.row2.newUIBlock("termInterestPostingPeriodBlock", Size.Six_6);
        this.termInterestPostingPeriodIContainer = this.termInterestPostingPeriodBlock.newUIContainer("termInterestPostingPeriodIContainer");
        this.termInterestPostingPeriodField = new Select2SingleChoice<>("termInterestPostingPeriodField", new PropertyModel<>(this.itemPage, "termInterestPostingPeriodValue"), this.termInterestPostingPeriodProvider);
        this.termInterestPostingPeriodIContainer.add(this.termInterestPostingPeriodField);
        this.termInterestPostingPeriodIContainer.newFeedback("termInterestPostingPeriodFeedback", this.termInterestPostingPeriodField);

        this.row3 = UIRow.newUIRow("row3", this.form);

        this.termInterestCalculatedUsingBlock = this.row3.newUIBlock("termInterestCalculatedUsingBlock", Size.Six_6);
        this.termInterestCalculatedUsingIContainer = this.termInterestCalculatedUsingBlock.newUIContainer("termInterestCalculatedUsingIContainer");
        this.termInterestCalculatedUsingField = new Select2SingleChoice<>("termInterestCalculatedUsingField", new PropertyModel<>(this.itemPage, "termInterestCalculatedUsingValue"), this.termInterestCalculatedUsingProvider);
        this.termInterestCalculatedUsingIContainer.add(this.termInterestCalculatedUsingField);
        this.termInterestCalculatedUsingIContainer.newFeedback("termInterestCalculatedUsingFeedback", this.termInterestCalculatedUsingField);

        this.termDayInYearBlock = this.row3.newUIBlock("termDayInYearBlock", Size.Six_6);
        this.termDayInYearIContainer = this.termDayInYearBlock.newUIContainer("termDayInYearIContainer");
        this.termDayInYearField = new Select2SingleChoice<>("termDayInYearField", new PropertyModel<>(this.itemPage, "termDayInYearValue"), this.termDayInYearProvider);
        this.termDayInYearIContainer.add(this.termDayInYearField);
        this.termDayInYearIContainer.newFeedback("termDayInYearFeedback", this.termDayInYearField);

    }

    @Override
    protected void configureMetaData() {
        this.termDayInYearField.setLabel(Model.of("Days In Year"));
        this.termDayInYearField.add(new OnChangeAjaxBehavior());
        this.termDayInYearField.setRequired(true);

        this.termInterestCalculatedUsingField.setLabel(Model.of("Interest calculated using"));
        this.termInterestCalculatedUsingField.add(new OnChangeAjaxBehavior());
        this.termInterestCalculatedUsingField.setRequired(true);

        this.termInterestPostingPeriodField.setLabel(Model.of("Interest posting period"));
        this.termInterestPostingPeriodField.add(new OnChangeAjaxBehavior());
        this.termInterestPostingPeriodField.setRequired(true);

        this.termInterestCompoundingPeriodField.setLabel(Model.of("Interest Compounding Period"));
        this.termInterestCompoundingPeriodField.add(new OnChangeAjaxBehavior());
        this.termInterestCompoundingPeriodField.setRequired(true);

        this.termMaximumDepositAmountField.setLabel(Model.of("Maximum Deposit Amount"));
        this.termMaximumDepositAmountField.add(new OnChangeAjaxBehavior());
        this.termMaximumDepositAmountField.setRequired(true);
        this.termMaximumDepositAmountField.add(RangeValidator.minimum(0d));

        this.termMinimumDepositAmountField.setLabel(Model.of("Minimum Deposit Amount"));
        this.termMinimumDepositAmountField.add(new OnChangeAjaxBehavior());
        this.termMinimumDepositAmountField.setRequired(true);
        this.termMinimumDepositAmountField.add(RangeValidator.minimum(0d));

        this.termDefaultDepositAmountField.setLabel(Model.of("Default Deposit Amount"));
        this.termDefaultDepositAmountField.setRequired(true);
        this.termDefaultDepositAmountField.add(new OnChangeAjaxBehavior());
        this.termDefaultDepositAmountField.add(RangeValidator.minimum(0d));

        this.form.add(new LamdaFormValidator(this::depositAmountValidation, this.termMinimumDepositAmountField, this.termMaximumDepositAmountField, this.termDefaultDepositAmountField));

    }

    protected void depositAmountValidation(Form<?> form) {
        if (this.termMinimumDepositAmountValue.getObject() != null && this.termMaximumDepositAmountValue.getObject() != null && this.termDefaultDepositAmountValue.getObject() != null) {
            if (this.termMinimumDepositAmountValue.getObject() >= this.termDefaultDepositAmountValue.getObject()) {
                this.termMinimumDepositAmountField.error(new ValidationError("Invalid"));
            }
            if (this.termMaximumDepositAmountValue.getObject() <= this.termDefaultDepositAmountValue.getObject()) {
                this.termMaximumDepositAmountField.error(new ValidationError("Invalid"));
            }
        }
    }

    protected void nextButtonSubmit(Button button) {
        this.errorTerm.setObject(false);
        this.tab.getObject().setSelectedTab(FixedCreatePage.TAB_SETTING);
    }

    protected void nextButtonError(Button button) {
        this.errorTerm.setObject(true);
    }

    protected boolean backLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.tab.getObject().setSelectedTab(FixedCreatePage.TAB_CURRENCY);
        if (target != null) {
            target.add(this.tab.getObject());
        }
        return false;
    }

}
