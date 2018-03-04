package com.angkorteam.fintech.pages.client.center;

import java.util.Date;
import java.util.Map;

import com.angkorteam.fintech.ddl.MGroup;
import com.angkorteam.fintech.ddl.MStaff;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcNamed;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.CenterBuilder;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
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
public class CenterModifyPage extends Page {

    protected String centerId;
    protected String officeId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock nameBlock;
    protected WebMarkupContainer nameIContainer;
    protected String nameValue;
    protected TextField<String> nameField;
    protected TextFeedbackPanel nameFeedback;

    protected WebMarkupBlock staffBlock;
    protected WebMarkupContainer staffIContainer;
    protected SingleChoiceProvider staffProvider;
    protected Option staffValue;
    protected Select2SingleChoice<Option> staffField;
    protected TextFeedbackPanel staffFeedback;

    protected WebMarkupBlock externalIdBlock;
    protected WebMarkupContainer externalIdIContainer;
    protected String externalIdValue;
    protected TextField<String> externalIdField;
    protected TextFeedbackPanel externalIdFeedback;

    protected WebMarkupBlock activationDateBlock;
    protected WebMarkupContainer activationDateIContainer;
    protected Date activationDateValue;
    protected DateTextField activationDateField;
    protected TextFeedbackPanel activationDateFeedback;

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        PageParameters parameters = new PageParameters();
        parameters.add("centerId", this.centerId);
        this.closeLink = new BookmarkablePageLink<>("closeLink", CenterPreviewPage.class, parameters);
        this.form.add(this.closeLink);

        initNameBlock();

        initStaffBlock();

        initExternalIdBlock();

        initActivationDateBlock();
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected void initActivationDateBlock() {
        this.activationDateBlock = new WebMarkupBlock("activationDateBlock", Size.Six_6);
        this.form.add(this.activationDateBlock);
        this.activationDateIContainer = new WebMarkupContainer("activationDateIContainer");
        this.activationDateBlock.add(this.activationDateIContainer);
        this.activationDateField = new DateTextField("activationDateField", new PropertyModel<>(this, "activationDateValue"));
        this.activationDateField.setLabel(Model.of("Activation Date"));
        this.activationDateField.setRequired(true);
        this.activationDateIContainer.add(this.activationDateField);
        this.activationDateFeedback = new TextFeedbackPanel("activationDateFeedback", this.activationDateField);
        this.activationDateIContainer.add(this.activationDateFeedback);
    }

    protected void initExternalIdBlock() {
        this.externalIdBlock = new WebMarkupBlock("externalIdBlock", Size.Six_6);
        this.form.add(this.externalIdBlock);
        this.externalIdIContainer = new WebMarkupContainer("externalIdIContainer");
        this.externalIdBlock.add(this.externalIdIContainer);
        this.externalIdField = new TextField<>("externalIdField", new PropertyModel<>(this, "externalIdValue"));
        this.externalIdField.setLabel(Model.of("External ID"));
        this.externalIdField.setRequired(true);
        this.externalIdIContainer.add(this.externalIdField);
        this.externalIdFeedback = new TextFeedbackPanel("externalIdFeedback", this.externalIdField);
        this.externalIdIContainer.add(this.externalIdFeedback);
    }

    protected void initStaffBlock() {
        this.staffBlock = new WebMarkupBlock("staffBlock", Size.Six_6);
        this.form.add(this.staffBlock);
        this.staffIContainer = new WebMarkupContainer("staffIContainer");
        this.staffBlock.add(this.staffIContainer);
        this.staffProvider = new SingleChoiceProvider(MStaff.NAME, MStaff.Field.ID, MStaff.Field.DISPLAY_NAME);
        this.staffProvider.applyWhere("office_id", MStaff.Field.OFFICE_ID + " = " + this.officeId);
        this.staffProvider.applyWhere("is_active", MStaff.Field.IS_ACTIVE + " = 1");
        this.staffField = new Select2SingleChoice<>("staffField", new PropertyModel<>(this, "staffValue"), this.staffProvider);
        this.staffField.setLabel(Model.of("Staff"));
        this.staffField.add(new OnChangeAjaxBehavior());
        this.staffField.setRequired(true);
        this.staffIContainer.add(this.staffField);
        this.staffFeedback = new TextFeedbackPanel("staffFeedback", this.staffField);
        this.staffIContainer.add(this.staffFeedback);
    }

    protected void initNameBlock() {
        this.nameBlock = new WebMarkupBlock("nameBlock", Size.Six_6);
        this.form.add(this.nameBlock);
        this.nameIContainer = new WebMarkupContainer("nameIContainer");
        this.nameBlock.add(this.nameIContainer);
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setLabel(Model.of("Name"));
        this.nameField.setRequired(true);
        this.nameIContainer.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.nameIContainer.add(this.nameFeedback);
    }

    @Override
    protected void initData() {
        this.centerId = getPageParameters().get("centerId").toString();
        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery(MGroup.NAME);
        selectQuery.addWhere(MGroup.Field.ID + " = :" + MGroup.Field.ID, this.centerId);
        selectQuery.addField(MGroup.Field.OFFICE_ID);
        selectQuery.addField(MGroup.Field.DISPLAY_NAME);
        selectQuery.addField(MGroup.Field.STAFF_ID);
        selectQuery.addField(MGroup.Field.EXTERNAL_ID);
        selectQuery.addField(MGroup.Field.ACTIVATION_DATE);
        Map<String, Object> centerObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
        this.officeId = String.valueOf(centerObject.get("office_id"));
        this.nameValue = (String) centerObject.get("display_name");

        selectQuery = new SelectQuery(MStaff.NAME);
        selectQuery.addWhere(MStaff.Field.ID + " = :" + MStaff.Field.ID, centerObject.get("staff_id"));
        selectQuery.addField(MStaff.Field.ID);
        selectQuery.addField(MStaff.Field.DISPLAY_NAME + " AS text");
        this.staffValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
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
