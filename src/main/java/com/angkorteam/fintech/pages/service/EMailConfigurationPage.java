package com.angkorteam.fintech.pages.service;

import java.util.List;
import java.util.Map;

import com.angkorteam.fintech.ddl.CExternalService;
import com.angkorteam.fintech.ddl.CExternalServiceProperties;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcNamed;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.ExternalServiceBuilder;
import com.angkorteam.fintech.dto.enums.ServiceType;
import com.angkorteam.fintech.helper.ServiceHelper;
import com.angkorteam.fintech.pages.ServiceDashboardPage;
import com.angkorteam.fintech.pages.SystemDashboardPage;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class EMailConfigurationPage extends Page {

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock usernameBlock;
    protected WebMarkupContainer usernameIContainer;
    protected String usernameValue;
    protected TextField<String> usernameField;
    protected TextFeedbackPanel usernameFeedback;

    protected WebMarkupBlock passwordBlock;
    protected WebMarkupContainer passwordIContainer;
    protected String passwordValue;
    protected TextField<String> passwordField;
    protected TextFeedbackPanel passwordFeedback;

    protected WebMarkupBlock hostBlock;
    protected WebMarkupContainer hostIContainer;
    protected String hostValue;
    protected TextField<String> hostField;
    protected TextFeedbackPanel hostFeedback;

    protected WebMarkupBlock portBlock;
    protected WebMarkupContainer portIContainer;
    protected Long portValue = 25l;
    protected TextField<Long> portField;
    protected TextFeedbackPanel portFeedback;

    protected WebMarkupBlock useTlsBlock;
    protected WebMarkupContainer useTlsIContainer;
    protected Boolean useTlsValue;
    protected CheckBox useTlsField;
    protected TextFeedbackPanel useTlsFeedback;

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
            breadcrumb.setLabel("System");
            breadcrumb.setPage(SystemDashboardPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("External Service");
            breadcrumb.setPage(ServiceDashboardPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("SMTP");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initData() {
        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery(CExternalServiceProperties.NAME);
        selectQuery.addField(CExternalServiceProperties.Field.NAME);
        selectQuery.addField(CExternalServiceProperties.Field.VALUE);
        selectQuery.addWhere(CExternalServiceProperties.Field.EXTERNAL_SERVICE_ID + " = :" + CExternalServiceProperties.Field.EXTERNAL_SERVICE_ID, ServiceType.SMTP.getLiteral());

        List<Map<String, Object>> temps = named.queryForList(selectQuery.toSQL(), selectQuery.getParam());
        Map<String, Object> params = Maps.newHashMap();
        for (Map<String, Object> temp : temps) {
            params.put((String) temp.get(CExternalServiceProperties.Field.NAME), temp.get(CExternalServiceProperties.Field.VALUE));
        }

        this.usernameValue = (String) params.get("username");
        this.passwordValue = (String) params.get("password");
        this.hostValue = (String) params.get("host");
        this.portValue = Long.valueOf((String) params.get("port"));
        this.useTlsValue = Boolean.valueOf((String) params.get("useTLS"));
    }

    @Override
    protected void initComponent() {

        this.form = new Form<>("form");
        this.add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", ServiceDashboardPage.class);
        this.form.add(this.closeLink);

        initUsernameBlock();

        initPasswordBlock();

        initHostBlock();

        initPortBlock();

        initUseTlsBlock();
    }

    protected void initUseTlsBlock() {
        this.useTlsBlock = new WebMarkupBlock("useTlsBlock", Size.Twelve_12);
        this.form.add(this.useTlsBlock);
        this.useTlsIContainer = new WebMarkupContainer("useTlsIContainer");
        this.useTlsBlock.add(this.useTlsIContainer);
        this.useTlsField = new CheckBox("useTlsField", new PropertyModel<>(this, "useTlsValue"));
        this.useTlsField.setRequired(true);
        this.useTlsIContainer.add(this.useTlsField);
        this.useTlsFeedback = new TextFeedbackPanel("useTlsFeedback", this.useTlsField);
        this.useTlsIContainer.add(this.useTlsFeedback);
    }

    protected void initPortBlock() {
        this.portBlock = new WebMarkupBlock("portBlock", Size.Twelve_12);
        this.form.add(this.portBlock);
        this.portIContainer = new WebMarkupContainer("portIContainer");
        this.portBlock.add(this.portIContainer);
        this.portField = new TextField<>("portField", new PropertyModel<>(this, "portValue"));
        this.portField.setRequired(true);
        this.portIContainer.add(this.portField);
        this.portFeedback = new TextFeedbackPanel("portFeedback", this.portField);
        this.portIContainer.add(this.portFeedback);
    }

    protected void initHostBlock() {
        this.hostBlock = new WebMarkupBlock("hostBlock", Size.Twelve_12);
        this.form.add(this.hostBlock);
        this.hostIContainer = new WebMarkupContainer("hostIContainer");
        this.hostBlock.add(this.hostIContainer);
        this.hostField = new TextField<>("hostField", new PropertyModel<>(this, "hostValue"));
        this.hostField.setRequired(true);
        this.hostIContainer.add(this.hostField);
        this.hostFeedback = new TextFeedbackPanel("hostFeedback", this.hostField);
        this.hostIContainer.add(this.hostFeedback);
    }

    protected void initPasswordBlock() {
        this.passwordBlock = new WebMarkupBlock("passwordBlock", Size.Twelve_12);
        this.form.add(this.passwordBlock);
        this.passwordIContainer = new WebMarkupContainer("passwordIContainer");
        this.passwordBlock.add(this.passwordIContainer);
        this.passwordField = new TextField<>("passwordField", new PropertyModel<>(this, "passwordValue"));
        this.passwordField.setRequired(true);
        this.passwordIContainer.add(this.passwordField);
        this.passwordFeedback = new TextFeedbackPanel("passwordFeedback", this.passwordField);
        this.passwordIContainer.add(this.passwordFeedback);
    }

    protected void initUsernameBlock() {
        this.usernameBlock = new WebMarkupBlock("usernameBlock", Size.Twelve_12);
        this.form.add(this.usernameBlock);
        this.usernameIContainer = new WebMarkupContainer("usernameIContainer");
        this.usernameBlock.add(this.usernameIContainer);
        this.usernameField = new TextField<>("usernameField", new PropertyModel<>(this, "usernameValue"));
        this.usernameField.setRequired(true);
        this.usernameIContainer.add(this.usernameField);
        this.usernameFeedback = new TextFeedbackPanel("usernameFeedback", this.usernameField);
        this.usernameIContainer.add(this.usernameFeedback);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected void saveButtonSubmit(Button button) {
        ExternalServiceBuilder builder = new ExternalServiceBuilder(ServiceType.SMTP);
        builder.withHost(this.hostValue);
        builder.withPassword(this.passwordValue);
        builder.withPort(this.portValue);
        builder.withUsername(this.usernameValue);
        builder.withUseTls(this.useTlsValue);

        JsonNode node = null;
        try {
            node = ServiceHelper.update((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(ServiceDashboardPage.class);
    }

}
