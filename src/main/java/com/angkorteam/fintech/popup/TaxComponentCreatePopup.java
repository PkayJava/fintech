package com.angkorteam.fintech.popup;

import java.util.Date;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.ddl.MTaxComponent;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class TaxComponentCreatePopup extends PopupPanel {

    protected Form<Void> form;
    protected AjaxButton saveButton;

    protected WebMarkupBlock taxBlock;
    protected WebMarkupContainer taxIContainer;
    protected SingleChoiceProvider taxProvider;
    protected PropertyModel<Option> taxValue;
    protected Select2SingleChoice<Option> taxField;
    protected TextFeedbackPanel taxFeedback;

    protected WebMarkupBlock startDateBlock;
    protected WebMarkupContainer startDateIContainer;
    protected PropertyModel<Date> startDateValue;
    protected DateTextField startDateField;
    protected TextFeedbackPanel startDateFeedback;

    public TaxComponentCreatePopup(String name, Map<String, Object> model) {
        super(name, model);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new AjaxButton("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.taxBlock = new WebMarkupBlock("taxBlock", Size.Six_6);
        this.form.add(this.taxBlock);
        this.taxIContainer = new WebMarkupContainer("taxIContainer");
        this.taxBlock.add(this.taxIContainer);
        this.taxProvider = new SingleChoiceProvider(MTaxComponent.NAME, MTaxComponent.Field.ID, MTaxComponent.Field.NAME);
        this.taxValue = new PropertyModel<>(this.model, "taxValue");
        this.taxField = new Select2SingleChoice<>("taxField", this.taxValue, this.taxProvider);
        this.taxIContainer.add(this.taxField);
        this.taxFeedback = new TextFeedbackPanel("taxFeedback", this.taxField);
        this.taxIContainer.add(this.taxFeedback);

        this.startDateBlock = new WebMarkupBlock("startDateBlock", Size.Six_6);
        this.form.add(this.startDateBlock);
        this.startDateIContainer = new WebMarkupContainer("startDateIContainer");
        this.startDateBlock.add(this.startDateIContainer);
        this.startDateValue = new PropertyModel<>(this.model, "startDateValue");
        this.startDateField = new DateTextField("startDateField", this.startDateValue);
        this.startDateField.setRequired(true);
        this.startDateIContainer.add(this.startDateField);
        this.startDateFeedback = new TextFeedbackPanel("startDateFeedback", this.startDateField);
        this.startDateIContainer.add(this.startDateFeedback);
    }

    @Override
    protected void configureMetaData() {
    }

    protected boolean saveButtonSubmit(AjaxButton ajaxButton, AjaxRequestTarget target) {
        this.window.setSignalId(ajaxButton.getId());
        this.window.close(target);
        return true;
    }
}
