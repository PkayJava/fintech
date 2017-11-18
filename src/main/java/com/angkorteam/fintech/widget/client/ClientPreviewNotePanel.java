package com.angkorteam.fintech.widget.client;

import org.apache.wicket.Page;

import com.angkorteam.fintech.widget.Panel;

public class ClientPreviewNotePanel extends Panel {

    private Page itemPage;

    public ClientPreviewNotePanel(String id, Page itemPage) {
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
