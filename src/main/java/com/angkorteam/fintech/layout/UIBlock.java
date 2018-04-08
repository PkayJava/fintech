package com.angkorteam.fintech.layout;

import org.apache.http.util.Args;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;

public class UIBlock extends WebMarkupContainer {

    protected Size size;

    public UIBlock(String id, Size size) {
        super(id);
        this.size = size;
        Args.notNull(size, "size");
        setOutputMarkupId(true);
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        String clazz = tag.getAttribute("class");
        if (clazz == null || "".equals(clazz)) {
            clazz = this.size.getCss();
        } else {
            if (!clazz.contains(this.size.getCss())) {
                clazz = clazz + " " + this.size.getCss();
            }
        }
        if (clazz != null && !"".equals(clazz)) {
            tag.put("class", clazz);
        }
    }

    public UIContainer newUIContainer(String id) {
        UIContainer container = new UIContainer(id);
        add(container);
        return container;
    }

}
