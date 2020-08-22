package com.angkorteam.fintech.pages.admin.system.hook;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.MifosDataContextManager;
import com.angkorteam.fintech.client.FineractClient;
import com.angkorteam.fintech.client.Function;
import com.angkorteam.fintech.client.dto.PutHookRequest;
import com.angkorteam.fintech.meta.tenant.*;
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.webui.frmk.common.Bookmark;
import com.angkorteam.webui.frmk.common.WicketFactory;
import com.angkorteam.webui.frmk.provider.ListDataProvider;
import com.angkorteam.webui.frmk.wicket.extensions.markup.html.repeater.data.table.AbstractDataTable;
import com.angkorteam.webui.frmk.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.webui.frmk.wicket.extensions.markup.html.repeater.data.table.filter.ActionFilteredColumn;
import com.angkorteam.webui.frmk.wicket.extensions.markup.html.repeater.data.table.filter.ActionItem;
import com.angkorteam.webui.frmk.wicket.extensions.markup.html.repeater.data.table.filter.Column;
import com.angkorteam.webui.frmk.wicket.extensions.markup.html.repeater.data.table.filter.ItemCss;
import com.angkorteam.webui.frmk.wicket.layout.Size;
import com.angkorteam.webui.frmk.wicket.layout.UIColumn;
import com.angkorteam.webui.frmk.wicket.layout.UIContainer;
import com.angkorteam.webui.frmk.wicket.layout.UIRow;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.query.FunctionType;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.springframework.context.ApplicationContext;

import java.util.*;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
@Bookmark("/admin/system/hook/modify")
public class HookModifyPage extends MasterPage {

    protected String hookId;

    protected String templateId;

    protected Form<Void> form1;

    protected UIRow row1;

    protected UIColumn groupingColumn;
    protected UIContainer groupingContainer;
    protected DropDownChoice<String> groupingField;
    protected List<String> groupingProvider;
    protected String groupingValue;

    protected UIColumn entityNameColumn;
    protected UIContainer entityNameContainer;
    protected DropDownChoice<String> entityNameField;
    protected List<String> entityNameProvider;
    protected String entityNameValue;

    protected UIColumn actionNameColumn;
    protected UIContainer actionNameContainer;
    protected DropDownChoice<String> actionNameField;
    protected List<String> actionNameProvider;
    protected String actionNameValue;

    protected AjaxButton addButton;

    protected Form<Void> form2;

    protected UIRow row2;

    protected UIColumn templateColumn;
    protected UIContainer templateContainer;
    protected TextField<String> templateField;
    protected String templateValue;

    protected UIColumn nameColumn;
    protected UIContainer nameContainer;
    protected TextField<String> nameField;
    protected String nameValue;

    protected UIColumn activeColumn;
    protected UIContainer activeContainer;
    protected DropDownChoice<String> activeField;
    protected String activeValue;

    // dynamic row

    protected Map<String, String> configValue;
    protected RepeatingView configField;

    protected UIRow row4;

    protected UIColumn configurationColumn;
    protected UIContainer configurationContainer;

    protected ListDataProvider configurationBrowseProvider;
    protected List<Map<String, Object>> configurationBrowseValue;
    protected List<IColumn<Map<String, Object>, String>> configurationBrowseColumn;
    protected AbstractDataTable<Map<String, Object>, String> configurationBrowseTable;

    protected Button updateButton;
    protected BookmarkablePageLink<Void> cancelButton;

