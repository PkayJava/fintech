package com.angkorteam.fintech.popup;

import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.helper.GLAccountHelper;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.markup.html.form.Form;

/**
 * Created by socheatkhauv on 7/2/17.
 */
public class ReversePopup extends PopupPanel {

    protected String transactionId;

    protected Form<Void> form;
    protected AjaxButton reverseButton;

    protected String reasonValue;
    protected TextArea<String> reasonField;
    protected TextFeedbackPanel reasonFeedback;

    public ReversePopup(String name, Map<String, Object> model, String transactionId) {
        super(name, model);
        this.transactionId = transactionId;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.reverseButton = new AjaxButton("reverseButton");
        this.reverseButton.setOnSubmit(this::reverseButtonSubmit);
        this.reverseButton.setOnError(this::reverseButtonError);
        this.form.add(this.reverseButton);

        this.reasonField = new TextArea<>("reasonField", new PropertyModel<>(this, "reasonValue"));
        this.reasonField.setLabel(Model.of("Reason"));
        this.reasonField.setRequired(true);
        this.form.add(this.reasonField);
        this.reasonFeedback = new TextFeedbackPanel("reasonFeedback", this.reasonField);
        this.form.add(this.reasonFeedback);
    }

    @Override
    protected void configureMetaData() {
    }

    protected boolean reverseButtonSubmit(AjaxButton ajaxButton, AjaxRequestTarget target) {
        this.window.setSignalId(ajaxButton.getId());
        GLAccountHelper.reverseEntry((Session) getSession(), this.transactionId, this.reasonValue);
        this.window.close(target);

        return true;
    }

    protected boolean reverseButtonError(AjaxButton ajaxButton, AjaxRequestTarget target) {
        target.add(this.form);
        return true;
    }
}
