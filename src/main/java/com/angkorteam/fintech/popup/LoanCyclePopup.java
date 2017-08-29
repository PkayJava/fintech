package com.angkorteam.fintech.popup;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.provider.WhenProvider;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;

public class LoanCyclePopup extends Panel {

    private ModalWindow window;

    private Form<Void> form;
    private AjaxButton okayButton;

    private WhenProvider whenProvider;
    private Select2SingleChoice<Option> whenField;
    private TextFeedbackPanel whenFeedback;

    private TextField<Integer> loanCycleField;
    private TextFeedbackPanel loanCycleFeedback;

    private TextField<Double> minimumField;
    private TextFeedbackPanel minimumFeedback;

    private TextField<Double> defaultField;
    private TextFeedbackPanel defaultFeedback;

    private TextField<Double> maximumField;
    private TextFeedbackPanel maximumFeedback;

    private Object model;

    public LoanCyclePopup(String id, ModalWindow window, Object model) {
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

        this.whenProvider = new WhenProvider();
        this.whenField = new Select2SingleChoice<>("whenField", 0, new PropertyModel<>(this.model, "itemWhenValue"),
                this.whenProvider);
        this.form.add(this.whenField);
        this.whenFeedback = new TextFeedbackPanel("whenFeedback", this.whenField);
        this.form.add(this.whenFeedback);

        this.loanCycleField = new TextField<>("loanCycleField", new PropertyModel<>(this.model, "itemLoanCycleValue"));
        this.form.add(this.loanCycleField);
        this.loanCycleFeedback = new TextFeedbackPanel("loanCycleFeedback", this.loanCycleField);
        this.form.add(this.loanCycleFeedback);

        this.minimumField = new TextField<>("minimumField", new PropertyModel<>(this.model, "itemMinimumValue"));
        this.form.add(this.minimumField);
        this.minimumFeedback = new TextFeedbackPanel("minimumFeedback", this.minimumField);
        this.form.add(this.minimumFeedback);

        this.defaultField = new TextField<>("defaultField", new PropertyModel<>(this.model, "itemDefaultValue"));
        this.form.add(this.defaultField);
        this.defaultFeedback = new TextFeedbackPanel("defaultFeedback", this.defaultField);
        this.form.add(this.defaultFeedback);

        this.maximumField = new TextField<>("maximumField", new PropertyModel<>(this.model, "itemMaximumValue"));
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