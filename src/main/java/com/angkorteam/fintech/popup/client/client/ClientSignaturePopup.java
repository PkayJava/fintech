package com.angkorteam.fintech.popup.client.client;

import com.angkorteam.fintech.popup.PopupPanel;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;

public class ClientSignaturePopup extends PopupPanel {

    protected ModalWindow window;
    protected Object model;

    public ClientSignaturePopup(String name, ModalWindow window, Object model) {
        super(name, window);
        this.model = model;
        this.window = window;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initComponent() {
        // https://demo.openmf.org/fineract-provider/api/v1/clients/6/documents/84/attachment?tenantIdentifier=default
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

}
