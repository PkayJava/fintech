package com.angkorteam.fintech.pages.client.common.saving;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.joda.time.DateTime;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.MCharge;
import com.angkorteam.fintech.ddl.MClient;
import com.angkorteam.fintech.ddl.MGroup;
import com.angkorteam.fintech.ddl.MSavingsProduct;
import com.angkorteam.fintech.ddl.MSavingsProductCharge;
import com.angkorteam.fintech.ddl.MTaxGroup;
import com.angkorteam.fintech.dto.ClientEnum;
import com.angkorteam.fintech.dto.builder.SavingAccountBuilder;
import com.angkorteam.fintech.dto.enums.ChargeCalculation;
import com.angkorteam.fintech.dto.enums.ChargeTime;
import com.angkorteam.fintech.dto.enums.DayInYear;
import com.angkorteam.fintech.dto.enums.InterestCalculatedUsing;
import com.angkorteam.fintech.dto.enums.InterestCompoundingPeriod;
import com.angkorteam.fintech.dto.enums.InterestPostingPeriod;
import com.angkorteam.fintech.dto.enums.LockInType;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.fintech.widget.client.common.saving.Charges;
import com.angkorteam.fintech.widget.client.common.saving.Details;
import com.angkorteam.fintech.widget.client.common.saving.Review;
import com.angkorteam.fintech.widget.client.common.saving.Terms;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.github.openunirest.http.JsonNode;

public class AccountCreatePage extends Page {

    public static int TAB_DETAIL = 0;
    public static int TAB_TERM = 1;
    public static int TAB_CHARGE = 2;
    public static int TAB_REVIEW = 3;

    protected ClientEnum client;

    protected String clientId;

    protected String savingId;
    protected String officeId;

    protected String clientDisplayName;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected String detailProductNameValue;
    protected Date detailSubmittedOnValue;
    protected Option detailOfficerValue;
    protected String detailExternalIdValue;

    protected String termCurrencyValue;
    protected Long termDecimalPlacesValue;
    protected Double termNominalAnnualInterestValue;
    protected Option termInterestCompoundingPeriodValue;
    protected Long termCurrencyInMultiplesOfValue;
    protected Option termInterestPostingPeriodValue;
    protected Option termInterestCalculatedUsingValue;
    protected Option termDayInYearValue;
    protected Double termMinimumOpeningBalanceValue;
    protected Long termLockInPeriodValue;
    protected Option termLockInTypeValue;
    protected Boolean termApplyWithdrawalFeeForTransferValue;
    protected Boolean termOverdraftAllowedValue;
    protected Double termMaximumOverdraftAmountLimitValue;
    protected Double termNominalAnnualInterestForOverdraftValue;
    protected Double termMinOverdraftRequiredForInterestCalculationValue;
    protected Boolean termEnforceMinimumBalanceValue;
    protected Double termMinimumBalanceValue;
    protected Double termBalanceRequiredForInterestCalculationValue;
    protected Boolean termWithHoldValue;
    protected String termTaxGroupValue;

    protected List<Map<String, Object>> chargeValue;

    protected AjaxTabbedPanel<ITab> tab;

    protected boolean errorDetail;
    protected boolean errorTerm;
    protected boolean errorCharge;

