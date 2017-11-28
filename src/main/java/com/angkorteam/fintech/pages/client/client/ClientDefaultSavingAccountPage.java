package com.angkorteam.fintech.pages.client.client;

import java.util.Map;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
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
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ClientDefaultSavingAccountPage extends Page {

    protected String clientId;

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
        this.savingProvider = new SingleChoiceProvider("m_savings_account", "m_savings_account.id", "m_savings_account.account_no", "concat(m_savings_account.account_no, ' => ', m_savings_product.name)");
        this.savingProvider.applyJoin("m_savings_product", "INNER JOIN m_savings_product ON m_savings_account.product_id = m_savings_product.id");
        this.savingProvider.applyWhere("status_enum", "m_savings_account.status_enum = 300");
        this.savingProvider.applyWhere("client_id", "m_savings_account.client_id = " + this.clientId);
        this.savingField = new Select2SingleChoice<>("savingField", new PropertyModel<>(this, "savingValue"), this.savingProvider);
        this.savingField.setRequired(true);
        this.savingField.setLabel(Model.of("Saving Account"));
        this.savingIContainer.add(this.savingField);
        this.savingFeedback = new TextFeedbackPanel("savingFeedback", this.savingField);
        this.savingIContainer.add(this.savingFeedback);
    }

    @Override
    protected void initData() {
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        this.clientId = getPageParameters().get("clientId").toString();
        Map<String, Object> clientObject = jdbcTemplate.queryForMap("select * from m_client where id = ?", this.clientId);
        this.savingValue = jdbcTemplate.queryForObject("select m_savings_account.id id, concat(m_savings_account.account_no, ' => ', m_savings_product.name) text from m_savings_account INNER JOIN m_savings_product ON m_savings_account.product_id = m_savings_product.id where m_savings_account.id = ?", Option.MAPPER, clientObject.get("default_savings_account"));
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
