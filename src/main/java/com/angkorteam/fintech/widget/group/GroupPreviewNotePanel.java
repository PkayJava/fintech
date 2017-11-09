package com.angkorteam.fintech.widget.group;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.panel.Panel;
 

public class GroupPreviewNotePanel extends Panel {

    private Page itemPage;
    
    public GroupPreviewNotePanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }
    
}
