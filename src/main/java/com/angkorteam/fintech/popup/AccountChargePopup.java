package com.angkorteam.fintech.popup;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.angkorteam.fintech.ddl.MCharge;
import com.angkorteam.fintech.dto.enums.ChargeCalculation;
import com.angkorteam.fintech.dto.enums.ChargeTime;
import com.angkorteam.fintech.dto.enums.ChargeType;
import com.angkorteam.fintech.dto.enums.ProductPopup;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.DayMonthTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class AccountChargePopup extends PopupPanel {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AccountChargePopup.class);

    protected Form<Void> form;
    protected AjaxButton okayButton;

    protected UIRow row1;

    protected UIBlock chargeBlock;
    protected UIContainer chargeIContainer;
    protected SingleChoiceProvider chargeProvider;
    protected Select2SingleChoice<Option> chargeField;
    protected PropertyModel<Option> chargeValue;

    protected UIBlock chargeTypeBlock;
    protected UIContainer chargeTypeIContainer;
    protected ReadOnlyView chargeTypeView;
    protected PropertyModel<Option> chargeTypeValue;

    protected UIRow row2;

    protected UIBlock amountBlock;
    protected UIContainer amountIContainer;
    protected TextField<Double> amountField;
    protected PropertyModel<Double> amountValue;

    protected UIBlock collectedOnBlock;
    protected UIContainer collectedOnIContainer;
    protected ReadOnlyView collectedOnView;
    protected PropertyModel<Option> collectedOnValue;

    protected UIRow row3;

    protected UIBlock dayMonthBlock;
    protected UIContainer dayMonthIContainer;
    protected DayMonthTextField dayMonthField;
    protected PropertyModel<Date> dayMonthValue;

    protected UIBlock row3Block1;

    protected UIRow row4;

    protected UIBlock dateBlock;
    protected UIContainer dateIContainer;
    protected DateTextField dateField;
    protected PropertyModel<Date> dateValue;

    protected UIBlock row4Block1;

    protected UIRow row5;

    protected UIBlock repaymentEveryBlock;
    protected UIContainer repaymentEveryIContainer;
    protected TextField<Long> repaymentEveryField;
    protected PropertyModel<Long> repaymentEveryValue;

    protected UIBlock row5Block1;

    protected String currencyCode;

    protected ProductPopup productPopup;

    public AccountChargePopup(String name, Map<String, Object> model, ProductPopup productPopup, String currencyCode) {
        super(name, model);
        this.currencyCode = currencyCode;
        this.productPopup = productPopup;
    }

    @Override
    protected void initData() {
        this.chargeValue = new PropertyModel<>(this.model, "chargeValue");
        this.chargeTypeValue = new PropertyModel<>(this.model, "chargeTypeValue");
        this.amountValue = new PropertyModel<>(this.model, "amountValue");
        this.collectedOnValue = new PropertyModel<>(this.model, "collectedOnValue");
        this.dayMonthValue = new PropertyModel<>(this.model, "dayMonthValue");
        this.dateValue = new PropertyModel<>(this.model, "dateValue");
        this.repaymentEveryValue = new PropertyModel<>(this.model, "repaymentEveryValue");

        this.chargeProvider = new SingleChoiceProvider(MCharge.NAME, MCharge.Field.ID, MCharge.Field.NAME);
        this.chargeProvider.applyWhere("currency_code", MCharge.Field.CURRENCY_CODE + " = '" + this.currencyCode + "'");
        if (this.productPopup == ProductPopup.Share) {
            this.chargeProvider.applyWhere("charge_applies_to_enum", MCharge.Field.CHARGE_APPLIES_TO_ENUM + " = " + ChargeType.Share.getLiteral());
        } else if (this.productPopup == ProductPopup.Loan) {
            this.chargeProvider.applyWhere("charge_applies_to_enum", MCharge.Field.CHARGE_APPLIES_TO_ENUM + " = " + ChargeType.Loan.getLiteral());
        } else if (this.productPopup == ProductPopup.Fixed || this.productPopup == ProductPopup.Saving || this.productPopup == ProductPopup.Recurring) {
            this.chargeProvider.applyWhere("charge_applies_to_enum", MCharge.Field.CHARGE_APPLIES_TO_ENUM + " = " + ChargeType.SavingDeposit.getLiteral());
        }
        this.chargeProvider.applyWhere("is_active", MCharge.Field.IS_ACTIVE + " = 1");
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.okayButton = new AjaxButton("okayButton");
        this.okayButton.setOnSubmit(this::okayButtonSubmit);
        this.form.add(this.okayButton);

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.chargeBlock = this.row1.newUIBlock("chargeBlock", Size.Six_6);
        this.chargeIContainer = this.chargeBlock.newUIContainer("chargeIContainer");
        this.chargeField = new Select2SingleChoice<>("chargeField", this.chargeValue, this.chargeProvider);
        this.chargeIContainer.add(this.chargeField);
        this.chargeIContainer.newFeedback("chargeFeedback", this.chargeField);

        this.chargeTypeBlock = this.row1.newUIBlock("chargeTypeBlock", Size.Six_6);
        this.chargeTypeIContainer = this.chargeTypeBlock.newUIContainer("chargeTypeIContainer");
        this.chargeTypeView = new ReadOnlyView("chargeTypeView", this.chargeTypeValue);
        this.chargeTypeIContainer.add(this.chargeTypeView);

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.amountBlock = this.row2.newUIBlock("amountBlock", Size.Six_6);
        this.amountIContainer = this.amountBlock.newUIContainer("amountIContainer");
        this.amountField = new TextField<>("amountField", this.amountValue);
        this.amountIContainer.add(this.amountField);
        this.amountIContainer.newFeedback("amountFeedback", this.amountField);

        this.collectedOnBlock = this.row2.newUIBlock("collectedOnBlock", Size.Six_6);
        this.collectedOnIContainer = this.collectedOnBlock.newUIContainer("collectedOnIContainer");
        this.collectedOnView = new ReadOnlyView("collectedOnView", this.collectedOnValue);
        this.collectedOnIContainer.add(this.collectedOnView);

        this.row3 = UIRow.newUIRow("row3", this.form);

        this.dayMonthBlock = this.row3.newUIBlock("dayMonthBlock", Size.Six_6);
        this.dayMonthIContainer = this.dayMonthBlock.newUIContainer("dayMonthIContainer");
        this.dayMonthField = new DayMonthTextField("dayMonthField", this.dayMonthValue);
        this.dayMonthIContainer.add(this.dayMonthField);
        this.dayMonthIContainer.newFeedback("dayMonthFeedback", this.dayMonthField);

        this.row3Block1 = this.row3.newUIBlock("row3Block1", Size.Six_6);

        this.row4 = UIRow.newUIRow("row4", this.form);

        this.dateBlock = this.row4.newUIBlock("dateBlock", Size.Six_6);
        this.dateIContainer = this.dateBlock.newUIContainer("dateIContainer");
        this.dateField = new DateTextField("dateField", this.dateValue);
        this.dateIContainer.add(this.dateField);
        this.dateIContainer.newFeedback("dateFeedback", this.dateField);

        this.row4Block1 = this.row4.newUIBlock("row4Block1", Size.Six_6);

        this.row5 = UIRow.newUIRow("row5", this.form);

        this.repaymentEveryBlock = this.row5.newUIBlock("repaymentEveryBlock", Size.Six_6);
        this.repaymentEveryIContainer = this.repaymentEveryBlock.newUIContainer("repaymentEveryIContainer");
        this.repaymentEveryField = new TextField<>("repaymentEveryField", this.repaymentEveryValue);
        this.repaymentEveryIContainer.add(this.repaymentEveryField);
        this.repaymentEveryIContainer.newFeedback("repaymentEveryFeedback", this.repaymentEveryField);

        this.row5Block1 = this.row5.newUIBlock("row5Block1", Size.Six_6);
    }

    @Override
    protected void configureMetaData() {
        this.repaymentEveryField.setType(Long.class);
        this.repaymentEveryField.setLabel(Model.of("repaymentEvery"));

        this.dateField.setLabel(Model.of("Day Month"));

        this.dayMonthField.setLabel(Model.of("Day Month"));

        this.amountField.setType(Double.class);
        this.amountField.setLabel(Model.of("Amount"));

        this.chargeField.setRequired(true);
        this.chargeField.add(new OnChangeAjaxBehavior(this::chargeFieldUpdate));
        this.chargeField.setLabel(Model.of("Charge"));

        chargeFieldUpdate(null);
    }

    protected boolean chargeFieldUpdate(AjaxRequestTarget target) {
        if (this.chargeValue.getObject() != null) {
            this.chargeTypeIContainer.setVisible(true);
            this.amountIContainer.setVisible(true);
            this.collectedOnIContainer.setVisible(true);

            JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
            Map<String, Object> chargeObject = jdbcTemplate.queryForMap("select * from m_charge where id = ?", this.chargeValue.getObject().getId());
            ChargeTime chargeTime = ChargeTime.parseLiteral(String.valueOf(chargeObject.get("charge_time_enum")));
            this.model.put("chargeTime", chargeObject.get("charge_time_enum"));
            this.repaymentEveryIContainer.setVisible(chargeTime == ChargeTime.MonthlyFee || chargeTime == ChargeTime.WeeklyFee);
            this.dayMonthIContainer.setVisible(chargeTime == ChargeTime.AnnualFee || chargeTime == ChargeTime.MonthlyFee);
            this.dateIContainer.setVisible(chargeTime == ChargeTime.SpecifiedDueDate || chargeTime == ChargeTime.WeeklyFee);
            Long collectedOn = (Long) chargeObject.get("charge_time_enum");
            if (collectedOn != null) {
                this.collectedOnValue.setObject(ChargeTime.optionLiteral(String.valueOf(collectedOn)));
            } else {
                this.collectedOnValue.setObject(null);
            }

            Double amount = (Double) chargeObject.get("amount");
            if (amount != null) {
                this.amountValue.setObject(amount);
            } else {
                this.amountValue.setObject(null);
            }

            Long type = (Long) chargeObject.get("charge_calculation_enum");
            if (type != null) {
                this.chargeTypeValue.setObject(ChargeCalculation.optionLiteral(String.valueOf(type)));
            } else {
                this.chargeTypeValue.setObject(null);
            }

            Long repaymentEveryValue = (Long) chargeObject.get("fee_interval");
            this.repaymentEveryValue.setObject(repaymentEveryValue);

            Long month = (Long) chargeObject.get("fee_on_month");
            Long day = (Long) chargeObject.get("fee_on_day");
            if (day != null && month != null) {
                try {
                    this.dayMonthValue.setObject(DateUtils.parseDate(day + "/" + month, "d/M"));
                } catch (ParseException e) {
                    LOGGER.info(e.getMessage());
                }
            }

        } else {
            this.chargeTypeIContainer.setVisible(false);
            this.amountIContainer.setVisible(false);
            this.collectedOnIContainer.setVisible(false);
            this.dayMonthIContainer.setVisible(false);
            this.dateIContainer.setVisible(false);
            this.repaymentEveryIContainer.setVisible(false);
        }
        if (target != null) {
            target.add(this.chargeTypeBlock);
            target.add(this.amountBlock);
            target.add(this.collectedOnBlock);
            target.add(this.dateBlock);
            target.add(this.dayMonthBlock);
            target.add(this.repaymentEveryBlock);
        }
        return false;
    }

    protected boolean okayButtonSubmit(AjaxButton ajaxButton, AjaxRequestTarget target) {
        this.window.setSignalId(ajaxButton.getId());
        this.window.close(target);
        return true;
    }

}