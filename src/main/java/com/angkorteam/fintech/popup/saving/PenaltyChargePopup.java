package com.angkorteam.fintech.popup.saving;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.dto.AccountType;
import com.angkorteam.fintech.dto.AccountUsage;
import com.angkorteam.fintech.dto.ChargeType;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class PenaltyChargePopup extends Panel {

    private ModalWindow window;

    private Form<Void> form;
    private AjaxButton okayButton;

    private SingleChoiceProvider chargeProvider;
    private Select2SingleChoice<Option> chargeField;
    private TextFeedbackPanel chargeFeedback;

    private SingleChoiceProvider accountProvider;
    private Select2SingleChoice<Option> accountField;
    private TextFeedbackPanel accountFeedback;

    private String currencyCode;

    private Object model;

    public PenaltyChargePopup(String id, ModalWindow window, Object model, String currencyCode) {
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

        this.chargeProvider = new SingleChoiceProvider("m_charge", "id", "name");
        this.chargeProvider.applyWhere("charge_applies_to_enum", "charge_applies_to_enum = " + ChargeType.SavingDeposit.getLiteral());
        this.chargeProvider.applyWhere("currency_code", "currency_code = '" + this.currencyCode + "'");
        this.chargeProvider.applyWhere("is_penalty", "is_penalty = 1");
        this.chargeProvider.applyWhere("is_active", "is_active = 1");
        this.chargeField = new Select2SingleChoice<>("chargeField", 0, new PropertyModel<>(this.model, "itemChargeValue"), this.chargeProvider);
        this.chargeField.setLabel(Model.of("Charge"));
        this.form.add(this.chargeField);
        this.chargeFeedback = new TextFeedbackPanel("chargeFeedback", this.chargeField);
        this.form.add(this.chargeFeedback);

        this.accountProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.accountProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.accountProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
        this.accountField = new Select2SingleChoice<>("accountField", 0, new PropertyModel<>(this.model, "itemAccountValue"), this.accountProvider);
        this.accountField.setLabel(Model.of("Account"));
        this.form.add(this.accountField);
        this.accountFeedback = new TextFeedbackPanel("accountFeedback", this.accountField);
        this.form.add(this.accountFeedback);

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