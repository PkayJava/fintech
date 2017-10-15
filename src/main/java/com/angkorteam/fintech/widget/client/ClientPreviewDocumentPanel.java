package com.angkorteam.fintech.widget.client;

import org.apache.wicket.markup.html.panel.Panel;

import com.angkorteam.fintech.Page;

public class ClientPreviewDocumentPanel extends Panel {

    private Page itemPage;

    public ClientPreviewDocumentPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

}
