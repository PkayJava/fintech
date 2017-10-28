package com.angkorteam.fintech.popup;

import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;

public class TaxGroupModifyPopup extends Panel {

    protected ModalWindow window;

    protected Form<Void> form;
    protected AjaxButton saveButton;

    protected WebMarkupBlock startDateBlock;
    protected WebMarkupContainer startDateVContainer;
    protected PropertyModel<String> startDateValue;
    protected Label startDateView;

    protected WebMarkupBlock endDateBlock;
    protected WebMarkupContainer endDateIContainer;
    protected DateTextField endDateField;
    protected TextFeedbackPanel endDateFeedback;
    protected PropertyModel<Date> endDateValue;

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

        this.startDateValue = new PropertyModel<>(this.model, "taxComponentItemStartDateValue");
        this.startDateBlock = new WebMarkupBlock("startDateBlock", Size.Twelve_12);
        this.form.add(this.startDateBlock);
        this.startDateVContainer = new WebMarkupContainer("startDateVContainer");
        this.startDateBlock.add(this.startDateVContainer);
        this.startDateView = new Label("startDateView", this.startDateValue);
        this.form.add(this.startDateView);

        this.endDateBlock = new WebMarkupBlock("endDateBlock", Size.Twelve_12);
        this.form.add(this.endDateBlock);
        this.endDateIContainer = new WebMarkupContainer("endDateIContainer");
        this.endDateBlock.add(this.endDateIContainer);
        this.endDateValue = new PropertyModel<>(this.model, "taxComponentItemEndDateValue");
        this.endDateField = new DateTextField("endDateField", this.endDateValue);
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
