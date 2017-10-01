package com.angkorteam.fintech.widget.center;

import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class CenterGeneral implements ITab {

    @Override
    public IModel<String> getTitle() {
        return Model.of("General");
    }

    @Override
    public WebMarkupContainer getPanel(String containerId) {
        return new CenterGeneralPanel(containerId, null);
    }

    @Override
    public boolean isVisible() {
        return true;
    }

}
