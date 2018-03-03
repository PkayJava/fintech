package com.angkorteam.fintech;

import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.Language;
import com.angkorteam.fintech.helper.LoginHelper;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.concurrent.atomic.AtomicBoolean;

public class Session extends AbstractAuthenticatedWebSession implements IMifos {

    private static final Logger LOGGER = LoggerFactory.getLogger(Session.class);

    private Roles roles;

    private String token;

    private String identifier;

    private Language language = Language.English;

    public static final String IDENTIFIER = "mifos_identifier";

    public static final String TOKEN = "mifos_token";

    public Session(Request request) {
        super(request);
        this.roles = new Roles();
    }

    public String getToken() {
        return token;
    }

    public Language getLanguage() {
        return this.language;
    }

    protected boolean authenticate(HttpSession session, String identifier, String username, String password) {
        try {
            JsonNode tokenObject = LoginHelper.authenticate(identifier, username, password);
            if (tokenObject.getObject().has("base64EncodedAuthenticationKey")) {
                this.token = tokenObject.getObject().getString("base64EncodedAuthenticationKey");
                if (tokenObject.getObject().has("roles")) {
                    JSONArray array = tokenObject.getObject().getJSONArray("roles");
                    if (array != null) {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = (JSONObject) array.get(i);
                            String role = object.getString("name");
                            if (role != null && !"".equals(role)) {
                                this.roles.add(role);
                            }
                        }
                    }
                }
                this.identifier = identifier;
                session.setAttribute(IDENTIFIER, this.identifier);
                session.setAttribute(TOKEN, this.token);
                this.roles.add(Function.ALL_FUNCTION);
                LOGGER.info("identifier {} token {}", this.identifier, this.token);
                return true;
            } else {
                return false;
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        LOGGER.info("token : " + this.token);
        LOGGER.info("identifier : " + this.identifier);
        return false;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public Roles getRoles() {
        return this.roles;
    }

    /**
     * @return Current authenticated web session
     */
    public static AuthenticatedWebSession get() {
        return (AuthenticatedWebSession) Session.get();
    }

    /**
     * True when the user is signed in
     */
    private final AtomicBoolean signedIn = new AtomicBoolean(false);

    public final boolean signIn(HttpSession session, final String identifier, final String username, final String password) {
        boolean authenticated = authenticate(session, identifier, username, password);

        if (authenticated && signedIn.compareAndSet(false, true)) {
            bind();
        }
        return signedIn.get();
    }

    protected final void signIn(boolean value) {
        signedIn.set(value);
    }

    /**
     * @return true, if user is signed in
     */
    @Override
    public final boolean isSignedIn() {
        return signedIn.get();
    }

    /**
     * Sign the user out.
     * <p>
     * This method is an alias of {@link #invalidate()}
     * </p>
     */
    public void signOut() {
        invalidate();
    }

    /**
     * Call signOut() and remove the logon data from where ever they have been
     * persisted (e.g. Cookies)
     */
    @Override
    public void invalidate() {
        signedIn.set(false);
        super.invalidate();
    }
}
