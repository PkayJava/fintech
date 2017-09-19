package com.angkorteam.fintech.table;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.angkorteam.framework.BadgeType;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;

/**
 * Created by socheatkhauv on 6/17/17.
 */
public class BadgeCell extends ItemPanel {

    private BadgeType type;

    private IModel<?> model;

    public BadgeCell(BadgeType type, byte v) {
        this(type, Model.of(String.valueOf(v)));
    }

    public BadgeCell(BadgeType type, Byte v) {
        this(type, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public BadgeCell(BadgeType type, boolean v) {
        this(type, Model.of(String.valueOf(v)));
    }

    public BadgeCell(BadgeType type, Boolean v) {
        this(type, v != null && v ? Model.of("Yes") : Model.of("No"));
    }

    public BadgeCell(BadgeType type, short v) {
        this(type, Model.of(String.valueOf(v)));
    }

    public BadgeCell(BadgeType type, Short v) {
        this(type, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public BadgeCell(BadgeType type, int v) {
        this(type, Model.of(String.valueOf(v)));
    }

    public BadgeCell(BadgeType type, Integer v) {
        this(type, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public BadgeCell(BadgeType type, long v) {
        this(type, Model.of(String.valueOf(v)));
    }

    public BadgeCell(BadgeType type, Long v) {
        this(type, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public BadgeCell(BadgeType type, double v) {
        this(type, Model.of(String.valueOf(v)));
    }

    public BadgeCell(BadgeType type, Double v) {
        this(type, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public BadgeCell(BadgeType type, float v) {
        this(type, Model.of(String.valueOf(v)));
    }

    public BadgeCell(BadgeType type, Float v) {
        this(type, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public BadgeCell(BadgeType type, String v) {
        this(type, v != null ? Model.of(v) : Model.of(""));
    }

    public BadgeCell(BadgeType type, Date v, String pattern) {
        this(type, v != null ? Model.of(DateFormatUtils.format(v, pattern)) : Model.of(""));
    }

    public BadgeCell(BadgeType type, BigDecimal v) {
        this(type, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public BadgeCell(BadgeType type, BigInteger v) {
        this(type, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public BadgeCell(BadgeType type, Character v) {
        this(type, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public BadgeCell(BadgeType type, Number v) {
        this(type, v != null ? Model.of(String.valueOf(v.doubleValue())) : Model.of(""));
    }

    public BadgeCell(BadgeType type, char v) {
        this(type, Model.of(String.valueOf(v)));
    }

    public BadgeCell(BadgeType type, IModel<?> model) {
        super(model);
        this.type = type;
        this.model = model;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        WebMarkupContainer badge = new WebMarkupContainer("badge");
        add(badge);
        if (this.type != null) {
            badge.add(AttributeModifier.append("class", this.type.getLiteral()));
        }
        Label text = new Label("text", this.model);
        badge.add(text);
    }
}
