//package com.angkorteam.fintech.popup;
//
//import java.util.Map;
//
//import org.apache.wicket.ajax.AjaxRequestTarget;
//import org.apache.wicket.markup.html.form.CheckBox;
//import org.apache.wicket.markup.html.form.TextField;
//import org.apache.wicket.model.PropertyModel;
//
//import com.angkorteam.fintech.ddl.MCode;
//import com.angkorteam.fintech.dto.enums.ColumnType;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.provider.ColumnTypeOptionProvider;
//import com.angkorteam.fintech.provider.SingleChoiceProvider;
//import com.angkorteam.fintech.widget.TextFeedbackPanel;
//import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
//import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
//
//public class ColumnPopup extends PopupPanel {
//
//    protected Form<Void> form;
//    protected AjaxButton okayButton;
//
//    protected UIRow row1;
//
//    protected UIBlock nameBlock;
//    protected UIContainer nameIContainer;
//    protected PropertyModel<String> nameValue;
//    protected TextField<String> nameField;
//
//    protected UIBlock typeBlock;
//    protected UIContainer typeIContainer;
//    protected ColumnTypeOptionProvider typeProvider;
//    protected PropertyModel<Option> typeValue;
//    protected Select2SingleChoice<Option> typeField;
//
//    protected UIRow row2;
//
//    protected UIBlock lengthBlock;
//    protected UIContainer lengthIContainer;
//    protected PropertyModel<Long> lengthValue;
//    protected TextField<Long> lengthField;
//
//    protected UIBlock codeBlock;
//    protected UIContainer codeIContainer;
//    protected SingleChoiceProvider codeProvider;
//    protected PropertyModel<Option> codeValue;
//    protected Select2SingleChoice<Option> codeField;
//
//    protected UIRow row3;
//
//    protected UIBlock mandatoryBlock;
//    protected UIContainer mandatoryIContainer;
//    protected PropertyModel<Boolean> mandatoryValue;
//    protected CheckBox mandatoryField;
//
//    public ColumnPopup(String name, Map<String, Object> model) {
//        super(name, model);
//    }
//
//    @Override
//    protected void initData() {
//        this.nameValue = new PropertyModel<>(this.model, "nameValue");
//        this.lengthValue = new PropertyModel<>(this.model, "lengthValue");
//        this.mandatoryValue = new PropertyModel<>(this.model, "mandatoryValue");
//        this.typeValue = new PropertyModel<>(this.model, "typeValue");
//        this.codeValue = new PropertyModel<>(this.model, "codeValue");
//
//        this.typeProvider = new ColumnTypeOptionProvider();
//        this.codeProvider = new SingleChoiceProvider(MCode.NAME, MCode.Field.CODE_NAME);
//    }
//
//    @Override
//    protected void initComponent() {
//        this.form = new Form<>("form");
//        add(this.form);
//
//        this.okayButton = new AjaxButton("okayButton");
//        this.okayButton.setOnSubmit(this::okayButtonSubmit);
//        this.okayButton.setOnError(this::okayButtonError);
//        this.form.add(this.okayButton);
//
//        this.row1 = UIRow.newUIRow("row1", this.form);
//
//        this.nameBlock = this.row1.newUIBlock("nameBlock", Size.Six_6);
//        this.nameIContainer = this.nameBlock.newUIContainer("nameIContainer");
//        this.nameField = new TextField<>("nameField", this.nameValue);
//        this.nameIContainer.add(this.nameField);
//        this.nameIContainer.newFeedback("nameFeedback", this.nameField);
//
//        this.typeBlock = this.row1.newUIBlock("typeBlock", Size.Six_6);
//        this.typeIContainer = this.typeBlock.newUIContainer("typeIContainer");
//        this.typeField = new Select2SingleChoice<>("typeField", this.typeValue, this.typeProvider);
//        this.typeIContainer.add(this.typeField);
//        this.typeIContainer.newFeedback("typeFeedback", this.typeField);
//
//        this.row2 = UIRow.newUIRow("row2", this.form);
//
//        this.lengthBlock = this.row2.newUIBlock("lengthBlock", Size.Six_6);
//        this.lengthIContainer = this.lengthBlock.newUIContainer("lengthIContainer");
//        this.lengthField = new TextField<>("lengthField", this.lengthValue);
//        this.lengthIContainer.add(this.lengthField);
//        this.lengthIContainer.newFeedback("lengthFeedback", this.lengthField);
//
//        this.codeBlock = this.row2.newUIBlock("codeBlock", Size.Six_6);
//        this.codeIContainer = this.codeBlock.newUIContainer("codeIContainer");
//        this.codeField = new Select2SingleChoice<>("codeField", this.codeValue, this.codeProvider);
//        this.codeIContainer.add(this.codeField);
//        this.codeIContainer.newFeedback("codeFeedback", this.codeField);
//
//        this.row3 = UIRow.newUIRow("row3", this.form);
//
//        this.mandatoryBlock = this.row3.newUIBlock("mandatoryBlock", Size.Twelve_12);
//        this.mandatoryIContainer = this.mandatoryBlock.newUIContainer("mandatoryIContainer");
//        this.mandatoryField = new CheckBox("mandatoryField", this.mandatoryValue);
//        this.mandatoryIContainer.add(this.mandatoryField);
//        this.mandatoryIContainer.newFeedback("mandatoryFeedback", this.mandatoryField);
//
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.typeField.add(new OnChangeAjaxBehavior(this::typeFieldUpdate));
//        this.typeField.setRequired(true);
//        this.mandatoryField.setRequired(true);
//        this.lengthField.setType(Long.class);
//        this.nameField.setRequired(true);
//        typeFieldUpdate(null);
//    }
//
//    protected boolean typeFieldUpdate(AjaxRequestTarget target) {
//        ColumnType columnType = null;
//        if (this.typeValue.getObject() != null) {
//            columnType = ColumnType.valueOf(this.typeValue.getObject().getId());
//        }
//        boolean codeVisible = false;
//        boolean lengthVisible = false;
//        if (columnType != null) {
//            codeVisible = columnType == ColumnType.DropDown;
//            lengthVisible = columnType == ColumnType.String;
//        }
//
//        this.codeIContainer.setVisible(codeVisible);
//        this.lengthIContainer.setVisible(lengthVisible);
//        if (target != null) {
//            target.add(this.codeBlock);
//            target.add(this.lengthBlock);
//        }
//        return false;
//    }
//
//    protected boolean okayButtonSubmit(AjaxButton ajaxButton, AjaxRequestTarget target) {
//        this.window.setSignalId(ajaxButton.getId());
//        this.window.close(target);
//        return true;
//    }
//
//    protected boolean okayButtonError(AjaxButton ajaxButton, AjaxRequestTarget target) {
//        target.add(this.form);
//        return true;
//    }
//
//}