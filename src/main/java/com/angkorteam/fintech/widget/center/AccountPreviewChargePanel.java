package com.angkorteam.fintech.widget.center;

import org.apache.wicket.markup.html.panel.Panel;

import com.angkorteam.fintech.Page;

public class AccountPreviewChargePanel extends Panel {

    protected Page itemPage;

    public AccountPreviewChargePanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

}
