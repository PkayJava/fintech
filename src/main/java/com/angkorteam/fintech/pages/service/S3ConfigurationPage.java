package com.angkorteam.fintech.pages.service;

import java.util.List;
import java.util.Map;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import com.angkorteam.fintech.widget.WebMarkupContainer;
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
import com.angkorteam.fintech.pages.ServiceDashboardPage;
import com.angkorteam.fintech.pages.SystemDashboardPage;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class S3ConfigurationPage extends Page {

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock s3AccessKeyBlock;
    protected WebMarkupContainer s3AccessKeyIContainer;
    protected String s3AccessKeyValue;
    protected TextField<String> s3AccessKeyField;
    protected TextFeedbackPanel s3AccessKeyFeedback;

    protected WebMarkupBlock s3BucketNameBlock;
    protected WebMarkupContainer s3BucketNameIContainer;
    protected String s3BucketNameValue;
    protected TextField<String> s3BucketNameField;
    protected TextFeedbackPanel s3BucketNameFeedback;

    protected WebMarkupBlock s3SecretKeyBlock;
    protected WebMarkupContainer s3SecretKeyIContainer;
    protected String s3SecretKeyValue;
    protected TextField<String> s3SecretKeyField;
    protected TextFeedbackPanel s3SecretKeyFeedback;

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
            breadcrumb.setLabel("Amazon S3");
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
        selectQuery.addWhere(CExternalServiceProperties.Field.EXTERNAL_SERVICE_ID + " = :" + CExternalServiceProperties.Field.EXTERNAL_SERVICE_ID, ServiceType.S3.getLiteral());

        List<Map<String, Object>> temps = named.queryForList(selectQuery.toSQL(), selectQuery.getParam());
        Map<String, Object> params = Maps.newHashMap();
        for (Map<String, Object> temp : temps) {
            params.put((String) temp.get(CExternalServiceProperties.Field.NAME), temp.get(CExternalServiceProperties.Field.VALUE));
        }

        this.s3AccessKeyValue = (String) params.get("s3_access_key");
        this.s3BucketNameValue = (String) params.get("s3_bucket_name");
        this.s3SecretKeyValue = (String) params.get("s3_secret_key");

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

        initS3AccessKeyBlock();

        initS3BucketNameBlock();

        initS3SecretKeyBlock();
    }

    protected void initS3SecretKeyBlock() {
        this.s3SecretKeyBlock = new WebMarkupBlock("s3SecretKeyBlock", Size.Twelve_12);
        this.form.add(this.s3SecretKeyBlock);
        this.s3SecretKeyIContainer = new WebMarkupContainer("s3SecretKeyIContainer");
        this.s3SecretKeyBlock.add(this.s3SecretKeyIContainer);
        this.s3SecretKeyField = new TextField<>("s3SecretKeyField", new PropertyModel<>(this, "s3SecretKeyValue"));
        this.s3SecretKeyField.setRequired(true);
        this.s3SecretKeyIContainer.add(this.s3SecretKeyField);
        this.s3SecretKeyFeedback = new TextFeedbackPanel("s3SecretKeyFeedback", this.s3SecretKeyField);
        this.s3SecretKeyIContainer.add(this.s3SecretKeyFeedback);
    }

    protected void initS3BucketNameBlock() {
        this.s3BucketNameBlock = new WebMarkupBlock("s3BucketNameBlock", Size.Twelve_12);
        this.form.add(this.s3BucketNameBlock);
        this.s3BucketNameIContainer = new WebMarkupContainer("s3BucketNameIContainer");
        this.s3BucketNameBlock.add(this.s3BucketNameIContainer);
        this.s3BucketNameField = new TextField<>("s3BucketNameField", new PropertyModel<>(this, "s3BucketNameValue"));
        this.s3BucketNameField.setRequired(true);
        this.s3BucketNameIContainer.add(this.s3BucketNameField);
        this.s3BucketNameFeedback = new TextFeedbackPanel("s3BucketNameFeedback", this.s3BucketNameField);
        this.s3BucketNameIContainer.add(this.s3BucketNameFeedback);
    }

    protected void initS3AccessKeyBlock() {
        this.s3AccessKeyBlock = new WebMarkupBlock("s3AccessKeyBlock", Size.Twelve_12);
        this.form.add(this.s3AccessKeyBlock);
        this.s3AccessKeyIContainer = new WebMarkupContainer("s3AccessKeyIContainer");
        this.s3AccessKeyBlock.add(this.s3AccessKeyIContainer);
        this.s3AccessKeyField = new TextField<>("s3AccessKeyField", new PropertyModel<>(this, "s3AccessKeyValue"));
        this.s3AccessKeyField.setRequired(true);
        this.s3AccessKeyIContainer.add(this.s3AccessKeyField);
        this.s3AccessKeyFeedback = new TextFeedbackPanel("s3AccessKeyFeedback", this.s3AccessKeyField);
        this.s3AccessKeyIContainer.add(this.s3AccessKeyFeedback);
    }

    @Override
    protected void configureMetaData() {
    }

    protected void saveButtonSubmit(Button button) {
        ExternalServiceBuilder builder = new ExternalServiceBuilder(ServiceType.S3);
        builder.withS3AccessKey(this.s3AccessKeyValue);
        builder.withS3BucketName(this.s3BucketNameValue);
        builder.withS3SecretKey(this.s3SecretKeyValue);

        JsonNode node = ServiceHelper.update((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(ServiceDashboardPage.class);
    }
}
