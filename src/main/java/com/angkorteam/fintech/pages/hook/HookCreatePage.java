package com.angkorteam.fintech.pages.hook;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.request.HookBuilder;
import com.angkorteam.fintech.helper.HookHelper;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.HookFieldWidget;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.spring.JdbcTemplate;
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
import com.angkorteam.framework.wicket.markup.html.form.select2.OptionMapper;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by socheatkhauv on 6/27/17.
 */
public class HookCreatePage extends Page {

    private List<Option> groupingProvider;
    private Option groupingValue;
    private DropDownChoice<Option> groupingField;
    private TextFeedbackPanel groupingFeedback;

    private List<Option> entityNameProvider;
    private Option entityNameValue;
    private DropDownChoice<Option> entityNameField;
    private TextFeedbackPanel entityNameFeedback;

    private List<Option> actionNameProvider;
    private Option actionNameValue;
    private DropDownChoice<Option> actionNameField;
    private TextFeedbackPanel actionNameFeedback;

    private Form<Void> eventForm;
    private AjaxButton addButton;

    private String templateId;
    private String templateValue;
    private Label templateField;

    private String nameValue;
    private TextField<String> nameField;
    private TextFeedbackPanel nameFeedback;

    private Boolean activeValue;
    private CheckBox activeField;
    private TextFeedbackPanel activeFeedback;

    private Map<String, String> configValue;
    private RepeatingView configField;

    private ListDataProvider provider;
    private DataTable<Map<String, Object>, String> dataTable;
    private List<Map<String, Object>> events;

    private Form<Void> hookForm;
    private Button createButton;

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.configValue = Maps.newHashMap();
        PageParameters parameters = getPageParameters();
        this.templateId = parameters.get("templateId").toString("");

        this.events = Lists.newArrayList();
        this.provider = new ListDataProvider(this.events);

        List<IColumn<Map<String, Object>, String>> columns = Lists.newArrayList();
        columns.add(new TextColumn(Model.of("Entity Name"), "entity_name", "entity_name", this::entityNameColumn));
        columns.add(new TextColumn(Model.of("Action Name"), "action_name", "action_name", this::actionNameColumn));
        columns.add(new ActionFilterColumn<>(Model.of("Action"), this::actionItem, this::actionClick));

