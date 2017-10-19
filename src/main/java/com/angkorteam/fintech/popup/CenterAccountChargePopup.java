package com.angkorteam.fintech.popup;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.angkorteam.fintech.dto.enums.ChargeCalculation;
import com.angkorteam.fintech.dto.enums.ChargeTime;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.markup.html.form.DayMonthTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class CenterAccountChargePopup extends Panel {

    private static final Logger LOGGER = LoggerFactory.getLogger(CenterAccountChargePopup.class);

    protected ModalWindow window;
    protected Object model;

    protected Form<Void> form;
    protected AjaxButton okayButton;

    protected WebMarkupContainer chargeBlock;
    protected WebMarkupContainer chargeContainer;
    protected SingleChoiceProvider chargeProvider;
    protected Select2SingleChoice<Option> chargeField;
    protected TextFeedbackPanel chargeFeedback;
    protected PropertyModel<Option> chargeValue;

    protected WebMarkupContainer chargeTypeBlock;
    protected WebMarkupContainer chargeTypeContainer;
    protected Label chargeTypeField;
    protected PropertyModel<String> chargeTypeValue;

    protected WebMarkupContainer amountBlock;
    protected WebMarkupContainer amountContainer;
    protected TextField<Double> amountField;
    protected TextFeedbackPanel amountFeedback;
    protected PropertyModel<Double> amountValue;

    protected WebMarkupContainer collectedOnBlock;
    protected WebMarkupContainer collectedOnContainer;
    protected Label collectedOnField;
    protected PropertyModel<String> collectedOnValue;

    protected WebMarkupContainer dateBlock;
    protected WebMarkupContainer dateContainer;
    protected DayMonthTextField dateField;
    protected TextFeedbackPanel dateFeedback;
    protected PropertyModel<Date> dateValue;

    protected WebMarkupContainer repaymentEveryBlock;
    protected WebMarkupContainer repaymentEveryContainer;
    protected TextField<Integer> repaymentEveryField;
    protected TextFeedbackPanel repaymentEveryFeedback;
    protected PropertyModel<Integer> repaymentEveryValue;

    protected String currencyCode;



    public CenterAccountChargePopup(String id, ModalWindow window, Object model, String currencyCode) {
        super(id);
        this.model = model;
        this.window = window;
        this.currencyCode = currencyCode;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.form = new Form<>("form");
        add(this.form);

        this.okayButton = new AjaxButton("okayButton");
        this.okayButton.setOnSubmit(this::okayButtonSubmit);
        this.okayButton.setOnError(this::okayButtonError);
        this.form.add(this.okayButton);

        this.chargeValue = new PropertyModel<>(this.model, "itemChargeValue");
        this.chargeProvider = new SingleChoiceProvider("m_charge", "id", "name");
        this.chargeProvider.applyWhere("currency_code", "currency_code = '" + this.currencyCode + "'");
        this.chargeProvider.applyWhere("charge_applies_to_enum", "charge_applies_to_enum = 2");
        this.chargeProvider.applyWhere("is_active", "is_active = 1");
        this.chargeBlock = new WebMarkupContainer("chargeBlock");
        this.chargeBlock.setOutputMarkupId(true);
        this.form.add(this.chargeBlock);
        this.chargeContainer = new WebMarkupContainer("chargeContainer");
        this.chargeBlock.add(this.chargeContainer);
        this.chargeField = new Select2SingleChoice<>("chargeField", 0, this.chargeValue, this.chargeProvider);
        this.chargeField.setRequired(true);
        this.chargeField.add(new OnChangeAjaxBehavior(this::chargeFieldUpdate));
        this.chargeField.setLabel(Model.of("Charge"));
        this.chargeContainer.add(this.chargeField);
        this.chargeFeedback = new TextFeedbackPanel("chargeFeedback", this.chargeField);
        this.chargeContainer.add(this.chargeFeedback);

        this.chargeTypeValue = new PropertyModel<>(this.model, "itemChargeTypeValue");
        this.chargeTypeBlock = new WebMarkupContainer("chargeTypeBlock");
        this.chargeTypeBlock.setOutputMarkupId(true);
        this.form.add(this.chargeTypeBlock);
        this.chargeTypeContainer = new WebMarkupContainer("chargeTypeContainer");
        this.chargeTypeBlock.add(this.chargeTypeContainer);
        this.chargeTypeField = new Label("chargeTypeField", this.chargeTypeValue);
        this.chargeTypeContainer.add(this.chargeTypeField);

        this.amountValue = new PropertyModel<>(this.model, "itemAmountValue");
        this.amountBlock = new WebMarkupContainer("amountBlock");
        this.amountBlock.setOutputMarkupId(true);
        this.form.add(this.amountBlock);
        this.amountContainer = new WebMarkupContainer("amountContainer");
        this.amountBlock.add(this.amountContainer);
        this.amountField = new TextField<>("amountField", this.amountValue);
        this.amountField.setLabel(Model.of("Amount"));
        this.amountContainer.add(this.amountField);
        this.amountFeedback = new TextFeedbackPanel("amountFeedback", this.amountField);
        this.amountContainer.add(this.amountFeedback);

        this.collectedOnValue = new PropertyModel<>(this.model, "itemCollectedOnValue");
        this.collectedOnBlock = new WebMarkupContainer("collectedOnBlock");
        this.collectedOnBlock.setOutputMarkupId(true);
        this.form.add(this.collectedOnBlock);
        this.collectedOnContainer = new WebMarkupContainer("collectedOnContainer");
        this.collectedOnBlock.add(this.collectedOnContainer);
        this.collectedOnField = new Label("collectedOnField", this.collectedOnValue);
        this.collectedOnContainer.add(this.collectedOnField);

        this.dateValue = new PropertyModel<>(this.model, "itemDateValue");
        this.dateBlock = new WebMarkupContainer("dateBlock");
        this.dateBlock.setOutputMarkupId(true);
        this.form.add(this.dateBlock);
        this.dateContainer = new WebMarkupContainer("dateContainer");
        this.dateBlock.add(this.dateContainer);
        this.dateField = new DayMonthTextField("dateField", this.dateValue);
        this.dateField.setLabel(Model.of("Date"));
        this.dateContainer.add(this.dateField);
        this.dateFeedback = new TextFeedbackPanel("dateFeedback", this.dateField);
        this.dateContainer.add(this.dateFeedback);

        this.repaymentEveryValue = new PropertyModel<>(this.model, "itemRepaymentEveryValue");
        this.repaymentEveryBlock = new WebMarkupContainer("repaymentEveryBlock");
        this.repaymentEveryBlock.setOutputMarkupId(true);
        this.form.add(this.repaymentEveryBlock);
        this.repaymentEveryContainer = new WebMarkupContainer("repaymentEveryContainer");
        this.repaymentEveryBlock.add(this.repaymentEveryContainer);
        this.repaymentEveryField = new TextField<>("repaymentEveryField", this.repaymentEveryValue);
        this.repaymentEveryField.setLabel(Model.of("repaymentEvery"));
        this.repaymentEveryContainer.add(this.repaymentEveryField);
        this.repaymentEveryFeedback = new TextFeedbackPanel("repaymentEveryFeedback", this.repaymentEveryField);
        this.repaymentEveryContainer.add(this.repaymentEveryFeedback);
    }

    protected boolean chargeFieldUpdate(AjaxRequestTarget target) {
        if (this.chargeValue.getObject() != null) {
            JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
            Map<String, Object> chargeObject = jdbcTemplate.queryForMap("select * from m_charge where id = ?", this.chargeValue.getObject().getId());
            Integer collectedOn = (Integer) chargeObject.get("charge_time_enum");
            if (collectedOn != null) {
                Option option = ChargeTime.optionLiteral(String.valueOf(collectedOn));
                if (option != null) {
                    this.collectedOnValue.setObject(option.getText());
                }
            } else {
                this.collectedOnValue.setObject(null);
            }

            BigDecimal amount = (BigDecimal) chargeObject.get("amount");
            if (amount != null) {
                this.amountValue.setObject(amount.doubleValue());
            } else {
                this.amountValue.setObject(null);
            }

            Integer type = (Integer) chargeObject.get("charge_calculation_enum");
            if (type != null) {
                Option option = ChargeCalculation.optionLiteral(String.valueOf(type));
                if (option != null) {
                    this.chargeTypeValue.setObject(option.getText());
                }
            } else {
                this.chargeTypeValue.setObject(null);
            }

            Integer repaymentEveryValue = (Integer) chargeObject.get("fee_interval");
            this.repaymentEveryValue.setObject(repaymentEveryValue);

            Integer month = (Integer) chargeObject.get("fee_on_month");
            Integer day = (Integer) chargeObject.get("fee_on_day");
            if (day != null && month != null) {
                try {
                    this.dateValue.setObject(DateUtils.parseDate(day + "/" + month, "d/M"));
                } catch (ParseException e) {
                    LOGGER.info(e.getMessage());
                }
            }

            if (target != null) {
                target.add(this.chargeTypeBlock);
                target.add(this.amountBlock);
                target.add(this.collectedOnBlock);
                target.add(this.dateBlock);
                target.add(this.repaymentEveryBlock);
            }

        }
        return false;
    }

    protected boolean okayButtonSubmit(AjaxButton ajaxButton, AjaxRequestTarget target) {
        this.window.setElementId(ajaxButton.getId());
        this.window.close(target);
        return true;
    }

    protected boolean okayButtonError(AjaxButton ajaxButton, AjaxRequestTarget target) {
        target.add(this.form);
        return true;
    }

}