package com.angkorteam.fintech.popup;

import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

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

public class OverdueChargePopup extends PopupPanel {

    protected ModalWindow window;

    protected Form<Void> form;
    protected AjaxButton okayButton;

    protected WebMarkupBlock overdueChargeBlock;
    protected WebMarkupContainer overdueChargeIContainer;
    protected SingleChoiceProvider overdueChargeProvider;
    protected Select2SingleChoice<Option> overdueChargeField;
    protected TextFeedbackPanel overdueChargeFeedback;

    protected String currencyCode;

    protected Map<String, Object> model;

    protected ProductPopup productPopup;

    public OverdueChargePopup(String name, ModalWindow window, ProductPopup productPopup, Map<String, Object> model, String currencyCode) {
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

        this.overdueChargeBlock = new WebMarkupBlock("overdueChargeBlock", Size.Six_6);
        this.form.add(this.overdueChargeBlock);
        this.overdueChargeIContainer = new WebMarkupContainer("overdueChargeIContainer");
        this.overdueChargeBlock.add(this.overdueChargeIContainer);
        this.overdueChargeProvider = new SingleChoiceProvider("m_charge", "id", "name");
        if (this.productPopup == ProductPopup.Saving) {
            this.overdueChargeProvider.applyWhere("charge_applies_to_enum", "charge_applies_to_enum = " + ChargeType.SavingDeposit.getLiteral());
        } else if (this.productPopup == ProductPopup.Loan) {
            this.overdueChargeProvider.applyWhere("charge_applies_to_enum", "charge_applies_to_enum = " + ChargeType.Loan.getLiteral());
        }
        this.overdueChargeProvider.applyWhere("currency_code", "currency_code = '" + this.currencyCode + "'");
        this.overdueChargeProvider.applyWhere("is_penalty", "is_penalty = 1");
        this.overdueChargeProvider.applyWhere("is_active", "is_active = 1");
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