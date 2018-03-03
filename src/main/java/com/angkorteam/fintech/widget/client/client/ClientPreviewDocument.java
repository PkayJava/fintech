package com.angkorteam.fintech.widget.client.client;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;

public class ClientPreviewDocument extends ITab {

    protected Page itemPage;

    public ClientPreviewDocument(Page itemPage) {
        this.itemPage = itemPage;
    }

    @Override
    public IModel<String> getTitle() {
        return Model.of("Documents");
    }

    @Override
    public WebMarkupContainer getPanel(String containerId) {
        return new ClientPreviewDocumentPanel(containerId, this.itemPage);
    }

    @Override
    public boolean isVisible() {
        return true;
    }

}