package com.angkorteam.fintech.widget.product.recurring;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.dto.enums.DayInYear;
import com.angkorteam.fintech.pages.product.saving.SavingBrowsePage;
import com.angkorteam.fintech.pages.product.saving.SavingCreatePage;
import com.angkorteam.fintech.provider.DayInYearProvider;
import com.angkorteam.fintech.provider.InterestCalculatedUsingProvider;
import com.angkorteam.fintech.provider.InterestCompoundingPeriodProvider;
import com.angkorteam.fintech.provider.InterestPostingPeriodProvider;
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

public class TermsPanel extends Panel {

    protected Page itemPage;
    protected PropertyModel<AjaxTabbedPanel<ITab>> tab;
    protected PropertyModel<Boolean> errorTerm;

    protected Form<Void> form;
    protected Button nextButton;
    protected AjaxLink<Void> backLink;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock termNominalAnnualInterestBlock;
    protected WebMarkupContainer termNominalAnnualInterestIContainer;
    protected TextField<Double> termNominalAnnualInterestField;
    protected TextFeedbackPanel termNominalAnnualInterestFeedback;

    protected WebMarkupBlock termInterestCompoundingPeriodBlock;
    protected WebMarkupContainer termInterestCompoundingPeriodIContainer;
    protected InterestCompoundingPeriodProvider termInterestCompoundingPeriodProvider;
    protected Select2SingleChoice<Option> termInterestCompoundingPeriodField;
    protected TextFeedbackPanel termInterestCompoundingPeriodFeedback;

    protected WebMarkupBlock termInterestCalculatedUsingBlock;
    protected WebMarkupContainer termInterestCalculatedUsingIContainer;
    protected InterestCalculatedUsingProvider termInterestCalculatedUsingProvider;
    protected Select2SingleChoice<Option> termInterestCalculatedUsingField;
    protected TextFeedbackPanel termInterestCalculatedUsingFeedback;

    protected WebMarkupBlock termInterestPostingPeriodBlock;
    protected WebMarkupContainer termInterestPostingPeriodIContainer;
    protected InterestPostingPeriodProvider termInterestPostingPeriodProvider;
    protected Select2SingleChoice<Option> termInterestPostingPeriodField;
    protected TextFeedbackPanel termInterestPostingPeriodFeedback;

    protected WebMarkupBlock termDayInYearBlock;
    protected WebMarkupContainer termDayInYearIContainer;
    protected DayInYearProvider termDayInYearProvider;
    protected Select2SingleChoice<Option> termDayInYearField;
    protected TextFeedbackPanel termDayInYearFeedback;

