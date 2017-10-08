package com.angkorteam.fintech.pages.service;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.ExternalServiceBuilder;
import com.angkorteam.fintech.dto.enums.ServiceType;
import com.angkorteam.fintech.helper.ServiceHelper;
import com.angkorteam.fintech.pages.ServiceDashboardPage;
import com.angkorteam.fintech.pages.SystemDashboardPage;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import java.util.List;
import java.util.Map;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class SMSConfigurationPage extends Page {

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

    private String endpointValue;
    private TextField<String> endpointField;
    private TextFeedbackPanel endpointFeedback;

    private String tenantAppKeyValue;
    private TextField<String> tenantAppKeyField;
    private TextFeedbackPanel tenantAppKeyFeedback;

    private String hostValue;
    private TextField<String> hostField;
    private TextFeedbackPanel hostFeedback;

    private int portValue = 9191;
    private TextField<Integer> portField;
    private TextFeedbackPanel portFeedback;

    private static final List<PageBreadcrumb> BREADCRUMB;

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
    protected void onInitialize() {
        super.onInitialize();

        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        List<Map<String, Object>> temps = jdbcTemplate.queryForList("select name, value from c_external_service_properties where external_service_id = ?", ServiceType.SMS.getLiteral());
        Map<String, Object> params = Maps.newHashMap();
        for (Map<String, Object> temp : temps) {
            params.put((String) temp.get("name"), temp.get("value"));
        }

        this.form = new Form<>("form");
        this.add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", ServiceDashboardPage.class);
        this.form.add(this.closeLink);

        this.endpointValue = (String) params.get("end_point");
        this.endpointField = new TextField<>("endpointField", new PropertyModel<>(this, "endpointValue"));
        this.endpointField.setRequired(true);
        this.form.add(this.endpointField);
        this.endpointFeedback = new TextFeedbackPanel("endpointFeedback", this.endpointField);
        this.form.add(this.endpointFeedback);

        this.tenantAppKeyValue = (String) params.get("tenant_app_key");
        this.tenantAppKeyField = new TextField<>("tenantAppKeyField", new PropertyModel<>(this, "tenantAppKeyValue"));
        this.tenantAppKeyField.setRequired(true);
        this.form.add(this.tenantAppKeyField);
        this.tenantAppKeyFeedback = new TextFeedbackPanel("tenantAppKeyFeedback", this.tenantAppKeyField);
        this.form.add(this.tenantAppKeyFeedback);

        this.hostValue = (String) params.get("host_name");
        this.hostField = new TextField<>("hostField", new PropertyModel<>(this, "hostValue"));
        this.hostField.setRequired(true);
        this.form.add(this.hostField);
        this.hostFeedback = new TextFeedbackPanel("hostFeedback", this.hostField);
        this.form.add(this.hostFeedback);

        this.portValue = Integer.valueOf((String) params.get("port_number"));
        this.portField = new TextField<>("portField", new PropertyModel<>(this, "portValue"));
        this.portField.setRequired(true);
        this.form.add(this.portField);
        this.portFeedback = new TextFeedbackPanel("portFeedback", this.portField);
        this.form.add(this.portFeedback);
    }

    private void saveButtonSubmit(Button button) {
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
