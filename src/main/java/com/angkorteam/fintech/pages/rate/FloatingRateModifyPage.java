package com.angkorteam.fintech.pages.rate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.FloatingRateBuilder;
import com.angkorteam.fintech.helper.FloatingRateHelper;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.popup.RateModifyPopup;
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.fintech.table.BadgeCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.BadgeType;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.models.PageBreadcrumb;
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
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class FloatingRateModifyPage extends Page {

    protected String rateId;

    protected Form<Void> rateForm;
    protected AjaxButton addButton;

    protected WebMarkupBlock fromDateBlock;
    protected WebMarkupContainer fromDateIContainer;
    protected Date fromDateValue;
    protected DateTextField fromDateField;
    protected TextFeedbackPanel fromDateFeedback;

    protected WebMarkupBlock interestRateBlock;
    protected WebMarkupContainer interestRateIContainer;
    protected Double interestRateValue;
    protected TextField<Double> interestRateField;
    protected TextFeedbackPanel interestRateFeedback;

    protected WebMarkupBlock differentialBlock;
    protected WebMarkupContainer differentialIContainer;
    protected Boolean differentialValue;
    protected CheckBox differentialField;
    protected TextFeedbackPanel differentialFeedback;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock nameBlock;
    protected WebMarkupContainer nameIContainer;
    protected String nameValue;
    protected TextField<String> nameField;
    protected TextFeedbackPanel nameFeedback;

    protected WebMarkupBlock activeBlock;
    protected WebMarkupContainer activeIContainer;
    protected Boolean activeValue;
    protected CheckBox activeField;
    protected TextFeedbackPanel activeFeedback;

    protected WebMarkupBlock baseLendingBlock;
    protected WebMarkupContainer baseLendingIContainer;
    protected Boolean baseLendingValue;
    protected CheckBox baseLendingField;
    protected TextFeedbackPanel baseLendingFeedback;

    protected WebMarkupBlock rateBlock;
    protected WebMarkupContainer rateIContainer;
    protected List<Map<String, Object>> rateValue = Lists.newArrayList();
    protected DataTable<Map<String, Object>, String> rateTable;
    protected ListDataProvider rateProvider;
    protected List<IColumn<Map<String, Object>, String>> rateColumn;
    protected ModalWindow ratePopup;

    protected Map<String, Object> popupModel;

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
            breadcrumb.setLabel("Floating Rate Modify");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void initData() {
        this.popupModel = Maps.newHashMap();
        PageParameters parameters = getPageParameters();
        this.rateId = parameters.get("rateId").toString("");
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);

        Map<String, Object> rateObject = jdbcTemplate.queryForMap("select * from m_floating_rates where id = ?", this.rateId);
        this.nameValue = (String) rateObject.get("name");
        this.activeValue = (Boolean) rateObject.get("is_active");
        this.baseLendingValue = (Boolean) rateObject.get("is_base_lending_rate");

        this.rateValue.clear();
        List<Map<String, Object>> rates = jdbcTemplate.queryForList("select id, from_date fromDate, concat(id, '') uuid, interest_rate interestRate, is_differential_to_base_lending_rate differential from m_floating_rates_periods where floating_rates_id = ? order by id desc", this.rateId);
        List<String> dates = Lists.newArrayList();
        for (Map<String, Object> rate : rates) {
            String date = DateFormatUtils.format((Date) rate.get("fromDate"), "yyyy-MM-dd");
            if (dates.contains(date)) {
                continue;
            }
            dates.add(date);
            this.rateValue.add(rate);
        }
    }

    @Override
    protected void initComponent() {
        this.rateForm = new Form<>("rateForm");
        add(this.rateForm);

        this.addButton = new AjaxButton("addButton");
        this.addButton.setOnSubmit(this::addButtonSubmit);
        this.rateForm.add(this.addButton);

        initFromDateBlock();

        initInterestRateBlock();

        initDifferentialBlock();

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", FloatingRateBrowsePage.class);
        this.form.add(this.closeLink);

        initNameBlock();

        initActiveBlock();

        initBaseLendingBlock();

        initRateBlock();
    }

    protected void initRateBlock() {
        this.rateBlock = new WebMarkupBlock("rateBlock", Size.Twelve_12);
        this.form.add(this.rateBlock);
        this.rateIContainer = new WebMarkupContainer("rateIContainer");
        this.rateBlock.add(this.rateIContainer);
        this.rateColumn = Lists.newArrayList();
        this.rateColumn.add(new TextColumn(Model.of("From Date"), "fromDate", "fromDate", this::rateColumn));
        this.rateColumn.add(new TextColumn(Model.of("Interest Rate"), "interestRate", "interestRate", this::rateColumn));
        this.rateColumn.add(new TextColumn(Model.of("Is Differential"), "differential", "differential", this::rateColumn));
        this.rateColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::rateAction, this::rateClick));
        this.rateProvider = new ListDataProvider(this.rateValue);
        this.rateTable = new DataTable<>("rateTable", this.rateColumn, this.rateProvider, 20);
        this.rateIContainer.add(this.rateTable);
        this.rateTable.addTopToolbar(new HeadersToolbar<>(this.rateTable, this.rateProvider));
        this.rateTable.addBottomToolbar(new NoRecordsToolbar(this.rateTable));

        this.ratePopup = new ModalWindow("ratePopup");
        this.rateIContainer.add(this.ratePopup);
        this.ratePopup.setOnClose(this::ratePopupClose);
    }

    protected void initBaseLendingBlock() {
        this.baseLendingBlock = new WebMarkupBlock("baseLendingBlock", Size.Four_4);
        this.form.add(this.baseLendingBlock);
        this.baseLendingIContainer = new WebMarkupContainer("baseLendingIContainer");
        this.baseLendingBlock.add(this.baseLendingIContainer);
        this.baseLendingField = new CheckBox("baseLendingField", new PropertyModel<>(this, "baseLendingValue"));
        this.baseLendingField.setRequired(true);
        this.baseLendingIContainer.add(this.baseLendingField);
        this.baseLendingFeedback = new TextFeedbackPanel("baseLendingFeedback", this.baseLendingField);
        this.baseLendingIContainer.add(this.baseLendingFeedback);
    }

    protected void initActiveBlock() {
        this.activeBlock = new WebMarkupBlock("activeBlock", Size.Four_4);
        this.form.add(this.activeBlock);
        this.activeIContainer = new WebMarkupContainer("activeIContainer");
        this.activeBlock.add(this.activeIContainer);
        this.activeField = new CheckBox("activeField", new PropertyModel<>(this, "activeValue"));
        this.activeField.setRequired(true);
        this.activeIContainer.add(this.activeField);
        this.activeFeedback = new TextFeedbackPanel("activeFeedback", this.activeField);
        this.activeIContainer.add(this.activeFeedback);
    }

    protected void initNameBlock() {
        this.nameBlock = new WebMarkupBlock("nameBlock", Size.Four_4);
        this.form.add(this.nameBlock);
        this.nameIContainer = new WebMarkupContainer("nameIContainer");
        this.nameBlock.add(this.nameIContainer);
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setRequired(true);
        this.nameIContainer.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.nameIContainer.add(this.nameFeedback);
    }

    protected void initDifferentialBlock() {
        this.differentialBlock = new WebMarkupBlock("differentialBlock", Size.Four_4);
        this.rateForm.add(this.differentialBlock);
        this.differentialIContainer = new WebMarkupContainer("differentialIContainer");
        this.differentialBlock.add(this.differentialIContainer);
        this.differentialField = new CheckBox("differentialField", new PropertyModel<>(this, "differentialValue"));
        this.differentialField.setRequired(true);
        this.differentialIContainer.add(this.differentialField);
        this.differentialFeedback = new TextFeedbackPanel("differentialFeedback", this.differentialField);
        this.differentialIContainer.add(this.differentialFeedback);
    }

    protected void initInterestRateBlock() {
        this.interestRateBlock = new WebMarkupBlock("interestRateBlock", Size.Four_4);
        this.rateForm.add(this.interestRateBlock);
        this.interestRateIContainer = new WebMarkupContainer("interestRateIContainer");
        this.interestRateBlock.add(this.interestRateIContainer);
        this.interestRateField = new TextField<>("interestRateField", new PropertyModel<>(this, "interestRateValue"));
        this.interestRateField.setRequired(true);
        this.interestRateIContainer.add(this.interestRateField);
        this.interestRateFeedback = new TextFeedbackPanel("interestRateFeedback", this.interestRateField);
        this.interestRateIContainer.add(this.interestRateFeedback);
    }

    protected void initFromDateBlock() {
        this.fromDateBlock = new WebMarkupBlock("fromDateBlock", Size.Four_4);
        this.rateForm.add(this.fromDateBlock);
        this.fromDateIContainer = new WebMarkupContainer("fromDateIContainer");
        this.fromDateBlock.add(this.fromDateIContainer);
        this.fromDateField = new DateTextField("fromDateField", new PropertyModel<>(this, "fromDateValue"));
        this.fromDateField.setRequired(true);
        this.fromDateIContainer.add(this.fromDateField);
        this.fromDateFeedback = new TextFeedbackPanel("fromDateFeedback", this.fromDateField);
        this.fromDateIContainer.add(this.fromDateFeedback);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected void ratePopupClose(String popupName, String signalId, AjaxRequestTarget target) {
        for (Map<String, Object> item : this.rateValue) {
            if (this.popupModel.get("idValue").equals(item.get("id"))) {
                item.put("fromDate", this.popupModel.get("fromDateValue"));
                item.put("interestRate", this.popupModel.get("interestRateValue"));
                item.put("differential", this.popupModel.get("differentialValue"));
                this.popupModel.clear();
                break;
            }
        }
        target.add(this.rateTable);
    }

    protected void rateClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
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
        } else if ("modify".equals(s)) {
            this.popupModel.put("idValue", model.get("id"));
            this.popupModel.put("fromDateValue", model.get("fromDate"));
            if (model.get("interestRate") instanceof Double) {
                this.popupModel.put("interestRateValue", model.get("interestRate"));
            } else if (model.get("interestRate") instanceof BigDecimal) {
                this.popupModel.put("interestRateValue", ((BigDecimal) model.get("interestRate")).doubleValue());
            }
            this.popupModel.put("differentialValue", model.get("differential"));
            this.ratePopup.setContent(new RateModifyPopup(this.ratePopup.getContentId(), this.ratePopup, this.popupModel));
            this.ratePopup.show(target);
        }
    }

    protected List<ActionItem> rateAction(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        if (model.get("id") == null) {
            actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        } else {
            actions.add(new ActionItem("modify", Model.of("Modify"), ItemCss.INFO));
        }
        return actions;
    }

    protected ItemPanel rateColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("fromDate".equals(column)) {
            Date value = (Date) model.get(column);
            return new TextCell(value, "dd/MM/yy");
        } else if ("interestRate".equals(column)) {
            Double value = (Double) model.get(column);
            return new TextCell(value);
        } else if ("differential".equals(column)) {
            Boolean differential = (Boolean) model.get(column);
            if (differential != null && differential) {
                return new BadgeCell(BadgeType.Success, Model.of("Yes"));
            } else {
                return new BadgeCell(BadgeType.Danger, Model.of("No"));
            }
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected boolean addButtonSubmit(AjaxButton button, AjaxRequestTarget target) {
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        Map<String, Object> rate = Maps.newHashMap();
        rate.put("uuid", generator.externalId());
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

    protected void saveButtonSubmit(Button button) {
        FloatingRateBuilder builder = new FloatingRateBuilder();
        builder.withId(this.rateId);
        builder.withActive(this.activeValue);
        builder.withBaseLendingRate(this.baseLendingValue);
        builder.withName(this.nameValue);
        for (Map<String, Object> rate : this.rateValue) {
            if (rate.get("interestRate") instanceof Double) {
                builder.withRatePeriod((Date) rate.get("fromDate"), (Double) rate.get("interestRate"), (Boolean) rate.get("differential"));
            } else if (rate.get("interestRate") instanceof BigDecimal) {
                builder.withRatePeriod((Date) rate.get("fromDate"), ((BigDecimal) rate.get("interestRate")).doubleValue(), (Boolean) rate.get("differential"));
            }
        }

        JsonNode node = null;
        try {
            node = FloatingRateHelper.update((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(FloatingRateBrowsePage.class);
    }

}
