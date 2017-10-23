package com.angkorteam.fintech.popup;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;

public class TaxGroupModifyPopup extends Panel {

    protected ModalWindow window;

    protected Form<Void> form;
    protected AjaxButton saveButton;

    protected Label startDateView;

    protected DateTextField endDateField;
    protected TextFeedbackPanel endDateFeedback;

    protected Object model;

    public TaxGroupModifyPopup(String id, ModalWindow window, Object model) {
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

        this.startDateView = new Label("startDateView", new PropertyModel<>(this.model, "itemStartDateValue"));
        this.form.add(this.startDateView);

        this.endDateField = new DateTextField("endDateField", new PropertyModel<>(this.model, "itemEndDateValue"));
        this.endDateField.setLabel(Model.of("End Date"));
        this.endDateField.setRequired(true);
        this.form.add(this.endDateField);
        this.endDateFeedback = new TextFeedbackPanel("endDateFeedback", this.endDateField);
        this.form.add(this.endDateFeedback);
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
