package com.angkorteam.fintech.widget.client;

import org.apache.wicket.Page;

import com.angkorteam.fintech.widget.Panel;

public class ClientPreviewDocumentPanel extends Panel {

    private Page itemPage;

    public ClientPreviewDocumentPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initComponent() {
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

}
