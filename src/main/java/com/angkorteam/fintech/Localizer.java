package com.angkorteam.fintech;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.model.IModel;

import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.spring.JdbcTemplate;

public class Localizer extends org.apache.wicket.Localizer {

    private static List<String> SYSTEM = new ArrayList<>();

    static {
        SYSTEM.add("OddEvenItem.CSS.odd");
        SYSTEM.add("OddEvenItem.CSS.even");
        SYSTEM.add("OrderByLink.CSS.none");
        SYSTEM.add("OrderByLink.CSS.ascending");
        SYSTEM.add("OrderByLink.CSS.descending");
        SYSTEM.add("AutoLabel.CSS.required");
        SYSTEM.add("NavigatorLabel");
        SYSTEM.add("PagingNavigation.page");
        SYSTEM.add("PagingNavigator.first");
        SYSTEM.add("PagingNavigator.last");
        SYSTEM.add("PagingNavigator.next");
        SYSTEM.add("PagingNavigator.previous");
        SYSTEM.add("datatable.go");
        SYSTEM.add("datatable.clear");
        SYSTEM.add("datatable.no-records-found");
    }

    @Override
    public String getStringIgnoreSettings(String key, Component component, IModel<?> model, Locale locale, String style, String defaultValue) {
        if (SYSTEM.contains(key)) {
            return super.getStringIgnoreSettings(key, component, model, locale, style, defaultValue);
        }
        String pageClass = null;
        if (component != null) {
            if (component instanceof org.apache.wicket.Page) {
                pageClass = component.getClass().getName();
            } else {
                Page page = component.findParent(Page.class);
                if (page != null) {
                    pageClass = page.getClass().getName();
                }
            }
        }

        // Make sure locale, style and variation have the right values
        String variation = (component != null ? component.getVariation() : null);

        if ((locale == null) && (component != null)) {
            locale = component.getLocale();
        }
        if (locale == null) {
            locale = org.apache.wicket.Session.exists() ? org.apache.wicket.Session.get().getLocale() : Locale.getDefault();
        }

        if ((style == null) && (component != null)) {
            style = component.getStyle();
        }
        if (style == null) {
            style = org.apache.wicket.Session.exists() ? Session.get().getStyle() : null;
        }

        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
        List<String> wheres = new ArrayList<>();
        if (pageClass == null || "".equals(pageClass)) {
            wheres.add("page_class is null");
        } else {
            wheres.add("page_class = '" + pageClass + "'");
        }
        if (variation == null || "".equals(variation)) {
            wheres.add("variation is null");
        } else {
            wheres.add("variation = '" + variation + "'");
        }
        if (locale == null || locale.getLanguage() == null || "".equals(locale.getLanguage())) {
            wheres.add("locale is null");
        } else {
            wheres.add("locale = '" + locale.getLanguage() + "'");
        }
        if (style == null || "".equals(style)) {
            wheres.add("style is null");
        } else {
            wheres.add("style = '" + style + "'");
        }
        wheres.add("message_key = '" + key + "'");

        if (key.contains("genderField")) {
            System.out.println("break");
        }

        Map<String, Object> localization = jdbcTemplate.queryForMap("select id, message, silent from m_localization where " + StringUtils.join(wheres, " and "));
        if (localization == null) {
            String wicketValue = super.getStringIgnoreSettings(key, component, model, locale, style, defaultValue);
            Map<String, Object> rec = new HashMap<>();
            String id = jdbcTemplate.queryForObject("select uuid() from dual", String.class);
            if (pageClass != null && !"".equals(pageClass)) {
                rec.put("page_class", pageClass);
            }
            if (variation != null && !"".equals(variation)) {
                rec.put("variation", variation);
            }
            if (locale != null && locale.getLanguage() != null && !"".equals(locale.getLanguage())) {
                rec.put("locale", locale.getLanguage());
            }
            if (style != null && !"".equals(style)) {
                rec.put("style", style);
            }
            if (!"".equals(key)) {
                rec.put("message_key", key);
            }
            rec.put("silent", false);
            rec.put("version", 1);
            rec.put("demand", new Date());
            // Repository.insert(jdbcTemplate, named, "m_localization", id, rec);
            return wicketValue;
        } else {
            if (localization.get("message") == null || "".equals(localization.get("message"))) {
                String wicketValue = super.getStringIgnoreSettings(key, component, model, locale, style, defaultValue);
                if (localization.get("silent") != null && !((Boolean) localization.get("silent"))) {
                    jdbcTemplate.update("UPDATE m_localization SET demand = ? where id = ?", new Date(), localization.get("id"));
                }
                return wicketValue;
            } else {
                if (localization.get("silent") != null && !((Boolean) localization.get("silent"))) {
                    jdbcTemplate.update("UPDATE m_localization SET demand = ? where id = ?", new Date(), localization.get("id"));
                }
                return (String) localization.get("message");
            }
        }
    }
}
