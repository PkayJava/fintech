package com.angkorteam.fintech.table;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;

/**
 * Created by socheatkhauv on 6/17/17.
 */
public class TextCell extends ItemPanel {

    public TextCell(IModel<String> model) {
        super(model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        String object = (String) getDefaultModelObject();
        Label text = new Label("text", object);
        add(text);
    }
}
