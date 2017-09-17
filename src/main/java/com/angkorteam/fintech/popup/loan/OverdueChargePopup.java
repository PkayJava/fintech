package com.angkorteam.fintech.popup.loan;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.dto.ChargeType;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class OverdueChargePopup extends Panel {

    private ModalWindow window;

    private Form<Void> form;
    private AjaxButton okayButton;

    private SingleChoiceProvider overdueChargeProvider;
    private Select2SingleChoice<Option> overdueChargeField;
    private TextFeedbackPanel overdueChargeFeedback;

    private String currencyCode;

    private Object model;

    public OverdueChargePopup(String id, ModalWindow window, Object model, String currencyCode) {
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

        this.overdueChargeProvider = new SingleChoiceProvider("m_charge", "id", "name");
        this.overdueChargeProvider.applyWhere("charge_applies_to_enum", "charge_applies_to_enum = " + ChargeType.Loan.getLiteral());
        this.overdueChargeProvider.applyWhere("currency_code", "currency_code = '" + this.currencyCode + "'");
        this.overdueChargeProvider.applyWhere("is_penalty", "is_penalty = 1");
        this.overdueChargeProvider.applyWhere("is_active", "is_active = 1");
        this.overdueChargeField = new Select2SingleChoice<>("overdueChargeField", 0, new PropertyModel<>(this.model, "itemOverdueChargeValue"), this.overdueChargeProvider);
        this.overdueChargeField.setLabel(Model.of("Overdue Charge"));
        this.form.add(this.overdueChargeField);
        this.overdueChargeFeedback = new TextFeedbackPanel("overdueChargeFeedback", this.overdueChargeField);
        this.form.add(this.overdueChargeFeedback);

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