package com.angkorteam.fintech.widget.center;

import org.apache.wicket.markup.html.panel.Panel;

import com.angkorteam.fintech.Page;

public class AccountPreviewTransactionPanel extends Panel {

    protected Page itemPage;

    public AccountPreviewTransactionPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

}
