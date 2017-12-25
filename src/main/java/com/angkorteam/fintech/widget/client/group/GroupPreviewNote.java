package com.angkorteam.fintech.widget.client.group;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;

public class GroupPreviewNote extends ITab {

    protected Page itemPage;

    public GroupPreviewNote(Page itemPage) {
        this.itemPage = itemPage;
    }

    @Override
    public IModel<String> getTitle() {
        return Model.of("Note");
    }

    @Override
    public WebMarkupContainer getPanel(String containerId) {
        return new GroupPreviewNotePanel(containerId, this.itemPage);
    }

    @Override
    public boolean isVisible() {
        return true;
    }

}