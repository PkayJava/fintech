package com.angkorteam.fintech.popup;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.angkorteam.fintech.dto.enums.ChargeCalculation;
import com.angkorteam.fintech.dto.enums.ChargeTime;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.PopupPanel;
import com.angkorteam.framework.wicket.markup.html.form.DayMonthTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class AccountChargePopup extends PopupPanel {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountChargePopup.class);

    protected ModalWindow window;
    protected Map<String, Object> model;

    protected Form<Void> form;
    protected AjaxButton okayButton;

    protected WebMarkupBlock chargeBlock;
    protected WebMarkupContainer chargeContainer;
    protected SingleChoiceProvider chargeProvider;
    protected Select2SingleChoice<Option> chargeField;
    protected TextFeedbackPanel chargeFeedback;
    protected PropertyModel<Option> chargeValue;

    protected WebMarkupBlock chargeTypeBlock;
    protected WebMarkupContainer chargeTypeContainer;
    protected Label chargeTypeView;
    protected PropertyModel<String> chargeTypeValue;

    protected WebMarkupBlock amountBlock;
    protected WebMarkupContainer amountContainer;
    protected TextField<Double> amountField;
    protected TextFeedbackPanel amountFeedback;
    protected PropertyModel<Double> amountValue;

    protected WebMarkupBlock collectedOnBlock;
    protected WebMarkupContainer collectedOnContainer;
    protected Label collectedOnView;
    protected PropertyModel<String> collectedOnValue;

    protected WebMarkupBlock dateBlock;
    protected WebMarkupContainer dateContainer;
    protected DayMonthTextField dateField;
    protected TextFeedbackPanel dateFeedback;
    protected PropertyModel<Date> dateValue;

    protected WebMarkupBlock repaymentEveryBlock;
    protected WebMarkupContainer repaymentEveryContainer;
    protected TextField<Integer> repaymentEveryField;
    protected TextFeedbackPanel repaymentEveryFeedback;
    protected PropertyModel<Integer> repaymentEveryValue;

    protected String currencyCode;

    public AccountChargePopup(String name, ModalWindow window, Map<String, Object> model, String currencyCode) {
        super(name, window);
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
        this.form.add(this.okayButton);

        this.chargeValue = new PropertyModel<>(this.model, "chargeValue");
        this.chargeProvider = new SingleChoiceProvider("m_charge", "id", "name");
        this.chargeProvider.applyWhere("currency_code", "currency_code = '" + this.currencyCode + "'");
        this.chargeProvider.applyWhere("charge_applies_to_enum", "charge_applies_to_enum = 2");
        this.chargeProvider.applyWhere("is_active", "is_active = 1");
        this.chargeBlock = new WebMarkupBlock("chargeBlock", Size.Six_6);
        this.form.add(this.chargeBlock);
        this.chargeContainer = new WebMarkupContainer("chargeContainer");
        this.chargeBlock.add(this.chargeContainer);
        this.chargeField = new Select2SingleChoice<>("chargeField", this.chargeValue, this.chargeProvider);
        this.chargeField.setRequired(true);
        this.chargeField.add(new OnChangeAjaxBehavior(this::chargeFieldUpdate));
        this.chargeField.setLabel(Model.of("Charge"));
        this.chargeContainer.add(this.chargeField);
        this.chargeFeedback = new TextFeedbackPanel("chargeFeedback", this.chargeField);
        this.chargeContainer.add(this.chargeFeedback);

        this.chargeTypeValue = new PropertyModel<>(this.model, "chargeTypeValue");
        this.chargeTypeBlock = new WebMarkupBlock("chargeTypeBlock", Size.Six_6);
        this.form.add(this.chargeTypeBlock);
        this.chargeTypeContainer = new WebMarkupContainer("chargeTypeContainer");
        this.chargeTypeBlock.add(this.chargeTypeContainer);
        this.chargeTypeView = new Label("chargeTypeView", this.chargeTypeValue);
        this.chargeTypeContainer.add(this.chargeTypeView);

        this.amountValue = new PropertyModel<>(this.model, "amountValue");
        this.amountBlock = new WebMarkupBlock("amountBlock", Size.Six_6);
        this.form.add(this.amountBlock);
        this.amountContainer = new WebMarkupContainer("amountContainer");
        this.amountBlock.add(this.amountContainer);
        this.amountField = new TextField<>("amountField", this.amountValue);
        this.amountField.setType(Double.class);
        this.amountField.setLabel(Model.of("Amount"));
        this.amountContainer.add(this.amountField);
        this.amountFeedback = new TextFeedbackPanel("amountFeedback", this.amountField);
        this.amountContainer.add(this.amountFeedback);

        this.collectedOnValue = new PropertyModel<>(this.model, "collectedOnValue");
        this.collectedOnBlock = new WebMarkupBlock("collectedOnBlock", Size.Six_6);
        this.form.add(this.collectedOnBlock);
        this.collectedOnContainer = new WebMarkupContainer("collectedOnContainer");
        this.collectedOnBlock.add(this.collectedOnContainer);
        this.collectedOnView = new Label("collectedOnView", this.collectedOnValue);
        this.collectedOnContainer.add(this.collectedOnView);

        this.dateValue = new PropertyModel<>(this.model, "dateValue");
        this.dateBlock = new WebMarkupBlock("dateBlock", Size.Six_6);
        this.form.add(this.dateBlock);
        this.dateContainer = new WebMarkupContainer("dateContainer");
        this.dateBlock.add(this.dateContainer);
        this.dateField = new DayMonthTextField("dateField", this.dateValue);
        this.dateField.setLabel(Model.of("Date"));
        this.dateContainer.add(this.dateField);
        this.dateFeedback = new TextFeedbackPanel("dateFeedback", this.dateField);
        this.dateContainer.add(this.dateFeedback);

        this.repaymentEveryValue = new PropertyModel<>(this.model, "repaymentEveryValue");
        this.repaymentEveryBlock = new WebMarkupBlock("repaymentEveryBlock", Size.Six_6);
        this.form.add(this.repaymentEveryBlock);
        this.repaymentEveryContainer = new WebMarkupContainer("repaymentEveryContainer");
        this.repaymentEveryBlock.add(this.repaymentEveryContainer);
        this.repaymentEveryField = new TextField<>("repaymentEveryField", this.repaymentEveryValue);
        this.repaymentEveryField.setType(Integer.class);
        this.repaymentEveryField.setLabel(Model.of("repaymentEvery"));
        this.repaymentEveryContainer.add(this.repaymentEveryField);
        this.repaymentEveryFeedback = new TextFeedbackPanel("repaymentEveryFeedback", this.repaymentEveryField);
        this.repaymentEveryContainer.add(this.repaymentEveryFeedback);
    }

    protected boolean chargeFieldUpdate(AjaxRequestTarget target) {
        if (this.chargeValue.getObject() != null) {
            JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
            Map<String, Object> chargeObject = jdbcTemplate.queryForMap("select * from m_charge where id = ?", this.chargeValue.getObject().getId());
            Long collectedOn = (Long) chargeObject.get("charge_time_enum");
            if (collectedOn != null) {
                Option option = ChargeTime.optionLiteral(String.valueOf(collectedOn));
                if (option != null) {
                    this.collectedOnValue.setObject(option.getText());
                }
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
                Option option = ChargeCalculation.optionLiteral(String.valueOf(type));
                if (option != null) {
                    this.chargeTypeValue.setObject(option.getText());
                }
            } else {
                this.chargeTypeValue.setObject(null);
            }

            Long repaymentEveryValue = (Long) chargeObject.get("fee_interval");
            this.repaymentEveryValue.setObject(repaymentEveryValue == null ? null : repaymentEveryValue.intValue());

            Long month = (Long) chargeObject.get("fee_on_month");
            Long day = (Long) chargeObject.get("fee_on_day");
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
        this.window.setSignalId(ajaxButton.getId());
        this.window.close(target);
        return true;
    }

}