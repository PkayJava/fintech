package com.angkorteam.fintech.widget.client.common.saving;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.dto.ClientEnum;
import com.angkorteam.fintech.dto.enums.ChargeTime;
import com.angkorteam.fintech.dto.enums.ProductPopup;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
import com.angkorteam.fintech.pages.client.common.saving.AccountCreatePage;
import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
import com.angkorteam.fintech.popup.AccountChargePopup;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.Panel;
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
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ChargesPanel extends Panel {

    protected Page itemPage;
    protected PropertyModel<AjaxTabbedPanel<ITab>> tab;
    protected PropertyModel<Boolean> errorCharge;

    protected PropertyModel<String> officeId;
    protected PropertyModel<String> termCurrencyValue;

    protected Form<Void> form;
    protected Button nextButton;
    protected AjaxLink<Void> backLink;
    protected BookmarkablePageLink<Void> closeLink;

    protected ModalWindow modalWindow;
    protected Map<String, Object> popupModel;

    protected ClientEnum client;
    protected String clientId;

    protected UIRow row1;

    protected UIBlock chargeBlock;
    protected UIContainer chargeIContainer;
    protected List<IColumn<Map<String, Object>, String>> chargeColumn;
    protected PropertyModel<List<Map<String, Object>>> chargeValue;
    protected DataTable<Map<String, Object>, String> chargeTable;
    protected ListDataProvider chargeProvider;
    protected AjaxLink<Void> chargeAddLink;

    public ChargesPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void initData() {
        this.popupModel = new HashMap<String, Object>();

        this.client = new PropertyModel<ClientEnum>(this.itemPage, "client").getObject();
        this.clientId = new PropertyModel<String>(this.itemPage, "clientId").getObject();

        this.tab = new PropertyModel<>(this.itemPage, "tab");
        this.errorCharge = new PropertyModel<>(this.itemPage, "errorCharge");

        this.termCurrencyValue = new PropertyModel<>(this.itemPage, "termCurrencyValue");
        this.chargeValue = new PropertyModel<>(this.itemPage, "chargeValue");

        this.chargeColumn = Lists.newLinkedList();
        this.chargeColumn.add(new TextColumn(Model.of("Name"), "name", "name", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Type"), "type", "type", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Amount"), "amount", "amount", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Collected On"), "collectedOn", "collectedOn", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Date"), "date", "date", this::chargeColumn));
        this.chargeColumn.add(new TextColumn(Model.of("Repayments Every"), "repaymentEvery", "repaymentEvery", this::chargeColumn));
        this.chargeColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::chargeAction, this::chargeClick));
        this.chargeProvider = new ListDataProvider(this.chargeValue.getObject());
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

        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);
        if (this.client == ClientEnum.Client) {
            this.closeLink = new BookmarkablePageLink<>("closeLink", ClientPreviewPage.class, parameters);
        } else if (this.client == ClientEnum.Group) {
            this.closeLink = new BookmarkablePageLink<>("closeLink", GroupPreviewPage.class, parameters);
        } else if (this.client == ClientEnum.Center) {
            this.closeLink = new BookmarkablePageLink<>("closeLink", CenterPreviewPage.class, parameters);
        }
        this.form.add(this.closeLink);

        this.modalWindow = new ModalWindow("modalWindow");
        add(this.modalWindow);
        this.modalWindow.setOnClose(this::modalWindowClose);

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.chargeBlock = this.row1.newUIBlock("chargeBlock", Size.Twelve_12);
        this.chargeIContainer = this.chargeBlock.newUIContainer("chargeIContainer");
        this.chargeTable = new DataTable<>("chargeTable", this.chargeColumn, this.chargeProvider, 20);
        this.chargeIContainer.add(this.chargeTable);
        this.chargeAddLink = new AjaxLink<>("chargeAddLink");
        this.chargeIContainer.add(this.chargeAddLink);
    }

    @Override
    protected void configureMetaData() {
        this.chargeTable.addTopToolbar(new HeadersToolbar<>(this.chargeTable, this.chargeProvider));
        this.chargeTable.addBottomToolbar(new NoRecordsToolbar(this.chargeTable));
        this.chargeAddLink.setOnClick(this::chargeAddLinkClick);
    }

    protected void chargeClick(String column, Map<String, Object> model, AjaxRequestTarget target) {
        if ("delete".equals(column)) {
            int index = -1;
            for (int i = 0; i < this.chargeValue.getObject().size(); i++) {
                Map<String, Object> value = this.chargeValue.getObject().get(i);
                if (model.get("uuid").equals(value.get("uuid"))) {
                    index = i;
                    break;
                }
            }
            if (index >= 0) {
                this.chargeValue.getObject().remove(index);
            }
            target.add(this.chargeTable);
        } else if ("update".equals(column)) {
            this.popupModel.clear();
            this.popupModel.put("uuid", model.get("uuid"));
            this.popupModel.put("chargeValue", model.get("charge"));
            this.popupModel.put("amountValue", model.get("amount"));
            this.popupModel.put("dateValue", model.get("date"));
            this.popupModel.put("repaymentEveryValue", model.get("repaymentEvery"));
            this.popupModel.put("chargeTypeValue", model.get("type"));
            this.popupModel.put("chargeValue", model.get("name"));
            this.popupModel.put("collectedOnValue", model.get("collectedOn"));
            this.modalWindow.setContent(new AccountChargePopup("charge", this.popupModel, ProductPopup.Saving, this.termCurrencyValue.getObject()));
            this.modalWindow.show(target);
        }
    }

    protected List<ActionItem> chargeAction(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("update", Model.of("Update"), ItemCss.PRIMARY));
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    protected boolean chargeAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        this.modalWindow.setContent(new AccountChargePopup("charge", this.popupModel, ProductPopup.Saving, this.termCurrencyValue.getObject()));
        this.modalWindow.show(target);
        return false;
    }

    protected ItemPanel chargeColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("name".equals(column) || "type".equals(column) || "collectedOn".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        } else if ("amount".equals(column)) {
            Double value = (Double) model.get(column);
            return new TextCell(value, "#,###,##0.00");
        } else if ("date".equals(column)) {
            ChargeTime chargeTime = ChargeTime.parseLiteral(String.valueOf(model.get("chargeTime")));
            if (chargeTime == ChargeTime.AnnualFee || chargeTime == ChargeTime.MonthlyFee) {
                Date value = (Date) model.get("dayMonth");
                return new TextCell(value, "dd MMMM");
            } else if (chargeTime == ChargeTime.WeeklyFee || chargeTime == ChargeTime.SpecifiedDueDate) {
                Date value = (Date) model.get("date");
                return new TextCell(value, "yyyy-MM-dd");
            } else {
                return new TextCell("");
            }
        } else if ("repaymentEvery".equals(column)) {
            ChargeTime chargeTime = ChargeTime.parseLiteral(String.valueOf(model.get("chargeTime")));
            if (chargeTime == ChargeTime.MonthlyFee || chargeTime == ChargeTime.WeeklyFee) {
                Long value = (Long) model.get(column);
                return new TextCell(value);
            } else {
                return new TextCell("");
            }
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected void modalWindowClose(String popupName, String signalId, AjaxRequestTarget target) {
        Map<String, Object> item = null;
        if (this.popupModel.get("uuid") != null) {
            for (int i = 0; i < this.chargeValue.getObject().size(); i++) {
                item = this.chargeValue.getObject().get(i);
                if (this.popupModel.get("uuid").equals(item.get("uuid"))) {
                    break;
                }
            }
        } else {
            item = Maps.newHashMap();
            item.put("uuid", UUID.randomUUID().toString());
            this.chargeValue.getObject().add(item);
        }
        item.put("charge", this.popupModel.get("chargeValue"));
        item.put("chargeTime", this.popupModel.get("chargeTime"));
        item.put("amount", this.popupModel.get("amountValue"));
        item.put("date", this.popupModel.get("dateValue"));
        item.put("dayMonth", this.popupModel.get("dayMonthValue"));
        item.put("repaymentEvery", this.popupModel.get("repaymentEveryValue"));
        item.put("type", this.popupModel.get("chargeTypeValue"));
        item.put("name", this.popupModel.get("chargeValue"));
        item.put("collectedOn", this.popupModel.get("collectedOnValue"));
        target.add(this.chargeTable);
    }

    protected boolean backLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.tab.getObject().setSelectedTab(AccountCreatePage.TAB_TERM);
        if (target != null) {
            target.add(this.tab.getObject());
        }
        return false;
    }

    protected void nextButtonSubmit(Button button) {
        this.errorCharge.setObject(false);
        this.tab.getObject().setSelectedTab(AccountCreatePage.TAB_REVIEW);
    }

    protected void nextButtonError(Button button) {
        this.errorCharge.setObject(true);
    }

}
