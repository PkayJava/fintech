package com.angkorteam.fintech.popup.client.client;

import java.util.Map;

import com.angkorteam.fintech.popup.PopupPanel;

public class ClientSignaturePopup extends PopupPanel {

    public ClientSignaturePopup(String name, Map<String, Object> model) {
        super(name, model);
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
