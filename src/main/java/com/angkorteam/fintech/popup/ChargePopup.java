package com.angkorteam.fintech.popup;

import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.ddl.MCharge;
import com.angkorteam.fintech.dto.enums.ChargeType;
import com.angkorteam.fintech.dto.enums.ProductPopup;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class ChargePopup extends PopupPanel {

    protected Form<Void> form;
    protected AjaxButton okayButton;

    protected UIRow row1;

    protected UIBlock chargeBlock;
    protected UIContainer chargeIContainer;
    protected SingleChoiceProvider chargeProvider;
    protected Select2SingleChoice<Option> chargeField;
    protected PropertyModel<Option> chargeValue;

    protected String currencyCode;

    protected ProductPopup productPopup;

    public ChargePopup(String name, Map<String, Object> model, ProductPopup productPopup, String currencyCode) {
        super(name, model);
        this.productPopup = productPopup;
        this.currencyCode = currencyCode;
    }

    @Override
    protected void initData() {
        this.chargeValue = new PropertyModel<>(this.model, "chargeValue");

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
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.okayButton = new AjaxButton("okayButton");
        this.okayButton.setOnSubmit(this::okayButtonSubmit);
        this.okayButton.setOnError(this::okayButtonError);
        this.form.add(this.okayButton);

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.chargeBlock = this.row1.newUIBlock("chargeBlock", Size.Twelve_12);
        this.chargeIContainer = this.chargeBlock.newUIContainer("chargeIContainer");
        this.chargeField = new Select2SingleChoice<>("chargeField", this.chargeValue, this.chargeProvider);
        this.chargeIContainer.add(this.chargeField);
        this.chargeIContainer.newFeedback("chargeFeedback", this.chargeField);
    }

    @Override
    protected void configureMetaData() {
        this.chargeField.setLabel(Model.of("Charge"));
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