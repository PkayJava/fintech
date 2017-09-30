package com.angkorteam.fintech.popup;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class GroupPopup extends Panel {

    private ModalWindow window;

    private Form<Void> form;
    private AjaxButton okayButton;

    private SingleChoiceProvider groupProvider;
    private Select2SingleChoice<Option> groupField;
    private TextFeedbackPanel groupFeedback;

    private String officeId;

    private Object model;

    public GroupPopup(String id, ModalWindow window, Object model, String officeId) {
        super(id);
        this.model = model;
        this.window = window;
        this.officeId = officeId;
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

        this.groupProvider = new SingleChoiceProvider("m_group", "id", "display_name");
        this.groupProvider.applyWhere("office_id", "office_id = " + this.officeId);
        this.groupField = new Select2SingleChoice<>("groupField", 0, new PropertyModel<>(this.model, "itemGroupValue"), this.groupProvider);
        this.groupField.setLabel(Model.of("Group"));
        this.form.add(this.groupField);
        this.groupFeedback = new TextFeedbackPanel("groupFeedback", this.groupField);
        this.form.add(this.groupFeedback);

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