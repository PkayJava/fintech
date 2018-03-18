package com.angkorteam.fintech.pages.role;

import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.MRole;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.helper.RoleHelper;
import com.angkorteam.fintech.pages.SystemDashboardPage;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.BadgeCell;
import com.angkorteam.fintech.table.LinkCell;
import com.angkorteam.fintech.table.TextCell;
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
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/**
 * Created by socheatkhauv on 6/26/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class RoleBrowsePage extends Page {

    protected WebMarkupBlock dataBlock;
    protected WebMarkupContainer dataIContainer;
    protected DataTable<Map<String, Object>, String> dataTable;
    protected JdbcProvider dataProvider;
    protected List<IColumn<Map<String, Object>, String>> dataColumn;
    protected FilterForm<Map<String, String>> dataFilterForm;

    protected BookmarkablePageLink<Void> createLink;

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
            breadcrumb.setLabel("Role");
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

        this.createLink = new BookmarkablePageLink<>("createLink", RoleCreatePage.class);
        add(this.createLink);
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
        this.dataProvider = new JdbcProvider(MRole.NAME);
        this.dataProvider.boardField(MRole.Field.ID, "id", Long.class);
        this.dataProvider.boardField(MRole.Field.DESCRIPTION, "description", String.class);
        this.dataProvider.boardField(MRole.Field.IS_DISABLED, "disabled", Boolean.class);
        this.dataProvider.boardField(MRole.Field.NAME, "name", String.class);

        this.dataColumn = Lists.newArrayList();
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Long, Model.of("ID"), "id", "id", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Name"), "name", "name", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Description"), "description", "description", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Boolean, Model.of("Is Disabled ?"), "disabled", "disabled", this::dataColumn));
        this.dataColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::dataAction, this::dataClick));

        this.dataFilterForm = new FilterForm<>("dataFilterForm", this.dataProvider);
        this.dataIContainer.add(this.dataFilterForm);

        this.dataTable = new DefaultDataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, this.dataFilterForm));
        this.dataFilterForm.add(this.dataTable);
    }

    protected void dataClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        Long value = (Long) model.get("id");
        if ("enable".equals(s)) {
            RoleHelper.enable((Session) getSession(), String.valueOf(value));
            target.add(this.dataTable);
        } else if ("disable".equals(s)) {
            RoleHelper.disable((Session) getSession(), String.valueOf(value));
            target.add(this.dataTable);
        } else if ("delete".equals(s)) {
            RoleHelper.delete((Session) getSession(), String.valueOf(value));
            target.add(this.dataTable);
        }
    }

    protected List<ActionItem> dataAction(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        Boolean disabled = (Boolean) model.get("disabled");
        if (disabled == null || disabled) {
            actions.add(new ActionItem("enable", Model.of("Enable"), ItemCss.PRIMARY));
        } else {
            actions.add(new ActionItem("disable", Model.of("Disable"), ItemCss.PRIMARY));
        }
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.WARNING));
        return actions;
    }

    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("id".equals(column)) {
            Long value = (Long) model.get(column);
            return new TextCell(value);
        } else if ("name".equals(column)) {
            String value = (String) model.get(column);
            if (Strings.isNullOrEmpty(value)) {
                return new TextCell(value);
            } else {
                PageParameters parameters = new PageParameters();
                parameters.add("roleId", model.get("id"));
                return new LinkCell(RolePermissionPage.class, parameters, value);
            }
        } else if ("description".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("disabled".equals(column)) {
            Boolean disabled = (Boolean) model.get(column);
            if (disabled == null || disabled) {
                return new BadgeCell(BadgeType.Danger, Model.of("Yes"));
            } else {
                return new BadgeCell(BadgeType.Success, Model.of("No"));
            }
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

}
