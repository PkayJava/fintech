package com.angkorteam.fintech.pages.client.center;

import java.util.Date;
import java.util.Map;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.DeprecatedPage;
import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.CenterBuilder;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class CenterModifyPage extends DeprecatedPage {

    protected String centerId;
    protected String officeId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupContainer nameBlock;
    protected WebMarkupContainer nameContainer;
    protected String nameValue;
    protected TextField<String> nameField;
    protected TextFeedbackPanel nameFeedback;

    protected WebMarkupContainer staffBlock;
    protected WebMarkupContainer staffContainer;
    protected SingleChoiceProvider staffProvider;
    protected Option staffValue;
    protected Select2SingleChoice<Option> staffField;
    protected TextFeedbackPanel staffFeedback;

    protected WebMarkupContainer externalIdBlock;
    protected WebMarkupContainer externalIdContainer;
    protected String externalIdValue;
    protected TextField<String> externalIdField;
    protected TextFeedbackPanel externalIdFeedback;

    protected WebMarkupContainer activationDateBlock;
    protected WebMarkupContainer activationDateContainer;
    protected Date activationDateValue;
    protected DateTextField activationDateField;
    protected TextFeedbackPanel activationDateFeedback;

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

        this.nameBlock = new WebMarkupContainer("nameBlock");
        this.nameBlock.setOutputMarkupId(true);
        this.form.add(this.nameBlock);
        this.nameContainer = new WebMarkupContainer("nameContainer");
        this.nameBlock.add(this.nameContainer);
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setLabel(Model.of("Name"));
        this.nameField.setRequired(true);
        this.nameContainer.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.nameContainer.add(this.nameFeedback);

        this.staffBlock = new WebMarkupContainer("staffBlock");
        this.form.add(this.staffBlock);
        this.staffContainer = new WebMarkupContainer("staffContainer");
        this.staffBlock.add(this.staffContainer);
        this.staffProvider = new SingleChoiceProvider("m_staff", "id", "display_name");
        this.staffProvider.applyWhere("office_id", "office_id = " + this.officeId);
        this.staffProvider.applyWhere("is_active", "is_active = 1");
        this.staffField = new Select2SingleChoice<>("staffField", 0, new PropertyModel<>(this, "staffValue"), this.staffProvider);
        this.staffField.setLabel(Model.of("Staff"));
        this.staffField.add(new OnChangeAjaxBehavior());
        this.staffField.setRequired(true);
        this.staffContainer.add(this.staffField);
        this.staffFeedback = new TextFeedbackPanel("staffFeedback", this.staffField);
        this.staffContainer.add(this.staffFeedback);

        this.externalIdBlock = new WebMarkupContainer("externalIdBlock");
        this.externalIdBlock.setOutputMarkupId(true);
        this.form.add(this.externalIdBlock);
        this.externalIdContainer = new WebMarkupContainer("externalIdContainer");
        this.externalIdBlock.add(this.externalIdContainer);
        this.externalIdField = new TextField<>("externalIdField", new PropertyModel<>(this, "externalIdValue"));
        this.externalIdField.setLabel(Model.of("External ID"));
        this.externalIdField.setRequired(true);
        this.externalIdContainer.add(this.externalIdField);
        this.externalIdFeedback = new TextFeedbackPanel("externalIdFeedback", this.externalIdField);
        this.externalIdContainer.add(this.externalIdFeedback);

        this.activationDateBlock = new WebMarkupContainer("activationDateBlock");
        this.activationDateBlock.setOutputMarkupId(true);
        this.form.add(this.activationDateBlock);
        this.activationDateContainer = new WebMarkupContainer("activationDateContainer");
        this.activationDateBlock.add(this.activationDateContainer);
        this.activationDateField = new DateTextField("activationDateField", new PropertyModel<>(this, "activationDateValue"));
        this.activationDateField.setLabel(Model.of("Activation Date"));
        this.activationDateField.setRequired(true);
        this.activationDateContainer.add(this.activationDateField);
        this.activationDateFeedback = new TextFeedbackPanel("activationDateFeedback", this.activationDateField);
        this.activationDateContainer.add(this.activationDateFeedback);

    }

    protected void initData() {
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        this.centerId = getPageParameters().get("centerId").toString();
        Map<String, Object> centerObject = jdbcTemplate.queryForMap("select * from m_group where id = ?", this.centerId);
        this.officeId = String.valueOf(centerObject.get("office_id"));
        this.nameValue = (String) centerObject.get("display_name");
        this.staffValue = jdbcTemplate.queryForObject("select id, display_name as text from m_staff where id = ?", Option.MAPPER, centerObject.get("staff_id"));
        this.externalIdValue = (String) centerObject.get("external_id");
        this.activationDateValue = (Date) centerObject.get("activation_date");
    }

    protected void saveButtonSubmit(Button button) {
        CenterBuilder builder = new CenterBuilder();
        builder.withId(this.centerId);
        builder.withExternalId(this.externalIdValue);
        builder.withName(this.nameValue);
        if (this.staffValue != null) {
            builder.withStaffId(this.staffValue.getId());
        }
        builder.withActivationDate(this.activationDateValue);

        JsonNode node = null;
        try {
            node = ClientHelper.updateCenter((Session) getSession(), builder.build());
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
