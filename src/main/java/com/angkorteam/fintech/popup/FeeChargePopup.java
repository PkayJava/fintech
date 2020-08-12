//package com.angkorteam.fintech.popup;
//
//import java.util.Map;
//
//import org.apache.wicket.ajax.AjaxRequestTarget;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//
//import com.angkorteam.fintech.ddl.AccGLAccount;
//import com.angkorteam.fintech.ddl.MCharge;
//import com.angkorteam.fintech.dto.enums.AccountType;
//import com.angkorteam.fintech.dto.enums.AccountUsage;
//import com.angkorteam.fintech.dto.enums.ChargeType;
//import com.angkorteam.fintech.dto.enums.ProductPopup;
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
//public class FeeChargePopup extends PopupPanel {
//
//    protected Form<Void> form;
//    protected AjaxButton okayButton;
//
//    protected UIRow row1;
//
//    protected UIBlock chargeBlock;
//    protected UIContainer chargeIContainer;
//    protected PropertyModel<Option> chargeValue;
//    protected SingleChoiceProvider chargeProvider;
//    protected Select2SingleChoice<Option> chargeField;
//
//    protected UIBlock accountBlock;
//    protected UIContainer accountIContainer;
//    protected PropertyModel<Option> accountValue;
//    protected SingleChoiceProvider accountProvider;
//    protected Select2SingleChoice<Option> accountField;
//
//    protected String currencyCode;
//
//    protected ProductPopup productPopup;
//
//    public FeeChargePopup(String name, Map<String, Object> model, ProductPopup productPopup, String currencyCode) {
//        super(name, model);
//        this.currencyCode = currencyCode;
//        this.productPopup = productPopup;
//    }
//
//    @Override
//    protected void initData() {
//        this.chargeValue = new PropertyModel<>(this.model, "chargeValue");
//        this.accountValue = new PropertyModel<>(this.model, "accountValue");
//
//        this.chargeProvider = new SingleChoiceProvider(MCharge.NAME, MCharge.Field.ID, MCharge.Field.NAME);
//        if (this.productPopup == ProductPopup.Saving || this.productPopup == ProductPopup.Recurring || this.productPopup == ProductPopup.Fixed) {
//            this.chargeProvider.applyWhere("charge_applies_to_enum", MCharge.Field.CHARGE_APPLIES_TO_ENUM + " = " + ChargeType.SavingDeposit.getLiteral());
//        } else if (this.productPopup == ProductPopup.Loan) {
//            this.chargeProvider.applyWhere("charge_applies_to_enum", MCharge.Field.CHARGE_APPLIES_TO_ENUM + " = " + ChargeType.Loan.getLiteral());
//        }
//        this.chargeProvider.applyWhere("currency_code", MCharge.Field.CURRENCY_CODE + " = '" + this.currencyCode + "'");
//        this.chargeProvider.applyWhere("is_penalty", MCharge.Field.IS_PENALTY + " = 0");
//        this.chargeProvider.applyWhere("is_active", MCharge.Field.IS_ACTIVE + " = 1");
//
//        this.accountProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
//        this.accountProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
//        this.accountProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " IN (" + AccountType.Income.getLiteral() + ")");
//
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
//        this.chargeBlock = this.row1.newUIBlock("chargeBlock", Size.Six_6);
//        this.chargeIContainer = this.chargeBlock.newUIContainer("chargeIContainer");
//        this.chargeField = new Select2SingleChoice<>("chargeField", this.chargeValue, this.chargeProvider);
//        this.chargeIContainer.add(this.chargeField);
//        this.chargeIContainer.newFeedback("chargeFeedback", this.chargeField);
//
//        this.accountBlock = this.row1.newUIBlock("accountBlock", Size.Six_6);
//        this.accountIContainer = this.accountBlock.newUIContainer("accountIContainer");
//        this.accountField = new Select2SingleChoice<>("accountField", this.accountValue, this.accountProvider);
//        this.accountIContainer.add(this.accountField);
//        this.accountIContainer.newFeedback("accountFeedback", this.accountField);
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.chargeField.setLabel(Model.of("Charge"));
//        this.accountField.setLabel(Model.of("Account"));
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