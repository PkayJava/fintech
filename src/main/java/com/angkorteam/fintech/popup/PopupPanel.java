package com.angkorteam.fintech.popup;

import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;

public abstract class PopupPanel extends com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.PopupPanel {

    public PopupPanel(String name, ModalWindow window) {
        super(name, window);
    }

    @Override
    protected final void onInitialize() {
        initData();
        super.onInitialize();
        initComponent();
        configureRequiredValidation();
        configureMetaData();
    }

    protected abstract void initData();

    protected abstract void initComponent();

    protected abstract void configureRequiredValidation();

    protected abstract void configureMetaData();

}
