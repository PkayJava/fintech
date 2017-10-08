package com.angkorteam.fintech.pages.client.center;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.provider.InterestCompoundingPeriodProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;

public class AccountCreatePage extends Page {

    protected String centerId;
    protected String productId;
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

    protected String decimalPlacesValue;
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

    protected String currencyInMultiplesOfValue;
    protected Label currencyInMultiplesOfField;

    protected SingleChoiceProvider interestPostingPeriodProvider;
    protected WebMarkupContainer interestPostingPeriodBlock;
    protected WebMarkupContainer interestPostingPeriodContainer;
    protected Option interestPostingPeriodValue;
    protected Select2SingleChoice<Option> interestPostingPeriodField;
    protected TextFeedbackPanel interestPostingPeriodFeedback;

    protected SingleChoiceProvider interestCalculatedUsingProvider;
    protected WebMarkupContainer interestCalculatedUsingBlock;
    protected WebMarkupContainer interestCalculatedUsingContainer;
    protected Option interestCalculatedUsingValue;
    protected Select2SingleChoice<Option> interestCalculatedUsingField;
    protected TextFeedbackPanel interestCalculatedUsingFeedback;

    protected SingleChoiceProvider dayInYearProvider;
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

    protected SingleChoiceProvider lockInTypeProvider;
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

    protected List<Map<String, Object>> chargeValue = Lists.newLinkedList();
    protected DataTable<Map<String, Object>, String> chargeTable;
    protected ListDataProvider chargeProvider;
    protected ModalWindow chargePopup;
    protected AjaxLink<Void> chargeAddLink;

    @Override
    protected void onInitialize() {
        super.onInitialize();
        initData();

        PageParameters parameters = new PageParameters();
        parameters.add("centerId", this.centerId);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", AccountPreviewPage.class, parameters);
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

    }

    protected void initData() {
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        this.centerId = getPageParameters().get("centerId").toString();
        Map<String, Object> centerObject = jdbcTemplate.queryForMap("select * from m_group where id = ?", this.centerId);
        this.officeId = String.valueOf(centerObject.get("office_id"));
        this.productId = getPageParameters().get("productId").toString();
        Map<String, Object> productObject = jdbcTemplate.queryForMap("select * from m_savings_product where id = ?", this.productId);
        this.productValue = (String) productObject.get("name");
        this.currencyValue = (String) productObject.get("currency_code");
        this.decimalPlacesValue = String.valueOf(productObject.get("currency_digits"));
        this.nominalAnnualInterestValue = (Double) productObject.get("nominal_annual_interest_rate");
        this.interestCompoundingPeriodValue = (Option) productObject.get("interest_compounding_period_enum");
        this.interestPostingPeriodValue = (Option) productObject.get("interest_posting_period_enum");
        this.interestCalculatedUsingValue = (Option) productObject.get("interest_calculation_type_enum");
        this.dayInYearValue = (Option) productObject.get("interest_calculation_days_in_year_type_enum");
        this.lockInPeriodValue = (Integer) productObject.get("lockin_period_frequency");
        this.lockInTypeValue = (Option) productObject.get("lockin_period_frequency_enum");
        this.applyWithdrawalFeeForTransferValue = (Boolean) productObject.get("withdrawal_fee_for_transfer");
        this.minimumOpeningBalanceValue = (Double) productObject.get("min_required_opening_balance");
        this.overdraftAllowedValue = (Boolean) productObject.get("allow_overdraft");
        this.maximumOverdraftAmountLimitValue = (Double) productObject.get("overdraft_limit");
        this.nominalAnnualInterestValue = (Double) productObject.get("nominal_annual_interest_rate");
        this.minOverdraftRequiredForInterestCalculationValue = (Double) productObject.get("min_overdraft_for_interest_calculation");
        this.enforceMinimumBalanceValue = (Boolean) productObject.get("enforce_min_required_balance");
        this.minimumBalanceValue = (Double) productObject.get("min_required_balance");

        // select * from m_charge where currency_code = 'USD' and charge_applies_to_enum
        // = 2 and is_active = 1;
    }

    protected void saveButtonSubmit(Button button) {

    }

}
