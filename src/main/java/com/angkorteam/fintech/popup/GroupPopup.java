package com.angkorteam.fintech.popup;

import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class GroupPopup extends Panel {

    protected ModalWindow window;

    protected Form<Void> form;
    protected AjaxButton okayButton;

    protected WebMarkupBlock groupBlock;
    protected WebMarkupContainer groupIContainer;
    protected SingleChoiceProvider groupProvider;
    protected Select2SingleChoice<Option> groupField;
    protected TextFeedbackPanel groupFeedback;

    protected String officeId;

    protected Map<String, Object> model;

    public GroupPopup(String id, ModalWindow window, Map<String, Object> model, String officeId) {
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

        initGroupBlock();

    }

    protected void initGroupBlock() {
        this.groupBlock = new WebMarkupBlock("groupBlock", Size.Six_6);
        this.form.add(this.groupBlock);
        this.groupIContainer = new WebMarkupContainer("groupIContainer");
        this.groupBlock.add(this.groupIContainer);
        this.groupProvider = new SingleChoiceProvider("m_group", "id", "display_name");
        this.groupProvider.applyWhere("office_id", "office_id = " + this.officeId);
        this.groupProvider.applyWhere("level_id", "level_id = " + 2);
        this.groupField = new Select2SingleChoice<>("groupField", new PropertyModel<>(this.model, "groupValue"), this.groupProvider);
        this.groupField.setLabel(Model.of("Group"));
        this.groupIContainer.add(this.groupField);
        this.groupFeedback = new TextFeedbackPanel("groupFeedback", this.groupField);
        this.groupIContainer.add(this.groupFeedback);
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