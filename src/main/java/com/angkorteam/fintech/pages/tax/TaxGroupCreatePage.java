package com.angkorteam.fintech.pages.tax;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.request.TaxGroupBuilder;
import com.angkorteam.fintech.helper.TaxGroupHelper;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.pages.TaxDashboardPage;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.table.TextCell;
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
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by socheatkhauv on 7/16/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class TaxGroupCreatePage extends Page {

    private Form<Void> taxForm;
    private AjaxButton addButton;

    private SingleChoiceProvider taxProvider;
    private Option taxValue;
    private Select2SingleChoice<Option> taxField;
    private TextFeedbackPanel taxFeedback;

    private Date startDateValue;
    private DateTextField startDateField;
    private TextFeedbackPanel startDateFeedback;

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

    private String nameValue;
    private TextField<String> nameField;
    private TextFeedbackPanel nameFeedback;

    private List<Map<String, Object>> taxComponentValue;
    private DataTable<Map<String, Object>, String> taxComponentTable;
    private ListDataProvider taxComponentProvider;
    
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
            breadcrumb.setLabel("Tax");
            breadcrumb.setPage(TaxDashboardPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Tax Group");
            breadcrumb.setPage(TaxGroupBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Tax Group Create");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        initTaxForm();

        initForm();
    }

    private void initTaxForm() {
        this.taxForm = new Form<>("taxForm");
        add(this.taxForm);

        this.addButton = new AjaxButton("addButton");
        this.addButton.setOnSubmit(this::addButtonSubmit);
        this.taxForm.add(this.addButton);

        this.taxProvider = new SingleChoiceProvider("m_tax_component", "id", "name");
        this.taxField = new Select2SingleChoice<>("taxField", 0, new PropertyModel<>(this, "taxValue"),
                this.taxProvider);
        this.taxForm.add(this.taxField);
        this.taxFeedback = new TextFeedbackPanel("taxFeedback", this.taxField);
        this.taxForm.add(this.taxFeedback);

        this.startDateField = new DateTextField("startDateField", new PropertyModel<>(this, "startDateValue"));
        this.startDateField.setRequired(true);
        this.taxForm.add(this.startDateField);
        this.startDateFeedback = new TextFeedbackPanel("startDateFeedback", this.startDateField);
        this.taxForm.add(this.startDateFeedback);
    }

    protected boolean addButtonSubmit(AjaxButton button, AjaxRequestTarget target) {
        Map<String, Object> tax = Maps.newHashMap();
        tax.put("uuid", UUID.randomUUID().toString());
        tax.put("taxComponentId", this.taxValue.getId());
        tax.put("tax", this.taxValue.getText());
        tax.put("startDate", this.startDateValue);
        this.taxComponentValue.add(tax);
        this.taxValue = null;
        this.startDateValue = null;
        target.add(this.form);
        target.add(this.taxForm);
        return false;
    }

    private void initForm() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", TaxGroupBrowsePage.class);
        this.form.add(this.closeLink);

        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setRequired(true);
        this.form.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.form.add(this.nameFeedback);

        List<IColumn<Map<String, Object>, String>> taxComponentColumn = Lists.newArrayList();
        taxComponentColumn.add(new TextColumn(Model.of("Tax Component"), "tax", "tax", this::taxComponentTaxColumn));
        taxComponentColumn.add(
                new TextColumn(Model.of("Start Date"), "startDate", "startDate", this::taxComponentStartDateColumn));
        taxComponentColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::taxComponentActionItem,
                this::taxComponentActionClick));
        this.taxComponentValue = Lists.newArrayList();
        this.taxComponentProvider = new ListDataProvider(this.taxComponentValue);
        this.taxComponentTable = new DataTable<>("taxComponentTable", taxComponentColumn, this.taxComponentProvider,
                20);
        this.form.add(this.taxComponentTable);
        this.taxComponentTable.addTopToolbar(new HeadersToolbar<>(this.taxComponentTable, this.taxComponentProvider));
        this.taxComponentTable.addBottomToolbar(new NoRecordsToolbar(this.taxComponentTable));
    }

    private ItemPanel taxComponentTaxColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String tax = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(tax));
    }

    private ItemPanel taxComponentStartDateColumn(String jdbcColumn, IModel<String> display,
                                                  Map<String, Object> model) {
        Date startDate = (Date) model.get(jdbcColumn);
        if (startDate == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(DateFormatUtils.format(startDate, "yyyy-MM-dd")));
        }
    }

    private void taxComponentActionClick(String s, Map<String, Object> stringObjectMap,
                                         AjaxRequestTarget ajaxRequestTarget) {
        int index = -1;
        for (int i = 0; i < this.taxComponentValue.size(); i++) {
            Map<String, Object> column = this.taxComponentValue.get(i);
            if (stringObjectMap.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.taxComponentValue.remove(index);
        }
        ajaxRequestTarget.add(this.taxComponentTable);
    }

    private List<ActionItem> taxComponentActionItem(String s, Map<String, Object> stringObjectMap) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    private void saveButtonSubmit(Button button) {
        TaxGroupBuilder builder = new TaxGroupBuilder();
        builder.withName(this.nameValue);
        for (Map<String, Object> tax : this.taxComponentValue) {
            builder.withTaxComponent(null, (String) tax.get("taxComponentId"), (Date) tax.get("startDate"), null);
        }

        JsonNode node = null;
        try {
            node = TaxGroupHelper.create((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(TaxGroupBrowsePage.class);
    }
}
