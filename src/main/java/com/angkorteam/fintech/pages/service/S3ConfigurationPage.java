package com.angkorteam.fintech.pages.service;

import java.util.List;
import java.util.Map;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
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
public class S3ConfigurationPage extends Page {

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected UIRow row1;

    protected UIBlock s3AccessKeyBlock;
    protected UIContainer s3AccessKeyIContainer;
    protected String s3AccessKeyValue;
    protected TextField<String> s3AccessKeyField;

    protected UIRow row2;

    protected UIBlock s3BucketNameBlock;
    protected UIContainer s3BucketNameIContainer;
    protected String s3BucketNameValue;
    protected TextField<String> s3BucketNameField;

    protected UIRow row3;

    protected UIBlock s3SecretKeyBlock;
    protected UIContainer s3SecretKeyIContainer;
    protected String s3SecretKeyValue;
    protected TextField<String> s3SecretKeyField;

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

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.s3AccessKeyBlock = this.row1.newUIBlock("s3AccessKeyBlock", Size.Twelve_12);
        this.s3AccessKeyIContainer = this.s3AccessKeyBlock.newUIContainer("s3AccessKeyIContainer");
        this.s3AccessKeyField = new TextField<>("s3AccessKeyField", new PropertyModel<>(this, "s3AccessKeyValue"));
        this.s3AccessKeyIContainer.add(this.s3AccessKeyField);
        this.s3AccessKeyIContainer.newFeedback("s3AccessKeyFeedback", this.s3AccessKeyField);

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.s3BucketNameBlock = this.row2.newUIBlock("s3BucketNameBlock", Size.Twelve_12);
        this.s3BucketNameIContainer = this.s3BucketNameBlock.newUIContainer("s3BucketNameIContainer");
        this.s3BucketNameField = new TextField<>("s3BucketNameField", new PropertyModel<>(this, "s3BucketNameValue"));
        this.s3BucketNameIContainer.add(this.s3BucketNameField);
        this.s3BucketNameIContainer.newFeedback("s3BucketNameFeedback", this.s3BucketNameField);

        this.row3 = UIRow.newUIRow("row3", this.form);

        this.s3SecretKeyBlock = this.row3.newUIBlock("s3SecretKeyBlock", Size.Twelve_12);
        this.s3SecretKeyIContainer = this.s3SecretKeyBlock.newUIContainer("s3SecretKeyIContainer");
        this.s3SecretKeyField = new TextField<>("s3SecretKeyField", new PropertyModel<>(this, "s3SecretKeyValue"));
        this.s3SecretKeyIContainer.add(this.s3SecretKeyField);
        this.s3SecretKeyIContainer.newFeedback("s3SecretKeyFeedback", this.s3SecretKeyField);
    }

    @Override
    protected void configureMetaData() {
        this.s3SecretKeyField.setRequired(true);
        this.s3BucketNameField.setRequired(true);
        this.s3AccessKeyField.setRequired(true);
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
