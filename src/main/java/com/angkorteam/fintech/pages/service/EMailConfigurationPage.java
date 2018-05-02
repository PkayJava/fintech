package com.angkorteam.fintech.pages.service;

import java.util.List;
import java.util.Map;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.CExternalServiceProperties;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.ExternalServiceBuilder;
import com.angkorteam.fintech.dto.enums.ServiceType;
import com.angkorteam.fintech.helper.ServiceHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.ServiceDashboardPage;
import com.angkorteam.fintech.pages.SystemDashboardPage;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.github.openunirest.http.JsonNode;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class EMailConfigurationPage extends Page {

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected UIRow row1;

    protected UIBlock usernameBlock;
    protected UIContainer usernameIContainer;
    protected String usernameValue;
    protected TextField<String> usernameField;

    protected UIRow row2;

    protected UIBlock passwordBlock;
    protected UIContainer passwordIContainer;
    protected String passwordValue;
    protected TextField<String> passwordField;

    protected UIRow row3;

    protected UIBlock hostBlock;
    protected UIContainer hostIContainer;
    protected String hostValue;
    protected TextField<String> hostField;

    protected UIRow row4;

    protected UIBlock portBlock;
    protected UIContainer portIContainer;
    protected Long portValue = 25l;
    protected TextField<Long> portField;

    protected UIRow row5;

    protected UIBlock useTlsBlock;
    protected UIContainer useTlsIContainer;
    protected Boolean useTlsValue;
    protected CheckBox useTlsField;

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

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.usernameBlock = this.row1.newUIBlock("usernameBlock", Size.Twelve_12);
        this.usernameIContainer = this.usernameBlock.newUIContainer("usernameIContainer");
        this.usernameField = new TextField<>("usernameField", new PropertyModel<>(this, "usernameValue"));
        this.usernameIContainer.add(this.usernameField);
        this.usernameIContainer.newFeedback("usernameFeedback", this.usernameField);

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.passwordBlock = this.row2.newUIBlock("passwordBlock", Size.Twelve_12);
        this.passwordIContainer = this.passwordBlock.newUIContainer("passwordIContainer");
        this.passwordField = new TextField<>("passwordField", new PropertyModel<>(this, "passwordValue"));
        this.passwordIContainer.add(this.passwordField);
        this.passwordIContainer.newFeedback("passwordFeedback", this.passwordField);

        this.row3 = UIRow.newUIRow("row3", this.form);

        this.hostBlock = this.row3.newUIBlock("hostBlock", Size.Twelve_12);
        this.hostIContainer = this.hostBlock.newUIContainer("hostIContainer");
        this.hostField = new TextField<>("hostField", new PropertyModel<>(this, "hostValue"));
        this.hostIContainer.add(this.hostField);
        this.hostIContainer.newFeedback("hostFeedback", this.hostField);

        this.row4 = UIRow.newUIRow("row4", this.form);

        this.portBlock = this.row4.newUIBlock("portBlock", Size.Twelve_12);
        this.portIContainer = this.portBlock.newUIContainer("portIContainer");
        this.portField = new TextField<>("portField", new PropertyModel<>(this, "portValue"));
        this.portIContainer.add(this.portField);
        this.portIContainer.newFeedback("portFeedback", this.portField);

        this.row5 = UIRow.newUIRow("row5", this.form);

        this.useTlsBlock = this.row5.newUIBlock("useTlsBlock", Size.Twelve_12);
        this.useTlsIContainer = this.useTlsBlock.newUIContainer("useTlsIContainer");
        this.useTlsField = new CheckBox("useTlsField", new PropertyModel<>(this, "useTlsValue"));
        this.useTlsIContainer.add(this.useTlsField);
        this.useTlsIContainer.newFeedback("useTlsFeedback", this.useTlsField);
    }

    @Override
    protected void configureMetaData() {
        this.useTlsField.setRequired(true);
        this.portField.setRequired(true);
        this.hostField.setRequired(true);
        this.passwordField.setRequired(true);
        this.usernameField.setRequired(true);
    }

    protected void saveButtonSubmit(Button button) {
        ExternalServiceBuilder builder = new ExternalServiceBuilder(ServiceType.SMTP);
        builder.withHost(this.hostValue);
        builder.withPassword(this.passwordValue);
        builder.withPort(this.portValue);
        builder.withUsername(this.usernameValue);
        builder.withUseTls(this.useTlsValue);

        JsonNode node = ServiceHelper.update((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(ServiceDashboardPage.class);
    }

}
