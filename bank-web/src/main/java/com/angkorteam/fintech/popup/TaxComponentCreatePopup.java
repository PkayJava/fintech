//package com.angkorteam.fintech.popup;
//
//import java.util.Date;
//import java.util.Map;
//
//import org.apache.wicket.ajax.AjaxRequestTarget;
//import org.apache.wicket.model.PropertyModel;
//
//import com.angkorteam.fintech.ddl.MTaxComponent;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.provider.SingleChoiceProvider;
//import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
//import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
//
//public class TaxComponentCreatePopup extends PopupPanel {
//
//    protected Form<Void> form;
//    protected AjaxButton saveButton;
//
//    protected UIRow row1;
//
//    protected UIBlock taxBlock;
//    protected UIContainer taxIContainer;
//    protected SingleChoiceProvider taxProvider;
//    protected PropertyModel<Option> taxValue;
//    protected Select2SingleChoice<Option> taxField;
//
//    protected UIBlock startDateBlock;
//    protected UIContainer startDateIContainer;
//    protected PropertyModel<Date> startDateValue;
//    protected DateTextField startDateField;
//
//    public TaxComponentCreatePopup(String name, Map<String, Object> model) {
//        super(name, model);
//    }
//
//    @Override
//    protected void initData() {
//        this.taxValue = new PropertyModel<>(this.model, "taxValue");
//        this.startDateValue = new PropertyModel<>(this.model, "startDateValue");
//        this.taxProvider = new SingleChoiceProvider(MTaxComponent.NAME, MTaxComponent.Field.ID, MTaxComponent.Field.NAME);
//    }
//
//    @Override
//    protected void initComponent() {
//        this.form = new Form<>("form");
//        add(this.form);
//
//        this.saveButton = new AjaxButton("saveButton");
//        this.saveButton.setOnSubmit(this::saveButtonSubmit);
//        this.form.add(this.saveButton);
//
//        this.row1 = UIRow.newUIRow("row1", this.form);
//
//        this.taxBlock = this.row1.newUIBlock("taxBlock", Size.Six_6);
//        this.taxIContainer = this.taxBlock.newUIContainer("taxIContainer");
//        this.taxField = new Select2SingleChoice<>("taxField", this.taxValue, this.taxProvider);
//        this.taxIContainer.add(this.taxField);
//        this.taxIContainer.newFeedback("taxFeedback", this.taxField);
//
//        this.startDateBlock = this.row1.newUIBlock("startDateBlock", Size.Six_6);
//        this.startDateIContainer = this.startDateBlock.newUIContainer("startDateIContainer");
//        this.startDateField = new DateTextField("startDateField", this.startDateValue);
//        this.startDateIContainer.add(this.startDateField);
//        this.startDateIContainer.newFeedback("startDateFeedback", this.startDateField);
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.startDateField.setRequired(true);
//    }
//
//    protected boolean saveButtonSubmit(AjaxButton ajaxButton, AjaxRequestTarget target) {
//        this.window.setSignalId(ajaxButton.getId());
//        this.window.close(target);
//        return true;
//    }
//}
