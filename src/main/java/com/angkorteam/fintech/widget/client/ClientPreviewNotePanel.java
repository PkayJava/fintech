package com.angkorteam.fintech.widget.client;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.panel.Panel;
 

public class ClientPreviewNotePanel extends Panel {

    private Page itemPage;

    public ClientPreviewNotePanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

}
