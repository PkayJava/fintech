package com.angkorteam.fintech.pages.client.center;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

import com.angkorteam.fintech.DeprecatedPage;
import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
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
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class CenterCreatePage extends DeprecatedPage {

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupContainer nameBlock;
    protected WebMarkupContainer nameContainer;
    protected String nameValue;
    protected TextField<String> nameField;
    protected TextFeedbackPanel nameFeedback;

    protected WebMarkupContainer officeBlock;
    protected WebMarkupContainer officeContainer;
    protected SingleChoiceProvider officeProvider;
    protected Option officeValue;
    protected Select2SingleChoice<Option> officeField;
    protected TextFeedbackPanel officeFeedback;

    protected WebMarkupContainer staffBlock;
    protected WebMarkupContainer staffContainer;
    protected SingleChoiceProvider staffProvider;
    protected Option staffValue;
    protected Select2SingleChoice<Option> staffField;
    protected TextFeedbackPanel staffFeedback;

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

    protected WebMarkupContainer externalIdBlock;
    protected WebMarkupContainer externalIdContainer;
    protected String externalIdValue;
    protected TextField<String> externalIdField;
    protected TextFeedbackPanel externalIdFeedback;

    protected WebMarkupContainer submittedOnBlock;
    protected WebMarkupContainer submittedOnContainer;
    protected Date submittedOnValue = new Date();
    protected DateTextField submittedOnField;
    protected TextFeedbackPanel submittedOnFeedback;

    protected List<Map<String, Object>> groupValue = Lists.newLinkedList();
    protected DataTable<Map<String, Object>, String> groupTable;
    protected ListDataProvider groupProvider;
    protected ModalWindow groupPopup;
    protected AjaxLink<Void> groupAddLink;

    protected Option itemGroupValue;

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
            breadcrumb.setLabel("Centers");
            breadcrumb.setPage(CenterBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Group Create");
            BREADCRUMB.add(breadcrumb);
        }
    }

    protected void initData() {
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        this.externalIdValue = generator.externalId();
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        
        initData();

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", ClientBrowsePage.class);
        this.form.add(this.closeLink);

        this.officeBlock = new WebMarkupContainer("officeBlock");
        this.officeBlock.setOutputMarkupId(true);
        this.form.add(this.officeBlock);
        this.officeContainer = new WebMarkupContainer("officeContainer");
        this.officeBlock.add(this.officeContainer);
        this.officeProvider = new SingleChoiceProvider("m_office", "id", "name");
        this.officeField = new Select2SingleChoice<>("officeField", 0, new PropertyModel<>(this, "officeValue"), this.officeProvider);
        this.officeField.setLabel(Model.of("Office"));
        this.officeField.add(new OnChangeAjaxBehavior(this::officeFieldUpdate));
        this.officeContainer.add(this.officeField);
        this.officeFeedback = new TextFeedbackPanel("officeFeedback", this.officeField);
        this.officeContainer.add(this.officeFeedback);

        this.staffBlock = new WebMarkupContainer("staffBlock");
        this.staffBlock.setOutputMarkupId(true);
        this.form.add(this.staffBlock);
        this.staffContainer = new WebMarkupContainer("staffContainer");
        this.staffBlock.add(this.staffContainer);
        this.staffProvider = new SingleChoiceProvider("m_staff", "m_staff.id", "m_staff.display_name");
        this.staffProvider.addJoin("inner join m_office on m_staff.office_id = m_office.id");
        this.staffField = new Select2SingleChoice<>("staffField", 0, new PropertyModel<>(this, "staffValue"), this.staffProvider);
        this.staffField.setLabel(Model.of("Staff"));
        this.staffField.add(new OnChangeAjaxBehavior());
        this.staffContainer.add(this.staffField);
        this.staffFeedback = new TextFeedbackPanel("staffFeedback", this.staffField);
        this.staffContainer.add(this.staffFeedback);

        this.nameBlock = new WebMarkupContainer("nameBlock");
        this.nameBlock.setOutputMarkupId(true);
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
        this.externalIdBlock.setOutputMarkupId(true);
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
        this.activeBlock.setOutputMarkupId(true);
        this.form.add(this.activeBlock);
        this.activeContainer = new WebMarkupContainer("activeContainer");
        this.activeBlock.add(this.activeContainer);
        this.activeField = new CheckBox("activeField", new PropertyModel<>(this, "activeValue"));
        this.activeField.add(new OnChangeAjaxBehavior(this::activeFieldUpdate));
        this.activeContainer.add(this.activeField);
        this.activeFeedback = new TextFeedbackPanel("activeFeedback", this.activeField);
        this.activeContainer.add(this.activeFeedback);

        this.activationDateBlock = new WebMarkupContainer("activationDateBlock");
        this.activationDateBlock.setOutputMarkupId(true);
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
        this.submittedOnBlock.setOutputMarkupId(true);
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
            this.groupPopup = new ModalWindow("groupPopup");
            add(this.groupPopup);
            this.groupPopup.setOnClose(this::groupPopupOnClose);

            List<IColumn<Map<String, Object>, String>> groupColumn = Lists.newLinkedList();
            groupColumn.add(new TextColumn(Model.of("Display Name"), "displayName", "displayName", this::groupDisplayNameColumn));
            groupColumn.add(new TextColumn(Model.of("Account#"), "account", "account", this::groupAccountColumn));
            groupColumn.add(new TextColumn(Model.of("Status"), "status", "status", this::groupStatusColumn));
            groupColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::groupActionItem, this::groupActionClick));
            this.groupProvider = new ListDataProvider(this.groupValue);
            this.groupTable = new DataTable<>("groupTable", groupColumn, this.groupProvider, 20);
            this.form.add(this.groupTable);
            this.groupTable.addTopToolbar(new HeadersToolbar<>(this.groupTable, this.groupProvider));
            this.groupTable.addBottomToolbar(new NoRecordsToolbar(this.groupTable));

            this.groupAddLink = new AjaxLink<>("groupAddLink");
            this.groupAddLink.setOnClick(this::groupAddLinkClick);
            this.form.add(this.groupAddLink);
        }
        officeFieldUpdate(null);
        activeFieldUpdate(null);
    }

    protected boolean groupAddLinkClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        this.itemGroupValue = null;
        if (this.officeValue == null) {
            this.groupPopup.setContent(new OfficePopup(this.groupPopup.getContentId()));
            this.groupPopup.show(target);
        } else {
            this.groupPopup.setContent(new GroupPopup(this.groupPopup.getContentId(), this.groupPopup, this, this.officeValue.getId()));
            this.groupPopup.show(target);
        }
        return false;
    }

    protected ItemPanel groupDisplayNameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel groupAccountColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel groupStatusColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected void groupActionClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
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

    protected List<ActionItem> groupActionItem(String s, Map<String, Object> model) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected void groupPopupOnClose(String elementId, AjaxRequestTarget target) {
        if (this.itemGroupValue != null) {
            JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
            SelectQuery query = new SelectQuery("m_group");
            query.addJoin("LEFT JOIN r_enum_value status on status.enum_id = m_group.status_enum and status.enum_name = 'status_enum'");
            query.addField("concat(m_group.id,'') uuid");
            query.addField("m_group.display_name displayName");
            query.addField("m_group.account_no account");
            query.addField("status.enum_value status");
            query.addWhere("m_group.id = :id", this.itemGroupValue.getId());
            Map<String, Object> group = named.queryForMap(query.toSQL(), query.getParam());
            this.groupValue.add(group);
            target.add(this.groupTable);
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

    protected boolean officeFieldUpdate(AjaxRequestTarget target) {
        boolean visible = this.officeValue != null;
        this.staffValue = null;
        this.groupValue.clear();
        if (this.officeValue == null) {
            this.staffProvider.setDisabled(true);
        } else {
            this.staffProvider.setDisabled(false);
            this.staffProvider.applyWhere("office", "m_office.id = " + this.officeValue.getId());
        }
        this.staffContainer.setVisible(visible);
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

        JsonNode node = null;
        try {
            node = ClientHelper.createCenter((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(CenterBrowsePage.class);
    }

}
