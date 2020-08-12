//package com.angkorteam.fintech.pages.client.center;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.wicket.WicketRuntimeException;
//import org.apache.wicket.ajax.AjaxRequestTarget;
//import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
//import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
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
//import com.angkorteam.fintech.ddl.MClient;
//import com.angkorteam.fintech.ddl.MCodeValue;
//import com.angkorteam.fintech.ddl.MGroup;
//import com.angkorteam.fintech.ddl.MOffice;
//import com.angkorteam.fintech.ddl.MStaff;
//import com.angkorteam.fintech.ddl.REnumValue;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.dto.builder.GroupBuilder;
//import com.angkorteam.fintech.helper.ClientHelper;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.pages.client.group.GroupBrowsePage;
//import com.angkorteam.fintech.popup.ClientPopup;
//import com.angkorteam.fintech.popup.OfficePopup;
//import com.angkorteam.fintech.provider.SingleChoiceProvider;
//import com.angkorteam.fintech.spring.StringGenerator;
//import com.angkorteam.fintech.table.TextCell;
//import com.angkorteam.fintech.widget.ReadOnlyView;
//import com.angkorteam.framework.SpringBean;
//import com.angkorteam.framework.jdbc.SelectQuery;
//import com.angkorteam.framework.models.PageBreadcrumb;
//import com.angkorteam.framework.share.provider.ListDataProvider;
//import com.angkorteam.framework.spring.JdbcNamed;
//import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
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
//import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//
//import io.github.openunirest.http.JsonNode;
//
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class GroupCreatePage extends Page {
//
//    protected String centerId;
//    protected String officeId;
//
//    protected String centerDisplayName;
//
//    protected Form<Void> form;
//    protected Button saveButton;
//    protected BookmarkablePageLink<Void> closeLink;
//
//    protected UIRow row1;
//
//    protected UIBlock externalIdBlock;
//    protected UIContainer externalIdIContainer;
//    protected String externalIdValue;
//    protected TextField<String> externalIdField;
//
//    protected UIBlock officeBlock;
//    protected UIContainer officeVContainer;
//    protected String officeValue;
//    protected ReadOnlyView officeView;
//
//    protected UIBlock staffBlock;
//    protected UIContainer staffIContainer;
//    protected SingleChoiceProvider staffProvider;
//    protected Option staffValue;
//    protected Select2SingleChoice<Option> staffField;
//
//    protected UIRow row2;
//
//    protected UIBlock nameBlock;
//    protected UIContainer nameIContainer;
//    protected String nameValue;
//    protected TextField<String> nameField;
//
//    protected UIBlock activeBlock;
//    protected UIContainer activeIContainer;
//    protected Boolean activeValue;
//    protected CheckBox activeField;
//
//    protected UIBlock activationDateBlock;
//    protected UIContainer activationDateIContainer;
//    protected Date activationDateValue;
//    protected DateTextField activationDateField;
//
//    protected UIBlock submittedOnBlock;
//    protected UIContainer submittedOnIContainer;
//    protected Date submittedOnValue;
//    protected DateTextField submittedOnField;
//
//    protected UIRow row3;
//
//    protected UIBlock clientBlock;
//    protected UIContainer clientIContainer;
//    protected List<IColumn<Map<String, Object>, String>> clientColumn;
//    protected List<Map<String, Object>> clientValue;
//    protected DataTable<Map<String, Object>, String> clientTable;
//    protected ListDataProvider clientProvider;
//    protected AjaxLink<Void> clientAddLink;
//
//    protected ModalWindow modalWindow;
//    protected Map<String, Object> popupModel;
//
//    @Override
//    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
//        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Clients");
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Centers");
//            breadcrumb.setPage(CenterBrowsePage.class);
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageParameters parameters = new PageParameters();
//            parameters.add("centerId", this.centerId);
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel(this.centerDisplayName);
//            breadcrumb.setPage(CenterPreviewPage.class);
//            breadcrumb.setParameters(parameters);
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Group Create");
//            BREADCRUMB.add(breadcrumb);
//        }
//        return Model.ofList(BREADCRUMB);
//    }
//
//    @Override
//    protected void initComponent() {
//        this.popupModel = Maps.newHashMap();
//        PageParameters parameters = new PageParameters();
//        parameters.add("centerId", this.centerId);
//
//        this.form = new Form<>("form");
//        add(this.form);
//
//        this.saveButton = new Button("saveButton");
//        this.saveButton.setOnSubmit(this::saveButtonSubmit);
//        this.form.add(this.saveButton);
//
//        this.closeLink = new BookmarkablePageLink<>("closeLink", GroupBrowsePage.class, parameters);
//        this.form.add(this.closeLink);
//
//        this.row1 = UIRow.newUIRow("row1", this.form);
//
//        this.externalIdBlock = this.row1.newUIBlock("externalIdBlock", Size.Six_6);
//        this.externalIdIContainer = this.externalIdBlock.newUIContainer("externalIdIContainer");
//        this.externalIdField = new TextField<>("externalIdField", new PropertyModel<>(this, "externalIdValue"));
//        this.externalIdIContainer.add(this.externalIdField);
//        this.externalIdIContainer.newFeedback("externalIdFeedback", this.externalIdField);
//
//        this.officeBlock = this.row1.newUIBlock("officeBlock", Size.Three_3);
//        this.officeVContainer = this.officeBlock.newUIContainer("officeVContainer");
//        this.officeView = new ReadOnlyView("officeView", new PropertyModel<>(this, "officeValue"));
//        this.officeVContainer.add(this.officeView);
//
//        this.staffBlock = this.row1.newUIBlock("staffBlock", Size.Three_3);
//        this.staffIContainer = this.staffBlock.newUIContainer("staffIContainer");
//        this.staffField = new Select2SingleChoice<>("staffField", new PropertyModel<>(this, "staffValue"), this.staffProvider);
//        this.staffIContainer.add(this.staffField);
//        this.staffIContainer.newFeedback("staffFeedback", this.staffField);
//
//        this.row2 = UIRow.newUIRow("row2", this.form);
//
//        this.nameBlock = this.row2.newUIBlock("nameBlock", Size.Three_3);
//        this.nameIContainer = this.nameBlock.newUIContainer("nameIContainer");
//        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
//        this.nameIContainer.add(this.nameField);
//        this.nameIContainer.newFeedback("nameFeedback", this.nameField);
//
//        this.activeBlock = this.row2.newUIBlock("activeBlock", Size.Three_3);
//        this.activeIContainer = this.activeBlock.newUIContainer("activeIContainer");
//        this.activeField = new CheckBox("activeField", new PropertyModel<>(this, "activeValue"));
//        this.activeIContainer.add(this.activeField);
//        this.activeIContainer.newFeedback("activeFeedback", this.activeField);
//
//        this.activationDateBlock = this.row2.newUIBlock("activationDateBlock", Size.Three_3);
//        this.activationDateIContainer = this.activationDateBlock.newUIContainer("activationDateIContainer");
//        this.activationDateField = new DateTextField("activationDateField", new PropertyModel<>(this, "activationDateValue"));
//        this.activationDateIContainer.add(this.activationDateField);
//        this.activationDateIContainer.newFeedback("activationDateFeedback", this.activationDateField);
//
//        this.submittedOnBlock = this.row2.newUIBlock("submittedOnBlock", Size.Three_3);
//        this.submittedOnIContainer = this.submittedOnBlock.newUIContainer("submittedOnIContainer");
//        this.submittedOnField = new DateTextField("submittedOnField", new PropertyModel<>(this, "submittedOnValue"));
//        this.submittedOnIContainer.add(this.submittedOnField);
//        this.submittedOnIContainer.newFeedback("submittedOnFeedback", this.submittedOnField);
//
//        this.row3 = UIRow.newUIRow("row3", this.form);
//
//        this.clientBlock = this.row3.newUIBlock("clientBlock", Size.Twelve_12);
//        this.clientIContainer = this.clientBlock.newUIContainer("clientIContainer");
//        this.clientTable = new DataTable<>("clientTable", this.clientColumn, this.clientProvider, 20);
//        this.clientIContainer.add(this.clientTable);
//        this.clientTable.addTopToolbar(new HeadersToolbar<>(this.clientTable, this.clientProvider));
//        this.clientTable.addBottomToolbar(new NoRecordsToolbar(this.clientTable));
//        this.clientAddLink = new AjaxLink<>("clientAddLink");
//        this.clientAddLink.setOnClick(this::clientAddLinkClick);
//        this.clientIContainer.add(this.clientAddLink);
//
//        this.modalWindow = new ModalWindow("modalWindow");
//        add(this.modalWindow);
//        this.modalWindow.setOnClose(this::modalWindowClose);
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.submittedOnField.setLabel(Model.of("Submitted On"));
//        this.submittedOnField.add(new OnChangeAjaxBehavior());
//
//        this.activationDateField.setLabel(Model.of("Activation Date"));
//        this.activationDateField.add(new OnChangeAjaxBehavior());
//
//        this.activeField.add(new OnChangeAjaxBehavior(this::activeFieldUpdate));
//
//        this.nameField.setLabel(Model.of("Name"));
//        this.nameField.add(new OnChangeAjaxBehavior());
//
//        this.staffField.setLabel(Model.of("Staff"));
//        this.staffField.add(new OnChangeAjaxBehavior());
//
//        this.externalIdField.setLabel(Model.of("External ID"));
//        this.externalIdField.add(new OnChangeAjaxBehavior());
//
//        activeFieldUpdate(null);
//    }
//
//    @Override
//    protected void initData() {
//        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
//        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
//        this.externalIdValue = generator.externalId();
//        this.centerId = getPageParameters().get("centerId").toString();
//
//        SelectQuery selectQuery = null;
//
//        selectQuery = new SelectQuery(MGroup.NAME);
//        selectQuery.addWhere(MGroup.Field.ID + " = :" + MGroup.Field.ID, this.centerId);
//        selectQuery.addField(MGroup.Field.OFFICE_ID);
//        selectQuery.addField(MGroup.Field.DISPLAY_NAME);
//        Map<String, Object> centerObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
//
//        this.officeId = String.valueOf(centerObject.get("office_id"));
//        this.centerDisplayName = (String) centerObject.get("display_name");
//
//        selectQuery = new SelectQuery(MOffice.NAME);
//        selectQuery.addWhere(MOffice.Field.ID + " = :" + MOffice.Field.ID, this.officeId);
//        selectQuery.addField(MOffice.Field.NAME);
//        this.officeValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), String.class);
//
//        this.activationDateValue = new Date();
//        this.submittedOnValue = new Date();
//
//        this.clientValue = Lists.newLinkedList();
//
//        this.staffProvider = new SingleChoiceProvider(MStaff.NAME, MStaff.NAME + "." + MStaff.Field.ID, MStaff.NAME + "." + MStaff.Field.DISPLAY_NAME);
//        this.staffProvider.applyWhere("office_id", MStaff.NAME + "." + MStaff.Field.OFFICE_ID + " = " + this.officeId);
//
//        this.clientColumn = Lists.newLinkedList();
//        this.clientColumn.add(new TextColumn(Model.of("Display Name"), "displayName", "displayName", this::clientColumn));
//        this.clientColumn.add(new TextColumn(Model.of("Mobile Number"), "mobileNumber", "mobileNumber", this::clientColumn));
//        this.clientColumn.add(new TextColumn(Model.of("Gender"), "gender", "gender", this::clientColumn));
//        this.clientColumn.add(new TextColumn(Model.of("Account#"), "account", "account", this::clientColumn));
//        this.clientColumn.add(new TextColumn(Model.of("Status"), "status", "status", this::clientColumn));
//        this.clientColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::clientAction, this::clientClick));
//
//        this.clientValue = new ArrayList<>();
//        this.clientProvider = new ListDataProvider(this.clientValue);
//    }
//
//    protected boolean clientAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
//        this.popupModel.clear();
//        if (this.officeValue == null) {
//            this.modalWindow.setContent(new OfficePopup("office"));
//            this.modalWindow.show(target);
//        } else {
//            this.modalWindow.setContent(new ClientPopup("client", this.popupModel, this.officeId));
//            this.modalWindow.show(target);
//        }
//        return false;
//    }
//
//    protected ItemPanel clientColumn(String column, IModel<String> display, Map<String, Object> model) {
//        if ("displayName".equals(column) || "mobileNumber".equals(column) || "gender".equals(column) || "account".equals(column) || "status".equals(column)) {
//            String value = (String) model.get(column);
//            return new TextCell(value);
//        }
//        throw new WicketRuntimeException("Unknown " + column);
//    }
//
//    protected void clientClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
//        int index = -1;
//        for (int i = 0; i < this.clientValue.size(); i++) {
//            Map<String, Object> column = this.clientValue.get(i);
//            if (model.get("uuid").equals(column.get("uuid"))) {
//                index = i;
//                break;
//            }
//        }
//        if (index >= 0) {
//            this.clientValue.remove(index);
//        }
//        target.add(this.clientTable);
//    }
//
//    protected List<ActionItem> clientAction(String s, Map<String, Object> model) {
//        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
//    }
//
//    protected void modalWindowClose(String popupName, String signalId, AjaxRequestTarget target) {
//        if (this.popupModel.get("clientValue") != null) {
//            JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
//            SelectQuery selectQuery = null;
//            selectQuery = new SelectQuery(MClient.NAME);
//            selectQuery.addJoin("LEFT JOIN " + MCodeValue.NAME + " gender_code ON " + MClient.NAME + "." + MClient.Field.GENDER_CV_ID + " = gender_code." + MCodeValue.Field.ID);
//            selectQuery.addJoin("LEFT JOIN " + REnumValue.NAME + " status ON status." + REnumValue.Field.ENUM_ID + " = " + MClient.NAME + "." + MClient.Field.STATUS_ENUM + " AND status." + REnumValue.Field.ENUM_NAME + " = 'status_enum'");
//            selectQuery.addField("CONCAT(" + MClient.NAME + "." + MClient.Field.ID + ",'') uuid");
//            selectQuery.addField(MClient.NAME + "." + MClient.Field.DISPLAY_NAME + " displayName");
//            selectQuery.addField(MClient.NAME + "." + MClient.Field.MOBILE_NO + " mobileNumber");
//            selectQuery.addField("gender_code." + MCodeValue.Field.CODE_DESCRIPTION + " gender");
//            selectQuery.addField(MClient.NAME + "." + MClient.Field.ACCOUNT_NO + " account");
//            selectQuery.addField("status." + REnumValue.Field.ENUM_VALUE + " status");
//            selectQuery.addWhere(MClient.NAME + "." + MClient.Field.ID + " = :" + MClient.Field.ID, ((Option) this.popupModel.get("clientValue")).getId());
//            Map<String, Object> client = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
//            this.clientValue.add(client);
//            target.add(this.clientTable);
//        }
//    }
//
//    protected boolean activeFieldUpdate(AjaxRequestTarget target) {
//        boolean visible = this.activeValue == null ? false : this.activeValue;
//        this.activationDateIContainer.setVisible(visible);
//        if (target != null) {
//            target.add(this.activationDateBlock);
//        }
//        return false;
//    }
//
//    protected void saveButtonSubmit(Button button) {
//        GroupBuilder builder = new GroupBuilder();
//        if (this.officeValue != null) {
//            builder.withOfficeId(this.officeId);
//        }
//        builder.withCenterId(this.centerId);
//        builder.withExternalId(this.externalIdValue);
//        builder.withName(this.nameValue);
//        builder.withSubmittedOnDate(this.submittedOnValue);
//        if (this.staffValue != null) {
//            builder.withStaffId(this.staffValue.getId());
//        }
//        boolean active = this.activeValue == null ? false : this.activeValue;
//        builder.withActive(active);
//        if (active) {
//            builder.withActivationDate(this.activationDateValue);
//        }
//        for (Map<String, Object> clientMember : this.clientValue) {
//            builder.withClientMember((String) clientMember.get("uuid"));
//        }
//
//        JsonNode node = ClientHelper.createGroup((Session) getSession(), builder.build());
//
//        if (reportError(node)) {
//            return;
//        }
//
//        PageParameters parameters = new PageParameters();
//        parameters.add("centerId", this.centerId);
//        setResponsePage(CenterPreviewPage.class, parameters);
//    }
//}
