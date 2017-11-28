package com.angkorteam.fintech.pages.holiday;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.pages.OrganizationDashboardPage;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.*;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 6/26/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class HolidayBrowsePage extends Page {

    protected WebMarkupBlock dataBlock;
    protected WebMarkupContainer dataIContainer;
    protected FilterForm<Map<String, String>> dataFilterForm;
    protected DataTable<Map<String, Object>, String> dataTable;
    protected JdbcProvider dataProvider;
    protected List<IColumn<Map<String, Object>, String>> dataColumn;

    protected BookmarkablePageLink<Void> createLink;

    protected WebMarkupBlock officeBlock;
    protected WebMarkupContainer officeIContainer;
    protected SingleChoiceProvider officeProvider;
    protected Option officeValue;
    protected Select2SingleChoice<Option> officeField;
    protected TextFeedbackPanel officeFeedback;

    protected Form<Void> form;
    protected Button searchButton;

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
            breadcrumb.setLabel("Organization");
            breadcrumb.setPage(OrganizationDashboardPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Holiday");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initComponent() {
        initDataBlock();

        this.createLink = new BookmarkablePageLink<>("createLink", HolidayCreatePage.class);
        add(this.createLink);

        this.form = new Form<>("form");
        add(this.form);

        this.searchButton = new Button("searchButton");
        this.searchButton.setOnSubmit(this::searchButtonSubmit);
        this.form.add(this.searchButton);

        initOfficeBlock();
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected void initOfficeBlock() {
        this.officeBlock = new WebMarkupBlock("officeBlock", WebMarkupBlock.Size.Twelve_12);
        this.form.add(this.officeBlock);
        this.officeIContainer = new WebMarkupContainer("officeIContainer");
        this.officeBlock.add(this.officeIContainer);
        this.officeProvider = new SingleChoiceProvider("m_office", "id", "name");
        this.officeProvider.applyWhere("id", "id IN (select office_id from m_holiday_office)");
        this.officeField = new Select2SingleChoice<>("officeField", new PropertyModel<>(this, "officeValue"), this.officeProvider);
        this.officeIContainer.add(this.officeField);
        this.officeFeedback = new TextFeedbackPanel("officeFeedback", this.officeField);
        this.officeIContainer.add(this.officeFeedback);
    }

    protected void initDataBlock() {
        this.dataBlock = new WebMarkupBlock("dataBlock", WebMarkupBlock.Size.Twelve_12);
        add(this.dataBlock);
        this.dataIContainer = new WebMarkupContainer("dataIContainer");
        this.dataBlock.add(this.dataIContainer);
        this.dataProvider = new JdbcProvider("m_holiday");
        this.dataProvider.applyJoin("m_holiday_office", "LEFT JOIN m_holiday_office ON m_holiday.id = m_holiday_office.holiday_id");
        this.dataProvider.setGroupBy("m_holiday.id");
        this.dataProvider.boardField("m_holiday.id", "id", Long.class);
        this.dataProvider.boardField("m_holiday.name", "name", String.class);
        this.dataProvider.boardField("m_holiday.status_enum", "status_enum", Integer.class);
        this.dataProvider.boardField("m_holiday.from_date", "from_date", Calendar.Date);
        this.dataProvider.boardField("m_holiday.to_date", "to_date", Calendar.Date);
        this.dataProvider.boardField("m_holiday.repayments_rescheduled_to", "repayments_rescheduled_to", Calendar.Date);

        this.dataColumn = Lists.newArrayList();
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Long, Model.of("ID"), "id", "id", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Name"), "name", "name", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Date, Model.of("Start Date"), "from_date", "from_date", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Date, Model.of("End Date"), "to_date", "to_date", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Date, Model.of("Alternate Working Day"), "repayments_rescheduled_to", "repayments_rescheduled_to", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Integer, Model.of("Status"), "status_enum", "status_enum", this::dataColumn));

        this.dataFilterForm = new FilterForm<>("dataFilterForm", this.dataProvider);
        this.dataIContainer.add(this.dataFilterForm);

        this.dataTable = new DefaultDataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, this.dataFilterForm));
        this.dataFilterForm.add(this.dataTable);
    }

    protected void searchButtonSubmit(Button button) {
        if (this.officeValue == null) {
            this.dataProvider.removeWhere("office");
        } else {
            this.dataProvider.applyWhere("office", "m_holiday_office.office_id = " + this.officeValue.getId());
        }
    }

    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("id".equals(column) || "status_enum".equals(column)) {
            Long value = (Long) model.get(column);
            return new TextCell(value);
        } else if ("name".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("from_date".equals(column) || "to_date".equals(column) || "repayments_rescheduled_to".equals(column)) {
            Date value = (Date) model.get(column);
            return new TextCell(value, "dd/MM/yyyy");
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

}
