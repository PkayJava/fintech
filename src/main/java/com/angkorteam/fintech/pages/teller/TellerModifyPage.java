//package com.angkorteam.fintech.pages.teller;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
//import org.apache.wicket.markup.html.form.TextArea;
//import org.apache.wicket.markup.html.form.TextField;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.IModel;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//import org.apache.wicket.request.mapper.parameter.PageParameters;
//
//import com.angkorteam.fintech.Page;
//import com.angkorteam.fintech.Session;
//import com.angkorteam.fintech.ddl.MOffice;
//import com.angkorteam.fintech.ddl.MTellers;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.dto.builder.TellerBuilder;
//import com.angkorteam.fintech.dto.constant.TellerStatus;
//import com.angkorteam.fintech.helper.TellerHelper;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.layout.UIBlock;
//import com.angkorteam.fintech.layout.UIContainer;
//import com.angkorteam.fintech.layout.UIRow;
//import com.angkorteam.fintech.pages.OrganizationDashboardPage;
//import com.angkorteam.fintech.provider.SingleChoiceProvider;
//import com.angkorteam.fintech.provider.TellerStateProvider;
//import com.angkorteam.framework.SpringBean;
//import com.angkorteam.framework.jdbc.SelectQuery;
//import com.angkorteam.framework.models.PageBreadcrumb;
//import com.angkorteam.framework.spring.JdbcNamed;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
//import com.google.common.collect.Lists;
//
//import io.github.openunirest.http.JsonNode;
//
///**
// * Created by socheatkhauv on 7/13/17.
// */
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class TellerModifyPage extends Page {
//
//    protected String tellerId;
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
//    protected UIRow row2;
//
//    protected UIBlock officeBlock;
//    protected UIContainer officeIContainer;
//    protected SingleChoiceProvider officeProvider;
//    protected Option officeValue;
//    protected Select2SingleChoice<Option> officeField;
//
//    protected UIRow row3;
//
//    protected UIBlock statusBlock;
//    protected UIContainer statusIContainer;
//    protected TellerStateProvider statusProvider;
//    protected Option statusValue;
//    protected Select2SingleChoice<Option> statusField;
//
//    protected UIRow row4;
//
//    protected UIBlock startDateBlock;
//    protected UIContainer startDateIContainer;
//    protected Date startDateValue;
//    protected DateTextField startDateField;
//
//    protected UIRow row5;
//
//    protected UIBlock endDateBlock;
//    protected UIContainer endDateIContainer;
//    protected Date endDateValue;
//    protected DateTextField endDateField;
//
//    protected UIRow row6;
//
//    protected UIBlock descriptionBlock;
//    protected UIContainer descriptionIContainer;
//    protected String descriptionValue;
//    protected TextArea<String> descriptionField;
//
//    @Override
//    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
//        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Admin");
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Organization");
//            breadcrumb.setPage(OrganizationDashboardPage.class);
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Teller");
//            breadcrumb.setPage(TellerBrowsePage.class);
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Teller Modify");
//            BREADCRUMB.add(breadcrumb);
//        }
//        return Model.ofList(BREADCRUMB);
//    }
//
//    @Override
//    protected void initData() {
//        PageParameters parameters = getPageParameters();
//        this.tellerId = parameters.get("tellerId").toString("");
//
//        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
//
//        SelectQuery selectQuery = null;
//
//        selectQuery = new SelectQuery(MTellers.NAME);
//        selectQuery.addWhere(MTellers.Field.ID + " = :" + MTellers.Field.ID, this.tellerId);
//        selectQuery.addField(MTellers.Field.OFFICE_ID);
//        selectQuery.addField(MTellers.Field.STATE);
//        selectQuery.addField(MTellers.Field.NAME);
//        selectQuery.addField(MTellers.Field.VALID_FROM);
//        selectQuery.addField(MTellers.Field.VALID_TO);
//        selectQuery.addField(MTellers.Field.DESCRIPTION);
//        Map<String, Object> tellerObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
//
//        selectQuery = new SelectQuery(MOffice.NAME);
//        selectQuery.addField(MOffice.Field.ID);
//        selectQuery.addField(MOffice.Field.NAME + " as text");
//        selectQuery.addWhere(MOffice.Field.ID + " = :" + MOffice.Field.ID, tellerObject.get(MTellers.Field.OFFICE_ID));
//        this.officeValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
//
//        this.statusValue = TellerStatus.optionLiteral(String.valueOf(tellerObject.get(MTellers.Field.STATE)));
//        this.nameValue = (String) tellerObject.get(MTellers.Field.NAME);
//        this.startDateValue = (Date) tellerObject.get(MTellers.Field.VALID_FROM);
//        this.endDateValue = (Date) tellerObject.get(MTellers.Field.VALID_TO);
//        this.descriptionValue = (String) tellerObject.get(MTellers.Field.DESCRIPTION);
//
//        this.officeProvider = new SingleChoiceProvider(MOffice.NAME, MOffice.Field.ID, MOffice.Field.NAME);
//        this.statusProvider = new TellerStateProvider();
//    }
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
//        this.closeLink = new BookmarkablePageLink<>("closeLink", TellerBrowsePage.class);
//        this.form.add(this.closeLink);
//
//        this.row1 = UIRow.newUIRow("row1", this.form);
//
//        this.nameBlock = this.row1.newUIBlock("nameBlock", Size.Twelve_12);
//        this.nameIContainer = this.nameBlock.newUIContainer("nameIContainer");
//        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
//        this.nameIContainer.add(this.nameField);
//        this.nameIContainer.newFeedback("nameFeedback", this.nameField);
//
//        this.row2 = UIRow.newUIRow("row2", this.form);
//
//        this.officeBlock = this.row2.newUIBlock("officeBlock", Size.Twelve_12);
//        this.officeIContainer = this.officeBlock.newUIContainer("officeIContainer");
//        this.officeField = new Select2SingleChoice<>("officeField", new PropertyModel<>(this, "officeValue"), this.officeProvider);
//        this.officeIContainer.add(this.officeField);
//        this.officeIContainer.newFeedback("officeFeedback", this.officeField);
//
//        this.row3 = UIRow.newUIRow("row3", this.form);
//
//        this.descriptionBlock = this.row3.newUIBlock("descriptionBlock", Size.Twelve_12);
//        this.descriptionIContainer = this.descriptionBlock.newUIContainer("descriptionIContainer");
//        this.descriptionField = new TextArea<>("descriptionField", new PropertyModel<>(this, "descriptionValue"));
//        this.descriptionIContainer.add(this.descriptionField);
//        this.descriptionIContainer.newFeedback("descriptionFeedback", this.descriptionField);
//
//        this.row4 = UIRow.newUIRow("row4", this.form);
//
//        this.startDateBlock = this.row4.newUIBlock("startDateBlock", Size.Twelve_12);
//        this.startDateIContainer = this.startDateBlock.newUIContainer("startDateIContainer");
//        this.startDateField = new DateTextField("startDateField", new PropertyModel<>(this, "startDateValue"));
//        this.startDateIContainer.add(this.startDateField);
//        this.startDateIContainer.newFeedback("startDateFeedback", this.startDateField);
//
//        this.row5 = UIRow.newUIRow("row5", this.form);
//
//        this.endDateBlock = this.row5.newUIBlock("endDateBlock", Size.Twelve_12);
//        this.endDateIContainer = this.endDateBlock.newUIContainer("endDateIContainer");
//        this.endDateField = new DateTextField("endDateField", new PropertyModel<>(this, "endDateValue"));
//        this.endDateIContainer.add(this.endDateField);
//        this.endDateIContainer.newFeedback("endDateFeedback", this.endDateField);
//
//        this.row6 = UIRow.newUIRow("row6", this.form);
//
//        this.statusBlock = this.row6.newUIBlock("statusBlock", Size.Twelve_12);
//        this.statusIContainer = this.statusBlock.newUIContainer("statusIContainer");
//        this.statusField = new Select2SingleChoice<>("statusField", new PropertyModel<>(this, "statusValue"), this.statusProvider);
//        this.statusIContainer.add(this.statusField);
//        this.statusIContainer.newFeedback("statusFeedback", this.statusField);
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.endDateField.setRequired(true);
//        this.startDateField.setRequired(true);
//        this.descriptionField.setRequired(true);
//        this.officeField.setRequired(true);
//        this.nameField.setRequired(true);
//    }
//
//    protected void saveButtonSubmit(Button button) {
//        TellerBuilder builder = new TellerBuilder();
//        builder.withId(this.tellerId);
//        builder.withDescription(this.descriptionValue);
//        builder.withName(this.nameValue);
//        builder.withEndDate(this.endDateValue);
//        builder.withStartDate(this.startDateValue);
//        builder.withStatus(TellerStatus.valueOf(this.statusValue.getId()));
//        if (this.officeValue != null) {
//            builder.withOfficeId(this.officeValue.getId());
//        }
//
//        JsonNode node = TellerHelper.update((Session) getSession(), builder.build());
//
//        if (reportError(node)) {
//            return;
//        }
//        setResponsePage(TellerBrowsePage.class);
//
//    }
//
//}
