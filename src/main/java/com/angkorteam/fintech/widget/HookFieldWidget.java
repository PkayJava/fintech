package com.angkorteam.fintech.widget;

import java.util.Map;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

/**
 * Created by socheatkhauv on 6/27/17.
 */
public class HookFieldWidget extends Panel {

    private String itemName;
    private Map<String, String> itemValue;
    private Label itemLabel;
    private TextField<String> itemField;
    private TextFeedbackPanel itemFeedback;

    public HookFieldWidget(String id, String itemName, Map<String, String> itemValue) {
        super(id);
        this.itemName = itemName;
        this.itemValue = itemValue;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.itemLabel = new Label("itemLabel", this.itemName);
        add(this.itemLabel);
        this.itemField = new TextField<>("itemField", new PropertyModel<>(this.itemValue, this.itemName));
        this.itemField.setRequired(true);
        add(this.itemField);
        this.itemFeedback = new TextFeedbackPanel("itemFeedback", this.itemField);
        add(this.itemFeedback);
    }
}
