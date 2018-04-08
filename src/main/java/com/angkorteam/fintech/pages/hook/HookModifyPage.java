package com.angkorteam.fintech.pages.hook;

import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import com.angkorteam.fintech.widget.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.MHook;
import com.angkorteam.fintech.ddl.MHookConfiguration;
import com.angkorteam.fintech.ddl.MHookRegisteredEvents;
import com.angkorteam.fintech.ddl.MHookSchema;
import com.angkorteam.fintech.ddl.MHookTemplates;
import com.angkorteam.fintech.ddl.MPermission;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.HookBuilder;
import com.angkorteam.fintech.helper.HookHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.pages.SystemDashboardPage;
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.HookFieldWidget;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.TextColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionFilterColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionItem;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemCss;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.OptionChoiceRenderer;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;

/**
 * Created by socheatkhauv on 6/27/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class HookModifyPage extends Page {

    protected String hookId;

    protected WebMarkupBlock groupingBlock;
    protected WebMarkupContainer groupingIContainer;
    protected List<Option> groupingProvider;
    protected Option groupingValue;
    protected DropDownChoice<Option> groupingField;
    protected TextFeedbackPanel groupingFeedback;

    protected WebMarkupBlock entityNameBlock;
    protected WebMarkupContainer entityNameIContainer;
    protected List<Option> entityNameProvider;
    protected Option entityNameValue;
    protected DropDownChoice<Option> entityNameField;
    protected TextFeedbackPanel entityNameFeedback;

    protected WebMarkupBlock actionNameBlock;
    protected WebMarkupContainer actionNameIContainer;
    protected List<Option> actionNameProvider;
    protected Option actionNameValue;
    protected DropDownChoice<Option> actionNameField;
    protected TextFeedbackPanel actionNameFeedback;

    protected Form<Void> form;
    protected AjaxButton addButton;

    protected String templateId;

    protected WebMarkupBlock templateBlock;
    protected WebMarkupContainer templateVContainer;
    protected String templateValue;
    protected ReadOnlyView templateView;

    protected WebMarkupBlock nameBlock;
    protected WebMarkupContainer nameIContainer;
    protected String nameValue;
    protected TextField<String> nameField;
    protected TextFeedbackPanel nameFeedback;

    protected WebMarkupBlock activeBlock;
    protected WebMarkupContainer activeIContainer;
    protected Boolean activeValue;
    protected CheckBox activeField;
    protected TextFeedbackPanel activeFeedback;

    protected Map<String, String> configValue;
    protected RepeatingView configField;

    protected WebMarkupBlock dataBlock;
    protected WebMarkupContainer dataIContainer;
    protected ListDataProvider dataProvider;
    protected DataTable<Map<String, Object>, String> dataTable;
    protected List<Map<String, Object>> dataValue;
    protected List<IColumn<Map<String, Object>, String>> dataColumn;

    protected Form<Void> hookForm;
    protected Button saveButton;

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
            breadcrumb.setPage(HookBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Hook Modify");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initData() {
        PageParameters parameters = getPageParameters();
        this.hookId = parameters.get("hookId").toString("");

        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery(MHook.NAME);
        selectQuery.addWhere(MHook.Field.ID + " = :" + MHook.Field.ID, this.hookId);
        selectQuery.addField(MHook.Field.TEMPLATE_ID);
        selectQuery.addField(MHook.Field.NAME);
        selectQuery.addField(MHook.Field.IS_ACTIVE);
        Map<String, Object> hookObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        selectQuery = new SelectQuery(MHookTemplates.NAME);
        selectQuery.addField(MHookTemplates.Field.ID);
        selectQuery.addField(MHookTemplates.Field.NAME);
        selectQuery.addWhere(MHookTemplates.Field.ID + " = :" + MHookTemplates.Field.ID, hookObject.get("template_id"));
        Map<String, Object> templateObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        this.configValue = Maps.newHashMap();
        selectQuery = new SelectQuery(MHookConfiguration.NAME);
        selectQuery.addField(MHookConfiguration.Field.FIELD_NAME);
        selectQuery.addField(MHookConfiguration.Field.FIELD_VALUE);
        selectQuery.addWhere(MHookConfiguration.Field.HOOK_ID + " = :" + MHookConfiguration.Field.HOOK_ID, this.hookId);
        List<Map<String, Object>> configurations = named.queryForList(selectQuery.toSQL(), selectQuery.getParam());
        for (Map<String, Object> configuration : configurations) {
            this.configValue.put((String) configuration.get("field_name"), (String) configuration.get("field_value"));
        }
        this.templateId = String.valueOf(templateObject.get("id"));
        this.templateValue = (String) templateObject.get("name");
        this.dataValue = Lists.newArrayList();
        selectQuery = new SelectQuery(MHookRegisteredEvents.NAME);
        selectQuery.addField(MHookRegisteredEvents.Field.ENTITY_NAME);
        selectQuery.addField(MHookRegisteredEvents.Field.ACTION_NAME);
        selectQuery.addWhere(MHookRegisteredEvents.Field.HOOK_ID + " = :" + MHookRegisteredEvents.Field.HOOK_ID, this.hookId);
        List<Map<String, Object>> events = named.queryForList(selectQuery.toSQL(), selectQuery.getParam());
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        for (Map<String, Object> event : events) {
            Map<String, Object> temp = Maps.newHashMap();
            temp.put("uuid", generator.externalId());
            temp.put("entity_name", event.get("entity_name"));
            temp.put("action_name", event.get("action_name"));
            this.dataValue.add(temp);
        }
        this.nameValue = (String) hookObject.get("name");
        this.activeValue = hookObject.get("is_active") != null && String.valueOf(hookObject.get("is_active")).equals("1");

        selectQuery = new SelectQuery(MHookTemplates.NAME);
        selectQuery.addField(MHookTemplates.Field.NAME);
        selectQuery.addWhere(MHookTemplates.Field.ID + " = :" + MHookTemplates.Field.ID, this.templateId);
        this.templateValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), String.class);
    }

    @Override
    protected void initComponent() {
        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        this.form = new Form<>("form");
        add(this.form);

        initGroupingBlock();

        initEntityNameBlock();

        initActionNameBlock();

        this.addButton = new AjaxButton("addButton");
        this.addButton.setOnSubmit(this::addButtonSubmit);
        this.form.add(this.addButton);

        this.hookForm = new Form<>("hookForm");
        add(this.hookForm);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.hookForm.add(this.saveButton);

        initTemplateBlock();

        initNameBlock();

        initActiveBlock();

        this.configField = new RepeatingView("configField");
        this.hookForm.add(this.configField);

        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery(MHookSchema.NAME);
        selectQuery.addField(MHookSchema.Field.FIELD_NAME);
        selectQuery.addWhere(MHookSchema.Field.HOOK_TEMPLATE_ID + " = :" + MHookSchema.Field.HOOK_TEMPLATE_ID, this.templateId);

        List<Map<String, Object>> temps = named.queryForList(selectQuery.toSQL(), selectQuery.getParam());
        for (Map<String, Object> temp : temps) {
            String id = this.configField.newChildId();
            HookFieldWidget field = new HookFieldWidget(id, (String) temp.get(MHookSchema.Field.FIELD_NAME), this.configValue);
            this.configField.add(field);
        }

        initDataBlock();
    }

    protected void initActionNameBlock() {
        this.actionNameBlock = new WebMarkupBlock("actionNameBlock", Size.Four_4);
        this.form.add(this.actionNameBlock);
        this.actionNameIContainer = new WebMarkupContainer("actionNameIContainer");
        this.actionNameBlock.add(this.actionNameIContainer);
        this.actionNameProvider = Lists.newArrayList();
        this.actionNameField = new DropDownChoice<>("actionNameField", new PropertyModel<>(this, "actionNameValue"), new PropertyModel<>(this, "actionNameProvider"), new OptionChoiceRenderer());
        this.actionNameField.setRequired(true);
        this.actionNameIContainer.add(this.actionNameField);
        this.actionNameFeedback = new TextFeedbackPanel("actionNameFeedback", this.actionNameField);
        this.actionNameIContainer.add(this.actionNameFeedback);
    }

    protected void initEntityNameBlock() {
        this.entityNameBlock = new WebMarkupBlock("entityNameBlock", Size.Four_4);
        this.form.add(this.entityNameBlock);
        this.entityNameIContainer = new WebMarkupContainer("entityNameIContainer");
        this.entityNameBlock.add(this.entityNameIContainer);
        this.entityNameProvider = Lists.newArrayList();
        this.entityNameField = new DropDownChoice<>("entityNameField", new PropertyModel<>(this, "entityNameValue"), new PropertyModel<>(this, "entityNameProvider"), new OptionChoiceRenderer());
        this.entityNameField.setRequired(true);
        this.entityNameField.add(new OnChangeAjaxBehavior(this::entityNameFieldUpdate));
        this.entityNameIContainer.add(this.entityNameField);
        this.entityNameFeedback = new TextFeedbackPanel("entityNameFeedback", this.entityNameField);
        this.entityNameIContainer.add(this.entityNameFeedback);
    }

    protected void initGroupingBlock() {
        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
        this.groupingBlock = new WebMarkupBlock("groupingBlock", Size.Four_4);
        this.form.add(this.groupingBlock);
        this.groupingIContainer = new WebMarkupContainer("groupingIContainer");
        this.groupingBlock.add(this.groupingIContainer);

        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery(MPermission.NAME);
        selectQuery.addField("MAX(" + MPermission.Field.GROUPING + ") as id");
        selectQuery.addField("MAX(" + MPermission.Field.GROUPING + ") as text");
        selectQuery.addGroupBy(MPermission.Field.GROUPING);
        this.groupingProvider = named.query(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);

        this.groupingField = new DropDownChoice<>("groupingField", new PropertyModel<>(this, "groupingValue"), new PropertyModel<>(this, "groupingProvider"), new OptionChoiceRenderer());
        this.groupingField.setRequired(true);
        this.groupingField.add(new OnChangeAjaxBehavior(this::groupingFieldUpdate));
        this.groupingIContainer.add(this.groupingField);
        this.groupingFeedback = new TextFeedbackPanel("groupingFeedback", this.groupingField);
        this.groupingIContainer.add(this.groupingFeedback);
    }

    protected void initDataBlock() {
        this.dataBlock = new WebMarkupBlock("dataBlock", Size.Twelve_12);
        this.hookForm.add(this.dataBlock);
        this.dataIContainer = new WebMarkupContainer("dataIContainer");
        this.dataBlock.add(this.dataIContainer);

        this.dataProvider = new ListDataProvider(this.dataValue);

        this.dataColumn = Lists.newArrayList();
        this.dataColumn.add(new TextColumn(Model.of("Entity Name"), "entity_name", "entity_name", this::dataColumn));
        this.dataColumn.add(new TextColumn(Model.of("Action Name"), "action_name", "action_name", this::dataColumn));
        this.dataColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::dataAction, this::dataClick));

        this.dataTable = new DataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
        this.dataIContainer.add(this.dataTable);
        this.dataTable.addTopToolbar(new HeadersToolbar<>(this.dataTable, this.dataProvider));
        this.dataTable.addBottomToolbar(new NoRecordsToolbar(this.dataTable));
    }

    protected void initActiveBlock() {
        this.activeBlock = new WebMarkupBlock("activeBlock", Size.Twelve_12);
        this.hookForm.add(this.activeBlock);
        this.activeIContainer = new WebMarkupContainer("activeIContainer");
        this.activeBlock.add(this.activeIContainer);
        this.activeField = new CheckBox("activeField", new PropertyModel<>(this, "activeValue"));
        this.activeField.setRequired(true);
        this.activeIContainer.add(this.activeField);
        this.activeFeedback = new TextFeedbackPanel("activeFeedback", this.activeField);
        this.activeIContainer.add(this.activeFeedback);
    }

    protected void initTemplateBlock() {
        this.templateBlock = new WebMarkupBlock("templateBlock", Size.Six_6);
        this.hookForm.add(this.templateBlock);
        this.templateVContainer = new WebMarkupContainer("templateVContainer");
        this.templateBlock.add(this.templateVContainer);
        this.templateView = new ReadOnlyView("templateView", new PropertyModel<>(this, "templateValue"));
        this.templateVContainer.add(this.templateView);
    }

    protected void initNameBlock() {
        this.nameBlock = new WebMarkupBlock("nameBlock", Size.Six_6);
        this.hookForm.add(this.nameBlock);
        this.nameIContainer = new WebMarkupContainer("nameIContainer");
        this.nameBlock.add(this.nameIContainer);
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setRequired(true);
        this.nameIContainer.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.nameIContainer.add(this.nameFeedback);
    }

    @Override
    protected void configureMetaData() {
    }

    protected boolean groupingFieldUpdate(AjaxRequestTarget target) {
        if (this.groupingValue != null) {
            JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

            SelectQuery selectQuery = null;

            selectQuery = new SelectQuery(MPermission.NAME);
            selectQuery.addField("MAX(" + MPermission.Field.ENTITY_NAME + ") as id");
            selectQuery.addField("MAX(" + MPermission.Field.ENTITY_NAME + ") as text");
            selectQuery.addGroupBy(MPermission.Field.ENTITY_NAME);
            selectQuery.addWhere(MPermission.Field.GROUPING + " = :" + MPermission.Field.GROUPING, this.groupingValue.getId());
            this.entityNameProvider = named.query(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
        } else {
            if (this.entityNameProvider != null) {
                this.entityNameProvider.clear();
            }
            if (this.actionNameProvider != null) {
                this.actionNameProvider.clear();
            }
        }
        target.add(this.form);
        return false;
    }

    protected boolean entityNameFieldUpdate(AjaxRequestTarget target) {
        if (this.entityNameValue != null) {
            JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

            SelectQuery selectQuery = null;
            selectQuery = new SelectQuery(MPermission.NAME);
            selectQuery.addField("MAX(" + MPermission.Field.ACTION_NAME + ") as id");
            selectQuery.addField("MAX(" + MPermission.Field.ACTION_NAME + ") as text");
            selectQuery.addGroupBy(MPermission.Field.ACTION_NAME);
            selectQuery.addWhere(MPermission.Field.ENTITY_NAME + " = :" + MPermission.Field.ENTITY_NAME, this.entityNameValue.getId());
            this.actionNameProvider = named.query(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
        } else {
            if (this.actionNameProvider != null) {
                this.actionNameProvider.clear();
            }
        }
        target.add(this.form);
        return false;
    }

    protected void dataClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        int index = -1;
        for (int i = 0; i < this.dataValue.size(); i++) {
            Map<String, Object> column = this.dataValue.get(i);
            if (model.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.dataValue.remove(index);
        }
        target.add(this.dataTable);
    }

    protected List<ActionItem> dataAction(String s, Map<String, Object> model) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected void saveButtonSubmit(Button button) {
        HookBuilder builder = new HookBuilder();
        builder.withId(this.hookId);
        builder.withDisplayName(this.nameValue);
        builder.withName(this.templateValue);
        builder.withActive(this.activeValue);
        for (Map.Entry<String, String> config : this.configValue.entrySet()) {
            builder.withConfig(config.getKey(), config.getValue());
        }
        for (Map<String, Object> event : this.dataValue) {
            builder.withEvent((String) event.get("entity_name"), (String) event.get("action_name"));
        }

        JsonNode node = HookHelper.update((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(HookBrowsePage.class);
    }

    protected boolean addButtonError(AjaxButton button, AjaxRequestTarget target) {
        target.add(this.form);
        return false;
    }

    protected boolean addButtonSubmit(AjaxButton button, AjaxRequestTarget target) {
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        Map<String, Object> event = Maps.newHashMap();
        event.put("uuid", generator.externalId());
        if (this.entityNameValue != null) {
            event.put("entity_name", this.entityNameValue.getId());
        }
        if (this.actionNameValue != null) {
            event.put("action_name", this.actionNameValue.getId());
        }
        this.dataValue.add(event);
        this.entityNameValue = null;
        this.actionNameValue = null;
        target.add(this.form);
        target.add(this.dataTable);
        return false;
    }

    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("entity_name".equals(column) || "action_name".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }
}
