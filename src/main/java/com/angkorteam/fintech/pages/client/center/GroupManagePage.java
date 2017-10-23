package com.angkorteam.fintech.pages.client.center;

import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionFilterColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionItem;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemCss;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class GroupManagePage extends Page {

    protected String centerId;
    protected String officeId;

    protected DataTable<Map<String, Object>, String> associatedGroupTable;
    protected JdbcProvider associatedGroupProvider;

    protected String officeValue;
    protected Label officeView;

    private SingleChoiceProvider groupProvider;
    private Option groupValue;
    private Select2SingleChoice<Option> groupField;
    private TextFeedbackPanel groupFeedback;

    protected Form<Void> form;
    protected Button addButton;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        this.centerId = getPageParameters().get("centerId").toString();
        String centerValue = jdbcTemplate.queryForObject("SELECT display_name FROM m_group where id = ?", String.class, this.centerId);

        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Clients");
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Centers");
            breadcrumb.setPage(CenterBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageParameters parameters = new PageParameters();
            parameters.add("centerId", this.centerId);
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel(centerValue);
            breadcrumb.setParameters(parameters);
            breadcrumb.setPage(CenterPreviewPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Group Manage");
            BREADCRUMB.add(breadcrumb);
        }

        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        initData();

        this.associatedGroupProvider = new JdbcProvider("m_group");
        this.associatedGroupProvider.addJoin("left join m_office on m_group.office_id = m_office.id ");
        this.associatedGroupProvider.addJoin("LEFT JOIN r_enum_value ON  m_group.status_enum = r_enum_value.enum_id AND r_enum_value.enum_name = 'status_enum'");
        this.associatedGroupProvider.boardField("m_group.id", "id", Long.class);
        this.associatedGroupProvider.boardField("m_group.account_no", "account", String.class);
        this.associatedGroupProvider.boardField("m_group.display_name", "name", String.class);
        this.associatedGroupProvider.boardField("m_office.name", "office", String.class);
        this.associatedGroupProvider.boardField("m_group.external_id", "externalId", String.class);
        this.associatedGroupProvider.boardField("r_enum_value.enum_value", "status", String.class);
        this.associatedGroupProvider.applyWhere("office_id", "m_office.id = " + this.officeId);
        this.associatedGroupProvider.applyWhere("parent_id", "m_group.parent_id = " + this.centerId);
        this.associatedGroupProvider.applyWhere("level_id", "m_group.level_id = 2");

        this.associatedGroupProvider.selectField("id", Integer.class);

        List<IColumn<Map<String, Object>, String>> associatedGroupColumns = Lists.newArrayList();
        associatedGroupColumns.add(new TextFilterColumn(this.associatedGroupProvider, ItemClass.String, Model.of("Name"), "name", "name", this::associatedGroupNameColumn));
        associatedGroupColumns.add(new TextFilterColumn(this.associatedGroupProvider, ItemClass.String, Model.of("Account"), "account", "account", this::associatedGroupAccountColumn));
        associatedGroupColumns.add(new TextFilterColumn(this.associatedGroupProvider, ItemClass.String, Model.of("Status"), "status", "status", this::associatedGroupStatusColumn));
        associatedGroupColumns.add(new ActionFilterColumn<>(Model.of("Action"), this::associatedGroupActionItem, this::actionClick));

        this.associatedGroupTable = new DefaultDataTable<>("associatedGroupTable", associatedGroupColumns, this.associatedGroupProvider, 20);
        add(this.associatedGroupTable);

        this.form = new Form<>("form");
        add(this.form);

        this.addButton = new Button("addButton");
        this.addButton.setOnSubmit(this::addButtonSubmit);
        this.form.add(this.addButton);

        this.groupProvider = new SingleChoiceProvider("m_group", "id", "display_name");
        this.groupProvider.applyWhere("level_id", "level_id = 2");
        this.groupProvider.applyWhere("parent_id", "(parent_id is NULL or parent_id != " + this.centerId + ")");
        this.groupField = new Select2SingleChoice<>("groupField", 0, new PropertyModel<>(this, "groupValue"), this.groupProvider);
        this.groupField.setLabel(Model.of("Group"));
        this.groupField.add(new OnChangeAjaxBehavior());
        this.groupField.setRequired(true);
        this.form.add(this.groupField);
        this.groupFeedback = new TextFeedbackPanel("groupFeedback", this.groupField);
        this.form.add(this.groupFeedback);

        this.officeView = new Label("officeView", new PropertyModel<>(this, "officeValue"));
        this.form.add(this.officeView);

    }

    protected void initData() {
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        this.centerId = getPageParameters().get("centerId").toString();
        this.officeId = jdbcTemplate.queryForObject("select office_id from m_group where id = ?", String.class, this.centerId);
        this.officeValue = jdbcTemplate.queryForObject("select name from m_office where id = ?", String.class, this.officeId);
    }

    protected void addButtonSubmit(Button button) {
        PageParameters parameters = new PageParameters();
        parameters.add("centerId", this.centerId);
        setResponsePage(CenterPreviewPage.class, parameters);
    }

    protected void actionClick(String s, Map<String, Object> model, AjaxRequestTarget target) {

    }

    protected List<ActionItem> associatedGroupActionItem(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    protected ItemPanel associatedGroupNameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel associatedGroupAccountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel associatedGroupStatusColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

}
