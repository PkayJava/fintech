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
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class ChargePopup extends PopupPanel {

    protected ModalWindow window;

    protected Form<Void> form;
    protected AjaxButton okayButton;

    protected WebMarkupBlock chargeBlock;
    protected WebMarkupContainer chargeIContainer;
    protected SingleChoiceProvider chargeProvider;
    protected Select2SingleChoice<Option> chargeField;
    protected TextFeedbackPanel chargeFeedback;
    protected PropertyModel<Option> chargeValue;

    protected String currencyCode;

    protected Map<String, Object> model;

    protected ProductPopup productPopup;

    public ChargePopup(String name, ModalWindow window, ProductPopup productPopup, Map<String, Object> model, String currencyCode) {
        super(name, window);
        this.productPopup = productPopup;
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
        this.okayButton.setOnError(this::okayButtonError);
        this.form.add(this.okayButton);

        this.chargeBlock = new WebMarkupBlock("chargeBlock", Size.Six_6);
        this.form.add(this.chargeBlock);
        this.chargeIContainer = new WebMarkupContainer("chargeIContainer");
        this.chargeBlock.add(this.chargeIContainer);
        this.chargeProvider = new SingleChoiceProvider(MCharge.NAME, MCharge.Field.ID, MCharge.Field.NAME);
        if (this.productPopup == ProductPopup.Share) {
            this.chargeProvider.applyWhere("charge_applies_to_enum", MCharge.Field.CHARGE_APPLIES_TO_ENUM + " = " + ChargeType.Share.getLiteral());
        } else if (this.productPopup == ProductPopup.Loan) {
            this.chargeProvider.applyWhere("charge_applies_to_enum", MCharge.Field.CHARGE_APPLIES_TO_ENUM + " = " + ChargeType.Loan.getLiteral());
        } else if (this.productPopup == ProductPopup.Fixed || this.productPopup == ProductPopup.Saving || this.productPopup == ProductPopup.Recurring) {
            this.chargeProvider.applyWhere("charge_applies_to_enum", MCharge.Field.CHARGE_APPLIES_TO_ENUM + " = " + ChargeType.SavingDeposit.getLiteral());
        }
        this.chargeProvider.applyWhere("currency_code", MCharge.Field.CURRENCY_CODE + " = '" + this.currencyCode + "'");
        this.chargeProvider.applyWhere("is_penalty", MCharge.Field.IS_PENALTY + " = 0");
        this.chargeProvider.applyWhere("is_active", MCharge.Field.IS_ACTIVE + " = 1");
        this.chargeValue = new PropertyModel<>(this.model, "chargeValue");
        this.chargeField = new Select2SingleChoice<>("chargeField", this.chargeValue, this.chargeProvider);
        this.chargeField.setLabel(Model.of("Charge"));
        this.chargeIContainer.add(this.chargeField);
        this.chargeFeedback = new TextFeedbackPanel("chargeFeedback", this.chargeField);
        this.chargeIContainer.add(this.chargeFeedback);
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