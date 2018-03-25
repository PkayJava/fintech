package com.angkorteam.fintech.widget.product.recurring;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;

public class Accounting extends ITab {

    protected Page itemPage;

    public Accounting(Page itemPage) {
        this.itemPage = itemPage;
    }

    @Override
    public IModel<String> getTitle() {
        return Model.of("6. Accounting");
    }

    @Override
    public WebMarkupContainer getPanel(String containerId) {
        return new AccountingPanel(containerId, this.itemPage);
    }

    @Override
    public boolean isVisible() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
