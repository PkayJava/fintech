package com.angkorteam.fintech.widget;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;

public class Webcam extends FormComponent<String> {

    public Webcam(String id) {
        super(id);
    }

    public Webcam(String id, IModel<String> model) {
        super(id, model);
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        // Must be attached to an input tag
        checkComponentTag(tag, "div");

        tag.put("value", getValue());

        // Default handling for component tag
        super.onComponentTag(tag);
    }
    
    // WebComponent
}