    @Override
    protected void initData() {
        this.errorDetail = true;
        this.errorTerm = true;
        this.errorCharge = true;

        this.client = ClientEnum.valueOf(getPageParameters().get("client").toString());

        this.clientId = getPageParameters().get("clientId").toString();

        this.savingId = getPageParameters().get("savingId").toString();

        this.chargeValue = Lists.newLinkedList();

        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        StringGenerator generator = SpringBean.getBean(StringGenerator.class);

        this.detailSubmittedOnValue = DateTime.now().toDate();
        this.detailExternalIdValue = generator.externalId();

        SelectQuery selectQuery = null;

        if (this.client == ClientEnum.Client) {
            selectQuery = new SelectQuery(MClient.NAME);
            selectQuery.addField(MClient.Field.OFFICE_ID);
            selectQuery.addField(MClient.Field.DISPLAY_NAME);
            selectQuery.addWhere(MClient.Field.ID + " = :" + MClient.Field.ID, this.clientId);
            Map<String, Object> clientObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
            this.officeId = String.valueOf(clientObject.get("office_id"));
            this.clientDisplayName = (String) clientObject.get("display_name");
        } else if (this.client == ClientEnum.Group || this.client == ClientEnum.Center) {
            selectQuery = new SelectQuery(MGroup.NAME);
            selectQuery.addField(MGroup.Field.OFFICE_ID);
            selectQuery.addField(MGroup.Field.DISPLAY_NAME);
            selectQuery.addWhere(MGroup.Field.ID + " = :" + MClient.Field.ID, this.clientId);
            Map<String, Object> groupObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
            this.officeId = String.valueOf(groupObject.get("office_id"));
            this.clientDisplayName = (String) groupObject.get("display_name");
        }

        selectQuery = new SelectQuery(MSavingsProduct.NAME);
        selectQuery.addWhere(MSavingsProduct.Field.ID + " = :" + MSavingsProduct.Field.ID, this.savingId);
        selectQuery.addField(MSavingsProduct.Field.NAME);
        selectQuery.addField(MSavingsProduct.Field.CURRENCY_CODE);
        selectQuery.addField(MSavingsProduct.Field.CURRENCY_DIGITS);
        selectQuery.addField(MSavingsProduct.Field.CURRENCY_MULTIPLES_OF);
        selectQuery.addField(MSavingsProduct.Field.NOMINAL_ANNUAL_INTEREST_RATE);
        selectQuery.addField(MSavingsProduct.Field.NOMINAL_ANNUAL_INTEREST_RATE_OVERDRAFT);
        selectQuery.addField(MSavingsProduct.Field.INTEREST_COMPOUNDING_PERIOD_ENUM);
        selectQuery.addField(MSavingsProduct.Field.INTEREST_POSTING_PERIOD_ENUM);
        selectQuery.addField(MSavingsProduct.Field.INTEREST_CALCULATION_TYPE_ENUM);
        selectQuery.addField(MSavingsProduct.Field.INTEREST_CALCULATION_DAYS_IN_YEAR_TYPE_ENUM);
        selectQuery.addField(MSavingsProduct.Field.LOCKIN_PERIOD_FREQUENCY);
        selectQuery.addField(MSavingsProduct.Field.LOCKIN_PERIOD_FREQUENCY_ENUM);
        selectQuery.addField(MSavingsProduct.Field.WITHDRAWAL_FEE_FOR_TRANSFER);
        selectQuery.addField(MSavingsProduct.Field.MIN_REQUIRED_OPENING_BALANCE);
        selectQuery.addField(MSavingsProduct.Field.ALLOW_OVERDRAFT);
        selectQuery.addField(MSavingsProduct.Field.OVERDRAFT_LIMIT);
        selectQuery.addField(MSavingsProduct.Field.MIN_OVERDRAFT_FOR_INTEREST_CALCULATION);
        selectQuery.addField(MSavingsProduct.Field.ENFORCE_MIN_REQUIRED_BALANCE);
        selectQuery.addField(MSavingsProduct.Field.MIN_REQUIRED_BALANCE);
        selectQuery.addField(MSavingsProduct.Field.MIN_BALANCE_FOR_INTEREST_CALCULATION);
        selectQuery.addField(MSavingsProduct.Field.WITHHOLD_TAX);
        selectQuery.addField(MSavingsProduct.Field.TAX_GROUP_ID);
        Map<String, Object> savingProductObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        this.detailProductNameValue = (String) savingProductObject.get("name");
        this.termCurrencyValue = (String) savingProductObject.get("currency_code");
        this.termDecimalPlacesValue = (Long) savingProductObject.get("currency_digits");

        this.termCurrencyInMultiplesOfValue = (Long) savingProductObject.get("currency_multiplesof");

        this.termNominalAnnualInterestValue = (Double) savingProductObject.get("nominal_annual_interest_rate");

        this.termNominalAnnualInterestForOverdraftValue = (Double) savingProductObject.get("nominal_annual_interest_rate_overdraft");

        this.termInterestCompoundingPeriodValue = InterestCompoundingPeriod.optionLiteral(String.valueOf(savingProductObject.get("interest_compounding_period_enum")));
        this.termInterestPostingPeriodValue = InterestPostingPeriod.optionLiteral(String.valueOf(savingProductObject.get("interest_posting_period_enum")));
        this.termInterestCalculatedUsingValue = InterestCalculatedUsing.optionLiteral(String.valueOf(savingProductObject.get("interest_calculation_type_enum")));
        this.termDayInYearValue = DayInYear.optionLiteral(String.valueOf(savingProductObject.get("interest_calculation_days_in_year_type_enum")));
        Double lockin_period_frequency = (Double) savingProductObject.get("lockin_period_frequency");
        this.termLockInPeriodValue = lockin_period_frequency != null ? lockin_period_frequency.longValue() : null;
        this.termLockInTypeValue = LockInType.optionLiteral(String.valueOf(savingProductObject.get("lockin_period_frequency_enum")));

        Long applyWithdrawalFeeForTransferValue = (Long) savingProductObject.get("withdrawal_fee_for_transfer");
        this.termApplyWithdrawalFeeForTransferValue = applyWithdrawalFeeForTransferValue == null ? false : applyWithdrawalFeeForTransferValue == 1;

        this.termMinimumOpeningBalanceValue = (Double) savingProductObject.get("min_required_opening_balance");

        this.termOverdraftAllowedValue = (Boolean) savingProductObject.get("allow_overdraft");

        this.termMaximumOverdraftAmountLimitValue = (Double) savingProductObject.get("overdraft_limit");

        this.termMinOverdraftRequiredForInterestCalculationValue = (Double) savingProductObject.get("min_overdraft_for_interest_calculation");
        this.termEnforceMinimumBalanceValue = (Boolean) savingProductObject.get("enforce_min_required_balance");

        this.termMinimumBalanceValue = (Double) savingProductObject.get("min_required_balance");

        this.termBalanceRequiredForInterestCalculationValue = (Double) savingProductObject.get("min_balance_for_interest_calculation");

        this.termWithHoldValue = savingProductObject.get("withhold_tax") == null ? false : ((Long) savingProductObject.get("withhold_tax")) == 1;

        selectQuery = new SelectQuery(MTaxGroup.NAME);
        selectQuery.addWhere(MTaxGroup.Field.ID + " = :" + MTaxGroup.Field.ID, savingProductObject.get("tax_group_id"));
        selectQuery.addField(MTaxGroup.Field.NAME);
        this.termTaxGroupValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), String.class);

        selectQuery = new SelectQuery(MSavingsProductCharge.NAME);
        selectQuery.addJoin("INNER JOIN " + MCharge.NAME + " ON " + MSavingsProductCharge.NAME + "." + MSavingsProductCharge.Field.CHARGE_ID + " = " + MCharge.NAME + "." + MCharge.Field.ID);
        selectQuery.addWhere(MSavingsProductCharge.NAME + "." + MSavingsProductCharge.Field.SAVINGS_PRODUCT_ID + " = :" + MSavingsProductCharge.Field.SAVINGS_PRODUCT_ID, this.savingId);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.ID);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.NAME);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.AMOUNT);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.CHARGE_TIME_ENUM);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.CHARGE_CALCULATION_ENUM);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.FEE_INTERVAL);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.FEE_ON_DAY);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.FEE_ON_MONTH);
        List<Map<String, Object>> charges = named.queryForList(selectQuery.toSQL(), selectQuery.getParam());
        if (charges != null && !charges.isEmpty()) {
            for (Map<String, Object> chargeObject : charges) {
                Map<String, Object> item = Maps.newHashMap();
                item.put("uuid", generator.externalId());
                Option charge = new Option(String.valueOf(chargeObject.get("id")), (String) chargeObject.get("name"));
                item.put("charge", charge);
                item.put("name", charge);
                item.put("chargeTime", chargeObject.get("charge_time_enum"));
                item.put("amount", chargeObject.get("amount"));

                item.put("collectedOn", ChargeTime.optionLiteral(String.valueOf(chargeObject.get("charge_time_enum"))));

                item.put("type", ChargeCalculation.optionLiteral(String.valueOf(chargeObject.get("charge_calculation_enum"))));

                Long repaymentEveryValue = (Long) chargeObject.get("fee_interval");
                item.put("repaymentEvery", repaymentEveryValue == null ? null : repaymentEveryValue.intValue());

                Long month = (Long) chargeObject.get("fee_on_month");
                Long day = (Long) chargeObject.get("fee_on_day");
                if (day != null && month != null) {
                    try {
                        item.put("dayMonth", DateUtils.parseDate(day + "/" + month, "d/M"));
                    } catch (ParseException e) {
                    }
                }
                this.chargeValue.add(item);
            }
        }
    }

    @Override
    protected void initComponent() {
        this.tab = new AjaxTabbedPanel<>("tab", Arrays.asList(new Details(this), new Terms(this), new Charges(this), new Review(this)));
        add(this.tab);
    }

    @Override
    protected void configureMetaData() {
    }

    public void saveButtonSubmit(Button button) {
        SavingAccountBuilder builder = new SavingAccountBuilder();

        builder.withProductId(this.savingId);
        builder.withNominalAnnualInterestRate(this.termNominalAnnualInterestValue);

        builder.withMinRequiredOpeningBalance(this.termMinimumOpeningBalanceValue);

        builder.withWithdrawalFeeForTransfers(this.termApplyWithdrawalFeeForTransferValue == null ? false : this.termApplyWithdrawalFeeForTransferValue);

        boolean allowOverdraft = this.termOverdraftAllowedValue == null ? false : this.termOverdraftAllowedValue;
        builder.withAllowOverdraft(allowOverdraft);

        if (allowOverdraft) {
            builder.withOverdraftLimit(this.termMaximumOverdraftAmountLimitValue);
            builder.withNominalAnnualInterestRateOverdraft(this.termNominalAnnualInterestForOverdraftValue);
            builder.withMinOverdraftForInterestCalculation(this.termMinOverdraftRequiredForInterestCalculationValue);
        }

        builder.withEnforceMinRequiredBalance(this.termEnforceMinimumBalanceValue == null ? false : this.termEnforceMinimumBalanceValue);
        builder.withMinRequiredBalance(this.termMinimumBalanceValue);
        builder.withHoldTax(false);
        if (this.termInterestCompoundingPeriodValue != null) {
            builder.withInterestCompoundingPeriodType(InterestCompoundingPeriod.valueOf(this.termInterestCompoundingPeriodValue.getId()));
        }
        if (this.termInterestPostingPeriodValue != null) {
            builder.withInterestPostingPeriodType(InterestPostingPeriod.valueOf(this.termInterestPostingPeriodValue.getId()));
        }
        if (this.termInterestCalculatedUsingValue != null) {
            builder.withInterestCalculationType(InterestCalculatedUsing.valueOf(this.termInterestCalculatedUsingValue.getId()));
        }
        if (this.termDayInYearValue != null) {
            builder.withInterestCalculationDaysInYearType(DayInYear.valueOf(this.termDayInYearValue.getId()));
        }
        builder.withLockinPeriodFrequency(this.termLockInPeriodValue);
        if (this.termLockInTypeValue != null) {
            builder.withLockinPeriodFrequencyType(LockInType.valueOf(this.termLockInTypeValue.getId()));
        }
        if (this.detailOfficerValue != null) {
            builder.withFieldOfficerId(this.detailOfficerValue.getId());
        }
        builder.withExternalId(this.detailExternalIdValue);
        builder.withSubmittedOnDate(this.detailSubmittedOnValue);

        for (Map<String, Object> charge : this.chargeValue) {
            String chargeId = ((Option) charge.get("charge")).getId();
            Double amount = (Double) charge.get("amount");
            Date feeOnMonthDay = (Date) charge.get("dayMonth");
            Date dueDate = (Date) charge.get("date");
            Long feeInterval = (Long) charge.get("repaymentEvery");
            builder.withCharge(chargeId, amount, feeOnMonthDay, dueDate, feeInterval);
        }

        if (this.client == ClientEnum.Client) {
            builder.withClientId(this.clientId);
        } else if (this.client == ClientEnum.Group) {
            builder.withGroupId(this.clientId);
        } else if (this.client == ClientEnum.Center) {
            builder.withGroupId(this.clientId);
        }

        JsonNode request = builder.build();
        JsonNode node = ClientHelper.createSavingAccount((Session) getSession(), request);

        if (reportError(node)) {
            return;
        }
        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);
        if (this.client == ClientEnum.Client) {
            setResponsePage(ClientPreviewPage.class, parameters);
        } else if (this.client == ClientEnum.Center) {
            setResponsePage(CenterPreviewPage.class, parameters);
        } else if (this.client == ClientEnum.Group) {
            setResponsePage(GroupPreviewPage.class, parameters);
        }
    }

}
