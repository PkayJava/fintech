package com.angkorteam.fintech.widget;

import java.io.Serializable;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

public class ReadOnlyView extends Label {

    public ReadOnlyView(String id, IModel<?> model) {
        super(id, model);
    }

    public ReadOnlyView(String id, Serializable label) {
        super(id, label);
    }

    public ReadOnlyView(String id) {
        super(id);
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        tag.put("readonly", "readonly");
    }

}
