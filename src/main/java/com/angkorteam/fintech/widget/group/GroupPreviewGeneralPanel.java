package com.angkorteam.fintech.widget.group;

import org.apache.wicket.markup.html.panel.Panel;

import com.angkorteam.fintech.Page;

public class GroupPreviewGeneralPanel extends Panel {

    private Page itemPage;
    
    public GroupPreviewGeneralPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }
    
}
