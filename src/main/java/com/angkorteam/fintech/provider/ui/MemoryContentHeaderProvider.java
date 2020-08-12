package com.angkorteam.fintech.provider.ui;

import com.angkorteam.webui.frmk.model.ContentHeader;
import com.angkorteam.webui.frmk.provider.IContentHeaderProvider;

public class MemoryContentHeaderProvider implements IContentHeaderProvider {

    private final ContentHeader contentHeader;

    public MemoryContentHeaderProvider(ContentHeader contentHeader) {
        this.contentHeader = contentHeader;
    }

    @Override
    public ContentHeader fetchContentHeader() {
        return this.contentHeader;
    }

}
