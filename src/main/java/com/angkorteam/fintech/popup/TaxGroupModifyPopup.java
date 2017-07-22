package com.angkorteam.fintech.popup;

import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TaxGroupModifyPopup extends Panel {

    private ModalWindow window;

    private Form<Void> form;
    private AjaxButton saveButton;

    private String startDateValue;
    private Label startDateField;

    private Date endDateValue;
    private DateTextField endDateField;
    private TextFeedbackPanel endDateFeedback;

    private Object model;

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

        this.endDateField = new DateTextField("endDateField", new PropertyModel<>(this, "endDateValue"));
        this.endDateField.setRequired(true);
        this.form.add(this.endDateField);
        this.endDateFeedback = new TextFeedbackPanel("endDateFeedback", this.endDateField);
        this.form.add(this.endDateFeedback);
    }

    private void saveButtonSubmit(AjaxButton ajaxButton, AjaxRequestTarget target) {
        PropertyModel<Boolean> taxClick = new PropertyModel<>(this.model, "taxClick");
        taxClick.setObject(true);
        PropertyModel<String> itemIdProperty = new PropertyModel<>(this.model, "itemId");
        String itemId = itemIdProperty.getObject();

        PropertyModel<List<Map<String, Object>>> taxComponentProperty = new PropertyModel<>(this.model, "taxComponentValue");
        List<Map<String, Object>> taxComponentValue = taxComponentProperty.getObject();
        for (Map<String, Object> item : taxComponentValue) {
            if (itemId.equals(String.valueOf(item.get("id")))) {
                item.put("endDate", this.endDateValue);
                break;
            }
        }

        this.window.close(target);
    }

    private void saveButtonError(AjaxButton ajaxButton, AjaxRequestTarget target) {
        target.add(this.form);
    }
}
