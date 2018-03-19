package com.angkorteam.fintech.popup;

import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.ddl.MCode;
import com.angkorteam.fintech.dto.enums.ColumnType;
import com.angkorteam.fintech.provider.ColumnTypeOptionProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class ColumnPopup extends PopupPanel {

    protected ModalWindow window;

    protected Form<Void> form;
    protected AjaxButton okayButton;

    protected WebMarkupBlock nameBlock;
    protected WebMarkupContainer nameIContainer;
    protected PropertyModel<String> nameValue;
    protected TextField<String> nameField;
    protected TextFeedbackPanel nameFeedback;

    protected WebMarkupBlock lengthBlock;
    protected WebMarkupContainer lengthIContainer;
    protected PropertyModel<Long> lengthValue;
    protected TextField<Long> lengthField;
    protected TextFeedbackPanel lengthFeedback;

    protected WebMarkupBlock mandatoryBlock;
    protected WebMarkupContainer mandatoryIContainer;
    protected PropertyModel<Boolean> mandatoryValue;
    protected CheckBox mandatoryField;
    protected TextFeedbackPanel mandatoryFeedback;

    protected WebMarkupBlock typeBlock;
    protected WebMarkupContainer typeIContainer;
    protected ColumnTypeOptionProvider typeProvider;
    protected PropertyModel<Option> typeValue;
    protected Select2SingleChoice<Option> typeField;
    protected TextFeedbackPanel typeFeedback;

    protected WebMarkupContainer codeBlock;
    protected WebMarkupContainer codeIContainer;
    protected SingleChoiceProvider codeProvider;
    protected PropertyModel<Option> codeValue;
    protected Select2SingleChoice<Option> codeField;
    protected TextFeedbackPanel codeFeedback;

    protected Map<String, Object> model;

    public ColumnPopup(String name, ModalWindow window, Map<String, Object> model) {
        super(name, window);
        this.model = model;
        this.window = window;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.okayButton = new AjaxButton("okayButton");
        this.okayButton.setOnSubmit(this::okayButtonSubmit);
        this.okayButton.setOnError(this::okayButtonError);
        this.form.add(this.okayButton);

        this.nameBlock = new WebMarkupBlock("nameBlock", Size.Six_6);
        this.form.add(this.nameBlock);
        this.nameIContainer = new WebMarkupContainer("nameIContainer");
        this.nameBlock.add(this.nameIContainer);
        this.nameValue = new PropertyModel<>(this.model, "nameValue");
        this.nameField = new TextField<>("nameField", this.nameValue);
        this.nameField.setRequired(true);
        this.nameIContainer.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.nameIContainer.add(this.nameFeedback);

        this.lengthBlock = new WebMarkupBlock("lengthBlock", Size.Six_6);
        this.form.add(this.lengthBlock);
        this.lengthIContainer = new WebMarkupContainer("lengthIContainer");
        this.lengthBlock.add(this.lengthIContainer);
        this.lengthValue = new PropertyModel<>(this.model, "lengthValue");
        this.lengthField = new TextField<>("lengthField", this.lengthValue);
        this.lengthField.setType(Long.class);
        this.lengthIContainer.add(this.lengthField);
        this.lengthFeedback = new TextFeedbackPanel("lengthFeedback", this.lengthField);
        this.lengthIContainer.add(this.lengthFeedback);

        this.mandatoryBlock = new WebMarkupBlock("mandatoryBlock", Size.Twelve_12);
        this.form.add(this.mandatoryBlock);
        this.mandatoryIContainer = new WebMarkupContainer("mandatoryIContainer");
        this.mandatoryBlock.add(this.mandatoryIContainer);
        this.mandatoryValue = new PropertyModel<>(this.model, "mandatoryValue");
        this.mandatoryField = new CheckBox("mandatoryField", this.mandatoryValue);
        this.mandatoryField.setRequired(true);
        this.mandatoryIContainer.add(this.mandatoryField);
        this.mandatoryFeedback = new TextFeedbackPanel("mandatoryFeedback", this.mandatoryField);
        this.mandatoryIContainer.add(this.mandatoryFeedback);

        this.typeBlock = new WebMarkupBlock("typeBlock", Size.Six_6);
        this.form.add(this.typeBlock);
        this.typeIContainer = new WebMarkupContainer("typeIContainer");
        this.typeBlock.add(this.typeIContainer);
        this.typeProvider = new ColumnTypeOptionProvider();
        this.typeValue = new PropertyModel<>(this.model, "typeValue");
        this.typeField = new Select2SingleChoice<>("typeField", this.typeValue, this.typeProvider);
        this.typeField.add(new OnChangeAjaxBehavior(this::typeFieldUpdate));
        this.typeField.setRequired(true);
        this.typeIContainer.add(this.typeField);
        this.typeFeedback = new TextFeedbackPanel("typeFeedback", this.typeField);
        this.typeIContainer.add(this.typeFeedback);

        this.codeBlock = new WebMarkupBlock("codeBlock", Size.Six_6);
        this.form.add(this.codeBlock);
        this.codeIContainer = new WebMarkupContainer("codeIContainer");
        this.codeBlock.add(this.codeIContainer);
        this.codeProvider = new SingleChoiceProvider(MCode.NAME, MCode.Field.CODE_NAME);
        this.codeValue = new PropertyModel<>(this.model, "codeValue");
        this.codeField = new Select2SingleChoice<>("codeField", this.codeValue, this.codeProvider);
        this.codeIContainer.add(this.codeField);
        this.codeFeedback = new TextFeedbackPanel("codeFeedback", this.codeField);
        this.codeIContainer.add(this.codeFeedback);

    }

    protected boolean typeFieldUpdate(AjaxRequestTarget target) {
        ColumnType columnType = null;
        if (this.typeValue.getObject() != null) {
            columnType = ColumnType.valueOf(this.typeValue.getObject().getId());
        }
        boolean codeVisible = false;
        boolean lengthVisible = false;
        if (columnType != null) {
            codeVisible = columnType == ColumnType.DropDown;
            lengthVisible = columnType == ColumnType.String;
        }

        this.codeIContainer.setVisible(codeVisible);
        this.lengthIContainer.setVisible(lengthVisible);
        if (target != null) {
            target.add(this.codeBlock);
            target.add(this.lengthBlock);
        }
        return false;
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
        typeFieldUpdate(null);
    }

    protected boolean okayButtonSubmit(AjaxButton ajaxButton, AjaxRequestTarget target) {
        this.window.setSignalId(ajaxButton.getId());
        this.window.close(target);
        return true;
    }

    protected boolean okayButtonError(AjaxButton ajaxButton, AjaxRequestTarget target) {
        target.add(this.form);
        return true;
    }

}