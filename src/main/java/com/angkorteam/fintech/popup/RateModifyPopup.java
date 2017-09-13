package com.angkorteam.fintech.popup;

import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

public class RateModifyPopup extends Panel {

    private ModalWindow window;

    private Form<Void> form;
    private AjaxButton saveButton;

    private DateTextField fromDateField;
    private TextFeedbackPanel fromDateFeedback;

    private TextField<Double> interestRateField;
    private TextFeedbackPanel interestRateFeedback;

    private CheckBox differentialField;
    private TextFeedbackPanel differentialFeedback;

    private Object model;

    public RateModifyPopup(String id, ModalWindow window, Object model) {
        super(id);
        this.model = model;
        this.window = window;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new AjaxButton("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.saveButton.setOnError(this::saveButtonError);
        this.form.add(this.saveButton);

        this.fromDateField = new DateTextField("fromDateField", new PropertyModel<>(this.model, "itemFromDateValue"));
        this.fromDateField.setRequired(true);
        this.form.add(this.fromDateField);
        this.fromDateFeedback = new TextFeedbackPanel("fromDateFeedback", this.fromDateField);
        this.form.add(this.fromDateFeedback);

        this.interestRateField = new TextField<>("interestRateField",
                new PropertyModel<>(this.model, "itemInterestRateValue"));
        this.interestRateField.setRequired(true);
        this.form.add(this.interestRateField);
        this.interestRateFeedback = new TextFeedbackPanel("interestRateFeedback", this.interestRateField);
        this.form.add(this.interestRateFeedback);

        this.differentialField = new CheckBox("differentialField",
                new PropertyModel<>(this.model, "itemDifferentialValue"));
        this.differentialField.setRequired(true);
        this.form.add(this.differentialField);
        this.differentialFeedback = new TextFeedbackPanel("differentialFeedback", this.differentialField);
        this.form.add(this.differentialFeedback);
    }

    protected boolean saveButtonSubmit(AjaxButton ajaxButton, AjaxRequestTarget target) {
        this.window.setElementId(ajaxButton.getId());
        this.window.close(target);
        return true;
    }

    protected boolean saveButtonError(AjaxButton ajaxButton, AjaxRequestTarget target) {
        target.add(this.form);
        return true;
    }

}
