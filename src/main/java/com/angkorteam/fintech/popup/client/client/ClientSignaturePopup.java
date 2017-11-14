package com.angkorteam.fintech.popup.client.client;

import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.PopupPanel;

public class ClientSignaturePopup extends PopupPanel {

    protected ModalWindow window;
    protected Object model;

    public ClientSignaturePopup(String name, ModalWindow window, Object model) {
        super(name, window);
        this.model = model;
        this.window = window;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        // https://demo.openmf.org/fineract-provider/api/v1/clients/6/documents/84/attachment?tenantIdentifier=default
    }

}
