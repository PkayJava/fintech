package com.angkorteam.fintech.pages.code;

import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.CodeValueBuilder;
import com.angkorteam.fintech.helper.CodeHelper;
import com.angkorteam.fintech.pages.SystemDashboardPage;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.BadgeCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.BadgeType;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcTemplate;
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
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/27/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ValueBrowsePage extends Page {

    protected String codeId;

    protected WebMarkupBlock dataBlock;
    protected WebMarkupContainer dataIContainer;
    protected DataTable<Map<String, Object>, String> dataTable;
    protected JdbcProvider dataProvider;
    protected List<IColumn<Map<String, Object>, String>> dataColumn;
    protected FilterForm<Map<String, String>> dataFilterForm;

    protected WebMarkupBlock nameBlock;
    protected WebMarkupContainer nameIContainer;
    protected String nameValue;
    protected TextField<String> nameField;
    protected TextFeedbackPanel nameFeedback;

    protected WebMarkupBlock descriptionBlock;
    protected WebMarkupContainer descriptionIContainer;
    protected String descriptionValue;
    protected TextField<String> descriptionField;
    protected TextFeedbackPanel descriptionFeedback;

    protected WebMarkupBlock positionBlock;
    protected WebMarkupContainer positionIContainer;
    protected Long positionValue;
    protected TextField<Long> positionField;
    protected TextFeedbackPanel positionFeedback;

    protected WebMarkupBlock activeBlock;
    protected WebMarkupContainer activeIContainer;
    protected Boolean activeValue;
    protected CheckBox activeField;
    protected TextFeedbackPanel activeFeedback;

    protected Form<Void> form;
    protected Button addButton;

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
            JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
            String codeName = jdbcTemplate.queryForObject("select code_name from m_code where id = ?", String.class, this.codeId);
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel(codeName);
            BREADCRUMB.add(breadcrumb);
        }

        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initData() {
        PageParameters parameters = getPageParameters();
        this.codeId = parameters.get("codeId").toString("");
    }

    @Override
    protected void initComponent() {

        initDataBlock();

        this.form = new Form<>("form");
        add(this.form);

        this.addButton = new Button("addButton");
        this.addButton.setOnSubmit(this::addButtonSubmit);
        this.form.add(this.addButton);

        initNameBlock();

        initDescriptionBlock();

        initPositionBlock();

        initActiveBlock();
    }

    @Override
    protected void configureRequiredValidation() {

    }

    @Override
    protected void configureMetaData() {

    }

    protected void initDataBlock() {
        this.dataBlock = new WebMarkupBlock("dataBlock", Size.Twelve_12);
        add(this.dataBlock);
        this.dataIContainer = new WebMarkupContainer("dataIContainer");
        this.dataBlock.add(this.dataIContainer);
        this.dataProvider = new JdbcProvider("m_code_value");
        this.dataProvider.applyWhere("code", "code_id = " + this.codeId);
        this.dataProvider.boardField("id", "id", Long.class);
        this.dataProvider.boardField("code_value", "code_value", String.class);
        this.dataProvider.boardField("code_description", "code_description", String.class);
        this.dataProvider.boardField("order_position", "order_position", Long.class);
        this.dataProvider.boardField("is_active", "active", Boolean.class);

        this.dataProvider.setSort("code_value", SortOrder.ASCENDING);

        this.dataProvider.selectField("id", Long.class);

        this.dataColumn = Lists.newArrayList();
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Name"), "code_value", "code_value", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Description"), "code_description", "code_description", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Long, Model.of("Position"), "order_position", "order_position", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Boolean, Model.of("Is Active ?"), "active", "active", this::dataColumn));
        this.dataColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::dataAction, this::dataClick));

        this.dataFilterForm = new FilterForm<>("dataFilterForm", this.dataProvider);
        this.dataIContainer.add(this.dataFilterForm);

        this.dataTable = new DefaultDataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, this.dataFilterForm));
        this.dataFilterForm.add(this.dataTable);
    }

    protected void initActiveBlock() {
        this.activeBlock = new WebMarkupBlock("activeBlock", Size.Six_6);
        this.form.add(this.activeBlock);
        this.activeIContainer = new WebMarkupContainer("activeIContainer");
        this.activeBlock.add(this.activeIContainer);
        this.activeField = new CheckBox("activeField", new PropertyModel<>(this, "activeValue"));
        this.activeField.setRequired(true);
        this.activeIContainer.add(this.activeField);
        this.activeFeedback = new TextFeedbackPanel("activeFeedback", this.activeField);
        this.activeIContainer.add(this.activeFeedback);
    }

    protected void initPositionBlock() {
        this.positionBlock = new WebMarkupBlock("positionBlock", Size.Six_6);
        this.form.add(this.positionBlock);
        this.positionIContainer = new WebMarkupContainer("positionIContainer");
        this.positionBlock.add(this.positionIContainer);
        this.positionField = new TextField<>("positionField", new PropertyModel<>(this, "positionValue"));
        this.positionField.setLabel(Model.of("Position"));
        this.positionField.setRequired(true);
        this.positionIContainer.add(this.positionField);
        this.positionFeedback = new TextFeedbackPanel("positionFeedback", this.positionField);
        this.positionIContainer.add(this.positionFeedback);
    }

    protected void initDescriptionBlock() {
        this.descriptionBlock = new WebMarkupBlock("descriptionBlock", Size.Six_6);
        this.form.add(this.descriptionBlock);
        this.descriptionIContainer = new WebMarkupContainer("descriptionIContainer");
        this.descriptionBlock.add(this.descriptionIContainer);
        this.descriptionField = new TextField<>("descriptionField", new PropertyModel<>(this, "descriptionValue"));
        this.descriptionField.setLabel(Model.of("Description"));
        this.descriptionField.setRequired(true);
        this.descriptionIContainer.add(this.descriptionField);
        this.descriptionFeedback = new TextFeedbackPanel("descriptionFeedback", this.descriptionField);
        this.descriptionIContainer.add(this.descriptionFeedback);
    }

    protected void initNameBlock() {
        this.nameBlock = new WebMarkupBlock("nameBlock", Size.Six_6);
        this.form.add(this.nameBlock);
        this.nameIContainer = new WebMarkupContainer("nameIContainer");
        this.nameBlock.add(this.nameIContainer);
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setLabel(Model.of("Name"));
        this.nameField.setRequired(true);
        this.nameIContainer.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.nameIContainer.add(this.nameFeedback);
    }

    protected void addButtonSubmit(Button button) {
        CodeValueBuilder builder = new CodeValueBuilder();
        builder.withCodeId(this.codeId);
        builder.withDescription(this.descriptionValue);
        builder.withName(this.nameValue);
        builder.withPosition(this.positionValue);
        builder.withActive(this.activeValue);

        JsonNode node = null;
        try {
            node = CodeHelper.createValue((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }

        PageParameters parameters = new PageParameters();
        parameters.add("codeId", this.codeId);

        setResponsePage(ValueBrowsePage.class, parameters);
    }

    protected void dataClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        Long id = (Long) model.get("id");
        try {
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
        } catch (UnirestException e) {
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
