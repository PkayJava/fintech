package com.angkorteam.fintech.table;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;

/**
 * Created by socheatkhauv on 6/17/17.
 */
public class TextCell extends ItemPanel {

    private IModel<?> model;

    public TextCell(byte v) {
        super(Model.of(String.valueOf(v)));
    }

    public TextCell(Byte v) {
        super(v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public TextCell(boolean v) {
        super(Model.of(String.valueOf(v)));
    }

    public TextCell(Boolean v) {
        super(v != null && v ? Model.of("Yes") : Model.of("No"));
    }

    public TextCell(short v) {
        super(Model.of(String.valueOf(v)));
    }

    public TextCell(Short v) {
        super(v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public TextCell(int v) {
        super(Model.of(String.valueOf(v)));
    }

    public TextCell(Integer v) {
        super(v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public TextCell(long v) {
        super(Model.of(String.valueOf(v)));
    }

    public TextCell(Long v) {
        super(v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public TextCell(double v) {
        super(Model.of(String.valueOf(v)));
    }

    public TextCell(Double v) {
        super(v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public TextCell(float v) {
        super(Model.of(String.valueOf(v)));
    }

    public TextCell(Float v) {
        super(v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public TextCell(String v) {
        super(v != null ? Model.of(v) : Model.of(""));
    }

    public TextCell(Date v, String pattern) {
        super(v != null ? Model.of(DateFormatUtils.format(v, pattern)) : Model.of(""));
    }

    public TextCell(BigDecimal v) {
        super(v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public TextCell(BigInteger v) {
        super(v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public TextCell(Character v) {
        super(v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public TextCell(char v) {
        super(Model.of(String.valueOf(v)));
    }
    
    public TextCell(Number v) {
        super(v != null ? Model.of(String.valueOf(v.doubleValue())) : Model.of(""));
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
