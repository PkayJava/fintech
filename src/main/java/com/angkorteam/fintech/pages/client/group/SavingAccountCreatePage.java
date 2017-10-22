package com.angkorteam.fintech.pages.client.group;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.joda.time.DateTime;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.AccountBuilder;
import com.angkorteam.fintech.dto.enums.DayInYear;
import com.angkorteam.fintech.dto.enums.InterestCalculatedUsing;
import com.angkorteam.fintech.dto.enums.InterestCompoundingPeriod;
import com.angkorteam.fintech.dto.enums.InterestPostingPeriod;
import com.angkorteam.fintech.dto.enums.LockInType;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.popup.AccountChargePopup;
import com.angkorteam.fintech.provider.DayInYearProvider;
import com.angkorteam.fintech.provider.InterestCalculatedUsingProvider;
import com.angkorteam.fintech.provider.InterestCompoundingPeriodProvider;
import com.angkorteam.fintech.provider.InterestPostingPeriodProvider;
import com.angkorteam.fintech.provider.LockInTypeProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.spring.JdbcTemplate;
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
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class SavingAccountCreatePage extends Page {

    protected String groupId;
    protected String savingId;
    protected String officeId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected String productValue;
    protected Label productField;

    protected WebMarkupContainer submittedOnBlock;
    protected WebMarkupContainer submittedOnContainer;
    protected Date submittedOnValue;
    protected DateTextField submittedOnField;
    protected TextFeedbackPanel submittedOnFeedback;

    protected SingleChoiceProvider officerProvider;
    protected WebMarkupContainer officerBlock;
    protected WebMarkupContainer officerContainer;
    protected Option officerValue;
    protected Select2SingleChoice<Option> officerField;
    protected TextFeedbackPanel officerFeedback;

    protected WebMarkupContainer externalIdBlock;
    protected WebMarkupContainer externalIdContainer;
    protected String externalIdValue;
    protected TextField<String> externalIdField;
    protected TextFeedbackPanel externalIdFeedback;

    protected String currencyValue;
    protected Label currencyField;

    protected Integer decimalPlacesValue;
    protected Label decimalPlacesField;

    protected WebMarkupContainer nominalAnnualInterestBlock;
    protected WebMarkupContainer nominalAnnualInterestContainer;
    protected Double nominalAnnualInterestValue;
    protected TextField<Double> nominalAnnualInterestField;
    protected TextFeedbackPanel nominalAnnualInterestFeedback;

    protected InterestCompoundingPeriodProvider interestCompoundingPeriodProvider;
    protected WebMarkupContainer interestCompoundingPeriodBlock;
    protected WebMarkupContainer interestCompoundingPeriodContainer;
    protected Option interestCompoundingPeriodValue;
    protected Select2SingleChoice<Option> interestCompoundingPeriodField;
    protected TextFeedbackPanel interestCompoundingPeriodFeedback;

    protected Integer currencyInMultiplesOfValue;
    protected Label currencyInMultiplesOfField;

    protected InterestPostingPeriodProvider interestPostingPeriodProvider;
    protected WebMarkupContainer interestPostingPeriodBlock;
    protected WebMarkupContainer interestPostingPeriodContainer;
    protected Option interestPostingPeriodValue;
    protected Select2SingleChoice<Option> interestPostingPeriodField;
    protected TextFeedbackPanel interestPostingPeriodFeedback;

    protected InterestCalculatedUsingProvider interestCalculatedUsingProvider;
    protected WebMarkupContainer interestCalculatedUsingBlock;
    protected WebMarkupContainer interestCalculatedUsingContainer;
    protected Option interestCalculatedUsingValue;
    protected Select2SingleChoice<Option> interestCalculatedUsingField;
    protected TextFeedbackPanel interestCalculatedUsingFeedback;

    protected DayInYearProvider dayInYearProvider;
    protected WebMarkupContainer dayInYearBlock;
    protected WebMarkupContainer dayInYearContainer;
    protected Option dayInYearValue;
    protected Select2SingleChoice<Option> dayInYearField;
    protected TextFeedbackPanel dayInYearFeedback;

    protected WebMarkupContainer minimumOpeningBalanceBlock;
    protected WebMarkupContainer minimumOpeningBalanceContainer;
    protected Double minimumOpeningBalanceValue;
    protected TextField<Double> minimumOpeningBalanceField;
    protected TextFeedbackPanel minimumOpeningBalanceFeedback;

    protected WebMarkupContainer lockInPeriodBlock;
    protected WebMarkupContainer lockInPeriodContainer;
    protected Integer lockInPeriodValue;
    protected TextField<Integer> lockInPeriodField;
    protected TextFeedbackPanel lockInPeriodFeedback;

    protected LockInTypeProvider lockInTypeProvider;
    protected WebMarkupContainer lockInTypeBlock;
    protected WebMarkupContainer lockInTypeContainer;
    protected Option lockInTypeValue;
    protected Select2SingleChoice<Option> lockInTypeField;
    protected TextFeedbackPanel lockInTypeFeedback;

    protected WebMarkupContainer applyWithdrawalFeeForTransferBlock;
    protected WebMarkupContainer applyWithdrawalFeeForTransferContainer;
    protected Boolean applyWithdrawalFeeForTransferValue;
    protected CheckBox applyWithdrawalFeeForTransferField;
    protected TextFeedbackPanel applyWithdrawalFeeForTransferFeedback;

    protected WebMarkupContainer overdraftAllowedBlock;
    protected WebMarkupContainer overdraftAllowedContainer;
    protected Boolean overdraftAllowedValue;
    protected CheckBox overdraftAllowedField;
    protected TextFeedbackPanel overdraftAllowedFeedback;

    protected WebMarkupContainer maximumOverdraftAmountLimitBlock;
    protected WebMarkupContainer maximumOverdraftAmountLimitContainer;
    protected Double maximumOverdraftAmountLimitValue;
    protected TextField<Double> maximumOverdraftAmountLimitField;
    protected TextFeedbackPanel maximumOverdraftAmountLimitFeedback;

    protected WebMarkupContainer nominalAnnualInterestForOverdraftBlock;
    protected WebMarkupContainer nominalAnnualInterestForOverdraftContainer;
    protected Double nominalAnnualInterestForOverdraftValue;
    protected TextField<Double> nominalAnnualInterestForOverdraftField;
    protected TextFeedbackPanel nominalAnnualInterestForOverdraftFeedback;

    protected WebMarkupContainer minOverdraftRequiredForInterestCalculationBlock;
    protected WebMarkupContainer minOverdraftRequiredForInterestCalculationContainer;
    protected Double minOverdraftRequiredForInterestCalculationValue;
    protected TextField<Double> minOverdraftRequiredForInterestCalculationField;
    protected TextFeedbackPanel minOverdraftRequiredForInterestCalculationFeedback;

    protected WebMarkupContainer enforceMinimumBalanceBlock;
    protected WebMarkupContainer enforceMinimumBalanceContainer;
    protected Boolean enforceMinimumBalanceValue;
    protected CheckBox enforceMinimumBalanceField;
    protected TextFeedbackPanel enforceMinimumBalanceFeedback;

    protected WebMarkupContainer minimumBalanceBlock;
    protected WebMarkupContainer minimumBalanceContainer;
    protected Double minimumBalanceValue;
    protected TextField<Double> minimumBalanceField;
    protected TextFeedbackPanel minimumBalanceFeedback;

    protected Double balanceRequiredForInterestCalculationValue;
    protected Label balanceRequiredForInterestCalculationField;

    protected List<Map<String, Object>> chargeValue = Lists.newLinkedList();
    protected DataTable<Map<String, Object>, String> chargeTable;
    protected ListDataProvider chargeProvider;
    protected AjaxLink<Void> chargeAddLink;
    protected ModalWindow chargePopup;

    protected Option itemChargeValue;
    protected String itemChargeTypeValue;
    protected Double itemAmountValue;
    protected String itemCollectedOnValue;
    protected Date itemDateValue;
    protected Integer itemRepaymentEveryValue;

    @Override
    protected void onInitialize() {
        super.onInitialize();
        initData();

        PageParameters parameters = new PageParameters();
        parameters.add("groupId", this.groupId);

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", GroupPreviewPage.class, parameters);
        this.form.add(this.closeLink);

        this.productField = new Label("productField", new PropertyModel<>(this, "productValue"));
        this.form.add(this.productField);

        this.submittedOnBlock = new WebMarkupContainer("submittedOnBlock");
        this.submittedOnBlock.setOutputMarkupId(true);
        this.form.add(this.submittedOnBlock);
        this.submittedOnContainer = new WebMarkupContainer("submittedOnContainer");
        this.submittedOnBlock.add(this.submittedOnContainer);
        this.submittedOnField = new DateTextField("submittedOnField", new PropertyModel<>(this, "submittedOnValue"));
        this.submittedOnField.setLabel(Model.of("Submitted On"));
        this.submittedOnField.setRequired(false);
        this.submittedOnContainer.add(this.submittedOnField);
        this.submittedOnFeedback = new TextFeedbackPanel("submittedOnFeedback", this.submittedOnField);
        this.submittedOnContainer.add(this.submittedOnFeedback);

        this.officerProvider = new SingleChoiceProvider("m_staff", "id", "display_name");
        this.officerProvider.applyWhere("is_active", "is_active = 1");
        this.officerProvider.applyWhere("office_id", "office_id = " + this.officeId);
        this.officerBlock = new WebMarkupContainer("officerBlock");
        this.officerBlock.setOutputMarkupId(true);
        this.form.add(this.officerBlock);
        this.officerContainer = new WebMarkupContainer("officerContainer");
        this.officerBlock.add(this.officerContainer);
        this.officerField = new Select2SingleChoice<>("officerField", new PropertyModel<>(this, "officerValue"), this.officerProvider);
        this.officerField.setLabel(Model.of("Officer"));
        this.officerField.setRequired(false);
        this.officerContainer.add(this.officerField);
        this.officerFeedback = new TextFeedbackPanel("officerFeedback", this.officerField);
        this.officerContainer.add(this.officerFeedback);

        this.externalIdBlock = new WebMarkupContainer("externalIdBlock");
        this.externalIdBlock.setOutputMarkupId(true);
        this.form.add(this.externalIdBlock);
        this.externalIdContainer = new WebMarkupContainer("externalIdContainer");
        this.externalIdBlock.add(this.externalIdContainer);
        this.externalIdField = new TextField<>("externalIdField", new PropertyModel<>(this, "externalIdValue"));
        this.externalIdField.setLabel(Model.of("External ID"));
        this.externalIdField.setRequired(false);
        this.externalIdContainer.add(this.externalIdField);
        this.externalIdFeedback = new TextFeedbackPanel("externalIdFeedback", this.externalIdField);
        this.externalIdContainer.add(this.externalIdFeedback);

        this.currencyField = new Label("currencyField", new PropertyModel<>(this, "currencyValue"));
        this.form.add(this.currencyField);

        this.decimalPlacesField = new Label("decimalPlacesField", new PropertyModel<>(this, "decimalPlacesValue"));
        this.form.add(this.decimalPlacesField);

        this.nominalAnnualInterestBlock = new WebMarkupContainer("nominalAnnualInterestBlock");
        this.nominalAnnualInterestBlock.setOutputMarkupId(true);
        this.form.add(this.nominalAnnualInterestBlock);
        this.nominalAnnualInterestContainer = new WebMarkupContainer("nominalAnnualInterestContainer");
        this.nominalAnnualInterestBlock.add(this.nominalAnnualInterestContainer);
        this.nominalAnnualInterestField = new TextField<>("nominalAnnualInterestField", new PropertyModel<>(this, "nominalAnnualInterestValue"));
        this.nominalAnnualInterestField.setLabel(Model.of("Nominal Annual Interest"));
        this.nominalAnnualInterestField.setRequired(false);
        this.nominalAnnualInterestContainer.add(this.nominalAnnualInterestField);
        this.nominalAnnualInterestFeedback = new TextFeedbackPanel("nominalAnnualInterestFeedback", this.nominalAnnualInterestField);
        this.nominalAnnualInterestContainer.add(this.nominalAnnualInterestFeedback);

        this.interestCompoundingPeriodProvider = new InterestCompoundingPeriodProvider();
        this.interestCompoundingPeriodBlock = new WebMarkupContainer("interestCompoundingPeriodBlock");
        this.interestCompoundingPeriodBlock.setOutputMarkupId(true);
        this.form.add(this.interestCompoundingPeriodBlock);
        this.interestCompoundingPeriodContainer = new WebMarkupContainer("interestCompoundingPeriodContainer");
        this.interestCompoundingPeriodBlock.add(this.interestCompoundingPeriodContainer);
        this.interestCompoundingPeriodField = new Select2SingleChoice<>("interestCompoundingPeriodField", new PropertyModel<>(this, "interestCompoundingPeriodValue"), this.interestCompoundingPeriodProvider);
        this.interestCompoundingPeriodField.setLabel(Model.of("Interest Compounding Period"));
        this.interestCompoundingPeriodField.setRequired(false);
        this.interestCompoundingPeriodContainer.add(this.interestCompoundingPeriodField);
        this.interestCompoundingPeriodFeedback = new TextFeedbackPanel("interestCompoundingPeriodFeedback", this.interestCompoundingPeriodField);
        this.interestCompoundingPeriodContainer.add(this.interestCompoundingPeriodFeedback);

        this.currencyInMultiplesOfField = new Label("currencyInMultiplesOfField", new PropertyModel<>(this, "currencyInMultiplesOfValue"));
        this.form.add(this.currencyInMultiplesOfField);

        this.interestPostingPeriodProvider = new InterestPostingPeriodProvider();
        this.interestPostingPeriodBlock = new WebMarkupContainer("interestPostingPeriodBlock");
        this.interestPostingPeriodBlock.setOutputMarkupId(true);
        this.form.add(this.interestPostingPeriodBlock);
        this.interestPostingPeriodContainer = new WebMarkupContainer("interestPostingPeriodContainer");
        this.interestPostingPeriodBlock.add(this.interestPostingPeriodContainer);
        this.interestPostingPeriodField = new Select2SingleChoice<>("interestPostingPeriodField", new PropertyModel<>(this, "interestPostingPeriodValue"), this.interestPostingPeriodProvider);
        this.interestPostingPeriodField.setLabel(Model.of("Interest Posting Period"));
        this.interestPostingPeriodField.setRequired(false);
        this.interestPostingPeriodContainer.add(this.interestPostingPeriodField);
        this.interestPostingPeriodFeedback = new TextFeedbackPanel("interestPostingPeriodFeedback", this.interestPostingPeriodField);
        this.interestPostingPeriodContainer.add(this.interestPostingPeriodFeedback);

        this.interestCalculatedUsingProvider = new InterestCalculatedUsingProvider();
        this.interestCalculatedUsingBlock = new WebMarkupContainer("interestCalculatedUsingBlock");
        this.interestCalculatedUsingBlock.setOutputMarkupId(true);
        this.form.add(this.interestCalculatedUsingBlock);
        this.interestCalculatedUsingContainer = new WebMarkupContainer("interestCalculatedUsingContainer");
        this.interestCalculatedUsingBlock.add(this.interestCalculatedUsingContainer);
        this.interestCalculatedUsingField = new Select2SingleChoice<>("interestCalculatedUsingField", new PropertyModel<>(this, "interestCalculatedUsingValue"), this.interestCalculatedUsingProvider);
        this.interestCalculatedUsingField.setLabel(Model.of("Interest Calculated Using"));
        this.interestCalculatedUsingField.setRequired(false);
        this.interestCalculatedUsingContainer.add(this.interestCalculatedUsingField);
        this.interestCalculatedUsingFeedback = new TextFeedbackPanel("interestCalculatedUsingFeedback", this.interestCalculatedUsingField);
        this.interestCalculatedUsingContainer.add(this.interestCalculatedUsingFeedback);

        this.dayInYearProvider = new DayInYearProvider(DayInYear.D365, DayInYear.D360);
        this.dayInYearBlock = new WebMarkupContainer("dayInYearBlock");
        this.dayInYearBlock.setOutputMarkupId(true);
        this.form.add(this.dayInYearBlock);
        this.dayInYearContainer = new WebMarkupContainer("dayInYearContainer");
        this.dayInYearBlock.add(this.dayInYearContainer);
        this.dayInYearField = new Select2SingleChoice<>("dayInYearField", new PropertyModel<>(this, "dayInYearValue"), this.dayInYearProvider);
        this.dayInYearField.setLabel(Model.of("Days In Year"));
        this.dayInYearField.setRequired(false);
        this.dayInYearContainer.add(this.dayInYearField);
        this.dayInYearFeedback = new TextFeedbackPanel("dayInYearFeedback", this.dayInYearField);
        this.dayInYearContainer.add(this.dayInYearFeedback);

        this.minimumOpeningBalanceBlock = new WebMarkupContainer("minimumOpeningBalanceBlock");
        this.minimumOpeningBalanceBlock.setOutputMarkupId(true);
        this.form.add(this.minimumOpeningBalanceBlock);
        this.minimumOpeningBalanceContainer = new WebMarkupContainer("minimumOpeningBalanceContainer");
        this.minimumOpeningBalanceBlock.add(this.minimumOpeningBalanceContainer);
        this.minimumOpeningBalanceField = new TextField<>("minimumOpeningBalanceField", new PropertyModel<>(this, "minimumOpeningBalanceValue"));
        this.minimumOpeningBalanceField.setLabel(Model.of("Minimum Opening Balance"));
        this.minimumOpeningBalanceField.setRequired(false);
        this.minimumOpeningBalanceContainer.add(this.minimumOpeningBalanceField);
        this.minimumOpeningBalanceFeedback = new TextFeedbackPanel("minimumOpeningBalanceFeedback", this.minimumOpeningBalanceField);
        this.minimumOpeningBalanceContainer.add(this.minimumOpeningBalanceFeedback);

        this.lockInPeriodBlock = new WebMarkupContainer("lockInPeriodBlock");
        this.lockInPeriodBlock.setOutputMarkupId(true);
        this.form.add(this.lockInPeriodBlock);
        this.lockInPeriodContainer = new WebMarkupContainer("lockInPeriodContainer");
        this.lockInPeriodBlock.add(this.lockInPeriodContainer);
        this.lockInPeriodField = new TextField<>("lockInPeriodField", new PropertyModel<>(this, "lockInPeriodValue"));
        this.lockInPeriodField.setLabel(Model.of("Lock In Period"));
        this.lockInPeriodField.setRequired(false);
        this.lockInPeriodContainer.add(this.lockInPeriodField);
        this.lockInPeriodFeedback = new TextFeedbackPanel("lockInPeriodFeedback", this.lockInPeriodField);
        this.lockInPeriodContainer.add(this.lockInPeriodFeedback);

        this.lockInTypeProvider = new LockInTypeProvider();
        this.lockInTypeBlock = new WebMarkupContainer("lockInTypeBlock");
        this.lockInTypeBlock.setOutputMarkupId(true);
        this.form.add(this.lockInTypeBlock);
        this.lockInTypeContainer = new WebMarkupContainer("lockInTypeContainer");
        this.lockInTypeBlock.add(this.lockInTypeContainer);
        this.lockInTypeField = new Select2SingleChoice<>("lockInTypeField", new PropertyModel<>(this, "lockInTypeValue"), this.lockInTypeProvider);
        this.lockInTypeField.setLabel(Model.of("Lock In Period"));
        this.lockInTypeField.setRequired(false);
        this.lockInTypeContainer.add(this.lockInTypeField);
        this.lockInTypeFeedback = new TextFeedbackPanel("lockInTypeFeedback", this.lockInTypeField);
        this.lockInTypeContainer.add(this.lockInTypeFeedback);

        this.applyWithdrawalFeeForTransferBlock = new WebMarkupContainer("applyWithdrawalFeeForTransferBlock");
        this.applyWithdrawalFeeForTransferBlock.setOutputMarkupId(true);
        this.form.add(this.applyWithdrawalFeeForTransferBlock);
        this.applyWithdrawalFeeForTransferContainer = new WebMarkupContainer("applyWithdrawalFeeForTransferContainer");
        this.applyWithdrawalFeeForTransferBlock.add(this.applyWithdrawalFeeForTransferContainer);
        this.applyWithdrawalFeeForTransferField = new CheckBox("applyWithdrawalFeeForTransferField", new PropertyModel<>(this, "applyWithdrawalFeeForTransferValue"));
        this.applyWithdrawalFeeForTransferField.setRequired(false);
        this.applyWithdrawalFeeForTransferContainer.add(this.applyWithdrawalFeeForTransferField);
        this.applyWithdrawalFeeForTransferFeedback = new TextFeedbackPanel("applyWithdrawalFeeForTransferFeedback", this.applyWithdrawalFeeForTransferField);
        this.applyWithdrawalFeeForTransferContainer.add(this.applyWithdrawalFeeForTransferFeedback);

        this.overdraftAllowedBlock = new WebMarkupContainer("overdraftAllowedBlock");
        this.overdraftAllowedBlock.setOutputMarkupId(true);
        this.form.add(this.overdraftAllowedBlock);
        this.overdraftAllowedContainer = new WebMarkupContainer("overdraftAllowedContainer");
        this.overdraftAllowedBlock.add(this.overdraftAllowedContainer);
        this.overdraftAllowedField = new CheckBox("overdraftAllowedField", new PropertyModel<>(this, "overdraftAllowedValue"));
        this.overdraftAllowedField.add(new OnChangeAjaxBehavior(this::overdraftAllowedFieldUpdate));
        this.overdraftAllowedField.setRequired(false);
        this.overdraftAllowedContainer.add(this.overdraftAllowedField);
        this.overdraftAllowedFeedback = new TextFeedbackPanel("overdraftAllowedFeedback", this.overdraftAllowedField);
        this.overdraftAllowedContainer.add(this.overdraftAllowedFeedback);

        this.maximumOverdraftAmountLimitBlock = new WebMarkupContainer("maximumOverdraftAmountLimitBlock");
        this.maximumOverdraftAmountLimitBlock.setOutputMarkupId(true);
        this.form.add(this.maximumOverdraftAmountLimitBlock);
        this.maximumOverdraftAmountLimitContainer = new WebMarkupContainer("maximumOverdraftAmountLimitContainer");
        this.maximumOverdraftAmountLimitBlock.add(this.maximumOverdraftAmountLimitContainer);
        this.maximumOverdraftAmountLimitField = new TextField<>("maximumOverdraftAmountLimitField", new PropertyModel<>(this, "maximumOverdraftAmountLimitValue"));
        this.maximumOverdraftAmountLimitField.setLabel(Model.of("Maximum Overdraft Amount Limit"));
        this.maximumOverdraftAmountLimitField.setRequired(false);
        this.maximumOverdraftAmountLimitContainer.add(this.maximumOverdraftAmountLimitField);
        this.maximumOverdraftAmountLimitFeedback = new TextFeedbackPanel("maximumOverdraftAmountLimitFeedback", this.maximumOverdraftAmountLimitField);
        this.maximumOverdraftAmountLimitContainer.add(this.maximumOverdraftAmountLimitFeedback);

        this.nominalAnnualInterestForOverdraftBlock = new WebMarkupContainer("nominalAnnualInterestForOverdraftBlock");
        this.nominalAnnualInterestForOverdraftBlock.setOutputMarkupId(true);
        this.form.add(this.nominalAnnualInterestForOverdraftBlock);
        this.nominalAnnualInterestForOverdraftContainer = new WebMarkupContainer("nominalAnnualInterestForOverdraftContainer");
        this.nominalAnnualInterestForOverdraftBlock.add(this.nominalAnnualInterestForOverdraftContainer);
        this.nominalAnnualInterestForOverdraftField = new TextField<>("nominalAnnualInterestForOverdraftField", new PropertyModel<>(this, "nominalAnnualInterestForOverdraftValue"));
        this.nominalAnnualInterestForOverdraftField.setLabel(Model.of("Nominal Annual Interest For Overdraft"));
        this.nominalAnnualInterestForOverdraftField.setRequired(false);
        this.nominalAnnualInterestForOverdraftContainer.add(this.nominalAnnualInterestForOverdraftField);
        this.nominalAnnualInterestForOverdraftFeedback = new TextFeedbackPanel("nominalAnnualInterestForOverdraftFeedback", this.nominalAnnualInterestForOverdraftField);
        this.nominalAnnualInterestForOverdraftContainer.add(this.nominalAnnualInterestForOverdraftFeedback);

        this.minOverdraftRequiredForInterestCalculationBlock = new WebMarkupContainer("minOverdraftRequiredForInterestCalculationBlock");
        this.minOverdraftRequiredForInterestCalculationBlock.setOutputMarkupId(true);
        this.form.add(this.minOverdraftRequiredForInterestCalculationBlock);
        this.minOverdraftRequiredForInterestCalculationContainer = new WebMarkupContainer("minOverdraftRequiredForInterestCalculationContainer");
        this.minOverdraftRequiredForInterestCalculationBlock.add(this.minOverdraftRequiredForInterestCalculationContainer);
        this.minOverdraftRequiredForInterestCalculationField = new TextField<>("minOverdraftRequiredForInterestCalculationField", new PropertyModel<>(this, "minOverdraftRequiredForInterestCalculationValue"));
        this.minOverdraftRequiredForInterestCalculationField.setLabel(Model.of("Min Overdraft Required For Interest Calculation"));
        this.minOverdraftRequiredForInterestCalculationField.setRequired(false);
        this.minOverdraftRequiredForInterestCalculationContainer.add(this.minOverdraftRequiredForInterestCalculationField);
        this.minOverdraftRequiredForInterestCalculationFeedback = new TextFeedbackPanel("minOverdraftRequiredForInterestCalculationFeedback", this.minOverdraftRequiredForInterestCalculationField);
        this.minOverdraftRequiredForInterestCalculationContainer.add(this.minOverdraftRequiredForInterestCalculationFeedback);

        this.enforceMinimumBalanceBlock = new WebMarkupContainer("enforceMinimumBalanceBlock");
        this.enforceMinimumBalanceBlock.setOutputMarkupId(true);
        this.form.add(this.enforceMinimumBalanceBlock);
        this.enforceMinimumBalanceContainer = new WebMarkupContainer("enforceMinimumBalanceContainer");
        this.enforceMinimumBalanceBlock.add(this.enforceMinimumBalanceContainer);
        this.enforceMinimumBalanceField = new CheckBox("enforceMinimumBalanceField", new PropertyModel<>(this, "enforceMinimumBalanceValue"));
        this.enforceMinimumBalanceField.setRequired(false);
        this.enforceMinimumBalanceContainer.add(this.enforceMinimumBalanceField);
        this.enforceMinimumBalanceFeedback = new TextFeedbackPanel("enforceMinimumBalanceFeedback", this.enforceMinimumBalanceField);
        this.enforceMinimumBalanceContainer.add(this.enforceMinimumBalanceFeedback);

        this.minimumBalanceBlock = new WebMarkupContainer("minimumBalanceBlock");
        this.minimumBalanceBlock.setOutputMarkupId(true);
        this.form.add(this.minimumBalanceBlock);
        this.minimumBalanceContainer = new WebMarkupContainer("minimumBalanceContainer");
        this.minimumBalanceBlock.add(this.minimumBalanceContainer);
        this.minimumBalanceField = new TextField<>("minimumBalanceField", new PropertyModel<>(this, "minimumBalanceValue"));
        this.minimumBalanceField.setLabel(Model.of("Minimum Balance"));
        this.minimumBalanceField.setRequired(false);
        this.minimumBalanceContainer.add(this.minimumBalanceField);
        this.minimumBalanceFeedback = new TextFeedbackPanel("minimumBalanceFeedback", this.minimumBalanceField);
        this.minimumBalanceContainer.add(this.minimumBalanceFeedback);

        this.balanceRequiredForInterestCalculationField = new Label("balanceRequiredForInterestCalculationField", new PropertyModel<>(this, "balanceRequiredForInterestCalculationValue"));
        form.add(this.balanceRequiredForInterestCalculationField);

        // Table
        this.chargePopup = new ModalWindow("chargePopup");
        add(this.chargePopup);
        this.chargePopup.setOnClose(this::chargePopupOnClose);

        List<IColumn<Map<String, Object>, String>> chargeColumn = Lists.newLinkedList();
        chargeColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::chargeNameColumn));
        chargeColumn.add(new TextColumn(Model.of("Type"), "type", "type", this::chargeTypeColumn));
        chargeColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::chargeAmountColumn));
        chargeColumn.add(new TextColumn(Model.of("Collected On"), "collectedOn", "collectedOn", this::chargeCollectedOnColumn));
        chargeColumn.add(new TextColumn(Model.of("Date"), "date", "date", this::chargeDateColumn));
        chargeColumn.add(new TextColumn(Model.of("Repayments Every"), "repaymentEvery", "repaymentEvery", this::chargeRepaymentEveryColumn));
        chargeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::chargeActionItem, this::chargeActionClick));
        this.chargeProvider = new ListDataProvider(this.chargeValue);
        this.chargeTable = new DataTable<>("chargeTable", chargeColumn, this.chargeProvider, 20);
        this.form.add(this.chargeTable);
        this.chargeTable.addTopToolbar(new HeadersToolbar<>(this.chargeTable, this.chargeProvider));
        this.chargeTable.addBottomToolbar(new NoRecordsToolbar(this.chargeTable));

        this.chargeAddLink = new AjaxLink<>("chargeAddLink");
        this.chargeAddLink.setOnClick(this::chargeAddLinkClick);
        this.form.add(this.chargeAddLink);

        overdraftAllowedFieldUpdate(null);

    }

    protected ItemPanel chargeCollectedOnColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel chargeDateColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Date value = (Date) model.get(jdbcColumn);
        return new TextCell(value, "dd MMMM");
    }

    protected ItemPanel chargeNameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel chargeTypeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel chargeAmountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        return new TextCell(value, "#,###.00");
    }

    protected ItemPanel chargeRepaymentEveryColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Integer value = (Integer) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected void chargeActionClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        int index = -1;
        for (int i = 0; i < this.chargeValue.size(); i++) {
            Map<String, Object> column = this.chargeValue.get(i);
            if (model.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.chargeValue.remove(index);
        }
        target.add(this.chargeTable);
    }

    protected List<ActionItem> chargeActionItem(String s, Map<String, Object> model) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected boolean chargeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.itemChargeValue = null;
        this.itemChargeTypeValue = null;
        this.itemAmountValue = null;
        this.itemCollectedOnValue = null;
        this.itemDateValue = null;
        this.itemRepaymentEveryValue = null;
        this.chargePopup.setContent(new AccountChargePopup(this.chargePopup.getContentId(), this.chargePopup, this, this.currencyValue));
        this.chargePopup.show(target);
        return false;
    }

    protected boolean overdraftAllowedFieldUpdate(AjaxRequestTarget target) {
        boolean visible = this.overdraftAllowedValue == null ? false : this.overdraftAllowedValue;
        this.maximumOverdraftAmountLimitContainer.setVisible(visible);
        this.nominalAnnualInterestForOverdraftContainer.setVisible(visible);
        this.minOverdraftRequiredForInterestCalculationContainer.setVisible(visible);
        if (target != null) {
            target.add(this.maximumOverdraftAmountLimitBlock);
            target.add(this.nominalAnnualInterestForOverdraftBlock);
            target.add(this.minOverdraftRequiredForInterestCalculationBlock);
        }
        return false;
    }

    protected void chargePopupOnClose(String elementId, AjaxRequestTarget target) {
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", generator.externalId());
        item.put("chargeId", this.itemChargeValue.getId());
        item.put("amount", this.itemAmountValue);
        item.put("date", this.itemDateValue);
        item.put("repaymentEvery", this.itemRepaymentEveryValue);
        item.put("type", this.itemChargeTypeValue);
        item.put("name", this.itemChargeValue.getText());
        item.put("collectedOn", this.itemCollectedOnValue);
        this.chargeValue.add(item);
        target.add(this.chargeTable);
    }

    protected void initData() {
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);

        this.submittedOnValue = DateTime.now().toDate();
        this.externalIdValue = generator.externalId();

        this.groupId = getPageParameters().get("groupId").toString();
        this.savingId = getPageParameters().get("savingId").toString();

        Map<String, Object> groupObject = jdbcTemplate.queryForMap("select * from m_group where id = ?", this.groupId);
        this.officeId = String.valueOf(groupObject.get("office_id"));
        Map<String, Object> savingObject = jdbcTemplate.queryForMap("select * from m_savings_product where id = ?", this.savingId);
        this.productValue = (String) savingObject.get("name");
        this.currencyValue = (String) savingObject.get("currency_code");
        this.decimalPlacesValue = (Integer) savingObject.get("currency_digits");

        this.currencyInMultiplesOfValue = (Integer) savingObject.get("currency_multiplesof");

        BigDecimal nominalAnnualInterestValue = (BigDecimal) savingObject.get("nominal_annual_interest_rate");
        this.nominalAnnualInterestValue = nominalAnnualInterestValue == null ? null : nominalAnnualInterestValue.doubleValue();

        BigDecimal nominalAnnualInterestForOverdraftValue = (BigDecimal) savingObject.get("nominal_annual_interest_rate_overdraft");
        this.nominalAnnualInterestForOverdraftValue = nominalAnnualInterestForOverdraftValue == null ? null : nominalAnnualInterestForOverdraftValue.doubleValue();

        this.interestCompoundingPeriodValue = InterestCompoundingPeriod.optionLiteral(String.valueOf(savingObject.get("interest_compounding_period_enum")));
        this.interestPostingPeriodValue = InterestPostingPeriod.optionLiteral(String.valueOf(savingObject.get("interest_posting_period_enum")));
        this.interestCalculatedUsingValue = InterestCalculatedUsing.optionLiteral(String.valueOf(savingObject.get("interest_calculation_type_enum")));
        this.dayInYearValue = DayInYear.optionLiteral(String.valueOf(savingObject.get("interest_calculation_days_in_year_type_enum")));
        BigDecimal lockInPeriodValue = (BigDecimal) savingObject.get("lockin_period_frequency");
        this.lockInPeriodValue = lockInPeriodValue == null ? null : lockInPeriodValue.intValue();
        this.lockInTypeValue = LockInType.optionLiteral(String.valueOf(savingObject.get("lockin_period_frequency_enum")));

        Integer applyWithdrawalFeeForTransferValue = (Integer) savingObject.get("withdrawal_fee_for_transfer");
        this.applyWithdrawalFeeForTransferValue = applyWithdrawalFeeForTransferValue == null ? false : applyWithdrawalFeeForTransferValue == 1;

        BigDecimal minimumOpeningBalanceValue = (BigDecimal) savingObject.get("min_required_opening_balance");
        this.minimumOpeningBalanceValue = minimumOpeningBalanceValue == null ? null : minimumOpeningBalanceValue.doubleValue();

        this.overdraftAllowedValue = (Boolean) savingObject.get("allow_overdraft");

        BigDecimal maximumOverdraftAmountLimitValue = (BigDecimal) savingObject.get("overdraft_limit");
        this.maximumOverdraftAmountLimitValue = maximumOverdraftAmountLimitValue == null ? null : maximumOverdraftAmountLimitValue.doubleValue();

        BigDecimal minOverdraftRequiredForInterestCalculationValue = (BigDecimal) savingObject.get("min_overdraft_for_interest_calculation");
        this.minOverdraftRequiredForInterestCalculationValue = minOverdraftRequiredForInterestCalculationValue == null ? null : minOverdraftRequiredForInterestCalculationValue.doubleValue();
        this.enforceMinimumBalanceValue = (Boolean) savingObject.get("enforce_min_required_balance");

        BigDecimal minimumBalanceValue = (BigDecimal) savingObject.get("min_required_balance");
        this.minimumBalanceValue = minimumBalanceValue == null ? null : minimumBalanceValue.doubleValue();

        BigDecimal balanceRequiredForInterestCalculationValue = (BigDecimal) savingObject.get("min_balance_for_interest_calculation");
        this.balanceRequiredForInterestCalculationValue = balanceRequiredForInterestCalculationValue == null ? null : balanceRequiredForInterestCalculationValue.doubleValue();

    }

    protected void saveButtonSubmit(Button button) {
        AccountBuilder builder = new AccountBuilder();

        builder.withProductId(this.savingId);
        builder.withNominalAnnualInterestRate(this.nominalAnnualInterestValue);

        builder.withMinRequiredOpeningBalance(this.minimumOpeningBalanceValue);

        builder.withWithdrawalFeeForTransfers(this.applyWithdrawalFeeForTransferValue == null ? false : this.applyWithdrawalFeeForTransferValue);

        boolean allowOverdraft = this.overdraftAllowedValue == null ? false : this.overdraftAllowedValue;
        builder.withAllowOverdraft(allowOverdraft);

        if (allowOverdraft) {
            builder.withOverdraftLimit(this.maximumOverdraftAmountLimitValue);
            builder.withNominalAnnualInterestRateOverdraft(this.nominalAnnualInterestForOverdraftValue);
            builder.withMinOverdraftForInterestCalculation(this.minOverdraftRequiredForInterestCalculationValue);
        }

        builder.withEnforceMinRequiredBalance(this.enforceMinimumBalanceValue == null ? false : this.enforceMinimumBalanceValue);
        builder.withMinRequiredBalance(this.minimumBalanceValue);
        builder.withHoldTax(false);
        if (this.interestCompoundingPeriodValue != null) {
            builder.withInterestCompoundingPeriodType(InterestCompoundingPeriod.valueOf(this.interestCompoundingPeriodValue.getId()));
        }
        if (this.interestPostingPeriodValue != null) {
            builder.withInterestPostingPeriodType(InterestPostingPeriod.valueOf(this.interestPostingPeriodValue.getId()));
        }
        if (this.interestCalculatedUsingValue != null) {
            builder.withInterestCalculationType(InterestCalculatedUsing.valueOf(this.interestCalculatedUsingValue.getId()));
        }
        if (this.dayInYearValue != null) {
            builder.withInterestCalculationDaysInYearType(DayInYear.valueOf(this.dayInYearValue.getId()));
        }
        builder.withLockinPeriodFrequency(this.lockInPeriodValue);
        if (this.lockInTypeValue != null) {
            builder.withLockinPeriodFrequencyType(LockInType.valueOf(this.lockInTypeValue.getId()));
        }
        if (this.officerValue != null) {
            builder.withFieldOfficerId(this.officerValue.getId());
        }
        builder.withExternalId(this.externalIdValue);
        builder.withSubmittedOnDate(this.submittedOnValue);

        for (Map<String, Object> charge : this.chargeValue) {
            builder.withCharge((String) charge.get("chargeId"), (Double) charge.get("amount"), (Date) charge.get("date"), (Integer) charge.get("repaymentEvery"));
        }

        builder.withGroupId(this.groupId);

        JsonNode node = null;
        JsonNode request = builder.build();
        try {
            node = ClientHelper.createSavingAccount((Session) getSession(), request);
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        PageParameters parameters = new PageParameters();
        parameters.add("groupId", this.groupId);
        setResponsePage(GroupPreviewPage.class, parameters);

    }
}