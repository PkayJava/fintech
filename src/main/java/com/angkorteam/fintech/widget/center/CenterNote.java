package com.angkorteam.fintech.widget.center;

import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class CenterNote implements ITab {

    @Override
    public IModel<String> getTitle() {
        return Model.of("Notes");
    }

    @Override
    public WebMarkupContainer getPanel(String containerId) {
        return new CenterNotePanel(containerId, null);
    }

    @Override
    public boolean isVisible() {
        return true;
    }

}
