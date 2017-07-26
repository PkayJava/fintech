package com.angkorteam.fintech.pages.holiday;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.*;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
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

    private FilterForm<Map<String, String>> filterForm;
    private DataTable<Map<String, Object>, String> dataTable;

    private JdbcProvider provider;

    private BookmarkablePageLink<Void> createLink;

    private SingleChoiceProvider officeProvider;
    private Option officeValue;
    private Select2SingleChoice<Option> officeField;
    private TextFeedbackPanel officeFeedback;

    private Form<Void> form;
    private Button searchButton;

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.provider = new JdbcProvider("m_holiday");
        this.provider.addJoin("LEFT JOIN m_holiday_office ON m_holiday.id = m_holiday_office.holiday_id");
        this.provider.setGroupBy("m_holiday.id");
        this.provider.boardField("m_holiday.id", "id", Long.class);
        this.provider.boardField("m_holiday.name", "name", String.class);
        this.provider.boardField("m_holiday.status_enum", "status_enum", Integer.class);
        this.provider.boardField("m_holiday.from_date", "from_date", Calendar.Date);
        this.provider.boardField("m_holiday.to_date", "to_date", Calendar.Date);
        this.provider.boardField("m_holiday.repayments_rescheduled_to", "repayments_rescheduled_to", Calendar.Date);

        List<IColumn<Map<String, Object>, String>> columns = Lists.newArrayList();
        columns.add(new TextFilterColumn(this.provider, ItemClass.Long, Model.of("ID"), "id", "id", this::idColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Name"), "name", "name", this::nameColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.Date, Model.of("Start Date"), "from_date", "from_date", this::startDateColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.Date, Model.of("End Date"), "to_date", "to_date", this::endDateColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.Date, Model.of("Alternate Working Day"), "repayments_rescheduled_to", "repayments_rescheduled_to", this::alternateWorkingDayColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.Integer, Model.of("Status"), "status_enum", "status_enum", this::statusColumn));

        this.filterForm = new FilterForm<>("filter-form", this.provider);
        add(this.filterForm);

        this.dataTable = new DefaultDataTable<>("table", columns, this.provider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, this.filterForm));
        this.filterForm.add(this.dataTable);

        this.createLink = new BookmarkablePageLink<>("createLink", HolidayCreatePage.class);
        add(this.createLink);

        this.form = new Form<>("form");
        add(this.form);

        this.searchButton = new Button("searchButton");
        this.searchButton.setOnSubmit(this::searchButtonSubmit);
        this.form.add(this.searchButton);

        this.officeProvider = new SingleChoiceProvider("m_office", "id", "name");
        this.officeProvider.applyWhere("id","id IN (select office_id from m_holiday_office)");
        this.officeField = new Select2SingleChoice<>("officeField", new PropertyModel<>(this, "officeValue"), this.officeProvider);
        this.form.add(this.officeField);
        this.officeFeedback = new TextFeedbackPanel("officeFeedback", this.officeField);
        this.form.add(this.officeFeedback);
    }

    private void searchButtonSubmit(Button button) {
        if (this.officeValue == null) {
            this.provider.removeWhere("office");
        } else {
            this.provider.applyWhere("office", "m_holiday_office.office_id = " + this.officeValue.getId());
        }
    }

    private ItemPanel idColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Long id = (Long) model.get(jdbcColumn);
        return new TextCell(Model.of(String.valueOf(id)));
    }

    private ItemPanel statusColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Integer status = (Integer) model.get(jdbcColumn);
        return new TextCell(Model.of(String.valueOf(status)));
    }

    private ItemPanel startDateColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Date startDate = (Date) model.get(jdbcColumn);
        if (startDate == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(DateFormatUtils.format(startDate, "yyyy-MM-dd")));
        }
    }

    private ItemPanel endDateColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Date endDate = (Date) model.get(jdbcColumn);
        if (endDate == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(DateFormatUtils.format(endDate, "yyyy-MM-dd")));
        }
    }

    private ItemPanel alternateWorkingDayColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Date alternateWorkingDay = (Date) model.get(jdbcColumn);
        if (alternateWorkingDay == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(DateFormatUtils.format(alternateWorkingDay, "yyyy-MM-dd")));
        }
    }

    private ItemPanel nameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String name = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(name));
    }

}
