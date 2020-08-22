package com.angkorteam.fintech.pages.admin.system.hook;

import com.angkorteam.webui.frmk.wicket.layout.Size;
import com.angkorteam.webui.frmk.wicket.layout.UIColumn;
import com.angkorteam.webui.frmk.wicket.layout.UIContainer;
import com.angkorteam.webui.frmk.wicket.layout.UIRow;
import com.angkorteam.webui.frmk.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;

import java.util.Map;

/**
 * Created by socheatkhauv on 6/27/17.
 */
public class HookFieldRow extends Panel {

    protected UIRow row;

    protected UIColumn rowColumn;
    protected UIContainer rowContainer;
    protected TextField<String> itemField;
    protected Map<String, String> itemValue;
    protected Label itemLabel;
    protected String itemName;

    public HookFieldRow(String id, String itemName, Map<String, String> itemValue) {
        super(id);
        this.itemName = itemName;
        this.itemValue = itemValue;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void onInitHtml() {
        this.row = UIRow.newUIRow("row", this);

        this.rowColumn = this.row.newUIColumn("rowColumn", Size.Twelve_12);
        this.rowContainer = this.rowColumn.newUIContainer("rowContainer");
        this.itemLabel = new Label("itemLabel", this.itemName);
        this.rowContainer.add(this.itemLabel);
        this.itemField = new TextField<>("itemField", new PropertyModel<>(this.itemValue, this.itemName));
        this.itemField.setRequired(true);
        this.rowContainer.add(this.itemField);
        this.rowContainer.newFeedback("itemFeedback", this.itemField);

        this.row.newUIColumn("lastColumn");
    }
}
