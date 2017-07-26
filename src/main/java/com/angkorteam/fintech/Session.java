package com.angkorteam.fintech;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;
import org.json.JSONArray;
import org.json.JSONObject;

import com.angkorteam.fintech.helper.LoginHelper;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Session extends AbstractAuthenticatedWebSession implements IMifos {

    private Roles roles;

    private String token;

    private String identifier;

    public Session(Request request) {
        super(request);
        this.roles = new Roles();
    }

    public String getToken() {
        return token;
    }

    protected boolean authenticate(String identifier, String username, String password) {
        try {
            JsonNode tokenObject = LoginHelper.authenticate(identifier, username, password);
            this.token = tokenObject.getObject().getString("base64EncodedAuthenticationKey");
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
            this.identifier = identifier;
            return true;
        } catch (UnirestException e) {
            e.printStackTrace();
        }
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

    /** True when the user is signed in */
    private final AtomicBoolean signedIn = new AtomicBoolean(false);

    /**
     * Try to logon the user. It'll call {@link #authenticate(String, String)}
     * to do the real work and that is what you need to subclass to provide your
     * own authentication mechanism.
     * 
     * @param username
     * @param password
     * @return true, if logon was successful
     */
    public final boolean signIn(final String identifier, final String username, final String password) {
        boolean authenticated = authenticate(identifier, username, password);

        if (authenticated && signedIn.compareAndSet(false, true)) {
            bind();
        }
        return signedIn.get();
    }

    /**
     * Cookie based logins (remember me) may not rely on putting username and
     * password into the cookie but something else that safely identifies the
     * user. This method is meant to support these use cases.
     * 
     * It is protected (and not public) to enforce that cookie based
     * authentication gets implemented in a subclass (like you need to implement
     * {@link #authenticate(String, String)} for 'normal' authentication).
     * 
     * @see #authenticate(String, String)
     * 
     * @param value
     */
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
