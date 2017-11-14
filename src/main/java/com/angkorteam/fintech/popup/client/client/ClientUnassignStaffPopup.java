package com.angkorteam.fintech.popup.client.client;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;

import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.markup.html.form.Form;

public class ClientUnassignStaffPopup extends Panel {

    protected ModalWindow window;
    protected Object model;

    protected Form<Void> form;
    protected AjaxButton cancelButton;
    protected AjaxButton confirmButton;

    public ClientUnassignStaffPopup(String id, ModalWindow window, Object model) {
        super(id);
        this.model = model;
        this.window = window;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.form = new Form<>("form");
        add(this.form);

        this.cancelButton = new AjaxButton("cancelButton");
        this.cancelButton.setOnSubmit(this::cancelButtonSubmit);
        this.form.add(this.cancelButton);

        this.confirmButton = new AjaxButton("confirmButton");
        this.confirmButton.setOnSubmit(this::confirmButtonSubmit);
        this.form.add(this.confirmButton);
    }

    protected boolean cancelButtonSubmit(AjaxButton ajaxButton, AjaxRequestTarget target) {
        this.window.setSignalId(ajaxButton.getId());
        this.window.close(target);
        return true;
    }

    protected boolean confirmButtonSubmit(AjaxButton ajaxButton, AjaxRequestTarget target) {
        this.window.setSignalId(ajaxButton.getId());
        this.window.close(target);
        return true;
    }

}
