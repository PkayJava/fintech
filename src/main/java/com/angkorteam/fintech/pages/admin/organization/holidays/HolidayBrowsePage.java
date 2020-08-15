package com.angkorteam.fintech.pages.admin.organization.holidays;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.MifosDataContextManager;
import com.angkorteam.fintech.data.MySQLDataProvider;
import com.angkorteam.fintech.client.Function;
import com.angkorteam.fintech.meta.tenant.MHoliday;
import com.angkorteam.fintech.meta.tenant.MHolidayOffice;
import com.angkorteam.fintech.meta.tenant.MOffice;
import com.angkorteam.webui.frmk.common.WicketFactory;
import com.angkorteam.webui.frmk.provider.QueryDataProvider;
import com.angkorteam.webui.frmk.wicket.extensions.markup.html.repeater.data.table.AbstractDataTable;
import com.angkorteam.webui.frmk.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.webui.frmk.wicket.extensions.markup.html.repeater.data.table.filter.*;
import com.angkorteam.webui.frmk.wicket.layout.Size;
import com.angkorteam.webui.frmk.wicket.layout.UIColumn;
import com.angkorteam.webui.frmk.wicket.layout.UIContainer;
import com.angkorteam.webui.frmk.wicket.layout.UIRow;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.metamodel.DataContext;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class HolidayBrowsePage extends MasterPage {

    protected Form<Void> filterForm;

    protected UIRow row1;

    protected UIColumn holidayColumn;
    protected UIContainer holidayContainer;
    protected TextField<String> holidayField;
    protected String holidayValue;

    protected UIColumn officeColumn;
    protected UIContainer officeContainer;
    protected TextField<String> officeField;
    protected String officeValue;

    protected Button searchButton;
    protected BookmarkablePageLink<Void> clearButton;

    protected BookmarkablePageLink<Void> createLink;

    protected FilterForm<Map<String, Expression>> holidayBrowseForm;
    protected QueryDataProvider holidayBrowseProvider;
    protected List<IColumn<Map<String, Object>, String>> holidayBrowseColumn;
    protected AbstractDataTable<Map<String, Object>, String> holidayBrowseTable;

    @Override
    protected void onInitData() {
        super.onInitData();
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
        MHoliday mHoliday = MHoliday.staticInitialize(dataContext);
        MOffice mOffice = MOffice.staticInitialize(dataContext);
        MHolidayOffice mHolidayOffice = MHolidayOffice.staticInitialize(dataContext);

        this.holidayBrowseProvider = new MySQLDataProvider(mHoliday.getName());
        this.holidayBrowseProvider.setCountField(mHoliday.getName() + "." + mHoliday.ID.getName());
        this.holidayBrowseProvider.selectField(mHoliday.getName() + "." + mHoliday.ID.getName(), "holidayId");

        this.holidayBrowseProvider.applyJoin("m_holiday_office", "LEFT JOIN " + mHolidayOffice.getName() + " ON " + mHoliday.getName() + "." + mHoliday.ID.getName() + " = " + mHolidayOffice.getName() + "." + mHolidayOffice.HOLIDAY_ID.getName());
        this.holidayBrowseProvider.applyJoin("m_office", "LEFT JOIN " + mOffice.getName() + " ON " + mHolidayOffice.getName() + "." + mHolidayOffice.OFFICE_ID.getName() + " = " + mOffice.getName() + "." + mOffice.ID.getName());

        this.holidayBrowseColumn = new ArrayList<>();
        this.holidayBrowseColumn.add(Column.text(Model.of("Office"), mOffice.getName() + "." + mOffice.NAME.getName(), "office", this.holidayBrowseProvider));
        this.holidayBrowseColumn.add(Column.text(Model.of("Holiday"), mHoliday.getName() + "." + mHoliday.NAME.getName(), "holiday", this.holidayBrowseProvider));
        this.holidayBrowseColumn.add(Column.datetime(Model.of("Start Date"), mHoliday.getName() + "." + mHoliday.FROM_DATE.getName(), "startDate", this.holidayBrowseProvider, "dd MMM yyyy"));
        this.holidayBrowseColumn.add(Column.datetime(Model.of("End Date"), mHoliday.getName() + "." + mHoliday.TO_DATE.getName(), "endDate", this.holidayBrowseProvider, "dd MMM yyyy"));
        this.holidayBrowseColumn.add(Column.text(Model.of("Repayments Scheduled To"), "CASE " + mHoliday.getName() + "." + mHoliday.RESCHEDULING_TYPE.getName() + " WHEN 1 THEN 'Next Repayment date' WHEN 2 THEN DATE_FORMAT(" + mHoliday.getName() + "." + mHoliday.REPAYMENT_RESCHEDULED_TO.getName() + ", '%d %b %Y') ELSE 'N/A' END", "repaymentSchedule", this.holidayBrowseProvider));
        this.holidayBrowseColumn.add(Column.text(Model.of("Status"), "CASE " + mHoliday.getName() + "." + mHoliday.STATUS_ENUM.getName() + " WHEN 100 THEN 'Pending for activation' WHEN 300 THEN 'Active' ELSE CONCAT(" + mHoliday.getName() + "." + mHoliday.STATUS_ENUM.getName() + ",'') END", "status", this.holidayBrowseProvider));

        this.holidayBrowseColumn.add(new ActionFilteredColumn<>(Model.of("Action"), this::holidayBrowseActionLink, this::holidayBrowseActionClick));
    }

    @Override
    protected void onInitHtml(MarkupContainer body) {
        this.filterForm = new Form<>("filterForm");
        body.add(this.filterForm);

        this.row1 = UIRow.newUIRow("row1", this.filterForm);

        this.holidayColumn = this.row1.newUIColumn("holidayColumn", Size.Three_3);
        this.holidayContainer = this.holidayColumn.newUIContainer("holidayContainer");
        this.holidayField = new TextField<>("holidayField", new PropertyModel<>(this, "holidayValue"));
        this.holidayField.setLabel(Model.of("Holiday"));
        this.holidayContainer.add(this.holidayField);
        this.holidayContainer.newFeedback("holidayFeedback", this.holidayField);

        this.officeColumn = this.row1.newUIColumn("officeColumn", Size.Three_3);
        this.officeContainer = this.officeColumn.newUIContainer("officeContainer");
        this.officeField = new TextField<>("officeField", new PropertyModel<>(this, "officeValue"));
        this.officeField.setLabel(Model.of("Office"));
        this.officeContainer.add(this.officeField);
        this.officeContainer.newFeedback("officeFeedback", this.officeField);

        this.row1.newUIColumn("lastColumn");

        this.searchButton = new Button("searchButton") {
            @Override
            public void onSubmit() {
                searchButtonSubmit();
            }
        };
        this.filterForm.add(this.searchButton);

        this.clearButton = new BookmarkablePageLink<>("clearButton", HolidayBrowsePage.class);
        this.filterForm.add(this.clearButton);

        this.createLink = new BookmarkablePageLink<>("createLink", HolidayCreatePage.class);
        body.add(this.createLink);

        this.holidayBrowseForm = new FilterForm<>("holidayBrowseForm", this.holidayBrowseProvider);
        body.add(this.holidayBrowseForm);

        this.holidayBrowseTable = new DataTable<>("holidayBrowseTable", this.holidayBrowseColumn, this.holidayBrowseProvider, 20);
        this.holidayBrowseForm.add(this.holidayBrowseTable);
    }

    protected List<ActionItem> holidayBrowseActionLink(String link, Map<String, Object> model) {
        List<ActionItem> actions = new ArrayList<>();
        actions.add(new ActionItem("Edit", Model.of("Edit"), ItemCss.WARNING));
        return actions;
    }

    protected void holidayBrowseActionClick(String link, Map<String, Object> model, AjaxRequestTarget target) {
        long holidayId = (long) model.get("holidayId");
        PageParameters parameters = new PageParameters();
        parameters.add("holidayId", holidayId);
        setResponsePage(HolidayModifyPage.class, parameters);
    }

    protected void searchButtonSubmit() {
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
        MHoliday mHoliday = MHoliday.staticInitialize(dataContext);
        MOffice mOffice = MOffice.staticInitialize(dataContext);

        this.holidayBrowseProvider.removeWhere("holiday");
        if (this.holidayValue != null && !"".equals(this.holidayValue)) {
            this.holidayBrowseProvider.applyWhere("holiday", mHoliday.getName() + "." + mHoliday.NAME.getName() + " LIKE '" + StringEscapeUtils.escapeSql(this.holidayValue) + "%'");
        }

        this.holidayBrowseProvider.removeWhere("office");
        if (this.officeValue != null && !"".equals(this.officeValue)) {
            this.holidayBrowseProvider.applyWhere("office", mOffice.getName() + "." + mOffice.NAME.getName() + " LIKE '" + StringEscapeUtils.escapeSql(this.officeValue) + "%'");
        }
    }

}
