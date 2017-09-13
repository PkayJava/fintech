package com.angkorteam.fintech.popup.share;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.fintech.widget.TextFeedbackPanel;

public class MarketPricePopup extends Panel {

    private ModalWindow window;

    private Form<Void> form;
    private AjaxButton okayButton;

    private DateTextField fromDateField;
    private TextFeedbackPanel fromDateFeedback;

    private TextField<Double> unitPriceField;
    private TextFeedbackPanel unitPriceFeedback;

    private Object model;

    public MarketPricePopup(String id, ModalWindow window, Object model) {
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

        this.fromDateField = new DateTextField("fromDateField", new PropertyModel<>(this.model, "itemFromDateValue"));
        this.fromDateField.setRequired(true);
        this.form.add(this.fromDateField);
        this.fromDateFeedback = new TextFeedbackPanel("fromDateFeedback", this.fromDateField);
        this.form.add(this.fromDateFeedback);

        this.unitPriceField = new TextField<>("unitPriceField", new PropertyModel<>(this.model, "itemUnitPriceValue"));
        this.unitPriceField.setRequired(true);
        this.form.add(this.unitPriceField);
        this.unitPriceFeedback = new TextFeedbackPanel("unitPriceFeedback", this.unitPriceField);
        this.form.add(this.unitPriceFeedback);
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