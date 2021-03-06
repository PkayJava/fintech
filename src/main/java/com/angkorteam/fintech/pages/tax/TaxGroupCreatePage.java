package com.angkorteam.fintech.pages.tax;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.TaxGroupBuilder;
import com.angkorteam.fintech.helper.TaxGroupHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.pages.TaxDashboardPage;
import com.angkorteam.fintech.popup.TaxComponentCreatePopup;
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.share.provider.ListDataProvider;
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

import io.github.openunirest.http.JsonNode;

/**
 * Created by socheatkhauv on 7/16/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class TaxGroupCreatePage extends Page {

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected UIRow row1;

    protected UIBlock nameBlock;
    protected UIContainer nameIContainer;
    protected String nameValue;
    protected TextField<String> nameField;
    protected TextFeedbackPanel nameFeedback;

    protected UIRow row2;

    protected UIBlock taxComponentBlock;
    protected UIContainer taxComponentIContainer;
    protected List<Map<String, Object>> taxComponentValue;
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
            breadcrumb.setLabel("Tax Group Create");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initData() {
        this.popupModel = Maps.newHashMap();

        this.taxComponentColumn = Lists.newArrayList();
        this.taxComponentColumn.add(new TextColumn(Model.of("Tax Component"), "tax", "tax", this::taxComponentColumn));
        this.taxComponentColumn.add(new TextColumn(Model.of("Start Date"), "startDate", "startDate", this::taxComponentColumn));
        this.taxComponentColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::taxComponentAction, this::taxComponentClick));

        this.taxComponentValue = Lists.newArrayList();
        this.taxComponentProvider = new ListDataProvider(this.taxComponentValue);
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

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.nameBlock = this.row1.newUIBlock("nameBlock", Size.Twelve_12);
        this.nameIContainer = this.nameBlock.newUIContainer("nameIContainer");
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameIContainer.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.nameIContainer.add(this.nameFeedback);

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.taxComponentBlock = this.row2.newUIBlock("taxComponentBlock", Size.Twelve_12);
        this.taxComponentIContainer = this.taxComponentBlock.newUIContainer("taxComponentIContainer");
        this.taxComponentTable = new DataTable<>("taxComponentTable", taxComponentColumn, this.taxComponentProvider, 20);
        this.taxComponentIContainer.add(this.taxComponentTable);
        this.taxComponentTable.addTopToolbar(new HeadersToolbar<>(this.taxComponentTable, this.taxComponentProvider));
        this.taxComponentTable.addBottomToolbar(new NoRecordsToolbar(this.taxComponentTable));
        this.taxComponentAddLink = new AjaxLink<>("taxComponentAddLink");
        this.taxComponentAddLink.setOnClick(this::taxComponentAddLinkClick);
        this.taxComponentIContainer.add(this.taxComponentAddLink);

        this.modalWindow = new ModalWindow("modalWindow");
        add(this.modalWindow);
        this.modalWindow.setOnClose(this::modalWindowClose);
    }

    protected void modalWindowClose(String popupName, String signalId, AjaxRequestTarget target) {
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        Map<String, Object> tax = Maps.newHashMap();
        tax.put("uuid", generator.externalId());
        tax.put("taxComponentId", ((Option) this.popupModel.get("taxValue")).getId());
        tax.put("tax", ((Option) this.popupModel.get("taxValue")).getText());
        tax.put("startDate", this.popupModel.get("startDateValue"));
        this.taxComponentValue.add(tax);
        target.add(this.taxComponentTable);
    }

    @Override
    protected void configureMetaData() {
        this.nameField.setRequired(true);
    }

    protected boolean taxComponentAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        this.modalWindow.setContent(new TaxComponentCreatePopup("add", this.popupModel));
        this.modalWindow.show(target);
        return false;
    }

    protected ItemPanel taxComponentColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("tax".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("startDate".equals(column)) {
            Date value = (Date) model.get(column);
            return new TextCell(value, "dd/MM/yyyy");
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected void taxComponentClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
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
    }

    protected List<ActionItem> taxComponentAction(String s, Map<String, Object> model) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected void saveButtonSubmit(Button button) {
        TaxGroupBuilder builder = new TaxGroupBuilder();
        builder.withName(this.nameValue);
        for (Map<String, Object> tax : this.taxComponentValue) {
            builder.withTaxComponent(null, (String) tax.get("taxComponentId"), (Date) tax.get("startDate"), null);
        }

        JsonNode node = TaxGroupHelper.create((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(TaxGroupBrowsePage.class);
    }
}
