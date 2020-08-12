package com.angkorteam.fintech;

import com.angkorteam.fintech.factory.WebSession;
import com.angkorteam.fintech.provider.ui.MemoryFooterProvider;
import com.angkorteam.fintech.provider.ui.MemoryMainSidebarProvider;
import com.angkorteam.fintech.provider.ui.MemoryNavbarProvider;
import com.angkorteam.fintech.provider.ui.MemoryThemeProvider;
import io.github.openunirest.http.JsonNode;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.MissingResourceException;

public abstract class MasterPage extends com.angkorteam.webui.frmk.wicket.layout.MasterPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(MasterPage.class);

    public MasterPage() {
    }

    public MasterPage(IModel<?> model) {
        super(model);
    }

    public MasterPage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitData() {
        this.themeProvider = new MemoryThemeProvider();
        this.mainSidebarProvider = new MemoryMainSidebarProvider(getSession());
        this.navbarProvider = new MemoryNavbarProvider(getSession());
        this.footerProvider = new MemoryFooterProvider(getSession());
    }

    @Override
    public WebSession getSession() {
        return (WebSession) super.getSession();
    }

    public boolean reportError(JsonNode node) {
        if (node.getObject().has("errors")) {
            JSONArray array = node.getObject().getJSONArray("errors");
            for (Object object : array) {
                JSONObject o = (JSONObject) object;
                String defaultUserMessage = o.getString("defaultUserMessage");
                String userMessageGlobalisationCode = o.getString("userMessageGlobalisationCode");
                if (userMessageGlobalisationCode != null && !"".equals(userMessageGlobalisationCode)) {
                    String fieldNames = null;
                    try {
                        fieldNames = getString(userMessageGlobalisationCode);
                    } catch (MissingResourceException e) {
                        error(userMessageGlobalisationCode + " due to this reason " + defaultUserMessage);
                        continue;
                    }

                    if (fieldNames != null && !"".equals(fieldNames)) {
                        for (String fieldName : StringUtils.split(fieldNames, ',')) {
                            java.lang.reflect.Field temp = null;
                            try {
                                temp = getClass().getDeclaredField(StringUtils.trimToEmpty(fieldName));
                            } catch (NoSuchFieldException | SecurityException e) {
                                error(userMessageGlobalisationCode + " due to this reason " + defaultUserMessage);
                                continue;
                            }
                            temp.setAccessible(true);

                            FormComponent<?> field = null;
                            try {
                                field = (FormComponent<?>) temp.get(this);
                            } catch (IllegalArgumentException | IllegalAccessException e) {
                                error(userMessageGlobalisationCode + " due to this reason " + defaultUserMessage);
                                continue;
                            }

                            if (o.has("parameterName")) {
                                String parameterName = o.getString("parameterName");
                                String parameterNameField = null;
                                String translated = null;
                                try {
                                    parameterNameField = getString(parameterName);
                                } catch (MissingResourceException e) {
                                }
                                if (parameterNameField != null && !"".equals(parameterNameField)) {
                                    java.lang.reflect.Field t = null;
                                    try {
                                        t = getClass().getDeclaredField(parameterNameField);
                                    } catch (NoSuchFieldException | SecurityException e) {
                                    }
                                    if (t != null) {
                                        t.setAccessible(true);
                                        FormComponent<?> f = null;
                                        try {
                                            f = (FormComponent<?>) temp.get(this);
                                        } catch (IllegalArgumentException | IllegalAccessException e) {
                                        }
                                        if (f != null && f.getLabel() != null) {
                                            translated = f.getLabel().getObject();
                                        }
                                    }
                                    if (translated != null && !"".equals(translated)) {
                                        defaultUserMessage = StringUtils.replaceAll(defaultUserMessage, parameterName, "'" + translated + "'");
                                    }
                                }
                            }

                            field.info(defaultUserMessage);
                            LOGGER.info("{} => {} => {}", userMessageGlobalisationCode, fieldName, defaultUserMessage);
                        }
                    } else {
                        error(userMessageGlobalisationCode + " due to this reason " + defaultUserMessage);
                    }
                } else {
                    error(defaultUserMessage);
                }
            }

            return true;
        } else {
            if (node.getObject().has("exception") || node.getObject().has("error")) {
                if (node.getObject().has("exception") && !node.getObject().isNull("exception")) {
                    error((String) node.getObject().get("exception"));
                }
                if (node.getObject().has("error") && !node.getObject().isNull("error")) {
                    error((String) node.getObject().get("error"));
                }
                if (node.getObject().has("message") && !node.getObject().isNull("message")) {
                    error((String) node.getObject().get("message").getClass().getName());
                }
                return true;
            }
        }
        return false;
    }

    protected void reportError(JsonNode node, AjaxRequestTarget target) {
        if (node == null) {
            return;
        }
        if (node.getObject().has("errors")) {
            JSONArray array = (JSONArray) node.getObject().get("errors");
            for (Object object : array) {
                JSONObject o = (JSONObject) object;
                error((String) o.get("defaultUserMessage"));
            }
        } else {
            if (node.getObject().has("exception") || node.getObject().has("error")) {
                if (node.getObject().has("exception")) {
                    error((String) node.getObject().get("exception"));
                }
                if (node.getObject().has("error")) {
                    error((String) node.getObject().get("error"));
                }
                if (node.getObject().has("message")) {
                    error((String) node.getObject().get("message"));
                }
            }
        }
        target.add(getMessageContainer());
    }

}
