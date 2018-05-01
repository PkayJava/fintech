package com.angkorteam.fintech.popup;

import java.util.Date;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
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

public class MarketPricePopup extends PopupPanel {

    protected Form<Void> form;
    protected AjaxButton okayButton;

    protected UIRow row1;

    protected UIBlock fromDateBlock;
    protected UIContainer fromDateIContainer;
    protected PropertyModel<Date> fromDateValue;
    protected DateTextField fromDateField;

    protected UIBlock unitPriceBlock;
    protected UIContainer unitPriceIContainer;
    protected PropertyModel<Double> unitPriceValue;
    protected TextField<Double> unitPriceField;

    public MarketPricePopup(String name, Map<String, Object> model) {
        super(name, model);
    }

    @Override
    protected void initData() {
        this.fromDateValue = new PropertyModel<>(this.model, "fromDateValue");
        this.unitPriceValue = new PropertyModel<>(this.model, "unitPriceValue");
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.okayButton = new AjaxButton("okayButton");
        this.okayButton.setOnSubmit(this::okayButtonSubmit);
        this.okayButton.setOnError(this::okayButtonError);
        this.form.add(this.okayButton);

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.fromDateBlock = this.row1.newUIBlock("fromDateBlock", Size.Six_6);
        this.fromDateIContainer = this.fromDateBlock.newUIContainer("fromDateIContainer");
        this.fromDateField = new DateTextField("fromDateField", this.fromDateValue);
        this.fromDateIContainer.add(this.fromDateField);
        this.fromDateIContainer.newFeedback("fromDateFeedback", this.fromDateField);

        this.unitPriceBlock = this.row1.newUIBlock("unitPriceBlock", Size.Six_6);
        this.unitPriceIContainer = this.unitPriceBlock.newUIContainer("unitPriceIContainer");
        this.unitPriceField = new TextField<>("unitPriceField", this.unitPriceValue);
        this.unitPriceIContainer.add(this.unitPriceField);
        this.unitPriceIContainer.newFeedback("unitPriceFeedback", this.unitPriceField);
    }

    @Override
    protected void configureMetaData() {
        this.unitPriceField.setType(Double.class);
        this.unitPriceField.setLabel(Model.of("Unit Price"));
        this.unitPriceField.setRequired(true);

        this.fromDateField.setLabel(Model.of("From Date"));
        this.fromDateField.setRequired(true);
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