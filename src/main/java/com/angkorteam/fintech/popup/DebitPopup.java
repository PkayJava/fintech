package com.angkorteam.fintech.popup;

import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.ddl.AccGLAccount;
import com.angkorteam.fintech.dto.enums.AccountUsage;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class DebitPopup extends PopupPanel {

    protected Form<Void> form;
    protected AjaxButton okayButton;

    protected UIRow row1;

    protected SingleChoiceProvider debitAccountNameProvider;
    protected UIBlock debitAccountNameBlock;
    protected UIContainer debitAccountNameIContainer;
    protected Select2SingleChoice<Option> debitAccountNameField;

    protected UIBlock debitAmountBlock;
    protected UIContainer debitAmountIContainer;
    protected TextField<Double> debitAmountField;

    public DebitPopup(String name, Map<String, Object> model) {
        super(name, model);
    }

    @Override
    protected void initData() {
        this.debitAccountNameProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.debitAccountNameProvider.applyWhere("usage", AccGLAccount.Field.ACCOUNT_USAGE + " = '" + AccountUsage.Detail.getLiteral() + "'");
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

        this.debitAccountNameBlock = this.row1.newUIBlock("debitAccountNameBlock", Size.Six_6);
        this.debitAccountNameIContainer = this.debitAccountNameBlock.newUIContainer("debitAccountNameIContainer");
        this.debitAccountNameField = new Select2SingleChoice<>("debitAccountNameField", new PropertyModel<>(this.model, "debitAccountNameValue"), this.debitAccountNameProvider);
        this.debitAccountNameIContainer.add(this.debitAccountNameField);
        this.debitAccountNameIContainer.newFeedback("debitAccountNameFeedback", this.debitAccountNameField);

        this.debitAmountBlock = this.row1.newUIBlock("debitAmountBlock", Size.Six_6);
        this.debitAmountIContainer = this.debitAmountBlock.newUIContainer("debitAmountIContainer");
        this.debitAmountField = new TextField<>("debitAmountField", new PropertyModel<>(this.model, "debitAmountValue"));
        this.debitAmountField.setType(Double.class);
        this.debitAmountIContainer.add(this.debitAmountField);
        this.debitAmountIContainer.newFeedback("debitAmountFeedback", this.debitAmountField);

    }

    @Override
    protected void configureMetaData() {
        this.debitAccountNameField.setRequired(true);
        this.debitAmountField.setRequired(true);
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