package com.angkorteam.fintech.popup;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.provider.fixed.LockInPeriodProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class InterestRateChartPopup extends Panel {

    private ModalWindow window;

    private Form<Void> form;
    private AjaxButton okayButton;

    private LockInPeriodProvider periodTypeProvider;
    private Select2SingleChoice<Option> periodTypeField;
    private TextFeedbackPanel periodTypeFeedback;

    private TextField<Integer> periodFromField;
    private TextFeedbackPanel periodFromFeedback;

    private TextField<Integer> periodToField;
    private TextFeedbackPanel periodToFeedback;

    private TextField<Integer> amountRangeFromField;
    private TextFeedbackPanel amountRangeFromFeedback;

    private TextField<Integer> amountRangeToField;
    private TextFeedbackPanel amountRangeToFeedback;

    private TextField<Double> interestField;
    private TextFeedbackPanel interestFeedback;

    private TextField<String> descriptionField;
    private TextFeedbackPanel descriptionFeedback;

    private Object model;

    public InterestRateChartPopup(String id, ModalWindow window, Object model) {
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
        this.okayButton.setOnError(this::okayButtonError);
        this.form.add(this.okayButton);

        this.periodTypeProvider = new LockInPeriodProvider();
        this.periodTypeField = new Select2SingleChoice<>("periodTypeField", 0, new PropertyModel<>(this.model, "itemPeriodTypeValue"), this.periodTypeProvider);
        this.periodTypeField.setLabel(Model.of("Period Type"));
        this.form.add(this.periodTypeField);
        this.periodTypeFeedback = new TextFeedbackPanel("periodTypeFeedback", this.periodTypeField);
        this.form.add(this.periodTypeFeedback);

        this.periodFromField = new TextField<>("periodFromField", new PropertyModel<>(this.model, "itemPeriodFromValue"));
        this.periodFromField.setLabel(Model.of("Period From"));
        this.form.add(this.periodFromField);
        this.periodFromFeedback = new TextFeedbackPanel("periodFromFeedback", this.periodFromField);
        this.form.add(this.periodFromFeedback);

        this.periodToField = new TextField<>("periodToField", new PropertyModel<>(this.model, "itemPeriodToValue"));
        this.periodToField.setLabel(Model.of("Period To"));
        this.form.add(this.periodToField);
        this.periodToFeedback = new TextFeedbackPanel("periodToFeedback", this.periodToField);
        this.form.add(this.periodToFeedback);

        this.amountRangeFromField = new TextField<>("amountRangeFromField", new PropertyModel<>(this.model, "itemAmountRangeFromValue"));
        this.amountRangeFromField.setLabel(Model.of("Amount Range From"));
        this.form.add(this.amountRangeFromField);
        this.amountRangeFromFeedback = new TextFeedbackPanel("amountRangeFromFeedback", this.amountRangeFromField);
        this.form.add(this.amountRangeFromFeedback);

        this.amountRangeToField = new TextField<>("amountRangeToField", new PropertyModel<>(this.model, "itemAmountRangeToValue"));
        this.amountRangeToField.setLabel(Model.of("Amount Range To"));
        this.form.add(this.amountRangeToField);
        this.amountRangeToFeedback = new TextFeedbackPanel("amountRangeToFeedback", this.amountRangeToField);
        this.form.add(this.amountRangeToFeedback);

        this.interestField = new TextField<>("interestField", new PropertyModel<>(this.model, "itemInterestValue"));
        this.interestField.setLabel(Model.of("Interest"));
        this.form.add(this.interestField);
        this.interestFeedback = new TextFeedbackPanel("interestFeedback", this.interestField);
        this.form.add(this.interestFeedback);

        this.descriptionField = new TextField<>("descriptionField", new PropertyModel<>(this.model, "itemDescriptionValue"));
        this.descriptionField.setLabel(Model.of("Description"));
        this.form.add(this.descriptionField);
        this.descriptionFeedback = new TextFeedbackPanel("descriptionFeedback", this.descriptionField);
        this.form.add(this.descriptionFeedback);

    }

    protected boolean okayButtonSubmit(AjaxButton ajaxButton, AjaxRequestTarget target) {
        this.window.setElementId(ajaxButton.getId());
        this.window.close(target);
        return true;
    }

    protected boolean okayButtonError(AjaxButton ajaxButton, AjaxRequestTarget target) {
        target.add(this.form);
        return true;
    }

}
