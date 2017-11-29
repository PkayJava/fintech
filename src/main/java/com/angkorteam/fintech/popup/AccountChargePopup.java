package com.angkorteam.fintech.popup;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.angkorteam.fintech.dto.enums.ChargeCalculation;
import com.angkorteam.fintech.dto.enums.ChargeTime;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.DayMonthTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class AccountChargePopup extends PopupPanel {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AccountChargePopup.class);

    protected ModalWindow window;
    protected Map<String, Object> model;

    protected Form<Void> form;
    protected AjaxButton okayButton;

    protected WebMarkupBlock chargeBlock;
    protected WebMarkupContainer chargeIContainer;
    protected SingleChoiceProvider chargeProvider;
    protected Select2SingleChoice<Option> chargeField;
    protected TextFeedbackPanel chargeFeedback;
    protected PropertyModel<Option> chargeValue;

    protected WebMarkupBlock chargeTypeBlock;
    protected WebMarkupContainer chargeTypeIContainer;
    protected ReadOnlyView chargeTypeView;
    protected PropertyModel<Option> chargeTypeValue;

    protected WebMarkupBlock amountBlock;
    protected WebMarkupContainer amountIContainer;
    protected TextField<Double> amountField;
    protected TextFeedbackPanel amountFeedback;
    protected PropertyModel<Double> amountValue;

    protected WebMarkupBlock collectedOnBlock;
    protected WebMarkupContainer collectedOnIContainer;
    protected ReadOnlyView collectedOnView;
    protected PropertyModel<Option> collectedOnValue;

    protected WebMarkupBlock dayMonthBlock;
    protected WebMarkupContainer dayMonthIContainer;
    protected DayMonthTextField dayMonthField;
    protected TextFeedbackPanel dayMonthFeedback;
    protected PropertyModel<Date> dayMonthValue;

    protected WebMarkupBlock dateBlock;
    protected WebMarkupContainer dateIContainer;
    protected DateTextField dateField;
    protected TextFeedbackPanel dateFeedback;
    protected PropertyModel<Date> dateValue;

    protected WebMarkupBlock repaymentEveryBlock;
    protected WebMarkupContainer repaymentEveryIContainer;
    protected TextField<Long> repaymentEveryField;
    protected TextFeedbackPanel repaymentEveryFeedback;
    protected PropertyModel<Long> repaymentEveryValue;

    protected String currencyCode;

    public AccountChargePopup(String name, ModalWindow window, Map<String, Object> model, String currencyCode) {
        super(name, window);
        this.model = model;
        this.window = window;
        this.currencyCode = currencyCode;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.okayButton = new AjaxButton("okayButton");
        this.okayButton.setOnSubmit(this::okayButtonSubmit);
        this.form.add(this.okayButton);

        this.chargeValue = new PropertyModel<>(this.model, "chargeValue");
        this.chargeProvider = new SingleChoiceProvider("m_charge", "id", "name");
        this.chargeProvider.applyWhere("currency_code", "currency_code = '" + this.currencyCode + "'");
        this.chargeProvider.applyWhere("charge_applies_to_enum", "charge_applies_to_enum = 2");
        this.chargeProvider.applyWhere("is_active", "is_active = 1");
        this.chargeBlock = new WebMarkupBlock("chargeBlock", Size.Six_6);
        this.form.add(this.chargeBlock);
        this.chargeIContainer = new WebMarkupContainer("chargeIContainer");
        this.chargeBlock.add(this.chargeIContainer);
        this.chargeField = new Select2SingleChoice<>("chargeField", this.chargeValue, this.chargeProvider);
        this.chargeField.setRequired(true);
        this.chargeField.add(new OnChangeAjaxBehavior(this::chargeFieldUpdate));
        this.chargeField.setLabel(Model.of("Charge"));
        this.chargeIContainer.add(this.chargeField);
        this.chargeFeedback = new TextFeedbackPanel("chargeFeedback", this.chargeField);
        this.chargeIContainer.add(this.chargeFeedback);

        this.chargeTypeValue = new PropertyModel<>(this.model, "chargeTypeValue");
        this.chargeTypeBlock = new WebMarkupBlock("chargeTypeBlock", Size.Six_6);
        this.form.add(this.chargeTypeBlock);
        this.chargeTypeIContainer = new WebMarkupContainer("chargeTypeIContainer");
        this.chargeTypeBlock.add(this.chargeTypeIContainer);
        this.chargeTypeView = new ReadOnlyView("chargeTypeView", this.chargeTypeValue);
        this.chargeTypeIContainer.add(this.chargeTypeView);

        this.amountValue = new PropertyModel<>(this.model, "amountValue");
        this.amountBlock = new WebMarkupBlock("amountBlock", Size.Six_6);
        this.form.add(this.amountBlock);
        this.amountIContainer = new WebMarkupContainer("amountIContainer");
        this.amountBlock.add(this.amountIContainer);
        this.amountField = new TextField<>("amountField", this.amountValue);
        this.amountField.setType(Double.class);
        this.amountField.setLabel(Model.of("Amount"));
        this.amountIContainer.add(this.amountField);
        this.amountFeedback = new TextFeedbackPanel("amountFeedback", this.amountField);
        this.amountIContainer.add(this.amountFeedback);

        this.collectedOnValue = new PropertyModel<>(this.model, "collectedOnValue");
        this.collectedOnBlock = new WebMarkupBlock("collectedOnBlock", Size.Six_6);
        this.form.add(this.collectedOnBlock);
        this.collectedOnIContainer = new WebMarkupContainer("collectedOnIContainer");
        this.collectedOnBlock.add(this.collectedOnIContainer);
        this.collectedOnView = new ReadOnlyView("collectedOnView", this.collectedOnValue);
        this.collectedOnIContainer.add(this.collectedOnView);

        this.dayMonthValue = new PropertyModel<>(this.model, "dayMonthValue");
        this.dayMonthBlock = new WebMarkupBlock("dayMonthBlock", Size.Six_6);
        this.form.add(this.dayMonthBlock);
        this.dayMonthIContainer = new WebMarkupContainer("dayMonthIContainer");
        this.dayMonthBlock.add(this.dayMonthIContainer);
        this.dayMonthField = new DayMonthTextField("dayMonthField", this.dayMonthValue);
        this.dayMonthField.setLabel(Model.of("Day Month"));
        this.dayMonthIContainer.add(this.dayMonthField);
        this.dayMonthFeedback = new TextFeedbackPanel("dayMonthFeedback", this.dayMonthField);
        this.dayMonthIContainer.add(this.dayMonthFeedback);

        this.dateValue = new PropertyModel<>(this.model, "dateValue");
        this.dateBlock = new WebMarkupBlock("dateBlock", Size.Six_6);
        this.form.add(this.dateBlock);
        this.dateIContainer = new WebMarkupContainer("dateIContainer");
        this.dateBlock.add(this.dateIContainer);
        this.dateField = new DateTextField("dateField", this.dateValue);
        this.dateField.setLabel(Model.of("Day Month"));
        this.dateIContainer.add(this.dateField);
        this.dateFeedback = new TextFeedbackPanel("dateFeedback", this.dateField);
        this.dateIContainer.add(this.dateFeedback);

        this.repaymentEveryValue = new PropertyModel<>(this.model, "repaymentEveryValue");
        this.repaymentEveryBlock = new WebMarkupBlock("repaymentEveryBlock", Size.Six_6);
        this.form.add(this.repaymentEveryBlock);
        this.repaymentEveryIContainer = new WebMarkupContainer("repaymentEveryIContainer");
        this.repaymentEveryBlock.add(this.repaymentEveryIContainer);
        this.repaymentEveryField = new TextField<>("repaymentEveryField", this.repaymentEveryValue);
        this.repaymentEveryField.setType(Long.class);
        this.repaymentEveryField.setLabel(Model.of("repaymentEvery"));
        this.repaymentEveryIContainer.add(this.repaymentEveryField);
        this.repaymentEveryFeedback = new TextFeedbackPanel("repaymentEveryFeedback", this.repaymentEveryField);
        this.repaymentEveryIContainer.add(this.repaymentEveryFeedback);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
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