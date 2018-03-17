package com.angkorteam.fintech.pages.client.client;

import java.util.List;
import java.util.Map;

import com.angkorteam.fintech.ddl.MClient;
import com.angkorteam.fintech.ddl.MSavingsAccount;
import com.angkorteam.fintech.ddl.MSavingsProduct;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcNamed;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.client.client.ClientDefaulSavingAccountBuilder;
import com.angkorteam.fintech.helper.ClientHelper;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ClientDefaultSavingAccountPage extends Page {

    protected String clientId;

    protected String clientDisplayName;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock savingBlock;
    protected WebMarkupContainer savingIContainer;
    protected SingleChoiceProvider savingProvider;
    protected Option savingValue;
    protected Select2SingleChoice<Option> savingField;
    protected TextFeedbackPanel savingFeedback;

    @Override
    protected void initComponent() {
        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", ClientPreviewPage.class, parameters);
        this.form.add(this.closeLink);

        initSavingBlock();
    }

    @Override
    protected void configureRequiredValidation() {

    }

    @Override
    protected void configureMetaData() {

    }

    protected void initSavingBlock() {
        this.savingBlock = new WebMarkupBlock("savingBlock", Size.Six_6);
        this.form.add(this.savingBlock);
        this.savingIContainer = new WebMarkupContainer("savingIContainer");
        this.savingBlock.add(this.savingIContainer);
        this.savingProvider = new SingleChoiceProvider(MSavingsAccount.NAME, MSavingsAccount.NAME + "." + MSavingsAccount.Field.ID, MSavingsAccount.NAME + "." + MSavingsAccount.Field.ACCOUNT_NO, "CONCAT(" + MSavingsAccount.NAME + "." + MSavingsAccount.Field.ACCOUNT_NO + ", ' => ', " + MSavingsProduct.NAME + "." + MSavingsProduct.Field.NAME + ")");
        this.savingProvider.applyJoin("m_savings_product", "INNER JOIN " + MSavingsProduct.NAME + " ON " + MSavingsAccount.NAME + "." + MSavingsAccount.Field.PRODUCT_ID + " = " + MSavingsProduct.NAME + "." + MSavingsProduct.Field.ID);
        this.savingProvider.applyWhere("status_enum", MSavingsAccount.NAME + "." + MSavingsAccount.Field.STATUS_ENUM + " = 300");
        this.savingProvider.applyWhere("client_id", MSavingsAccount.NAME + "." + MSavingsAccount.Field.CLIENT_ID + " = " + this.clientId);
        this.savingField = new Select2SingleChoice<>("savingField", new PropertyModel<>(this, "savingValue"), this.savingProvider);
        this.savingField.setRequired(true);
        this.savingField.setLabel(Model.of("Saving Account"));
        this.savingIContainer.add(this.savingField);
        this.savingFeedback = new TextFeedbackPanel("savingFeedback", this.savingField);
        this.savingIContainer.add(this.savingFeedback);
    }

    @Override
    protected void initData() {
        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
        this.clientId = getPageParameters().get("clientId").toString();

        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery(MClient.NAME);
        selectQuery.addWhere(MClient.Field.ID + " = :" + MClient.Field.ID, this.clientId);
        selectQuery.addField(MClient.Field.DEFAULT_SAVINGS_ACCOUNT);
        selectQuery.addField(MClient.Field.DISPLAY_NAME);
        Map<String, Object> clientObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        selectQuery = new SelectQuery(MSavingsAccount.NAME);
        selectQuery.addField(MSavingsAccount.NAME + "." + MSavingsAccount.Field.ID + " as id");
        selectQuery.addField("CONCAT(" + MSavingsAccount.NAME + "." + MSavingsAccount.Field.ACCOUNT_NO + ", ' => ', " + MSavingsProduct.NAME + "." + MSavingsProduct.Field.NAME + ") as text");
        selectQuery.addJoin("INNER JOIN " + MSavingsProduct.NAME + " ON " + MSavingsAccount.NAME + "." + MSavingsAccount.Field.PRODUCT_ID + " = " + MSavingsProduct.NAME + "." + MSavingsProduct.Field.ID);
        this.savingValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
        this.clientDisplayName = (String) clientObject.get("display_name");
    }

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Clients");
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Clients");
            breadcrumb.setPage(ClientBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageParameters parameters = new PageParameters();
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            parameters.add("clientId", this.clientId);
            breadcrumb.setLabel(this.clientDisplayName);
            breadcrumb.setPage(ClientPreviewPage.class);
            breadcrumb.setParameters(parameters);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Default Saving Account");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    protected void saveButtonSubmit(Button button) {
        ClientDefaulSavingAccountBuilder builder = new ClientDefaulSavingAccountBuilder();
        builder.withId(this.clientId);
        if (this.savingValue != null) {
            builder.withSavingsAccountId(this.savingValue.getId());
        }

        JsonNode node = null;
        try {
            node = ClientHelper.defaultSavingAccountClient((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);
        setResponsePage(ClientPreviewPage.class, parameters);
    }
}
