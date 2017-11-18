package com.angkorteam.fintech.popup.client.client;

import org.apache.wicket.ajax.AjaxRequestTarget;

import com.angkorteam.fintech.popup.PopupPanel;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.markup.html.form.Form;

public class ClientUnassignStaffPopup extends PopupPanel {

    protected ModalWindow window;
    protected Object model;

    protected Form<Void> form;
    protected AjaxButton cancelButton;
    protected AjaxButton confirmButton;

    public ClientUnassignStaffPopup(String name, ModalWindow window, Object model) {
        super(name, window);
        this.model = model;
        this.window = window;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.cancelButton = new AjaxButton("cancelButton");
        this.cancelButton.setOnSubmit(this::cancelButtonSubmit);
        this.form.add(this.cancelButton);

        this.confirmButton = new AjaxButton("confirmButton");
        this.confirmButton.setOnSubmit(this::confirmButtonSubmit);
        this.form.add(this.confirmButton);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
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
