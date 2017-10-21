package com.angkorteam.fintech.pages.office;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.OfficeBuilder;
import com.angkorteam.fintech.helper.OfficeHelper;
import com.angkorteam.fintech.pages.OrganizationDashboardPage;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/25/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class OfficeModifyPage extends Page {

    protected String officeId;

    protected String externalIdValue;
    protected TextField<String> externalIdField;
    protected TextFeedbackPanel externalIdFeedback;

    protected String nameValue;
    protected TextField<String> nameField;
    protected TextFeedbackPanel nameFeedback;

    protected SingleChoiceProvider parentProvider;
    protected Option parentValue;
    protected Select2SingleChoice<Option> parentField;
    protected TextFeedbackPanel parentFeedback;

    protected Date openingDateValue;
    protected DateTextField openingDateField;
    protected TextFeedbackPanel openingDateFeedback;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected static final List<PageBreadcrumb> BREADCRUMB;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        return Model.ofList(BREADCRUMB);
    }

    static {
        BREADCRUMB = Lists.newArrayList();
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
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", OfficeBrowsePage.class);
        this.form.add(this.closeLink);

        this.externalIdField = new TextField<>("externalIdField", new PropertyModel<>(this, "externalIdValue"));
        this.externalIdField.setRequired(true);
        this.form.add(this.externalIdField);
        this.externalIdFeedback = new TextFeedbackPanel("externalIdFeedback", this.externalIdField);
        this.form.add(this.externalIdFeedback);

        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setRequired(true);
        this.form.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.form.add(this.nameFeedback);

        this.openingDateField = new DateTextField("openingDateField", new PropertyModel<>(this, "openingDateValue"));
        this.openingDateField.setRequired(true);
        this.form.add(this.openingDateField);
        this.openingDateFeedback = new TextFeedbackPanel("openingDateFeedback", this.openingDateField);
        this.form.add(this.openingDateFeedback);

        this.parentProvider = new SingleChoiceProvider("m_office", "id", "name");
        this.parentField = new Select2SingleChoice<>("parentField", new PropertyModel<>(this, "parentValue"), this.parentProvider);
        this.parentField.setRequired(true);
        this.form.add(this.parentField);
        this.parentFeedback = new TextFeedbackPanel("parentFeedback", this.parentField);
        this.form.add(this.parentFeedback);
    }

    protected void initData() {
        PageParameters parameters = getPageParameters();
        this.officeId = parameters.get("officeId").toString("");
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        Map<String, Object> object = jdbcTemplate.queryForMap("select * from m_office where id = ?", this.officeId);
        this.externalIdValue = (String) object.get("external_id");
        this.nameValue = (String) object.get("name");
        this.openingDateValue = (Date) object.get("opening_date");
        this.parentValue = jdbcTemplate.queryForObject("select id, name as text from m_office where id = ?", Option.MAPPER, object.get("parent_id"));
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

        JsonNode node = null;
        try {
            node = OfficeHelper.update((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(OfficeBrowsePage.class);

    }

}
