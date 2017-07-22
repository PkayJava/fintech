package com.angkorteam.fintech.popup;

import com.angkorteam.fintech.helper.GLAccountHelper;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

/**
 * Created by socheatkhauv on 7/2/17.
 */
public class ReversePopup extends Panel {

    private ModalWindow window;

    private String transactionId;

    private Form<Void> form;
    private AjaxButton reverseButton;

    private String reasonValue;
    private TextArea<String> reasonField;
    private TextFeedbackPanel reasonFeedback;

    private Object model;

    public ReversePopup(String id, ModalWindow window, Object model, String transactionId) {
        super(id);
        this.model = model;
        this.window = window;
        this.transactionId = transactionId;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.form = new Form<>("form");
        add(this.form);

        this.reverseButton = new AjaxButton("reverseButton");
        this.reverseButton.setOnSubmit(this::reverseButtonSubmit);
        this.reverseButton.setOnError(this::reverseButtonError);
        this.form.add(this.reverseButton);

        this.reasonField = new TextArea<>("reasonField", new PropertyModel<>(this, "reasonValue"));
        this.reasonField.setRequired(true);
        this.form.add(this.reasonField);
        this.reasonFeedback = new TextFeedbackPanel("reasonFeedback", this.reasonField);
        this.form.add(this.reasonFeedback);
    }

    private void reverseButtonSubmit(AjaxButton ajaxButton, AjaxRequestTarget target) {
        try {
            GLAccountHelper.reverseEntry(this.transactionId, this.reasonValue);
            PropertyModel<Boolean> reverseClick = new PropertyModel<>(this.model, "reverseClick");
            reverseClick.setObject(true);
            this.window.close(target);
        } catch (UnirestException e) {
        }
    }

    private void reverseButtonError(AjaxButton ajaxButton, AjaxRequestTarget target) {
        target.add(this.form);
    }
}
