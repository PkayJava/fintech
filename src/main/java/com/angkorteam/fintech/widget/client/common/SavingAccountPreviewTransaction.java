package com.angkorteam.fintech.widget.client.common;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;

public class SavingAccountPreviewTransaction extends ITab {

    protected Page itemPage;

    public SavingAccountPreviewTransaction(Page itemPage) {
        this.itemPage = itemPage;
    }

    @Override
    public IModel<String> getTitle() {
        return Model.of("Transaction");
    }

    @Override
    public WebMarkupContainer getPanel(String containerId) {
        return new SavingAccountPreviewTransactionPanel(containerId, this.itemPage);
    }

    @Override
    public boolean isVisible() {
        return true;
    }

}
