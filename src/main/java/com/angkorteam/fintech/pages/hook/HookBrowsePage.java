package com.angkorteam.fintech.pages.hook;

import java.util.List;
import java.util.Map;

import com.angkorteam.fintech.ddl.MHook;
import com.angkorteam.fintech.ddl.MHookTemplates;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.helper.HookHelper;
import com.angkorteam.fintech.pages.SystemDashboardPage;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.table.BadgeCell;
import com.angkorteam.fintech.table.LinkCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.BadgeType;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionFilterColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionItem;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemCss;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/27/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class HookBrowsePage extends Page {

    protected WebMarkupBlock dataBlock;
    protected WebMarkupContainer dataIContainer;
    protected DataTable<Map<String, Object>, String> dataTable;
    protected JdbcProvider dataProvider;
    protected List<IColumn<Map<String, Object>, String>> dataColumn;
    protected FilterForm<Map<String, String>> dataFilterForm;

    protected WebMarkupBlock templateBlock;
    protected WebMarkupContainer templateIContainer;
    protected SingleChoiceProvider templateProvider;
    protected Option templateValue;
    protected Select2SingleChoice<Option> templateField;
    protected TextFeedbackPanel templateFeedback;

    protected Form<Void> form;
    protected Button createButton;

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
            breadcrumb.setLabel("System");
            breadcrumb.setPage(SystemDashboardPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Hook");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initComponent() {
        initDataBlock();

        this.form = new Form<>("form");
        add(this.form);

        this.createButton = new Button("createButton");
        this.createButton.setOnSubmit(this::createButtonSubmit);
        this.form.add(this.createButton);

        initTemplateBlock();
    }

    protected void initTemplateBlock() {
        this.templateBlock = new WebMarkupBlock("templateBlock", Size.Twelve_12);
        this.form.add(this.templateBlock);
        this.templateIContainer = new WebMarkupContainer("templateIContainer");
        this.templateBlock.add(this.templateIContainer);
        this.templateProvider = new SingleChoiceProvider(MHookTemplates.NAME, MHookTemplates.Field.ID, MHookTemplates.Field.NAME);
        this.templateField = new Select2SingleChoice<>("templateField", new PropertyModel<>(this, "templateValue"), this.templateProvider);
        this.templateField.setRequired(true);
        this.templateIContainer.add(this.templateField);
        this.templateFeedback = new TextFeedbackPanel("templateFeedback", this.templateField);
        this.templateIContainer.add(this.templateFeedback);
    }

    protected void initDataBlock() {
        this.dataBlock = new WebMarkupBlock("dataBlock", Size.Twelve_12);
        add(this.dataBlock);
        this.dataIContainer = new WebMarkupContainer("dataIContainer");
        this.dataBlock.add(this.dataIContainer);
        this.dataProvider = new JdbcProvider(MHook.NAME);
        this.dataProvider.applyJoin("m_hook_templates", "LEFT JOIN " + MHookTemplates.NAME + " ON " + MHook.NAME + "." + MHook.Field.TEMPLATE_ID + " = " + MHookTemplates.NAME + "." + MHookTemplates.Field.ID);
        this.dataProvider.boardField(MHook.NAME + "." + MHook.Field.ID, "id", Long.class);
        this.dataProvider.boardField(MHook.NAME + "." + MHook.Field.NAME, "name", String.class);
        this.dataProvider.boardField(MHookTemplates.NAME + "." + MHookTemplates.Field.NAME, "template", String.class);
        this.dataProvider.boardField(MHook.NAME + "." + MHook.Field.IS_ACTIVE, "active", Long.class);

        this.dataProvider.selectField("id", Long.class);

        this.dataColumn = Lists.newArrayList();
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Name"), "name", "name", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Template"), "template", "template", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Long, Model.of("Is Active ?"), "active", "active", this::dataColumn));
        this.dataColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::dataAction, this::dataClick));

        this.dataFilterForm = new FilterForm<>("dataFilterForm", this.dataProvider);
        this.dataIContainer.add(this.dataFilterForm);

        this.dataTable = new DefaultDataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, this.dataFilterForm));
        this.dataFilterForm.add(this.dataTable);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected void dataClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        Long id = (Long) model.get("id");
        try {
            HookHelper.delete((Session) getSession(), String.valueOf(id));
        } catch (UnirestException e) {
        }
        target.add(this.dataTable);
    }

    protected List<ActionItem> dataAction(String s, Map<String, Object> model) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected void createButtonSubmit(Button button) {
        PageParameters parameters = new PageParameters();
        parameters.add("templateId", this.templateValue.getId());
        setResponsePage(HookCreatePage.class, parameters);
    }

    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("name".equals(column)) {
            String value = (String) model.get(column);
            PageParameters parameters = new PageParameters();
            parameters.add("hookId", model.get("id"));
            return new LinkCell(HookModifyPage.class, parameters, value);
        } else if ("template".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("active".equals(column)) {
            Long value = (Long) model.get(column);
            if (value != null && value == 1) {
                return new BadgeCell(BadgeType.Success, Model.of("Yes"));
            } else {
                return new BadgeCell(BadgeType.Danger, Model.of("No"));
            }
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

}
