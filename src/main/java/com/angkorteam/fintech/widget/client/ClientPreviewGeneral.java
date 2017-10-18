package com.angkorteam.fintech.widget.client;

import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.widget.group.GroupPreviewCommitteePanel;

public class ClientPreviewGeneral implements ITab {

    private Page itemPage;

    public ClientPreviewGeneral(Page itemPage) {
        this.itemPage = itemPage;
    }

    @Override
    public IModel<String> getTitle() {
        return Model.of("General");
    }

    @Override
    public WebMarkupContainer getPanel(String containerId) {
        return new ClientPreviewGeneralPanel(containerId, this.itemPage);
    }

    @Override
    public boolean isVisible() {
        return true;
    }

}