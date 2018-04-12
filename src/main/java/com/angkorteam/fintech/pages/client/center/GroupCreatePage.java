package com.angkorteam.fintech.pages.client.center;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import com.angkorteam.fintech.widget.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.MClient;
import com.angkorteam.fintech.ddl.MCodeValue;
import com.angkorteam.fintech.ddl.MGroup;
import com.angkorteam.fintech.ddl.MOffice;
import com.angkorteam.fintech.ddl.MStaff;
import com.angkorteam.fintech.ddl.REnumValue;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.GroupBuilder;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.popup.ClientPopup;
import com.angkorteam.fintech.popup.OfficePopup;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
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
public class GroupCreatePage extends Page {

    protected String centerId;
    protected String officeId;

    protected String centerDisplayName;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock officeBlock;
    protected WebMarkupContainer officeVContainer;
    protected String officeValue;
    protected ReadOnlyView officeView;

    protected WebMarkupBlock staffBlock;
    protected WebMarkupContainer staffIContainer;
    protected SingleChoiceProvider staffProvider;
    protected Option staffValue;
    protected Select2SingleChoice<Option> staffField;
    protected TextFeedbackPanel staffFeedback;

    protected WebMarkupBlock nameBlock;
    protected WebMarkupContainer nameIContainer;
    protected String nameValue;
    protected TextField<String> nameField;
    protected TextFeedbackPanel nameFeedback;

    protected WebMarkupBlock externalIdBlock;
    protected WebMarkupContainer externalIdIContainer;
    protected String externalIdValue;
    protected TextField<String> externalIdField;
    protected TextFeedbackPanel externalIdFeedback;

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

    protected WebMarkupBlock submittedOnBlock;
    protected WebMarkupContainer submittedOnIContainer;
    protected Date submittedOnValue = new Date();
    protected DateTextField submittedOnField;
    protected TextFeedbackPanel submittedOnFeedback;

    protected List<IColumn<Map<String, Object>, String>> clientColumn;
    protected List<Map<String, Object>> clientValue = Lists.newLinkedList();
    protected DataTable<Map<String, Object>, String> clientTable;
    protected ListDataProvider clientProvider;
    protected ModalWindow clientPopup;
    protected AjaxLink<Void> clientAddLink;

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
            PageParameters parameters = new PageParameters();
            parameters.add("centerId", this.centerId);
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel(this.centerDisplayName);
            breadcrumb.setPage(CenterPreviewPage.class);
            breadcrumb.setParameters(parameters);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Group Create");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initComponent() {
        this.popupModel = Maps.newHashMap();
        PageParameters parameters = new PageParameters();
        parameters.add("centerId", this.centerId);

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", CenterPreviewPage.class, parameters);
        this.form.add(this.closeLink);

        initOfficeBlock();

        initStaffBlock();

        initNameBlock();

        initExternalIdBlock();

        initActiveBlock();

        initActivationDateBlock();

        initSubmittedOnBlock();

