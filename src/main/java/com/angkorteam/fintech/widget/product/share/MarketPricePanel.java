package com.angkorteam.fintech.widget.product.share;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.product.share.ShareBrowsePage;
import com.angkorteam.fintech.pages.product.share.ShareCreatePage;
import com.angkorteam.fintech.popup.MarketPricePopup;
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.Panel;
import com.angkorteam.framework.SpringBean;
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
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class MarketPricePanel extends Panel {

    protected Page itemPage;
    protected PropertyModel<AjaxTabbedPanel<ITab>> tab;
    protected PropertyModel<Boolean> errorMarketPrice;

    protected Form<Void> form;
    protected Button nextButton;
    protected AjaxLink<Void> backLink;
    protected BookmarkablePageLink<Void> closeLink;

    protected ModalWindow modalWindow;
    protected Map<String, Object> popupModel;

    protected UIRow row1;

    protected List<IColumn<Map<String, Object>, String>> marketPriceColumn;
    protected UIBlock marketPriceBlock;
    protected UIContainer marketPriceIContainer;
    protected DataTable<Map<String, Object>, String> marketPriceTable;
    protected ListDataProvider marketPriceProvider;
    protected AjaxLink<Void> marketPriceAddLink;
    protected PropertyModel<List<Map<String, Object>>> marketPriceValue;

    public MarketPricePanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void initData() {
        this.errorMarketPrice = new PropertyModel<>(this.itemPage, "errorMarketPrice");
        this.popupModel = new HashMap<>();
        this.tab = new PropertyModel<>(this.itemPage, "tab");
        this.marketPriceValue = new PropertyModel<>(this.itemPage, "marketPriceValue");

        this.marketPriceValue = new PropertyModel<>(this.itemPage, "marketPriceValue");
        this.marketPriceProvider = new ListDataProvider(this.marketPriceValue.getObject());
        this.marketPriceColumn = Lists.newArrayList();
        this.marketPriceColumn.add(new TextColumn(Model.of("From Date"), "fromDate", "fromDate", this::marketPriceColumn));
        this.marketPriceColumn.add(new TextColumn(Model.of("Nominal/Unit Price"), "unitPrice", "unitPrice", this::marketPriceColumn));
        this.marketPriceColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::marketPriceAction, this::marketPriceClick));
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.nextButton = new Button("nextButton");
        this.nextButton.setOnSubmit(this::nextButtonSubmit);
        this.nextButton.setOnError(this::nextButtonError);
        this.form.add(this.nextButton);

        this.backLink = new AjaxLink<>("backLink");
        this.backLink.setOnClick(this::backLinkClick);
        this.form.add(this.backLink);

        this.closeLink = new BookmarkablePageLink<>("closeLink", ShareBrowsePage.class);
        this.form.add(this.closeLink);

        this.modalWindow = new ModalWindow("modalWindow");
        add(this.modalWindow);
        this.modalWindow.setOnClose(this::modalWindowClose);

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.marketPriceBlock = this.row1.newUIBlock("marketPriceBlock", Size.Twelve_12);
        this.marketPriceIContainer = this.marketPriceBlock.newUIContainer("marketPriceIContainer");
        this.marketPriceTable = new DataTable<>("marketPriceTable", this.marketPriceColumn, this.marketPriceProvider, 20);
        this.marketPriceIContainer.add(this.marketPriceTable);
        this.marketPriceTable.addTopToolbar(new HeadersToolbar<>(this.marketPriceTable, this.marketPriceProvider));
        this.marketPriceTable.addBottomToolbar(new NoRecordsToolbar(this.marketPriceTable));
        this.marketPriceAddLink = new AjaxLink<>("marketPriceAddLink");
        this.marketPriceAddLink.setOnClick(this::marketPriceAddLinkClick);
        this.marketPriceIContainer.add(this.marketPriceAddLink);
    }

    @Override
    protected void configureMetaData() {
    }

    protected ItemPanel marketPriceColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("fromDate".equals(column)) {
            Date value = (Date) model.get(column);
            return new TextCell(value, "dd/MM/yyyy");
        } else if ("unitPrice".equals(column)) {
            Double value = (Double) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected void marketPriceClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        int index = -1;
        for (int i = 0; i < this.marketPriceValue.getObject().size(); i++) {
            Map<String, Object> column = this.marketPriceValue.getObject().get(i);
            if (model.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.marketPriceValue.getObject().remove(index);
        }
        target.add(this.marketPriceTable);
    }

    protected List<ActionItem> marketPriceAction(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    protected void nextButtonSubmit(Button button) {
        this.tab.getObject().setSelectedTab(ShareCreatePage.TAB_CHARGE);
        this.errorMarketPrice.setObject(false);
    }

    protected void nextButtonError(Button button) {
        this.errorMarketPrice.setObject(true);
    }

    protected boolean marketPriceAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        this.modalWindow.setContent(new MarketPricePopup("marketPrice", this.popupModel));
        this.modalWindow.show(target);
        return false;
    }

    protected boolean backLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.tab.getObject().setSelectedTab(ShareCreatePage.TAB_SETTING);
        if (target != null) {
            target.add(this.tab.getObject());
        }
        return false;
    }

    protected void modalWindowClose(String popupName, String signalId, AjaxRequestTarget target) {
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        Map<String, Object> item = Maps.newHashMap();
        item.put("uuid", generator.externalId());
        item.put("unitPrice", this.popupModel.get("unitPriceValue"));
        item.put("fromDate", this.popupModel.get("fromDateValue"));
        this.marketPriceValue.getObject().add(item);
        if (target != null) {
            target.add(this.marketPriceTable);
        }
    }

}
