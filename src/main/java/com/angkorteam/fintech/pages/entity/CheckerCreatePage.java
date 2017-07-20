package com.angkorteam.fintech.pages.entity;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.EntityStatus;
import com.angkorteam.fintech.dto.EntityType;
import com.angkorteam.fintech.dto.request.EntityCheckBuilder;
import com.angkorteam.fintech.helper.EntityCheckHelper;
import com.angkorteam.fintech.provider.EntityStatusProvider;
import com.angkorteam.fintech.provider.EntityTypeProvider;
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

    private EntityStatusProvider statusProvider;
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

        this.closeLink = new BookmarkablePageLink<>("closeLink", CheckerBrowsePage.class);
        this.form.add(this.closeLink);

        this.entityProvider = new EntityTypeProvider();
        this.entityField = new Select2SingleChoice<>("entityField", 0, new PropertyModel<>(this, "entityValue"), this.entityProvider);
        this.entityField.setRequired(true);
        this.form.add(this.entityField);
        this.entityFeedback = new TextFeedbackPanel("entityFeedback", this.entityField);
        this.form.add(this.entityFeedback);
        this.entityField.add(new OnChangeAjaxBehavior(this::entityFieldUpdate, this::entityFieldError));

        this.statusProvider = new EntityStatusProvider();
        this.statusField = new Select2SingleChoice<>("statusField", 0, new PropertyModel<>(this, "statusValue"), this.statusProvider);
        this.statusField.setRequired(true);
        this.form.add(this.statusField);
        this.statusFeedback = new TextFeedbackPanel("statusFeedback", this.statusField);
        this.form.add(this.statusFeedback);
        this.statusField.add(new OnChangeAjaxBehavior(this::statusFieldUpdate, this::statusFieldError));

        this.datatableProvider = new OptionSingleChoiceProvider("x_registered_table", "application_table_name", "registered_table_name");
        this.datatableProvider.setDisabled(true);
        this.datatableField = new Select2SingleChoice<>("datatableField", 0, new PropertyModel<>(this, "datatableValue"), this.datatableProvider);
        this.datatableField.setRequired(true);
        this.datatableField.add(new OnChangeAjaxBehavior(this::datatableFieldUpdate, this::datatableFieldError));
        this.form.add(this.datatableField);
        this.datatableFeedback = new TextFeedbackPanel("datatableFeedback", this.datatableField);
        this.form.add(this.datatableFeedback);

    }

    private void datatableFieldError(AjaxRequestTarget target, RuntimeException e) {
        if (e != null) {
            throw e;
        }
        target.add(this.form);
        target.appendJavaScript(Select2SingleChoice.REMOVE_POPUP_UP_SCRIPT);
    }

    private void datatableFieldUpdate(AjaxRequestTarget target) {
        target.add(this.form);
    }

    private void statusFieldError(AjaxRequestTarget target, RuntimeException e) {
        if (e != null) {
            throw e;
        }
        target.add(this.form);
        target.appendJavaScript(Select2SingleChoice.REMOVE_POPUP_UP_SCRIPT);
    }

    private void statusFieldUpdate(AjaxRequestTarget target) {
        target.add(this.form);
    }

    private void entityFieldError(AjaxRequestTarget target, RuntimeException e) {
        if (e != null) {
            throw e;
        }

        this.statusValue = null;
        this.statusProvider.setType(null);

        this.datatableValue = null;
        this.datatableProvider.setDisabled(true);

        target.add(this.form);
        target.appendJavaScript(Select2SingleChoice.REMOVE_POPUP_UP_SCRIPT);
    }

    private void entityFieldUpdate(AjaxRequestTarget target) {

        this.statusValue = null;

        if (this.entityValue != null) {
            EntityType entityType = EntityType.valueOf(this.entityValue.getId());
            this.statusProvider.setType(entityType);
        } else {
            this.statusProvider.setType(null);
        }

        this.datatableValue = null;

        if (this.entityValue != null) {
            EntityType entityType = EntityType.valueOf(this.entityValue.getId());
            this.datatableProvider.setDisabled(false);
            this.datatableProvider.applyWhere("application_table_name", "application_table_name = '" + entityType.getLiteral() + "'");
        } else {
            this.datatableProvider.setDisabled(true);
        }

        target.add(this.form);
    }

    private void saveButtonSubmit(Button button) {
        EntityCheckBuilder builder = new EntityCheckBuilder();
        builder.withDatatableName(this.datatableValue.getText());
        builder.withEntity(EntityType.valueOf(this.entityValue.getId()).getLiteral());
        builder.withStatus(EntityStatus.valueOf(this.statusValue.getId()));

        JsonNode node = null;
        try {
            node = EntityCheckHelper.createChecker(builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }

        setResponsePage(CheckerBrowsePage.class);
    }
}