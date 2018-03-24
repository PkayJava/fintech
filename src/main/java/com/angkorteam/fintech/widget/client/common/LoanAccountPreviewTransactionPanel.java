package com.angkorteam.fintech.widget.client.common;

import org.apache.wicket.Page;

import com.angkorteam.fintech.widget.Panel;

public class LoanAccountPreviewTransactionPanel extends Panel {

    protected Page itemPage;

    public LoanAccountPreviewTransactionPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void initComponent() {
    }

    @Override
    protected void configureMetaData() {
    }

    @Override
    protected void initData() {
    }

}
