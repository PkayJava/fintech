package com.angkorteam.fintech.popup;

import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.provider.LoanCollateralProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class CollateralPopup extends PopupPanel {

    protected Form<Void> form;
    protected AjaxButton okayButton;

    protected WebMarkupContainer collateralBlock;
    protected WebMarkupContainer collateralContainer;
    protected LoanCollateralProvider collateralProvider;
    protected Select2SingleChoice<Option> collateralField;
    protected TextFeedbackPanel collateralFeedback;
    protected PropertyModel<Option> collateralValue;

    protected WebMarkupContainer amountBlock;
    protected WebMarkupContainer amountContainer;
    protected TextField<Double> amountField;
    protected TextFeedbackPanel amountFeedback;
    protected PropertyModel<Double> amountValue;

    protected WebMarkupContainer descriptionBlock;
    protected WebMarkupContainer descriptionContainer;
    protected TextField<String> descriptionField;
    protected TextFeedbackPanel descriptionFeedback;
    protected PropertyModel<String> descriptionValue;

    public CollateralPopup(String name, Map<String, Object> model) {
        super(name, model);
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
        this.form.add(this.okayButton);

        this.collateralValue = new PropertyModel<>(this.model, "collateralValue");
        this.collateralProvider = new LoanCollateralProvider();
        this.collateralBlock = new WebMarkupContainer("collateralBlock");
        this.form.add(this.collateralBlock);
        this.collateralContainer = new WebMarkupContainer("collateralContainer");
        this.collateralBlock.add(this.collateralContainer);
        this.collateralField = new Select2SingleChoice<>("collateralField", this.collateralValue, this.collateralProvider);
        this.collateralField.setRequired(true);
        this.collateralField.setLabel(Model.of("Collateral"));
        this.collateralContainer.add(this.collateralField);
        this.collateralFeedback = new TextFeedbackPanel("collateralFeedback", this.collateralField);
        this.collateralContainer.add(this.collateralFeedback);

        this.amountValue = new PropertyModel<>(this.model, "amountValue");
        this.amountBlock = new WebMarkupContainer("amountBlock");
        this.form.add(this.amountBlock);
        this.amountContainer = new WebMarkupContainer("amountContainer");
        this.amountBlock.add(this.amountContainer);
        this.amountField = new TextField<>("amountField", this.amountValue);
        this.amountField.setLabel(Model.of("Value"));
        this.amountField.setType(Double.class);
        this.amountContainer.add(this.amountField);
        this.amountFeedback = new TextFeedbackPanel("amountFeedback", this.amountField);
        this.amountContainer.add(this.amountFeedback);

        this.descriptionValue = new PropertyModel<>(this.model, "descriptionValue");
        this.descriptionBlock = new WebMarkupContainer("descriptionBlock");
        this.form.add(this.descriptionBlock);
        this.descriptionContainer = new WebMarkupContainer("descriptionContainer");
        this.descriptionBlock.add(this.descriptionContainer);
        this.descriptionField = new TextField<>("descriptionField", this.descriptionValue);
        this.descriptionField.setLabel(Model.of("Description"));
        this.descriptionContainer.add(this.descriptionField);
        this.descriptionFeedback = new TextFeedbackPanel("descriptionFeedback", this.descriptionField);
        this.descriptionContainer.add(this.descriptionFeedback);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected boolean okayButtonSubmit(AjaxButton ajaxButton, AjaxRequestTarget target) {
        this.window.setSignalId(ajaxButton.getId());
        this.window.close(target);
        return true;
    }

}
