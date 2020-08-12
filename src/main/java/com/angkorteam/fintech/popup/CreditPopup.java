//package com.angkorteam.fintech.popup;
//
//import java.util.Map;
//
//import org.apache.wicket.ajax.AjaxRequestTarget;
//import org.apache.wicket.markup.html.form.TextField;
//import org.apache.wicket.model.PropertyModel;
//
//import com.angkorteam.fintech.ddl.AccGLAccount;
//import com.angkorteam.fintech.dto.enums.AccountUsage;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.provider.SingleChoiceProvider;
//import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
//
//public class CreditPopup extends PopupPanel {
//
//    protected Form<Void> form;
//    protected AjaxButton okayButton;
//
//    protected UIRow row1;
//
//    protected SingleChoiceProvider creditAccountNameProvider;
//    protected UIBlock creditAccountNameBlock;
//    protected UIContainer creditAccountNameIContainer;
//    protected Select2SingleChoice<Option> creditAccountNameField;
//
//    protected UIBlock creditAmountBlock;
//    protected UIContainer creditAmountIContainer;
//    protected TextField<Double> creditAmountField;
//
//    public CreditPopup(String name, Map<String, Object> model) {
//        super(name, model);
//    }
//
//    @Override
//    protected void initData() {
//        this.creditAccountNameProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
//        this.creditAccountNameProvider.applyWhere("usage", AccGLAccount.Field.ACCOUNT_USAGE + " = '" + AccountUsage.Detail.getLiteral() + "'");
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
//        this.creditAccountNameBlock = this.row1.newUIBlock("creditAccountNameBlock", Size.Six_6);
//        this.creditAccountNameIContainer = this.creditAccountNameBlock.newUIContainer("creditAccountNameIContainer");
//        this.creditAccountNameField = new Select2SingleChoice<>("creditAccountNameField", new PropertyModel<>(this.model, "creditAccountNameValue"), this.creditAccountNameProvider);
//        this.creditAccountNameIContainer.add(this.creditAccountNameField);
//        this.creditAccountNameIContainer.newFeedback("creditAccountNameFeedback", this.creditAccountNameField);
//
//        this.creditAmountBlock = this.row1.newUIBlock("creditAmountBlock", Size.Six_6);
//        this.creditAmountIContainer = this.creditAmountBlock.newUIContainer("creditAmountIContainer");
//        this.creditAmountField = new TextField<>("creditAmountField", new PropertyModel<>(this.model, "creditAmountValue"));
//        this.creditAmountField.setType(Double.class);
//        this.creditAmountIContainer.add(this.creditAmountField);
//        this.creditAmountIContainer.newFeedback("creditAmountFeedback", this.creditAmountField);
//
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.creditAccountNameField.setRequired(true);
//        this.creditAmountField.setRequired(true);
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