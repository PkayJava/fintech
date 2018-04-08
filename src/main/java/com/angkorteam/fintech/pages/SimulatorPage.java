package com.angkorteam.fintech.pages;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import com.angkorteam.fintech.widget.WebMarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.springframework.http.MediaType;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class SimulatorPage extends Page {

    protected Form<Void> form;
    protected Button simulateButton;

    protected WebMarkupBlock urlBlock;
    protected WebMarkupContainer urlIContainer;
    protected String urlValue;
    protected TextField<String> urlField;
    protected TextFeedbackPanel urlFeedback;

    protected WebMarkupBlock methodBlock;
    protected WebMarkupContainer methodIContainer;
    protected String methodValue;
    protected DropDownChoice<String> methodField;
    protected TextFeedbackPanel methodFeedback;

    protected WebMarkupBlock requestBlock;
    protected WebMarkupContainer requestIContainer;
    protected String requestValue;
    protected TextArea<String> requestField;
    protected TextFeedbackPanel requestFeedback;

    protected WebMarkupBlock statusCodeBlock;
    protected WebMarkupContainer statusCodeIContainer;
    protected String statusCodeValue;
    protected TextField<String> statusCodeField;
    protected TextFeedbackPanel statusCodeFeedback;

    protected WebMarkupBlock statusTextBlock;
    protected WebMarkupContainer statusTextIContainer;
    protected String statusTextValue;
    protected TextField<String> statusTextField;
    protected TextFeedbackPanel statusTextFeedback;

    protected WebMarkupBlock responseBlock;
    protected WebMarkupContainer responseIContainer;
    protected String responseValue;
    protected TextArea<String> responseField;
    protected TextFeedbackPanel responseFeedback;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Simulator");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void configureMetaData() {
        this.urlField.setRequired(true);
        this.methodField.setRequired(true);
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.simulateButton = new Button("simulateButton");
        this.simulateButton.setOnSubmit(this::simulateButtonSubmit);
        this.form.add(this.simulateButton);

        this.methodBlock = new WebMarkupBlock("methodBlock", Size.Four_4);
        this.form.add(this.methodBlock);
        this.methodIContainer = new WebMarkupContainer("methodIContainer");
        this.methodBlock.add(this.methodIContainer);
        this.methodField = new DropDownChoice<>("methodField", new PropertyModel<>(this, "methodValue"), Arrays.asList("Get", "Post", "Delete", "Put"));
        this.methodField.setLabel(Model.of("Method"));
        this.methodIContainer.add(this.methodField);
        this.methodFeedback = new TextFeedbackPanel("methodFeedback", this.methodField);
        this.methodIContainer.add(this.methodFeedback);

        this.urlBlock = new WebMarkupBlock("urlBlock", Size.Eight_8);
        this.form.add(this.urlBlock);
        this.urlIContainer = new WebMarkupContainer("urlIContainer");
        this.urlBlock.add(this.urlIContainer);
        this.urlField = new TextField<>("urlField", new PropertyModel<>(this, "urlValue"));
        this.urlField.setLabel(Model.of("URL"));
        this.urlIContainer.add(this.urlField);
        this.urlFeedback = new TextFeedbackPanel("urlFeedback", this.urlField);
        this.urlIContainer.add(this.urlFeedback);

        this.statusCodeBlock = new WebMarkupBlock("statusCodeBlock", Size.Six_6);
        this.form.add(this.statusCodeBlock);
        this.statusCodeIContainer = new WebMarkupContainer("statusCodeIContainer");
        this.statusCodeBlock.add(this.statusCodeIContainer);
        this.statusCodeField = new TextField<>("statusCodeField", new PropertyModel<>(this, "statusCodeValue"));
        this.statusCodeField.setLabel(Model.of("Status Code"));
        this.statusCodeIContainer.add(this.statusCodeField);
        this.statusCodeFeedback = new TextFeedbackPanel("statusCodeFeedback", this.statusCodeField);
        this.statusCodeIContainer.add(this.statusCodeFeedback);

        this.statusTextBlock = new WebMarkupBlock("statusTextBlock", Size.Six_6);
        this.form.add(this.statusTextBlock);
        this.statusTextIContainer = new WebMarkupContainer("statusTextIContainer");
        this.statusTextBlock.add(this.statusTextIContainer);
        this.statusTextField = new TextField<>("statusTextField", new PropertyModel<>(this, "statusTextValue"));
        this.statusTextField.setLabel(Model.of("Status Text"));
        this.statusTextIContainer.add(this.statusTextField);
        this.statusTextFeedback = new TextFeedbackPanel("statusTextFeedback", this.statusTextField);
        this.statusTextIContainer.add(this.statusTextFeedback);

        this.requestBlock = new WebMarkupBlock("requestBlock", Size.Twelve_12);
        this.form.add(this.requestBlock);
        this.requestIContainer = new WebMarkupContainer("requestIContainer");
        this.requestBlock.add(this.requestIContainer);
        this.requestField = new TextArea<>("requestField", new PropertyModel<>(this, "requestValue"));
        this.requestField.setLabel(Model.of("Request"));
        this.requestIContainer.add(this.requestField);
        this.requestFeedback = new TextFeedbackPanel("requestFeedback", this.requestField);
        this.requestIContainer.add(this.requestFeedback);

        this.responseBlock = new WebMarkupBlock("responseBlock", Size.Twelve_12);
        this.form.add(this.responseBlock);
        this.responseIContainer = new WebMarkupContainer("responseIContainer");
        this.responseBlock.add(this.responseIContainer);
        this.responseField = new TextArea<>("responseField", new PropertyModel<>(this, "responseValue"));
        this.responseField.setLabel(Model.of("Response"));
        this.responseIContainer.add(this.responseField);
        this.responseFeedback = new TextFeedbackPanel("responseFeedback", this.responseField);
        this.responseIContainer.add(this.responseFeedback);

    }

    protected void simulateButtonSubmit(Button button) {
        try {
            Session session = (Session) getSession();
            String identifier = session.getIdentifier();
            String token = session.getToken();
            HttpResponse<String> resp = null;
            this.statusTextValue = "";
            this.statusCodeValue = "";
            this.responseValue = "";
            if ("POST".equalsIgnoreCase(this.methodValue)) {
                if (this.requestValue == null || "".equalsIgnoreCase(this.requestValue)) {
                    resp = Unirest.post(this.urlValue).header("Authorization", "Basic " + token).header("Fineract-Platform-TenantId", identifier).header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE).asString();
                } else {
                    resp = Unirest.post(this.urlValue).header("Authorization", "Basic " + token).header("Fineract-Platform-TenantId", identifier).header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE).body(this.requestValue).asString();
                }
            } else if ("GET".equals(this.methodValue)) {
                resp = Unirest.get(this.urlValue).header("Authorization", "Basic " + token).header("Fineract-Platform-TenantId", identifier).header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE).asString();
            } else if ("PUT".equalsIgnoreCase(this.methodValue)) {
                if (this.requestValue == null || "".equalsIgnoreCase(this.requestValue)) {
                    resp = Unirest.put(this.urlValue).header("Authorization", "Basic " + token).header("Fineract-Platform-TenantId", identifier).header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE).asString();
                } else {
                    resp = Unirest.put(this.urlValue).header("Authorization", "Basic " + token).header("Fineract-Platform-TenantId", identifier).header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE).body(this.requestValue).asString();
                }
            } else if ("DELETE".equalsIgnoreCase(this.methodValue)) {
                resp = Unirest.delete(this.urlValue).header("Authorization", "Basic " + token).header("Fineract-Platform-TenantId", identifier).header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE).asString();
            }
            if (resp != null) {
                this.responseValue = resp.getBody();
                this.statusCodeValue = resp.getStatus() + "";
                this.statusTextValue = resp.getStatusText();
            }
        } catch (Throwable e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            this.responseValue = errors.toString();
        }
    }

}
