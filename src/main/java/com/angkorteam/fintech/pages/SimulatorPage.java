package com.angkorteam.fintech.pages;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
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
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.google.common.collect.Lists;

import io.github.openunirest.http.HttpResponse;
import io.github.openunirest.http.Unirest;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class SimulatorPage extends Page {

    protected Form<Void> form;
    protected Button simulateButton;

    protected UIRow row1;

    protected UIBlock urlBlock;
    protected UIContainer urlIContainer;
    protected String urlValue;
    protected TextField<String> urlField;

    protected UIBlock methodBlock;
    protected UIContainer methodIContainer;
    protected String methodValue;
    protected DropDownChoice<String> methodField;

    protected UIRow row2;

    protected UIBlock requestBlock;
    protected UIContainer requestIContainer;
    protected String requestValue;
    protected TextArea<String> requestField;

    protected UIRow row3;

    protected UIBlock statusCodeBlock;
    protected UIContainer statusCodeIContainer;
    protected String statusCodeValue;
    protected TextField<String> statusCodeField;

    protected UIBlock statusTextBlock;
    protected UIContainer statusTextIContainer;
    protected String statusTextValue;
    protected TextField<String> statusTextField;

    protected UIRow row4;

    protected UIBlock responseBlock;
    protected UIContainer responseIContainer;
    protected String responseValue;
    protected TextArea<String> responseField;

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
        this.methodField.setLabel(Model.of("Method"));
        this.urlField.setLabel(Model.of("URL"));
        this.urlField.setRequired(true);
        this.methodField.setRequired(true);
        this.requestField.setLabel(Model.of("Request"));
        this.statusCodeField.setLabel(Model.of("Status Code"));
        this.statusTextField.setLabel(Model.of("Status Text"));
        this.responseField.setLabel(Model.of("Response"));
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.simulateButton = new Button("simulateButton");
        this.simulateButton.setOnSubmit(this::simulateButtonSubmit);
        this.form.add(this.simulateButton);

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.methodBlock = this.row1.newUIBlock("methodBlock", Size.Four_4);
        this.methodIContainer = this.methodBlock.newUIContainer("methodIContainer");
        this.methodField = new DropDownChoice<>("methodField", new PropertyModel<>(this, "methodValue"), Arrays.asList("Get", "Post", "Delete", "Put"));
        this.methodIContainer.add(this.methodField);
        this.methodIContainer.newFeedback("methodFeedback", this.methodField);

        this.urlBlock = this.row1.newUIBlock("urlBlock", Size.Eight_8);
        this.urlIContainer = this.urlBlock.newUIContainer("urlIContainer");
        this.urlField = new TextField<>("urlField", new PropertyModel<>(this, "urlValue"));
        this.urlIContainer.add(this.urlField);
        this.urlIContainer.newFeedback("urlFeedback", this.urlField);

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.requestBlock = this.row2.newUIBlock("requestBlock", Size.Twelve_12);
        this.requestIContainer = this.requestBlock.newUIContainer("requestIContainer");
        this.requestField = new TextArea<>("requestField", new PropertyModel<>(this, "requestValue"));
        this.requestIContainer.add(this.requestField);
        this.requestIContainer.newFeedback("requestFeedback", this.requestField);

        this.row3 = UIRow.newUIRow("row3", this.form);

        this.statusCodeBlock = this.row3.newUIBlock("statusCodeBlock", Size.Six_6);
        this.statusCodeIContainer = this.statusCodeBlock.newUIContainer("statusCodeIContainer");
        this.statusCodeField = new TextField<>("statusCodeField", new PropertyModel<>(this, "statusCodeValue"));
        this.statusCodeIContainer.add(this.statusCodeField);
        this.statusCodeIContainer.newFeedback("statusCodeFeedback", this.statusCodeField);

        this.statusTextBlock = this.row3.newUIBlock("statusTextBlock", Size.Six_6);
        this.statusTextIContainer = this.statusTextBlock.newUIContainer("statusTextIContainer");
        this.statusTextField = new TextField<>("statusTextField", new PropertyModel<>(this, "statusTextValue"));
        this.statusTextIContainer.add(this.statusTextField);
        this.statusTextIContainer.newFeedback("statusTextFeedback", this.statusTextField);

        this.row4 = UIRow.newUIRow("row4", this.form);

        this.responseBlock = this.row4.newUIBlock("responseBlock", Size.Twelve_12);
        this.responseIContainer = this.responseBlock.newUIContainer("responseIContainer");
        this.responseField = new TextArea<>("responseField", new PropertyModel<>(this, "responseValue"));
        this.responseIContainer.add(this.responseField);
        this.responseIContainer.newFeedback("responseFeedback", this.responseField);

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
