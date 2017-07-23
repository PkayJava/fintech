package com.angkorteam.fintech.pages.tax;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.request.TaxGroupBuilder;
import com.angkorteam.fintech.helper.TaxGroupHelper;
import com.angkorteam.fintech.popup.TaxGroupModifyPopup;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.ajax.markup.html.form.AjaxButton;
import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
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
import com.angkorteam.framework.wicket.markup.html.form.select2.OptionSingleChoiceProvider;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by socheatkhauv on 7/16/17.
 */
public class TaxGroupModifyPage extends Page {

    private String taxId;

    private Form<Void> taxForm;
    private AjaxButton addButton;

    private OptionSingleChoiceProvider taxProvider;
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

    private List<Map<String, Object>> taxComponentValue = Lists.newArrayList();
    private DataTable<Map<String, Object>, String> taxComponentTable;
    private ListDataProvider taxComponentProvider;

    private Long itemId;
    private String itemStartDateValue;
    private Date itemEndDateValue;
    private ModalWindow taxPopup;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        PageParameters parameters = getPageParameters();
        this.taxId = parameters.get("taxId").toString("");

        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);

        Map<String, Object> taxObject = jdbcTemplate.queryForMap("select * from m_tax_group where id = ?", this.taxId);

        initTaxForm();

        initForm(taxObject);
    }

    private void initTaxForm() {
        this.taxForm = new Form<>("taxForm");
        add(this.taxForm);

        this.addButton = new AjaxButton("addButton");
        this.addButton.setOnSubmit(this::addButtonSubmit);
        this.taxForm.add(this.addButton);

        this.taxProvider = new OptionSingleChoiceProvider("m_tax_component", "id", "name");
        this.taxField = new Select2SingleChoice<>("taxField", 0, new PropertyModel<>(this, "taxValue"), this.taxProvider);
        this.taxForm.add(this.taxField);
        this.taxFeedback = new TextFeedbackPanel("taxFeedback", this.taxField);
        this.taxForm.add(this.taxFeedback);

        this.startDateField = new DateTextField("startDateField", new PropertyModel<>(this, "startDateValue"));
        this.startDateField.setRequired(true);
        this.taxForm.add(this.startDateField);
        this.startDateFeedback = new TextFeedbackPanel("startDateFeedback", this.startDateField);
        this.taxForm.add(this.startDateFeedback);
    }

    private void addButtonSubmit(AjaxButton button, AjaxRequestTarget target) {
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
    }

    private void initForm(Map<String, Object> taxObject) {
        this.form = new Form<>("form");
        add(this.form);

        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);

        List<Map<String, Object>> groups = jdbcTemplate.queryForList("SELECT if(m_tax_group_mappings.end_date is null, 'Y', 'N') allow, m_tax_group_mappings.id, concat(m_tax_group_mappings.id, '') AS uuid, concat(m_tax_component.id, '') taxComponentId, m_tax_component.name tax, m_tax_group_mappings.start_date startDate, m_tax_group_mappings.end_date endDate FROM m_tax_group_mappings INNER join m_tax_component on m_tax_group_mappings.tax_component_id = m_tax_component.id WHERE tax_group_id = ?", this.taxId);
        this.taxComponentValue.addAll(groups);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", TaxGroupBrowsePage.class);
        this.form.add(this.closeLink);

        this.nameValue = (String) taxObject.get("name");
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setRequired(true);
        this.form.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.form.add(this.nameFeedback);

        List<IColumn<Map<String, Object>, String>> taxComponentColumn = Lists.newArrayList();
        taxComponentColumn.add(new TextColumn(Model.of("Tax Component"), "tax", "tax", this::taxComponentTaxColumn));
        taxComponentColumn.add(new TextColumn(Model.of("Start Date"), "startDate", "startDate", this::taxComponentStartDateColumn));
        taxComponentColumn.add(new TextColumn(Model.of("End Date"), "endDate", "endDate", this::taxComponentEndDateColumn));
        taxComponentColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::taxComponentActionItem, this::taxComponentActionClick));
        this.taxComponentProvider = new ListDataProvider(this.taxComponentValue);
        this.taxComponentTable = new DataTable<>("taxComponentTable", taxComponentColumn, this.taxComponentProvider, 20);
        this.form.add(this.taxComponentTable);
        this.taxComponentTable.addTopToolbar(new HeadersToolbar<>(this.taxComponentTable, this.taxComponentProvider));
        this.taxComponentTable.addBottomToolbar(new NoRecordsToolbar(this.taxComponentTable));

        this.taxPopup = new ModalWindow("taxPopup");
        add(this.taxPopup);

        this.taxPopup.setContent(new TaxGroupModifyPopup(this.taxPopup.getContentId(), this.taxPopup, this));
        this.taxPopup.setOnClose(this::taxPopupOnClose);
    }

    private void taxPopupOnClose(AjaxRequestTarget target) {
        for (Map<String, Object> item : this.taxComponentValue) {
            if (this.itemId.equals(item.get("id"))) {
                item.put("endDate", this.itemEndDateValue);
                this.itemEndDateValue = null;
                break;
            }
        }
        target.add(this.taxComponentTable);
    }

    private ItemPanel taxComponentTaxColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String tax = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(tax));
    }

    private ItemPanel taxComponentStartDateColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Date startDate = (Date) model.get(jdbcColumn);
        if (startDate == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(DateFormatUtils.format(startDate, "yyyy-MM-dd")));
        }
    }

    private ItemPanel taxComponentEndDateColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Date endDate = (Date) model.get(jdbcColumn);
        if (endDate == null) {
            return new TextCell(Model.of(""));
        } else {
            return new TextCell(Model.of(DateFormatUtils.format(endDate, "yyyy-MM-dd")));
        }
    }

    private void taxComponentActionClick(String s, Map<String, Object> stringObjectMap, AjaxRequestTarget target) {
        if ("delete".equals(s)) {
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
            target.add(this.taxComponentTable);
        } else if ("modify".equals(s)) {
            this.itemId = (Long) stringObjectMap.get("id");
            this.itemEndDateValue = (Date) stringObjectMap.get("endDate");
            this.itemStartDateValue = DateFormatUtils.format((Date) stringObjectMap.get("startDate"), "yyyy-MM-dd");
            this.taxPopup.show(target);
        }
    }

    private List<ActionItem> taxComponentActionItem(String s, Map<String, Object> stringObjectMap) {
        List<ActionItem> actions = Lists.newArrayList();
        if (stringObjectMap.get("id") != null) {
            if (!"N".equals(stringObjectMap.get("allow"))) {
                actions.add(new ActionItem("modify", Model.of("Modify"), ItemCss.INFO));
            }
        } else {
            actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        }
        return actions;
    }

    private void saveButtonSubmit(Button button) {
        TaxGroupBuilder builder = new TaxGroupBuilder();
        builder.withId(this.taxId);
        builder.withName(this.nameValue);
        for (Map<String, Object> tax : this.taxComponentValue) {
            Long id = (Long) tax.get("id");
            String taxComponentId = (String) tax.get("taxComponentId");
            builder.withTaxComponent(id == null ? null : String.valueOf(id), taxComponentId, (Date) tax.get("startDate"), (Date) tax.get("endDate"));
        }

        JsonNode node = null;
        try {
            node = TaxGroupHelper.update(builder.build());
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
