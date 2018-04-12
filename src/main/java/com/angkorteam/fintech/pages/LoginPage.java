package com.angkorteam.fintech.pages;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.angkorteam.fintech.Application;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.Tenants;
import com.angkorteam.fintech.provider.MifosSingleChoiceProvider;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.framework.ReferenceUtilities;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

/**
 * Created by socheatkhauv on 6/17/17.
 */
public class LoginPage extends WebPage {

    protected MifosSingleChoiceProvider identifierProvider;
    protected Option identifierValue;
    protected Select2SingleChoice<Option> identifierField;

    protected String loginValue;
    protected TextField<String> loginField;

    protected String passwordValue;
    protected TextField<String> passwordField;

    protected String versionValue;
    protected ReadOnlyView versionView;

    protected Form<Void> form;
    protected Button loginButton;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        String manifest = "/META-INF/MANIFEST.MF";
        String absoluteDiskPath = Application.get().getServletContext().getRealPath(manifest);
        File file = new File(absoluteDiskPath);

        if (file.exists()) {
            try {
                List<String> lines = FileUtils.readLines(file, "UTF-8");
                String v = "";
                String t = "";
                for (String line : lines) {
                    if (line != null && !"".equals(line)) {
                        if (line.startsWith("Version")) {
                            v = StringUtils.trimToEmpty(StringUtils.substring(line, "Version:".length()));
                        } else if (line.startsWith("Build-Time")) {
                            t = StringUtils.trimToEmpty(StringUtils.substring(line, "Build-Time:".length()));
                        }
                    }
                }
                this.versionValue = "VERSION : " + v + " " + t;
            } catch (IOException e) {
                throw new WicketRuntimeException(e);
            }
        } else {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;

            try {
                builder = factory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                throw new WicketRuntimeException(e);
            }

            File popFile = new File("pom.xml");

            Document document = null;
            try {
                document = builder.parse(popFile);
            } catch (SAXException | IOException e) {
                throw new WicketRuntimeException(e);
            }

            this.versionValue = "VERSION : " + document.getDocumentElement().getElementsByTagName("version").item(0).getTextContent() + " " + DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT.format(new Date());
        }

        this.form = new Form<>("form");
        this.add(this.form);

        this.identifierProvider = new MifosSingleChoiceProvider(Tenants.NAME, Tenants.Field.IDENTIFIER, Tenants.Field.NAME);
        this.identifierField = new Select2SingleChoice<>("identifierField", new PropertyModel<>(this, "identifierValue"), this.identifierProvider);
        this.identifierField.add(new OnChangeAjaxBehavior());
        this.identifierField.setRequired(true);
        this.form.add(this.identifierField);

        this.loginField = new TextField<>("loginField", new PropertyModel<>(this, "loginValue"));
        this.loginField.setRequired(true);
        this.form.add(this.loginField);

        this.passwordField = new PasswordTextField("passwordField", new PropertyModel<>(this, "passwordValue"));
        this.passwordField.setRequired(true);
        this.form.add(this.passwordField);

        this.loginButton = new Button("loginButton");
        this.form.add(this.loginButton);
        this.loginButton.setOnSubmit(this::loginButtonClick);

        this.versionView = new ReadOnlyView("versionView", new PropertyModel<String>(this, "versionValue"));
        add(this.versionView);
    }

    protected void loginButtonClick(Button button) {
        Session session = (Session) getSession();
        HttpServletRequest request = (HttpServletRequest) getRequest().getContainerRequest();
        boolean valid = session.signIn(request.getSession(), this.identifierValue.getId(), this.loginValue, this.passwordValue);
        if (valid) {
            setResponsePage(getApplication().getHomePage());
        }
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        com.angkorteam.framework.Option option = new com.angkorteam.framework.Option();
        option.Bootstrap = true;
        option.FontAwesome = true;
        option.Ionicons = true;
        option.AdminLTE = true;
        option.ICheck = true;
        option.HTML5ShimAndRespondJS = true;
        option.GoogleFont = true;
        option.JQuery = true;
        ReferenceUtilities.render(option, response);
    }
}
