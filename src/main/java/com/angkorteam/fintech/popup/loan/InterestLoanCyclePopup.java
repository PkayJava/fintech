package com.angkorteam.fintech.popup.loan;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.provider.WhenProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class InterestLoanCyclePopup extends Panel {

    protected ModalWindow window;

    protected Form<Void> form;
    protected AjaxButton okayButton;

    protected PropertyModel<Option> whenValue;
    protected WhenProvider whenProvider;
    protected Select2SingleChoice<Option> whenField;
    protected TextFeedbackPanel whenFeedback;

    protected PropertyModel<Integer> loanCycleValue;
    protected TextField<Integer> loanCycleField;
    protected TextFeedbackPanel loanCycleFeedback;

    protected PropertyModel<Double> minimumValue;
    protected TextField<Double> minimumField;
    protected TextFeedbackPanel minimumFeedback;

    protected PropertyModel<Double> defaultValue;
    protected TextField<Double> defaultField;
    protected TextFeedbackPanel defaultFeedback;

    protected PropertyModel<Double> maximumValue;
    protected TextField<Double> maximumField;
    protected TextFeedbackPanel maximumFeedback;

    protected Object model;

    public InterestLoanCyclePopup(String id, ModalWindow window, Object model) {
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

        this.whenValue = new PropertyModel<>(this.model, "itemWhenValue");
        this.whenProvider = new WhenProvider();
        this.whenField = new Select2SingleChoice<>("whenField", 0, this.whenValue, this.whenProvider);
        this.whenField.setLabel(Model.of("When"));
        this.form.add(this.whenField);
        this.whenFeedback = new TextFeedbackPanel("whenFeedback", this.whenField);
        this.form.add(this.whenFeedback);

        this.loanCycleValue = new PropertyModel<>(this.model, "itemLoanCycleValue");
        this.loanCycleField = new TextField<>("loanCycleField", this.loanCycleValue);
        this.loanCycleField.setLabel(Model.of("Loan Cycle"));
        this.form.add(this.loanCycleField);
        this.loanCycleFeedback = new TextFeedbackPanel("loanCycleFeedback", this.loanCycleField);
        this.form.add(this.loanCycleFeedback);

        this.minimumValue = new PropertyModel<>(this.model, "itemMinimumValue");
        this.minimumField = new TextField<>("minimumField", this.minimumValue);
        this.minimumField.setLabel(Model.of("Minimum"));
        this.form.add(this.minimumField);
        this.minimumFeedback = new TextFeedbackPanel("minimumFeedback", this.minimumField);
        this.form.add(this.minimumFeedback);

        this.defaultValue = new PropertyModel<>(this.model, "itemDefaultValue");
        this.defaultField = new TextField<>("defaultField", this.defaultValue);
        this.defaultField.setLabel(Model.of("Default"));
        this.form.add(this.defaultField);
        this.defaultFeedback = new TextFeedbackPanel("defaultFeedback", this.defaultField);
        this.form.add(this.defaultFeedback);

        this.maximumValue = new PropertyModel<>(this.model, "itemMaximumValue");
        this.maximumField = new TextField<>("maximumField", this.maximumValue);
        this.maximumField.setLabel(Model.of("Maximum"));
        this.form.add(this.maximumField);
        this.maximumFeedback = new TextFeedbackPanel("maximumFeedback", this.maximumField);
        this.form.add(this.maximumFeedback);
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