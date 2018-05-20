package com.angkorteam.fintech.widget.product.fixed;

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

import com.angkorteam.fintech.ddl.MCharge;
import com.angkorteam.fintech.dto.enums.ChargeCalculation;
import com.angkorteam.fintech.dto.enums.ChargeTime;
import com.angkorteam.fintech.dto.enums.ProductPopup;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.product.fixed.FixedBrowsePage;
import com.angkorteam.fintech.pages.product.fixed.FixedCreatePage;
import com.angkorteam.fintech.popup.ChargePopup;
import com.angkorteam.fintech.popup.CurrencyPopup;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.Panel;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
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
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ChargesPanel extends Panel {

    protected Page itemPage;
    protected PropertyModel<AjaxTabbedPanel<ITab>> tab;
    protected PropertyModel<Boolean> errorCharge;

    protected Form<Void> form;
    protected Button nextButton;
    protected AjaxLink<Void> backLink;
    protected BookmarkablePageLink<Void> closeLink;

    protected ModalWindow modalWindow;
    protected Map<String, Object> popupModel;

    protected UIRow row1;

    protected List<IColumn<Map<String, Object>, String>> chargeColumn;
    protected UIBlock chargeBlock;
    protected UIContainer chargeIContainer;
    protected DataTable<Map<String, Object>, String> chargeTable;
    protected ListDataProvider chargeProvider;
    protected AjaxLink<Void> chargeAddLink;
    protected PropertyModel<List<Map<String, Object>>> chargeValue;

    public ChargesPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void initData() {
        this.errorCharge = new PropertyModel<>(this.itemPage, "errorCharge");
        this.popupModel = new HashMap<>();
        this.chargeValue = new PropertyModel<>(this.itemPage, "chargeValue");
        this.tab = new PropertyModel<>(this.itemPage, "tab");

        this.chargeValue = new PropertyModel<>(this.itemPage, "chargeValue");
        this.chargeProvider = new ListDataProvider(this.chargeValue.getObject());
        this.chargeColumn = Lists.newArrayList();
        this.chargeColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Type"), "type", "type", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Collected On"), "collect", "collect", this::chargeColumn));
        this.chargeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::chargeAction, this::chargeClick));
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

        this.closeLink = new BookmarkablePageLink<>("closeLink", FixedBrowsePage.class);
        this.form.add(this.closeLink);

        this.modalWindow = new ModalWindow("modalWindow");
        add(this.modalWindow);
        this.modalWindow.setOnClose(this::modalWindowClose);

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.chargeBlock = this.row1.newUIBlock("chargeBlock", Size.Twelve_12);
        this.chargeIContainer = this.chargeBlock.newUIContainer("chargeIContainer");
        this.chargeTable = new DataTable<>("chargeTable", this.chargeColumn, this.chargeProvider, 20);
        this.chargeIContainer.add(this.chargeTable);
        this.chargeTable.addTopToolbar(new HeadersToolbar<>(this.chargeTable, this.chargeProvider));
        this.chargeTable.addBottomToolbar(new NoRecordsToolbar(this.chargeTable));
        this.chargeAddLink = new AjaxLink<>("chargeAddLink");
        this.chargeAddLink.setOnClick(this::chargeAddLinkClick);
        this.chargeIContainer.add(this.chargeAddLink);
    }

    @Override
    protected void configureMetaData() {
    }

    protected ItemPanel chargeColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("name".equals(column) || "date".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("type".equals(column) || "collect".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        } else if ("amount".equals(column)) {
            Number value = (Number) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected void chargeClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        int index = -1;
        for (int i = 0; i < this.chargeValue.getObject().size(); i++) {
            Map<String, Object> column = this.chargeValue.getObject().get(i);
            if (model.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.chargeValue.getObject().remove(index);
        }
        target.add(this.chargeTable);
    }

    protected List<ActionItem> chargeAction(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    protected void nextButtonSubmit(Button button) {
        this.errorCharge.setObject(false);
        this.tab.getObject().setSelectedTab(FixedCreatePage.TAB_ACCOUNTING);
    }

    protected void nextButtonError(Button button) {
        this.errorCharge.setObject(true);
    }

    protected boolean chargeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        PropertyModel<Option> currencyCodeValue = new PropertyModel<>(this.itemPage, "currencyCodeValue");
        if (currencyCodeValue.getObject() != null) {
            this.modalWindow.setContent(new ChargePopup("charge", this.popupModel, ProductPopup.Fixed, currencyCodeValue.getObject().getId()));
            this.modalWindow.show(target);
        } else {
            this.modalWindow.setContent(new CurrencyPopup("currency"));
            this.modalWindow.show(target);
        }
        return false;
    }

    protected boolean backLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.tab.getObject().setSelectedTab(FixedCreatePage.TAB_INTEREST_RATE_CHART);
        if (target != null) {
            target.add(this.tab.getObject());
        }
        return false;
    }

    protected void modalWindowClose(String popupName, String signalId, AjaxRequestTarget target) {
        Option charge = (Option) this.popupModel.get("chargeValue");
        if (charge == null) {
            return;
        }
        Map<String, Object> item = Maps.newHashMap();
        for (Map<String, Object> temp : this.chargeValue.getObject()) {
            if (charge.getId().equals(temp.get("uuid"))) {
                return;
            }
        }
        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery(MCharge.NAME);
        selectQuery.addWhere(MCharge.Field.ID + " = :" + MCharge.Field.ID, charge.getId());
        selectQuery.addField(MCharge.Field.ID);
        selectQuery.addField(MCharge.Field.NAME);
        selectQuery.addField(MCharge.Field.CHARGE_CALCULATION_ENUM);
        selectQuery.addField(MCharge.Field.AMOUNT);
        selectQuery.addField(MCharge.Field.CHARGE_TIME_ENUM);
        Map<String, Object> chargeObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        Option type = ChargeCalculation.optionLiteral(String.valueOf(chargeObject.get(MCharge.Field.CHARGE_CALCULATION_ENUM)));
        Option collect = ChargeTime.optionLiteral(String.valueOf(chargeObject.get(MCharge.Field.CHARGE_TIME_ENUM)));
        item.put("uuid", charge.getId());
        item.put("charge", charge);
        item.put("name", chargeObject.get(MCharge.Field.NAME));
        item.put("type", type);
        item.put("amount", chargeObject.get(MCharge.Field.AMOUNT));
        item.put("collect", collect);
        item.put("date", "");
        this.chargeValue.getObject().add(item);
        target.add(this.chargeTable);
    }

}
