//package com.angkorteam.fintech.pages.client.center;
//
//import java.util.Date;
//import java.util.Map;
//
//import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
//import org.apache.wicket.markup.html.form.TextField;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//import org.apache.wicket.request.mapper.parameter.PageParameters;
//
//import com.angkorteam.fintech.Page;
//import com.angkorteam.fintech.Session;
//import com.angkorteam.fintech.ddl.MGroup;
//import com.angkorteam.fintech.ddl.MStaff;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.dto.builder.CenterBuilder;
//import com.angkorteam.fintech.helper.ClientHelper;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.provider.SingleChoiceProvider;
//import com.angkorteam.framework.SpringBean;
//import com.angkorteam.framework.jdbc.SelectQuery;
//import com.angkorteam.framework.spring.JdbcNamed;
//import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
//
//import io.github.openunirest.http.JsonNode;
//
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class CenterModifyPage extends Page {
//
//    protected String centerId;
//    protected String officeId;
//
//    protected Form<Void> form;
//    protected Button saveButton;
//    protected BookmarkablePageLink<Void> closeLink;
//
//    protected UIRow row1;
//
//    protected UIBlock nameBlock;
//    protected UIContainer nameIContainer;
//    protected String nameValue;
//    protected TextField<String> nameField;
//
//    protected UIBlock staffBlock;
//    protected UIContainer staffIContainer;
//    protected SingleChoiceProvider staffProvider;
//    protected Option staffValue;
//    protected Select2SingleChoice<Option> staffField;
//
//    protected UIRow row2;
//
//    protected UIBlock externalIdBlock;
//    protected UIContainer externalIdIContainer;
//    protected String externalIdValue;
//    protected TextField<String> externalIdField;
//
//    protected UIBlock activationDateBlock;
//    protected UIContainer activationDateIContainer;
//    protected Date activationDateValue;
//    protected DateTextField activationDateField;
//
//    @Override
//    protected void initComponent() {
//        this.form = new Form<>("form");
//        add(this.form);
//
//        this.saveButton = new Button("saveButton");
//        this.saveButton.setOnSubmit(this::saveButtonSubmit);
//        this.form.add(this.saveButton);
//
//        PageParameters parameters = new PageParameters();
//        parameters.add("centerId", this.centerId);
//        this.closeLink = new BookmarkablePageLink<>("closeLink", CenterPreviewPage.class, parameters);
//        this.form.add(this.closeLink);
//
//        this.row1 = UIRow.newUIRow("row1", this.form);
//
//        this.nameBlock = this.row1.newUIBlock("nameBlock", Size.Six_6);
//        this.nameIContainer = this.nameBlock.newUIContainer("nameIContainer");
//        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
//        this.nameIContainer.add(this.nameField);
//        this.nameIContainer.newFeedback("nameFeedback", this.nameField);
//
//        this.staffBlock = this.row1.newUIBlock("staffBlock", Size.Six_6);
//        this.staffIContainer = this.staffBlock.newUIContainer("staffIContainer");
//        this.staffField = new Select2SingleChoice<>("staffField", new PropertyModel<>(this, "staffValue"), this.staffProvider);
//        this.staffIContainer.add(this.staffField);
//        this.staffIContainer.newFeedback("staffFeedback", this.staffField);
//
//        this.row2 = UIRow.newUIRow("row2", this.form);
//
//        this.externalIdBlock = this.row2.newUIBlock("externalIdBlock", Size.Six_6);
//        this.externalIdIContainer = this.externalIdBlock.newUIContainer("externalIdIContainer");
//        this.externalIdField = new TextField<>("externalIdField", new PropertyModel<>(this, "externalIdValue"));
//        this.externalIdIContainer.add(this.externalIdField);
//        this.externalIdIContainer.newFeedback("externalIdFeedback", this.externalIdField);
//
//        this.activationDateBlock = this.row2.newUIBlock("activationDateBlock", Size.Six_6);
//        this.activationDateIContainer = this.activationDateBlock.newUIContainer("activationDateIContainer");
//        this.activationDateField = new DateTextField("activationDateField", new PropertyModel<>(this, "activationDateValue"));
//        this.activationDateIContainer.add(this.activationDateField);
//        this.activationDateIContainer.newFeedback("activationDateFeedback", this.activationDateField);
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.activationDateField.setLabel(Model.of("Activation Date"));
//        this.activationDateField.setRequired(true);
//
//        this.externalIdField.setLabel(Model.of("External ID"));
//        this.externalIdField.setRequired(true);
//
//        this.staffField.setLabel(Model.of("Staff"));
//        this.staffField.add(new OnChangeAjaxBehavior());
//        this.staffField.setRequired(true);
//
//        this.nameField.setLabel(Model.of("Name"));
//        this.nameField.setRequired(true);
//    }
//
//    @Override
//    protected void initData() {
//        this.centerId = getPageParameters().get("centerId").toString();
//        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
//
//        SelectQuery selectQuery = null;
//
//        selectQuery = new SelectQuery(MGroup.NAME);
//        selectQuery.addWhere(MGroup.Field.ID + " = :" + MGroup.Field.ID, this.centerId);
//        selectQuery.addField(MGroup.Field.OFFICE_ID);
//        selectQuery.addField(MGroup.Field.DISPLAY_NAME);
//        selectQuery.addField(MGroup.Field.STAFF_ID);
//        selectQuery.addField(MGroup.Field.EXTERNAL_ID);
//        selectQuery.addField(MGroup.Field.ACTIVATION_DATE);
//        Map<String, Object> centerObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
//        this.officeId = String.valueOf(centerObject.get("office_id"));
//        this.nameValue = (String) centerObject.get("display_name");
//
//        selectQuery = new SelectQuery(MStaff.NAME);
//        selectQuery.addWhere(MStaff.Field.ID + " = :" + MStaff.Field.ID, centerObject.get("staff_id"));
//        selectQuery.addField(MStaff.Field.ID);
//        selectQuery.addField(MStaff.Field.DISPLAY_NAME + " AS text");
//        this.staffValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
//        this.externalIdValue = (String) centerObject.get("external_id");
//        this.activationDateValue = (Date) centerObject.get("activation_date");
//
//        this.staffProvider = new SingleChoiceProvider(MStaff.NAME, MStaff.Field.ID, MStaff.Field.DISPLAY_NAME);
//        this.staffProvider.applyWhere("office_id", MStaff.Field.OFFICE_ID + " = " + this.officeId);
//        this.staffProvider.applyWhere("is_active", MStaff.Field.IS_ACTIVE + " = 1");
//    }
//
//    protected void saveButtonSubmit(Button button) {
//        CenterBuilder builder = new CenterBuilder();
//        builder.withId(this.centerId);
//        builder.withExternalId(this.externalIdValue);
//        builder.withName(this.nameValue);
//        if (this.staffValue != null) {
//            builder.withStaffId(this.staffValue.getId());
//        }
//        builder.withActivationDate(this.activationDateValue);
//
//        JsonNode node = ClientHelper.updateCenter((Session) getSession(), builder.build());
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
