package com.angkorteam.fintech.popup;

import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class ClientPopup extends PopupPanel {

    protected ModalWindow window;

    protected Form<Void> form;
    protected AjaxButton okayButton;

    protected SingleChoiceProvider clientProvider;
    protected Select2SingleChoice<Option> clientField;
    protected TextFeedbackPanel clientFeedback;

    protected String officeId;

    protected Map<String, Object> model;

    public ClientPopup(String name, ModalWindow window, Map<String, Object> model, String officeId) {
        super(name, window);
        this.model = model;
        this.window = window;
        this.officeId = officeId;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.okayButton = new AjaxButton("okayButton");
        this.okayButton.setOnSubmit(this::okayButtonSubmit);
        this.okayButton.setOnError(this::okayButtonError);
        this.form.add(this.okayButton);

        this.clientProvider = new SingleChoiceProvider("m_client", "id", "display_name");
        this.clientProvider.applyWhere("office_id", "office_id = " + this.officeId);
        this.clientField = new Select2SingleChoice<>("clientField", new PropertyModel<>(this.model, "clientValue"), this.clientProvider);
        this.clientField.setLabel(Model.of("Client"));
        this.form.add(this.clientField);
        this.clientFeedback = new TextFeedbackPanel("clientFeedback", this.clientField);
        this.form.add(this.clientFeedback);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
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