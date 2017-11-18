package com.angkorteam.fintech.widget.center;

import org.apache.wicket.Page;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
 

public class AccountPreviewTransaction implements ITab {

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