        this.eventForm = new Form<>("eventForm");
        add(this.eventForm);

        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);

        this.groupingProvider = jdbcTemplate.query("select max(grouping) id, max(grouping) text from m_permission GROUP BY grouping", new OptionMapper());
        this.groupingField = new DropDownChoice<>("groupingField", new PropertyModel<>(this, "groupingValue"), new PropertyModel<>(this, "groupingProvider"), new OptionChoiceRenderer());
        this.groupingField.setRequired(true);
        this.groupingField.add(new OnChangeAjaxBehavior(this::groupingFieldUpdate));
        this.eventForm.add(this.groupingField);
        this.groupingFeedback = new TextFeedbackPanel("groupingFeedback", this.groupingField);
        this.eventForm.add(this.groupingFeedback);

        this.entityNameProvider = Lists.newArrayList();
        this.entityNameField = new DropDownChoice<>("entityNameField", new PropertyModel<>(this, "entityNameValue"), new PropertyModel<>(this, "entityNameProvider"), new OptionChoiceRenderer());
        this.entityNameField.setRequired(true);
        this.entityNameField.add(new OnChangeAjaxBehavior(this::entityNameFieldUpdate));
        this.eventForm.add(this.entityNameField);
        this.entityNameFeedback = new TextFeedbackPanel("entityNameFeedback", this.entityNameField);
        this.eventForm.add(this.entityNameFeedback);

        this.actionNameProvider = Lists.newArrayList();
        this.actionNameField = new DropDownChoice<>("actionNameField", new PropertyModel<>(this, "actionNameValue"), new PropertyModel<>(this, "actionNameProvider"), new OptionChoiceRenderer());
        this.actionNameField.setRequired(true);
        this.eventForm.add(this.actionNameField);
        this.actionNameFeedback = new TextFeedbackPanel("actionNameFeedback", this.actionNameField);
        this.eventForm.add(this.actionNameFeedback);

        this.addButton = new AjaxButton("addButton");
        this.addButton.setOnSubmit(this::addButtonSubmit);
        this.addButton.setOnError(this::addButtonError);
        this.eventForm.add(this.addButton);

        this.hookForm = new Form<>("hookForm");
        add(this.hookForm);

        this.createButton = new Button("createButton");
        this.createButton.setOnSubmit(this::createButtonSubmit);
        this.hookForm.add(this.createButton);

        this.dataTable = new DataTable<>("table", columns, this.provider, 20);
        this.hookForm.add(this.dataTable);
        this.dataTable.addTopToolbar(new HeadersToolbar<>(this.dataTable, this.provider));
        this.dataTable.addBottomToolbar(new NoRecordsToolbar(this.dataTable));

        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setRequired(true);
        this.hookForm.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.hookForm.add(this.nameFeedback);

        this.activeField = new CheckBox("activeField", new PropertyModel<>(this, "activeValue"));
        this.activeField.setRequired(true);
        this.hookForm.add(this.activeField);
        this.activeFeedback = new TextFeedbackPanel("activeFeedback", this.activeField);
        this.hookForm.add(this.activeFeedback);

        this.templateValue = jdbcTemplate.queryForObject("SELECT name from m_hook_templates where id = ?", String.class, this.templateId);
        this.templateField = new Label("templateField", new PropertyModel<>(this, "templateValue"));
        this.hookForm.add(this.templateField);

        this.configField = new RepeatingView("configField");
        this.hookForm.add(this.configField);
        List<Map<String, Object>> temps = jdbcTemplate.queryForList("select * from m_hook_schema where hook_template_id = ?", this.templateId);
        for (Map<String, Object> temp : temps) {
            String id = this.configField.newChildId();
            HookFieldWidget field = new HookFieldWidget(id, (String) temp.get("field_name"), this.configValue);
            this.configField.add(field);
        }
    }

    private void entityNameFieldUpdate(AjaxRequestTarget target) {
        if (this.entityNameValue != null) {
            JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
            this.actionNameProvider = jdbcTemplate.query("select max(action_name) id, max(action_name) text from m_permission WHERE  entity_name = ? GROUP BY action_name", new OptionMapper(), this.entityNameValue.getId());
        } else {
            if (this.actionNameProvider != null) {
                this.actionNameProvider.clear();
            }
        }
        target.add(this.eventForm);
    }

    private void groupingFieldUpdate(AjaxRequestTarget target) {
        if (this.groupingValue != null) {
            JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
            this.entityNameProvider = jdbcTemplate.query("select max(entity_name) id, max(entity_name) text from m_permission WHERE  grouping = ? GROUP BY entity_name", new OptionMapper(), this.groupingValue.getId());
        } else {
            if (this.entityNameProvider != null) {
                this.entityNameProvider.clear();
            }
            if (this.actionNameProvider != null) {
                this.actionNameProvider.clear();
            }
        }
        target.add(this.eventForm);
    }

    private void actionClick(String s, Map<String, Object> stringObjectMap, AjaxRequestTarget ajaxRequestTarget) {
        int index = -1;
        for (int i = 0; i < this.events.size(); i++) {
            Map<String, Object> column = this.events.get(i);
            if (stringObjectMap.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.events.remove(index);
        }
        ajaxRequestTarget.add(this.dataTable);
    }

    private List<ActionItem> actionItem(String s, Map<String, Object> stringObjectMap) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    private void createButtonSubmit(Button button) {
        HookBuilder builder = new HookBuilder();
        builder.withName(this.nameValue);
        builder.withTemplateId(this.templateValue);
        builder.withActive(this.activeValue);
        for (Map.Entry<String, String> config : this.configValue.entrySet()) {
            builder.withConfig(config.getKey(), config.getValue());
        }
        for (Map<String, Object> event : this.events) {
            builder.withEvent((String) event.get("entity_name"), (String) event.get("action_name"));
        }

        JsonNode node = null;
        try {
            node = HookHelper.create(builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(HookBrowsePage.class);
    }

    private void addButtonError(AjaxButton button, AjaxRequestTarget target) {
        target.add(this.eventForm);
    }

    private void addButtonSubmit(AjaxButton button, AjaxRequestTarget target) {
        Map<String, Object> event = Maps.newHashMap();
        event.put("uuid", UUID.randomUUID().toString());
        if (this.entityNameValue != null) {
            event.put("entity_name", this.entityNameValue.getId());
        }
        if (this.actionNameValue != null) {
            event.put("action_name", this.actionNameValue.getId());
        }
        this.events.add(event);
        this.entityNameValue = null;
        this.actionNameValue = null;
        target.add(this.eventForm);
        target.add(this.dataTable);
    }

    private ItemPanel entityNameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String entityName = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(entityName));
    }

    private ItemPanel actionNameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String actionName = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(actionName));
    }
}