        {
            this.clientPopup = new ModalWindow("clientPopup");
            add(this.clientPopup);
            this.clientPopup.setOnClose(this::clientPopupClose);

            this.clientColumn = Lists.newLinkedList();
            this.clientColumn.add(new TextColumn(Model.of("Display Name"), "displayName", "displayName", this::clientColumn));
            this.clientColumn.add(new TextColumn(Model.of("Mobile Number"), "mobileNumber", "mobileNumber", this::clientColumn));
            this.clientColumn.add(new TextColumn(Model.of("Gender"), "gender", "gender", this::clientColumn));
            this.clientColumn.add(new TextColumn(Model.of("Account#"), "account", "account", this::clientColumn));
            this.clientColumn.add(new TextColumn(Model.of("Status"), "status", "status", this::clientColumn));
            this.clientColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::clientAction, this::clientClick));
            this.clientProvider = new ListDataProvider(this.clientValue);
            this.clientTable = new DataTable<>("clientTable", this.clientColumn, this.clientProvider, 20);
            this.form.add(this.clientTable);
            this.clientTable.addTopToolbar(new HeadersToolbar<>(this.clientTable, this.clientProvider));
            this.clientTable.addBottomToolbar(new NoRecordsToolbar(this.clientTable));

            this.clientAddLink = new AjaxLink<>("clientAddLink");
            this.clientAddLink.setOnClick(this::clientAddLinkClick);
            this.form.add(this.clientAddLink);
        }
    }

    @Override
    protected void configureMetaData() {
        activeFieldUpdate(null);
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

    protected void initStaffBlock() {
        this.staffBlock = new WebMarkupBlock("staffBlock", Size.Six_6);
        this.form.add(this.staffBlock);
        this.staffIContainer = new WebMarkupContainer("staffIContainer");
        this.staffBlock.add(this.staffIContainer);
        this.staffProvider = new SingleChoiceProvider(MStaff.NAME, MStaff.NAME + "." + MStaff.Field.ID, MStaff.NAME + "." + MStaff.Field.DISPLAY_NAME);
        this.staffProvider.applyWhere("office_id", MStaff.NAME + "." + MStaff.Field.OFFICE_ID + " = " + this.officeId);
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
        this.officeVContainer = new WebMarkupContainer("officeVContainer");
        this.officeBlock.add(this.officeVContainer);
        this.officeView = new ReadOnlyView("officeView", new PropertyModel<>(this, "officeValue"));
        this.officeVContainer.add(this.officeView);
    }

    @Override
    protected void initData() {
        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        this.externalIdValue = generator.externalId();
        this.centerId = getPageParameters().get("centerId").toString();

        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery(MGroup.NAME);
        selectQuery.addWhere(MGroup.Field.ID + " = :" + MGroup.Field.ID, this.centerId);
        selectQuery.addField(MGroup.Field.OFFICE_ID);
        selectQuery.addField(MGroup.Field.DISPLAY_NAME);
        Map<String, Object> centerObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        this.officeId = String.valueOf(centerObject.get("office_id"));
        this.centerDisplayName = (String) centerObject.get("display_name");

        selectQuery = new SelectQuery(MOffice.NAME);
        selectQuery.addWhere(MOffice.Field.ID + " = :" + MOffice.Field.ID, this.officeId);
        selectQuery.addField(MOffice.Field.NAME);
        this.officeValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), String.class);
    }

    protected boolean clientAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.popupModel.clear();
        if (this.officeValue == null) {
            this.clientPopup.setContent(new OfficePopup("office"));
            this.clientPopup.show(target);
        } else {
            this.clientPopup.setContent(new ClientPopup("client", this.popupModel, this.officeId));
            this.clientPopup.show(target);
        }
        return false;
    }

    protected ItemPanel clientColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("displayName".equals(column) || "mobileNumber".equals(column) || "gender".equals(column) || "account".equals(column) || "status".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    protected void clientClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        int index = -1;
        for (int i = 0; i < this.clientValue.size(); i++) {
            Map<String, Object> column = this.clientValue.get(i);
            if (model.get("uuid").equals(column.get("uuid"))) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.clientValue.remove(index);
        }
        target.add(this.clientTable);
    }

    protected List<ActionItem> clientAction(String s, Map<String, Object> model) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected void clientPopupClose(String popupName, String signalId, AjaxRequestTarget target) {
        if (this.popupModel.get("clientValue") != null) {
            JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
            SelectQuery selectQuery = null;
            selectQuery = new SelectQuery(MClient.NAME);
            selectQuery.addJoin("LEFT JOIN " + MCodeValue.NAME + " gender_code ON " + MClient.NAME + "." + MClient.Field.GENDER_CV_ID + " = gender_code." + MCodeValue.Field.ID);
            selectQuery.addJoin("LEFT JOIN " + REnumValue.NAME + " status ON status." + REnumValue.Field.ENUM_ID + " = " + MClient.NAME + "." + MClient.Field.STATUS_ENUM + " AND status." + REnumValue.Field.ENUM_NAME + " = 'status_enum'");
            selectQuery.addField("CONCAT(" + MClient.NAME + "." + MClient.Field.ID + ",'') uuid");
            selectQuery.addField(MClient.NAME + "." + MClient.Field.DISPLAY_NAME + " displayName");
            selectQuery.addField(MClient.NAME + "." + MClient.Field.MOBILE_NO + " mobileNumber");
            selectQuery.addField("gender_code." + MCodeValue.Field.CODE_DESCRIPTION + " gender");
            selectQuery.addField(MClient.NAME + "." + MClient.Field.ACCOUNT_NO + " account");
            selectQuery.addField("status." + REnumValue.Field.ENUM_VALUE + " status");
            selectQuery.addWhere(MClient.NAME + "." + MClient.Field.ID + " = :" + MClient.Field.ID, ((Option) this.popupModel.get("clientValue")).getId());
            Map<String, Object> client = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
            this.clientValue.add(client);
            target.add(this.clientTable);
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

    protected void saveButtonSubmit(Button button) {
        GroupBuilder builder = new GroupBuilder();
        if (this.officeValue != null) {
            builder.withOfficeId(this.officeId);
        }
        builder.withCenterId(this.centerId);
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
        for (Map<String, Object> clientMember : this.clientValue) {
            builder.withClientMember((String) clientMember.get("uuid"));
        }

        JsonNode node = ClientHelper.createGroup((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }

        PageParameters parameters = new PageParameters();
        parameters.add("centerId", this.centerId);
        setResponsePage(CenterPreviewPage.class, parameters);
    }
}
