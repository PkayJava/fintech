package com.angkorteam.fintech.widget.client;

import org.apache.wicket.markup.html.panel.Panel;

import com.angkorteam.fintech.Page;

public class ClientPreviewFamilyMemberPanel extends Panel {

    private Page itemPage;

    public ClientPreviewFamilyMemberPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

}