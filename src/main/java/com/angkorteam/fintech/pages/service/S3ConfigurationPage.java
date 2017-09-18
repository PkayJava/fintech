package com.angkorteam.fintech.pages.service;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.ServiceType;
import com.angkorteam.fintech.dto.request.ExternalServiceBuilder;
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
public class S3ConfigurationPage extends Page {

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

    private String s3AccessKeyValue;
    private TextField<String> s3AccessKeyField;
    private TextFeedbackPanel s3AccessKeyFeedback;

    private String s3BucketNameValue;
    private TextField<String> s3BucketNameField;
    private TextFeedbackPanel s3BucketNameFeedback;

    private String s3SecretKeyValue;
    private TextField<String> s3SecretKeyField;
    private TextFeedbackPanel s3SecretKeyFeedback;

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
            breadcrumb.setLabel("Amazon S3");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        List<Map<String, Object>> temps = jdbcTemplate.queryForList("select name, value from c_external_service_properties where external_service_id = ?", ServiceType.S3.getLiteral());
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

        this.s3AccessKeyValue = (String) params.get("s3_access_key");
        this.s3AccessKeyField = new TextField<>("s3AccessKeyField", new PropertyModel<>(this, "s3AccessKeyValue"));
        this.s3AccessKeyField.setRequired(true);
        this.form.add(this.s3AccessKeyField);
        this.s3AccessKeyFeedback = new TextFeedbackPanel("s3AccessKeyFeedback", this.s3AccessKeyField);
        this.form.add(this.s3AccessKeyFeedback);

        this.s3BucketNameValue = (String) params.get("s3_bucket_name");
        this.s3BucketNameField = new TextField<>("s3BucketNameField", new PropertyModel<>(this, "s3BucketNameValue"));
        this.s3BucketNameField.setRequired(true);
        this.form.add(this.s3BucketNameField);
        this.s3BucketNameFeedback = new TextFeedbackPanel("s3BucketNameFeedback", this.s3BucketNameField);
        this.form.add(this.s3BucketNameFeedback);

        this.s3SecretKeyValue = (String) params.get("s3_secret_key");
        this.s3SecretKeyField = new TextField<>("s3SecretKeyField", new PropertyModel<>(this, "s3SecretKeyValue"));
        this.s3SecretKeyField.setRequired(true);
        this.form.add(this.s3SecretKeyField);
        this.s3SecretKeyFeedback = new TextFeedbackPanel("s3SecretKeyFeedback", this.s3SecretKeyField);
        this.form.add(this.s3SecretKeyFeedback);
    }

    private void saveButtonSubmit(Button button) {
        ExternalServiceBuilder builder = new ExternalServiceBuilder(ServiceType.S3);
        builder.withS3AccessKey(this.s3AccessKeyValue);
        builder.withS3BucketName(this.s3BucketNameValue);
        builder.withS3SecretKey(this.s3SecretKeyValue);

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
