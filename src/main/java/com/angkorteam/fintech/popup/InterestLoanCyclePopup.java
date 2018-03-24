package com.angkorteam.fintech.popup;

import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.provider.WhenProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class InterestLoanCyclePopup extends PopupPanel {

    protected Form<Void> form;
    protected AjaxButton okayButton;

    protected PropertyModel<Option> whenValue;
    protected WhenProvider whenProvider;
    protected Select2SingleChoice<Option> whenField;
    protected TextFeedbackPanel whenFeedback;

    protected PropertyModel<Long> loanCycleValue;
    protected TextField<Long> loanCycleField;
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

    public InterestLoanCyclePopup(String name, Map<String, Object> model) {
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
        this.okayButton.setOnError(this::okayButtonError);
        this.form.add(this.okayButton);

        this.whenValue = new PropertyModel<>(this.model, "whenValue");
        this.whenProvider = new WhenProvider();
        this.whenField = new Select2SingleChoice<>("whenField", this.whenValue, this.whenProvider);
        this.whenField.setLabel(Model.of("When"));
        this.form.add(this.whenField);
        this.whenFeedback = new TextFeedbackPanel("whenFeedback", this.whenField);
        this.form.add(this.whenFeedback);

        this.loanCycleValue = new PropertyModel<>(this.model, "loanCycleValue");
        this.loanCycleField = new TextField<>("loanCycleField", this.loanCycleValue);
        this.loanCycleField.setLabel(Model.of("Loan Cycle"));
        this.loanCycleField.setType(Long.class);
        this.form.add(this.loanCycleField);
        this.loanCycleFeedback = new TextFeedbackPanel("loanCycleFeedback", this.loanCycleField);
        this.form.add(this.loanCycleFeedback);

        this.minimumValue = new PropertyModel<>(this.model, "minimumValue");
        this.minimumField = new TextField<>("minimumField", this.minimumValue);
        this.minimumField.setType(Double.class);
        this.minimumField.setLabel(Model.of("Minimum"));
        this.form.add(this.minimumField);
        this.minimumFeedback = new TextFeedbackPanel("minimumFeedback", this.minimumField);
        this.form.add(this.minimumFeedback);

        this.defaultValue = new PropertyModel<>(this.model, "defaultValue");
        this.defaultField = new TextField<>("defaultField", this.defaultValue);
        this.defaultField.setLabel(Model.of("Default"));
        this.defaultField.setType(Double.class);
        this.form.add(this.defaultField);
        this.defaultFeedback = new TextFeedbackPanel("defaultFeedback", this.defaultField);
        this.form.add(this.defaultFeedback);

        this.maximumValue = new PropertyModel<>(this.model, "maximumValue");
        this.maximumField = new TextField<>("maximumField", this.maximumValue);
        this.maximumField.setType(Double.class);
        this.maximumField.setLabel(Model.of("Maximum"));
        this.form.add(this.maximumField);
        this.maximumFeedback = new TextFeedbackPanel("maximumFeedback", this.maximumField);
        this.form.add(this.maximumFeedback);
    }

    @Override
    protected void configureMetaData() {
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