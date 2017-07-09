package com.angkorteam.fintech.table;

import com.angkorteam.framework.BadgeType;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

/**
 * Created by socheatkhauv on 6/17/17.
 */
public class BadgeCell extends ItemPanel {

    private BadgeType type;

    public BadgeCell(BadgeType type, IModel<String> model) {
        super(model);
        this.type = type;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        WebMarkupContainer badge = new WebMarkupContainer("badge");
        add(badge);
        if (this.type != null) {
            badge.add(AttributeModifier.append("class", this.type.getLiteral()));
        }
        String object = (String) getDefaultModelObject();
        Label text = new Label("text", object);
        badge.add(text);
    }
}