    @Override
    protected void onInitData() {
        super.onInitData();
        this.hookId = getPageParameters().get("hookId").toString();

        ApplicationContext context = WicketFactory.getApplicationContext();
        StringGenerator generator = context.getBean(StringGenerator.class);
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
        MPermission mPermission = MPermission.staticInitialize(dataContext);
        MHookTemplate mHookTemplate = MHookTemplate.staticInitialize(dataContext);
        MHook mHook = MHook.staticInitialize(dataContext);
        MHookConfiguration mHookConfiguration = MHookConfiguration.staticInitialize(dataContext);
        MHookRegisteredEvent mHookRegisteredEvent = MHookRegisteredEvent.staticInitialize(dataContext);

        Map<String, Object> hookObject = null;
        try (DataSet rows = dataContext.query().from(mHook).selectAll().where(mHook.ID).eq(this.hookId).execute()) {
            rows.next();
            hookObject = new HashMap<>();
            for (org.apache.metamodel.schema.Column column : mHook.getColumns()) {
                hookObject.put(column.getName(), rows.getRow().getValue(column));
            }
        }

        Map<String, Object> templateObject = null;
        try (DataSet rows = dataContext.query().from(mHookTemplate).selectAll().where(mHookTemplate.ID).eq(hookObject.get(mHook.TEMPLATE_ID.getName())).execute()) {
            rows.next();
            templateObject = new HashMap<>();
            for (org.apache.metamodel.schema.Column column : mHookTemplate.getColumns()) {
                templateObject.put(column.getName(), rows.getRow().getValue(column));
            }
        }

        this.configValue = Maps.newHashMap();
        try (DataSet rows = dataContext.query().from(mHookConfiguration).selectAll().where(mHookConfiguration.HOOK_ID).eq(this.hookId).execute()) {
            while (rows.next()) {
                this.configValue.put((String) rows.getRow().getValue(mHookConfiguration.FIELD_NAME), (String) rows.getRow().getValue(mHookConfiguration.FIELD_VALUE));
            }
        }

        this.templateId = String.valueOf(templateObject.get(mHookTemplate.ID.getName()));
        this.templateValue = (String) templateObject.get(mHookTemplate.NAME.getName());

        this.configurationBrowseValue = Lists.newArrayList();
        try (DataSet rows = dataContext.query().from(mHookRegisteredEvent).selectAll().where(mHookRegisteredEvent.HOOK_ID).eq(this.hookId).execute()) {
            while (rows.next()) {
                Map<String, Object> temp = Maps.newHashMap();
                temp.put("uuid", generator.externalId());
                temp.put("entity_name", rows.getRow().getValue(mHookRegisteredEvent.ENTITY_NAME));
                temp.put("action_name", rows.getRow().getValue(mHookRegisteredEvent.ACTION_NAME));
                this.configurationBrowseValue.add(temp);
            }
        }
        this.nameValue = (String) hookObject.get(mHook.NAME.getName());
        int activeValue = hookObject.get(mHook.ACTIVE.getName()) == null ? 0 : (int) hookObject.get(mHook.ACTIVE.getName());
        this.activeValue = activeValue == 1 ? "Yes" : "No";

        try (DataSet rows = dataContext.query().from(mPermission).select(FunctionType.MAX, mPermission.GROUPING).groupBy(mPermission.GROUPING).execute()) {
            this.groupingProvider = new ArrayList<>();
            while (rows.next()) {
                this.groupingProvider.add((String) rows.getRow().getValue(0));
            }
        }

        this.entityNameProvider = Lists.newArrayList();
        this.actionNameProvider = Lists.newArrayList();

        this.configurationBrowseProvider = new ListDataProvider(this.configurationBrowseValue);

        this.configurationBrowseColumn = Lists.newArrayList();
        this.configurationBrowseColumn.add(Column.text(Model.of("Entity Name"), "entity_name", "entity_name", this.configurationBrowseProvider));
        this.configurationBrowseColumn.add(Column.text(Model.of("Action Name"), "action_name", "action_name", this.configurationBrowseProvider));
        this.configurationBrowseColumn.add(new ActionFilteredColumn<>(Model.of("Action"), this::configurationBrowseActionLink, this::configurationBrowseActionClick));
    }

    protected List<ActionItem> configurationBrowseActionLink(String link, Map<String, Object> model) {
        List<ActionItem> actions = new ArrayList<>();
        actions.add(new ActionItem("Delete", Model.of("Delete"), ItemCss.WARNING));
        return actions;
    }

