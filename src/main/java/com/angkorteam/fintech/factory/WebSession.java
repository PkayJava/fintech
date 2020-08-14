package com.angkorteam.fintech.factory;

import com.angkorteam.fintech.IMifos;
import com.angkorteam.fintech.client.dto.AuthenticateRequest;
import com.angkorteam.fintech.client.FineractClient;
import com.angkorteam.fintech.client.dto.PostAuthenticationResponse;
import com.angkorteam.fintech.client.dto.RoleData;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.Language;
import com.angkorteam.webui.frmk.common.WicketFactory;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class WebSession extends AuthenticatedWebSession implements IMifos {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSession.class);

    private Roles roles;

    private String token;

    private Language language = Language.English;

    public static final String IDENTIFIER = "mifos_identifier";

    public static final String TOKEN = "mifos_token";

    public WebSession(Request request) {
        super(request);
        this.roles = new Roles();
    }

    @Override
    protected boolean authenticate(String username, String password) {
        ApplicationContext context = WicketFactory.getApplicationContext();
        FineractClient client = context.getBean(FineractClient.class);
        String identifier = (String) getAttribute(IDENTIFIER);
        try {
            PostAuthenticationResponse response = client.authentication(identifier, new AuthenticateRequest(username, password));
            this.token = response.getBase64EncodedAuthenticationKey();
            if (response.getRoles() != null && !response.getRoles().isEmpty()) {
                for (RoleData role : response.getRoles()) {
                    this.roles.add(role.getName());
                }
            }
            setAttribute(TOKEN, this.token);
            this.roles.add(Function.ALL_FUNCTION);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public Roles getRoles() {
        return this.roles;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public String getIdentifier() {
        return (String) getAttribute(IDENTIFIER);
    }

    public Language getLanguage() {
        return language;
    }

}
