package com.angkorteam.fintech.popup.share;

import java.util.Date;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;

public class MarketPricePopup extends Panel {

    protected ModalWindow window;

    protected Form<Void> form;
    protected AjaxButton okayButton;

    protected WebMarkupBlock fromDateBlock;
    protected WebMarkupContainer fromDateIContainer;
    protected PropertyModel<Date> fromDateValue;
    protected DateTextField fromDateField;
    protected TextFeedbackPanel fromDateFeedback;

    protected WebMarkupBlock unitPriceBlock;
    protected WebMarkupContainer unitPriceIContainer;
    protected PropertyModel<Double> unitPriceValue;
    protected TextField<Double> unitPriceField;
    protected TextFeedbackPanel unitPriceFeedback;

    protected Map<String, Object> model;

    public MarketPricePopup(String id, ModalWindow window, Map<String, Object> model) {
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
        this.okayButton.setOnError(this::okayButtonError);
        this.form.add(this.okayButton);

        this.fromDateBlock = new WebMarkupBlock("fromDateBlock", Size.Six_6);
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

        this.unitPriceBlock = new WebMarkupBlock("unitPriceBlock", Size.Six_6);
        this.form.add(this.unitPriceBlock);
        this.unitPriceIContainer = new WebMarkupContainer("unitPriceIContainer");
        this.unitPriceBlock.add(this.unitPriceIContainer);
        this.unitPriceValue = new PropertyModel<>(this.model, "unitPriceValue");
        this.unitPriceField = new TextField<>("unitPriceField", this.unitPriceValue);
        this.unitPriceField.setType(Double.class);
        this.unitPriceField.setLabel(Model.of("Unit Price"));
        this.unitPriceField.setRequired(true);
        this.unitPriceIContainer.add(this.unitPriceField);
        this.unitPriceFeedback = new TextFeedbackPanel("unitPriceFeedback", this.unitPriceField);
        this.unitPriceIContainer.add(this.unitPriceFeedback);
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