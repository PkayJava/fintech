package com.angkorteam.fintech.pages;

import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.helper.GlobalConfigurationHelper;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.table.BadgeCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
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
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/26/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class GlobalConfigurationPage extends Page {

    protected DataTable<Map<String, Object>, String> dataTable;
    protected JdbcProvider dataProvider;
    protected List<IColumn<Map<String, Object>, String>> dataColumn;
    protected FilterForm<Map<String, String>> dataFilterForm;

    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock nameBlock;
    protected WebMarkupContainer nameIContainer;
    protected SingleChoiceProvider nameProvider;
    protected Option nameValue;
    protected Select2SingleChoice<Option> nameField;
    protected TextFeedbackPanel nameFeedback;

    protected WebMarkupBlock valueBlock;
    protected WebMarkupContainer valueIContainer;
    protected Integer valueValue;
    protected TextField<Integer> valueField;
    protected TextFeedbackPanel valueFeedback;

    protected Form<Void> form;
    protected Button saveButton;

    protected static final List<PageBreadcrumb> BREADCRUMB;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        return Model.ofList(BREADCRUMB);
    }

    static {
        BREADCRUMB = Lists.newArrayList();
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
            breadcrumb.setLabel("Global Configuration");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initComponent() {
        initDataTable();

        this.closeLink = new BookmarkablePageLink<>("closeLink", SystemDashboardPage.class);
        add(this.closeLink);

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        initNameBlock();

        initValueBlock();
    }

    protected void initValueBlock() {
        this.valueBlock = new WebMarkupBlock("valueBlock", Size.Twelve_12);
        this.form.add(this.valueBlock);
        this.valueIContainer = new WebMarkupContainer("valueIContainer");
        this.valueBlock.add(this.valueIContainer);
        this.valueField = new TextField<>("valueField", new PropertyModel<>(this, "valueValue"));
        this.valueField.setRequired(true);
        this.valueIContainer.add(this.valueField);
        this.valueFeedback = new TextFeedbackPanel("valueFeedback", this.valueField);
        this.valueIContainer.add(this.valueFeedback);
    }

    protected void initNameBlock() {
        this.nameBlock = new WebMarkupBlock("nameBlock", Size.Twelve_12);
        this.form.add(this.nameBlock);
        this.nameIContainer = new WebMarkupContainer("nameIContainer");
        this.nameBlock.add(this.nameIContainer);
        this.nameProvider = new SingleChoiceProvider("c_configuration", "id", "name");
        this.nameField = new Select2SingleChoice<>("nameField", 0, new PropertyModel<>(this, "nameValue"), this.nameProvider);
        this.nameField.setRequired(true);
        this.nameIContainer.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.nameIContainer.add(this.nameFeedback);
    }

    protected void initDataTable() {
        this.dataProvider = new JdbcProvider("c_configuration");
        this.dataProvider.boardField("id", "id", Long.class);
        this.dataProvider.boardField("name", "name", String.class);
        this.dataProvider.boardField("enabled", "enabled", Boolean.class);
        this.dataProvider.boardField("value", "value", Integer.class);

        this.dataProvider.setSort("name", SortOrder.ASCENDING);

        this.dataProvider.selectField("id", Long.class);

        this.dataColumn = Lists.newArrayList();
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Name"), "name", "name", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Boolean, Model.of("Enabled ?"), "enabled", "enabled", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Integer, Model.of("Value"), "value", "value", this::dataColumn));
        this.dataColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::dataAction, this::dataClick));

        this.dataFilterForm = new FilterForm<>("dataFilterForm", this.dataProvider);
        add(this.dataFilterForm);

        this.dataTable = new DefaultDataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, this.dataFilterForm));
        this.dataFilterForm.add(this.dataTable);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected void saveButtonSubmit(Button button) {
        JsonNode node = null;
        try {
            node = GlobalConfigurationHelper.updateValueForGlobalConfiguration((Session) getSession(), this.nameValue.getId(), String.valueOf(this.valueValue));
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(GlobalConfigurationPage.class);
    }

    protected void dataClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        try {
            Long id = (Long) model.get("id");
            if ("enable".equals(s)) {
                GlobalConfigurationHelper.updateEnabledFlagForGlobalConfiguration((Session) getSession(), String.valueOf(id), true);
                target.add(this.dataTable);
            } else if ("disable".equals(s)) {
                GlobalConfigurationHelper.updateEnabledFlagForGlobalConfiguration((Session) getSession(), String.valueOf(id), false);
                target.add(this.dataTable);
            }
        } catch (UnirestException e) {
        }
    }

    protected List<ActionItem> dataAction(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        Boolean enabled = (Boolean) model.get("enabled");
        if (enabled != null && enabled) {
            actions.add(new ActionItem("disable", Model.of("Disable"), ItemCss.PRIMARY));
        } else {
            actions.add(new ActionItem("enable", Model.of("Enable"), ItemCss.PRIMARY));
        }
        return actions;
    }

    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("name".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("enabled".equals(column)) {
            Boolean value = (Boolean) model.get(column);
            if (value != null && value) {
                return new BadgeCell(BadgeType.Success, Model.of("Yes"));
            } else {
                return new BadgeCell(BadgeType.Danger, Model.of("No"));
            }
        } else if ("value".equals(column)) {
            Integer value = (Integer) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

}
