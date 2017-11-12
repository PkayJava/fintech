package com.angkorteam.fintech.pages.group;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.helper.GroupHelper;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.LinkCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.*;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.exceptions.UnirestException;
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

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 6/22/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class GroupBrowsePage extends Page {

    protected WebMarkupBlock dataBlock;
    protected WebMarkupContainer dataIContainer;
    protected DataTable<Map<String, Object>, String> dataTable;
    protected JdbcProvider dataProvider;
    protected List<IColumn<Map<String, Object>, String>> dataColumn;
    protected FilterForm<Map<String, String>> dataFilterForm;

    protected BookmarkablePageLink<Void> hierarchyLink;

    protected BookmarkablePageLink<Void> createLink;

    @Override
    protected void initData() {
    }

    @Override
    protected void initComponent() {
        initDataBlock();

        this.hierarchyLink = new BookmarkablePageLink<>("hierarchyLink", GroupHierarchyPage.class);
        add(this.hierarchyLink);

        this.createLink = new BookmarkablePageLink<>("createLink", GroupCreatePage.class);
        add(this.createLink);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected void initDataBlock() {
        this.dataBlock = new WebMarkupBlock("dataBlock", WebMarkupBlock.Size.Twelve_12);
        add(this.dataBlock);
        this.dataIContainer = new WebMarkupContainer("dataIContainer");
        this.dataBlock.add(this.dataIContainer);
        this.dataProvider = new JdbcProvider("m_group g");
        this.dataProvider.addJoin("LEFT JOIN m_group parent ON g.parent_id = parent.id LEFT JOIN m_office office ON g.office_id = office.id");
        this.dataProvider.boardField("g.id", "id", Long.class);
        this.dataProvider.boardField("g.external_id", "external_id", String.class);
        this.dataProvider.boardField("parent.id", "parent_id", Long.class);
        this.dataProvider.boardField("g.display_name", "name", String.class);
        this.dataProvider.boardField("parent.display_name", "parent_name", String.class);
        this.dataProvider.boardField("office.name", "office", String.class);
        this.dataProvider.boardField("g.submittedon_date", "submittedon_date", Calendar.Date);

        this.dataProvider.selectField("parent_id", Long.class);

        this.dataColumn = Lists.newArrayList();
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Long, Model.of("ID"), "id", "id", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("External ID"), "external_id", "external_id", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Name"), "name", "name", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Parent Name"), "parent_name", "parent_name", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Office"), "office", "office", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Date, Model.of("Submitted On Date"), "submittedon_date", "submittedon_date", this::dataColumn));
        this.dataColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::dataAction, this::dataClick));

        this.dataFilterForm = new FilterForm<>("dataFilterForm", this.dataProvider);
        this.dataIContainer.add(this.dataFilterForm);

        this.dataTable = new DefaultDataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, this.dataFilterForm));
        this.dataFilterForm.add(this.dataTable);
    }

    protected void dataClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        Long id = (Long) model.get("id");
        try {
            GroupHelper.delete((Session) getSession(), String.valueOf(id));
        } catch (UnirestException e) {
        }
        target.add(this.dataTable);
    }

    protected List<ActionItem> dataAction(String s, Map<String, Object> model) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("id".equals(column)) {
            Long value = (Long) model.get(column);
            return new TextCell(value);
        } else if ("external_id".equals(column) || "office".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("name".equals(column)) {
            String value = (String) model.get(column);
            if (Strings.isNullOrEmpty(value)) {
                return new TextCell(value);
            } else {
                PageParameters parameters = new PageParameters();
                parameters.add("groupId", model.get("id"));
                return new LinkCell(GroupModifyPage.class, parameters, value);
            }
        } else if ("parent_name".equals(column)) {
            String value = (String) model.get(column);
            if (Strings.isNullOrEmpty(value)) {
                return new TextCell(value);
            } else {
                PageParameters parameters = new PageParameters();
                parameters.add("groupId", model.get("parent_id"));
                return new LinkCell(GroupModifyPage.class, parameters, value);
            }
        } else if ("submittedon_date".equals(column)) {
            Date value = (Date) model.get(column);
            return new TextCell(value, "dd/MM/yyyy");
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

}
