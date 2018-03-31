package com.angkorteam.fintech.widget.product.share;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;

public class MarketPrice extends ITab {

    protected Page itemPage;

    public MarketPrice(Page itemPage) {
        this.itemPage = itemPage;
    }

    @Override
    public IModel<String> getTitle() {
        return Model.of("5. Market Price");
    }

    @Override
    public WebMarkupContainer getPanel(String containerId) {
        return new MarketPricePanel(containerId, this.itemPage);
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
