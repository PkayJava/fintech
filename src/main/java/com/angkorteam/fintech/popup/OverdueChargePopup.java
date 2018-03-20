package com.angkorteam.fintech.popup;

import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.ddl.MCharge;
import com.angkorteam.fintech.dto.enums.ChargeType;
import com.angkorteam.fintech.dto.enums.ProductPopup;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class OverdueChargePopup extends PopupPanel {

    protected Form<Void> form;
    protected AjaxButton okayButton;

    protected WebMarkupBlock overdueChargeBlock;
    protected WebMarkupContainer overdueChargeIContainer;
    protected SingleChoiceProvider overdueChargeProvider;
    protected Select2SingleChoice<Option> overdueChargeField;
    protected TextFeedbackPanel overdueChargeFeedback;

    protected String currencyCode;

    protected ProductPopup productPopup;

    public OverdueChargePopup(String name, Map<String, Object> model, ProductPopup productPopup, String currencyCode) {
        super(name, model);
        this.currencyCode = currencyCode;
        this.productPopup = productPopup;
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
        this.okayButton.setOnError(this::okayButtonError);
        this.form.add(this.okayButton);

        this.overdueChargeBlock = new WebMarkupBlock("overdueChargeBlock", Size.Six_6);
        this.form.add(this.overdueChargeBlock);
        this.overdueChargeIContainer = new WebMarkupContainer("overdueChargeIContainer");
        this.overdueChargeBlock.add(this.overdueChargeIContainer);
        this.overdueChargeProvider = new SingleChoiceProvider(MCharge.NAME, MCharge.Field.ID, MCharge.Field.NAME);
        if (this.productPopup == ProductPopup.Saving) {
            this.overdueChargeProvider.applyWhere("charge_applies_to_enum", MCharge.Field.CHARGE_APPLIES_TO_ENUM + " = " + ChargeType.SavingDeposit.getLiteral());
        } else if (this.productPopup == ProductPopup.Loan) {
            this.overdueChargeProvider.applyWhere("charge_applies_to_enum", MCharge.Field.CHARGE_APPLIES_TO_ENUM + " = " + ChargeType.Loan.getLiteral());
        }
        this.overdueChargeProvider.applyWhere("currency_code", MCharge.Field.CURRENCY_CODE + " = '" + this.currencyCode + "'");
        this.overdueChargeProvider.applyWhere("is_penalty", MCharge.Field.IS_PENALTY + " = 1");
        this.overdueChargeProvider.applyWhere("is_active", MCharge.Field.IS_ACTIVE + " = 1");
        this.overdueChargeField = new Select2SingleChoice<>("overdueChargeField", new PropertyModel<>(this.model, "overdueChargeValue"), this.overdueChargeProvider);
        this.overdueChargeField.setLabel(Model.of("Overdue Charge"));
        this.overdueChargeIContainer.add(this.overdueChargeField);
        this.overdueChargeFeedback = new TextFeedbackPanel("overdueChargeFeedback", this.overdueChargeField);
        this.overdueChargeIContainer.add(this.overdueChargeFeedback);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected boolean okayButtonSubmit(AjaxButton ajaxButton, AjaxRequestTarget target) {
        this.window.setSignalId(ajaxButton.getId());
        this.window.close(target);
        return true;
    }

    protected boolean okayButtonError(AjaxButton ajaxButton, AjaxRequestTarget target) {
        target.add(this.form);
        return true;
    }

}