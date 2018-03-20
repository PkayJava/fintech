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

public class RepaymentLoanCyclePopup extends PopupPanel {

    protected Form<Void> form;
    protected AjaxButton okayButton;

    protected WhenProvider whenProvider;
    protected Select2SingleChoice<Option> whenField;
    protected TextFeedbackPanel whenFeedback;

    protected TextField<Long> loanCycleField;
    protected TextFeedbackPanel loanCycleFeedback;

    protected TextField<Double> minimumField;
    protected TextFeedbackPanel minimumFeedback;

    protected TextField<Double> defaultField;
    protected TextFeedbackPanel defaultFeedback;

    protected TextField<Double> maximumField;
    protected TextFeedbackPanel maximumFeedback;

    public RepaymentLoanCyclePopup(String name, Map<String, Object> model) {
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

        this.whenProvider = new WhenProvider();
        this.whenField = new Select2SingleChoice<>("whenField", new PropertyModel<>(this.model, "whenValue"), this.whenProvider);
        this.whenField.setLabel(Model.of("When"));
        this.form.add(this.whenField);
        this.whenFeedback = new TextFeedbackPanel("whenFeedback", this.whenField);
        this.form.add(this.whenFeedback);

        this.loanCycleField = new TextField<>("loanCycleField", new PropertyModel<>(this.model, "loanCycleValue"));
        this.loanCycleField.setType(Long.class);
        this.loanCycleField.setLabel(Model.of("Loan Cycle"));
        this.form.add(this.loanCycleField);
        this.loanCycleFeedback = new TextFeedbackPanel("loanCycleFeedback", this.loanCycleField);
        this.form.add(this.loanCycleFeedback);

        this.minimumField = new TextField<>("minimumField", new PropertyModel<>(this.model, "minimumValue"));
        this.minimumField.setType(Double.class);
        this.minimumField.setLabel(Model.of("Minimum"));
        this.form.add(this.minimumField);
        this.minimumFeedback = new TextFeedbackPanel("minimumFeedback", this.minimumField);
        this.form.add(this.minimumFeedback);

        this.defaultField = new TextField<>("defaultField", new PropertyModel<>(this.model, "defaultValue"));
        this.defaultField.setType(Double.class);
        this.defaultField.setLabel(Model.of("Default"));
        this.form.add(this.defaultField);
        this.defaultFeedback = new TextFeedbackPanel("defaultFeedback", this.defaultField);
        this.form.add(this.defaultFeedback);

        this.maximumField = new TextField<>("maximumField", new PropertyModel<>(this.model, "maximumValue"));
        this.maximumField.setType(Double.class);
        this.maximumField.setLabel(Model.of("Maximum"));
        this.form.add(this.maximumField);
        this.maximumFeedback = new TextFeedbackPanel("maximumFeedback", this.maximumField);
        this.form.add(this.maximumFeedback);
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