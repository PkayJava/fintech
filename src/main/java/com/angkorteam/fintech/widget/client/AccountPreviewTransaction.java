package com.angkorteam.fintech.widget.client;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;

public class AccountPreviewTransaction extends ITab {

    protected Page itemPage;

    public AccountPreviewTransaction(Page itemPage) {
        this.itemPage = itemPage;
    }

    @Override
    public IModel<String> getTitle() {
        return Model.of("Transaction");
    }

    @Override
    public WebMarkupContainer getPanel(String containerId) {
        return new AccountPreviewTransactionPanel(containerId, this.itemPage);
    }

    @Override
    public boolean isVisible() {
        return true;
    }

}
