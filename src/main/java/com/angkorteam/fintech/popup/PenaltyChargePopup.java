package com.angkorteam.fintech.popup;

import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.dto.enums.AccountType;
import com.angkorteam.fintech.dto.enums.AccountUsage;
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

public class PenaltyChargePopup extends PopupPanel {

    protected ModalWindow window;

    protected Form<Void> form;
    protected AjaxButton okayButton;

    protected WebMarkupBlock chargeBlock;
    protected WebMarkupContainer chargeIContainer;
    protected PropertyModel<Option> chargeValue;
    protected SingleChoiceProvider chargeProvider;
    protected Select2SingleChoice<Option> chargeField;
    protected TextFeedbackPanel chargeFeedback;

    protected WebMarkupBlock accountBlock;
    protected WebMarkupContainer accountIContainer;
    protected PropertyModel<Option> accountValue;
    protected SingleChoiceProvider accountProvider;
    protected Select2SingleChoice<Option> accountField;
    protected TextFeedbackPanel accountFeedback;

    protected String currencyCode;

    protected ProductPopup productPopup;

    protected Map<String, Object> model;

    public PenaltyChargePopup(String name, ModalWindow window, ProductPopup productPopup, Map<String, Object> model, String currencyCode) {
        super(name, window);
        this.model = model;
        this.window = window;
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

        this.chargeBlock = new WebMarkupBlock("chargeBlock", Size.Six_6);
        this.form.add(this.chargeBlock);
        this.chargeIContainer = new WebMarkupContainer("chargeIContainer");
        this.chargeBlock.add(this.chargeIContainer);
        this.chargeValue = new PropertyModel<>(this.model, "chargeValue");
        this.chargeProvider = new SingleChoiceProvider("m_charge", "id", "name");
        if (this.productPopup == ProductPopup.Saving || this.productPopup == ProductPopup.Fixed || this.productPopup == ProductPopup.Recurring) {
            this.chargeProvider.applyWhere("charge_applies_to_enum", "charge_applies_to_enum = " + ChargeType.SavingDeposit.getLiteral());
        } else if (this.productPopup == ProductPopup.Loan) {
            this.chargeProvider.applyWhere("charge_applies_to_enum", "charge_applies_to_enum = " + ChargeType.Loan.getLiteral());
        }
        this.chargeProvider.applyWhere("currency_code", "currency_code = '" + this.currencyCode + "'");
        this.chargeProvider.applyWhere("is_penalty", "is_penalty = 1");
        this.chargeProvider.applyWhere("is_active", "is_active = 1");
        this.chargeField = new Select2SingleChoice<>("chargeField", this.chargeValue, this.chargeProvider);
        this.chargeField.setLabel(Model.of("Charge"));
        this.chargeIContainer.add(this.chargeField);
        this.chargeFeedback = new TextFeedbackPanel("chargeFeedback", this.chargeField);
        this.chargeIContainer.add(this.chargeFeedback);

        this.accountBlock = new WebMarkupBlock("accountBlock", Size.Six_6);
        this.form.add(this.accountBlock);
        this.accountIContainer = new WebMarkupContainer("accountIContainer");
        this.accountBlock.add(this.accountIContainer);
        this.accountValue = new PropertyModel<>(this.model, "accountValue");
        this.accountProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.accountProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.accountProvider.applyWhere("classification_enum", "classification_enum = " + AccountType.Income.getLiteral());
        this.accountField = new Select2SingleChoice<>("accountField", this.accountValue, this.accountProvider);
        this.accountField.setLabel(Model.of("Account"));
        this.accountIContainer.add(this.accountField);
        this.accountFeedback = new TextFeedbackPanel("accountFeedback", this.accountField);
        this.accountIContainer.add(this.accountFeedback);
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