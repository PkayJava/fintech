package com.angkorteam.fintech.popup;

import java.util.Date;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;

public class FloatingRatePeriodPopup extends PopupPanel {

    protected Form<Void> form;
    protected AjaxButton saveButton;

    protected UIRow row1;

    protected UIBlock fromDateBlock;
    protected UIContainer fromDateIContainer;
    protected PropertyModel<Date> fromDateValue;
    protected DateTextField fromDateField;

    protected UIRow row2;

    protected UIBlock interestRateBlock;
    protected UIContainer interestRateIContainer;
    protected PropertyModel<Double> interestRateValue;
    protected TextField<Double> interestRateField;

    protected UIRow row3;

    protected UIBlock differentialBlock;
    protected UIContainer differentialIContainer;
    protected PropertyModel<Boolean> differentialValue;
    protected CheckBox differentialField;

    public FloatingRatePeriodPopup(String name, Map<String, Object> model) {
        super(name, model);
    }

    @Override
    protected void initData() {
        this.fromDateValue = new PropertyModel<>(this.model, "fromDateValue");
        this.interestRateValue = new PropertyModel<>(this.model, "interestRateValue");
        this.differentialValue = new PropertyModel<>(this.model, "differentialValue");
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new AjaxButton("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.fromDateBlock = this.row1.newUIBlock("fromDateBlock", Size.Twelve_12);
        this.fromDateIContainer = this.fromDateBlock.newUIContainer("fromDateIContainer");
        this.fromDateField = new DateTextField("fromDateField", this.fromDateValue);
        this.fromDateIContainer.add(this.fromDateField);
        this.fromDateIContainer.newFeedback("fromDateFeedback", this.fromDateField);

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.interestRateBlock = this.row2.newUIBlock("interestRateBlock", Size.Twelve_12);
        this.interestRateIContainer = this.interestRateBlock.newUIContainer("interestRateIContainer");
        this.interestRateField = new TextField<>("interestRateField", this.interestRateValue);
        this.interestRateIContainer.add(this.interestRateField);
        this.interestRateIContainer.newFeedback("interestRateFeedback", this.interestRateField);

        this.row3 = UIRow.newUIRow("row3", this.form);

        this.differentialBlock = this.row3.newUIBlock("differentialBlock", Size.Twelve_12);
        this.differentialIContainer = this.differentialBlock.newUIContainer("differentialIContainer");
        this.differentialField = new CheckBox("differentialField", this.differentialValue);
        this.differentialIContainer.add(this.differentialField);
        this.differentialIContainer.newFeedback("differentialFeedback", this.differentialField);
    }

    @Override
    protected void configureMetaData() {
        this.differentialField.setLabel(Model.of("Differential"));
        this.differentialField.setRequired(true);

        this.interestRateField.setType(Double.class);
        this.interestRateField.setLabel(Model.of("Interest Rate"));
        this.interestRateField.setRequired(true);

        this.fromDateField.setLabel(Model.of("From Date"));
        this.fromDateField.setRequired(true);
    }

    protected boolean saveButtonSubmit(AjaxButton ajaxButton, AjaxRequestTarget target) {
        this.window.setSignalId(ajaxButton.getId());
        this.window.close(target);
        return true;
    }

}
