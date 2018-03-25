package com.angkorteam.fintech.widget.product.loan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.ddl.MFloatingRates;
import com.angkorteam.fintech.dto.enums.LockInType;
import com.angkorteam.fintech.pages.product.loan.LoanBrowsePage;
import com.angkorteam.fintech.pages.product.loan.LoanCreatePage;
import com.angkorteam.fintech.popup.InterestLoanCyclePopup;
import com.angkorteam.fintech.popup.PrincipleLoanCyclePopup;
import com.angkorteam.fintech.popup.RepaymentLoanCyclePopup;
import com.angkorteam.fintech.provider.LockInTypeProvider;
import com.angkorteam.fintech.provider.NominalInterestRateTypeProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.Panel;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.TextColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionFilterColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionItem;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemCss;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class TermsPanel extends Panel {

    protected Page itemPage;
    protected PropertyModel<AjaxTabbedPanel<ITab>> tab;
    protected PropertyModel<Boolean> errorTerm;

    protected Form<Void> form;
    protected Button nextButton;
    protected AjaxLink<Void> backLink;
    protected BookmarkablePageLink<Void> closeLink;

    protected ModalWindow modalWindow;
    protected Map<String, Object> popupModel;

    // Terms

    // Row 1 : Terms vary based on loan cycle
    protected WebMarkupBlock termVaryBasedOnLoanCycleBlock;
    protected WebMarkupContainer termVaryBasedOnLoanCycleIContainer;
    protected CheckBox termVaryBasedOnLoanCycleField;
    protected TextFeedbackPanel termVaryBasedOnLoanCycleFeedback;

    // Row 2 : Principle
    protected WebMarkupBlock termPrincipleMinimumBlock;
    protected WebMarkupContainer termPrincipleMinimumIContainer;
    protected TextField<Double> termPrincipleMinimumField;
    protected TextFeedbackPanel termPrincipleMinimumFeedback;

    protected WebMarkupBlock termPrincipleDefaultBlock;
    protected WebMarkupContainer termPrincipleDefaultIContainer;
    protected TextField<Double> termPrincipleDefaultField;
    protected TextFeedbackPanel termPrincipleDefaultFeedback;

    protected WebMarkupBlock termPrincipleMaximumBlock;
    protected WebMarkupContainer termPrincipleMaximumIContainer;
    protected TextField<Double> termPrincipleMaximumField;
    protected TextFeedbackPanel termPrincipleMaximumFeedback;

    // Row 2 (Optional) : Principle by loan cycle
    protected WebMarkupBlock termPrincipleByLoanCycleBlock;
    protected WebMarkupContainer termPrincipleByLoanCycleIContainer;
    protected PropertyModel<List<Map<String, Object>>> termPrincipleByLoanCycleValue;
    protected DataTable<Map<String, Object>, String> termPrincipleByLoanCycleTable;
    protected List<IColumn<Map<String, Object>, String>> termPrincipleByLoanCycleColumn;
    protected ListDataProvider termPrincipleByLoanCycleProvider;
    protected AjaxLink<Void> termPrincipleByLoanCycleAddLink;

    // Row 3 : Number of repayments
    protected WebMarkupBlock termNumberOfRepaymentMinimumBlock;
    protected WebMarkupContainer termNumberOfRepaymentMinimumIContainer;
    protected TextField<Long> termNumberOfRepaymentMinimumField;
    protected TextFeedbackPanel termNumberOfRepaymentMinimumFeedback;

    protected WebMarkupBlock termNumberOfRepaymentDefaultBlock;
    protected WebMarkupContainer termNumberOfRepaymentDefaultIContainer;
    protected TextField<Long> termNumberOfRepaymentDefaultField;
    protected TextFeedbackPanel termNumberOfRepaymentDefaultFeedback;

    protected WebMarkupBlock termNumberOfRepaymentMaximumBlock;
    protected WebMarkupContainer termNumberOfRepaymentMaximumIContainer;
    protected TextField<Long> termNumberOfRepaymentMaximumField;
    protected TextFeedbackPanel termNumberOfRepaymentMaximumFeedback;

    protected WebMarkupBlock termRepaidEveryBlock;
    protected WebMarkupContainer termRepaidEveryIContainer;
    protected TextField<Long> termRepaidEveryField;
    protected TextFeedbackPanel termRepaidEveryFeedback;

    protected WebMarkupBlock termRepaidTypeBlock;
    protected WebMarkupContainer termRepaidTypeIContainer;
    protected LockInTypeProvider termRepaidTypeProvider;
    protected Select2SingleChoice<Option> termRepaidTypeField;
    protected TextFeedbackPanel termRepaidTypeFeedback;

    // Row 3 (Optional) : Number of Repayments by loan cycle
    protected List<IColumn<Map<String, Object>, String>> termNumberOfRepaymentByLoanCycleColumn;
    protected WebMarkupBlock termNumberOfRepaymentByLoanCycleBlock;
    protected WebMarkupContainer termNumberOfRepaymentByLoanCycleIContainer;
    protected PropertyModel<List<Map<String, Object>>> termNumberOfRepaymentByLoanCycleValue;
    protected DataTable<Map<String, Object>, String> termNumberOfRepaymentByLoanCycleTable;
    protected ListDataProvider termNumberOfRepaymentByLoanCycleProvider;
    protected AjaxLink<Void> termNumberOfRepaymentByLoanCycleAddLink;

    // Row 4 : Is Linked to Floating Interest Rates?
    protected WebMarkupBlock termLinkedToFloatingInterestRatesBlock;
    protected WebMarkupContainer termLinkedToFloatingInterestRatesIContainer;
    protected CheckBox termLinkedToFloatingInterestRatesField;
    protected TextFeedbackPanel termLinkedToFloatingInterestRatesFeedback;

    // Row 5 : Nominal interest rate
    protected WebMarkupBlock termNominalInterestRateMinimumBlock;
    protected WebMarkupContainer termNominalInterestRateMinimumIContainer;
    protected TextField<Double> termNominalInterestRateMinimumField;
    protected TextFeedbackPanel termNominalInterestRateMinimumFeedback;

    protected WebMarkupBlock termNominalInterestRateDefaultBlock;
    protected WebMarkupContainer termNominalInterestRateDefaultIContainer;
    protected TextField<Double> termNominalInterestRateDefaultField;
    protected TextFeedbackPanel termNominalInterestRateDefaultFeedback;

    protected WebMarkupBlock termNominalInterestRateMaximumBlock;
    protected WebMarkupContainer termNominalInterestRateMaximumIContainer;
    protected TextField<Double> termNominalInterestRateMaximumField;
    protected TextFeedbackPanel termNominalInterestRateMaximumFeedback;

    protected WebMarkupBlock termNominalInterestRateTypeBlock;
    protected WebMarkupContainer termNominalInterestRateTypeIContainer;
    protected NominalInterestRateTypeProvider termNominalInterestRateTypeProvider;
    protected Select2SingleChoice<Option> termNominalInterestRateTypeField;
    protected TextFeedbackPanel termNominalInterestRateTypeFeedback;

    protected WebMarkupBlock termFloatingInterestRateBlock;
    protected WebMarkupContainer termFloatingInterestRateIContainer;
    protected SingleChoiceProvider termFloatingInterestRateProvider;
    protected Select2SingleChoice<Option> termFloatingInterestRateField;
    protected TextFeedbackPanel termFloatingInterestRateFeedback;

    protected WebMarkupBlock termFloatingInterestDifferentialBlock;
    protected WebMarkupContainer termFloatingInterestDifferentialIContainer;
    protected TextField<Double> termFloatingInterestDifferentialField;
    protected TextFeedbackPanel termFloatingInterestDifferentialFeedback;

    protected WebMarkupBlock termFloatingInterestAllowedBlock;
    protected WebMarkupContainer termFloatingInterestAllowedIContainer;
    protected CheckBox termFloatingInterestAllowedField;
    protected TextFeedbackPanel termFloatingInterestAllowedFeedback;

    protected WebMarkupBlock termFloatingInterestMinimumBlock;
    protected WebMarkupContainer termFloatingInterestMinimumIContainer;
    protected TextField<Double> termFloatingInterestMinimumField;
    protected TextFeedbackPanel termFloatingInterestMinimumFeedback;

    protected WebMarkupBlock termFloatingInterestDefaultBlock;
    protected WebMarkupContainer termFloatingInterestDefaultIContainer;
    protected TextField<Double> termFloatingInterestDefaultField;
    protected TextFeedbackPanel termFloatingInterestDefaultFeedback;

    protected WebMarkupBlock termFloatingInterestMaximumBlock;
    protected WebMarkupContainer termFloatingInterestMaximumIContainer;
    protected TextField<Double> termFloatingInterestMaximumField;
    protected TextFeedbackPanel termFloatingInterestMaximumFeedback;

    // Row 6
    protected WebMarkupBlock termNominalInterestRateByLoanCycleBlock;
    protected WebMarkupContainer termNominalInterestRateByLoanCycleIContainer;
    protected PropertyModel<List<Map<String, Object>>> termNominalInterestRateByLoanCycleValue;
    protected DataTable<Map<String, Object>, String> termNominalInterestRateByLoanCycleTable;
    protected ListDataProvider termNominalInterestRateByLoanCycleProvider;
    protected AjaxLink<Void> termNominalInterestRateByLoanCycleAddLink;
    protected List<IColumn<Map<String, Object>, String>> termNominalInterestRateByLoanCycleColumn;

    protected WebMarkupBlock termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock;
    protected WebMarkupContainer termMinimumDayBetweenDisbursalAndFirstRepaymentDateIContainer;
    protected TextField<Long> termMinimumDayBetweenDisbursalAndFirstRepaymentDateField;
    protected TextFeedbackPanel termMinimumDayBetweenDisbursalAndFirstRepaymentDateFeedback;

    public TermsPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void initData() {
        this.tab = new PropertyModel<>(this.itemPage, "tab");
        this.errorTerm = new PropertyModel<>(this.itemPage, "errorTerm");

        this.popupModel = new HashMap<>();

        this.termPrincipleByLoanCycleValue = new PropertyModel<>(this.itemPage, "termPrincipleByLoanCycleValue");
        this.termPrincipleByLoanCycleColumn = Lists.newArrayList();
        this.termPrincipleByLoanCycleColumn.add(new TextColumn(Model.of("When"), "when", "when", this::termPrincipleByLoanCycleColumn));
        this.termPrincipleByLoanCycleColumn.add(new TextColumn(Model.of("Loan Cycle"), "cycle", "cycle", this::termPrincipleByLoanCycleColumn));
        this.termPrincipleByLoanCycleColumn.add(new TextColumn(Model.of("Min"), "minimum", "minimum", this::termPrincipleByLoanCycleColumn));
        this.termPrincipleByLoanCycleColumn.add(new TextColumn(Model.of("Default"), "default", "default", this::termPrincipleByLoanCycleColumn));
        this.termPrincipleByLoanCycleColumn.add(new TextColumn(Model.of("Max"), "maximum", "maximum", this::termPrincipleByLoanCycleColumn));
        this.termPrincipleByLoanCycleColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::termPrincipleByLoanCycleAction, this::termPrincipleByLoanCycleClick));
        this.termPrincipleByLoanCycleProvider = new ListDataProvider(this.termPrincipleByLoanCycleValue.getObject());

        this.termNominalInterestRateByLoanCycleValue = new PropertyModel<>(this.itemPage, "termNominalInterestRateByLoanCycleValue");
        this.termNominalInterestRateByLoanCycleColumn = Lists.newArrayList();
        this.termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("When"), "when", "when", this::termNominalInterestRateByLoanCycleColumn));
        this.termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Loan Cycle"), "cycle", "cycle", this::termNominalInterestRateByLoanCycleColumn));
        this.termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Min"), "minimum", "minimum", this::termNominalInterestRateByLoanCycleColumn));
        this.termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Default"), "default", "default", this::termNominalInterestRateByLoanCycleColumn));
        this.termNominalInterestRateByLoanCycleColumn.add(new TextColumn(Model.of("Max"), "maximum", "maximum", this::termNominalInterestRateByLoanCycleColumn));
        this.termNominalInterestRateByLoanCycleColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::termNominalInterestRateByLoanCycleActionItem, this::termNominalInterestRateByLoanCycleActionClick));
        this.termNominalInterestRateByLoanCycleProvider = new ListDataProvider(this.termNominalInterestRateByLoanCycleValue.getObject());

        this.termNumberOfRepaymentByLoanCycleValue = new PropertyModel<>(this.itemPage, "termNumberOfRepaymentByLoanCycleValue");
        this.termNumberOfRepaymentByLoanCycleColumn = Lists.newArrayList();
        this.termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("When"), "when", "when", this::termNumberOfRepaymentByLoanCycleColumn));
        this.termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Loan Cycle"), "cycle", "cycle", this::termNumberOfRepaymentByLoanCycleColumn));
        this.termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Min"), "minimum", "minimum", this::termNumberOfRepaymentByLoanCycleColumn));
        this.termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Default"), "default", "default", this::termNumberOfRepaymentByLoanCycleColumn));
        this.termNumberOfRepaymentByLoanCycleColumn.add(new TextColumn(Model.of("Max"), "maximum", "maximum", this::termNumberOfRepaymentByLoanCycleColumn));
        this.termNumberOfRepaymentByLoanCycleColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::termNumberOfRepaymentByLoanCycleAction, this::termNumberOfRepaymentByLoanCycleClick));
        this.termNumberOfRepaymentByLoanCycleProvider = new ListDataProvider(this.termNumberOfRepaymentByLoanCycleValue.getObject());
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

        this.modalWindow = new ModalWindow("modalWindow");
        add(this.modalWindow);
        this.modalWindow.setOnClose(this::modalWindowClose);

        this.termVaryBasedOnLoanCycleBlock = new WebMarkupBlock("termVaryBasedOnLoanCycleBlock", Size.Twelve_12);
        this.form.add(this.termVaryBasedOnLoanCycleBlock);
        this.termVaryBasedOnLoanCycleIContainer = new WebMarkupContainer("termVaryBasedOnLoanCycleIContainer");
        this.termVaryBasedOnLoanCycleBlock.add(this.termVaryBasedOnLoanCycleIContainer);
        this.termVaryBasedOnLoanCycleField = new CheckBox("termVaryBasedOnLoanCycleField", new PropertyModel<>(this.itemPage, "termVaryBasedOnLoanCycleValue"));
        this.termVaryBasedOnLoanCycleField.add(new OnChangeAjaxBehavior(this::termVaryBasedOnLoanCycleFieldUpdate));
        this.termVaryBasedOnLoanCycleIContainer.add(this.termVaryBasedOnLoanCycleField);
        this.termVaryBasedOnLoanCycleFeedback = new TextFeedbackPanel("termVaryBasedOnLoanCycleFeedback", this.termVaryBasedOnLoanCycleField);
        this.termVaryBasedOnLoanCycleIContainer.add(this.termVaryBasedOnLoanCycleFeedback);

        this.termPrincipleMinimumBlock = new WebMarkupBlock("termPrincipleMinimumBlock", Size.Three_3);
        this.form.add(this.termPrincipleMinimumBlock);
        this.termPrincipleMinimumIContainer = new WebMarkupContainer("termPrincipleMinimumIContainer");
        this.termPrincipleMinimumBlock.add(this.termPrincipleMinimumIContainer);
        this.termPrincipleMinimumField = new TextField<>("termPrincipleMinimumField", new PropertyModel<>(this.itemPage, "termPrincipleMinimumValue"));
        this.termPrincipleMinimumField.setLabel(Model.of("Principle Minimum"));
        this.termPrincipleMinimumField.add(new OnChangeAjaxBehavior());
        this.termPrincipleMinimumIContainer.add(this.termPrincipleMinimumField);
        this.termPrincipleMinimumFeedback = new TextFeedbackPanel("termPrincipleMinimumFeedback", this.termPrincipleMinimumField);
        this.termPrincipleMinimumIContainer.add(this.termPrincipleMinimumFeedback);

        this.termPrincipleDefaultBlock = new WebMarkupBlock("termPrincipleDefaultBlock", Size.Three_3);
        this.form.add(this.termPrincipleDefaultBlock);
        this.termPrincipleDefaultIContainer = new WebMarkupContainer("termPrincipleDefaultIContainer");
        this.termPrincipleDefaultBlock.add(this.termPrincipleDefaultIContainer);
        this.termPrincipleDefaultField = new TextField<>("termPrincipleDefaultField", new PropertyModel<>(this.itemPage, "termPrincipleDefaultValue"));
        this.termPrincipleDefaultField.setLabel(Model.of("Principle Default"));
        this.termPrincipleDefaultField.add(new OnChangeAjaxBehavior());
        this.termPrincipleDefaultIContainer.add(this.termPrincipleDefaultField);
        this.termPrincipleDefaultFeedback = new TextFeedbackPanel("termPrincipleDefaultFeedback", this.termPrincipleDefaultField);
        this.termPrincipleDefaultIContainer.add(this.termPrincipleDefaultFeedback);

        this.termPrincipleMaximumBlock = new WebMarkupBlock("termPrincipleMaximumBlock", Size.Three_3);
        this.form.add(this.termPrincipleMaximumBlock);
        this.termPrincipleMaximumIContainer = new WebMarkupContainer("termPrincipleMaximumIContainer");
        this.termPrincipleMaximumBlock.add(this.termPrincipleMaximumIContainer);
        this.termPrincipleMaximumField = new TextField<>("termPrincipleMaximumField", new PropertyModel<>(this.itemPage, "termPrincipleMaximumValue"));
        this.termPrincipleMaximumField.setLabel(Model.of("Principle Maximum"));
        this.termPrincipleMaximumField.add(new OnChangeAjaxBehavior());
        this.termPrincipleMaximumIContainer.add(this.termPrincipleMaximumField);
        this.termPrincipleMaximumFeedback = new TextFeedbackPanel("termPrincipleMaximumFeedback", this.termPrincipleMaximumField);
        this.termPrincipleMaximumIContainer.add(this.termPrincipleMaximumFeedback);

        this.termPrincipleByLoanCycleBlock = new WebMarkupBlock("termPrincipleByLoanCycleBlock", Size.Twelve_12);
        this.form.add(this.termPrincipleByLoanCycleBlock);
        this.termPrincipleByLoanCycleIContainer = new WebMarkupContainer("termPrincipleByLoanCycleIContainer");
        this.termPrincipleByLoanCycleBlock.add(this.termPrincipleByLoanCycleIContainer);
        this.termPrincipleByLoanCycleTable = new DataTable<>("termPrincipleByLoanCycleTable", this.termPrincipleByLoanCycleColumn, this.termPrincipleByLoanCycleProvider, 20);
        this.termPrincipleByLoanCycleIContainer.add(this.termPrincipleByLoanCycleTable);
        this.termPrincipleByLoanCycleTable.addTopToolbar(new HeadersToolbar<>(this.termPrincipleByLoanCycleTable, this.termPrincipleByLoanCycleProvider));
        this.termPrincipleByLoanCycleTable.addBottomToolbar(new NoRecordsToolbar(this.termPrincipleByLoanCycleTable));
        this.termPrincipleByLoanCycleAddLink = new AjaxLink<>("termPrincipleByLoanCycleAddLink");
        this.termPrincipleByLoanCycleAddLink.setOnClick(this::termPrincipleByLoanCycleAddLinkClick);
        this.termPrincipleByLoanCycleIContainer.add(this.termPrincipleByLoanCycleAddLink);

        this.termNumberOfRepaymentMinimumBlock = new WebMarkupBlock("termNumberOfRepaymentMinimumBlock", Size.Three_3);
        this.form.add(this.termNumberOfRepaymentMinimumBlock);
        this.termNumberOfRepaymentMinimumIContainer = new WebMarkupContainer("termNumberOfRepaymentMinimumIContainer");
        this.termNumberOfRepaymentMinimumBlock.add(this.termNumberOfRepaymentMinimumIContainer);
        this.termNumberOfRepaymentMinimumField = new TextField<>("termNumberOfRepaymentMinimumField", new PropertyModel<>(this.itemPage, "termNumberOfRepaymentMinimumValue"));
        this.termNumberOfRepaymentMinimumField.setLabel(Model.of("Number of repayment Minimum"));
        this.termNumberOfRepaymentMinimumField.add(new OnChangeAjaxBehavior());
        this.termNumberOfRepaymentMinimumIContainer.add(this.termNumberOfRepaymentMinimumField);
        this.termNumberOfRepaymentMinimumFeedback = new TextFeedbackPanel("termNumberOfRepaymentMinimumFeedback", this.termNumberOfRepaymentMinimumField);
        this.termNumberOfRepaymentMinimumIContainer.add(this.termNumberOfRepaymentMinimumFeedback);

        this.termNumberOfRepaymentDefaultBlock = new WebMarkupBlock("termNumberOfRepaymentDefaultBlock", Size.Three_3);
        this.form.add(this.termNumberOfRepaymentDefaultBlock);
        this.termNumberOfRepaymentDefaultIContainer = new WebMarkupContainer("termNumberOfRepaymentDefaultIContainer");
        this.termNumberOfRepaymentDefaultBlock.add(this.termNumberOfRepaymentDefaultIContainer);
        this.termNumberOfRepaymentDefaultField = new TextField<>("termNumberOfRepaymentDefaultField", new PropertyModel<>(this.itemPage, "termNumberOfRepaymentDefaultValue"));
        this.termNumberOfRepaymentDefaultField.setLabel(Model.of("Number of repayment Default"));
        this.termNumberOfRepaymentDefaultField.add(new OnChangeAjaxBehavior());
        this.termNumberOfRepaymentDefaultIContainer.add(this.termNumberOfRepaymentDefaultField);
        this.termNumberOfRepaymentDefaultFeedback = new TextFeedbackPanel("termNumberOfRepaymentDefaultFeedback", this.termNumberOfRepaymentDefaultField);
        this.termNumberOfRepaymentDefaultIContainer.add(this.termNumberOfRepaymentDefaultFeedback);

        this.termNumberOfRepaymentMaximumBlock = new WebMarkupBlock("termNumberOfRepaymentMaximumBlock", Size.Three_3);
        this.form.add(this.termNumberOfRepaymentMaximumBlock);
        this.termNumberOfRepaymentMaximumIContainer = new WebMarkupContainer("termNumberOfRepaymentMaximumIContainer");
        this.termNumberOfRepaymentMaximumBlock.add(this.termNumberOfRepaymentMaximumIContainer);
        this.termNumberOfRepaymentMaximumField = new TextField<>("termNumberOfRepaymentMaximumField", new PropertyModel<>(this.itemPage, "termNumberOfRepaymentMaximumValue"));
        this.termNumberOfRepaymentMaximumField.setLabel(Model.of("Number of repayment Maximum"));
        this.termNumberOfRepaymentMaximumField.add(new OnChangeAjaxBehavior());
        this.termNumberOfRepaymentMaximumIContainer.add(this.termNumberOfRepaymentMaximumField);
        this.termNumberOfRepaymentMaximumFeedback = new TextFeedbackPanel("termNumberOfRepaymentMaximumFeedback", this.termNumberOfRepaymentMaximumField);
        this.termNumberOfRepaymentMaximumIContainer.add(this.termNumberOfRepaymentMaximumFeedback);

        this.termNumberOfRepaymentByLoanCycleBlock = new WebMarkupBlock("termNumberOfRepaymentByLoanCycleBlock", Size.Twelve_12);
        this.form.add(this.termNumberOfRepaymentByLoanCycleBlock);
        this.termNumberOfRepaymentByLoanCycleIContainer = new WebMarkupContainer("termNumberOfRepaymentByLoanCycleIContainer");
        this.termNumberOfRepaymentByLoanCycleBlock.add(this.termNumberOfRepaymentByLoanCycleIContainer);
        this.termNumberOfRepaymentByLoanCycleTable = new DataTable<>("termNumberOfRepaymentByLoanCycleTable", this.termNumberOfRepaymentByLoanCycleColumn, this.termNumberOfRepaymentByLoanCycleProvider, 20);
        this.termNumberOfRepaymentByLoanCycleIContainer.add(this.termNumberOfRepaymentByLoanCycleTable);
        this.termNumberOfRepaymentByLoanCycleTable.addTopToolbar(new HeadersToolbar<>(this.termNumberOfRepaymentByLoanCycleTable, this.termNumberOfRepaymentByLoanCycleProvider));
        this.termNumberOfRepaymentByLoanCycleTable.addBottomToolbar(new NoRecordsToolbar(this.termNumberOfRepaymentByLoanCycleTable));
        this.termNumberOfRepaymentByLoanCycleAddLink = new AjaxLink<>("termNumberOfRepaymentByLoanCycleAddLink");
        this.termNumberOfRepaymentByLoanCycleAddLink.setOnClick(this::termNumberOfRepaymentByLoanCycleAddLinkClick);
        this.termNumberOfRepaymentByLoanCycleIContainer.add(this.termNumberOfRepaymentByLoanCycleAddLink);

        // Linked to Floating Interest Rates
        this.termLinkedToFloatingInterestRatesBlock = new WebMarkupBlock("termLinkedToFloatingInterestRatesBlock", Size.Twelve_12);
        this.form.add(this.termLinkedToFloatingInterestRatesBlock);
        this.termLinkedToFloatingInterestRatesIContainer = new WebMarkupContainer("termLinkedToFloatingInterestRatesIContainer");
        this.termLinkedToFloatingInterestRatesBlock.add(this.termLinkedToFloatingInterestRatesIContainer);
        this.termLinkedToFloatingInterestRatesField = new CheckBox("termLinkedToFloatingInterestRatesField", new PropertyModel<>(this.itemPage, "termLinkedToFloatingInterestRatesValue"));
        this.termLinkedToFloatingInterestRatesField.add(new OnChangeAjaxBehavior(this::termLinkedToFloatingInterestRatesFieldUpdate));
        this.termLinkedToFloatingInterestRatesIContainer.add(this.termLinkedToFloatingInterestRatesField);
        this.termLinkedToFloatingInterestRatesFeedback = new TextFeedbackPanel("termLinkedToFloatingInterestRatesFeedback", this.termLinkedToFloatingInterestRatesField);
        this.termLinkedToFloatingInterestRatesIContainer.add(this.termLinkedToFloatingInterestRatesFeedback);

        this.termNominalInterestRateMinimumBlock = new WebMarkupBlock("termNominalInterestRateMinimumBlock", Size.Three_3);
        this.form.add(this.termNominalInterestRateMinimumBlock);
        this.termNominalInterestRateMinimumIContainer = new WebMarkupContainer("termNominalInterestRateMinimumIContainer");
        this.termNominalInterestRateMinimumBlock.add(this.termNominalInterestRateMinimumIContainer);
        this.termNominalInterestRateMinimumField = new TextField<>("termNominalInterestRateMinimumField", new PropertyModel<>(this.itemPage, "termNominalInterestRateMinimumValue"));
        this.termNominalInterestRateMinimumField.setLabel(Model.of("Nominal interest rate minimum"));
        this.termNominalInterestRateMinimumField.add(new OnChangeAjaxBehavior());
        this.termNominalInterestRateMinimumIContainer.add(this.termNominalInterestRateMinimumField);
        this.termNominalInterestRateMinimumFeedback = new TextFeedbackPanel("termNominalInterestRateMinimumFeedback", this.termNominalInterestRateMinimumField);
        this.termNominalInterestRateMinimumIContainer.add(this.termNominalInterestRateMinimumFeedback);

        this.termNominalInterestRateDefaultBlock = new WebMarkupBlock("termNominalInterestRateDefaultBlock", Size.Three_3);
        this.form.add(this.termNominalInterestRateDefaultBlock);
        this.termNominalInterestRateDefaultIContainer = new WebMarkupContainer("termNominalInterestRateDefaultIContainer");
        this.termNominalInterestRateDefaultBlock.add(this.termNominalInterestRateDefaultIContainer);
        this.termNominalInterestRateDefaultField = new TextField<>("termNominalInterestRateDefaultField", new PropertyModel<>(this.itemPage, "termNominalInterestRateDefaultValue"));
        this.termNominalInterestRateDefaultField.setLabel(Model.of("Nominal interest rate Default"));
        this.termNominalInterestRateDefaultField.add(new OnChangeAjaxBehavior());
        this.termNominalInterestRateDefaultIContainer.add(this.termNominalInterestRateDefaultField);
        this.termNominalInterestRateDefaultFeedback = new TextFeedbackPanel("termNominalInterestRateDefaultFeedback", this.termNominalInterestRateDefaultField);
        this.termNominalInterestRateDefaultIContainer.add(this.termNominalInterestRateDefaultFeedback);

        this.termNominalInterestRateMaximumBlock = new WebMarkupBlock("termNominalInterestRateMaximumBlock", Size.Three_3);
        this.form.add(this.termNominalInterestRateMaximumBlock);
        this.termNominalInterestRateMaximumIContainer = new WebMarkupContainer("termNominalInterestRateMaximumIContainer");
        this.termNominalInterestRateMaximumBlock.add(this.termNominalInterestRateMaximumIContainer);
        this.termNominalInterestRateMaximumField = new TextField<>("termNominalInterestRateMaximumField", new PropertyModel<>(this.itemPage, "termNominalInterestRateMaximumValue"));
        this.termNominalInterestRateMaximumField.setLabel(Model.of("Nominal interest rate Maximum"));
        this.termNominalInterestRateMaximumField.add(new OnChangeAjaxBehavior());
        this.termNominalInterestRateMaximumIContainer.add(this.termNominalInterestRateMaximumField);
        this.termNominalInterestRateMaximumFeedback = new TextFeedbackPanel("termNominalInterestRateMaximumFeedback", this.termNominalInterestRateMaximumField);
        this.termNominalInterestRateMaximumIContainer.add(this.termNominalInterestRateMaximumFeedback);

        this.termNominalInterestRateTypeBlock = new WebMarkupBlock("termNominalInterestRateTypeBlock", Size.Three_3);
        this.form.add(this.termNominalInterestRateTypeBlock);
        this.termNominalInterestRateTypeIContainer = new WebMarkupContainer("termNominalInterestRateTypeIContainer");
        this.termNominalInterestRateTypeBlock.add(this.termNominalInterestRateTypeIContainer);
        this.termNominalInterestRateTypeProvider = new NominalInterestRateTypeProvider();
        this.termNominalInterestRateTypeField = new Select2SingleChoice<>("termNominalInterestRateTypeField", new PropertyModel<>(this.itemPage, "termNominalInterestRateTypeValue"), this.termNominalInterestRateTypeProvider);
        this.termNominalInterestRateTypeField.setLabel(Model.of("Type"));
        this.termNominalInterestRateTypeField.add(new OnChangeAjaxBehavior());
        this.termNominalInterestRateTypeIContainer.add(this.termNominalInterestRateTypeField);
        this.termNominalInterestRateTypeFeedback = new TextFeedbackPanel("termNominalInterestRateTypeFeedback", this.termNominalInterestRateTypeField);
        this.termNominalInterestRateTypeIContainer.add(this.termNominalInterestRateTypeFeedback);

        this.termFloatingInterestRateBlock = new WebMarkupBlock("termFloatingInterestRateBlock", Size.Four_4);
        this.form.add(this.termFloatingInterestRateBlock);
        this.termFloatingInterestRateIContainer = new WebMarkupContainer("termFloatingInterestRateIContainer");
        this.termFloatingInterestRateBlock.add(this.termFloatingInterestRateIContainer);
        this.termFloatingInterestRateProvider = new SingleChoiceProvider(MFloatingRates.NAME, MFloatingRates.Field.ID, MFloatingRates.Field.NAME);
        this.termFloatingInterestRateField = new Select2SingleChoice<>("termFloatingInterestRateField", new PropertyModel<>(this.itemPage, "termFloatingInterestRateValue"), this.termFloatingInterestRateProvider);
        this.termFloatingInterestRateField.setLabel(Model.of("Floating interest rate"));
        this.termFloatingInterestRateField.add(new OnChangeAjaxBehavior());
        this.termFloatingInterestRateIContainer.add(this.termFloatingInterestRateField);
        this.termFloatingInterestRateFeedback = new TextFeedbackPanel("termFloatingInterestRateFeedback", this.termFloatingInterestRateField);
        this.termFloatingInterestRateIContainer.add(this.termFloatingInterestRateFeedback);

        this.termFloatingInterestDifferentialBlock = new WebMarkupBlock("termFloatingInterestDifferentialBlock", Size.Four_4);
        this.form.add(this.termFloatingInterestDifferentialBlock);
        this.termFloatingInterestDifferentialIContainer = new WebMarkupContainer("termFloatingInterestDifferentialIContainer");
        this.termFloatingInterestDifferentialBlock.add(this.termFloatingInterestDifferentialIContainer);
        this.termFloatingInterestDifferentialField = new TextField<>("termFloatingInterestDifferentialField", new PropertyModel<>(this.itemPage, "termFloatingInterestDifferentialValue"));
        this.termFloatingInterestDifferentialField.setLabel(Model.of("Floating interest differential rate"));
        this.termFloatingInterestDifferentialField.add(new OnChangeAjaxBehavior());
        this.termFloatingInterestDifferentialIContainer.add(this.termFloatingInterestDifferentialField);
        this.termFloatingInterestDifferentialFeedback = new TextFeedbackPanel("termFloatingInterestDifferentialFeedback", this.termFloatingInterestDifferentialField);
        this.termFloatingInterestDifferentialIContainer.add(this.termFloatingInterestDifferentialFeedback);

        this.termFloatingInterestAllowedBlock = new WebMarkupBlock("termFloatingInterestAllowedBlock", Size.Four_4);
        this.form.add(this.termFloatingInterestAllowedBlock);
        this.termFloatingInterestAllowedIContainer = new WebMarkupContainer("termFloatingInterestAllowedIContainer");
        this.termFloatingInterestAllowedBlock.add(this.termFloatingInterestAllowedIContainer);
        this.termFloatingInterestAllowedField = new CheckBox("termFloatingInterestAllowedField", new PropertyModel<>(this.itemPage, "termFloatingInterestAllowedValue"));
        this.termFloatingInterestAllowedField.add(new OnChangeAjaxBehavior());
        this.termFloatingInterestAllowedIContainer.add(this.termFloatingInterestAllowedField);
        this.termFloatingInterestAllowedFeedback = new TextFeedbackPanel("termFloatingInterestAllowedFeedback", this.termFloatingInterestAllowedField);
        this.termFloatingInterestAllowedIContainer.add(this.termFloatingInterestAllowedFeedback);

        this.termFloatingInterestMinimumBlock = new WebMarkupBlock("termFloatingInterestMinimumBlock", Size.Four_4);
        this.form.add(this.termFloatingInterestMinimumBlock);
        this.termFloatingInterestMinimumIContainer = new WebMarkupContainer("termFloatingInterestMinimumIContainer");
        this.termFloatingInterestMinimumBlock.add(this.termFloatingInterestMinimumIContainer);
        this.termFloatingInterestMinimumField = new TextField<>("termFloatingInterestMinimumField", new PropertyModel<>(this.itemPage, "termFloatingInterestMinimumValue"));
        this.termFloatingInterestMinimumField.setLabel(Model.of("Floating interest minimum"));
        this.termFloatingInterestMinimumField.add(new OnChangeAjaxBehavior());
        this.termFloatingInterestMinimumIContainer.add(this.termFloatingInterestMinimumField);
        this.termFloatingInterestMinimumFeedback = new TextFeedbackPanel("termFloatingInterestMinimumFeedback", this.termFloatingInterestMinimumField);
        this.termFloatingInterestMinimumIContainer.add(this.termFloatingInterestMinimumFeedback);

        this.termFloatingInterestDefaultBlock = new WebMarkupBlock("termFloatingInterestDefaultBlock", Size.Four_4);
        this.form.add(this.termFloatingInterestDefaultBlock);
        this.termFloatingInterestDefaultIContainer = new WebMarkupContainer("termFloatingInterestDefaultIContainer");
        this.termFloatingInterestDefaultBlock.add(this.termFloatingInterestDefaultIContainer);
        this.termFloatingInterestDefaultField = new TextField<>("termFloatingInterestDefaultField", new PropertyModel<>(this.itemPage, "termFloatingInterestDefaultValue"));
        this.termFloatingInterestDefaultField.setLabel(Model.of("Floating interest Default"));
        this.termFloatingInterestDefaultField.add(new OnChangeAjaxBehavior());
        this.termFloatingInterestDefaultIContainer.add(this.termFloatingInterestDefaultField);
        this.termFloatingInterestDefaultFeedback = new TextFeedbackPanel("termFloatingInterestDefaultFeedback", this.termFloatingInterestDefaultField);
        this.termFloatingInterestDefaultIContainer.add(this.termFloatingInterestDefaultFeedback);

        this.termFloatingInterestMaximumBlock = new WebMarkupBlock("termFloatingInterestMaximumBlock", Size.Four_4);
        this.form.add(this.termFloatingInterestMaximumBlock);
        this.termFloatingInterestMaximumIContainer = new WebMarkupContainer("termFloatingInterestMaximumIContainer");
        this.termFloatingInterestMaximumBlock.add(this.termFloatingInterestMaximumIContainer);
        this.termFloatingInterestMaximumField = new TextField<>("termFloatingInterestMaximumField", new PropertyModel<>(this.itemPage, "termFloatingInterestMaximumValue"));
        this.termFloatingInterestMaximumField.setLabel(Model.of("Floating interest Maximum"));
        this.termFloatingInterestMaximumField.add(new OnChangeAjaxBehavior());
        this.termFloatingInterestMaximumIContainer.add(this.termFloatingInterestMaximumField);
        this.termFloatingInterestMaximumFeedback = new TextFeedbackPanel("termFloatingInterestMaximumFeedback", this.termFloatingInterestMaximumField);
        this.termFloatingInterestMaximumIContainer.add(this.termFloatingInterestMaximumFeedback);

        this.termNominalInterestRateByLoanCycleBlock = new WebMarkupBlock("termNominalInterestRateByLoanCycleBlock", Size.Twelve_12);
        this.form.add(this.termNominalInterestRateByLoanCycleBlock);
        this.termNominalInterestRateByLoanCycleIContainer = new WebMarkupContainer("termNominalInterestRateByLoanCycleIContainer");
        this.termNominalInterestRateByLoanCycleBlock.add(this.termNominalInterestRateByLoanCycleIContainer);
        this.termNominalInterestRateByLoanCycleTable = new DataTable<>("termNominalInterestRateByLoanCycleTable", this.termNominalInterestRateByLoanCycleColumn, this.termNominalInterestRateByLoanCycleProvider, 20);
        this.termNominalInterestRateByLoanCycleIContainer.add(this.termNominalInterestRateByLoanCycleTable);
        this.termNominalInterestRateByLoanCycleTable.addTopToolbar(new HeadersToolbar<>(this.termNominalInterestRateByLoanCycleTable, this.termNominalInterestRateByLoanCycleProvider));
        this.termNominalInterestRateByLoanCycleTable.addBottomToolbar(new NoRecordsToolbar(this.termNominalInterestRateByLoanCycleTable));
        this.termNominalInterestRateByLoanCycleAddLink = new AjaxLink<>("termNominalInterestRateByLoanCycleAddLink");
        this.termNominalInterestRateByLoanCycleAddLink.setOnClick(this::termNominalInterestRateByLoanCycleAddLinkClick);
        this.termNominalInterestRateByLoanCycleIContainer.add(this.termNominalInterestRateByLoanCycleAddLink);

        this.termRepaidEveryBlock = new WebMarkupBlock("termRepaidEveryBlock", Size.One_1);
        this.form.add(this.termRepaidEveryBlock);
        this.termRepaidEveryIContainer = new WebMarkupContainer("termRepaidEveryIContainer");
        this.termRepaidEveryBlock.add(this.termRepaidEveryIContainer);
        this.termRepaidEveryField = new TextField<>("termRepaidEveryField", new PropertyModel<>(this.itemPage, "termRepaidEveryValue"));
        this.termRepaidEveryField.setLabel(Model.of("Repaid every"));
        this.termRepaidEveryField.add(new OnChangeAjaxBehavior());
        this.termRepaidEveryIContainer.add(this.termRepaidEveryField);
        this.termRepaidEveryFeedback = new TextFeedbackPanel("termRepaidEveryFeedback", this.termRepaidEveryField);
        this.termRepaidEveryIContainer.add(this.termRepaidEveryFeedback);

        this.termRepaidTypeBlock = new WebMarkupBlock("termRepaidTypeBlock", Size.Two_2);
        this.termRepaidTypeProvider = new LockInTypeProvider(LockInType.Day, LockInType.Week, LockInType.Month);
        this.form.add(this.termRepaidTypeBlock);
        this.termRepaidTypeIContainer = new WebMarkupContainer("termRepaidTypeIContainer");
        this.termRepaidTypeBlock.add(this.termRepaidTypeIContainer);
        this.termRepaidTypeField = new Select2SingleChoice<>("termRepaidTypeField", new PropertyModel<>(this.itemPage, "termRepaidTypeValue"), this.termRepaidTypeProvider);
        this.termRepaidTypeField.setLabel(Model.of("Repaid Type"));
        this.termRepaidTypeField.add(new OnChangeAjaxBehavior());
        this.termRepaidTypeIContainer.add(this.termRepaidTypeField);
        this.termRepaidTypeFeedback = new TextFeedbackPanel("termRepaidTypeFeedback", this.termRepaidTypeField);
        this.termRepaidTypeIContainer.add(this.termRepaidTypeFeedback);

        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock = new WebMarkupBlock("termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock", Size.Six_6);
        this.form.add(this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock);
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateIContainer = new WebMarkupContainer("termMinimumDayBetweenDisbursalAndFirstRepaymentDateIContainer");
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock.add(this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateIContainer);
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateField = new TextField<>("termMinimumDayBetweenDisbursalAndFirstRepaymentDateField", new PropertyModel<>(this.itemPage, "termMinimumDayBetweenDisbursalAndFirstRepaymentDateValue"));
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateField.setLabel(Model.of("Minimum days between disbursal and first repayment date"));
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateField.add(new OnChangeAjaxBehavior());
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateIContainer.add(this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateField);
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateFeedback = new TextFeedbackPanel("termMinimumDayBetweenDisbursalAndFirstRepaymentDateFeedback", this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateField);
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateIContainer.add(this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateFeedback);

    }

    protected void modalWindowClose(String popupName, String signalId, AjaxRequestTarget target) {
        if (popupName.equals("termPrincipleByLoanCyclePopup")) {
            StringGenerator generator = SpringBean.getBean(StringGenerator.class);
            Map<String, Object> item = Maps.newHashMap();
            item.put("uuid", generator.externalId());
            item.put("when", this.popupModel.get("whenValue"));
            item.put("cycle", this.popupModel.get("loanCycleValue"));
            item.put("minimum", this.popupModel.get("minimumValue"));
            item.put("default", this.popupModel.get("defaultValue"));
            item.put("maximum", this.popupModel.get("maximumValue"));
            this.termPrincipleByLoanCycleValue.getObject().add(item);
            target.add(this.termPrincipleByLoanCycleBlock);
        } else if ("termNumberOfRepaymentByLoanCyclePopup".equals(popupName)) {
            StringGenerator generator = SpringBean.getBean(StringGenerator.class);
            Map<String, Object> item = Maps.newHashMap();
            item.put("uuid", generator.externalId());
            item.put("when", this.popupModel.get("whenValue"));
            item.put("cycle", this.popupModel.get("loanCycleValue"));
            item.put("minimum", this.popupModel.get("minimumValue"));
            item.put("default", this.popupModel.get("defaultValue"));
            item.put("maximum", this.popupModel.get("maximumValue"));
            this.termNumberOfRepaymentByLoanCycleValue.getObject().add(item);
            target.add(this.termNumberOfRepaymentByLoanCycleBlock);
        } else if ("termNominalInterestRateByLoanCyclePopup".equals(popupName)) {
            StringGenerator generator = SpringBean.getBean(StringGenerator.class);
            Map<String, Object> item = Maps.newHashMap();
            item.put("uuid", generator.externalId());
            item.put("when", this.popupModel.get("whenValue"));
            item.put("cycle", this.popupModel.get("loanCycleValue"));
            item.put("minimum", this.popupModel.get("minimumValue"));
            item.put("default", this.popupModel.get("defaultValue"));
            item.put("maximum", this.popupModel.get("maximumValue"));
            this.termNominalInterestRateByLoanCycleValue.getObject().add(item);
            target.add(this.termNominalInterestRateByLoanCycleBlock);
        }
    }

    protected boolean termVaryBasedOnLoanCycleFieldUpdate(AjaxRequestTarget target) {
        PropertyModel<Boolean> termVaryBasedOnLoanCycleValue = new PropertyModel<>(this.itemPage, "termVaryBasedOnLoanCycleValue");
        boolean visible = termVaryBasedOnLoanCycleValue.getObject() == null ? false : termVaryBasedOnLoanCycleValue.getObject();
        this.termPrincipleByLoanCycleIContainer.setVisible(visible);
        this.termNumberOfRepaymentByLoanCycleIContainer.setVisible(visible);
        this.termNominalInterestRateByLoanCycleIContainer.setVisible(visible);
        if (target != null) {
            target.add(this.termPrincipleByLoanCycleBlock);
            target.add(this.termNumberOfRepaymentByLoanCycleBlock);
            target.add(this.termNominalInterestRateByLoanCycleBlock);
        }
        return false;
    }

    protected boolean termNominalInterestRateByLoanCycleAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        this.modalWindow.setContent(new InterestLoanCyclePopup("termNominalInterestRateByLoanCyclePopup", this.popupModel));
        this.modalWindow.show(target);
        return false;
    }

    protected ItemPanel termNominalInterestRateByLoanCycleColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("when".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        } else if ("cycle".equals(column)) {
            Long value = (Long) model.get(column);
            return new TextCell(value);
        } else if ("minimum".equals(column) || "default".equals(column) || "maximum".equals(column)) {
            Double value = (Double) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected void termNominalInterestRateByLoanCycleActionClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        int index = -1;
        for (int i = 0; i < this.termNominalInterestRateByLoanCycleValue.getObject().size(); i++) {
            Map<String, Object> column = this.termNominalInterestRateByLoanCycleValue.getObject().get(i);
            if (model.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.termNominalInterestRateByLoanCycleValue.getObject().remove(index);
        }
        target.add(this.termNominalInterestRateByLoanCycleTable);
    }

    protected List<ActionItem> termNominalInterestRateByLoanCycleActionItem(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    protected ItemPanel termNumberOfRepaymentByLoanCycleColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("when".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        } else if ("cycle".equals(column)) {
            Long value = (Long) model.get(column);
            return new TextCell(value);
        } else if ("minimum".equals(column) || "default".equals(column) || "maximum".equals(column)) {
            Double value = (Double) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected boolean termNumberOfRepaymentByLoanCycleAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        this.modalWindow.setContent(new RepaymentLoanCyclePopup("termNumberOfRepaymentByLoanCyclePopup", this.popupModel));
        this.modalWindow.show(target);
        return false;
    }

    protected void termNumberOfRepaymentByLoanCycleClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        int index = -1;
        for (int i = 0; i < this.termNumberOfRepaymentByLoanCycleValue.getObject().size(); i++) {
            Map<String, Object> column = this.termNumberOfRepaymentByLoanCycleValue.getObject().get(i);
            if (model.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.termNumberOfRepaymentByLoanCycleValue.getObject().remove(index);
        }
        target.add(this.termNumberOfRepaymentByLoanCycleTable);
    }

    protected List<ActionItem> termNumberOfRepaymentByLoanCycleAction(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    protected boolean termPrincipleByLoanCycleAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        this.modalWindow.setContent(new PrincipleLoanCyclePopup("termPrincipleByLoanCyclePopup", this.popupModel));
        this.modalWindow.show(target);
        return false;
    }

    protected ItemPanel termPrincipleByLoanCycleColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("when".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        } else if ("cycle".equals(column)) {
            Long value = (Long) model.get(column);
            return new TextCell(value);
        } else if ("minimum".equals(column) || "default".equals(column) || "maximum".equals(column)) {
            Double value = (Double) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected void termPrincipleByLoanCycleClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        int index = -1;
        for (int i = 0; i < this.termPrincipleByLoanCycleValue.getObject().size(); i++) {
            Map<String, Object> column = this.termPrincipleByLoanCycleValue.getObject().get(i);
            if (model.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.termPrincipleByLoanCycleValue.getObject().remove(index);
        }
        target.add(this.termPrincipleByLoanCycleTable);
    }

    protected List<ActionItem> termPrincipleByLoanCycleAction(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    protected boolean termLinkedToFloatingInterestRatesFieldUpdate(AjaxRequestTarget target) {
        PropertyModel<Boolean> termLinkedToFloatingInterestRatesValue = new PropertyModel<>(this.itemPage, "termLinkedToFloatingInterestRatesValue");
        boolean notLinked = termLinkedToFloatingInterestRatesValue.getObject() == null ? true : !termLinkedToFloatingInterestRatesValue.getObject();
        this.termNominalInterestRateMinimumIContainer.setVisible(notLinked);
        this.termNominalInterestRateDefaultIContainer.setVisible(notLinked);
        this.termNominalInterestRateMaximumIContainer.setVisible(notLinked);
        this.termNominalInterestRateTypeIContainer.setVisible(notLinked);

        this.termFloatingInterestRateIContainer.setVisible(!notLinked);
        this.termFloatingInterestDifferentialIContainer.setVisible(!notLinked);
        this.termFloatingInterestAllowedIContainer.setVisible(!notLinked);
        this.termFloatingInterestMinimumIContainer.setVisible(!notLinked);
        this.termFloatingInterestDefaultIContainer.setVisible(!notLinked);
        this.termFloatingInterestMaximumIContainer.setVisible(!notLinked);

        if (target != null) {
            target.add(this.termNominalInterestRateMinimumBlock);
            target.add(this.termNominalInterestRateDefaultBlock);
            target.add(this.termNominalInterestRateMaximumBlock);
            target.add(this.termNominalInterestRateTypeBlock);
            target.add(this.termFloatingInterestRateBlock);
            target.add(this.termFloatingInterestDifferentialBlock);
            target.add(this.termFloatingInterestAllowedBlock);
            target.add(this.termFloatingInterestMinimumBlock);
            target.add(this.termFloatingInterestDefaultBlock);
            target.add(this.termFloatingInterestMaximumBlock);
        }
        return false;
    }

    protected void nextButtonSubmit(Button button) {
        this.tab.getObject().setSelectedTab(LoanCreatePage.TAB_SETTING);
        this.errorTerm.setObject(false);
    }

    protected void nextButtonError(Button button) {
        this.errorTerm.setObject(true);
    }

    protected boolean backLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.tab.getObject().setSelectedTab(LoanCreatePage.TAB_CURRENCY);
        if (target != null) {
            target.add(this.tab.getObject());
        }
        return false;
    }

    @Override
    protected void configureMetaData() {
        this.termPrincipleDefaultField.setRequired(true);
        this.termNumberOfRepaymentDefaultField.setRequired(true);
        this.termRepaidEveryField.setRequired(true);
        this.termRepaidTypeField.setRequired(true);
        this.termNominalInterestRateDefaultField.setRequired(true);
        this.termNominalInterestRateTypeField.setRequired(true);

        termVaryBasedOnLoanCycleFieldUpdate(null);

        termLinkedToFloatingInterestRatesFieldUpdate(null);
    }

}
