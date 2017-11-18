package com.angkorteam.fintech.widget.group;

import org.apache.wicket.Page;

import com.angkorteam.fintech.widget.Panel;

public class GroupPreviewNotePanel extends Panel {

    private Page itemPage;

    public GroupPreviewNotePanel(String id, Page itemPage) {
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
