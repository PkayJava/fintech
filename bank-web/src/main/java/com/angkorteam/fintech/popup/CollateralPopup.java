//package com.angkorteam.fintech.popup;
//
//import java.util.Map;
//
//import org.apache.wicket.ajax.AjaxRequestTarget;
//import org.apache.wicket.markup.html.form.TextField;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.provider.LoanCollateralProvider;
//import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
//
//public class CollateralPopup extends PopupPanel {
//
//    protected Form<Void> form;
//    protected AjaxButton okayButton;
//
//    protected UIRow row1;
//
//    protected UIBlock collateralBlock;
//    protected UIContainer collateralContainer;
//    protected LoanCollateralProvider collateralProvider;
//    protected Select2SingleChoice<Option> collateralField;
//    protected PropertyModel<Option> collateralValue;
//
//    protected UIBlock row1Block1;
//
//    protected UIRow row2;
//
//    protected UIBlock amountBlock;
//    protected UIContainer amountContainer;
//    protected TextField<Double> amountField;
//    protected PropertyModel<Double> amountValue;
//
//    protected UIBlock row2Block1;
//
//    protected UIRow row3;
//
//    protected UIBlock descriptionBlock;
//    protected UIContainer descriptionContainer;
//    protected TextField<String> descriptionField;
//    protected PropertyModel<String> descriptionValue;
//
//    protected UIBlock row3Block1;
//
//    public CollateralPopup(String name, Map<String, Object> model) {
//        super(name, model);
//    }
//
//    @Override
//    protected void initData() {
//        this.collateralValue = new PropertyModel<>(this.model, "collateralValue");
//        this.amountValue = new PropertyModel<>(this.model, "amountValue");
//        this.descriptionValue = new PropertyModel<>(this.model, "descriptionValue");
//
//        this.collateralProvider = new LoanCollateralProvider();
//    }
//
//    @Override
//    protected void initComponent() {
//        this.form = new Form<>("form");
//        add(this.form);
//
//        this.okayButton = new AjaxButton("okayButton");
//        this.okayButton.setOnSubmit(this::okayButtonSubmit);
//        this.form.add(this.okayButton);
//
//        this.row1 = UIRow.newUIRow("row1", this.form);
//
//        this.collateralBlock = this.row1.newUIBlock("collateralBlock", Size.Six_6);
//        this.collateralContainer = this.collateralBlock.newUIContainer("collateralContainer");
//        this.collateralField = new Select2SingleChoice<>("collateralField", this.collateralValue, this.collateralProvider);
//        this.collateralContainer.add(this.collateralField);
//        this.collateralContainer.newFeedback("collateralFeedback", this.collateralField);
//
//        this.row1Block1 = this.row1.newUIBlock("row1Block1", Size.Six_6);
//
//        this.row2 = UIRow.newUIRow("row2", this.form);
//
//        this.amountBlock = this.row2.newUIBlock("amountBlock", Size.Six_6);
//        this.amountContainer = this.amountBlock.newUIContainer("amountContainer");
//        this.amountField = new TextField<>("amountField", this.amountValue);
//        this.amountContainer.add(this.amountField);
//        this.amountContainer.newFeedback("amountFeedback", this.amountField);
//
//        this.row2Block1 = this.row2.newUIBlock("row2Block1", Size.Six_6);
//
//        this.row3 = UIRow.newUIRow("row3", this.form);
//
//        this.descriptionBlock = this.row3.newUIBlock("descriptionBlock", Size.Six_6);
//        this.descriptionContainer = this.descriptionBlock.newUIContainer("descriptionContainer");
//        this.descriptionField = new TextField<>("descriptionField", this.descriptionValue);
//        this.descriptionContainer.add(this.descriptionField);
//        this.descriptionContainer.newFeedback("descriptionFeedback", this.descriptionField);
//
//        this.row3Block1 = this.row3.newUIBlock("row3Block1", Size.Six_6);
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.descriptionField.setLabel(Model.of("Description"));
//
//        this.amountField.setLabel(Model.of("Value"));
//        this.amountField.setType(Double.class);
//
//        this.collateralField.setRequired(true);
//        this.collateralField.setLabel(Model.of("Collateral"));
//    }
//
//    protected boolean okayButtonSubmit(AjaxButton ajaxButton, AjaxRequestTarget target) {
//        this.window.setSignalId(ajaxButton.getId());
//        this.window.close(target);
//        return true;
//    }
//
//}
