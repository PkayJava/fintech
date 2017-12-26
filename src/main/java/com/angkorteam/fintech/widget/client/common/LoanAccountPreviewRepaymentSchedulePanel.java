package com.angkorteam.fintech.widget.client.common;

import org.apache.wicket.Page;

import com.angkorteam.fintech.widget.Panel;

public class LoanAccountPreviewRepaymentSchedulePanel extends Panel {

    protected Page itemPage;

    public LoanAccountPreviewRepaymentSchedulePanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void initComponent() {
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    @Override
    protected void initData() {
    }

}
