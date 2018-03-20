package com.angkorteam.fintech.pages.client.center;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.MGroup;
import com.angkorteam.fintech.ddl.MOffice;
import com.angkorteam.fintech.ddl.MStaff;
import com.angkorteam.fintech.ddl.REnumValue;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.CenterBuilder;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.pages.client.client.ClientBrowsePage;
import com.angkorteam.fintech.popup.GroupPopup;
import com.angkorteam.fintech.popup.OfficePopup;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
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
import com.mashape.unirest.http.JsonNode;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class CenterCreatePage extends Page {

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock nameBlock;
    protected WebMarkupContainer nameIContainer;
    protected String nameValue;
    protected TextField<String> nameField;
    protected TextFeedbackPanel nameFeedback;

    protected WebMarkupBlock officeBlock;
    protected WebMarkupContainer officeIContainer;
    protected SingleChoiceProvider officeProvider;
    protected Option officeValue;
    protected Select2SingleChoice<Option> officeField;
    protected TextFeedbackPanel officeFeedback;

    protected WebMarkupBlock staffBlock;
    protected WebMarkupContainer staffIContainer;
    protected SingleChoiceProvider staffProvider;
    protected Option staffValue;
    protected Select2SingleChoice<Option> staffField;
    protected TextFeedbackPanel staffFeedback;

    protected WebMarkupBlock activeBlock;
    protected WebMarkupContainer activeIContainer;
    protected Boolean activeValue;
    protected CheckBox activeField;
    protected TextFeedbackPanel activeFeedback;

    protected WebMarkupBlock activationDateBlock;
    protected WebMarkupContainer activationDateIContainer;
    protected Date activationDateValue = new Date();
    protected DateTextField activationDateField;
    protected TextFeedbackPanel activationDateFeedback;

    protected WebMarkupBlock externalIdBlock;
    protected WebMarkupContainer externalIdIContainer;
    protected String externalIdValue;
    protected TextField<String> externalIdField;
    protected TextFeedbackPanel externalIdFeedback;

    protected WebMarkupBlock submittedOnBlock;
    protected WebMarkupContainer submittedOnIContainer;
    protected Date submittedOnValue = new Date();
    protected DateTextField submittedOnField;
    protected TextFeedbackPanel submittedOnFeedback;

    protected WebMarkupBlock groupBlock;
    protected WebMarkupContainer groupIContainer;
    protected List<Map<String, Object>> groupValue;
    protected DataTable<Map<String, Object>, String> groupTable;
    protected ListDataProvider groupProvider;
    protected ModalWindow groupPopup;
    protected AjaxLink<Void> groupAddLink;
    protected List<IColumn<Map<String, Object>, String>> groupColumn;

    protected Map<String, Object> popupModel;

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
    protected void initData() {
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        this.externalIdValue = generator.externalId();
        this.popupModel = Maps.newHashMap();
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", ClientBrowsePage.class);
        this.form.add(this.closeLink);

        initNameBlock();

        initOfficeBlock();

        initStaffBlock();

        initActiveBlock();

        initActivationDateBlock();

        initExternalIdBlock();

        initSubmittedOnBlock();

        initGroupBlock();

        officeFieldUpdate(null);
        activeFieldUpdate(null);
    }

    @Override
    protected void configureRequiredValidation() {

    }

    @Override
    protected void configureMetaData() {

    }

    protected void initGroupBlock() {
        this.groupBlock = new WebMarkupBlock("groupBlock", Size.Twelve_12);
        this.form.add(this.groupBlock);
        this.groupIContainer = new WebMarkupContainer("groupIContainer");
        this.groupBlock.add(this.groupIContainer);

        this.groupPopup = new ModalWindow("groupPopup");
        this.groupIContainer.add(this.groupPopup);
        this.groupPopup.setOnClose(this::groupPopupClose);

        this.groupValue = Lists.newArrayList();
        this.groupColumn = Lists.newLinkedList();
        this.groupColumn.add(new TextColumn(Model.of("Display Name"), "displayName", "displayName", this::groupColumn));
        this.groupColumn.add(new TextColumn(Model.of("Account#"), "account", "account", this::groupColumn));
        this.groupColumn.add(new TextColumn(Model.of("Status"), "status", "status", this::groupColumn));
        this.groupColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::groupAction, this::groupClick));
        this.groupProvider = new ListDataProvider(this.groupValue);
        this.groupTable = new DataTable<>("groupTable", this.groupColumn, this.groupProvider, 20);
        this.groupIContainer.add(this.groupTable);
        this.groupTable.addTopToolbar(new HeadersToolbar<>(this.groupTable, this.groupProvider));
        this.groupTable.addBottomToolbar(new NoRecordsToolbar(this.groupTable));

        this.groupAddLink = new AjaxLink<>("groupAddLink");
        this.groupAddLink.setOnClick(this::groupAddLinkClick);
        this.groupIContainer.add(this.groupAddLink);
    }

    protected void initSubmittedOnBlock() {
        this.submittedOnBlock = new WebMarkupBlock("submittedOnBlock", Size.Six_6);
        this.form.add(this.submittedOnBlock);
        this.submittedOnIContainer = new WebMarkupContainer("submittedOnIContainer");
        this.submittedOnBlock.add(this.submittedOnIContainer);
        this.submittedOnField = new DateTextField("submittedOnField", new PropertyModel<>(this, "submittedOnValue"));
        this.submittedOnField.setLabel(Model.of("Submitted On"));
        this.submittedOnField.add(new OnChangeAjaxBehavior());
        this.submittedOnIContainer.add(this.submittedOnField);
        this.submittedOnFeedback = new TextFeedbackPanel("submittedOnFeedback", this.submittedOnField);
        this.submittedOnIContainer.add(this.submittedOnFeedback);
    }

    protected void initExternalIdBlock() {
        this.externalIdBlock = new WebMarkupBlock("externalIdBlock", Size.Six_6);
        this.form.add(this.externalIdBlock);
        this.externalIdIContainer = new WebMarkupContainer("externalIdIContainer");
        this.externalIdBlock.add(this.externalIdIContainer);
        this.externalIdField = new TextField<>("externalIdField", new PropertyModel<>(this, "externalIdValue"));
        this.externalIdField.setLabel(Model.of("External ID"));
        this.externalIdField.add(new OnChangeAjaxBehavior());
        this.externalIdIContainer.add(this.externalIdField);
        this.externalIdFeedback = new TextFeedbackPanel("externalIdFeedback", this.externalIdField);
        this.externalIdIContainer.add(this.externalIdFeedback);
    }

    protected void initActivationDateBlock() {
        this.activationDateBlock = new WebMarkupBlock("activationDateBlock", Size.Six_6);
        this.form.add(this.activationDateBlock);
        this.activationDateIContainer = new WebMarkupContainer("activationDateIContainer");
        this.activationDateBlock.add(this.activationDateIContainer);
        this.activationDateField = new DateTextField("activationDateField", new PropertyModel<>(this, "activationDateValue"));
        this.activationDateField.setLabel(Model.of("Activation Date"));
        this.activationDateField.add(new OnChangeAjaxBehavior());
        this.activationDateIContainer.add(this.activationDateField);
        this.activationDateFeedback = new TextFeedbackPanel("activationDateFeedback", this.activationDateField);
        this.activationDateIContainer.add(this.activationDateFeedback);
    }

    protected void initActiveBlock() {
        this.activeBlock = new WebMarkupBlock("activeBlock", Size.Six_6);
        this.form.add(this.activeBlock);
        this.activeIContainer = new WebMarkupContainer("activeIContainer");
        this.activeBlock.add(this.activeIContainer);
        this.activeField = new CheckBox("activeField", new PropertyModel<>(this, "activeValue"));
        this.activeField.add(new OnChangeAjaxBehavior(this::activeFieldUpdate));
        this.activeIContainer.add(this.activeField);
        this.activeFeedback = new TextFeedbackPanel("activeFeedback", this.activeField);
        this.activeIContainer.add(this.activeFeedback);
    }

    protected void initStaffBlock() {
        this.staffBlock = new WebMarkupBlock("staffBlock", Size.Six_6);
        this.form.add(this.staffBlock);
        this.staffIContainer = new WebMarkupContainer("staffIContainer");
        this.staffBlock.add(this.staffIContainer);
        this.staffProvider = new SingleChoiceProvider(MStaff.NAME, MStaff.NAME + "." + MStaff.Field.ID, MStaff.NAME + "." + MStaff.Field.DISPLAY_NAME);
        this.staffProvider.applyJoin("m_office", "INNER JOIN " + MOffice.NAME + " ON " + MStaff.NAME + "." + MStaff.Field.OFFICE_ID + " = " + MOffice.NAME + "." + MOffice.Field.ID);
        this.staffField = new Select2SingleChoice<>("staffField", new PropertyModel<>(this, "staffValue"), this.staffProvider);
        this.staffField.setLabel(Model.of("Staff"));
        this.staffField.add(new OnChangeAjaxBehavior());
        this.staffIContainer.add(this.staffField);
        this.staffFeedback = new TextFeedbackPanel("staffFeedback", this.staffField);
        this.staffIContainer.add(this.staffFeedback);
    }

    protected void initOfficeBlock() {
        this.officeBlock = new WebMarkupBlock("officeBlock", Size.Six_6);
        this.form.add(this.officeBlock);
        this.officeIContainer = new WebMarkupContainer("officeIContainer");
        this.officeBlock.add(this.officeIContainer);
        this.officeProvider = new SingleChoiceProvider(MOffice.NAME, MOffice.Field.ID, MOffice.Field.NAME);
        this.officeField = new Select2SingleChoice<>("officeField", new PropertyModel<>(this, "officeValue"), this.officeProvider);
        this.officeField.setLabel(Model.of("Office"));
        this.officeField.add(new OnChangeAjaxBehavior(this::officeFieldUpdate));
        this.officeIContainer.add(this.officeField);
        this.officeFeedback = new TextFeedbackPanel("officeFeedback", this.officeField);
        this.officeIContainer.add(this.officeFeedback);
    }

    protected void initNameBlock() {
        this.nameBlock = new WebMarkupBlock("nameBlock", Size.Six_6);
        this.form.add(this.nameBlock);
        this.nameIContainer = new WebMarkupContainer("nameIContainer");
        this.nameBlock.add(this.nameIContainer);
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setLabel(Model.of("Name"));
        this.nameField.add(new OnChangeAjaxBehavior());
        this.nameIContainer.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.nameIContainer.add(this.nameFeedback);
    }

    protected boolean groupAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        if (this.officeValue == null) {
            this.groupPopup.setContent(new OfficePopup("office"));
            this.groupPopup.show(target);
        } else {
            this.groupPopup.setContent(new GroupPopup("groupPopup", this.popupModel, this.officeValue.getId()));
            this.groupPopup.show(target);
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

    protected void groupPopupClose(String popupName, String signalId, AjaxRequestTarget target) {
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
