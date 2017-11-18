package com.angkorteam.fintech.popup;

import java.util.Date;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;

public class RateModifyPopup extends PopupPanel {

    protected ModalWindow window;

    protected Form<Void> form;
    protected AjaxButton saveButton;

    protected WebMarkupBlock fromDateBlock;
    protected WebMarkupContainer fromDateIContainer;
    protected PropertyModel<Date> fromDateValue;
    protected DateTextField fromDateField;
    protected TextFeedbackPanel fromDateFeedback;

    protected WebMarkupBlock interestRateBlock;
    protected WebMarkupContainer interestRateIContainer;
    protected PropertyModel<Double> interestRateValue;
    protected TextField<Double> interestRateField;
    protected TextFeedbackPanel interestRateFeedback;

    protected WebMarkupBlock differentialBlock;
    protected WebMarkupContainer differentialIContainer;
    protected PropertyModel<Boolean> differentialValue;
    protected CheckBox differentialField;
    protected TextFeedbackPanel differentialFeedback;

    protected Map<String, Object> model;

    public RateModifyPopup(String name, ModalWindow window, Map<String, Object> model) {
        super(name, window);
        this.model = model;
        this.window = window;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new AjaxButton("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.fromDateBlock = new WebMarkupBlock("fromDateBlock", Size.Twelve_12);
        this.form.add(this.fromDateBlock);
        this.fromDateIContainer = new WebMarkupContainer("fromDateIContainer");
        this.fromDateBlock.add(this.fromDateIContainer);
        this.fromDateValue = new PropertyModel<>(this.model, "fromDateValue");
        this.fromDateField = new DateTextField("fromDateField", this.fromDateValue);
        this.fromDateField.setLabel(Model.of("From Date"));
        this.fromDateField.setRequired(true);
        this.fromDateIContainer.add(this.fromDateField);
        this.fromDateFeedback = new TextFeedbackPanel("fromDateFeedback", this.fromDateField);
        this.fromDateIContainer.add(this.fromDateFeedback);

        this.interestRateBlock = new WebMarkupBlock("interestRateBlock", Size.Twelve_12);
        this.form.add(this.interestRateBlock);
        this.interestRateIContainer = new WebMarkupContainer("interestRateIContainer");
        this.interestRateBlock.add(this.interestRateIContainer);
        this.interestRateValue = new PropertyModel<>(this.model, "interestRateValue");
        this.interestRateField = new TextField<>("interestRateField", this.interestRateValue);
        this.interestRateField.setType(Double.class);
        this.interestRateField.setLabel(Model.of("Interest Rate"));
        this.interestRateField.setRequired(true);
        this.interestRateIContainer.add(this.interestRateField);
        this.interestRateFeedback = new TextFeedbackPanel("interestRateFeedback", this.interestRateField);
        this.interestRateIContainer.add(this.interestRateFeedback);

        this.differentialBlock = new WebMarkupBlock("differentialBlock", Size.Twelve_12);
        this.form.add(this.differentialBlock);
        this.differentialIContainer = new WebMarkupContainer("differentialIContainer");
        this.differentialBlock.add(this.differentialIContainer);
        this.differentialValue = new PropertyModel<>(this.model, "differentialValue");
        this.differentialField = new CheckBox("differentialField", this.differentialValue);
        this.differentialField.setLabel(Model.of("Differential"));
        this.differentialField.setRequired(true);
        this.differentialIContainer.add(this.differentialField);
        this.differentialFeedback = new TextFeedbackPanel("differentialFeedback", this.differentialField);
        this.differentialIContainer.add(this.differentialFeedback);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected boolean saveButtonSubmit(AjaxButton ajaxButton, AjaxRequestTarget target) {
        this.window.setSignalId(ajaxButton.getId());
        this.window.close(target);
        return true;
    }

}
