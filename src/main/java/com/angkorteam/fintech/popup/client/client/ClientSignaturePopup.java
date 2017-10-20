package com.angkorteam.fintech.popup.client.client;

import org.apache.wicket.markup.html.panel.Panel;

import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;

public class ClientSignaturePopup extends Panel {

    protected ModalWindow window;
    protected Object model;

    public ClientSignaturePopup(String id, ModalWindow window, Object model) {
        super(id);
        this.model = model;
        this.window = window;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        // https://demo.openmf.org/fineract-provider/api/v1/clients/6/documents/84/attachment?tenantIdentifier=default
    }

}
