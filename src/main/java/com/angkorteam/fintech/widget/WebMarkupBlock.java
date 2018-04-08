package com.angkorteam.fintech.widget;

import org.apache.http.util.Args;
import org.apache.wicket.markup.ComponentTag;
import com.angkorteam.fintech.widget.WebMarkupContainer;

import com.angkorteam.fintech.layout.Size;

@Deprecated
public class WebMarkupBlock extends WebMarkupContainer {

    protected Size size;

    public WebMarkupBlock(String id, Size size) {
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

}
