package com.angkorteam.fintech.widget;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.form.FormComponent;

public class TextFeedbackPanel extends com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel {

    public TextFeedbackPanel(String id, FormComponent<?> formComponent) {
        super(id, formComponent);
        add(AttributeModifier.replace("style", "color:red"));
    }

}
