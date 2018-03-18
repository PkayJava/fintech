package com.angkorteam.fintech.pages.office;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.MOffice;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.OfficeBuilder;
import com.angkorteam.fintech.helper.OfficeHelper;
import com.angkorteam.fintech.pages.OrganizationDashboardPage;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;

/**
 * Created by socheatkhauv on 6/25/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class OfficeModifyPage extends Page {

    protected String officeId;

    protected WebMarkupBlock externalIdBlock;
    protected WebMarkupContainer externalIdIContainer;
    protected String externalIdValue;
    protected TextField<String> externalIdField;
    protected TextFeedbackPanel externalIdFeedback;

    protected WebMarkupBlock nameBlock;
    protected WebMarkupContainer nameIContainer;
    protected String nameValue;
    protected TextField<String> nameField;
    protected TextFeedbackPanel nameFeedback;

    protected WebMarkupBlock parentBlock;
    protected WebMarkupContainer parentIContainer;
    protected SingleChoiceProvider parentProvider;
    protected Option parentValue;
    protected Select2SingleChoice<Option> parentField;
    protected TextFeedbackPanel parentFeedback;

    protected WebMarkupBlock openingDateBlock;
    protected WebMarkupContainer openingDateIContainer;
    protected Date openingDateValue;
    protected DateTextField openingDateField;
    protected TextFeedbackPanel openingDateFeedback;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Admin");
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Organization");
            breadcrumb.setPage(OrganizationDashboardPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Office");
            breadcrumb.setPage(OfficeBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Office Modify");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", OfficeBrowsePage.class);
        this.form.add(this.closeLink);

        this.externalIdBlock = new WebMarkupBlock("externalIdBlock", Size.Twelve_12);
        this.form.add(this.externalIdBlock);
        this.externalIdIContainer = new WebMarkupContainer("externalIdIContainer");
        this.externalIdBlock.add(this.externalIdIContainer);
        this.externalIdField = new TextField<>("externalIdField", new PropertyModel<>(this, "externalIdValue"));
        this.externalIdField.setRequired(true);
        this.externalIdIContainer.add(this.externalIdField);
        this.externalIdFeedback = new TextFeedbackPanel("externalIdFeedback", this.externalIdField);
        this.externalIdIContainer.add(this.externalIdFeedback);

        this.nameBlock = new WebMarkupBlock("nameBlock", Size.Twelve_12);
        this.form.add(this.nameBlock);
        this.nameIContainer = new WebMarkupContainer("nameIContainer");
        this.nameBlock.add(this.nameIContainer);
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setRequired(true);
        this.nameIContainer.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.nameIContainer.add(this.nameFeedback);

        this.openingDateBlock = new WebMarkupBlock("openingDateBlock", Size.Twelve_12);
        this.form.add(this.openingDateBlock);
        this.openingDateIContainer = new WebMarkupContainer("openingDateIContainer");
        this.openingDateBlock.add(this.openingDateIContainer);
        this.openingDateField = new DateTextField("openingDateField", new PropertyModel<>(this, "openingDateValue"));
        this.openingDateField.setRequired(true);
        this.openingDateIContainer.add(this.openingDateField);
        this.openingDateFeedback = new TextFeedbackPanel("openingDateFeedback", this.openingDateField);
        this.openingDateIContainer.add(this.openingDateFeedback);

        this.parentBlock = new WebMarkupBlock("parentBlock", Size.Twelve_12);
        this.form.add(this.parentBlock);
        this.parentIContainer = new WebMarkupContainer("parentIContainer");
        this.parentBlock.add(this.parentIContainer);
        this.parentProvider = new SingleChoiceProvider(MOffice.NAME, MOffice.Field.ID, MOffice.Field.NAME);
        this.parentField = new Select2SingleChoice<>("parentField", new PropertyModel<>(this, "parentValue"), this.parentProvider);
        this.parentField.setRequired(true);
        this.parentIContainer.add(this.parentField);
        this.parentFeedback = new TextFeedbackPanel("parentFeedback", this.parentField);
        this.parentIContainer.add(this.parentFeedback);
    }

    @Override
    protected void initData() {
        PageParameters parameters = getPageParameters();
        this.officeId = parameters.get("officeId").toString("");

        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery(MOffice.NAME);
        selectQuery.addField(MOffice.Field.EXTERNAL_ID);
        selectQuery.addField(MOffice.Field.NAME);
        selectQuery.addField(MOffice.Field.OPENING_DATE);
        selectQuery.addWhere(MOffice.Field.ID + " = :" + MOffice.Field.ID, this.officeId);
        Map<String, Object> object = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        this.externalIdValue = (String) object.get("external_id");
        this.nameValue = (String) object.get("name");
        this.openingDateValue = (Date) object.get("opening_date");

        selectQuery = new SelectQuery(MOffice.NAME);
        selectQuery.addWhere(MOffice.Field.ID + " = :" + MOffice.Field.ID, object.get("parent_id"));
        selectQuery.addField(MOffice.Field.ID);
        selectQuery.addField(MOffice.Field.NAME + " as text");
        this.parentValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
    }

    protected void saveButtonSubmit(Button button) {
        OfficeBuilder builder = new OfficeBuilder();
        builder.withName(this.nameValue);
        if (this.parentValue != null) {
            builder.withParentId(this.parentValue.getId());
        }
        builder.withOpeningDate(this.openingDateValue);
        builder.withExternalId(this.externalIdValue);
        builder.withId(this.officeId);

        JsonNode node = OfficeHelper.update((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(OfficeBrowsePage.class);

    }

}
