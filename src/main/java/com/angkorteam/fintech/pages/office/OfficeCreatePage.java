package com.angkorteam.fintech.pages.office;

import java.util.Date;
import java.util.List;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.joda.time.DateTime;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.MOffice;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.OfficeBuilder;
import com.angkorteam.fintech.helper.OfficeHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.OrganizationDashboardPage;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.DateTextField;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;

import io.github.openunirest.http.JsonNode;

/**
 * Created by socheatkhauv on 6/25/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class OfficeCreatePage extends Page {

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected UIRow row1;

    protected UIBlock externalIdBlock;
    protected UIContainer externalIdIContainer;
    protected String externalIdValue;
    protected TextField<String> externalIdField;

    protected UIRow row2;

    protected UIBlock parentBlock;
    protected UIContainer parentIContainer;
    protected SingleChoiceProvider parentProvider;
    protected Option parentValue;
    protected Select2SingleChoice<Option> parentField;

    protected UIRow row3;

    protected UIBlock nameBlock;
    protected UIContainer nameIContainer;
    protected String nameValue;
    protected TextField<String> nameField;

    protected UIRow row4;

    protected UIBlock openingDateBlock;
    protected UIContainer openingDateIContainer;
    protected Date openingDateValue;
    protected DateTextField openingDateField;

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
            breadcrumb.setLabel("Office Create");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initData() {
        StringGenerator generator = SpringBean.getBean(StringGenerator.class);
        this.externalIdValue = generator.externalId();
        this.openingDateValue = DateTime.now().toDate();
        this.parentProvider = new SingleChoiceProvider(MOffice.NAME, MOffice.Field.ID, MOffice.Field.NAME);
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

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.externalIdBlock = this.row1.newUIBlock("externalIdBlock", Size.Twelve_12);
        this.externalIdIContainer = this.externalIdBlock.newUIContainer("externalIdIContainer");
        this.externalIdField = new TextField<>("externalIdField", new PropertyModel<>(this, "externalIdValue"));
        this.externalIdIContainer.add(this.externalIdField);
        this.externalIdIContainer.newFeedback("externalIdFeedback", this.externalIdField);

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.parentBlock = this.row2.newUIBlock("parentBlock", Size.Twelve_12);
        this.parentIContainer = this.parentBlock.newUIContainer("parentIContainer");
        this.parentField = new Select2SingleChoice<>("parentField", new PropertyModel<>(this, "parentValue"), this.parentProvider);
        this.parentIContainer.add(this.parentField);
        this.parentIContainer.newFeedback("parentFeedback", this.parentField);

        this.row3 = UIRow.newUIRow("row3", this.form);

        this.nameBlock = this.row3.newUIBlock("nameBlock", Size.Twelve_12);
        this.nameIContainer = this.nameBlock.newUIContainer("nameIContainer");
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameIContainer.add(this.nameField);
        this.nameIContainer.newFeedback("nameFeedback", this.nameField);

        this.row4 = UIRow.newUIRow("row4", this.form);

        this.openingDateBlock = this.row4.newUIBlock("openingDateBlock", Size.Twelve_12);
        this.openingDateIContainer = this.openingDateBlock.newUIContainer("openingDateIContainer");
        this.openingDateField = new DateTextField("openingDateField", new PropertyModel<>(this, "openingDateValue"));
        this.openingDateIContainer.add(this.openingDateField);
        this.openingDateIContainer.newFeedback("openingDateFeedback", this.openingDateField);

    }

    @Override
    protected void configureMetaData() {
        this.nameField.setRequired(true);
        this.parentField.setRequired(true);
        this.openingDateField.setRequired(true);
        this.externalIdField.setRequired(true);
    }

    protected void saveButtonSubmit(Button button) {
        OfficeBuilder builder = new OfficeBuilder();
        builder.withName(this.nameValue);
        if (this.parentValue != null) {
            builder.withParentId(this.parentValue.getId());
        }
        builder.withOpeningDate(this.openingDateValue);
        builder.withExternalId(this.externalIdValue);

        JsonNode node = OfficeHelper.create((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(OfficeBrowsePage.class);
    }

}
