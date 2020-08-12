package com.angkorteam.fintech.pages;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.boot.AppProperties;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.factory.WebSession;
import com.angkorteam.fintech.provider.ui.MemoryContentHeaderProvider;
import com.angkorteam.webui.frmk.common.WicketFactory;
import com.angkorteam.webui.frmk.model.Breadcrumb;
import com.angkorteam.webui.frmk.model.ContentHeader;
import com.angkorteam.webui.frmk.wicket.layout.Size;
import com.angkorteam.webui.frmk.wicket.layout.UIColumn;
import com.angkorteam.webui.frmk.wicket.layout.UIContainer;
import com.angkorteam.webui.frmk.wicket.layout.UIRow;
import io.github.openunirest.http.HttpResponse;
import io.github.openunirest.http.Unirest;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class SimulatorPage extends MasterPage {

    protected Form<Void> form;
    protected Button simulateButton;

    protected UIRow row1;

    protected UIColumn urlColumn;
    protected UIContainer urlIContainer;
    protected String urlValue;
    protected TextField<String> urlField;

    protected UIColumn methodColumn;
    protected UIContainer methodContainer;
    protected String methodValue;
    protected DropDownChoice<String> methodField;

    protected UIRow row2;

    protected UIColumn requestColumn;
    protected UIContainer requestContainer;
    protected String requestValue;
    protected TextArea<String> requestField;

    protected UIRow row3;

    protected UIColumn statusCodeColumn;
    protected UIContainer statusCodeContainer;
    protected String statusCodeValue;
    protected TextField<String> statusCodeField;

    protected UIColumn statusTextColumn;
    protected UIContainer statusTextContainer;
    protected String statusTextValue;
    protected TextField<String> statusTextField;

    protected UIRow row4;

    protected UIColumn responseColumn;
    protected UIContainer responseContainer;
    protected String responseValue;
    protected TextArea<String> responseField;

//    @Override
//    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
//        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Simulator");
//            BREADCRUMB.add(breadcrumb);
//        }
//        return Model.ofList(BREADCRUMB);
//    }


    @Override
    protected void onInitData() {
        super.onInitData();
        ApplicationContext context = WicketFactory.getApplicationContext();
        AppProperties properties = context.getBean(AppProperties.class);
        this.urlValue = properties.getMifosUrl();
        this.contentHeaderProvider = new MemoryContentHeaderProvider(new ContentHeader("Simulator", new Breadcrumb("Simulator", SimulatorPage.class)));
    }

    @Override
    protected void onInitHtml(MarkupContainer body) {
        this.form = new Form<>("form");
        body.add(this.form);

        this.simulateButton = new Button("simulateButton") {
            @Override
            public void onSubmit() {
                simulateButtonClick();
            }
        };
        this.form.add(this.simulateButton);

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.methodColumn = this.row1.newUIColumn("methodColumn", Size.Four_4);
        this.methodContainer = this.methodColumn.newUIContainer("methodContainer");
        this.methodField = new DropDownChoice<>("methodField", new PropertyModel<>(this, "methodValue"), Arrays.asList("Get", "Post", "Delete", "Put"));
        this.methodField.setLabel(Model.of("Method"));
        this.methodField.setRequired(true);
        this.methodContainer.add(this.methodField);
        this.methodContainer.newFeedback("methodFeedback", this.methodField);

        this.urlColumn = this.row1.newUIColumn("urlColumn", Size.Eight_8);
        this.urlIContainer = this.urlColumn.newUIContainer("urlContainer");
        this.urlField = new TextField<>("urlField", new PropertyModel<>(this, "urlValue"));
        this.urlField.setLabel(Model.of("URL"));
        this.urlField.setRequired(true);
        this.urlIContainer.add(this.urlField);
        this.urlIContainer.newFeedback("urlFeedback", this.urlField);

        this.row1.newUIColumn("lastColumn");

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.requestColumn = this.row2.newUIColumn("requestColumn", Size.Twelve_12);
        this.requestContainer = this.requestColumn.newUIContainer("requestContainer");
        this.requestField = new TextArea<>("requestField", new PropertyModel<>(this, "requestValue"));
        this.requestField.setLabel(Model.of("Request"));
        this.requestContainer.add(this.requestField);
        this.requestContainer.newFeedback("requestFeedback", this.requestField);

        this.row2.newUIColumn("lastColumn");

        this.row3 = UIRow.newUIRow("row3", this.form);

        this.statusCodeColumn = this.row3.newUIColumn("statusCodeColumn", Size.Six_6);
        this.statusCodeContainer = this.statusCodeColumn.newUIContainer("statusCodeContainer");
        this.statusCodeField = new TextField<>("statusCodeField", new PropertyModel<>(this, "statusCodeValue"));
        this.statusCodeField.setLabel(Model.of("Status Code"));
        this.statusCodeContainer.add(this.statusCodeField);
        this.statusCodeContainer.newFeedback("statusCodeFeedback", this.statusCodeField);

        this.statusTextColumn = this.row3.newUIColumn("statusTextColumn", Size.Six_6);
        this.statusTextContainer = this.statusTextColumn.newUIContainer("statusTextContainer");
        this.statusTextField = new TextField<>("statusTextField", new PropertyModel<>(this, "statusTextValue"));
        this.statusTextField.setLabel(Model.of("Status Text"));
        this.statusTextContainer.add(this.statusTextField);
        this.statusTextContainer.newFeedback("statusTextFeedback", this.statusTextField);

        this.row3.newUIColumn("lastColumn");

        this.row4 = UIRow.newUIRow("row4", this.form);

        this.responseColumn = this.row4.newUIColumn("responseColumn", Size.Twelve_12);
        this.responseContainer = this.responseColumn.newUIContainer("responseContainer");
        this.responseField = new TextArea<>("responseField", new PropertyModel<>(this, "responseValue"));
        this.responseField.setLabel(Model.of("Response"));
        this.responseContainer.add(this.responseField);
        this.responseContainer.newFeedback("responseFeedback", this.responseField);

        this.row4.newUIColumn("lastColumn");
    }

    protected void simulateButtonClick() {
        try {
            WebSession session = getSession();
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
