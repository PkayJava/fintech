package com.angkorteam.fintech.pages.client.center;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.DeprecatedPage;
import com.angkorteam.fintech.DeprecatedPage;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.GroupBuilder;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.pages.client.group.GroupBrowsePage;
import com.angkorteam.fintech.popup.ClientPopup;
import com.angkorteam.fintech.popup.OfficePopup;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.share.provider.ListDataProvider;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.spring.JdbcTemplate;
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
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class GroupCreatePage extends DeprecatedPage {

    private String centerId;
    private String officeId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupContainer officeBlock;
    protected WebMarkupContainer officeContainer;
    protected String officeValue;
    protected Label officeView;

    protected WebMarkupContainer staffBlock;
    protected WebMarkupContainer staffContainer;
    protected SingleChoiceProvider staffProvider;
    protected Option staffValue;
    protected Select2SingleChoice<Option> staffField;
    protected TextFeedbackPanel staffFeedback;

    protected WebMarkupContainer nameBlock;
    protected WebMarkupContainer nameContainer;
    protected String nameValue;
    protected TextField<String> nameField;
    protected TextFeedbackPanel nameFeedback;

    protected WebMarkupContainer externalIdBlock;
    protected WebMarkupContainer externalIdContainer;
    protected String externalIdValue;
    protected TextField<String> externalIdField;
    protected TextFeedbackPanel externalIdFeedback;

    protected WebMarkupContainer activeBlock;
    protected WebMarkupContainer activeContainer;
    protected Boolean activeValue;
    protected CheckBox activeField;
    protected TextFeedbackPanel activeFeedback;

    protected WebMarkupContainer activationDateBlock;
    protected WebMarkupContainer activationDateContainer;
    protected Date activationDateValue = new Date();
    protected DateTextField activationDateField;
    protected TextFeedbackPanel activationDateFeedback;

    protected WebMarkupContainer submittedOnBlock;
    protected WebMarkupContainer submittedOnContainer;
    protected Date submittedOnValue = new Date();
    protected DateTextField submittedOnField;
    protected TextFeedbackPanel submittedOnFeedback;

    protected List<Map<String, Object>> clientValue = Lists.newLinkedList();
    protected DataTable<Map<String, Object>, String> clientTable;
    protected ListDataProvider clientProvider;
    protected ModalWindow clientPopup;
    protected AjaxLink<Void> clientAddLink;

    protected Option itemClientValue;

    protected static final List<PageBreadcrumb> BREADCRUMB;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        return Model.ofList(BREADCRUMB);
    }

    static {
        BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Clients");
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Groups");
            breadcrumb.setPage(GroupBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Group Create");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        initData();

        PageParameters parameters = new PageParameters();
        parameters.add("centerId", this.centerId);

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", CenterPreviewPage.class, parameters);
        this.form.add(this.closeLink);

        this.officeBlock = new WebMarkupContainer("officeBlock");
        this.form.add(this.officeBlock);
        this.officeContainer = new WebMarkupContainer("officeContainer");
        this.officeBlock.add(this.officeContainer);
        this.officeView = new Label("officeView", new PropertyModel<>(this, "officeValue"));
        this.officeContainer.add(this.officeView);

        this.staffBlock = new WebMarkupContainer("staffBlock");
        this.form.add(this.staffBlock);
        this.staffContainer = new WebMarkupContainer("staffContainer");
        this.staffBlock.add(this.staffContainer);
        this.staffProvider = new SingleChoiceProvider("m_staff", "m_staff.id", "m_staff.display_name");
        this.staffProvider.applyWhere("office_id", "m_staff.office_id = " + this.officeId);
        this.staffField = new Select2SingleChoice<>("staffField", new PropertyModel<>(this, "staffValue"), this.staffProvider);
        this.staffField.setLabel(Model.of("Staff"));
        this.staffField.add(new OnChangeAjaxBehavior());
        this.staffContainer.add(this.staffField);
        this.staffFeedback = new TextFeedbackPanel("staffFeedback", this.staffField);
        this.staffContainer.add(this.staffFeedback);

        this.nameBlock = new WebMarkupContainer("nameBlock");
        this.form.add(this.nameBlock);
        this.nameContainer = new WebMarkupContainer("nameContainer");
        this.nameBlock.add(this.nameContainer);
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setLabel(Model.of("Name"));
        this.nameField.add(new OnChangeAjaxBehavior());
        this.nameContainer.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.nameContainer.add(this.nameFeedback);

        this.externalIdBlock = new WebMarkupContainer("externalIdBlock");
        this.form.add(this.externalIdBlock);
        this.externalIdContainer = new WebMarkupContainer("externalIdContainer");
        this.externalIdBlock.add(this.externalIdContainer);
        this.externalIdField = new TextField<>("externalIdField", new PropertyModel<>(this, "externalIdValue"));
        this.externalIdField.setLabel(Model.of("External ID"));
        this.externalIdField.add(new OnChangeAjaxBehavior());
        this.externalIdContainer.add(this.externalIdField);
        this.externalIdFeedback = new TextFeedbackPanel("externalIdFeedback", this.externalIdField);
        this.externalIdContainer.add(this.externalIdFeedback);

        this.activeBlock = new WebMarkupContainer("activeBlock");
        this.form.add(this.activeBlock);
        this.activeContainer = new WebMarkupContainer("activeContainer");
        this.activeBlock.add(this.activeContainer);
        this.activeField = new CheckBox("activeField", new PropertyModel<>(this, "activeValue"));
        this.activeField.add(new OnChangeAjaxBehavior(this::activeFieldUpdate));
        this.activeContainer.add(this.activeField);
        this.activeFeedback = new TextFeedbackPanel("activeFeedback", this.activeField);
        this.activeContainer.add(this.activeFeedback);

        this.activationDateBlock = new WebMarkupContainer("activationDateBlock");
        this.form.add(this.activationDateBlock);
        this.activationDateContainer = new WebMarkupContainer("activationDateContainer");
        this.activationDateBlock.add(this.activationDateContainer);
        this.activationDateField = new DateTextField("activationDateField", new PropertyModel<>(this, "activationDateValue"));
        this.activationDateField.setLabel(Model.of("Activation Date"));
        this.activationDateField.add(new OnChangeAjaxBehavior());
        this.activationDateContainer.add(this.activationDateField);
        this.activationDateFeedback = new TextFeedbackPanel("activationDateFeedback", this.activationDateField);
        this.activationDateContainer.add(this.activationDateFeedback);

        this.submittedOnBlock = new WebMarkupContainer("submittedOnBlock");
        this.form.add(this.submittedOnBlock);
        this.submittedOnContainer = new WebMarkupContainer("submittedOnContainer");
        this.submittedOnBlock.add(this.submittedOnContainer);
        this.submittedOnField = new DateTextField("submittedOnField", new PropertyModel<>(this, "submittedOnValue"));
        this.submittedOnField.setLabel(Model.of("Submitted On"));
        this.submittedOnField.add(new OnChangeAjaxBehavior());
        this.submittedOnContainer.add(this.submittedOnField);
        this.submittedOnFeedback = new TextFeedbackPanel("submittedOnFeedback", this.submittedOnField);
        this.submittedOnContainer.add(this.submittedOnFeedback);

        {
            this.clientPopup = new ModalWindow("clientPopup");
            add(this.clientPopup);
            this.clientPopup.setOnClose(this::clientPopupClose);

            List<IColumn<Map<String, Object>, String>> clientColumn = Lists.newLinkedList();
            clientColumn.add(new TextColumn(Model.of("Display Name"), "displayName", "displayName", this::clientDisplayNameColumn));
            clientColumn.add(new TextColumn(Model.of("Mobile Number"), "mobileNumber", "mobileNumber", this::clientMobileNumberColumn));
            clientColumn.add(new TextColumn(Model.of("Gender"), "gender", "gender", this::clientGenderColumn));
            clientColumn.add(new TextColumn(Model.of("Account#"), "account", "account", this::clientAccountColumn));
            clientColumn.add(new TextColumn(Model.of("Status"), "status", "status", this::clientStatusColumn));
            clientColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::clientActionItem, this::clientActionClick));
            this.clientProvider = new ListDataProvider(this.clientValue);
            this.clientTable = new DataTable<>("clientTable", clientColumn, this.clientProvider, 20);
            this.form.add(this.clientTable);
            this.clientTable.addTopToolbar(new HeadersToolbar<>(this.clientTable, this.clientProvider));
            this.clientTable.addBottomToolbar(new NoRecordsToolbar(this.clientTable));

            this.clientAddLink = new AjaxLink<>("clientAddLink");
            this.clientAddLink.setOnClick(this::clientAddLinkClick);
            this.form.add(this.clientAddLink);
        }
        activeFieldUpdate(null);
    }

    protected void initData() {
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        this.externalIdValue = generator.externalId();
        this.centerId = getPageParameters().get("centerId").toString();
        this.officeId = jdbcTemplate.queryForObject("select office_id from m_group where id = ?", String.class, this.centerId);
        this.officeValue = jdbcTemplate.queryForObject("select name from m_office where id = ?", String.class, this.officeId);
    }

    protected boolean clientAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.itemClientValue = null;
        if (this.officeValue == null) {
            this.clientPopup.setContent(new OfficePopup("office", this.clientPopup));
            this.clientPopup.show(target);
        } else {
            this.clientPopup.setContent(new ClientPopup(this.clientPopup.getContentId(), this.clientPopup, this, this.officeId));
            this.clientPopup.show(target);
        }
        return false;
    }

    protected ItemPanel clientDisplayNameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel clientGenderColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel clientAccountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel clientStatusColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel clientMobileNumberColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected void clientActionClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
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

    protected List<ActionItem> clientActionItem(String s, Map<String, Object> model) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected void clientPopupClose(String popupName, String signalId, AjaxRequestTarget target) {
        if (this.itemClientValue != null) {
            JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
            SelectQuery query = new SelectQuery("m_client");
            query.addJoin("LEFT JOIN m_code_value gender_code on m_client.gender_cv_id = gender_code.id");
            query.addJoin("LEFT JOIN r_enum_value status on status.enum_id = m_client.status_enum and status.enum_name = 'status_enum'");
            query.addField("concat(m_client.id,'') uuid");
            query.addField("m_client.display_name displayName");
            query.addField("m_client.mobile_no mobileNumber");
            query.addField("gender_code.code_description gender");
            query.addField("m_client.account_no account");
            query.addField("status.enum_value status");
            query.addWhere("m_client.id = :id", this.itemClientValue.getId());
            Map<String, Object> client = named.queryForMap(query.toSQL(), query.getParam());
            this.clientValue.add(client);
            target.add(this.clientTable);
        }
    }

    protected boolean activeFieldUpdate(AjaxRequestTarget target) {
        boolean visible = this.activeValue == null ? false : this.activeValue;
        this.activationDateContainer.setVisible(visible);
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

        JsonNode node = null;
        try {
            node = ClientHelper.createGroup((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }

        PageParameters parameters = new PageParameters();
        parameters.add("centerId", this.centerId);
        setResponsePage(CenterPreviewPage.class, parameters);
    }
}
