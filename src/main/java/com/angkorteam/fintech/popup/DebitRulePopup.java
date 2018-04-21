package com.angkorteam.fintech.popup;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.ddl.AccAccountingRule;
import com.angkorteam.fintech.ddl.AccGLAccount;
import com.angkorteam.fintech.ddl.AccRuleTags;
import com.angkorteam.fintech.dto.enums.AccountUsage;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.account.RuleBrowsePage;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

public class DebitRulePopup extends PopupPanel {

    protected String ruleId;

    protected Map<String, Object> ruleObject;

    protected Form<Void> form;
    protected AjaxButton okayButton;

    protected UIRow row1;

    protected SingleChoiceProvider debitAccountNameProvider;
    protected UIBlock debitAccountNameBlock;
    protected UIContainer debitAccountNameIContainer;
    protected Select2SingleChoice<Option> debitAccountNameField;
    protected UIContainer debitAccountNameVContainer;
    protected ReadOnlyView debitAccountNameView;

    protected UIBlock debitAmountBlock;
    protected UIContainer debitAmountIContainer;
    protected TextField<Double> debitAmountField;

    public DebitRulePopup(String name, Map<String, Object> model, String ruleId) {
        super(name, model);
        this.ruleId = ruleId;
    }

    @Override
    protected void initData() {
        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        this.debitAccountNameProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.debitAccountNameProvider.applyWhere("usage", AccGLAccount.Field.ACCOUNT_USAGE + " = '" + AccountUsage.Detail.getLiteral() + "'");

        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery(AccAccountingRule.NAME);
        selectQuery.addWhere(AccAccountingRule.Field.ID + " = :" + AccAccountingRule.Field.ID, this.ruleId);
        selectQuery.addField(AccAccountingRule.Field.DEBIT_ACCOUNT_ID);
        selectQuery.addField(AccAccountingRule.Field.CREDIT_ACCOUNT_ID);
        selectQuery.addField(AccAccountingRule.Field.OFFICE_ID);
        this.ruleObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        if (this.ruleObject.get(AccAccountingRule.Field.DEBIT_ACCOUNT_ID) != null) {
            selectQuery = new SelectQuery(AccGLAccount.NAME);
            selectQuery.addField(AccGLAccount.Field.ID);
            selectQuery.addField(AccGLAccount.Field.NAME + " as text");
            selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, ruleObject.get(AccAccountingRule.Field.DEBIT_ACCOUNT_ID));
            this.model.put("debitAccountNameValue", named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER));
        } else {
            selectQuery = new SelectQuery(AccRuleTags.NAME);
            selectQuery.addField(AccRuleTags.Field.TAG_ID);
            selectQuery.addWhere(AccRuleTags.Field.ACC_TYPE_ENUM + " = :" + AccRuleTags.Field.ACC_TYPE_ENUM, RuleBrowsePage.DEBIT);
            selectQuery.addWhere(AccRuleTags.Field.ACC_RULE_ID + " = :" + AccRuleTags.Field.ACC_RULE_ID, this.ruleId);
            List<String> tags = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), String.class);
            if (tags != null && !tags.isEmpty()) {
                this.debitAccountNameProvider.applyWhere("tag", AccGLAccount.Field.TAG_ID + " IN (" + StringUtils.join(tags, ",") + ")");
            }
        }
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

        this.debitAccountNameVContainer = this.debitAccountNameBlock.newUIContainer("debitAccountNameVContainer");
        this.debitAccountNameView = new ReadOnlyView("debitAccountNameView", new PropertyModel<>(this.model, "debitAccountNameValue"));
        this.debitAccountNameVContainer.add(this.debitAccountNameView);

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

        if (this.ruleObject.get(AccAccountingRule.Field.DEBIT_ACCOUNT_ID) != null) {
            this.debitAccountNameVContainer.setVisible(true);
            this.debitAccountNameIContainer.setVisible(false);
        } else {
            this.debitAccountNameVContainer.setVisible(false);
            this.debitAccountNameIContainer.setVisible(true);
        }
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