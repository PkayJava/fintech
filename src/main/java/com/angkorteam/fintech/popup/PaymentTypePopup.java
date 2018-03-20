package com.angkorteam.fintech.popup;

import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.ddl.AccGLAccount;
import com.angkorteam.fintech.ddl.MPaymentType;
import com.angkorteam.fintech.dto.enums.AccountType;
import com.angkorteam.fintech.dto.enums.AccountUsage;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class PaymentTypePopup extends PopupPanel {

    protected Form<Void> form;
    protected AjaxButton okayButton;

    protected PropertyModel<Option> paymentValue;
    protected SingleChoiceProvider paymentProvider;
    protected Select2SingleChoice<Option> paymentField;
    protected TextFeedbackPanel paymentFeedback;

    protected PropertyModel<Option> accountValue;
    protected SingleChoiceProvider accountProvider;
    protected Select2SingleChoice<Option> accountField;
    protected TextFeedbackPanel accountFeedback;

    public PaymentTypePopup(String name, Map<String, Object> model) {
        super(name, model);
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

        this.paymentValue = new PropertyModel<>(this.model, "paymentValue");
        this.paymentProvider = new SingleChoiceProvider(MPaymentType.NAME, MPaymentType.Field.ID, MPaymentType.Field.VALUE);
        this.paymentField = new Select2SingleChoice<>("paymentField", this.paymentValue, this.paymentProvider);
        this.paymentField.setLabel(Model.of("Payment"));
        this.form.add(this.paymentField);
        this.paymentFeedback = new TextFeedbackPanel("paymentFeedback", this.paymentField);
        this.form.add(this.paymentFeedback);

        this.accountValue = new PropertyModel<>(this.model, "accountValue");
        this.accountProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.accountProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.accountProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = " + AccountType.Asset.getLiteral());
        this.accountField = new Select2SingleChoice<>("accountField", this.accountValue, this.accountProvider);
        this.accountField.setLabel(Model.of("Account"));
        this.form.add(this.accountField);
        this.accountFeedback = new TextFeedbackPanel("accountFeedback", this.accountField);
        this.form.add(this.accountFeedback);
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

}