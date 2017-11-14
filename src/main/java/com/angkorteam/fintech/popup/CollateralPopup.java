package com.angkorteam.fintech.popup;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.provider.LoanCollateralProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class CollateralPopup extends Panel {

    protected ModalWindow window;
    protected Object model;

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

    public CollateralPopup(String id, ModalWindow window, Object model) {
        super(id);
        this.model = model;
        this.window = window;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.form = new Form<>("form");
        add(this.form);

        this.okayButton = new AjaxButton("okayButton");
        this.okayButton.setOnSubmit(this::okayButtonSubmit);
        this.form.add(this.okayButton);

        this.collateralValue = new PropertyModel<>(this.model, "itemCollateralValue");
        this.collateralProvider = new LoanCollateralProvider();
        this.collateralBlock = new WebMarkupContainer("collateralBlock");
        this.form.add(this.collateralBlock);
        this.collateralContainer = new WebMarkupContainer("collateralContainer");
        this.collateralBlock.add(this.collateralContainer);
        this.collateralField = new Select2SingleChoice<>("collateralField", 0, this.collateralValue, this.collateralProvider);
        this.collateralField.setRequired(true);
        this.collateralField.setLabel(Model.of("Collateral"));
        this.collateralContainer.add(this.collateralField);
        this.collateralFeedback = new TextFeedbackPanel("collateralFeedback", this.collateralField);
        this.collateralContainer.add(this.collateralFeedback);

        this.amountValue = new PropertyModel<>(this.model, "itemAmountValue");
        this.amountBlock = new WebMarkupContainer("amountBlock");
        this.form.add(this.amountBlock);
        this.amountContainer = new WebMarkupContainer("amountContainer");
        this.amountBlock.add(this.amountContainer);
        this.amountField = new TextField<>("amountField", this.amountValue);
        this.amountField.setLabel(Model.of("Value"));
        this.amountContainer.add(this.amountField);
        this.amountFeedback = new TextFeedbackPanel("amountFeedback", this.amountField);
        this.amountContainer.add(this.amountFeedback);

        this.descriptionValue = new PropertyModel<>(this.model, "itemDescriptionValue");
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

    protected boolean okayButtonSubmit(AjaxButton ajaxButton, AjaxRequestTarget target) {
        this.window.setElementId(ajaxButton.getId());
        this.window.close(target);
        return true;
    }

}
