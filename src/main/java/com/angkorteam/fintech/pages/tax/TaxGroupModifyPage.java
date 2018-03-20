package com.angkorteam.fintech.pages.tax;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.MTaxComponent;
import com.angkorteam.fintech.ddl.MTaxGroup;
import com.angkorteam.fintech.ddl.MTaxGroupMappings;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.TaxGroupBuilder;
import com.angkorteam.fintech.helper.TaxGroupHelper;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.pages.TaxDashboardPage;
import com.angkorteam.fintech.popup.TaxComponentCreatePopup;
import com.angkorteam.fintech.popup.TaxComponentPopup;
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
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
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;

/**
 * Created by socheatkhauv on 7/16/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class TaxGroupModifyPage extends Page {

    protected String taxId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock nameBlock;
    protected WebMarkupContainer nameIContainer;
    protected String nameValue;
    protected TextField<String> nameField;
    protected TextFeedbackPanel nameFeedback;

    protected WebMarkupBlock taxComponentBlock;
    protected WebMarkupContainer taxComponentIContainer;
    protected List<Map<String, Object>> taxComponentValue = Lists.newArrayList();
    protected DataTable<Map<String, Object>, String> taxComponentTable;
    protected ListDataProvider taxComponentProvider;
    protected List<IColumn<Map<String, Object>, String>> taxComponentColumn;

    protected AjaxLink<Void> taxComponentAddLink;

    protected ModalWindow modalWindow;

    protected Map<String, Object> popupModel;

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
            breadcrumb.setLabel("Tax Group Modify");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initData() {
        this.popupModel = Maps.newHashMap();

        PageParameters parameters = getPageParameters();
        this.taxId = parameters.get("taxId").toString("");

        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery(MTaxGroup.NAME);
        selectQuery.addField(MTaxGroup.Field.NAME);
        selectQuery.addWhere(MTaxGroup.Field.ID + " = :" + MTaxGroup.Field.ID, this.taxId);
        Map<String, Object> taxObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        this.nameValue = (String) taxObject.get(MTaxGroup.Field.NAME);

        selectQuery = new SelectQuery(MTaxGroupMappings.NAME);
        selectQuery.addJoin("INNER JOIN " + MTaxComponent.NAME + " ON " + MTaxGroupMappings.NAME + "." + MTaxGroupMappings.Field.TAX_COMPONENT_ID + " = " + MTaxComponent.NAME + "." + MTaxComponent.Field.ID);
        selectQuery.addField("IF(" + MTaxGroupMappings.NAME + "." + MTaxGroupMappings.Field.END_DATE + " IS NULL, 'Y', 'N') allow");
        selectQuery.addField(MTaxGroupMappings.NAME + "." + MTaxGroupMappings.Field.ID);
        selectQuery.addField("CONCAT(" + MTaxGroupMappings.NAME + "." + MTaxGroupMappings.Field.ID + ", '') AS uuid");
        selectQuery.addField("CONCAT(" + MTaxComponent.NAME + "." + MTaxComponent.Field.ID + ", '') AS taxComponentId");
        selectQuery.addField("CONCAT(" + MTaxComponent.NAME + "." + MTaxComponent.Field.NAME + ", '') AS tax");
        selectQuery.addField(MTaxGroupMappings.NAME + "." + MTaxGroupMappings.Field.START_DATE + " AS startDate");
        selectQuery.addField(MTaxGroupMappings.NAME + "." + MTaxGroupMappings.Field.END_DATE + " AS endDate");
        selectQuery.addWhere(MTaxGroupMappings.Field.TAX_GROUP_ID + " = :" + MTaxGroupMappings.Field.TAX_GROUP_ID, this.taxId);

        List<Map<String, Object>> groups = named.queryForList(selectQuery.toSQL(), selectQuery.getParam());
        this.taxComponentValue.addAll(groups);
    }

    @Override
    protected void initComponent() {

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", TaxGroupBrowsePage.class);
        this.form.add(this.closeLink);

        initNameBlock();

        initTaxComponentBlock();

        this.modalWindow = new ModalWindow("modalWindow");
        add(this.modalWindow);
        this.modalWindow.setOnClose(this::modalWindowClose);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected void initNameBlock() {
        this.nameBlock = new WebMarkupBlock("nameBlock", Size.Twelve_12);
        this.form.add(this.nameBlock);
        this.nameIContainer = new WebMarkupContainer("nameIContainer");
        this.nameBlock.add(this.nameIContainer);
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setRequired(true);
        this.nameIContainer.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.nameIContainer.add(this.nameFeedback);
    }

    protected void initTaxComponentBlock() {
        this.taxComponentBlock = new WebMarkupBlock("taxComponentBlock", Size.Twelve_12);
        this.form.add(this.taxComponentBlock);
        this.taxComponentIContainer = new WebMarkupContainer("taxComponentIContainer");
        this.taxComponentBlock.add(this.taxComponentIContainer);
        this.taxComponentColumn = Lists.newArrayList();
        this.taxComponentColumn.add(new TextColumn(Model.of("Tax Component"), "tax", "tax", this::taxComponentColumn));
        this.taxComponentColumn.add(new TextColumn(Model.of("Start Date"), "startDate", "startDate", this::taxComponentColumn));
        this.taxComponentColumn.add(new TextColumn(Model.of("End Date"), "endDate", "endDate", this::taxComponentColumn));
        this.taxComponentColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::taxComponentAction, this::taxComponentClick));
        this.taxComponentProvider = new ListDataProvider(this.taxComponentValue);
        this.taxComponentTable = new DataTable<>("taxComponentTable", this.taxComponentColumn, this.taxComponentProvider, 20);
        this.taxComponentIContainer.add(this.taxComponentTable);
        this.taxComponentTable.addTopToolbar(new HeadersToolbar<>(this.taxComponentTable, this.taxComponentProvider));
        this.taxComponentTable.addBottomToolbar(new NoRecordsToolbar(this.taxComponentTable));

        this.taxComponentAddLink = new AjaxLink<>("taxComponentAddLink");
        this.taxComponentAddLink.setOnClick(this::taxComponentAddLinkClick);
        this.taxComponentIContainer.add(this.taxComponentAddLink);
    }

    protected boolean taxComponentAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        this.modalWindow.setContent(new TaxComponentCreatePopup("create", this.popupModel));
        this.modalWindow.show(target);
        return false;
    }

    protected void modalWindowClose(String popupName, String signalId, AjaxRequestTarget target) {
        if ("update".equals(popupName)) {
            for (Map<String, Object> item : this.taxComponentValue) {
                if (this.popupModel.get("idValue").equals(item.get("id"))) {
                    item.put("endDate", this.popupModel.get("endDateValue"));
                    break;
                }
            }
            target.add(this.taxComponentTable);
        } else if ("create".equals(popupName)) {
            StringGenerator generator = SpringBean.getBean(StringGenerator.class);
            Map<String, Object> tax = Maps.newHashMap();
            tax.put("uuid", generator.externalId());
            tax.put("taxComponentId", ((Option) this.popupModel.get("taxValue")).getId());
            tax.put("tax", ((Option) this.popupModel.get("taxValue")).getText());
            tax.put("startDate", this.popupModel.get("startDateValue"));
            this.taxComponentValue.add(tax);
            target.add(this.taxComponentTable);
        }
    }

    protected ItemPanel taxComponentColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("tax".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("startDate".equals(column) || "endDate".equals(column)) {
            Date value = (Date) model.get(column);
            return new TextCell(value, "dd/MM/yyyy");
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected void taxComponentClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        if ("delete".equals(s)) {
            int index = -1;
            for (int i = 0; i < this.taxComponentValue.size(); i++) {
                Map<String, Object> column = this.taxComponentValue.get(i);
                if (model.get("uuid").equals(column.get("uuid"))) {
                    index = i;
                    break;
                }
            }
            if (index >= 0) {
                this.taxComponentValue.remove(index);
            }
            target.add(this.taxComponentTable);
        } else if ("modify".equals(s)) {
            this.popupModel.clear();
            this.popupModel.put("idValue", model.get("id"));
            this.popupModel.put("endDateValue", model.get("endDate"));
            this.popupModel.put("startDateValue", DateFormatUtils.format((Date) model.get("startDate"), "yyyy-MM-dd"));
            this.modalWindow.setContent(new TaxComponentPopup("update", this.popupModel));
            this.modalWindow.show(target);
        }
    }

    protected List<ActionItem> taxComponentAction(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        if (model.get("id") != null) {
            if (!"N".equals(model.get("allow"))) {
                actions.add(new ActionItem("modify", Model.of("Modify"), ItemCss.INFO));
            }
        } else {
            actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        }
        return actions;
    }

    protected void saveButtonSubmit(Button button) {
        TaxGroupBuilder builder = new TaxGroupBuilder();
        builder.withId(this.taxId);
        builder.withName(this.nameValue);
        for (Map<String, Object> tax : this.taxComponentValue) {
            Long id = (Long) tax.get("id");
            String taxComponentId = (String) tax.get("taxComponentId");
            builder.withTaxComponent(id == null ? null : String.valueOf(id), taxComponentId, (Date) tax.get("startDate"), (Date) tax.get("endDate"));
        }

        JsonNode node = TaxGroupHelper.update((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(TaxGroupBrowsePage.class);
    }

}
