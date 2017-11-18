package com.angkorteam.fintech.table;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.angkorteam.fintech.Application;
import com.angkorteam.framework.wicket.WicketBiConsumer;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

/**
 * Created by socheatkhauv on 6/17/17.
 */
public class ClickableCell extends ItemPanel {

    private WicketBiConsumer<Map<String, Object>, Link<Void>> onClick;

    private IModel<?> label;

    private Map<String, Object> model;

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, byte v) {
        this(click, model, Model.of(String.valueOf(v)));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, byte v, String pattern) {
        this(click, model, Model.of(Application.FORMATS.get(pattern).format(v)));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, Byte v) {
        this(click, model, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, Byte v, String pattern) {
        this(click, model, v != null ? Model.of(Application.FORMATS.get(pattern).format(v)) : Model.of(""));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, boolean v) {
        this(click, model, Model.of(String.valueOf(v)));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, Boolean v) {
        this(click, model, v != null && v ? Model.of("Yes") : Model.of("No"));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, short v) {
        this(click, model, Model.of(String.valueOf(v)));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, short v, String pattern) {
        this(click, model, Model.of(Application.FORMATS.get(pattern).format(v)));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, Short v) {
        this(click, model, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, Short v, String pattern) {
        this(click, model, v != null ? Model.of(Application.FORMATS.get(pattern).format(v)) : Model.of(""));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, int v) {
        this(click, model, Model.of(String.valueOf(v)));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, int v, String pattern) {
        this(click, model, Model.of(Application.FORMATS.get(pattern).format(v)));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, Integer v) {
        this(click, model, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, Integer v, String pattern) {
        this(click, model, v != null ? Model.of(Application.FORMATS.get(pattern).format(v)) : Model.of(""));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, long v) {
        this(click, model, Model.of(String.valueOf(v)));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, long v, String pattern) {
        this(click, model, Model.of(Application.FORMATS.get(pattern).format(v)));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, Long v) {
        this(click, model, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, Long v, String pattern) {
        this(click, model, v != null ? Model.of(Application.FORMATS.get(pattern).format(v)) : Model.of(""));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, double v) {
        this(click, model, Model.of(String.valueOf(v)));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, double v, String pattern) {
        this(click, model, Model.of(Application.FORMATS.get(pattern).format(v)));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, Double v) {
        this(click, model, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, Double v, String pattern) {
        this(click, model, v != null ? Model.of(Application.FORMATS.get(pattern).format(v)) : Model.of(""));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, float v) {
        this(click, model, Model.of(String.valueOf(v)));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, float v, String pattern) {
        this(click, model, Model.of(Application.FORMATS.get(pattern).format(v)));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, Float v) {
        this(click, model, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, Float v, String pattern) {
        this(click, model, v != null ? Model.of(Application.FORMATS.get(pattern).format(v)) : Model.of(""));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, String v) {
        this(click, model, v != null ? Model.of(v) : Model.of(""));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, Date v, String pattern) {
        this(click, model, v != null ? Model.of(DateFormatUtils.format(v, pattern)) : Model.of(""));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, BigDecimal v) {
        this(click, model, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, BigDecimal v, String pattern) {
        this(click, model, v != null ? Model.of(Application.FORMATS.get(pattern).format(v)) : Model.of(""));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, BigInteger v) {
        this(click, model, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, BigInteger v, String pattern) {
        this(click, model, v != null ? Model.of(Application.FORMATS.get(pattern).format(v)) : Model.of(""));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, Character v) {
        this(click, model, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, Number v) {
        this(click, model, v != null ? Model.of(String.valueOf(v.doubleValue())) : Model.of(""));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, Number v, String pattern) {
        this(click, model, v != null ? Model.of(Application.FORMATS.get(pattern).format(v)) : Model.of(""));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, char v) {
        this(click, model, Model.of(String.valueOf(v)));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, Option v) {
        this(click, model, v != null && v.getText() != null && !"".equals(v.getText()) ? Model.of(String.valueOf(v.getText())) : Model.of(""));
    }

    public ClickableCell(WicketBiConsumer<Map<String, Object>, Link<Void>> click, Map<String, Object> model, IModel<?> v) {
        super(v);
        this.onClick = click;
        this.label = v;
        this.model = model;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        Link<Void> link = new Link<Void>("link") {

            @Override
            public void onClick() {
                onClick.accept(model, this);
            }

        };
        add(link);
        Label text = new Label("text", this.label);
        link.add(text);
    }
}
