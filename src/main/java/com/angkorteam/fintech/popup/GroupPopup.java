package com.angkorteam.fintech.popup;

import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.ddl.MGroup;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class GroupPopup extends PopupPanel {

    protected Form<Void> form;
    protected AjaxButton okayButton;

    protected UIRow row1;

    protected UIBlock groupBlock;
    protected UIContainer groupIContainer;
    protected SingleChoiceProvider groupProvider;
    protected Select2SingleChoice<Option> groupField;

    protected UIBlock row1Block1;

    protected String officeId;

    public GroupPopup(String name, Map<String, Object> model, String officeId) {
        super(name, model);
        this.officeId = officeId;
    }

    @Override
    protected void initData() {
        this.groupProvider = new SingleChoiceProvider(MGroup.NAME, MGroup.Field.ID, MGroup.Field.DISPLAY_NAME);
        this.groupProvider.applyWhere("office_id", MGroup.Field.OFFICE_ID + " = " + this.officeId);
        this.groupProvider.applyWhere("level_id", MGroup.Field.LEVEL_ID + " = " + 2);
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.okayButton = new AjaxButton("okayButton");
        this.okayButton.setOnSubmit(this::okayButtonSubmit);
        this.okayButton.setOnError(this::okayButtonError);
        this.form.add(this.okayButton);

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.groupBlock = this.row1.newUIBlock("groupBlock", Size.Six_6);
        this.groupIContainer = this.groupBlock.newUIContainer("groupIContainer");
        this.groupField = new Select2SingleChoice<>("groupField", new PropertyModel<>(this.model, "groupValue"), this.groupProvider);
        this.groupIContainer.add(this.groupField);
        this.groupIContainer.newFeedback("groupFeedback", this.groupField);

        this.row1Block1 = this.row1.newUIBlock("row1Block1", Size.Six_6);
    }

    @Override
    protected void configureMetaData() {
        this.groupField.setLabel(Model.of("Group"));
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