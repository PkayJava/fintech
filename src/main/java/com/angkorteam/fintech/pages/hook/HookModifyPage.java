//package com.angkorteam.fintech.pages.hook;
//
//import java.util.List;
//import java.util.Map;
//
//import org.apache.wicket.WicketRuntimeException;
//import org.apache.wicket.ajax.AjaxRequestTarget;
//import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
//import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
//import org.apache.wicket.markup.html.form.CheckBox;
//import org.apache.wicket.markup.html.form.DropDownChoice;
//import org.apache.wicket.markup.html.form.TextField;
//import org.apache.wicket.markup.repeater.RepeatingView;
//import org.apache.wicket.model.IModel;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//import org.apache.wicket.request.mapper.parameter.PageParameters;
//
//import com.angkorteam.fintech.Page;
//import com.angkorteam.fintech.Session;
//import com.angkorteam.fintech.ddl.MHook;
//import com.angkorteam.fintech.ddl.MHookConfiguration;
//import com.angkorteam.fintech.ddl.MHookRegisteredEvents;
//import com.angkorteam.fintech.ddl.MHookSchema;
//import com.angkorteam.fintech.ddl.MHookTemplates;
//import com.angkorteam.fintech.ddl.MPermission;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.dto.builder.HookBuilder;
//import com.angkorteam.fintech.helper.HookHelper;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.pages.SystemDashboardPage;
//import com.angkorteam.fintech.spring.StringGenerator;
//import com.angkorteam.fintech.table.TextCell;
//import com.angkorteam.fintech.widget.HookFieldWidget;
//import com.angkorteam.fintech.widget.ReadOnlyView;
//import com.angkorteam.framework.SpringBean;
//import com.angkorteam.framework.jdbc.SelectQuery;
//import com.angkorteam.framework.models.PageBreadcrumb;
//import com.angkorteam.framework.share.provider.ListDataProvider;
//import com.angkorteam.framework.spring.JdbcNamed;
//import com.angkorteam.framework.spring.JdbcTemplate;
//import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
//import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.TextColumn;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionFilterColumn;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionItem;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemCss;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.angkorteam.framework.wicket.markup.html.form.OptionChoiceRenderer;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//
//import io.github.openunirest.http.JsonNode;
//
///**
// * Created by socheatkhauv on 6/27/17.
// */
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class HookModifyPage extends Page {
//
//    protected String hookId;
//
//    protected String templateId;
//
//    protected Form<Void> form1;
//    protected AjaxButton addButton;
//
//    protected UIRow row1;
//
//    protected UIBlock groupingBlock;
//    protected UIContainer groupingIContainer;
//    protected List<Option> groupingProvider;
//    protected Option groupingValue;
//    protected DropDownChoice<Option> groupingField;
//
//    protected UIBlock entityNameBlock;
//    protected UIContainer entityNameIContainer;
//    protected List<Option> entityNameProvider;
//    protected Option entityNameValue;
//    protected DropDownChoice<Option> entityNameField;
//
//    protected UIBlock actionNameBlock;
//    protected UIContainer actionNameIContainer;
//    protected List<Option> actionNameProvider;
//    protected Option actionNameValue;
//    protected DropDownChoice<Option> actionNameField;
//
//    protected Form<Void> form2;
//    protected Button saveButton;
//
//    protected UIRow row2;
//
//    protected UIBlock templateBlock;
//    protected UIContainer templateVContainer;
//    protected String templateValue;
//    protected ReadOnlyView templateView;
//
//    protected UIBlock nameBlock;
//    protected UIContainer nameIContainer;
//    protected String nameValue;
//    protected TextField<String> nameField;
//
//    protected UIRow row3;
//
//    protected UIBlock activeBlock;
//    protected UIContainer activeIContainer;
//    protected Boolean activeValue;
//    protected CheckBox activeField;
//
//    protected Map<String, String> configValue;
//    protected RepeatingView configField;
//
//    protected UIRow row4;
//
//    protected UIBlock dataBlock;
//    protected UIContainer dataIContainer;
//    protected ListDataProvider dataProvider;
//    protected DataTable<Map<String, Object>, String> dataTable;
//    protected List<Map<String, Object>> dataValue;
//    protected List<IColumn<Map<String, Object>, String>> dataColumn;
//
//    @Override
//    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
//        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Admin");
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("System");
//            breadcrumb.setPage(SystemDashboardPage.class);
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Hook");
//            breadcrumb.setPage(HookBrowsePage.class);
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Hook Modify");
//            BREADCRUMB.add(breadcrumb);
//        }
//        return Model.ofList(BREADCRUMB);
//    }
//
//    @Override
//    protected void initData() {
//        PageParameters parameters = getPageParameters();
//        this.hookId = parameters.get("hookId").toString("");
//
//        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
//
//        SelectQuery selectQuery = null;
//
//        selectQuery = new SelectQuery(MHook.NAME);
//        selectQuery.addWhere(MHook.Field.ID + " = :" + MHook.Field.ID, this.hookId);
//        selectQuery.addField(MHook.Field.TEMPLATE_ID);
//        selectQuery.addField(MHook.Field.NAME);
//        selectQuery.addField(MHook.Field.IS_ACTIVE);
//        Map<String, Object> hookObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
//
//        selectQuery = new SelectQuery(MHookTemplates.NAME);
//        selectQuery.addField(MHookTemplates.Field.ID);
//        selectQuery.addField(MHookTemplates.Field.NAME);
//        selectQuery.addWhere(MHookTemplates.Field.ID + " = :" + MHookTemplates.Field.ID, hookObject.get("template_id"));
//        Map<String, Object> templateObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
//
//        this.configValue = Maps.newHashMap();
//        selectQuery = new SelectQuery(MHookConfiguration.NAME);
//        selectQuery.addField(MHookConfiguration.Field.FIELD_NAME);
//        selectQuery.addField(MHookConfiguration.Field.FIELD_VALUE);
//        selectQuery.addWhere(MHookConfiguration.Field.HOOK_ID + " = :" + MHookConfiguration.Field.HOOK_ID, this.hookId);
//        List<Map<String, Object>> configurations = named.queryForList(selectQuery.toSQL(), selectQuery.getParam());
//        for (Map<String, Object> configuration : configurations) {
//            this.configValue.put((String) configuration.get("field_name"), (String) configuration.get("field_value"));
//        }
//
//        this.templateId = String.valueOf(templateObject.get("id"));
//        this.templateValue = (String) templateObject.get("name");
//        this.dataValue = Lists.newArrayList();
//        selectQuery = new SelectQuery(MHookRegisteredEvents.NAME);
//        selectQuery.addField(MHookRegisteredEvents.Field.ENTITY_NAME);
//        selectQuery.addField(MHookRegisteredEvents.Field.ACTION_NAME);
//        selectQuery.addWhere(MHookRegisteredEvents.Field.HOOK_ID + " = :" + MHookRegisteredEvents.Field.HOOK_ID, this.hookId);
//        List<Map<String, Object>> events = named.queryForList(selectQuery.toSQL(), selectQuery.getParam());
//        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
//        for (Map<String, Object> event : events) {
//            Map<String, Object> temp = Maps.newHashMap();
//            temp.put("uuid", generator.externalId());
//            temp.put("entity_name", event.get("entity_name"));
//            temp.put("action_name", event.get("action_name"));
//            this.dataValue.add(temp);
//        }
//        this.nameValue = (String) hookObject.get("name");
//        this.activeValue = hookObject.get("is_active") != null && String.valueOf(hookObject.get("is_active")).equals("1");
//
//        selectQuery = new SelectQuery(MPermission.NAME);
//        selectQuery.addGroupBy(MPermission.Field.GROUPING);
//        selectQuery.addField("max(" + MPermission.Field.GROUPING + ") as id");
//        selectQuery.addField("max(" + MPermission.Field.GROUPING + ") as text");
//        this.groupingProvider = named.query(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
//
//        this.entityNameProvider = Lists.newArrayList();
//        this.actionNameProvider = Lists.newArrayList();
//
//        this.dataProvider = new ListDataProvider(this.dataValue);
//
//        this.dataColumn = Lists.newArrayList();
//        this.dataColumn.add(new TextColumn(Model.of("Entity Name"), "entity_name", "entity_name", this::dataColumn));
//        this.dataColumn.add(new TextColumn(Model.of("Action Name"), "action_name", "action_name", this::dataColumn));
//        this.dataColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::dataAction, this::dataClick));
//    }
//
//    @Override
//    protected void initComponent() {
//        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
//
//        this.form1 = new Form<>("form1");
//        add(this.form1);
//
//        this.addButton = new AjaxButton("addButton");
//        this.addButton.setOnSubmit(this::addButtonSubmit);
//        this.form1.add(this.addButton);
//
//        this.row1 = UIRow.newUIRow("row1", this.form1);
//
//        this.groupingBlock = this.row1.newUIBlock("groupingBlock", Size.Four_4);
//        this.groupingIContainer = this.groupingBlock.newUIContainer("groupingIContainer");
//        this.groupingField = new DropDownChoice<>("groupingField", new PropertyModel<>(this, "groupingValue"), new PropertyModel<>(this, "groupingProvider"), new OptionChoiceRenderer());
//        this.groupingIContainer.add(this.groupingField);
//        this.groupingIContainer.newFeedback("groupingFeedback", this.groupingField);
//
//        this.entityNameBlock = this.row1.newUIBlock("entityNameBlock", Size.Four_4);
//        this.entityNameIContainer = this.entityNameBlock.newUIContainer("entityNameIContainer");
//        this.entityNameField = new DropDownChoice<>("entityNameField", new PropertyModel<>(this, "entityNameValue"), new PropertyModel<>(this, "entityNameProvider"), new OptionChoiceRenderer());
//        this.entityNameIContainer.add(this.entityNameField);
//        this.entityNameIContainer.newFeedback("entityNameFeedback", this.entityNameField);
//
//        this.actionNameBlock = this.row1.newUIBlock("actionNameBlock", Size.Four_4);
//        this.actionNameIContainer = this.actionNameBlock.newUIContainer("actionNameIContainer");
//        this.actionNameField = new DropDownChoice<>("actionNameField", new PropertyModel<>(this, "actionNameValue"), new PropertyModel<>(this, "actionNameProvider"), new OptionChoiceRenderer());
//        this.actionNameIContainer.add(this.actionNameField);
//        this.actionNameIContainer.newFeedback("actionNameFeedback", this.actionNameField);
//
//        this.form2 = new Form<>("form2");
//        add(this.form2);
//
//        this.saveButton = new Button("saveButton");
//        this.saveButton.setOnSubmit(this::saveButtonSubmit);
//        this.form2.add(this.saveButton);
//
//        this.row2 = UIRow.newUIRow("row2", this.form2);
//
//        this.templateBlock = this.row2.newUIBlock("templateBlock", Size.Six_6);
//        this.templateVContainer = this.templateBlock.newUIContainer("templateVContainer");
//        this.templateView = new ReadOnlyView("templateView", new PropertyModel<>(this, "templateValue"));
//        this.templateVContainer.add(this.templateView);
//
//        this.nameBlock = this.row2.newUIBlock("nameBlock", Size.Six_6);
//        this.nameIContainer = this.nameBlock.newUIContainer("nameIContainer");
//        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
//        this.nameIContainer.add(this.nameField);
//        this.nameIContainer.newFeedback("nameFeedback", this.nameField);
//
//        this.row3 = UIRow.newUIRow("row3", this.form2);
//
//        this.activeBlock = this.row3.newUIBlock("activeBlock", Size.Twelve_12);
//        this.activeIContainer = this.activeBlock.newUIContainer("activeIContainer");
//        this.activeField = new CheckBox("activeField", new PropertyModel<>(this, "activeValue"));
//        this.activeIContainer.add(this.activeField);
//        this.activeIContainer.newFeedback("activeFeedback", this.activeField);
//
//        this.configField = new RepeatingView("configField");
//        this.form2.add(this.configField);
//        List<Map<String, Object>> temps = jdbcTemplate.queryForList("select " + MHookSchema.Field.FIELD_NAME + " from " + MHookSchema.NAME + " where " + MHookSchema.Field.HOOK_TEMPLATE_ID + " = ?", this.templateId);
//        for (Map<String, Object> temp : temps) {
//            String id = this.configField.newChildId();
//            HookFieldWidget field = new HookFieldWidget(id, (String) temp.get("field_name"), this.configValue);
//            this.configField.add(field);
//        }
//
//        this.row4 = UIRow.newUIRow("row4", this.form2);
//
//        this.dataBlock = this.row4.newUIBlock("dataBlock", Size.Twelve_12);
//        this.dataIContainer = this.dataBlock.newUIContainer("dataIContainer");
//        this.dataTable = new DataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
//        this.dataIContainer.add(this.dataTable);
//        this.dataTable.addTopToolbar(new HeadersToolbar<>(this.dataTable, this.dataProvider));
//        this.dataTable.addBottomToolbar(new NoRecordsToolbar(this.dataTable));
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.activeField.setRequired(true);
//
//        this.nameField.setRequired(true);
//
//        this.actionNameField.setRequired(true);
//
//        this.entityNameField.setRequired(true);
//        this.entityNameField.add(new OnChangeAjaxBehavior(this::entityNameFieldUpdate));
//
//        this.groupingField.setRequired(true);
//        this.groupingField.add(new OnChangeAjaxBehavior(this::groupingFieldUpdate));
//    }
//
//    protected boolean groupingFieldUpdate(AjaxRequestTarget target) {
//        if (this.groupingValue != null) {
//            JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
//
//            SelectQuery selectQuery = null;
//
//            selectQuery = new SelectQuery(MPermission.NAME);
//            selectQuery.addField("MAX(" + MPermission.Field.ENTITY_NAME + ") as id");
//            selectQuery.addField("MAX(" + MPermission.Field.ENTITY_NAME + ") as text");
//            selectQuery.addGroupBy(MPermission.Field.ENTITY_NAME);
//            selectQuery.addWhere(MPermission.Field.GROUPING + " = :" + MPermission.Field.GROUPING, this.groupingValue.getId());
//            this.entityNameProvider = named.query(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
//        } else {
//            if (this.entityNameProvider != null) {
//                this.entityNameProvider.clear();
//            }
//            if (this.actionNameProvider != null) {
//                this.actionNameProvider.clear();
//            }
//        }
//        target.add(this.form1);
//        return false;
//    }
//
//    protected boolean entityNameFieldUpdate(AjaxRequestTarget target) {
//        if (this.entityNameValue != null) {
//            JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
//
//            SelectQuery selectQuery = null;
//            selectQuery = new SelectQuery(MPermission.NAME);
//            selectQuery.addField("MAX(" + MPermission.Field.ACTION_NAME + ") as id");
//            selectQuery.addField("MAX(" + MPermission.Field.ACTION_NAME + ") as text");
//            selectQuery.addGroupBy(MPermission.Field.ACTION_NAME);
//            selectQuery.addWhere(MPermission.Field.ENTITY_NAME + " = :" + MPermission.Field.ENTITY_NAME, this.entityNameValue.getId());
//            this.actionNameProvider = named.query(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
//        } else {
//            if (this.actionNameProvider != null) {
//                this.actionNameProvider.clear();
//            }
//        }
//        target.add(this.form1);
//        return false;
//    }
//
//    protected void dataClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
//        int index = -1;
//        for (int i = 0; i < this.dataValue.size(); i++) {
//            Map<String, Object> column = this.dataValue.get(i);
//            if (model.get("uuid").equals(column.get("uuid"))) {
//                index = i;
//                break;
//            }
//        }
//        if (index >= 0) {
//            this.dataValue.remove(index);
//        }
//        target.add(this.dataTable);
//    }
//
//    protected List<ActionItem> dataAction(String s, Map<String, Object> model) {
//        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
//    }
//
//    protected void saveButtonSubmit(Button button) {
//        HookBuilder builder = new HookBuilder();
//        builder.withId(this.hookId);
//        builder.withDisplayName(this.nameValue);
//        builder.withName(this.templateValue);
//        builder.withActive(this.activeValue);
//        for (Map.Entry<String, String> config : this.configValue.entrySet()) {
//            builder.withConfig(config.getKey(), config.getValue());
//        }
//        for (Map<String, Object> event : this.dataValue) {
//            builder.withEvent((String) event.get("entity_name"), (String) event.get("action_name"));
//        }
//
//        JsonNode node = HookHelper.update((Session) getSession(), builder.build());
//
//        if (reportError(node)) {
//            return;
//        }
//        setResponsePage(HookBrowsePage.class);
//    }
//
//    protected boolean addButtonError(AjaxButton button, AjaxRequestTarget target) {
//        target.add(this.form1);
//        return false;
//    }
//
//    protected boolean addButtonSubmit(AjaxButton button, AjaxRequestTarget target) {
//        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
//        Map<String, Object> event = Maps.newHashMap();
//        event.put("uuid", generator.externalId());
//        if (this.entityNameValue != null) {
//            event.put("entity_name", this.entityNameValue.getId());
//        }
//        if (this.actionNameValue != null) {
//            event.put("action_name", this.actionNameValue.getId());
//        }
//        this.dataValue.add(event);
//        this.entityNameValue = null;
//        this.actionNameValue = null;
//        target.add(this.form1);
//        target.add(this.dataTable);
//        return false;
//    }
//
//    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
//        if ("entity_name".equals(column) || "action_name".equals(column)) {
//            String value = (String) model.get(column);
//            return new TextCell(value);
//        }
//        throw new WicketRuntimeException("Unknown " + column);
//    }
//}
