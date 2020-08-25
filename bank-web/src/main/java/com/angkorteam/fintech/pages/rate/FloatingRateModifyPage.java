//package com.angkorteam.fintech.pages.rate;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.lang3.time.DateFormatUtils;
//import org.apache.wicket.WicketRuntimeException;
//import org.apache.wicket.ajax.AjaxRequestTarget;
//import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
//import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
//import com.angkorteam.fintech.widget.WebMarkupContainer;
//import org.apache.wicket.markup.html.form.CheckBox;
//import org.apache.wicket.markup.html.form.TextField;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.IModel;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//import org.apache.wicket.request.mapper.parameter.PageParameters;
//
//import com.angkorteam.fintech.Page;
//import com.angkorteam.fintech.Session;
//import com.angkorteam.fintech.ddl.MFloatingRates;
//import com.angkorteam.fintech.ddl.MFloatingRatesPeriods;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.dto.builder.FloatingRateBuilder;
//import com.angkorteam.fintech.helper.FloatingRateHelper;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.pages.ProductDashboardPage;
//import com.angkorteam.fintech.popup.FloatingRatePeriodPopup;
//import com.angkorteam.fintech.spring.StringGenerator;
//import com.angkorteam.fintech.table.BadgeCell;
//import com.angkorteam.fintech.table.TextCell;
//import com.angkorteam.fintech.widget.TextFeedbackPanel;
//import com.angkorteam.fintech.widget.WebMarkupBlock;
//import com.angkorteam.framework.BadgeType;
//import com.angkorteam.framework.SpringBean;
//import com.angkorteam.framework.jdbc.SelectQuery;
//import com.angkorteam.framework.models.PageBreadcrumb;
//import com.angkorteam.framework.share.provider.ListDataProvider;
//import com.angkorteam.framework.spring.JdbcNamed;
//import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
//import com.angkorteam.framework.wicket.extensions.ajax.markup.html.modal.ModalWindow;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.TextColumn;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionFilterColumn;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionItem;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemCss;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//import io.github.openunirest.http.JsonNode;
//
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class FloatingRateModifyPage extends Page {
//
//    protected String rateId;
//
//    protected Form<Void> form;
//    protected Button saveButton;
//    protected BookmarkablePageLink<Void> closeLink;
//
//    protected WebMarkupBlock nameBlock;
//    protected WebMarkupContainer nameIContainer;
//    protected String nameValue;
//    protected TextField<String> nameField;
//    protected TextFeedbackPanel nameFeedback;
//
//    protected WebMarkupBlock activeBlock;
//    protected WebMarkupContainer activeIContainer;
//    protected Boolean activeValue;
//    protected CheckBox activeField;
//    protected TextFeedbackPanel activeFeedback;
//
//    protected WebMarkupBlock baseLendingBlock;
//    protected WebMarkupContainer baseLendingIContainer;
//    protected Boolean baseLendingValue;
//    protected CheckBox baseLendingField;
//    protected TextFeedbackPanel baseLendingFeedback;
//
//    protected WebMarkupBlock rateBlock;
//    protected WebMarkupContainer rateIContainer;
//    protected List<Map<String, Object>> rateValue = Lists.newArrayList();
//    protected DataTable<Map<String, Object>, String> rateTable;
//    protected ListDataProvider rateProvider;
//    protected List<IColumn<Map<String, Object>, String>> rateColumn;
//
//    protected AjaxLink<Void> rateAddLink;
//
//    protected ModalWindow ratePopup;
//
//    protected Map<String, Object> popupModel;
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
//            breadcrumb.setLabel("Product");
//            breadcrumb.setPage(ProductDashboardPage.class);
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Floating Rate");
//            breadcrumb.setPage(FloatingRateBrowsePage.class);
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Floating Rate Modify");
//            BREADCRUMB.add(breadcrumb);
//        }
//        return Model.ofList(BREADCRUMB);
//    }
//
//    @Override
//    protected void initData() {
//        this.popupModel = Maps.newHashMap();
//        PageParameters parameters = getPageParameters();
//        this.rateId = parameters.get("rateId").toString("");
//
//        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
//
//        SelectQuery selectQuery = null;
//
//        selectQuery = new SelectQuery(MFloatingRates.NAME);
//        selectQuery.addWhere(MFloatingRates.Field.ID + " = :" + MFloatingRates.Field.ID, this.rateId);
//        selectQuery.addField(MFloatingRates.Field.NAME);
//        selectQuery.addField(MFloatingRates.Field.IS_ACTIVE);
//        selectQuery.addField(MFloatingRates.Field.IS_BASE_LENDING_RATE);
//
//        Map<String, Object> rateObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
//        this.nameValue = (String) rateObject.get(MFloatingRates.Field.NAME);
//        this.activeValue = (Boolean) rateObject.get(MFloatingRates.Field.IS_ACTIVE);
//        this.baseLendingValue = (Boolean) rateObject.get(MFloatingRates.Field.IS_BASE_LENDING_RATE);
//
//        this.rateValue.clear();
//        selectQuery = new SelectQuery(MFloatingRatesPeriods.NAME);
//        selectQuery.addField(MFloatingRatesPeriods.Field.ID);
//        selectQuery.addField(MFloatingRatesPeriods.Field.FROM_DATE + " as fromDate");
//        selectQuery.addField("concat(" + MFloatingRatesPeriods.Field.ID + ", '')" + " as uuid");
//        selectQuery.addField(MFloatingRatesPeriods.Field.INTEREST_RATE + " as interestRate");
//        selectQuery.addField(MFloatingRatesPeriods.Field.IS_DIFFERENTIAL_TO_BASE_LENDING_RATE + " as differential");
//        selectQuery.addWhere(MFloatingRatesPeriods.Field.FLOATING_RATES_ID + " = :" + MFloatingRatesPeriods.Field.FLOATING_RATES_ID, this.rateId);
//        List<Map<String, Object>> rates = named.queryForList(selectQuery.toSQL(), selectQuery.getParam());
//        List<String> dates = Lists.newArrayList();
//        for (Map<String, Object> rate : rates) {
//            String date = DateFormatUtils.format((Date) rate.get("fromDate"), "yyyy-MM-dd");
//            if (dates.contains(date)) {
//                continue;
//            }
//            dates.add(date);
//            this.rateValue.add(rate);
//        }
//    }
//
//    @Override
//    protected void initComponent() {
//
//        this.form = new Form<>("form");
//        add(this.form);
//
//        this.saveButton = new Button("saveButton");
//        this.saveButton.setOnSubmit(this::saveButtonSubmit);
//        this.form.add(this.saveButton);
//
//        this.closeLink = new BookmarkablePageLink<>("closeLink", FloatingRateBrowsePage.class);
//        this.form.add(this.closeLink);
//
//        initNameBlock();
//
//        initActiveBlock();
//
//        initBaseLendingBlock();
//
//        initRateBlock();
//
//        this.ratePopup = new ModalWindow("ratePopup");
//        add(this.ratePopup);
//        this.ratePopup.setOnClose(this::ratePopupClose);
//    }
//
//    protected void initRateBlock() {
//        this.rateBlock = new WebMarkupBlock("rateBlock", Size.Twelve_12);
//        this.form.add(this.rateBlock);
//        this.rateIContainer = new WebMarkupContainer("rateIContainer");
//        this.rateBlock.add(this.rateIContainer);
//        this.rateColumn = Lists.newArrayList();
//        this.rateColumn.add(new TextColumn(Model.of("From Date"), "fromDate", "fromDate", this::rateColumn));
//        this.rateColumn.add(new TextColumn(Model.of("Interest Rate"), "interestRate", "interestRate", this::rateColumn));
//        this.rateColumn.add(new TextColumn(Model.of("Is Differential"), "differential", "differential", this::rateColumn));
//        this.rateColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::rateAction, this::rateClick));
//        this.rateProvider = new ListDataProvider(this.rateValue);
//        this.rateTable = new DataTable<>("rateTable", this.rateColumn, this.rateProvider, 20);
//        this.rateIContainer.add(this.rateTable);
//        this.rateTable.addTopToolbar(new HeadersToolbar<>(this.rateTable, this.rateProvider));
//        this.rateTable.addBottomToolbar(new NoRecordsToolbar(this.rateTable));
//
//        this.rateAddLink = new AjaxLink<>("rateAddLink");
//        this.rateAddLink.setOnClick(this::rateAddLinkClick);
//        this.rateIContainer.add(this.rateAddLink);
//    }
//
//    protected boolean rateAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
//        this.popupModel.clear();
//        this.ratePopup.setContent(new FloatingRatePeriodPopup("newRatePopup", this.popupModel));
//        this.ratePopup.show(target);
//        return false;
//    }
//
//    protected void initBaseLendingBlock() {
//        this.baseLendingBlock = new WebMarkupBlock("baseLendingBlock", Size.Four_4);
//        this.form.add(this.baseLendingBlock);
//        this.baseLendingIContainer = new WebMarkupContainer("baseLendingIContainer");
//        this.baseLendingBlock.add(this.baseLendingIContainer);
//        this.baseLendingField = new CheckBox("baseLendingField", new PropertyModel<>(this, "baseLendingValue"));
//        this.baseLendingField.setRequired(true);
//        this.baseLendingIContainer.add(this.baseLendingField);
//        this.baseLendingFeedback = new TextFeedbackPanel("baseLendingFeedback", this.baseLendingField);
//        this.baseLendingIContainer.add(this.baseLendingFeedback);
//    }
//
//    protected void initActiveBlock() {
//        this.activeBlock = new WebMarkupBlock("activeBlock", Size.Four_4);
//        this.form.add(this.activeBlock);
//        this.activeIContainer = new WebMarkupContainer("activeIContainer");
//        this.activeBlock.add(this.activeIContainer);
//        this.activeField = new CheckBox("activeField", new PropertyModel<>(this, "activeValue"));
//        this.activeField.setRequired(true);
//        this.activeIContainer.add(this.activeField);
//        this.activeFeedback = new TextFeedbackPanel("activeFeedback", this.activeField);
//        this.activeIContainer.add(this.activeFeedback);
//    }
//
//    protected void initNameBlock() {
//        this.nameBlock = new WebMarkupBlock("nameBlock", Size.Four_4);
//        this.form.add(this.nameBlock);
//        this.nameIContainer = new WebMarkupContainer("nameIContainer");
//        this.nameBlock.add(this.nameIContainer);
//        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
//        this.nameField.setRequired(true);
//        this.nameIContainer.add(this.nameField);
//        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
//        this.nameIContainer.add(this.nameFeedback);
//    }
//
//    @Override
//    protected void configureMetaData() {
//    }
//
//    protected void ratePopupClose(String popupName, String signalId, AjaxRequestTarget target) {
//        if ("modifyRatePopup".equals(popupName)) {
//            for (Map<String, Object> item : this.rateValue) {
//                if (this.popupModel.get("idValue").equals(item.get("id"))) {
//                    item.put("fromDate", this.popupModel.get("fromDateValue"));
//                    item.put("interestRate", this.popupModel.get("interestRateValue"));
//                    item.put("differential", this.popupModel.get("differentialValue"));
//                    break;
//                }
//            }
//            target.add(this.rateTable);
//        } else if ("newRatePopup".equals(popupName)) {
//            StringGenerator generator = SpringBean.getBean(StringGenerator.class);
//            Map<String, Object> rate = Maps.newHashMap();
//            rate.put("uuid", generator.externalId());
//            rate.put("fromDate", this.popupModel.get("fromDateValue"));
//            rate.put("interestRate", this.popupModel.get("interestRateValue"));
//            rate.put("differential", this.popupModel.get("differentialValue"));
//            this.rateValue.add(rate);
//            target.add(this.rateTable);
//
//        }
//    }
//
//    protected void rateClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
//        if ("delete".equals(s)) {
//            int index = -1;
//            for (int i = 0; i < this.rateValue.size(); i++) {
//                Map<String, Object> column = this.rateValue.get(i);
//                if (model.get("uuid").equals(column.get("uuid"))) {
//                    index = i;
//                    break;
//                }
//            }
//            if (index >= 0) {
//                this.rateValue.remove(index);
//            }
//            target.add(this.rateTable);
//        } else if ("modify".equals(s)) {
//            this.popupModel.clear();
//            this.popupModel.put("idValue", model.get("id"));
//            this.popupModel.put("fromDateValue", model.get("fromDate"));
//            this.popupModel.put("interestRateValue", (Double) model.get("interestRate"));
//            this.popupModel.put("differentialValue", model.get("differential"));
//            this.ratePopup.setContent(new FloatingRatePeriodPopup("modifyRatePopup", this.popupModel));
//            this.ratePopup.show(target);
//        }
//    }
//
//    protected List<ActionItem> rateAction(String s, Map<String, Object> model) {
//        List<ActionItem> actions = Lists.newArrayList();
//        if (model.get("id") == null) {
//            actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
//        } else {
//            actions.add(new ActionItem("modify", Model.of("Modify"), ItemCss.INFO));
//        }
//        return actions;
//    }
//
//    protected ItemPanel rateColumn(String column, IModel<String> display, Map<String, Object> model) {
//        if ("fromDate".equals(column)) {
//            Date value = (Date) model.get(column);
//            return new TextCell(value, "dd/MM/yy");
//        } else if ("interestRate".equals(column)) {
//            Double value = (Double) model.get(column);
//            return new TextCell(value);
//        } else if ("differential".equals(column)) {
//            Boolean differential = (Boolean) model.get(column);
//            if (differential != null && differential) {
//                return new BadgeCell(BadgeType.Success, Model.of("Yes"));
//            } else {
//                return new BadgeCell(BadgeType.Danger, Model.of("No"));
//            }
//        }
//        throw new WicketRuntimeException("Unknown " + column);
//    }
//
//    protected void saveButtonSubmit(Button button) {
//        FloatingRateBuilder builder = new FloatingRateBuilder();
//        builder.withId(this.rateId);
//        builder.withActive(this.activeValue);
//        builder.withBaseLendingRate(this.baseLendingValue);
//        builder.withName(this.nameValue);
//        for (Map<String, Object> rate : this.rateValue) {
//            builder.withRatePeriod((Date) rate.get("fromDate"), (Double) rate.get("interestRate"), (Boolean) rate.get("differential"));
//        }
//
//        JsonNode node = FloatingRateHelper.update((Session) getSession(), builder.build());
//
//        if (reportError(node)) {
//            return;
//        }
//        setResponsePage(FloatingRateBrowsePage.class);
//    }
//
//}
