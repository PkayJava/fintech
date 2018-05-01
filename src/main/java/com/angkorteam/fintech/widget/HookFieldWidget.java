package com.angkorteam.fintech.widget;

import java.util.Map;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;

/**
 * Created by socheatkhauv on 6/27/17.
 */
public class HookFieldWidget extends Panel {

    protected UIRow row;

    protected UIBlock rowBlock;

    protected UIContainer rowContainer;
    protected String itemName;
    protected Map<String, String> itemValue;
    protected Label itemLabel;
    protected TextField<String> itemField;

    public HookFieldWidget(String id, String itemName, Map<String, String> itemValue) {
        super(id);
        this.itemName = itemName;
        this.itemValue = itemValue;
    }

    @Override
    protected final void onInitialize() {
        initData();
        super.onInitialize();
        initComponent();
        configureMetaData();
    }

    protected void initData() {
    }

    protected void initComponent() {
        this.row = UIRow.newUIRow("row", this);

        this.rowBlock = this.row.newUIBlock("rowBlock", Size.Twelve_12);
        this.rowContainer = this.rowBlock.newUIContainer("rowContainer");
        this.itemLabel = new Label("itemLabel", this.itemName);
        this.rowContainer.add(this.itemLabel);
        this.itemField = new TextField<>("itemField", new PropertyModel<>(this.itemValue, this.itemName));
        this.rowContainer.add(this.itemField);
        this.rowContainer.newFeedback("itemFeedback", this.itemField);
    }

    protected void configureMetaData() {
        this.itemField.setRequired(true);
    }
}
