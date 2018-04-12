package com.angkorteam.fintech.pages.code;

import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import com.angkorteam.fintech.widget.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.MCode;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.helper.CodeHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.pages.SystemDashboardPage;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.BadgeCell;
import com.angkorteam.fintech.table.LinkCell;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.framework.BadgeType;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
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
public class CodeBrowsePage extends Page {

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

        this.form = new Form<>("form");
        add(this.form);

        this.addButton = new Button("addButton");
        this.addButton.setOnSubmit(this::addButtonSubmit);
        this.form.add(this.addButton);

        initNameBlock();
    }

    @Override
    protected void configureMetaData() {
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

    protected void initDataBlock() {
        this.dataBlock = new WebMarkupBlock("dataBlock", Size.Twelve_12);
        add(this.dataBlock);
        this.dataIContainer = new WebMarkupContainer("dataIContainer");
        this.dataBlock.add(this.dataIContainer);
        this.dataProvider = new JdbcProvider(MCode.NAME);
        this.dataProvider.boardField(MCode.Field.ID, "id", Long.class);
        this.dataProvider.boardField(MCode.Field.CODE_NAME, "code_name", String.class);
        this.dataProvider.boardField(MCode.Field.IS_SYSTEM_DEFINED, "system", Boolean.class);
        this.dataProvider.setSort("code_name", SortOrder.ASCENDING);

        this.dataProvider.selectField("id", Long.class);

        this.dataColumn = Lists.newArrayList();
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Code Name"), "code_name", "code_name", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Boolean, Model.of("Is System ?"), "system", "system", this::dataColumn));

        this.dataFilterForm = new FilterForm<>("dataFilterForm", this.dataProvider);
        this.dataIContainer.add(this.dataFilterForm);

        this.dataTable = new DefaultDataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, this.dataFilterForm));
        this.dataFilterForm.add(this.dataTable);
    }

    protected void addButtonSubmit(Button button) {
        JsonNode node = CodeHelper.create((Session) getSession(), this.nameValue);

        if (reportError(node)) {
            return;
        }
        setResponsePage(CodeBrowsePage.class);
    }

    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("code_name".equals(column)) {
            String value = (String) model.get(column);
            PageParameters parameters = new PageParameters();
            parameters.add("codeId", model.get("id"));
            return new LinkCell(ValueBrowsePage.class, parameters, value);
        } else if ("system".equals(column)) {
            Boolean system = (Boolean) model.get(column);
            if (system != null && system) {
                return new BadgeCell(BadgeType.Success, Model.of("Yes"));
            } else {
                return new BadgeCell(BadgeType.Danger, Model.of("No"));
            }
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

}
