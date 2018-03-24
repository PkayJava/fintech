package com.angkorteam.fintech.widget;

import org.apache.wicket.model.IModel;

public abstract class Panel extends org.apache.wicket.markup.html.panel.Panel {

    public Panel(String id) {
        super(id);
    }

    public Panel(String id, IModel<?> model) {
        super(id, model);
    }

    @Override
    protected final void onInitialize() {
        initData();
        super.onInitialize();
        initComponent();
        configureMetaData();
    }

    protected abstract void initData();

    protected abstract void initComponent();

    protected abstract void configureMetaData();

}
