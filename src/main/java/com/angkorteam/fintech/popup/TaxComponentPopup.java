package com.angkorteam.fintech.popup;

import java.util.Date;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;

public class TaxComponentPopup extends PopupPanel {

    protected Form<Void> form;
    protected AjaxButton saveButton;

    protected UIRow row1;

    protected UIBlock startDateBlock;
    protected UIContainer startDateVContainer;
    protected PropertyModel<String> startDateValue;
    protected ReadOnlyView startDateView;

    protected UIRow row2;

    protected UIBlock endDateBlock;
    protected UIContainer endDateIContainer;
    protected DateTextField endDateField;
    protected PropertyModel<Date> endDateValue;

    public TaxComponentPopup(String name, Map<String, Object> model) {
        super(name, model);
    }

    @Override
    protected void initData() {
        this.startDateValue = new PropertyModel<>(this.model, "startDateValue");
        this.endDateValue = new PropertyModel<>(this.model, "endDateValue");
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new AjaxButton("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.startDateBlock = this.row1.newUIBlock("startDateBlock", Size.Twelve_12);
        this.startDateVContainer = this.startDateBlock.newUIContainer("startDateVContainer");
        this.startDateView = new ReadOnlyView("startDateView", this.startDateValue);
        this.startDateVContainer.add(this.startDateView);

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.endDateBlock = this.row2.newUIBlock("endDateBlock", Size.Twelve_12);
        this.endDateIContainer = this.endDateBlock.newUIContainer("endDateIContainer");
        this.endDateField = new DateTextField("endDateField", this.endDateValue);
        this.endDateIContainer.add(this.endDateField);
        this.endDateIContainer.newFeedback("endDateFeedback", this.endDateField);
    }

    @Override
    protected void configureMetaData() {
        this.endDateField.setLabel(Model.of("End Date"));
        this.endDateField.setRequired(true);
    }

    protected boolean saveButtonSubmit(AjaxButton ajaxButton, AjaxRequestTarget target) {
        this.window.setSignalId(ajaxButton.getId());
        this.window.close(target);
        return true;
    }
}
