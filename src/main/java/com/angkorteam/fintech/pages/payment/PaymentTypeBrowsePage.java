package com.angkorteam.fintech.pages.payment;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.helper.PaymentTypeHelper;
import com.angkorteam.fintech.table.BadgeCell;
import com.angkorteam.fintech.table.LinkCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.BadgeType;
import com.angkorteam.framework.share.provider.JdbcProvider;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.*;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class PaymentTypeBrowsePage extends Page {

    private DataTable<Map<String, Object>, String> dataTable;

    private JdbcProvider provider;

    private BookmarkablePageLink<Void> createLink;

    @Override
    protected void onInitialize() {
	super.onInitialize();
	this.provider = new JdbcProvider("m_payment_type");
	this.provider.boardField("id", "id", Integer.class);
	this.provider.boardField("value", "name", String.class);
	this.provider.boardField("description", "description", String.class);
	this.provider.boardField("is_cash_payment", "cash", Boolean.class);
	this.provider.boardField("order_position", "position", Integer.class);

	List<IColumn<Map<String, Object>, String>> columns = Lists.newArrayList();
	columns.add(new TextFilterColumn(this.provider, ItemClass.Integer, Model.of("ID"), "id", "id", this::idColumn));
	columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Name"), "name", "name",
		this::nameColumn));
	columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Description"), "description",
		"description", this::descriptionColumn));
	columns.add(new TextFilterColumn(this.provider, ItemClass.Boolean, Model.of("Is Cash Payment"), "cash", "cash",
		this::cashColumn));
	columns.add(new TextFilterColumn(this.provider, ItemClass.Integer, Model.of("Position"), "position", "position",
		this::positionColumn));
	columns.add(new ActionFilterColumn<>(Model.of("Action"), this::actionItem, this::actionClick));

	FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", this.provider);
	add(filterForm);

	this.dataTable = new DefaultDataTable<>("table", columns, this.provider, 20);
	this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
	filterForm.add(this.dataTable);

	this.createLink = new BookmarkablePageLink<>("createLink", PaymentTypeCreatePage.class);
	add(this.createLink);
    }

    private void actionClick(String s, Map<String, Object> stringObjectMap, AjaxRequestTarget ajaxRequestTarget) {
	Integer id = (Integer) stringObjectMap.get("id");
	try {
	    PaymentTypeHelper.delete((Session) getSession(), String.valueOf(id));
	} catch (UnirestException e) {
	}
	ajaxRequestTarget.add(this.dataTable);
    }

    private List<ActionItem> actionItem(String s, Map<String, Object> stringObjectMap) {
	return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    private ItemPanel idColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	Integer id = (Integer) model.get(jdbcColumn);
	return new TextCell(Model.of(String.valueOf(id)));
    }

    private ItemPanel descriptionColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	String description = (String) model.get(jdbcColumn);
	return new TextCell(Model.of(description));
    }

    private ItemPanel positionColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	Integer position = (Integer) model.get(jdbcColumn);
	return new TextCell(Model.of(String.valueOf(position)));
    }

    private ItemPanel cashColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	Boolean cash = (Boolean) model.get(jdbcColumn);
	if (cash != null && cash) {
	    return new BadgeCell(BadgeType.Success, Model.of("Yes"));
	} else {
	    return new BadgeCell(BadgeType.Danger, Model.of("No"));
	}
    }

    private ItemPanel nameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
	String name = (String) model.get(jdbcColumn);
	if (Strings.isNullOrEmpty(name)) {
	    return new TextCell(Model.of(name));
	} else {
	    PageParameters parameters = new PageParameters();
	    parameters.add("paymentTypeId", model.get("id"));
	    return new LinkCell(PaymentTypeModifyPage.class, parameters, Model.of(name));
	}
    }
}