    public TermsPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void initData() {
        this.tab = new PropertyModel<>(this.itemPage, "tab");
        this.errorTerm = new PropertyModel<>(this.itemPage, "errorTerm");
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

        this.termDayInYearBlock = new WebMarkupBlock("termDayInYearBlock", Size.Six_6);
        this.form.add(this.termDayInYearBlock);
        this.termDayInYearIContainer = new WebMarkupContainer("termDayInYearIContainer");
        this.termDayInYearBlock.add(this.termDayInYearIContainer);
        this.termDayInYearProvider = new DayInYearProvider(DayInYear.D365, DayInYear.D360);
        this.termDayInYearField = new Select2SingleChoice<>("termDayInYearField", new PropertyModel<>(this.itemPage, "termDayInYearValue"), this.termDayInYearProvider);
        this.termDayInYearField.setLabel(Model.of("Days in year"));
        this.termDayInYearField.add(new OnChangeAjaxBehavior());
        this.termDayInYearIContainer.add(this.termDayInYearField);
        this.termDayInYearFeedback = new TextFeedbackPanel("termDayInYearFeedback", this.termDayInYearField);
        this.termDayInYearIContainer.add(this.termDayInYearFeedback);

        this.termInterestPostingPeriodBlock = new WebMarkupBlock("termInterestPostingPeriodBlock", Size.Six_6);
        this.form.add(this.termInterestPostingPeriodBlock);
        this.termInterestPostingPeriodIContainer = new WebMarkupContainer("termInterestPostingPeriodIContainer");
        this.termInterestPostingPeriodBlock.add(this.termInterestPostingPeriodIContainer);
        this.termInterestPostingPeriodProvider = new InterestPostingPeriodProvider();
        this.termInterestPostingPeriodField = new Select2SingleChoice<>("termInterestPostingPeriodField", new PropertyModel<>(this.itemPage, "termInterestPostingPeriodValue"), this.termInterestPostingPeriodProvider);
        this.termInterestPostingPeriodField.setLabel(Model.of("Interest calculated using"));
        this.termInterestPostingPeriodField.add(new OnChangeAjaxBehavior());
        this.termInterestPostingPeriodIContainer.add(this.termInterestPostingPeriodField);
        this.termInterestPostingPeriodFeedback = new TextFeedbackPanel("termInterestPostingPeriodFeedback", this.termInterestPostingPeriodField);
        this.termInterestPostingPeriodIContainer.add(this.termInterestPostingPeriodFeedback);

        this.termInterestCalculatedUsingBlock = new WebMarkupBlock("termInterestCalculatedUsingBlock", Size.Six_6);
        this.form.add(this.termInterestCalculatedUsingBlock);
        this.termInterestCalculatedUsingIContainer = new WebMarkupContainer("termInterestCalculatedUsingIContainer");
        this.termInterestCalculatedUsingBlock.add(this.termInterestCalculatedUsingIContainer);
        this.termInterestCalculatedUsingProvider = new InterestCalculatedUsingProvider();
        this.termInterestCalculatedUsingField = new Select2SingleChoice<>("termInterestCalculatedUsingField", new PropertyModel<>(this.itemPage, "termInterestCalculatedUsingValue"), this.termInterestCalculatedUsingProvider);
        this.termInterestCalculatedUsingField.setLabel(Model.of("Interest posting period"));
        this.termInterestCalculatedUsingField.add(new OnChangeAjaxBehavior());
        this.termInterestCalculatedUsingIContainer.add(this.termInterestCalculatedUsingField);
        this.termInterestCalculatedUsingFeedback = new TextFeedbackPanel("termInterestCalculatedUsingFeedback", this.termInterestCalculatedUsingField);
        this.termInterestCalculatedUsingIContainer.add(this.termInterestCalculatedUsingFeedback);

        this.termInterestCompoundingPeriodBlock = new WebMarkupBlock("termInterestCompoundingPeriodBlock", Size.Six_6);
        this.form.add(this.termInterestCompoundingPeriodBlock);
        this.termInterestCompoundingPeriodIContainer = new WebMarkupContainer("termInterestCompoundingPeriodIContainer");
        this.termInterestCompoundingPeriodBlock.add(this.termInterestCompoundingPeriodIContainer);
        this.termInterestCompoundingPeriodProvider = new InterestCompoundingPeriodProvider();
        this.termInterestCompoundingPeriodField = new Select2SingleChoice<>("termInterestCompoundingPeriodField", new PropertyModel<>(this.itemPage, "termInterestCompoundingPeriodValue"), this.termInterestCompoundingPeriodProvider);
        this.termInterestCompoundingPeriodField.setLabel(Model.of("Interest compounding period"));
        this.termInterestCompoundingPeriodField.add(new OnChangeAjaxBehavior());
        this.termInterestCompoundingPeriodIContainer.add(this.termInterestCompoundingPeriodField);
        this.termInterestCompoundingPeriodFeedback = new TextFeedbackPanel("termInterestCompoundingPeriodFeedback", this.termInterestCompoundingPeriodField);
        this.termInterestCompoundingPeriodIContainer.add(this.termInterestCompoundingPeriodFeedback);

        this.termNominalAnnualInterestBlock = new WebMarkupBlock("termNominalAnnualInterestBlock", Size.Six_6);
        this.form.add(this.termNominalAnnualInterestBlock);
        this.termNominalAnnualInterestIContainer = new WebMarkupContainer("termNominalAnnualInterestIContainer");
        this.termNominalAnnualInterestBlock.add(this.termNominalAnnualInterestIContainer);
        this.termNominalAnnualInterestField = new TextField<>("termNominalAnnualInterestField", new PropertyModel<>(this.itemPage, "termNominalAnnualInterestValue"));
        this.termNominalAnnualInterestField.setLabel(Model.of("Nominal annual interest"));
        this.termNominalAnnualInterestField.add(new OnChangeAjaxBehavior());
        this.termNominalAnnualInterestIContainer.add(this.termNominalAnnualInterestField);
        this.termNominalAnnualInterestFeedback = new TextFeedbackPanel("termNominalAnnualInterestFeedback", this.termNominalAnnualInterestField);
        this.termNominalAnnualInterestIContainer.add(this.termNominalAnnualInterestFeedback);
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

    @Override
    protected void configureMetaData() {
        this.termNominalAnnualInterestField.setRequired(true);
        this.termInterestCompoundingPeriodField.setRequired(true);
        this.termInterestCalculatedUsingField.setRequired(true);
        this.termDayInYearField.setRequired(true);
        this.termInterestPostingPeriodField.setRequired(true);
    }

}
