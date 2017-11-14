package com.angkorteam.fintech.table;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.angkorteam.fintech.Application;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.google.common.collect.Maps;

/**
 * Created by socheatkhauv on 6/17/17.
 */
public class TextCell extends ItemPanel {

    private IModel<?> model;

    public TextCell(byte v) {
        this(Model.of(String.valueOf(v)));
    }

    public TextCell(byte v, String pattern) {
        this(Model.of(Application.FORMATS.get(pattern).format(v)));
    }

    public TextCell(Byte v) {
        this(v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public TextCell(Byte v, String pattern) {
        this(v != null ? Model.of(Application.FORMATS.get(pattern).format(v)) : Model.of(""));
    }

    public TextCell(boolean v) {
        this(Model.of(String.valueOf(v)));
    }

    public TextCell(Boolean v) {
        this(v != null && v ? Model.of("Yes") : Model.of("No"));
    }

    public TextCell(short v) {
        this(Model.of(String.valueOf(v)));
    }

    public TextCell(short v, String pattern) {
        this(Model.of(Application.FORMATS.get(pattern).format(v)));
    }

    public TextCell(Short v) {
        this(v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public TextCell(Short v, String pattern) {
        this(v != null ? Model.of(Application.FORMATS.get(pattern).format(v)) : Model.of(""));
    }

    public TextCell(int v) {
        this(Model.of(String.valueOf(v)));
    }

    public TextCell(int v, String pattern) {
        this(Model.of(Application.FORMATS.get(pattern).format(v)));
    }

    public TextCell(Integer v) {
        this(v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public TextCell(Integer v, String pattern) {
        this(v != null ? Model.of(Application.FORMATS.get(pattern).format(v)) : Model.of(""));
    }

    public TextCell(long v) {
        this(Model.of(String.valueOf(v)));
    }

    public TextCell(long v, String pattern) {
        this(Model.of(Application.FORMATS.get(pattern).format(v)));
    }

    public TextCell(Long v) {
        this(v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public TextCell(Long v, String pattern) {
        this(v != null ? Model.of(Application.FORMATS.get(pattern).format(v)) : Model.of(""));
    }

    public TextCell(double v) {
        this(Model.of(String.valueOf(v)));
    }

    public TextCell(double v, String pattern) {
        this(Model.of(Application.FORMATS.get(pattern).format(v)));
    }

    public TextCell(Double v) {
        this(v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public TextCell(Double v, String pattern) {
        this(v != null ? Model.of(Application.FORMATS.get(pattern).format(v)) : Model.of(""));
    }

    public TextCell(float v) {
        this(Model.of(String.valueOf(v)));
    }

    public TextCell(float v, String pattern) {
        this(Model.of(Application.FORMATS.get(pattern).format(v)));
    }

    public TextCell(Float v) {
        this(v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public TextCell(Float v, String pattern) {
        this(v != null ? Model.of(Application.FORMATS.get(pattern).format(v)) : Model.of(""));
    }

    public TextCell(String v) {
        this(v != null ? Model.of(v) : Model.of(""));
    }

    public TextCell(Date v, String pattern) {
        this(v != null ? Model.of(DateFormatUtils.format(v, pattern)) : Model.of(""));
    }

    public TextCell(BigDecimal v) {
        this(v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public TextCell(BigDecimal v, String pattern) {
        this(v != null ? Model.of(Application.FORMATS.get(pattern).format(v)) : Model.of(""));
    }

    public TextCell(BigInteger v) {
        this(v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public TextCell(BigInteger v, String pattern) {
        this(v != null ? Model.of(Application.FORMATS.get(pattern).format(v)) : Model.of(""));
    }

    public TextCell(Character v) {
        this(v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public TextCell(char v) {
        this(Model.of(String.valueOf(v)));
    }

    public TextCell(Option v) {
        this(v != null && v.getText() != null && !"".equals(v.getText()) ? Model.of(String.valueOf(v.getText())) : Model.of(""));
    }

    public TextCell(Number v) {
        this(v != null ? Model.of(String.valueOf(v.doubleValue())) : Model.of(""));
    }

    public TextCell(Number v, String pattern) {
        this(v != null ? Model.of(Application.FORMATS.get(pattern).format(v)) : Model.of(""));
    }

    public TextCell(IModel<?> model) {
        super(model);
        this.model = model;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        Label text = new Label("text", this.model);
        add(text);
    }
}