    protected void configurationBrowseActionClick(String link, Map<String, Object> model, AjaxRequestTarget target) {
        int index = -1;
        for (int i = 0; i < this.configurationBrowseValue.size(); i++) {
            Map<String, Object> column = this.configurationBrowseValue.get(i);
            if (model.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.configurationBrowseValue.remove(index);
        }
        target.add(this.configurationBrowseTable);
    }

    protected void entityNameFieldChange(AjaxRequestTarget target) {
        if (this.entityNameValue != null) {
            ApplicationContext context = WicketFactory.getApplicationContext();
            MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
            DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
            MPermission mPermission = MPermission.staticInitialize(dataContext);
            try (DataSet rows = dataContext.query().from(mPermission).select(FunctionType.MAX, mPermission.ACTION_NAME).groupBy(mPermission.ACTION_NAME).where(mPermission.ENTITY_NAME).eq(this.entityNameValue).execute()) {
                this.actionNameProvider = new ArrayList<>();
                while (rows.next()) {
                    this.actionNameProvider.add((String) rows.getRow().getValue(0));
                }
            }
        } else {
            if (this.actionNameProvider != null) {
                this.actionNameProvider.clear();
            }
        }
        this.actionNameValue = null;
        target.add(this.actionNameColumn);
    }

    protected void groupingFieldChange(AjaxRequestTarget target) {
        if (this.groupingValue != null) {
            ApplicationContext context = WicketFactory.getApplicationContext();
            MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
            DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
            MPermission mPermission = MPermission.staticInitialize(dataContext);
            try (DataSet rows = dataContext.query().from(mPermission).select(FunctionType.MAX, mPermission.ENTITY_NAME).groupBy(mPermission.ENTITY_NAME).where(mPermission.GROUPING).eq(this.groupingValue).execute()) {
                this.entityNameProvider = new ArrayList<>();
                while (rows.next()) {
                    this.entityNameProvider.add((String) rows.getRow().getValue(0));
                }
            }
        } else {
            if (this.entityNameProvider != null) {
                this.entityNameProvider.clear();
            }
        }
        if (this.actionNameProvider != null) {
            this.actionNameProvider.clear();
        }
        this.entityNameValue = null;
        this.actionNameValue = null;
        target.add(this.entityNameColumn);
        target.add(this.actionNameColumn);
    }

    @Override
    protected void onInitHtml(MarkupContainer body) {
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
        MHookSchema mHookSchema = MHookSchema.staticInitialize(dataContext);

        this.form1 = new Form<>("form1");
        body.add(this.form1);

        this.row1 = UIRow.newUIRow("row1", this.form1);

        this.groupingColumn = this.row1.newUIColumn("groupingColumn", Size.Four_4);
        this.groupingContainer = this.groupingColumn.newUIContainer("groupingContainer");
        this.groupingField = new DropDownChoice<>("groupingField", new PropertyModel<>(this, "groupingValue"), new PropertyModel<>(this, "groupingProvider"));
        this.groupingField.setRequired(true);
        this.groupingField.add(OnChangeAjaxBehavior.onChange(this::groupingFieldChange));
        this.groupingContainer.add(this.groupingField);
        this.groupingContainer.newFeedback("groupingFeedback", this.groupingField);

        this.entityNameColumn = this.row1.newUIColumn("entityNameColumn", Size.Four_4);
        this.entityNameContainer = this.entityNameColumn.newUIContainer("entityNameContainer");
        this.entityNameField = new DropDownChoice<>("entityNameField", new PropertyModel<>(this, "entityNameValue"), new PropertyModel<>(this, "entityNameProvider"));
        this.entityNameField.setRequired(true);
        this.entityNameField.add(OnChangeAjaxBehavior.onChange(this::entityNameFieldChange));
        this.entityNameContainer.add(this.entityNameField);
        this.entityNameContainer.newFeedback("entityNameFeedback", this.entityNameField);

        this.actionNameColumn = this.row1.newUIColumn("actionNameColumn", Size.Four_4);
        this.actionNameContainer = this.actionNameColumn.newUIContainer("actionNameContainer");
        this.actionNameField = new DropDownChoice<>("actionNameField", new PropertyModel<>(this, "actionNameValue"), new PropertyModel<>(this, "actionNameProvider"));
        this.actionNameField.setRequired(true);
        this.actionNameContainer.add(this.actionNameField);
        this.actionNameContainer.newFeedback("actionNameFeedback", this.actionNameField);

        this.row1.newUIColumn("lastColumn");

        this.addButton = new AjaxButton("addButton") {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                addButtonSubmit(target);
            }

            @Override
            protected void onError(AjaxRequestTarget target) {
                addButtonError(target);
            }
        };
        this.form1.add(this.addButton);

        this.form2 = new Form<>("form2");
        body.add(this.form2);

        this.row2 = UIRow.newUIRow("row2", this.form2);

        this.templateColumn = this.row2.newUIColumn("templateColumn", Size.Four_4);
        this.templateContainer = this.templateColumn.newUIContainer("templateContainer");
        this.templateField = new TextField<>("templateField", new PropertyModel<>(this, "templateValue"));
        this.templateField.setEnabled(false);
        this.templateContainer.add(this.templateField);
        this.templateContainer.newFeedback("templateFeedback", this.templateField);

        this.nameColumn = this.row2.newUIColumn("nameColumn", Size.Four_4);
        this.nameContainer = this.nameColumn.newUIContainer("nameContainer");
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setRequired(true);
        this.nameContainer.add(this.nameField);
        this.nameContainer.newFeedback("nameFeedback", this.nameField);

        this.activeColumn = this.row2.newUIColumn("activeColumn", Size.Four_4);
        this.activeContainer = this.activeColumn.newUIContainer("activeContainer");
        this.activeField = new DropDownChoice<>("activeField", new PropertyModel<>(this, "activeValue"), Arrays.asList("Yes", "No"));
        this.activeField.setRequired(true);
        this.activeContainer.add(this.activeField);
        this.activeContainer.newFeedback("activeFeedback", this.activeField);

        this.row2.newUIColumn("lastColumn");

        this.configField = new RepeatingView("configField");
        this.form2.add(this.configField);
        try (DataSet rows = dataContext.query().from(mHookSchema).select(mHookSchema.FIELD_NAME).where(mHookSchema.HOOK_TEMPLATE_ID).eq(this.templateId).execute()) {
            while (rows.next()) {
                String id = this.configField.newChildId();
                HookFieldRow field = new HookFieldRow(id, (String) rows.getRow().getValue(0), this.configValue);
                this.configField.add(field);
            }
        }

        this.row4 = UIRow.newUIRow("row4", this.form2);

        this.configurationColumn = this.row4.newUIColumn("configurationColumn", Size.Twelve_12);
        this.configurationContainer = this.configurationColumn.newUIContainer("configurationContainer");
        this.configurationBrowseTable = new DataTable<>("configurationBrowseTable", this.configurationBrowseColumn, this.configurationBrowseProvider, 20);
        this.configurationContainer.add(this.configurationBrowseTable);

        this.row4.newUIColumn("lastColumn");

        this.updateButton = new Button("updateButton") {
            @Override
            public void onSubmit() {
                updateButtonSubmit();
            }
        };
        this.form2.add(this.updateButton);

        this.cancelButton = new BookmarkablePageLink<>("cancelButton", HookBrowsePage.class);
        this.form2.add(this.cancelButton);
    }

