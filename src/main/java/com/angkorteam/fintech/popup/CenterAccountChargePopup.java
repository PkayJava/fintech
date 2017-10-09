package com.angkorteam.fintech.popup;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.markup.html.form.DayMonthTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class CenterAccountChargePopup extends Panel {

    protected ModalWindow window;

    protected Form<Void> form;
    protected AjaxButton okayButton;

    protected SingleChoiceProvider chargeProvider;
    protected Select2SingleChoice<Option> chargeField;
    protected TextFeedbackPanel chargeFeedback;

    protected Label chargeTypeField;

    protected TextField<Double> amountField;
    protected TextFeedbackPanel amountFeedback;

    protected Label collectedOnField;

    protected PropertyModel<String> dateValue;
    protected DayMonthTextField dateField;
    protected TextFeedbackPanel dateFeedback;

    protected TextField<Integer> repaymentEveryField;
    protected TextFeedbackPanel repaymentEveryFeedback;

    protected String currencyCode;

    protected Object model;

    public CenterAccountChargePopup(String id, ModalWindow window, Object model, String currencyCode) {
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
        this.chargeProvider.applyWhere("currency_code", "currency_code = '" + this.currencyCode + "'");
        this.chargeProvider.applyWhere("charge_applies_to_enum", "charge_applies_to_enum = 2");
        this.chargeProvider.applyWhere("is_active", "is_active = 1");
        this.chargeField = new Select2SingleChoice<>("chargeField", 0, new PropertyModel<>(this.model, "itemChargeValue"), this.chargeProvider);
        this.chargeField.add(new OnChangeAjaxBehavior(this::chargeFieldUpdate));
        this.chargeField.setLabel(Model.of("charge"));
        this.form.add(this.chargeField);
        this.chargeFeedback = new TextFeedbackPanel("chargeFeedback", this.chargeField);
        this.form.add(this.chargeFeedback);
         

    }
    
    protected boolean chargeFieldUpdate(AjaxRequestTarget target) {
        
        return false;
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