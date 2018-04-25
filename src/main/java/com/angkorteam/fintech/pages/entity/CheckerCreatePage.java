package com.angkorteam.fintech.pages.entity;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.XRegisteredTable;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.EntityCheckBuilder;
import com.angkorteam.fintech.dto.enums.EntityStatus;
import com.angkorteam.fintech.dto.enums.EntityType;
import com.angkorteam.fintech.helper.EntityCheckHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.OrganizationDashboardPage;
import com.angkorteam.fintech.provider.EntityStatusProvider;
import com.angkorteam.fintech.provider.EntityTypeProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;

import io.github.openunirest.http.JsonNode;

/**
 * Created by socheatkhauv on 7/15/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class CheckerCreatePage extends Page {

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected UIRow row1;

    protected UIBlock entityBlock;
    protected UIContainer entityIContainer;
    protected EntityTypeProvider entityProvider;
    protected Option entityValue;
    protected Select2SingleChoice<Option> entityField;

    protected UIBlock row1Block1;

    protected UIRow row2;

    protected UIBlock statusBlock;
    protected UIContainer statusIContainer;
    protected EntityStatusProvider statusProvider;
    protected Option statusValue;
    protected Select2SingleChoice<Option> statusField;

    protected UIBlock row2Block1;

    protected UIRow row3;

    protected UIBlock datatableBlock;
    protected UIContainer datatableIContainer;
    protected SingleChoiceProvider datatableProvider;
    protected Option datatableValue;
    protected Select2SingleChoice<Option> datatableField;

    protected UIBlock row3Block1;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
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
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initData() {
        this.entityProvider = new EntityTypeProvider();
        this.statusProvider = new EntityStatusProvider();
        this.datatableProvider = new SingleChoiceProvider(XRegisteredTable.NAME, XRegisteredTable.Field.APPLICATION_TABLE_NAME, XRegisteredTable.Field.REGISTERED_TABLE_NAME);
        this.datatableProvider.setDisabled(true);
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

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.entityBlock = this.row1.newUIBlock("entityBlock", Size.Six_6);
        this.entityIContainer = this.entityBlock.newUIContainer("entityIContainer");
        this.entityField = new Select2SingleChoice<>("entityField", new PropertyModel<>(this, "entityValue"), this.entityProvider);
        this.entityIContainer.add(this.entityField);
        this.entityIContainer.newFeedback("entityFeedback", this.entityField);

        this.row1Block1 = this.row1.newUIBlock("row1Block1", Size.Six_6);

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.statusBlock = this.row2.newUIBlock("statusBlock", Size.Six_6);
        this.statusIContainer = this.statusBlock.newUIContainer("statusIContainer");
        this.statusField = new Select2SingleChoice<>("statusField", new PropertyModel<>(this, "statusValue"), this.statusProvider);
        this.statusIContainer.add(this.statusField);
        this.statusIContainer.newFeedback("statusFeedback", this.statusField);

        this.row2Block1 = this.row2.newUIBlock("row2Block1", Size.Six_6);

        this.row3 = UIRow.newUIRow("row3", this.form);

        this.datatableBlock = this.row3.newUIBlock("datatableBlock", Size.Six_6);
        this.datatableIContainer = this.datatableBlock.newUIContainer("datatableIContainer");
        this.datatableField = new Select2SingleChoice<>("datatableField", new PropertyModel<>(this, "datatableValue"), this.datatableProvider);
        this.datatableIContainer.add(this.datatableField);
        this.datatableIContainer.newFeedback("datatableFeedback", this.datatableField);

        this.row3Block1 = this.row3.newUIBlock("row3Block1", Size.Six_6);
    }

    @Override
    protected void configureMetaData() {
        this.datatableField.setRequired(true);
        this.datatableField.add(new OnChangeAjaxBehavior(this::datatableFieldUpdate));

        this.statusField.setRequired(true);
        this.statusField.add(new OnChangeAjaxBehavior(this::statusFieldUpdate));

        this.entityField.setRequired(true);
        this.entityField.add(new OnChangeAjaxBehavior(this::entityFieldUpdate));

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
            this.datatableProvider.applyWhere("application_table_name", XRegisteredTable.Field.APPLICATION_TABLE_NAME + " = '" + entityType.getLiteral() + "'");
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

        JsonNode node = EntityCheckHelper.create((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }

        setResponsePage(CheckerBrowsePage.class);
    }
}
