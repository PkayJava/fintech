package com.angkorteam.fintech.layout;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.FormComponent;

import com.angkorteam.fintech.widget.TextFeedbackPanel;

public class UIContainer extends WebMarkupContainer {

    protected UIContainer(final String id) {
        super(id);
        setOutputMarkupId(true);
    }

    public TextFeedbackPanel newFeedback(String id, FormComponent<?> formComponent) {
        TextFeedbackPanel feedback = new TextFeedbackPanel(id, formComponent);
        add(feedback);
        return feedback;
    }

}