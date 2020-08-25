//package com.angkorteam.fintech.pages.entity;
//
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.wicket.WicketRuntimeException;
//import org.apache.wicket.ajax.AjaxRequestTarget;
//import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
//import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
//import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.IModel;
//import org.apache.wicket.model.Model;
//
//import com.angkorteam.fintech.Page;
//import com.angkorteam.fintech.Session;
//import com.angkorteam.fintech.ddl.MEntityDataTableCheck;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.dto.enums.EntityStatus;
//import com.angkorteam.fintech.dto.enums.EntityType;
//import com.angkorteam.fintech.helper.EntityCheckHelper;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.pages.OrganizationDashboardPage;
//import com.angkorteam.fintech.provider.JdbcProvider;
//import com.angkorteam.fintech.table.BadgeCell;
//import com.angkorteam.fintech.table.TextCell;
//import com.angkorteam.framework.BadgeType;
//import com.angkorteam.framework.models.PageBreadcrumb;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionFilterColumn;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionItem;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemCss;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
//import com.google.common.collect.Lists;
//
//import io.github.openunirest.http.JsonNode;
//
///**
// * Created by socheatkhauv on 7/15/17.
// */
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class CheckerBrowsePage extends Page {
//
//    protected FilterForm<Map<String, String>> form;
//
//    protected UIRow row1;
//
//    protected UIBlock dataBlock;
//    protected UIContainer dataIContainer;
//    protected DataTable<Map<String, Object>, String> dataTable;
//    protected JdbcProvider dataProvider;
//    protected List<IColumn<Map<String, Object>, String>> dataColumn;
//
//    protected BookmarkablePageLink<Void> createLink;
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
//            breadcrumb.setLabel("Organization");
//            breadcrumb.setPage(OrganizationDashboardPage.class);
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Entity Data Table Check");
//            BREADCRUMB.add(breadcrumb);
//        }
//        return Model.ofList(BREADCRUMB);
//    }
//
//    @Override
//    protected void initData() {
//        List<String> entity = Lists.newArrayList();
//        for (EntityType t : EntityType.values()) {
//            entity.add("WHEN '" + t.getLiteral() + "' THEN '" + t.getDescription() + "'");
//        }
//        entity.add("ELSE 'N/A'");
//        List<String> status = Lists.newArrayList();
//        for (EntityStatus t : EntityStatus.values()) {
//            status.add("WHEN " + t.getLiteral() + " THEN '" + t.getDescription() + "'");
//        }
//        status.add("ELSE 'N/A'");
//        this.dataProvider = new JdbcProvider(MEntityDataTableCheck.NAME);
//        this.dataProvider.boardField(MEntityDataTableCheck.Field.ID, "id", Long.class);
//        this.dataProvider.boardField("case " + MEntityDataTableCheck.Field.APPLICATION_TABLE_NAME + " " + StringUtils.join(entity, " ") + " end", "entity", String.class);
//        this.dataProvider.boardField("case " + MEntityDataTableCheck.Field.SYSTEM_DEFINED + " when 0 then 'No' else 'Yes' end", "system", String.class);
//        this.dataProvider.boardField("case " + MEntityDataTableCheck.Field.STATUS_ENUM + " " + StringUtils.join(status, " ") + " end", "status", String.class);
//        this.dataProvider.boardField(MEntityDataTableCheck.Field.X_REGISTERED_TABLE_NAME, "dataTable", String.class);
//        this.dataProvider.boardField(MEntityDataTableCheck.Field.PRODUCT_ID, "product", Long.class);
//        this.dataProvider.selectField("id", Long.class);
//
//        this.dataColumn = Lists.newArrayList();
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Entity"), "entity", "entity", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Long, Model.of("Product"), "product", "account", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Data Table"), "dataTable", "dataTable", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Status"), "status", "status", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("System"), "system", "system", this::dataColumn));
//        this.dataColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::dataAction, this::dataClick));
//    }
//
//    @Override
//    protected void initComponent() {
//        this.form = new FilterForm<>("form", this.dataProvider);
//        add(this.form);
//
//        this.row1 = UIRow.newUIRow("row1", this.form);
//
//        this.dataBlock = this.row1.newUIBlock("dataBlock", Size.Twelve_12);
//        this.dataIContainer = this.dataBlock.newUIContainer("dataIContainer");
//        this.dataTable = new DefaultDataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
//        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, this.form));
//        this.dataIContainer.add(this.dataTable);
//
//        this.createLink = new BookmarkablePageLink<>("createLink", CheckerCreatePage.class);
//        add(this.createLink);
//    }
//
//    @Override
//    protected void configureMetaData() {
//    }
//
//    protected void dataClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
//        Long id = (Long) model.get("id");
//        JsonNode node = EntityCheckHelper.delete((Session) getSession(), String.valueOf(id));
//
//        reportError(node, target);
//        target.add(this.dataTable);
//    }
//
//    protected List<ActionItem> dataAction(String s, Map<String, Object> model) {
//        List<ActionItem> actions = Lists.newArrayList();
//        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
//        return actions;
//    }
//
//    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
//        if ("entity".equals(column) || "dataTable".equals(column) || "status".equals(column)) {
//            String value = (String) model.get(column);
//            return new TextCell(value);
//        } else if ("account".equals(column)) {
//            Long value = (Long) model.get(column);
//            return new TextCell(value);
//        } else if ("system".equals(column)) {
//            String value = (String) model.get(column);
//            if ("Yes".equals(value)) {
//                return new BadgeCell(BadgeType.Danger, Model.of("Yes"));
//            } else if ("No".equals(value)) {
//                return new BadgeCell(BadgeType.Success, Model.of("No"));
//            } else {
//                return new TextCell(value);
//            }
//        }
//        throw new WicketRuntimeException("Unknown " + column);
//    }
//
//}
