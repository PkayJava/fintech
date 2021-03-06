package com.angkorteam.fintech.pages.code;

import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.MCode;
import com.angkorteam.fintech.ddl.MCodeValue;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.CodeValueBuilder;
import com.angkorteam.fintech.helper.CodeHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.SystemDashboardPage;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.BadgeCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.BadgeType;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcNamed;
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
import com.google.common.collect.Lists;

import io.github.openunirest.http.JsonNode;

/**
 * Created by socheatkhauv on 6/27/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ValueBrowsePage extends Page {

    protected String codeId;
    protected String codeName;

    protected Form<Void> form1;
    protected Button addButton;

    protected UIRow row1;

    protected UIBlock nameBlock;
    protected UIContainer nameIContainer;
    protected String nameValue;
    protected TextField<String> nameField;

    protected UIBlock descriptionBlock;
    protected UIContainer descriptionIContainer;
    protected String descriptionValue;
    protected TextField<String> descriptionField;

    protected UIRow row2;

    protected UIBlock positionBlock;
    protected UIContainer positionIContainer;
    protected Long positionValue;
    protected TextField<Long> positionField;

    protected UIBlock activeBlock;
    protected UIContainer activeIContainer;
    protected Boolean activeValue;
    protected CheckBox activeField;

    protected FilterForm<Map<String, String>> form2;

    protected UIRow row3;

    protected UIBlock dataBlock;
    protected UIContainer dataIContainer;
    protected DataTable<Map<String, Object>, String> dataTable;
    protected JdbcProvider dataProvider;
    protected List<IColumn<Map<String, Object>, String>> dataColumn;

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
            breadcrumb.setLabel("Code");
            breadcrumb.setPage(CodeBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel(this.codeName);
            BREADCRUMB.add(breadcrumb);
        }

        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initData() {
        PageParameters parameters = getPageParameters();
        this.codeId = parameters.get("codeId").toString("");
        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
        SelectQuery selectQuery = null;
        selectQuery = new SelectQuery(MCode.NAME);
        selectQuery.addField(MCode.Field.CODE_NAME);
        selectQuery.addWhere(MCode.Field.ID + " = :" + MCode.Field.ID, this.codeId);
        this.codeName = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), String.class);

        this.dataProvider = new JdbcProvider(MCodeValue.NAME);
        this.dataProvider.boardField(MCodeValue.Field.ID, "id", Long.class);
        this.dataProvider.boardField(MCodeValue.Field.CODE_VALUE, "code_value", String.class);
        this.dataProvider.boardField(MCodeValue.Field.CODE_DESCRIPTION, "code_description", String.class);
        this.dataProvider.boardField(MCodeValue.Field.ORDER_POSITION, "order_position", Long.class);
        this.dataProvider.boardField(MCodeValue.Field.IS_ACTIVE, "active", Boolean.class);
        this.dataProvider.applyWhere("code", MCodeValue.Field.CODE_ID + " = " + this.codeId);
        this.dataProvider.setSort("code_value", SortOrder.ASCENDING);
        this.dataProvider.selectField("id", Long.class);

        this.dataColumn = Lists.newArrayList();
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Name"), "code_value", "code_value", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Description"), "code_description", "code_description", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Long, Model.of("Position"), "order_position", "order_position", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Boolean, Model.of("Is Active ?"), "active", "active", this::dataColumn));
        this.dataColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::dataAction, this::dataClick));
    }

    @Override
    protected void initComponent() {

        this.form1 = new Form<>("form1");
        add(this.form1);

        this.addButton = new Button("addButton");
        this.addButton.setOnSubmit(this::addButtonSubmit);
        this.form1.add(this.addButton);

        this.row1 = UIRow.newUIRow("row1", this.form1);

        this.nameBlock = this.row1.newUIBlock("nameBlock", Size.Six_6);
        this.nameIContainer = this.nameBlock.newUIContainer("nameIContainer");
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameIContainer.add(this.nameField);
        this.nameIContainer.newFeedback("nameFeedback", this.nameField);

        this.descriptionBlock = this.row1.newUIBlock("descriptionBlock", Size.Six_6);
        this.descriptionIContainer = this.descriptionBlock.newUIContainer("descriptionIContainer");
        this.descriptionField = new TextField<>("descriptionField", new PropertyModel<>(this, "descriptionValue"));
        this.descriptionIContainer.add(this.descriptionField);
        this.descriptionIContainer.newFeedback("descriptionFeedback", this.descriptionField);

        this.row2 = UIRow.newUIRow("row2", this.form1);

        this.positionBlock = this.row2.newUIBlock("positionBlock", Size.Six_6);
        this.positionIContainer = this.positionBlock.newUIContainer("positionIContainer");
        this.positionField = new TextField<>("positionField", new PropertyModel<>(this, "positionValue"));
        this.positionIContainer.add(this.positionField);
        this.positionIContainer.newFeedback("positionFeedback", this.positionField);

        this.activeBlock = this.row2.newUIBlock("activeBlock", Size.Six_6);
        this.activeIContainer = this.activeBlock.newUIContainer("activeIContainer");
        this.activeField = new CheckBox("activeField", new PropertyModel<>(this, "activeValue"));
        this.activeIContainer.add(this.activeField);
        this.activeIContainer.newFeedback("activeFeedback", this.activeField);

        this.form2 = new FilterForm<>("form2", this.dataProvider);
        add(this.form2);

        this.row3 = UIRow.newUIRow("row3", this.form2);

        this.dataBlock = this.row3.newUIBlock("dataBlock", Size.Twelve_12);
        this.dataIContainer = this.dataBlock.newUIContainer("dataIContainer");
        this.dataTable = new DefaultDataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, this.form2));
        this.dataIContainer.add(this.dataTable);
    }

    @Override
    protected void configureMetaData() {
        this.activeField.setRequired(true);

        this.positionField.setLabel(Model.of("Position"));
        this.positionField.setRequired(true);

        this.descriptionField.setLabel(Model.of("Description"));
        this.descriptionField.setRequired(true);

        this.nameField.setLabel(Model.of("Name"));
        this.nameField.setRequired(true);
    }

    protected void addButtonSubmit(Button button) {
        CodeValueBuilder builder = new CodeValueBuilder();
        builder.withCodeId(this.codeId);
        builder.withDescription(this.descriptionValue);
        builder.withName(this.nameValue);
        builder.withPosition(this.positionValue);
        builder.withActive(this.activeValue);

        JsonNode node = CodeHelper.createValue((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }

        PageParameters parameters = new PageParameters();
        parameters.add("codeId", this.codeId);

        setResponsePage(ValueBrowsePage.class, parameters);
    }

    protected void dataClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        Long id = (Long) model.get("id");
        if ("disable".equals(s)) {
            CodeValueBuilder builder = new CodeValueBuilder();
            builder.withId(String.valueOf(id));
            builder.withCodeId(this.codeId);
            builder.withActive(false);
            CodeHelper.updateValue((Session) getSession(), builder.build());
        } else if ("enable".equals(s)) {
            CodeValueBuilder builder = new CodeValueBuilder();
            builder.withId(String.valueOf(id));
            builder.withCodeId(this.codeId);
            builder.withActive(true);
            CodeHelper.updateValue((Session) getSession(), builder.build());
        }

        target.add(this.dataTable);
    }

    protected List<ActionItem> dataAction(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        Boolean active = (Boolean) model.get("active");
        if (active != null && active) {
            actions.add(new ActionItem("disable", Model.of("Disable"), ItemCss.INFO));
        } else {
            actions.add(new ActionItem("enable", Model.of("Enable"), ItemCss.INFO));
        }
        return actions;
    }

    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("code_value".equals(column) || "code_description".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("order_position".equals(column)) {
            Long value = (Long) model.get(column);
            return new TextCell(value);
        } else if ("active".equals(column)) {
            Boolean value = (Boolean) model.get(column);
            if (value != null && value) {
                return new BadgeCell(BadgeType.Success, Model.of("Yes"));
            } else {
                return new BadgeCell(BadgeType.Danger, Model.of("No"));
            }

        }
        throw new WicketRuntimeException("Unknown " + column);
    }

}
