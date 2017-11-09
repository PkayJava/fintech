package com.angkorteam.fintech.pages.service;

import java.util.List;
import java.util.Map;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.DeprecatedPage;
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
public class SMSConfigurationPage extends DeprecatedPage {

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock endpointBlock;
    protected WebMarkupContainer endpointIContainer;
    protected String endpointValue;
    protected TextField<String> endpointField;
    protected TextFeedbackPanel endpointFeedback;

    protected WebMarkupBlock tenantAppKeyBlock;
    protected WebMarkupContainer tenantAppKeyIContainer;
    protected String tenantAppKeyValue;
    protected TextField<String> tenantAppKeyField;
    protected TextFeedbackPanel tenantAppKeyFeedback;

    protected WebMarkupBlock hostBlock;
    protected WebMarkupContainer hostIContainer;
    protected String hostValue;
    protected TextField<String> hostField;
    protected TextFeedbackPanel hostFeedback;

    protected WebMarkupBlock portBlock;
    protected WebMarkupContainer portIContainer;
    protected Integer portValue = 9191;
    protected TextField<Integer> portField;
    protected TextFeedbackPanel portFeedback;

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
            breadcrumb.setLabel("SMS");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void initData() {
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        List<Map<String, Object>> temps = jdbcTemplate.queryForList("select name, value from c_external_service_properties where external_service_id = ?", ServiceType.SMS.getLiteral());
        Map<String, Object> params = Maps.newHashMap();
        for (Map<String, Object> temp : temps) {
            params.put((String) temp.get("name"), temp.get("value"));
        }
        this.endpointValue = (String) params.get("end_point");
        this.tenantAppKeyValue = (String) params.get("tenant_app_key");
        this.hostValue = (String) params.get("host_name");
        this.portValue = Integer.valueOf((String) params.get("port_number"));
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

        initEndpointBlock();

        initTenantAppKeyBlock();

        initHostBlock();

        initPortBlock();
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

    protected void initTenantAppKeyBlock() {
        this.tenantAppKeyBlock = new WebMarkupBlock("tenantAppKeyBlock", Size.Twelve_12);
        this.form.add(this.tenantAppKeyBlock);
        this.tenantAppKeyIContainer = new WebMarkupContainer("tenantAppKeyIContainer");
        this.tenantAppKeyBlock.add(this.tenantAppKeyIContainer);
        this.tenantAppKeyField = new TextField<>("tenantAppKeyField", new PropertyModel<>(this, "tenantAppKeyValue"));
        this.tenantAppKeyField.setRequired(true);
        this.tenantAppKeyIContainer.add(this.tenantAppKeyField);
        this.tenantAppKeyFeedback = new TextFeedbackPanel("tenantAppKeyFeedback", this.tenantAppKeyField);
        this.tenantAppKeyIContainer.add(this.tenantAppKeyFeedback);
    }

    protected void initEndpointBlock() {
        this.endpointBlock = new WebMarkupBlock("endpointBlock", Size.Twelve_12);
        this.form.add(this.endpointBlock);
        this.endpointIContainer = new WebMarkupContainer("endpointIContainer");
        this.endpointBlock.add(this.endpointIContainer);
        this.endpointField = new TextField<>("endpointField", new PropertyModel<>(this, "endpointValue"));
        this.endpointField.setRequired(true);
        this.endpointIContainer.add(this.endpointField);
        this.endpointFeedback = new TextFeedbackPanel("endpointFeedback", this.endpointField);
        this.endpointIContainer.add(this.endpointFeedback);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected void saveButtonSubmit(Button button) {
        ExternalServiceBuilder builder = new ExternalServiceBuilder(ServiceType.SMS);
        builder.withHost(this.hostValue);
        builder.withPort(this.portValue);
        builder.withEndpoint(this.endpointValue);
        builder.withTenantAppKey(this.tenantAppKeyValue);

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
