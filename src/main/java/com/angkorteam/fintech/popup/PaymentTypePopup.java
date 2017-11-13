package com.angkorteam.fintech.popup;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.dto.enums.AccountType;
import com.angkorteam.fintech.dto.enums.AccountUsage;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

import java.util.Map;

public class PaymentTypePopup extends Panel {

    private ModalWindow window;

    private Form<Void> form;
    private AjaxButton okayButton;

    private SingleChoiceProvider paymentProvider;
    private Select2SingleChoice<Option> paymentField;
    private TextFeedbackPanel paymentFeedback;

    private SingleChoiceProvider accountProvider;
    private Select2SingleChoice<Option> accountField;
    private TextFeedbackPanel accountFeedback;

    private Map<String, Object> model;

    public PaymentTypePopup(String id, ModalWindow window, Map<String, Object> model) {
        super(id);
        this.model = model;
        this.window = window;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.form = new Form<>("form");
        add(this.form);

        this.okayButton = new AjaxButton("okayButton");
        this.okayButton.setOnSubmit(this::okayButtonSubmit);
        this.form.add(this.okayButton);

        this.paymentProvider = new SingleChoiceProvider("m_payment_type", "id", "value");
        this.paymentField = new Select2SingleChoice<>("paymentField", 0, new PropertyModel<>(this.model, "paymentValue"), this.paymentProvider);
        this.paymentField.setLabel(Model.of("Payment"));
        this.form.add(this.paymentField);
        this.paymentFeedback = new TextFeedbackPanel("paymentFeedback", this.paymentField);
        this.form.add(this.paymentFeedback);

        this.accountProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.accountProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.accountProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Asset.getLiteral());
        this.accountField = new Select2SingleChoice<>("accountField", 0, new PropertyModel<>(this.model, "accountValue"), this.accountProvider);
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

}