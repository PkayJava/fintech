//package com.angkorteam.fintech.pages.holiday;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.wicket.WicketRuntimeException;
//import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
//import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
//import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.IModel;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//
//import com.angkorteam.fintech.Page;
//import com.angkorteam.fintech.ddl.MHoliday;
//import com.angkorteam.fintech.ddl.MHolidayOffice;
//import com.angkorteam.fintech.ddl.MOffice;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.pages.OrganizationDashboardPage;
//import com.angkorteam.fintech.provider.JdbcProvider;
//import com.angkorteam.fintech.provider.SingleChoiceProvider;
//import com.angkorteam.fintech.table.TextCell;
//import com.angkorteam.framework.models.PageBreadcrumb;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.Calendar;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
//import com.google.common.collect.Lists;
//
///**
// * Created by socheatkhauv on 6/26/17.
// */
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class HolidayBrowsePage extends Page {
//
//    protected BookmarkablePageLink<Void> createLink;
//
//    protected Form<Void> form1;
//    protected Button searchButton;
//
//    protected UIRow row1;
//
//    protected UIBlock officeBlock;
//    protected UIContainer officeIContainer;
//    protected SingleChoiceProvider officeProvider;
//    protected Option officeValue;
//    protected Select2SingleChoice<Option> officeField;
//
//    protected FilterForm<Map<String, String>> form2;
//
//    protected UIRow row2;
//
//    protected UIBlock dataBlock;
//    protected UIContainer dataIContainer;
//    protected DataTable<Map<String, Object>, String> dataTable;
//    protected JdbcProvider dataProvider;
//    protected List<IColumn<Map<String, Object>, String>> dataColumn;
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
//            breadcrumb.setLabel("Holiday");
//            BREADCRUMB.add(breadcrumb);
//        }
//        return Model.ofList(BREADCRUMB);
//    }
//
//    @Override
//    protected void initData() {
//        this.officeProvider = new SingleChoiceProvider(MOffice.NAME, MOffice.Field.ID, MOffice.Field.NAME);
//        this.officeProvider.applyWhere("id", MOffice.Field.ID + " IN (select " + MHolidayOffice.Field.OFFICE_ID + " from " + MHolidayOffice.NAME + ")");
//
//        this.dataProvider = new JdbcProvider(MHoliday.NAME);
//        this.dataProvider.applyJoin("m_holiday_office", "LEFT JOIN " + MHolidayOffice.NAME + " ON " + MHoliday.NAME + "." + MHoliday.Field.ID + " = " + MHolidayOffice.NAME + "." + MHolidayOffice.Field.HOLIDAY_ID);
//        this.dataProvider.setGroupBy(MHoliday.NAME + "." + MHoliday.Field.ID);
//        this.dataProvider.boardField(MHoliday.NAME + "." + MHoliday.Field.ID, "id", Long.class);
//        this.dataProvider.boardField(MHoliday.NAME + "." + MHoliday.Field.NAME, "name", String.class);
//        this.dataProvider.boardField("CASE " + MHoliday.NAME + "." + MHoliday.Field.STATUS_ENUM + " WHEN 100 THEN 'Pending for activation' WHEN 300 THEN 'Active' ELSE CONCAT(" + MHoliday.NAME + "." + MHoliday.Field.STATUS_ENUM + ",'') END", "status_enum", Long.class);
//        this.dataProvider.boardField(MHoliday.NAME + "." + MHoliday.Field.FROM_DATE, "from_date", Calendar.Date);
//        this.dataProvider.boardField(MHoliday.NAME + "." + MHoliday.Field.TO_DATE, "to_date", Calendar.Date);
//        this.dataProvider.boardField("CASE " + MHoliday.NAME + "." + MHoliday.Field.RESCHEDULING_TYPE + " WHEN 1 THEN 'Next Repayment date' WHEN 2 THEN DATE_FORMAT(" + MHoliday.NAME + "." + MHoliday.Field.REPAYMENTS_RESCHEDULED_TO + ", '%e/%m/%Y') ELSE 'N/A' END", "repayments_rescheduled_to", String.class);
//
//        this.dataColumn = Lists.newArrayList();
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Long, Model.of("ID"), "id", "id", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Name"), "name", "name", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Date, Model.of("Start Date"), "from_date", "from_date", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Date, Model.of("End Date"), "to_date", "to_date", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Repayments Scheduled To"), "repayments_rescheduled_to", "repayments_rescheduled_to", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Long, Model.of("Status"), "status_enum", "status_enum", this::dataColumn));
//    }
//
//    @Override
//    protected void initComponent() {
//        this.createLink = new BookmarkablePageLink<>("createLink", HolidayCreatePage.class);
//        add(this.createLink);
//
//        this.form1 = new Form<>("form1");
//        add(this.form1);
//
//        this.searchButton = new Button("searchButton");
//        this.searchButton.setOnSubmit(this::searchButtonSubmit);
//        this.form1.add(this.searchButton);
//
//        this.row1 = UIRow.newUIRow("row1", this.form1);
//
//        this.officeBlock = this.row1.newUIBlock("officeBlock", Size.Twelve_12);
//        this.officeIContainer = this.officeBlock.newUIContainer("officeIContainer");
//        this.officeField = new Select2SingleChoice<>("officeField", new PropertyModel<>(this, "officeValue"), this.officeProvider);
//        this.officeIContainer.add(this.officeField);
//        this.officeIContainer.newFeedback("officeFeedback", this.officeField);
//
//        this.form2 = new FilterForm<>("form2", this.dataProvider);
//        add(this.form2);
//
//        this.row2 = UIRow.newUIRow("row2", this.form2);
//
//        this.dataBlock = this.row2.newUIBlock("dataBlock", Size.Twelve_12);
//        this.dataIContainer = this.dataBlock.newUIContainer("dataIContainer");
//        this.dataTable = new DefaultDataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
//        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, this.form2));
//        this.dataIContainer.add(this.dataTable);
//    }
//
//    @Override
//    protected void configureMetaData() {
//    }
//
//    protected void searchButtonSubmit(Button button) {
//        if (this.officeValue == null) {
//            this.dataProvider.removeWhere("office");
//        } else {
//            this.dataProvider.applyWhere("office", MHolidayOffice.NAME + "." + MHolidayOffice.Field.OFFICE_ID + " = " + this.officeValue.getId());
//        }
//    }
//
//    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
//        if ("id".equals(column)) {
//            Long value = (Long) model.get(column);
//            return new TextCell(value);
//        } else if ("name".equals(column) || "status_enum".equals(column) || "repayments_rescheduled_to".equals(column)) {
//            String value = (String) model.get(column);
//            return new TextCell(value);
//        } else if ("from_date".equals(column) || "to_date".equals(column)) {
//            Date value = (Date) model.get(column);
//            return new TextCell(value, "dd/MM/yyyy");
//        }
//        throw new WicketRuntimeException("Unknown " + column);
//    }
//
//}
