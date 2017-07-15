package com.angkorteam.fintech.pages.entity;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.AccountType;
import com.angkorteam.fintech.dto.FinancialActivityType;
import com.angkorteam.fintech.pages.office.OfficeBrowsePage;
import com.angkorteam.fintech.provider.EntityTypeProvider;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.OptionSingleChoiceProvider;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;

/**
 * Created by socheatkhauv on 7/15/17.
 */
public class CheckerCreatePage extends Page {

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

    private EntityTypeProvider entityProvider;
    private Option entityValue;
    private Select2SingleChoice<Option> entityField;
    private TextFeedbackPanel entityFeedback;

    private OptionSingleChoiceProvider statusProvider;
    private Option statusValue;
    private Select2SingleChoice<Option> statusField;
    private TextFeedbackPanel statusFeedback;

    private OptionSingleChoiceProvider datatableProvider;
    private Option datatableValue;
    private Select2SingleChoice<Option> datatableField;
    private TextFeedbackPanel datatableFeedback;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", OfficeBrowsePage.class);
        this.form.add(this.closeLink);

        this.entityProvider = new EntityTypeProvider();
        this.entityField = new Select2SingleChoice<>("entityField", new PropertyModel<>(this, "entityValue"), this.entityProvider);
        this.entityField.setRequired(true);
        this.form.add(this.entityField);
        this.entityFeedback = new TextFeedbackPanel("entityFeedback", this.entityField);
        this.form.add(this.entityFeedback);
        this.entityField.add(new OnChangeAjaxBehavior() {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                if (entityValue == null) {
                    accountValue = null;
                    accountProvider.setDisabled(true);
                } else {
                    AccountType classification_enum = null;
                    for (FinancialActivityType a : FinancialActivityType.values()) {
                        if (financialActivityValue.getId().equals(a.name())) {
                            classification_enum = a.getAccountType();
                            break;
                        }
                    }
                    accountValue = null;
                    accountProvider.setDisabled(false);
                    accountProvider.applyWhere("classification_enum", "classification_enum = " + classification_enum.getLiteral());
                }
                target.add(form);
            }

            @Override
            protected void onError(AjaxRequestTarget target, RuntimeException e) {
                if (e != null) {
                    throw e;
                }
                target.add(form);
                target.appendJavaScript(Select2SingleChoice.REMOVE_POPUP_UP_SCRIPT);
            }
        });

    }

    private void saveButtonSubmit(Button button) {

    }
}
