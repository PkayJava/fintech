package com.angkorteam.fintech.popup.loan;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.dto.enums.ChargeType;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.PopupPanel;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class OverdueChargePopup extends PopupPanel {

    protected ModalWindow window;

    protected Form<Void> form;
    protected AjaxButton okayButton;

    protected PropertyModel<Option> overdueChargeValue;
    protected SingleChoiceProvider overdueChargeProvider;
    protected Select2SingleChoice<Option> overdueChargeField;
    protected TextFeedbackPanel overdueChargeFeedback;

    protected String currencyCode;

    protected Object model;

    public OverdueChargePopup(String name, ModalWindow window, Object model, String currencyCode) {
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

        this.overdueChargeValue = new PropertyModel<>(this.model, "overdueChargeValue");
        this.overdueChargeProvider = new SingleChoiceProvider("m_charge", "id", "name");
        this.overdueChargeProvider.applyWhere("charge_applies_to_enum", "charge_applies_to_enum = " + ChargeType.Loan.getLiteral());
        this.overdueChargeProvider.applyWhere("currency_code", "currency_code = '" + this.currencyCode + "'");
        this.overdueChargeProvider.applyWhere("is_penalty", "is_penalty = 1");
        this.overdueChargeProvider.applyWhere("is_active", "is_active = 1");
        this.overdueChargeField = new Select2SingleChoice<>("overdueChargeField", this.overdueChargeValue, this.overdueChargeProvider);
        this.overdueChargeField.setLabel(Model.of("Overdue Charge"));
        this.form.add(this.overdueChargeField);
        this.overdueChargeFeedback = new TextFeedbackPanel("overdueChargeFeedback", this.overdueChargeField);
        this.form.add(this.overdueChargeFeedback);

    }

    protected boolean okayButtonSubmit(AjaxButton ajaxButton, AjaxRequestTarget target) {
        this.window.setSignalId(ajaxButton.getId());
        this.window.close(target);
        return true;
    }

}