package com.angkorteam.fintech.widget.product.savings;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.dto.enums.DayInYear;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.product.saving.SavingBrowsePage;
import com.angkorteam.fintech.pages.product.saving.SavingCreatePage;
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

public class TermsPanel extends Panel {

    protected Page itemPage;
    protected PropertyModel<AjaxTabbedPanel<ITab>> tab;
    protected PropertyModel<Boolean> errorTerm;

    protected Form<Void> form;
    protected Button nextButton;
    protected AjaxLink<Void> backLink;
    protected BookmarkablePageLink<Void> closeLink;

    protected UIRow row1;

    protected UIBlock termNominalAnnualInterestBlock;
    protected UIContainer termNominalAnnualInterestIContainer;
    protected TextField<Double> termNominalAnnualInterestField;

    protected UIBlock row1Block1;

    protected UIRow row2;

    protected UIBlock termInterestCompoundingPeriodBlock;
    protected UIContainer termInterestCompoundingPeriodIContainer;
    protected InterestCompoundingPeriodProvider termInterestCompoundingPeriodProvider;
    protected Select2SingleChoice<Option> termInterestCompoundingPeriodField;

    protected UIBlock termInterestCalculatedUsingBlock;
    protected UIContainer termInterestCalculatedUsingIContainer;
    protected InterestCalculatedUsingProvider termInterestCalculatedUsingProvider;
    protected Select2SingleChoice<Option> termInterestCalculatedUsingField;

    protected UIRow row3;

    protected UIBlock termInterestPostingPeriodBlock;
    protected UIContainer termInterestPostingPeriodIContainer;
    protected InterestPostingPeriodProvider termInterestPostingPeriodProvider;
    protected Select2SingleChoice<Option> termInterestPostingPeriodField;

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
        this.termInterestCalculatedUsingProvider = new InterestCalculatedUsingProvider();
        this.termDayInYearProvider = new DayInYearProvider(DayInYear.D365, DayInYear.D360);
        this.termInterestPostingPeriodProvider = new InterestPostingPeriodProvider();
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

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.termNominalAnnualInterestBlock = this.row1.newUIBlock("termNominalAnnualInterestBlock", Size.Six_6);
        this.termNominalAnnualInterestIContainer = this.termNominalAnnualInterestBlock.newUIContainer("termNominalAnnualInterestIContainer");
        this.termNominalAnnualInterestField = new TextField<>("termNominalAnnualInterestField", new PropertyModel<>(this.itemPage, "termNominalAnnualInterestValue"));
        this.termNominalAnnualInterestIContainer.add(this.termNominalAnnualInterestField);
        this.termNominalAnnualInterestIContainer.newFeedback("termNominalAnnualInterestFeedback", this.termNominalAnnualInterestField);

        this.row1Block1 = this.row1.newUIBlock("row1Block1", Size.Six_6);

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
        this.termInterestPostingPeriodField.setLabel(Model.of("Interest calculated using"));
        this.termInterestPostingPeriodField.add(new OnChangeAjaxBehavior());

        this.termDayInYearField.setLabel(Model.of("Days in year"));
        this.termDayInYearField.add(new OnChangeAjaxBehavior());

        this.termInterestCalculatedUsingField.setLabel(Model.of("Interest posting period"));
        this.termInterestCalculatedUsingField.add(new OnChangeAjaxBehavior());

        this.termInterestCompoundingPeriodField.setLabel(Model.of("Interest compounding period"));
        this.termInterestCompoundingPeriodField.add(new OnChangeAjaxBehavior());

        this.termNominalAnnualInterestField.setLabel(Model.of("Nominal annual interest"));
        this.termNominalAnnualInterestField.add(new OnChangeAjaxBehavior());

        this.termNominalAnnualInterestField.setRequired(true);
        this.termInterestCompoundingPeriodField.setRequired(true);
        this.termInterestCalculatedUsingField.setRequired(true);
        this.termDayInYearField.setRequired(true);
        this.termInterestPostingPeriodField.setRequired(true);
    }

    protected void nextButtonSubmit(Button button) {
        this.tab.getObject().setSelectedTab(SavingCreatePage.TAB_SETTING);
        this.errorTerm.setObject(false);
    }

    protected void nextButtonError(Button button) {
        this.errorTerm.setObject(true);
    }

    protected boolean backLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.tab.getObject().setSelectedTab(SavingCreatePage.TAB_CURRENCY);
        if (target != null) {
            target.add(this.tab.getObject());
        }
        return false;
    }

}
