package com.angkorteam.fintech.pages.teller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import com.angkorteam.fintech.widget.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.MOffice;
import com.angkorteam.fintech.ddl.MTellers;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.TellerBuilder;
import com.angkorteam.fintech.dto.constant.TellerStatus;
import com.angkorteam.fintech.helper.TellerHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.pages.OrganizationDashboardPage;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.provider.TellerStateProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
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
import io.github.openunirest.http.JsonNode;

/**
 * Created by socheatkhauv on 7/13/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class TellerModifyPage extends Page {

    protected String tellerId;

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

    protected WebMarkupBlock statusBlock;
    protected WebMarkupContainer statusIContainer;
    protected TellerStateProvider statusProvider;
    protected Option statusValue;
    protected Select2SingleChoice<Option> statusField;
    protected TextFeedbackPanel statusFeedback;

    protected WebMarkupBlock startDateBlock;
    protected WebMarkupContainer startDateIContainer;
    protected Date startDateValue;
    protected DateTextField startDateField;
    protected TextFeedbackPanel startDateFeedback;

    protected WebMarkupBlock endDateBlock;
    protected WebMarkupContainer endDateIContainer;
    protected Date endDateValue;
    protected DateTextField endDateField;
    protected TextFeedbackPanel endDateFeedback;

    protected WebMarkupBlock descriptionBlock;
    protected WebMarkupContainer descriptionIContainer;
    protected String descriptionValue;
    protected TextArea<String> descriptionField;
    protected TextFeedbackPanel descriptionFeedback;

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
            breadcrumb.setLabel("Teller");
            breadcrumb.setPage(TellerBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Teller Modify");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initData() {
        PageParameters parameters = getPageParameters();
        this.tellerId = parameters.get("tellerId").toString("");

        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery(MTellers.NAME);
        selectQuery.addWhere(MTellers.Field.ID + " = :" + MTellers.Field.ID, this.tellerId);
        selectQuery.addField(MTellers.Field.OFFICE_ID);
        selectQuery.addField(MTellers.Field.STATE);
        selectQuery.addField(MTellers.Field.NAME);
        selectQuery.addField(MTellers.Field.VALID_FROM);
        selectQuery.addField(MTellers.Field.VALID_TO);
        selectQuery.addField(MTellers.Field.DESCRIPTION);
        Map<String, Object> tellerObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        selectQuery = new SelectQuery(MOffice.NAME);
        selectQuery.addField(MOffice.Field.ID);
        selectQuery.addField(MOffice.Field.NAME + " as text");
        selectQuery.addWhere(MOffice.Field.ID + " = :" + MOffice.Field.ID, tellerObject.get(MTellers.Field.OFFICE_ID));
        this.officeValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);

        this.statusValue = TellerStatus.optionLiteral(String.valueOf(tellerObject.get(MTellers.Field.STATE)));
        this.nameValue = (String) tellerObject.get(MTellers.Field.NAME);
        this.startDateValue = (Date) tellerObject.get(MTellers.Field.VALID_FROM);
        this.endDateValue = (Date) tellerObject.get(MTellers.Field.VALID_TO);
        this.descriptionValue = (String) tellerObject.get(MTellers.Field.DESCRIPTION);
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", TellerBrowsePage.class);
        this.form.add(this.closeLink);

        initOfficeBlock();

        initStatusBlock();

        initNameBlock();

        initStartDateBlock();

        initEndDateBlock();

        initDescriptionBlock();
    }

    @Override
    protected void configureMetaData() {
    }

    protected void initDescriptionBlock() {
        this.descriptionBlock = new WebMarkupBlock("descriptionBlock", Size.Twelve_12);
        this.form.add(this.descriptionBlock);
        this.descriptionIContainer = new WebMarkupContainer("descriptionIContainer");
        this.descriptionBlock.add(this.descriptionIContainer);
        this.descriptionField = new TextArea<>("descriptionField", new PropertyModel<>(this, "descriptionValue"));
        this.descriptionField.setRequired(true);
        this.descriptionIContainer.add(this.descriptionField);
        this.descriptionFeedback = new TextFeedbackPanel("descriptionFeedback", this.descriptionField);
        this.descriptionIContainer.add(this.descriptionFeedback);
    }

    protected void initEndDateBlock() {
        this.endDateBlock = new WebMarkupBlock("endDateBlock", Size.Twelve_12);
        this.form.add(this.endDateBlock);
        this.endDateIContainer = new WebMarkupContainer("endDateIContainer");
        this.endDateBlock.add(this.endDateIContainer);
        this.endDateField = new DateTextField("endDateField", new PropertyModel<>(this, "endDateValue"));
        this.endDateField.setRequired(true);
        this.endDateIContainer.add(this.endDateField);
        this.endDateFeedback = new TextFeedbackPanel("endDateFeedback", this.endDateField);
        this.endDateIContainer.add(this.endDateFeedback);
    }

    protected void initStartDateBlock() {
        this.startDateBlock = new WebMarkupBlock("startDateBlock", Size.Twelve_12);
        this.form.add(this.startDateBlock);
        this.startDateIContainer = new WebMarkupContainer("startDateIContainer");
        this.startDateBlock.add(this.startDateIContainer);
        this.startDateField = new DateTextField("startDateField", new PropertyModel<>(this, "startDateValue"));
        this.startDateField.setRequired(true);
        this.startDateIContainer.add(this.startDateField);
        this.startDateFeedback = new TextFeedbackPanel("startDateFeedback", this.startDateField);
        this.startDateIContainer.add(this.startDateFeedback);
    }

    protected void initNameBlock() {
        this.nameBlock = new WebMarkupBlock("nameBlock", Size.Twelve_12);
        this.form.add(this.nameBlock);
        this.nameIContainer = new WebMarkupContainer("nameIContainer");
        this.nameBlock.add(this.nameIContainer);
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setRequired(true);
        this.nameIContainer.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.nameIContainer.add(this.nameFeedback);
    }

    protected void initStatusBlock() {
        this.statusBlock = new WebMarkupBlock("statusBlock", Size.Twelve_12);
        this.form.add(this.statusBlock);
        this.statusIContainer = new WebMarkupContainer("statusIContainer");
        this.statusBlock.add(this.statusIContainer);
        this.statusProvider = new TellerStateProvider();
        this.statusField = new Select2SingleChoice<>("statusField", new PropertyModel<>(this, "statusValue"), this.statusProvider);
        this.statusIContainer.add(this.statusField);
        this.statusFeedback = new TextFeedbackPanel("statusFeedback", this.statusField);
        this.statusIContainer.add(this.statusFeedback);
    }

    protected void initOfficeBlock() {
        this.officeBlock = new WebMarkupBlock("officeBlock", Size.Twelve_12);
        this.form.add(this.officeBlock);
        this.officeIContainer = new WebMarkupContainer("officeIContainer");
        this.officeBlock.add(this.officeIContainer);
        this.officeProvider = new SingleChoiceProvider(MOffice.NAME, MOffice.Field.ID, MOffice.Field.NAME);
        this.officeField = new Select2SingleChoice<>("officeField", new PropertyModel<>(this, "officeValue"), this.officeProvider);
        this.officeField.setRequired(true);
        this.officeIContainer.add(this.officeField);
        this.officeFeedback = new TextFeedbackPanel("officeFeedback", this.officeField);
        this.officeIContainer.add(this.officeFeedback);
    }

    protected void saveButtonSubmit(Button button) {
        TellerBuilder builder = new TellerBuilder();
        builder.withId(this.tellerId);
        builder.withDescription(this.descriptionValue);
        builder.withName(this.nameValue);
        builder.withEndDate(this.endDateValue);
        builder.withStartDate(this.startDateValue);
        builder.withStatus(TellerStatus.valueOf(this.statusValue.getId()));
        if (this.officeValue != null) {
            builder.withOfficeId(this.officeValue.getId());
        }

        JsonNode node = TellerHelper.update((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(TellerBrowsePage.class);

    }

}