    protected void addButtonError(AjaxRequestTarget target) {
        target.add(this.form1);
    }

    protected void addButtonSubmit(AjaxRequestTarget target) {
        ApplicationContext context = WicketFactory.getApplicationContext();
        StringGenerator generator = context.getBean(StringGenerator.class);
        Map<String, Object> event = Maps.newHashMap();
        event.put("uuid", generator.externalId());
        if (this.entityNameValue != null) {
            event.put("entity_name", this.entityNameValue);
        }
        if (this.actionNameValue != null) {
            event.put("action_name", this.actionNameValue);
        }
        this.configurationBrowseValue.add(event);
        this.entityNameValue = null;
        this.actionNameValue = null;
        target.add(this.form1);
        target.add(this.configurationBrowseTable);
    }

    protected void updateButtonSubmit() {
        ApplicationContext context = WicketFactory.getApplicationContext();
        FineractClient client = context.getBean(FineractClient.class);

        PutHookRequest request = new PutHookRequest();
        request.setName(this.templateValue);
        request.setDisplayName(this.nameValue);
        request.setActive(true);
        for (Map.Entry<String, String> config : this.configValue.entrySet()) {
            request.getConfig().put(config.getKey(), config.getValue());
        }
        for (Map<String, Object> event : this.configurationBrowseValue) {
            request.getEvents().add(new PutHookRequest.Event((String) event.get("entity_name"), (String) event.get("action_name")));
        }
        client.hookUpdate(getSession().getIdentifier(), getSession().getToken(), Long.valueOf(this.hookId), request);

        setResponsePage(HookBrowsePage.class);
    }

}
