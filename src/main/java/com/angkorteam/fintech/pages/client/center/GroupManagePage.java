package com.angkorteam.fintech.pages.client.center;

import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import com.angkorteam.fintech.widget.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.ddl.MGroup;
import com.angkorteam.fintech.ddl.MOffice;
import com.angkorteam.fintech.ddl.REnumValue;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcNamed;
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

    protected String centerDisplayName;

    protected DataTable<Map<String, Object>, String> associatedGroupTable;
    protected JdbcProvider associatedGroupProvider;
    protected List<IColumn<Map<String, Object>, String>> associatedGroupColumn;

    protected WebMarkupBlock officeBlock;
    protected WebMarkupContainer officeVContainer;
    protected String officeValue;
    protected ReadOnlyView officeView;

    protected WebMarkupBlock groupBlock;
    protected WebMarkupContainer groupIContainer;
    protected SingleChoiceProvider groupProvider;
    protected Option groupValue;
    protected Select2SingleChoice<Option> groupField;
    protected TextFeedbackPanel groupFeedback;

    protected Form<Void> form;
    protected Button addButton;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
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
            breadcrumb.setLabel(this.centerDisplayName);
            breadcrumb.setPage(CenterPreviewPage.class);
            breadcrumb.setParameters(parameters);
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
    protected void initComponent() {
        this.associatedGroupProvider = new JdbcProvider(MGroup.NAME);
        this.associatedGroupProvider.applyJoin("m_office", "LEFT JOIN " + MOffice.NAME + " ON " + MGroup.NAME + "." + MGroup.Field.OFFICE_ID + " = " + MOffice.NAME + "." + MOffice.Field.ID);
        this.associatedGroupProvider.applyJoin("r_enum_value", "LEFT JOIN " + REnumValue.NAME + " ON " + MGroup.NAME + "." + MGroup.Field.STATUS_ENUM + " = " + REnumValue.NAME + "." + REnumValue.Field.ENUM_ID + " AND " + REnumValue.NAME + "." + REnumValue.Field.ENUM_NAME + " = 'status_enum'");
        this.associatedGroupProvider.boardField(MGroup.NAME + "." + MGroup.Field.ID, "id", Long.class);
        this.associatedGroupProvider.boardField(MGroup.NAME + "." + MGroup.Field.ACCOUNT_NO, "account", String.class);
        this.associatedGroupProvider.boardField(MGroup.NAME + "." + MGroup.Field.DISPLAY_NAME, "name", String.class);
        this.associatedGroupProvider.boardField(MOffice.NAME + "." + MOffice.Field.NAME, "office", String.class);
        this.associatedGroupProvider.boardField(MGroup.NAME + "." + MGroup.Field.EXTERNAL_ID, "externalId", String.class);
        this.associatedGroupProvider.boardField(REnumValue.NAME + "." + REnumValue.Field.ENUM_VALUE, "status", String.class);
        this.associatedGroupProvider.applyWhere("office_id", MOffice.NAME + "." + MOffice.Field.ID + " = " + this.officeId);
        this.associatedGroupProvider.applyWhere("parent_id", MGroup.NAME + "." + MGroup.Field.PARENT_ID + " = " + this.centerId);
        this.associatedGroupProvider.applyWhere("level_id", MGroup.NAME + "." + MGroup.Field.LEVEL_ID + " = 2");

        this.associatedGroupProvider.selectField("id", Long.class);

        this.associatedGroupColumn = Lists.newArrayList();
        this.associatedGroupColumn.add(new TextFilterColumn(this.associatedGroupProvider, ItemClass.String, Model.of("Name"), "name", "name", this::associatedGroupColumn));
        this.associatedGroupColumn.add(new TextFilterColumn(this.associatedGroupProvider, ItemClass.String, Model.of("Account"), "account", "account", this::associatedGroupColumn));
        this.associatedGroupColumn.add(new TextFilterColumn(this.associatedGroupProvider, ItemClass.String, Model.of("Status"), "status", "status", this::associatedGroupColumn));
        this.associatedGroupColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::associatedGroupAction, this::associatedGroupClick));

        this.associatedGroupTable = new DefaultDataTable<>("associatedGroupTable", this.associatedGroupColumn, this.associatedGroupProvider, 20);
        add(this.associatedGroupTable);

        this.form = new Form<>("form");
        add(this.form);

        this.addButton = new Button("addButton");
        this.addButton.setOnSubmit(this::addButtonSubmit);
        this.form.add(this.addButton);

        initOfficeBlock();

        initGroupBlock();
    }

    @Override
    protected void configureMetaData() {
    }

    protected void initGroupBlock() {
        this.groupBlock = new WebMarkupBlock("groupBlock", Size.Six_6);
        this.form.add(this.groupBlock);
        this.groupIContainer = new WebMarkupContainer("groupIContainer");
        this.groupBlock.add(this.groupIContainer);
        this.groupProvider = new SingleChoiceProvider(MGroup.NAME, MGroup.Field.ID, MGroup.Field.DISPLAY_NAME);
        this.groupProvider.applyWhere("level_id", MGroup.Field.LEVEL_ID + " = 2");
        this.groupProvider.applyWhere("parent_id", "(" + MGroup.Field.PARENT_ID + " IS NULL OR " + MGroup.Field.PARENT_ID + " != " + this.centerId + ")");
        this.groupField = new Select2SingleChoice<>("groupField", new PropertyModel<>(this, "groupValue"), this.groupProvider);
        this.groupField.setLabel(Model.of("Group"));
        this.groupField.add(new OnChangeAjaxBehavior());
        this.groupField.setRequired(true);
        this.groupIContainer.add(this.groupField);
        this.groupFeedback = new TextFeedbackPanel("groupFeedback", this.groupField);
        this.groupIContainer.add(this.groupFeedback);
    }

    protected void initOfficeBlock() {
        this.officeBlock = new WebMarkupBlock("officeBlock", Size.Six_6);
        this.form.add(this.officeBlock);
        this.officeVContainer = new WebMarkupContainer("officeVContainer");
        this.officeBlock.add(this.officeVContainer);
        this.officeView = new ReadOnlyView("officeView", new PropertyModel<>(this, "officeValue"));
        this.officeVContainer.add(this.officeView);
    }

    @Override
    protected void initData() {
        this.centerId = getPageParameters().get("centerId").toString();

        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery(MGroup.NAME);
        selectQuery.addField(MGroup.Field.OFFICE_ID);
        selectQuery.addField(MGroup.Field.DISPLAY_NAME);
        selectQuery.addWhere(MGroup.Field.ID + " = :" + MGroup.Field.ID, this.centerId);
        Map<String, Object> centerObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        this.officeId = String.valueOf(centerObject.get("office_id"));
        this.centerDisplayName = (String) centerObject.get("display_name");

        selectQuery = new SelectQuery(MOffice.NAME);
        selectQuery.addField(MOffice.Field.NAME);
        selectQuery.addWhere(MOffice.Field.ID + " = :" + MOffice.Field.ID, this.officeId);
        this.officeValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), String.class);
    }

    protected void addButtonSubmit(Button button) {
        PageParameters parameters = new PageParameters();
        parameters.add("centerId", this.centerId);
        setResponsePage(CenterPreviewPage.class, parameters);
    }

    protected void associatedGroupClick(String s, Map<String, Object> model, AjaxRequestTarget target) {

    }

    protected List<ActionItem> associatedGroupAction(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    protected ItemPanel associatedGroupColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("name".equals(column) || "account".equals(column) || "status".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

}
