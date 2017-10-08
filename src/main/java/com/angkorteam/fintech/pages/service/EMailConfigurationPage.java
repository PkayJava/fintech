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
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import java.util.List;
import java.util.Map;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class EMailConfigurationPage extends Page {

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

    private String usernameValue;
    private TextField<String> usernameField;
    private TextFeedbackPanel usernameFeedback;

    private String passwordValue;
    private TextField<String> passwordField;
    private TextFeedbackPanel passwordFeedback;

    private String hostValue;
    private TextField<String> hostField;
    private TextFeedbackPanel hostFeedback;

    private int portValue = 25;
    private TextField<Integer> portField;
    private TextFeedbackPanel portFeedback;

    private Boolean useTlsValue;
    private CheckBox useTlsField;
    private TextFeedbackPanel useTlsFeedback;

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
            breadcrumb.setLabel("SMTP");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        List<Map<String, Object>> temps = jdbcTemplate.queryForList("select name, value from c_external_service_properties where external_service_id = ?", ServiceType.SMTP.getLiteral());
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

        this.usernameValue = (String) params.get("username");
        this.usernameField = new TextField<>("usernameField", new PropertyModel<>(this, "usernameValue"));
        this.usernameField.setRequired(true);
        this.form.add(this.usernameField);
        this.usernameFeedback = new TextFeedbackPanel("usernameFeedback", this.usernameField);
        this.form.add(this.usernameFeedback);

        this.passwordValue = (String) params.get("password");
        this.passwordField = new TextField<>("passwordField", new PropertyModel<>(this, "passwordValue"));
        this.passwordField.setRequired(true);
        this.form.add(this.passwordField);
        this.passwordFeedback = new TextFeedbackPanel("passwordFeedback", this.passwordField);
        this.form.add(this.passwordFeedback);

        this.hostValue = (String) params.get("host");
        this.hostField = new TextField<>("hostField", new PropertyModel<>(this, "hostValue"));
        this.hostField.setRequired(true);
        this.form.add(this.hostField);
        this.hostFeedback = new TextFeedbackPanel("hostFeedback", this.hostField);
        this.form.add(this.hostFeedback);

        this.portValue = Integer.valueOf((String) params.get("port"));
        this.portField = new TextField<>("portField", new PropertyModel<>(this, "portValue"));
        this.portField.setRequired(true);
        this.form.add(this.portField);
        this.portFeedback = new TextFeedbackPanel("portFeedback", this.portField);
        this.form.add(this.portFeedback);

        this.useTlsValue = Boolean.valueOf((String) params.get("useTLS"));
        this.useTlsField = new CheckBox("useTlsField", new PropertyModel<>(this, "useTlsValue"));
        this.useTlsField.setRequired(true);
        this.form.add(this.useTlsField);
        this.useTlsFeedback = new TextFeedbackPanel("useTlsFeedback", this.useTlsField);
        this.form.add(this.useTlsFeedback);
    }

    private void saveButtonSubmit(Button button) {
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
