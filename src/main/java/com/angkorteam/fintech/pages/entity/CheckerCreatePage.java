package com.angkorteam.fintech.pages.entity;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.EntityCheckBuilder;
import com.angkorteam.fintech.dto.enums.EntityStatus;
import com.angkorteam.fintech.dto.enums.EntityType;
import com.angkorteam.fintech.helper.EntityCheckHelper;
import com.angkorteam.fintech.pages.OrganizationDashboardPage;
import com.angkorteam.fintech.provider.EntityStatusProvider;
import com.angkorteam.fintech.provider.EntityTypeProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 7/15/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class CheckerCreatePage extends Page {

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock entityBlock;
    protected WebMarkupContainer entityIContainer;
    protected EntityTypeProvider entityProvider;
    protected Option entityValue;
    protected Select2SingleChoice<Option> entityField;
    protected TextFeedbackPanel entityFeedback;

    protected WebMarkupBlock statusBlock;
    protected WebMarkupContainer statusIContainer;
    protected EntityStatusProvider statusProvider;
    protected Option statusValue;
    protected Select2SingleChoice<Option> statusField;
    protected TextFeedbackPanel statusFeedback;

    protected WebMarkupBlock datatableBlock;
    protected WebMarkupContainer datatableIContainer;
    protected SingleChoiceProvider datatableProvider;
    protected Option datatableValue;
    protected Select2SingleChoice<Option> datatableField;
    protected TextFeedbackPanel datatableFeedback;

    protected static final List<PageBreadcrumb> BREADCRUMB;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        return Model.ofList(BREADCRUMB);
    }

    static {
        BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Admin");
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Organization");
            breadcrumb.setPage(OrganizationDashboardPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Entity Data Table Check");
            breadcrumb.setPage(CheckerBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Entity Data Table Check Create");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", CheckerBrowsePage.class);
        this.form.add(this.closeLink);

        initEntityBlock();

        initStatusBlock();

        initDatatableBlock();
    }

    @Override
    protected void configureRequiredValidation() {

    }

    @Override
    protected void configureMetaData() {

    }

    protected void initEntityBlock() {
        this.entityBlock = new WebMarkupBlock("entityBlock", Size.Twelve_12);
        this.form.add(this.entityBlock);
        this.entityIContainer = new WebMarkupContainer("entityIContainer");
        this.entityBlock.add(this.entityIContainer);
        this.entityProvider = new EntityTypeProvider();
        this.entityField = new Select2SingleChoice<>("entityField", new PropertyModel<>(this, "entityValue"), this.entityProvider);
        this.entityField.setRequired(true);
        this.entityIContainer.add(this.entityField);
        this.entityFeedback = new TextFeedbackPanel("entityFeedback", this.entityField);
        this.entityIContainer.add(this.entityFeedback);
        this.entityField.add(new OnChangeAjaxBehavior(this::entityFieldUpdate));
    }

    protected void initStatusBlock() {
        this.statusBlock = new WebMarkupBlock("statusBlock", Size.Twelve_12);
        this.form.add(this.statusBlock);
        this.statusIContainer = new WebMarkupContainer("statusIContainer");
        this.statusBlock.add(this.statusIContainer);
        this.statusProvider = new EntityStatusProvider();
        this.statusField = new Select2SingleChoice<>("statusField", 0, new PropertyModel<>(this, "statusValue"), this.statusProvider);
        this.statusField.setRequired(true);
        this.statusIContainer.add(this.statusField);
        this.statusFeedback = new TextFeedbackPanel("statusFeedback", this.statusField);
        this.statusIContainer.add(this.statusFeedback);
        this.statusField.add(new OnChangeAjaxBehavior(this::statusFieldUpdate));
    }

    protected void initDatatableBlock() {
        this.datatableBlock = new WebMarkupBlock("datatableBlock", Size.Twelve_12);
        this.form.add(this.datatableBlock);
        this.datatableIContainer = new WebMarkupContainer("datatableIContainer");
        this.datatableBlock.add(this.datatableIContainer);
        this.datatableProvider = new SingleChoiceProvider("x_registered_table", "application_table_name", "registered_table_name");
        this.datatableProvider.setDisabled(true);
        this.datatableField = new Select2SingleChoice<>("datatableField", 0, new PropertyModel<>(this, "datatableValue"), this.datatableProvider);
        this.datatableField.setRequired(true);
        this.datatableField.add(new OnChangeAjaxBehavior(this::datatableFieldUpdate));
        this.datatableIContainer.add(this.datatableField);
        this.datatableFeedback = new TextFeedbackPanel("datatableFeedback", this.datatableField);
        this.datatableIContainer.add(this.datatableFeedback);
    }

    protected boolean datatableFieldUpdate(AjaxRequestTarget target) {
        target.add(this.form);
        return false;
    }

    protected boolean statusFieldUpdate(AjaxRequestTarget target) {
        target.add(this.form);
        return false;
    }

    protected boolean entityFieldUpdate(AjaxRequestTarget target) {

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

        if (target != null) {
            target.add(this.statusBlock);
            target.add(this.datatableBlock);
            target.add(this.entityBlock);
        }
        return false;
    }

    protected void saveButtonSubmit(Button button) {
        EntityCheckBuilder builder = new EntityCheckBuilder();
        builder.withDatatableName(this.datatableValue.getText());
        builder.withEntity(EntityType.valueOf(this.entityValue.getId()).getLiteral());
        builder.withStatus(EntityStatus.valueOf(this.statusValue.getId()));

        JsonNode node = null;
        try {
            node = EntityCheckHelper.create((Session) getSession(), builder.build());
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
