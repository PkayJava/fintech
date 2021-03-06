package com.angkorteam.fintech.widget.product.loan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.RangeValidator;

import com.angkorteam.fintech.ddl.MFloatingRates;
import com.angkorteam.fintech.dto.enums.LockInType;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
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
import com.angkorteam.framework.wicket.markup.html.form.validation.LamdaFormValidator;
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

    protected UIRow row1;

    // Row 1 : Terms vary based on loan cycle
    protected UIBlock termVaryBasedOnLoanCycleBlock;
    protected UIContainer termVaryBasedOnLoanCycleIContainer;
    protected CheckBox termVaryBasedOnLoanCycleField;

    protected UIRow row2;

    // Row 2 : Principle
    protected UIBlock termPrincipalMinimumBlock;
    protected UIContainer termPrincipalMinimumIContainer;
    protected TextField<Double> termPrincipalMinimumField;
    protected PropertyModel<Double> termPrincipalMinimumValue;

    protected UIBlock termPrincipalDefaultBlock;
    protected UIContainer termPrincipalDefaultIContainer;
    protected TextField<Double> termPrincipalDefaultField;
    protected PropertyModel<Double> termPrincipalDefaultValue;

    protected UIBlock termPrincipalMaximumBlock;
    protected UIContainer termPrincipalMaximumIContainer;
    protected TextField<Double> termPrincipalMaximumField;
    protected PropertyModel<Double> termPrincipalMaximumValue;

    protected UIBlock row2Block1;

    protected UIRow row3;

    // Row 2 (Optional) : Principle by loan cycle
    protected UIBlock termPrincipleByLoanCycleBlock;
    protected UIContainer termPrincipleByLoanCycleIContainer;
    protected PropertyModel<List<Map<String, Object>>> termPrincipleByLoanCycleValue;
    protected DataTable<Map<String, Object>, String> termPrincipleByLoanCycleTable;
    protected List<IColumn<Map<String, Object>, String>> termPrincipleByLoanCycleColumn;
    protected ListDataProvider termPrincipleByLoanCycleProvider;
    protected AjaxLink<Void> termPrincipleByLoanCycleAddLink;

    protected UIRow row4;

    // Row 3 : Number of repayments
    protected UIBlock termNumberOfRepaymentMinimumBlock;
    protected UIContainer termNumberOfRepaymentMinimumIContainer;
    protected TextField<Long> termNumberOfRepaymentMinimumField;
    protected PropertyModel<Long> termNumberOfRepaymentMinimumValue;

    protected UIBlock termNumberOfRepaymentDefaultBlock;
    protected UIContainer termNumberOfRepaymentDefaultIContainer;
    protected TextField<Long> termNumberOfRepaymentDefaultField;
    protected PropertyModel<Long> termNumberOfRepaymentDefaultValue;

    protected UIBlock termNumberOfRepaymentMaximumBlock;
    protected UIContainer termNumberOfRepaymentMaximumIContainer;
    protected TextField<Long> termNumberOfRepaymentMaximumField;
    protected PropertyModel<Long> termNumberOfRepaymentMaximumValue;

    protected UIBlock termRepaidEveryBlock;
    protected UIContainer termRepaidEveryIContainer;
    protected TextField<Long> termRepaidEveryField;

    protected UIBlock termRepaidTypeBlock;
    protected UIContainer termRepaidTypeIContainer;
    protected LockInTypeProvider termRepaidTypeProvider;
    protected Select2SingleChoice<Option> termRepaidTypeField;

    protected UIRow row5;

    // Row 3 (Optional) : Number of Repayments by loan cycle
    protected List<IColumn<Map<String, Object>, String>> termNumberOfRepaymentByLoanCycleColumn;
    protected UIBlock termNumberOfRepaymentByLoanCycleBlock;
    protected UIContainer termNumberOfRepaymentByLoanCycleIContainer;
    protected PropertyModel<List<Map<String, Object>>> termNumberOfRepaymentByLoanCycleValue;
    protected DataTable<Map<String, Object>, String> termNumberOfRepaymentByLoanCycleTable;
    protected ListDataProvider termNumberOfRepaymentByLoanCycleProvider;
    protected AjaxLink<Void> termNumberOfRepaymentByLoanCycleAddLink;

    protected UIRow row6;

    // Row 4 : Is Linked to Floating Interest Rates?
    protected UIBlock termLinkedToFloatingInterestRatesBlock;
    protected UIContainer termLinkedToFloatingInterestRatesIContainer;
    protected CheckBox termLinkedToFloatingInterestRatesField;

    protected UIRow row7;

    // Row 5 : Nominal interest rate
    protected UIBlock termNominalInterestRateMinimumBlock;
    protected UIContainer termNominalInterestRateMinimumIContainer;
    protected TextField<Double> termNominalInterestRateMinimumField;
    protected PropertyModel<Double> termNominalInterestRateMinimumValue;

    protected UIBlock termNominalInterestRateDefaultBlock;
    protected UIContainer termNominalInterestRateDefaultIContainer;
    protected TextField<Double> termNominalInterestRateDefaultField;
    protected PropertyModel<Double> termNominalInterestRateDefaultValue;

    protected UIBlock termNominalInterestRateMaximumBlock;
    protected UIContainer termNominalInterestRateMaximumIContainer;
    protected TextField<Double> termNominalInterestRateMaximumField;
    protected PropertyModel<Double> termNominalInterestRateMaximumValue;

    protected UIBlock termNominalInterestRateTypeBlock;
    protected UIContainer termNominalInterestRateTypeIContainer;
    protected NominalInterestRateTypeProvider termNominalInterestRateTypeProvider;
    protected Select2SingleChoice<Option> termNominalInterestRateTypeField;

    protected UIRow row8;

    protected UIBlock termFloatingInterestRateBlock;
    protected UIContainer termFloatingInterestRateIContainer;
    protected SingleChoiceProvider termFloatingInterestRateProvider;
    protected Select2SingleChoice<Option> termFloatingInterestRateField;

    protected UIBlock termFloatingInterestDifferentialBlock;
    protected UIContainer termFloatingInterestDifferentialIContainer;
    protected TextField<Double> termFloatingInterestDifferentialField;

    protected UIBlock termFloatingInterestAllowedBlock;
    protected UIContainer termFloatingInterestAllowedIContainer;
    protected CheckBox termFloatingInterestAllowedField;

    protected UIRow row9;

    protected UIBlock termFloatingInterestMinimumBlock;
    protected UIContainer termFloatingInterestMinimumIContainer;
    protected TextField<Double> termFloatingInterestMinimumField;
    protected PropertyModel<Double> termFloatingInterestMinimumValue;

    protected UIBlock termFloatingInterestDefaultBlock;
    protected UIContainer termFloatingInterestDefaultIContainer;
    protected TextField<Double> termFloatingInterestDefaultField;
    protected PropertyModel<Double> termFloatingInterestDefaultValue;

    protected UIBlock termFloatingInterestMaximumBlock;
    protected UIContainer termFloatingInterestMaximumIContainer;
    protected TextField<Double> termFloatingInterestMaximumField;
    protected PropertyModel<Double> termFloatingInterestMaximumValue;

    protected UIRow row10;

    // Row 6
    protected UIBlock termNominalInterestRateByLoanCycleBlock;
    protected UIContainer termNominalInterestRateByLoanCycleIContainer;
    protected PropertyModel<List<Map<String, Object>>> termNominalInterestRateByLoanCycleValue;
    protected DataTable<Map<String, Object>, String> termNominalInterestRateByLoanCycleTable;
    protected ListDataProvider termNominalInterestRateByLoanCycleProvider;
    protected AjaxLink<Void> termNominalInterestRateByLoanCycleAddLink;
    protected List<IColumn<Map<String, Object>, String>> termNominalInterestRateByLoanCycleColumn;

    protected UIRow row11;

    protected UIBlock termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock;
    protected UIContainer termMinimumDayBetweenDisbursalAndFirstRepaymentDateIContainer;
    protected TextField<Long> termMinimumDayBetweenDisbursalAndFirstRepaymentDateField;

    protected UIBlock row11Block1;

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

        this.termRepaidTypeProvider = new LockInTypeProvider(LockInType.Day, LockInType.Week, LockInType.Month);
        this.termNominalInterestRateTypeProvider = new NominalInterestRateTypeProvider();
        this.termFloatingInterestRateProvider = new SingleChoiceProvider(MFloatingRates.NAME, MFloatingRates.Field.ID, MFloatingRates.Field.NAME);

        this.termPrincipalMinimumValue = new PropertyModel<>(this.itemPage, "termPrincipalMinimumValue");
        this.termPrincipalDefaultValue = new PropertyModel<>(this.itemPage, "termPrincipalDefaultValue");
        this.termPrincipalMaximumValue = new PropertyModel<>(this.itemPage, "termPrincipalMaximumValue");

        this.termNumberOfRepaymentMinimumValue = new PropertyModel<>(this.itemPage, "termNumberOfRepaymentMinimumValue");
        this.termNumberOfRepaymentDefaultValue = new PropertyModel<>(this.itemPage, "termNumberOfRepaymentDefaultValue");
        this.termNumberOfRepaymentMaximumValue = new PropertyModel<>(this.itemPage, "termNumberOfRepaymentMaximumValue");

        this.termNominalInterestRateMinimumValue = new PropertyModel<>(this.itemPage, "termNominalInterestRateMinimumValue");
        this.termNominalInterestRateMaximumValue = new PropertyModel<>(this.itemPage, "termNominalInterestRateMaximumValue");
        this.termNominalInterestRateDefaultValue = new PropertyModel<>(this.itemPage, "termNominalInterestRateDefaultValue");

        this.termFloatingInterestMinimumValue = new PropertyModel<>(this.itemPage, "termFloatingInterestMinimumValue");
        this.termFloatingInterestDefaultValue = new PropertyModel<>(this.itemPage, "termFloatingInterestDefaultValue");
        this.termFloatingInterestMaximumValue = new PropertyModel<>(this.itemPage, "termFloatingInterestMaximumValue");
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

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.termVaryBasedOnLoanCycleBlock = this.row1.newUIBlock("termVaryBasedOnLoanCycleBlock", Size.Twelve_12);
        this.termVaryBasedOnLoanCycleIContainer = this.termVaryBasedOnLoanCycleBlock.newUIContainer("termVaryBasedOnLoanCycleIContainer");
        this.termVaryBasedOnLoanCycleField = new CheckBox("termVaryBasedOnLoanCycleField", new PropertyModel<>(this.itemPage, "termVaryBasedOnLoanCycleValue"));
        this.termVaryBasedOnLoanCycleIContainer.add(this.termVaryBasedOnLoanCycleField);
        this.termVaryBasedOnLoanCycleIContainer.newFeedback("termVaryBasedOnLoanCycleFeedback", this.termVaryBasedOnLoanCycleField);

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.termPrincipalMinimumBlock = this.row2.newUIBlock("termPrincipalMinimumBlock", Size.Three_3);
        this.termPrincipalMinimumIContainer = this.termPrincipalMinimumBlock.newUIContainer("termPrincipalMinimumIContainer");
        this.termPrincipalMinimumField = new TextField<>("termPrincipalMinimumField", this.termPrincipalMinimumValue);
        this.termPrincipalMinimumIContainer.add(this.termPrincipalMinimumField);
        this.termPrincipalMinimumIContainer.newFeedback("termPrincipalMinimumFeedback", this.termPrincipalMinimumField);

        this.termPrincipalDefaultBlock = this.row2.newUIBlock("termPrincipalDefaultBlock", Size.Three_3);
        this.termPrincipalDefaultIContainer = this.termPrincipalDefaultBlock.newUIContainer("termPrincipalDefaultIContainer");
        this.termPrincipalDefaultField = new TextField<>("termPrincipalDefaultField", this.termPrincipalDefaultValue);
        this.termPrincipalDefaultIContainer.add(this.termPrincipalDefaultField);
        this.termPrincipalDefaultIContainer.newFeedback("termPrincipalDefaultFeedback", this.termPrincipalDefaultField);

        this.termPrincipalMaximumBlock = this.row2.newUIBlock("termPrincipalMaximumBlock", Size.Three_3);
        this.termPrincipalMaximumIContainer = this.termPrincipalMaximumBlock.newUIContainer("termPrincipalMaximumIContainer");
        this.termPrincipalMaximumField = new TextField<>("termPrincipalMaximumField", this.termPrincipalMaximumValue);
        this.termPrincipalMaximumIContainer.add(this.termPrincipalMaximumField);
        this.termPrincipalMaximumIContainer.newFeedback("termPrincipalMaximumFeedback", this.termPrincipalMaximumField);

        this.row2Block1 = this.row2.newUIBlock("row2Block1", Size.Three_3);

        this.row3 = UIRow.newUIRow("row3", this.form);

        this.termPrincipleByLoanCycleBlock = this.row3.newUIBlock("termPrincipleByLoanCycleBlock", Size.Twelve_12);
        this.termPrincipleByLoanCycleIContainer = this.termPrincipleByLoanCycleBlock.newUIContainer("termPrincipleByLoanCycleIContainer");
        this.termPrincipleByLoanCycleTable = new DataTable<>("termPrincipleByLoanCycleTable", this.termPrincipleByLoanCycleColumn, this.termPrincipleByLoanCycleProvider, 20);
        this.termPrincipleByLoanCycleIContainer.add(this.termPrincipleByLoanCycleTable);
        this.termPrincipleByLoanCycleTable.addTopToolbar(new HeadersToolbar<>(this.termPrincipleByLoanCycleTable, this.termPrincipleByLoanCycleProvider));
        this.termPrincipleByLoanCycleTable.addBottomToolbar(new NoRecordsToolbar(this.termPrincipleByLoanCycleTable));
        this.termPrincipleByLoanCycleAddLink = new AjaxLink<>("termPrincipleByLoanCycleAddLink");
        this.termPrincipleByLoanCycleAddLink.setOnClick(this::termPrincipleByLoanCycleAddLinkClick);
        this.termPrincipleByLoanCycleIContainer.add(this.termPrincipleByLoanCycleAddLink);

        this.row4 = UIRow.newUIRow("row4", this.form);

        this.termNumberOfRepaymentMinimumBlock = this.row4.newUIBlock("termNumberOfRepaymentMinimumBlock", Size.Three_3);
        this.termNumberOfRepaymentMinimumIContainer = this.termNumberOfRepaymentMinimumBlock.newUIContainer("termNumberOfRepaymentMinimumIContainer");
        this.termNumberOfRepaymentMinimumField = new TextField<>("termNumberOfRepaymentMinimumField", this.termNumberOfRepaymentMinimumValue);
        this.termNumberOfRepaymentMinimumIContainer.add(this.termNumberOfRepaymentMinimumField);
        this.termNumberOfRepaymentMinimumIContainer.newFeedback("termNumberOfRepaymentMinimumFeedback", this.termNumberOfRepaymentMinimumField);

        this.termNumberOfRepaymentDefaultBlock = this.row4.newUIBlock("termNumberOfRepaymentDefaultBlock", Size.Three_3);
        this.termNumberOfRepaymentDefaultIContainer = this.termNumberOfRepaymentDefaultBlock.newUIContainer("termNumberOfRepaymentDefaultIContainer");
        this.termNumberOfRepaymentDefaultField = new TextField<>("termNumberOfRepaymentDefaultField", this.termNumberOfRepaymentDefaultValue);
        this.termNumberOfRepaymentDefaultIContainer.add(this.termNumberOfRepaymentDefaultField);
        this.termNumberOfRepaymentDefaultIContainer.newFeedback("termNumberOfRepaymentDefaultFeedback", this.termNumberOfRepaymentDefaultField);

        this.termNumberOfRepaymentMaximumBlock = this.row4.newUIBlock("termNumberOfRepaymentMaximumBlock", Size.Three_3);
        this.termNumberOfRepaymentMaximumIContainer = this.termNumberOfRepaymentMaximumBlock.newUIContainer("termNumberOfRepaymentMaximumIContainer");
        this.termNumberOfRepaymentMaximumField = new TextField<>("termNumberOfRepaymentMaximumField", this.termNumberOfRepaymentMaximumValue);
        this.termNumberOfRepaymentMaximumIContainer.add(this.termNumberOfRepaymentMaximumField);
        this.termNumberOfRepaymentMaximumIContainer.newFeedback("termNumberOfRepaymentMaximumFeedback", this.termNumberOfRepaymentMaximumField);

        this.termRepaidEveryBlock = this.row4.newUIBlock("termRepaidEveryBlock", Size.One_1);
        this.termRepaidEveryIContainer = this.termRepaidEveryBlock.newUIContainer("termRepaidEveryIContainer");
        this.termRepaidEveryField = new TextField<>("termRepaidEveryField", new PropertyModel<>(this.itemPage, "termRepaidEveryValue"));
        this.termRepaidEveryIContainer.add(this.termRepaidEveryField);
        this.termRepaidEveryIContainer.newFeedback("termRepaidEveryFeedback", this.termRepaidEveryField);

        this.termRepaidTypeBlock = this.row4.newUIBlock("termRepaidTypeBlock", Size.Two_2);
        this.termRepaidTypeIContainer = this.termRepaidTypeBlock.newUIContainer("termRepaidTypeIContainer");
        this.termRepaidTypeField = new Select2SingleChoice<>("termRepaidTypeField", new PropertyModel<>(this.itemPage, "termRepaidTypeValue"), this.termRepaidTypeProvider);
        this.termRepaidTypeIContainer.add(this.termRepaidTypeField);
        this.termRepaidTypeIContainer.newFeedback("termRepaidTypeFeedback", this.termRepaidTypeField);

        this.row5 = UIRow.newUIRow("row5", this.form);

        this.termNumberOfRepaymentByLoanCycleBlock = this.row5.newUIBlock("termNumberOfRepaymentByLoanCycleBlock", Size.Twelve_12);
        this.termNumberOfRepaymentByLoanCycleIContainer = this.termNumberOfRepaymentByLoanCycleBlock.newUIContainer("termNumberOfRepaymentByLoanCycleIContainer");
        this.termNumberOfRepaymentByLoanCycleTable = new DataTable<>("termNumberOfRepaymentByLoanCycleTable", this.termNumberOfRepaymentByLoanCycleColumn, this.termNumberOfRepaymentByLoanCycleProvider, 20);
        this.termNumberOfRepaymentByLoanCycleIContainer.add(this.termNumberOfRepaymentByLoanCycleTable);
        this.termNumberOfRepaymentByLoanCycleTable.addTopToolbar(new HeadersToolbar<>(this.termNumberOfRepaymentByLoanCycleTable, this.termNumberOfRepaymentByLoanCycleProvider));
        this.termNumberOfRepaymentByLoanCycleTable.addBottomToolbar(new NoRecordsToolbar(this.termNumberOfRepaymentByLoanCycleTable));
        this.termNumberOfRepaymentByLoanCycleAddLink = new AjaxLink<>("termNumberOfRepaymentByLoanCycleAddLink");
        this.termNumberOfRepaymentByLoanCycleAddLink.setOnClick(this::termNumberOfRepaymentByLoanCycleAddLinkClick);
        this.termNumberOfRepaymentByLoanCycleIContainer.add(this.termNumberOfRepaymentByLoanCycleAddLink);

        this.row6 = UIRow.newUIRow("row6", this.form);

        // Linked to Floating Interest Rates
        this.termLinkedToFloatingInterestRatesBlock = this.row6.newUIBlock("termLinkedToFloatingInterestRatesBlock", Size.Twelve_12);
        this.termLinkedToFloatingInterestRatesIContainer = this.termLinkedToFloatingInterestRatesBlock.newUIContainer("termLinkedToFloatingInterestRatesIContainer");
        this.termLinkedToFloatingInterestRatesField = new CheckBox("termLinkedToFloatingInterestRatesField", new PropertyModel<>(this.itemPage, "termLinkedToFloatingInterestRatesValue"));
        this.termLinkedToFloatingInterestRatesIContainer.add(this.termLinkedToFloatingInterestRatesField);
        this.termLinkedToFloatingInterestRatesIContainer.newFeedback("termLinkedToFloatingInterestRatesFeedback", this.termLinkedToFloatingInterestRatesField);

        this.row7 = UIRow.newUIRow("row7", this.form);

        this.termNominalInterestRateMinimumBlock = this.row7.newUIBlock("termNominalInterestRateMinimumBlock", Size.Three_3);
        this.termNominalInterestRateMinimumIContainer = this.termNominalInterestRateMinimumBlock.newUIContainer("termNominalInterestRateMinimumIContainer");
        this.termNominalInterestRateMinimumField = new TextField<>("termNominalInterestRateMinimumField", this.termNominalInterestRateMinimumValue);
        this.termNominalInterestRateMinimumIContainer.add(this.termNominalInterestRateMinimumField);
        this.termNominalInterestRateMinimumIContainer.newFeedback("termNominalInterestRateMinimumFeedback", this.termNominalInterestRateMinimumField);

        this.termNominalInterestRateDefaultBlock = this.row7.newUIBlock("termNominalInterestRateDefaultBlock", Size.Three_3);
        this.termNominalInterestRateDefaultIContainer = this.termNominalInterestRateDefaultBlock.newUIContainer("termNominalInterestRateDefaultIContainer");
        this.termNominalInterestRateDefaultField = new TextField<>("termNominalInterestRateDefaultField", this.termNominalInterestRateDefaultValue);
        this.termNominalInterestRateDefaultIContainer.add(this.termNominalInterestRateDefaultField);
        this.termNominalInterestRateDefaultIContainer.newFeedback("termNominalInterestRateDefaultFeedback", this.termNominalInterestRateDefaultField);

        this.termNominalInterestRateMaximumBlock = this.row7.newUIBlock("termNominalInterestRateMaximumBlock", Size.Three_3);
        this.termNominalInterestRateMaximumIContainer = this.termNominalInterestRateMaximumBlock.newUIContainer("termNominalInterestRateMaximumIContainer");
        this.termNominalInterestRateMaximumField = new TextField<>("termNominalInterestRateMaximumField", this.termNominalInterestRateMaximumValue);
        this.termNominalInterestRateMaximumIContainer.add(this.termNominalInterestRateMaximumField);
        this.termNominalInterestRateMaximumIContainer.newFeedback("termNominalInterestRateMaximumFeedback", this.termNominalInterestRateMaximumField);

        this.termNominalInterestRateTypeBlock = this.row7.newUIBlock("termNominalInterestRateTypeBlock", Size.Three_3);
        this.termNominalInterestRateTypeIContainer = this.termNominalInterestRateTypeBlock.newUIContainer("termNominalInterestRateTypeIContainer");
        this.termNominalInterestRateTypeField = new Select2SingleChoice<>("termNominalInterestRateTypeField", new PropertyModel<>(this.itemPage, "termNominalInterestRateTypeValue"), this.termNominalInterestRateTypeProvider);
        this.termNominalInterestRateTypeIContainer.add(this.termNominalInterestRateTypeField);
        this.termNominalInterestRateTypeIContainer.newFeedback("termNominalInterestRateTypeFeedback", this.termNominalInterestRateTypeField);

        this.row8 = UIRow.newUIRow("row8", this.form);

        this.termFloatingInterestRateBlock = this.row8.newUIBlock("termFloatingInterestRateBlock", Size.Four_4);
        this.termFloatingInterestRateIContainer = this.termFloatingInterestRateBlock.newUIContainer("termFloatingInterestRateIContainer");
        this.termFloatingInterestRateField = new Select2SingleChoice<>("termFloatingInterestRateField", new PropertyModel<>(this.itemPage, "termFloatingInterestRateValue"), this.termFloatingInterestRateProvider);
        this.termFloatingInterestRateIContainer.add(this.termFloatingInterestRateField);
        this.termFloatingInterestRateIContainer.newFeedback("termFloatingInterestRateFeedback", this.termFloatingInterestRateField);

        this.termFloatingInterestDifferentialBlock = this.row8.newUIBlock("termFloatingInterestDifferentialBlock", Size.Four_4);
        this.termFloatingInterestDifferentialIContainer = this.termFloatingInterestDifferentialBlock.newUIContainer("termFloatingInterestDifferentialIContainer");
        this.termFloatingInterestDifferentialField = new TextField<>("termFloatingInterestDifferentialField", new PropertyModel<>(this.itemPage, "termFloatingInterestDifferentialValue"));
        this.termFloatingInterestDifferentialIContainer.add(this.termFloatingInterestDifferentialField);
        this.termFloatingInterestDifferentialIContainer.newFeedback("termFloatingInterestDifferentialFeedback", this.termFloatingInterestDifferentialField);

        this.termFloatingInterestAllowedBlock = this.row8.newUIBlock("termFloatingInterestAllowedBlock", Size.Four_4);
        this.termFloatingInterestAllowedIContainer = this.termFloatingInterestAllowedBlock.newUIContainer("termFloatingInterestAllowedIContainer");
        this.termFloatingInterestAllowedField = new CheckBox("termFloatingInterestAllowedField", new PropertyModel<>(this.itemPage, "termFloatingInterestAllowedValue"));
        this.termFloatingInterestAllowedIContainer.add(this.termFloatingInterestAllowedField);
        this.termFloatingInterestAllowedIContainer.newFeedback("termFloatingInterestAllowedFeedback", this.termFloatingInterestAllowedField);

        this.row9 = UIRow.newUIRow("row9", this.form);

        this.termFloatingInterestMinimumBlock = this.row9.newUIBlock("termFloatingInterestMinimumBlock", Size.Four_4);
        this.termFloatingInterestMinimumIContainer = this.termFloatingInterestMinimumBlock.newUIContainer("termFloatingInterestMinimumIContainer");
        this.termFloatingInterestMinimumField = new TextField<>("termFloatingInterestMinimumField", this.termFloatingInterestMinimumValue);
        this.termFloatingInterestMinimumIContainer.add(this.termFloatingInterestMinimumField);
        this.termFloatingInterestMinimumIContainer.newFeedback("termFloatingInterestMinimumFeedback", this.termFloatingInterestMinimumField);

        this.termFloatingInterestDefaultBlock = this.row9.newUIBlock("termFloatingInterestDefaultBlock", Size.Four_4);
        this.termFloatingInterestDefaultIContainer = this.termFloatingInterestDefaultBlock.newUIContainer("termFloatingInterestDefaultIContainer");
        this.termFloatingInterestDefaultField = new TextField<>("termFloatingInterestDefaultField", this.termFloatingInterestDefaultValue);
        this.termFloatingInterestDefaultIContainer.add(this.termFloatingInterestDefaultField);
        this.termFloatingInterestDefaultIContainer.newFeedback("termFloatingInterestDefaultFeedback", this.termFloatingInterestDefaultField);

        this.termFloatingInterestMaximumBlock = this.row9.newUIBlock("termFloatingInterestMaximumBlock", Size.Four_4);
        this.termFloatingInterestMaximumIContainer = this.termFloatingInterestMaximumBlock.newUIContainer("termFloatingInterestMaximumIContainer");
        this.termFloatingInterestMaximumField = new TextField<>("termFloatingInterestMaximumField", this.termFloatingInterestMaximumValue);
        this.termFloatingInterestMaximumIContainer.add(this.termFloatingInterestMaximumField);
        this.termFloatingInterestMaximumIContainer.newFeedback("termFloatingInterestMaximumFeedback", this.termFloatingInterestMaximumField);

        this.row10 = UIRow.newUIRow("row10", this.form);

        this.termNominalInterestRateByLoanCycleBlock = this.row10.newUIBlock("termNominalInterestRateByLoanCycleBlock", Size.Twelve_12);
        this.termNominalInterestRateByLoanCycleIContainer = this.termNominalInterestRateByLoanCycleBlock.newUIContainer("termNominalInterestRateByLoanCycleIContainer");
        this.termNominalInterestRateByLoanCycleTable = new DataTable<>("termNominalInterestRateByLoanCycleTable", this.termNominalInterestRateByLoanCycleColumn, this.termNominalInterestRateByLoanCycleProvider, 20);
        this.termNominalInterestRateByLoanCycleIContainer.add(this.termNominalInterestRateByLoanCycleTable);
        this.termNominalInterestRateByLoanCycleTable.addTopToolbar(new HeadersToolbar<>(this.termNominalInterestRateByLoanCycleTable, this.termNominalInterestRateByLoanCycleProvider));
        this.termNominalInterestRateByLoanCycleTable.addBottomToolbar(new NoRecordsToolbar(this.termNominalInterestRateByLoanCycleTable));
        this.termNominalInterestRateByLoanCycleAddLink = new AjaxLink<>("termNominalInterestRateByLoanCycleAddLink");
        this.termNominalInterestRateByLoanCycleAddLink.setOnClick(this::termNominalInterestRateByLoanCycleAddLinkClick);
        this.termNominalInterestRateByLoanCycleIContainer.add(this.termNominalInterestRateByLoanCycleAddLink);

        this.row11 = UIRow.newUIRow("row11", this.form);

        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock = this.row11.newUIBlock("termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock", Size.Six_6);
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateIContainer = this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateBlock.newUIContainer("termMinimumDayBetweenDisbursalAndFirstRepaymentDateIContainer");
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateField = new TextField<>("termMinimumDayBetweenDisbursalAndFirstRepaymentDateField", new PropertyModel<>(this.itemPage, "termMinimumDayBetweenDisbursalAndFirstRepaymentDateValue"));
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateIContainer.add(this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateField);
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateIContainer.newFeedback("termMinimumDayBetweenDisbursalAndFirstRepaymentDateFeedback", this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateField);

        this.row11Block1 = this.row11.newUIBlock("row11Block1", Size.Six_6);
    }

    @Override
    protected void configureMetaData() {
        this.termVaryBasedOnLoanCycleField.add(new OnChangeAjaxBehavior(this::termVaryBasedOnLoanCycleFieldUpdate));

        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateField.setLabel(Model.of("Minimum days between disbursal and first repayment date"));
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateField.add(new OnChangeAjaxBehavior());
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateField.add(RangeValidator.minimum(0l));
        this.termMinimumDayBetweenDisbursalAndFirstRepaymentDateField.setRequired(true);

        this.termFloatingInterestMaximumField.setLabel(Model.of("Floating interest Maximum"));
        this.termFloatingInterestMaximumField.add(new OnChangeAjaxBehavior());
        this.termFloatingInterestMaximumField.add(RangeValidator.minimum(0d));

        this.termFloatingInterestDefaultField.setLabel(Model.of("Floating Interest Default"));
        this.termFloatingInterestDefaultField.add(new OnChangeAjaxBehavior());
        this.termFloatingInterestDefaultField.add(RangeValidator.minimum(0d));

        this.termFloatingInterestMinimumField.setLabel(Model.of("Floating Interest Minimum"));
        this.termFloatingInterestMinimumField.add(new OnChangeAjaxBehavior());
        this.termFloatingInterestMinimumField.add(RangeValidator.minimum(0d));

        this.termFloatingInterestAllowedField.add(new OnChangeAjaxBehavior());

        this.termFloatingInterestDifferentialField.setLabel(Model.of("Floating interest differential rate"));
        this.termFloatingInterestDifferentialField.add(new OnChangeAjaxBehavior());
        this.termFloatingInterestDifferentialField.add(RangeValidator.minimum(0d));

        this.termFloatingInterestRateField.setLabel(Model.of("Floating interest rate"));
        this.termFloatingInterestRateField.add(new OnChangeAjaxBehavior());

        this.termNominalInterestRateTypeField.setLabel(Model.of("Type"));
        this.termNominalInterestRateTypeField.add(new OnChangeAjaxBehavior());
        this.termNominalInterestRateTypeField.setRequired(true);

        this.termNominalInterestRateMinimumField.setLabel(Model.of("Nominal interest rate minimum"));
        this.termNominalInterestRateMinimumField.add(new OnChangeAjaxBehavior());
        this.termNominalInterestRateMinimumField.add(RangeValidator.minimum(0d));

        this.termNominalInterestRateDefaultField.setLabel(Model.of("Nominal interest rate Default"));
        this.termNominalInterestRateDefaultField.add(new OnChangeAjaxBehavior());
        this.termNominalInterestRateDefaultField.add(RangeValidator.minimum(0d));
        this.termNominalInterestRateDefaultField.setRequired(true);

        this.termNominalInterestRateMaximumField.setLabel(Model.of("Nominal interest rate Maximum"));
        this.termNominalInterestRateMaximumField.add(new OnChangeAjaxBehavior());

        this.termLinkedToFloatingInterestRatesField.add(new OnChangeAjaxBehavior(this::termLinkedToFloatingInterestRatesFieldUpdate));

        this.termRepaidTypeField.setLabel(Model.of("Repaid Type"));
        this.termRepaidTypeField.add(new OnChangeAjaxBehavior());
        this.termRepaidTypeField.setRequired(true);

        this.termRepaidEveryField.setLabel(Model.of("Repaid Every"));
        this.termRepaidEveryField.add(new OnChangeAjaxBehavior());
        this.termRepaidEveryField.setRequired(true);

        this.termNumberOfRepaymentMinimumField.setLabel(Model.of("Number of repayment Minimum"));
        this.termNumberOfRepaymentMinimumField.add(new OnChangeAjaxBehavior());
        this.termNumberOfRepaymentMinimumField.add(RangeValidator.minimum(0l));

        this.termNumberOfRepaymentDefaultField.setLabel(Model.of("Number of repayment Default"));
        this.termNumberOfRepaymentDefaultField.add(new OnChangeAjaxBehavior());
        this.termNumberOfRepaymentDefaultField.add(RangeValidator.minimum(0l));
        this.termNumberOfRepaymentDefaultField.setRequired(true);

        this.termNumberOfRepaymentMaximumField.setLabel(Model.of("Number of repayment Maximum"));
        this.termNumberOfRepaymentMaximumField.add(new OnChangeAjaxBehavior());

        this.termPrincipalMinimumField.setLabel(Model.of("Principal Minimum"));
        this.termPrincipalMinimumField.add(new OnChangeAjaxBehavior());
        this.termPrincipalMinimumField.add(RangeValidator.minimum(0d));

        this.termPrincipalDefaultField.setLabel(Model.of("Principal Default"));
        this.termPrincipalDefaultField.add(new OnChangeAjaxBehavior());
        this.termPrincipalDefaultField.add(RangeValidator.minimum(0d));
        this.termPrincipalDefaultField.setRequired(true);

        this.termPrincipalMaximumField.setLabel(Model.of("Principal Maximum"));
        this.termPrincipalMaximumField.add(new OnChangeAjaxBehavior());

        termVaryBasedOnLoanCycleFieldUpdate(null);

        termLinkedToFloatingInterestRatesFieldUpdate(null);

        this.form.add(new LamdaFormValidator(this::principalValidator, this.termPrincipalMinimumField, this.termPrincipalDefaultField, this.termPrincipalMaximumField));

        this.form.add(new LamdaFormValidator(this::numberOfRepaymentValidator, this.termNumberOfRepaymentMinimumField, this.termNumberOfRepaymentDefaultField, this.termNumberOfRepaymentMaximumField));

        this.form.add(new LamdaFormValidator(this::termNominalInterestRateValidator, this.termNominalInterestRateMinimumField, this.termNominalInterestRateDefaultField, this.termNominalInterestRateMaximumField));

        this.form.add(new LamdaFormValidator(this::termFloatingInterestValidator, this.termFloatingInterestMinimumField, this.termFloatingInterestDefaultField, this.termFloatingInterestMaximumField));

    }

    protected void termFloatingInterestValidator(Form<?> form) {
        if (this.termFloatingInterestDefaultValue.getObject() != null) {
            if (this.termFloatingInterestMinimumValue.getObject() == null && this.termFloatingInterestMaximumValue.getObject() == null) {
            } else if (this.termFloatingInterestMinimumValue.getObject() != null && this.termFloatingInterestMaximumValue.getObject() != null) {
                if (this.termFloatingInterestMinimumValue.getObject() >= this.termFloatingInterestDefaultValue.getObject()) {
                    this.termFloatingInterestMinimumField.error(new ValidationError("Invalid"));
                }
                if (this.termFloatingInterestMaximumValue.getObject() <= this.termFloatingInterestDefaultValue.getObject()) {
                    this.termFloatingInterestMaximumField.error(new ValidationError("Invalid"));
                }
            } else {
                if (this.termFloatingInterestMinimumValue.getObject() == null) {
                    this.termFloatingInterestMinimumField.error(new ValidationError("Required"));
                } else {
                    if (this.termFloatingInterestMinimumValue.getObject() >= this.termFloatingInterestDefaultValue.getObject()) {
                        this.termFloatingInterestMinimumField.error(new ValidationError("Invalid"));
                    }
                }
                if (this.termFloatingInterestMaximumValue.getObject() == null) {
                    this.termFloatingInterestMaximumField.error(new ValidationError("Required"));
                } else {
                    if (this.termFloatingInterestMaximumValue.getObject() <= this.termFloatingInterestDefaultValue.getObject()) {
                        this.termFloatingInterestMaximumField.error(new ValidationError("Invalid"));
                    }
                }
            }
        }
    }

    protected void termNominalInterestRateValidator(Form<?> form) {
        if (this.termNominalInterestRateMinimumValue.getObject() == null && this.termNominalInterestRateMaximumValue.getObject() == null) {
        } else if (this.termNominalInterestRateMinimumValue.getObject() != null && this.termNominalInterestRateMaximumValue.getObject() != null) {
            if (this.termNominalInterestRateMinimumValue.getObject() >= this.termNominalInterestRateDefaultValue.getObject()) {
                this.termNominalInterestRateMinimumField.error(new ValidationError("Invalid"));
            }
            if (this.termNominalInterestRateMaximumValue.getObject() <= this.termNominalInterestRateDefaultValue.getObject()) {
                this.termNominalInterestRateMaximumField.error(new ValidationError("Invalid"));
            }
        } else {
            if (this.termNominalInterestRateMinimumValue.getObject() == null) {
                this.termNominalInterestRateMinimumField.error(new ValidationError("Required"));
            } else {
                if (this.termNominalInterestRateMinimumValue.getObject() >= this.termNominalInterestRateDefaultValue.getObject()) {
                    this.termNominalInterestRateMinimumField.error(new ValidationError("Invalid"));
                }
            }
            if (this.termNominalInterestRateMaximumValue.getObject() == null) {
                this.termNominalInterestRateMaximumField.error(new ValidationError("Required"));
            } else {
                if (this.termNominalInterestRateMaximumValue.getObject() <= this.termNominalInterestRateDefaultValue.getObject()) {
                    this.termNominalInterestRateMaximumField.error(new ValidationError("Invalid"));
                }
            }
        }
    }

    protected void numberOfRepaymentValidator(Form<?> form) {
        if (this.termNumberOfRepaymentMinimumValue.getObject() == null && this.termNumberOfRepaymentMaximumValue.getObject() == null) {
        } else if (this.termNumberOfRepaymentMinimumValue.getObject() != null && this.termNumberOfRepaymentMaximumValue.getObject() != null) {
            if (this.termNumberOfRepaymentMinimumValue.getObject() >= this.termNumberOfRepaymentDefaultValue.getObject()) {
                this.termNumberOfRepaymentMinimumField.error(new ValidationError("Invalid"));
            }
            if (this.termNumberOfRepaymentMaximumValue.getObject() <= this.termNumberOfRepaymentDefaultValue.getObject()) {
                this.termNumberOfRepaymentMaximumField.error(new ValidationError("Invalid"));
            }
        } else {
            if (this.termNumberOfRepaymentMinimumValue.getObject() == null) {
                this.termNumberOfRepaymentMinimumField.error(new ValidationError("Required"));
            } else {
                if (this.termNumberOfRepaymentMinimumValue.getObject() >= this.termNumberOfRepaymentDefaultValue.getObject()) {
                    this.termNumberOfRepaymentMinimumField.error(new ValidationError("Invalid"));
                }
            }
            if (this.termNumberOfRepaymentMaximumValue.getObject() == null) {
                this.termNumberOfRepaymentMaximumField.error(new ValidationError("Required"));
            } else {
                if (this.termNumberOfRepaymentMaximumValue.getObject() <= this.termNumberOfRepaymentDefaultValue.getObject()) {
                    this.termNumberOfRepaymentMaximumField.error(new ValidationError("Invalid"));
                }
            }
        }
    }

    protected void principalValidator(Form<?> form) {
        if (this.termPrincipalMinimumValue.getObject() == null && this.termPrincipalMaximumValue.getObject() == null) {
        } else if (this.termPrincipalMinimumValue.getObject() != null && this.termPrincipalMaximumValue.getObject() != null) {
            if (this.termPrincipalMinimumValue.getObject() >= this.termPrincipalDefaultValue.getObject()) {
                this.termPrincipalMinimumField.error(new ValidationError("Invalid"));
            }
            if (this.termPrincipalMaximumValue.getObject() <= this.termPrincipalDefaultValue.getObject()) {
                this.termPrincipalMaximumField.error(new ValidationError("Invalid"));
            }
        } else {
            if (this.termPrincipalMinimumValue.getObject() == null) {
                this.termPrincipalMinimumField.error(new ValidationError("Required"));
            } else {
                if (this.termPrincipalMinimumValue.getObject() >= this.termPrincipalDefaultValue.getObject()) {
                    this.termPrincipalMinimumField.error(new ValidationError("Invalid"));
                }
            }
            if (this.termPrincipalMaximumValue.getObject() == null) {
                this.termPrincipalMaximumField.error(new ValidationError("Required"));
            } else {
                if (this.termPrincipalMaximumValue.getObject() <= this.termPrincipalDefaultValue.getObject()) {
                    this.termPrincipalMaximumField.error(new ValidationError("Invalid"));
                }
            }
        }
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
        this.errorTerm.setObject(false);
        this.tab.getObject().setSelectedTab(LoanCreatePage.TAB_SETTING);
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

}
