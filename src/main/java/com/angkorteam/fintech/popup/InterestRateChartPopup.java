package com.angkorteam.fintech.popup;

import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.provider.LockInTypeProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class InterestRateChartPopup extends PopupPanel {

    protected Form<Void> form;
    protected AjaxButton okayButton;

    protected PropertyModel<Option> periodTypeValue;
    protected LockInTypeProvider periodTypeProvider;
    protected Select2SingleChoice<Option> periodTypeField;
    protected TextFeedbackPanel periodTypeFeedback;

    protected PropertyModel<Long> periodFromValue;
    protected TextField<Long> periodFromField;
    protected TextFeedbackPanel periodFromFeedback;

    protected PropertyModel<Long> periodToValue;
    protected TextField<Long> periodToField;
    protected TextFeedbackPanel periodToFeedback;

    protected PropertyModel<Long> amountRangeFromValue;
    protected TextField<Long> amountRangeFromField;
    protected TextFeedbackPanel amountRangeFromFeedback;

    protected PropertyModel<Long> amountRangeToValue;
    protected TextField<Long> amountRangeToField;
    protected TextFeedbackPanel amountRangeToFeedback;

    protected PropertyModel<Double> interestValue;
    protected TextField<Double> interestField;
    protected TextFeedbackPanel interestFeedback;

    protected PropertyModel<String> descriptionValue;
    protected TextField<String> descriptionField;
    protected TextFeedbackPanel descriptionFeedback;

    public InterestRateChartPopup(String name, Map<String, Object> model) {
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

        this.periodTypeValue = new PropertyModel<>(this.model, "periodTypeValue");
        this.periodTypeProvider = new LockInTypeProvider();
        this.periodTypeField = new Select2SingleChoice<>("periodTypeField", this.periodTypeValue, this.periodTypeProvider);
        this.periodTypeField.setLabel(Model.of("Period Type"));
        this.form.add(this.periodTypeField);
        this.periodTypeFeedback = new TextFeedbackPanel("periodTypeFeedback", this.periodTypeField);
        this.form.add(this.periodTypeFeedback);

        this.periodFromValue = new PropertyModel<>(this.model, "periodFromValue");
        this.periodFromField = new TextField<>("periodFromField", this.periodFromValue);
        this.periodFromField.setLabel(Model.of("Period From"));
        this.periodFromField.setType(Long.class);
        this.form.add(this.periodFromField);
        this.periodFromFeedback = new TextFeedbackPanel("periodFromFeedback", this.periodFromField);
        this.form.add(this.periodFromFeedback);

        this.periodToValue = new PropertyModel<>(this.model, "periodToValue");
        this.periodToField = new TextField<>("periodToField", this.periodToValue);
        this.periodToField.setLabel(Model.of("Period To"));
        this.periodToField.setType(Long.class);
        this.form.add(this.periodToField);
        this.periodToFeedback = new TextFeedbackPanel("periodToFeedback", this.periodToField);
        this.form.add(this.periodToFeedback);

        this.amountRangeFromValue = new PropertyModel<>(this.model, "amountRangeFromValue");
        this.amountRangeFromField = new TextField<>("amountRangeFromField", this.amountRangeFromValue);
        this.amountRangeFromField.setLabel(Model.of("Amount Range From"));
        this.amountRangeFromField.setType(Long.class);
        this.form.add(this.amountRangeFromField);
        this.amountRangeFromFeedback = new TextFeedbackPanel("amountRangeFromFeedback", this.amountRangeFromField);
        this.form.add(this.amountRangeFromFeedback);

        this.amountRangeToValue = new PropertyModel<>(this.model, "amountRangeToValue");
        this.amountRangeToField = new TextField<>("amountRangeToField", new PropertyModel<>(this.model, "amountRangeToValue"));
        this.amountRangeToField.setLabel(Model.of("Amount Range To"));
        this.amountRangeToField.setType(Long.class);
        this.form.add(this.amountRangeToField);
        this.amountRangeToFeedback = new TextFeedbackPanel("amountRangeToFeedback", this.amountRangeToField);
        this.form.add(this.amountRangeToFeedback);

        this.interestValue = new PropertyModel<>(this.model, "interestValue");
        this.interestField = new TextField<>("interestField", this.interestValue);
        this.interestField.setLabel(Model.of("Interest"));
        this.interestField.setType(Double.class);
        this.form.add(this.interestField);
        this.interestFeedback = new TextFeedbackPanel("interestFeedback", this.interestField);
        this.form.add(this.interestFeedback);

        this.descriptionValue = new PropertyModel<>(this.model, "descriptionValue");
        this.descriptionField = new TextField<>("descriptionField", this.descriptionValue);
        this.descriptionField.setLabel(Model.of("Description"));
        this.form.add(this.descriptionField);
        this.descriptionFeedback = new TextFeedbackPanel("descriptionFeedback", this.descriptionField);
        this.form.add(this.descriptionFeedback);
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
