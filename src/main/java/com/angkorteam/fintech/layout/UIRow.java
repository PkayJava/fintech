package com.angkorteam.fintech.layout;

import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebMarkupContainer;

public class UIRow extends WebMarkupContainer {

    private int size = 0;

    protected UIRow(String id) {
        super(id);
        setOutputMarkupId(true);
    }

    public static UIRow newUIRow(String id, WebMarkupContainer parent) {
        UIRow row = new UIRow(id);
        parent.add(row);
        return row;
    }

    public static UIRow newUIRow(String id, Page parent) {
        UIRow row = new UIRow(id);
        parent.add(row);
        return row;
    }

    public UIBlock newUIBlock(String id, Size size) {
        UIBlock block = new UIBlock(id, size);
        add(block);
        this.size = this.size + size.getSize();
        return block;
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();
        if (this.size != 12) {
            throw new WicketRuntimeException(this.getId() + " size is " + this.size);
        }
    }

    @Override
    public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
        super.onComponentTagBody(markupStream, openTag);
        getWebResponse().write("<div class=\"clearfix\"></div>");
    }

}
