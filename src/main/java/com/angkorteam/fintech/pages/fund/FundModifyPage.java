package com.angkorteam.fintech.pages.fund;

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
import com.angkorteam.fintech.ddl.MFund;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.FundBuilder;
import com.angkorteam.fintech.helper.FundHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.OrganizationDashboardPage;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.google.common.collect.Lists;

import io.github.openunirest.http.JsonNode;

/**
 * Created by socheatkhauv on 6/26/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class FundModifyPage extends Page {

    protected String fundId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected UIRow row1;

    protected UIBlock externalIdBlock;
    protected UIContainer externalIdIContainer;
    protected String externalIdValue;
    protected TextField<String> externalIdField;

    protected UIBlock row1Block1;

    protected UIRow row2;

    protected UIBlock nameBlock;
    protected UIContainer nameIContainer;
    protected String nameValue;
    protected TextField<String> nameField;

    protected UIBlock row2Block1;

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
            breadcrumb.setLabel("Fund");
            breadcrumb.setPage(FundBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Fund Modify");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initData() {
        PageParameters parameters = getPageParameters();
        this.fundId = parameters.get("fundId").toString("");

        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery(MFund.NAME);
        selectQuery.addField(MFund.Field.EXTERNAL_ID);
        selectQuery.addField(MFund.Field.NAME);
        selectQuery.addWhere(MFund.Field.ID + " = :" + MFund.Field.ID, this.fundId);

        Map<String, Object> fundObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
        this.externalIdValue = (String) fundObject.get(MFund.Field.EXTERNAL_ID);
        this.nameValue = (String) fundObject.get(MFund.Field.NAME);
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", FundBrowsePage.class);
        this.form.add(this.closeLink);

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.externalIdBlock = this.row1.newUIBlock("externalIdBlock", Size.Six_6);
        this.externalIdIContainer = this.externalIdBlock.newUIContainer("externalIdIContainer");
        this.externalIdField = new TextField<>("externalIdField", new PropertyModel<>(this, "externalIdValue"));
        this.externalIdIContainer.add(this.externalIdField);
        this.externalIdIContainer.newFeedback("externalIdFeedback", this.externalIdField);

        this.row1Block1 = this.row1.newUIBlock("row1Block1", Size.Six_6);

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.nameBlock = this.row2.newUIBlock("nameBlock", Size.Six_6);
        this.nameIContainer = this.nameBlock.newUIContainer("nameIContainer");
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameIContainer.add(this.nameField);
        this.nameIContainer.newFeedback("nameFeedback", this.nameField);

        this.row2Block1 = this.row2.newUIBlock("row2Block1", Size.Six_6);
    }

    @Override
    protected void configureMetaData() {
        this.externalIdField.setRequired(true);
        this.nameField.setRequired(true);
    }

    protected void saveButtonSubmit(Button button) {
        FundBuilder builder = new FundBuilder();
        builder.withName(this.nameValue);
        builder.withExternalId(this.externalIdValue);
        builder.withId(this.fundId);

        JsonNode node = FundHelper.update((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(FundBrowsePage.class);
    }
}
