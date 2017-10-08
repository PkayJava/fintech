package com.angkorteam.fintech.pages.rate;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.FloatingRateBuilder;
import com.angkorteam.fintech.helper.FloatingRateHelper;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.pages.staff.StaffBrowsePage;
import com.angkorteam.fintech.table.BadgeCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.BadgeType;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.TextColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionFilterColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionItem;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemCss;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class FloatingRateCreatePage extends Page {

    private Form<Void> rateForm;
    private AjaxButton addButton;

    private Date fromDateValue;
    private DateTextField fromDateField;
    private TextFeedbackPanel fromDateFeedback;

    private Double interestRateValue;
    private TextField<Double> interestRateField;
    private TextFeedbackPanel interestRateFeedback;

    private Boolean differentialValue;
    private CheckBox differentialField;
    private TextFeedbackPanel differentialFeedback;

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

    private String nameValue;
    private TextField<String> nameField;
    private TextFeedbackPanel nameFeedback;

    private Boolean activeValue;
    private CheckBox activeField;
    private TextFeedbackPanel activeFeedback;

    private Boolean baseLendingValue;
    private CheckBox baseLendingField;
    private TextFeedbackPanel baseLendingFeedback;

    private List<Map<String, Object>> rateValue = Lists.newArrayList();
    private DataTable<Map<String, Object>, String> rateTable;
    private ListDataProvider rateProvider;

    private static final List<PageBreadcrumb> BREADCRUMB;

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
            breadcrumb.setLabel("Product");
            breadcrumb.setPage(ProductDashboardPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Floating Rate");
            breadcrumb.setPage(FloatingRateBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Floating Rate Create");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.rateForm = new Form<>("rateForm");
        add(this.rateForm);

        this.addButton = new AjaxButton("addButton");
        this.addButton.setOnSubmit(this::addButtonSubmit);
        this.rateForm.add(this.addButton);

        this.fromDateField = new DateTextField("fromDateField", new PropertyModel<>(this, "fromDateValue"));
        this.fromDateField.setRequired(true);
        this.rateForm.add(this.fromDateField);
        this.fromDateFeedback = new TextFeedbackPanel("fromDateFeedback", this.fromDateField);
        this.rateForm.add(this.fromDateFeedback);

        this.interestRateField = new TextField<>("interestRateField", new PropertyModel<>(this, "interestRateValue"));
        this.interestRateField.setRequired(true);
        this.rateForm.add(this.interestRateField);
        this.interestRateFeedback = new TextFeedbackPanel("interestRateFeedback", this.interestRateField);
        this.rateForm.add(this.interestRateFeedback);

        this.differentialField = new CheckBox("differentialField", new PropertyModel<>(this, "differentialValue"));
        this.differentialField.setRequired(true);
        this.rateForm.add(this.differentialField);
        this.differentialFeedback = new TextFeedbackPanel("differentialFeedback", this.differentialField);
        this.rateForm.add(this.differentialFeedback);

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", StaffBrowsePage.class);
        this.form.add(this.closeLink);

        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setRequired(true);
        this.form.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.form.add(this.nameFeedback);

        this.activeField = new CheckBox("activeField", new PropertyModel<>(this, "activeValue"));
        this.activeField.setRequired(true);
        this.form.add(this.activeField);
        this.activeFeedback = new TextFeedbackPanel("activeFeedback", this.activeField);
        this.form.add(this.activeFeedback);

        this.baseLendingField = new CheckBox("baseLendingField", new PropertyModel<>(this, "baseLendingValue"));
        this.baseLendingField.setRequired(true);
        this.form.add(this.baseLendingField);
        this.baseLendingFeedback = new TextFeedbackPanel("baseLendingFeedback", this.baseLendingField);
        this.form.add(this.baseLendingFeedback);

        List<IColumn<Map<String, Object>, String>> rateColumn = Lists.newArrayList();
        rateColumn.add(new TextColumn(Model.of("From Date"), "fromDate", "fromDate", this::rateFromDateColumn));
        rateColumn.add(new TextColumn(Model.of("Interest Rate"), "interestRate", "interestRate", this::rateInterestRateColumn));
        rateColumn.add(new TextColumn(Model.of("Is Differential"), "differential", "differential", this::rateDifferentialColumn));
        rateColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::rateActionItem, this::rateActionClick));
        this.rateProvider = new ListDataProvider(this.rateValue);
        this.rateTable = new DataTable<>("rateTable", rateColumn, this.rateProvider, 20);
        this.form.add(this.rateTable);
        this.rateTable.addTopToolbar(new HeadersToolbar<>(this.rateTable, this.rateProvider));
        this.rateTable.addBottomToolbar(new NoRecordsToolbar(this.rateTable));
    }

    private void rateActionClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        if ("delete".equals(s)) {
            int index = -1;
            for (int i = 0; i < this.rateValue.size(); i++) {
                Map<String, Object> column = this.rateValue.get(i);
                if (model.get("uuid").equals(column.get("uuid"))) {
                    index = i;
                    break;
                }
            }
            if (index >= 0) {
                this.rateValue.remove(index);
            }
            target.add(this.rateTable);
        }
    }

    private List<ActionItem> rateActionItem(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    private ItemPanel rateInterestRateColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Double value = (Double) model.get(jdbcColumn);
        return new TextCell(value);
    }

    private ItemPanel rateFromDateColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Date value = (Date) model.get(jdbcColumn);
        return new TextCell(value, "dd/MM/yyyy");
    }

    private ItemPanel rateDifferentialColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Boolean differential = (Boolean) model.get(jdbcColumn);
        if (differential != null && differential) {
            return new BadgeCell(BadgeType.Success, Model.of("Yes"));
        } else {
            return new BadgeCell(BadgeType.Danger, Model.of("No"));
        }
    }

    protected boolean addButtonSubmit(AjaxButton button, AjaxRequestTarget target) {
        Map<String, Object> rate = Maps.newHashMap();
        rate.put("uuid", UUID.randomUUID().toString());
        rate.put("fromDate", this.fromDateValue);
        rate.put("interestRate", this.interestRateValue);
        rate.put("differential", this.differentialValue);
        this.rateValue.add(rate);
        this.fromDateValue = null;
        this.interestRateValue = null;
        this.differentialValue = null;
        target.add(this.form);
        target.add(this.rateForm);
        return false;
    }

    private void saveButtonSubmit(Button button) {
        FloatingRateBuilder builder = new FloatingRateBuilder();
        builder.withActive(this.activeValue);
        builder.withBaseLendingRate(this.baseLendingValue);
        builder.withName(this.nameValue);
        for (Map<String, Object> rate : this.rateValue) {
            builder.withRatePeriod((Date) rate.get("fromDate"), (Double) rate.get("interestRate"), (Boolean) rate.get("differential"));
        }

        JsonNode node = null;
        try {
            node = FloatingRateHelper.create((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(FloatingRateCreatePage.class);
    }

}
