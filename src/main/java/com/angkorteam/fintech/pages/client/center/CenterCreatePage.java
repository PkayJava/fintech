package com.angkorteam.fintech.pages.client.center;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.MGroup;
import com.angkorteam.fintech.ddl.MOffice;
import com.angkorteam.fintech.ddl.MStaff;
import com.angkorteam.fintech.ddl.REnumValue;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.CenterBuilder;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.popup.GroupPopup;
import com.angkorteam.fintech.popup.OfficePopup;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.spring.JdbcNamed;
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
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.github.openunirest.http.JsonNode;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class CenterCreatePage extends Page {

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected ModalWindow modalWindow;
    protected Map<String, Object> popupModel;

    protected UIRow row1;

    protected UIBlock nameBlock;
    protected UIContainer nameIContainer;
    protected String nameValue;
    protected TextField<String> nameField;

    protected UIBlock officeBlock;
    protected UIContainer officeIContainer;
    protected SingleChoiceProvider officeProvider;
    protected Option officeValue;
    protected Select2SingleChoice<Option> officeField;

    protected UIBlock staffBlock;
    protected UIContainer staffIContainer;
    protected SingleChoiceProvider staffProvider;
    protected Option staffValue;
    protected Select2SingleChoice<Option> staffField;

    protected UIRow row5;

    protected UIBlock externalIdBlock;
    protected UIContainer externalIdIContainer;
    protected String externalIdValue;
    protected TextField<String> externalIdField;

    protected UIBlock submittedOnBlock;
    protected UIContainer submittedOnIContainer;
    protected Date submittedOnValue;
    protected DateTextField submittedOnField;

    protected UIBlock activeBlock;
    protected UIContainer activeIContainer;
    protected Boolean activeValue;
    protected CheckBox activeField;

    protected UIBlock activationDateBlock;
    protected UIContainer activationDateIContainer;
    protected Date activationDateValue;
    protected DateTextField activationDateField;

    protected UIRow row7;

    protected UIBlock groupBlock;
    protected UIContainer groupIContainer;
    protected List<Map<String, Object>> groupValue;
    protected DataTable<Map<String, Object>, String> groupTable;
    protected ListDataProvider groupProvider;
    protected AjaxLink<Void> groupAddLink;
    protected List<IColumn<Map<String, Object>, String>> groupColumn;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Clients");
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Centers");
            breadcrumb.setPage(CenterBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Create");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", CenterBrowsePage.class);
        this.form.add(this.closeLink);

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.nameBlock = this.row1.newUIBlock("nameBlock", Size.Six_6);
        this.nameIContainer = this.nameBlock.newUIContainer("nameIContainer");
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameIContainer.add(this.nameField);
        this.nameIContainer.newFeedback("nameFeedback", this.nameField);

        this.officeBlock = this.row1.newUIBlock("officeBlock", Size.Three_3);
        this.officeIContainer = this.officeBlock.newUIContainer("officeIContainer");
        this.officeField = new Select2SingleChoice<>("officeField", new PropertyModel<>(this, "officeValue"), this.officeProvider);
        this.officeIContainer.add(this.officeField);
        this.officeIContainer.newFeedback("officeFeedback", this.officeField);

        this.staffBlock = this.row1.newUIBlock("staffBlock", Size.Three_3);
        this.staffIContainer = this.staffBlock.newUIContainer("staffIContainer");
        this.staffField = new Select2SingleChoice<>("staffField", new PropertyModel<>(this, "staffValue"), this.staffProvider);
        this.staffIContainer.add(this.staffField);
        this.staffIContainer.newFeedback("staffFeedback", this.staffField);

        this.row5 = UIRow.newUIRow("row5", this.form);

        this.activeBlock = this.row5.newUIBlock("activeBlock", Size.Three_3);
        this.activeIContainer = this.activeBlock.newUIContainer("activeIContainer");
        this.activeField = new CheckBox("activeField", new PropertyModel<>(this, "activeValue"));
        this.activeIContainer.add(this.activeField);
        this.activeIContainer.newFeedback("activeFeedback", this.activeField);

        this.activationDateBlock = this.row5.newUIBlock("activationDateBlock", Size.Three_3);
        this.activationDateIContainer = this.activationDateBlock.newUIContainer("activationDateIContainer");
        this.activationDateField = new DateTextField("activationDateField", new PropertyModel<>(this, "activationDateValue"));
        this.activationDateIContainer.add(this.activationDateField);
        this.activationDateIContainer.newFeedback("activationDateFeedback", this.activationDateField);

        this.externalIdBlock = this.row5.newUIBlock("externalIdBlock", Size.Three_3);
        this.externalIdIContainer = this.externalIdBlock.newUIContainer("externalIdIContainer");
        this.externalIdField = new TextField<>("externalIdField", new PropertyModel<>(this, "externalIdValue"));
        this.externalIdIContainer.add(this.externalIdField);
        this.externalIdIContainer.newFeedback("externalIdFeedback", this.externalIdField);

        this.submittedOnBlock = this.row5.newUIBlock("submittedOnBlock", Size.Three_3);
        this.submittedOnIContainer = this.submittedOnBlock.newUIContainer("submittedOnIContainer");
        this.submittedOnField = new DateTextField("submittedOnField", new PropertyModel<>(this, "submittedOnValue"));
        this.submittedOnIContainer.add(this.submittedOnField);
        this.submittedOnIContainer.newFeedback("submittedOnFeedback", this.submittedOnField);

        this.row7 = UIRow.newUIRow("row7", this.form);

        this.groupBlock = this.row7.newUIBlock("groupBlock", Size.Twelve_12);
        this.groupIContainer = this.groupBlock.newUIContainer("groupIContainer");
        this.groupTable = new DataTable<>("groupTable", this.groupColumn, this.groupProvider, 20);
        this.groupIContainer.add(this.groupTable);
        this.groupTable.addTopToolbar(new HeadersToolbar<>(this.groupTable, this.groupProvider));
        this.groupTable.addBottomToolbar(new NoRecordsToolbar(this.groupTable));
        this.groupAddLink = new AjaxLink<>("groupAddLink");
        this.groupAddLink.setOnClick(this::groupAddLinkClick);
        this.groupIContainer.add(this.groupAddLink);

        this.modalWindow = new ModalWindow("modalWindow");
        add(this.modalWindow);
        this.modalWindow.setOnClose(this::modalWindowClose);
    }

    @Override
    protected void initData() {
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        this.externalIdValue = generator.externalId();
        this.popupModel = Maps.newHashMap();
        this.submittedOnValue = new Date();
        this.activationDateValue = new Date();

        this.officeProvider = new SingleChoiceProvider(MOffice.NAME, MOffice.Field.ID, MOffice.Field.NAME);

        this.staffProvider = new SingleChoiceProvider(MStaff.NAME, MStaff.NAME + "." + MStaff.Field.ID, MStaff.NAME + "." + MStaff.Field.DISPLAY_NAME);
        this.staffProvider.applyJoin("m_office", "INNER JOIN " + MOffice.NAME + " ON " + MStaff.NAME + "." + MStaff.Field.OFFICE_ID + " = " + MOffice.NAME + "." + MOffice.Field.ID);

        this.groupColumn = Lists.newLinkedList();
        this.groupColumn.add(new TextColumn(Model.of("Display Name"), "displayName", "displayName", this::groupColumn));
        this.groupColumn.add(new TextColumn(Model.of("Account#"), "account", "account", this::groupColumn));
        this.groupColumn.add(new TextColumn(Model.of("Status"), "status", "status", this::groupColumn));
        this.groupColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::groupAction, this::groupClick));

        this.groupValue = Lists.newArrayList();
        this.groupProvider = new ListDataProvider(this.groupValue);
    }

    @Override
    protected void configureMetaData() {
        this.submittedOnField.setLabel(Model.of("Submitted On"));
        this.submittedOnField.add(new OnChangeAjaxBehavior());

        this.externalIdField.setLabel(Model.of("External ID"));
        this.externalIdField.add(new OnChangeAjaxBehavior());

        this.activationDateField.setLabel(Model.of("Activation Date"));
        this.activationDateField.add(new OnChangeAjaxBehavior());

        this.activeField.add(new OnChangeAjaxBehavior(this::activeFieldUpdate));

        this.staffField.setLabel(Model.of("Staff"));
        this.staffField.add(new OnChangeAjaxBehavior());

        this.nameField.setLabel(Model.of("Name"));
        this.nameField.add(new OnChangeAjaxBehavior());

        this.officeField.setLabel(Model.of("Office"));
        this.officeField.add(new OnChangeAjaxBehavior(this::officeFieldUpdate));

        officeFieldUpdate(null);
        activeFieldUpdate(null);
    }

    protected boolean groupAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        if (this.officeValue == null) {
            this.modalWindow.setContent(new OfficePopup("office"));
            this.modalWindow.show(target);
        } else {
            this.modalWindow.setContent(new GroupPopup("groupPopup", this.popupModel, this.officeValue.getId()));
            this.modalWindow.show(target);
        }
        return false;
    }

    protected ItemPanel groupColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("displayName".equals(column) || "account".equals(column) || "status".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected void groupClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        int index = -1;
        for (int i = 0; i < this.groupValue.size(); i++) {
            Map<String, Object> column = this.groupValue.get(i);
            if (model.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.groupValue.remove(index);
        }
        target.add(this.groupTable);
    }

    protected List<ActionItem> groupAction(String column, Map<String, Object> model) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected void modalWindowClose(String popupName, String signalId, AjaxRequestTarget target) {
        if (this.popupModel.get("groupValue") != null) {
            JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
            SelectQuery query = new SelectQuery(MGroup.NAME);
            query.addJoin("LEFT JOIN " + REnumValue.NAME + " status on status." + REnumValue.Field.ENUM_ID + " = " + MGroup.NAME + "." + MGroup.Field.STATUS_ENUM + " and status." + REnumValue.Field.ENUM_NAME + " = 'status_enum'");
            query.addField("CONCAT(" + MGroup.NAME + "." + MGroup.Field.ID + ",'') uuid");
            query.addField(MGroup.NAME + "." + MGroup.Field.DISPLAY_NAME + " displayName");
            query.addField(MGroup.NAME + "." + MGroup.Field.ACCOUNT_NO + " account");
            query.addField("status." + REnumValue.Field.ENUM_VALUE + " status");
            query.addWhere(MGroup.NAME + "." + MGroup.Field.ID + " = :" + MGroup.Field.ID, ((Option) this.popupModel.get("groupValue")).getId());
            Map<String, Object> group = named.queryForMap(query.toSQL(), query.getParam());
            this.groupValue.add(group);
            target.add(this.groupTable);
        }
    }

    protected boolean activeFieldUpdate(AjaxRequestTarget target) {
        boolean visible = this.activeValue == null ? false : this.activeValue;
        this.activationDateIContainer.setVisible(visible);
        this.activationDateField.setRequired(visible);
        if (target != null) {
            target.add(this.activationDateBlock);
        }
        return false;
    }

    protected boolean officeFieldUpdate(AjaxRequestTarget target) {
        boolean visible = this.officeValue != null;
        this.staffValue = null;
        this.groupValue.clear();
        if (this.officeValue == null) {
            this.staffProvider.setDisabled(true);
        } else {
            this.staffProvider.setDisabled(false);
            this.staffProvider.applyWhere("office", MOffice.NAME + "." + MOffice.Field.ID + " = " + this.officeValue.getId());
        }
        this.staffIContainer.setVisible(visible);
        if (target != null) {
            target.add(this.staffBlock);
            target.add(this.groupTable);
        }
        return false;
    }

    protected void saveButtonSubmit(Button button) {
        CenterBuilder builder = new CenterBuilder();
        if (this.officeValue != null) {
            builder.withOfficeId(this.officeValue.getId());
        }
        builder.withExternalId(this.externalIdValue);
        builder.withName(this.nameValue);
        builder.withSubmittedOnDate(this.submittedOnValue);
        if (this.staffValue != null) {
            builder.withStaffId(this.staffValue.getId());
        }
        boolean active = this.activeValue == null ? false : this.activeValue;
        builder.withActive(active);
        if (active) {
            builder.withActivationDate(this.activationDateValue);
        }
        for (Map<String, Object> groupMember : this.groupValue) {
            builder.withGroupMember((String) groupMember.get("uuid"));
        }

        JsonNode node = ClientHelper.createCenter((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(CenterBrowsePage.class);
    }

}
