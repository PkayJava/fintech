package com.angkorteam.fintech.popup;

import java.util.Map;

public abstract class PopupPanel extends com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.PopupPanel {

    public PopupPanel(String name, Map<String, Object> model) {
        super(name, model);
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
