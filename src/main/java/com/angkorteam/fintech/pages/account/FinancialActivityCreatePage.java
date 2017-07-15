package com.angkorteam.fintech.pages.account;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.AccountType;
import com.angkorteam.fintech.dto.AccountUsage;
import com.angkorteam.fintech.dto.FinancialActivityType;
import com.angkorteam.fintech.dto.request.FinancialActivityBuilder;
import com.angkorteam.fintech.helper.FinancialActivityHelper;
import com.angkorteam.fintech.provider.FinancialActivityProvider;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.OptionSingleChoiceProvider;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;

/**
 * Created by socheatkhauv on 6/27/17.
 */
public class FinancialActivityCreatePage extends Page {

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

    private FinancialActivityProvider financialActivityProvider;
    private Option financialActivityValue;
    private Select2SingleChoice<Option> financialActivityField;
    private TextFeedbackPanel financialActivityFeedback;

    private OptionSingleChoiceProvider accountProvider;
    private Option accountValue;
    private Select2SingleChoice<Option> accountField;
    private TextFeedbackPanel accountFeedback;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", FinancialActivityBrowsePage.class);
        this.form.add(this.closeLink);

        this.financialActivityProvider = new FinancialActivityProvider();
        this.financialActivityField = new Select2SingleChoice<>("financialActivityField", 0, new PropertyModel<>(this, "financialActivityValue"), this.financialActivityProvider);
        this.form.add(this.financialActivityField);
        this.financialActivityFeedback = new TextFeedbackPanel("financialActivityFeedback", this.financialActivityField);
        this.form.add(this.financialActivityFeedback);
        this.financialActivityField.add(new OnChangeAjaxBehavior(this::financialActivityFieldUpdate, this::financialActivityFieldError));

        this.accountProvider = new OptionSingleChoiceProvider("acc_gl_account", "id", "name");
        this.accountProvider.applyWhere("usage", AccountUsage.Detail.getLiteral());
        this.accountProvider.setDisabled(true);
        this.accountField = new Select2SingleChoice<>("accountField", 0, new PropertyModel<>(this, "accountValue"), this.accountProvider);
        this.form.add(this.accountField);
        this.accountFeedback = new TextFeedbackPanel("accountFeedback", this.accountField);
        this.form.add(this.accountFeedback);
    }

    private void financialActivityFieldUpdate(AjaxRequestTarget target) {
        if (this.financialActivityValue == null) {
            this.accountValue = null;
            this.accountProvider.setDisabled(true);
        } else {
            AccountType classification_enum = null;
            for (FinancialActivityType a : FinancialActivityType.values()) {
                if (this.financialActivityValue.getId().equals(a.name())) {
                    classification_enum = a.getAccountType();
                    break;
                }
            }
            this.accountValue = null;
            this.accountProvider.setDisabled(false);
            this.accountProvider.applyWhere("classification_enum", "classification_enum = " + classification_enum.getLiteral());
        }
        target.add(this.form);
    }

    private void financialActivityFieldError(AjaxRequestTarget target, RuntimeException e) {
        if (e != null) {
            throw e;
        }
        target.add(this.form);
        target.appendJavaScript(Select2SingleChoice.REMOVE_POPUP_UP_SCRIPT);
    }

    private void saveButtonSubmit(Button button) {
        FinancialActivityBuilder builder = new FinancialActivityBuilder();
        if (this.financialActivityValue != null) {
            builder.withFinancialActivityBuilder(FinancialActivityType.valueOf(this.financialActivityValue.getId()).getLiteral());
        }
        if (this.accountValue != null) {
            builder.withGlAccountId(this.accountValue.getId());
        }
        JsonNode node = null;
        try {
            node = FinancialActivityHelper.createFinancialActivity(builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(FinancialActivityBrowsePage.class);
    }

}
