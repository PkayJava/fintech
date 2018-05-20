package com.angkorteam.fintech.widget.product.fixed;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.ValidationError;

import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.product.fixed.FixedBrowsePage;
import com.angkorteam.fintech.pages.product.fixed.FixedCreatePage;
import com.angkorteam.fintech.popup.IncentivePopup;
import com.angkorteam.fintech.popup.InterestRateChartPopup;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.Panel;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
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
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.validation.LamdaFormValidator;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class InterestRateChartPanel extends Panel {

    protected Page itemPage;
    protected PropertyModel<AjaxTabbedPanel<ITab>> tab;
    protected PropertyModel<Boolean> errorInterestRateChart;

    protected Form<Void> form;
    protected Button nextButton;
    protected AjaxLink<Void> backLink;
    protected BookmarkablePageLink<Void> closeLink;

    protected ModalWindow modalWindow;
    protected Map<String, Object> popupModel;

    // Interest Rate Chart

    protected UIRow row1;

    protected UIBlock interestRateValidFromDateBlock;
    protected UIContainer interestRateValidFromDateIContainer;
    protected DateTextField interestRateValidFromDateField;
    protected PropertyModel<Date> interestRateValidFromDateValue;

    protected UIBlock interestRateValidEndDateBlock;
    protected UIContainer interestRateValidEndDateIContainer;
    protected DateTextField interestRateValidEndDateField;
    protected PropertyModel<Date> interestRateValidEndDateValue;

    protected UIRow row2;

    protected UIBlock interestRatePrimaryGroupingByAmountBlock;
    protected UIContainer interestRatePrimaryGroupingByAmountIContainer;
    protected CheckBox interestRatePrimaryGroupingByAmountField;

    protected UIBlock row2Block1;

    protected UIRow row3;

    protected List<IColumn<Map<String, Object>, String>> interestRateChartColumn;
    protected UIBlock interestRateChartBlock;
    protected UIContainer interestRateChartIContainer;
    protected DataTable<Map<String, Object>, String> interestRateChartTable;
    protected ListDataProvider interestRateChartProvider;
    protected AjaxLink<Void> interestRateChartAddLink;
    protected PropertyModel<List<Map<String, Object>>> interestRateChartValue;

    public InterestRateChartPanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void initData() {
        this.popupModel = new HashMap<>();
        this.tab = new PropertyModel<>(this.itemPage, "tab");
        this.errorInterestRateChart = new PropertyModel<>(this.itemPage, "errorInterestRateChart");

        this.interestRateChartValue = new PropertyModel<>(this.itemPage, "interestRateChartValue");
        this.interestRateChartColumn = Lists.newLinkedList();
        this.interestRateChartColumn.add(new TextColumn(Model.of("Period Type"), "periodType", "periodType", this::interestRateChartColumn));
        this.interestRateChartColumn.add(new TextColumn(Model.of("Period From/To"), "period", "period", this::interestRateChartColumn));
        this.interestRateChartColumn.add(new TextColumn(Model.of("Amount From/To"), "amountRange", "amountRange", this::interestRateChartColumn));
        this.interestRateChartColumn.add(new TextColumn(Model.of("Interest"), "interest", "interest", this::interestRateChartColumn));
        this.interestRateChartColumn.add(new TextColumn(Model.of("Description"), "description", "description", this::interestRateChartColumn));
        this.interestRateChartColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::interestRateChartAction, this::interestRateChartClick));
        this.interestRateChartProvider = new ListDataProvider(this.interestRateChartValue.getObject());

        this.interestRateValidFromDateValue = new PropertyModel<>(this.itemPage, "interestRateValidFromDateValue");
        this.interestRateValidEndDateValue = new PropertyModel<>(this.itemPage, "interestRateValidEndDateValue");
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

        this.interestRateValidFromDateBlock = this.row1.newUIBlock("interestRateValidFromDateBlock", Size.Six_6);
        this.interestRateValidFromDateIContainer = this.interestRateValidFromDateBlock.newUIContainer("interestRateValidFromDateIContainer");
        this.interestRateValidFromDateField = new DateTextField("interestRateValidFromDateField", this.interestRateValidFromDateValue);
        this.interestRateValidFromDateIContainer.add(this.interestRateValidFromDateField);
        this.interestRateValidFromDateIContainer.newFeedback("interestRateValidFromDateFeedback", this.interestRateValidFromDateField);

        this.interestRateValidEndDateBlock = this.row1.newUIBlock("interestRateValidEndDateBlock", Size.Six_6);
        this.interestRateValidEndDateIContainer = this.interestRateValidEndDateBlock.newUIContainer("interestRateValidEndDateIContainer");
        this.interestRateValidEndDateField = new DateTextField("interestRateValidEndDateField", this.interestRateValidEndDateValue);
        this.interestRateValidEndDateIContainer.add(this.interestRateValidEndDateField);
        this.interestRateValidEndDateIContainer.newFeedback("interestRateValidEndDateFeedback", this.interestRateValidEndDateField);

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.interestRatePrimaryGroupingByAmountBlock = this.row2.newUIBlock("interestRatePrimaryGroupingByAmountBlock", Size.Six_6);
        this.interestRatePrimaryGroupingByAmountIContainer = this.interestRatePrimaryGroupingByAmountBlock.newUIContainer("interestRatePrimaryGroupingByAmountIContainer");
        this.interestRatePrimaryGroupingByAmountField = new CheckBox("interestRatePrimaryGroupingByAmountField", new PropertyModel<>(this.itemPage, "interestRatePrimaryGroupingByAmountValue"));
        this.interestRatePrimaryGroupingByAmountIContainer.add(this.interestRatePrimaryGroupingByAmountField);
        this.interestRatePrimaryGroupingByAmountIContainer.newFeedback("interestRatePrimaryGroupingByAmountFeedback", this.interestRatePrimaryGroupingByAmountField);

        this.row2Block1 = this.row2.newUIBlock("row2Block1", Size.Six_6);

        this.row3 = UIRow.newUIRow("row3", this.form);

        this.interestRateChartBlock = this.row3.newUIBlock("interestRateChartBlock", Size.Twelve_12);
        this.interestRateChartIContainer = this.interestRateChartBlock.newUIContainer("interestRateChartIContainer");
        this.interestRateChartTable = new DataTable<>("interestRateChartTable", interestRateChartColumn, this.interestRateChartProvider, 20);
        this.interestRateChartIContainer.add(this.interestRateChartTable);
        this.interestRateChartTable.addTopToolbar(new HeadersToolbar<>(this.interestRateChartTable, this.interestRateChartProvider));
        this.interestRateChartTable.addBottomToolbar(new NoRecordsToolbar(this.interestRateChartTable));
        this.interestRateChartAddLink = new AjaxLink<>("interestRateChartAddLink");
        this.interestRateChartAddLink.setOnClick(this::interestRateChartAddLinkClick);
        this.interestRateChartIContainer.add(this.interestRateChartAddLink);
    }

    @Override
    protected void configureMetaData() {
        this.interestRatePrimaryGroupingByAmountField.add(new OnChangeAjaxBehavior());

        this.interestRateValidEndDateField.setLabel(Model.of("End Date"));
        this.interestRateValidEndDateField.add(new OnChangeAjaxBehavior());

        this.interestRateValidFromDateField.setLabel(Model.of("Valid From Date"));
        this.interestRateValidFromDateField.add(new OnChangeAjaxBehavior());
        this.interestRateValidFromDateField.setRequired(true);

        this.form.add(new LamdaFormValidator(this::interestRateValidValidation, this.interestRateValidEndDateField, this.interestRateValidFromDateField));

    }

    protected void interestRateValidValidation(Form<?> form) {
        if (this.interestRateValidEndDateValue.getObject() != null) {
            if (!this.interestRateValidEndDateValue.getObject().after(this.interestRateValidFromDateValue.getObject())) {
                this.interestRateValidEndDateField.error(new ValidationError("Invalid"));
            }
        }
    }

    protected void modalWindowClose(String popupName, String signalId, AjaxRequestTarget target) {
        if ("interestRateChart".equals(popupName)) {
            Map<String, Object> item = Maps.newHashMap();
            String uuid = UUID.randomUUID().toString();
            item.put("uuid", uuid);
            item.put("periodType", this.popupModel.get("periodTypeValue"));
            Long periodFrom = (Long) this.popupModel.get("periodFromValue");
            Long periodTo = (Long) this.popupModel.get("periodToValue");
            String period = null;
            if (periodFrom != null && periodTo != null) {
                period = String.valueOf(periodFrom) + " - " + String.valueOf(periodTo);
            } else if (periodFrom == null && periodTo == null) {
                period = "";
            } else {
                if (periodFrom != null) {
                    period = String.valueOf(periodFrom) + " - ";
                }
                if (periodTo != null) {
                    period = " - " + String.valueOf(periodTo);
                }
            }

            Long amountRangeFrom = (Long) this.popupModel.get("amountRangeFromValue");
            Long amountRangeTo = (Long) this.popupModel.get("amountRangeToValue");
            String amountRange = null;
            if (amountRangeFrom != null && amountRangeTo != null) {
                amountRange = String.valueOf(amountRangeFrom) + " - " + String.valueOf(amountRangeTo);
            } else if (amountRangeFrom == null && amountRangeTo == null) {
                amountRange = "";
            } else {
                if (amountRangeFrom != null) {
                    amountRange = String.valueOf(amountRangeFrom) + " - ";
                }
                if (amountRangeTo != null) {
                    amountRange = " - " + String.valueOf(amountRangeTo);
                }
            }
            item.put("periodFrom", this.popupModel.get("periodFromValue"));
            item.put("periodTo", this.popupModel.get("periodToValue"));
            item.put("period", period);
            item.put("amountRangeFrom", this.popupModel.get("amountRangeFromValue"));
            item.put("amountRangeTo", this.popupModel.get("amountRangeToValue"));
            item.put("amountRange", amountRange);
            item.put("interest", this.popupModel.get("interestValue"));
            item.put("description", this.popupModel.get("descriptionValue"));
            item.put("interestRate", Lists.newLinkedList());
            this.interestRateChartValue.getObject().add(item);
            target.add(this.interestRateChartTable);
        } else if ("incentive".equals(popupName)) {

        }
    }

    protected void interestRateChartClick(String link, Map<String, Object> model, AjaxRequestTarget target) {
        if ("delete".equals(link)) {
            int index = -1;
            for (int i = 0; i < this.interestRateChartValue.getObject().size(); i++) {
                Map<String, Object> column = this.interestRateChartValue.getObject().get(i);
                if (model.get("uuid").equals(column.get("uuid"))) {
                    index = i;
                    break;
                }
            }
            if (index >= 0) {
                this.interestRateChartValue.getObject().remove(index);
            }
            target.add(this.interestRateChartTable);
        } else if ("incentives".equals(link)) {
            List<Map<String, Object>> incentiveValue = (List<Map<String, Object>>) model.get("interestRate");
            this.modalWindow.setContent(new IncentivePopup("incentive", incentiveValue));
            this.modalWindow.show(target);
        }
    }

    protected boolean interestRateChartAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        this.modalWindow.setContent(new InterestRateChartPopup("interestRateChart", this.popupModel));
        this.modalWindow.show(target);
        return false;
    }

    protected List<ActionItem> interestRateChartAction(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newLinkedList();
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        actions.add(new ActionItem("incentives", Model.of("Incentives"), ItemCss.PRIMARY));
        return actions;
    }

    protected ItemPanel interestRateChartColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("periodType".equals(column)) {
            Option value = (Option) model.get(column);
            return new TextCell(value);
        } else if ("interest".equals(column)) {
            Double value = (Double) model.get(column);
            return new TextCell(value);
        } else if ("description".equals(column) || "period".equals(column) || "amountRange".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected boolean backLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.tab.getObject().setSelectedTab(FixedCreatePage.TAB_SETTING);
        if (target != null) {
            target.add(this.tab.getObject());
        }
        return false;
    }

    protected void nextButtonSubmit(Button button) {
        this.errorInterestRateChart.setObject(false);
        this.tab.getObject().setSelectedTab(FixedCreatePage.TAB_CHARGE);
    }

    protected void nextButtonError(Button button) {
        this.errorInterestRateChart.setObject(true);
    }

}
