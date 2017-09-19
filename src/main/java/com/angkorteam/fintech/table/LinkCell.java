package com.angkorteam.fintech.table;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;

/**
 * Created by socheatkhauv on 6/17/17.
 */
public class LinkCell extends ItemPanel {

    private Class<? extends Page> page;

    private PageParameters parameters;

    private IModel<?> model;

    public LinkCell(Class<? extends Page> page, PageParameters parameters, byte v) {
        this(page, parameters, Model.of(String.valueOf(v)));
    }

    public LinkCell(Class<? extends Page> page, PageParameters parameters, Byte v) {
        this(page, parameters, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public LinkCell(Class<? extends Page> page, PageParameters parameters, boolean v) {
        this(page, parameters, Model.of(String.valueOf(v)));
    }

    public LinkCell(Class<? extends Page> page, PageParameters parameters, Boolean v) {
        this(page, parameters, v != null && v ? Model.of("Yes") : Model.of("No"));
    }

    public LinkCell(Class<? extends Page> page, PageParameters parameters, short v) {
        this(page, parameters, Model.of(String.valueOf(v)));
    }

    public LinkCell(Class<? extends Page> page, PageParameters parameters, Short v) {
        this(page, parameters, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public LinkCell(Class<? extends Page> page, PageParameters parameters, int v) {
        this(page, parameters, Model.of(String.valueOf(v)));
    }

    public LinkCell(Class<? extends Page> page, PageParameters parameters, Integer v) {
        this(page, parameters, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public LinkCell(Class<? extends Page> page, PageParameters parameters, long v) {
        this(page, parameters, Model.of(String.valueOf(v)));
    }

    public LinkCell(Class<? extends Page> page, PageParameters parameters, Long v) {
        this(page, parameters, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public LinkCell(Class<? extends Page> page, PageParameters parameters, double v) {
        this(page, parameters, Model.of(String.valueOf(v)));
    }

    public LinkCell(Class<? extends Page> page, PageParameters parameters, Double v) {
        this(page, parameters, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public LinkCell(Class<? extends Page> page, PageParameters parameters, float v) {
        this(page, parameters, Model.of(String.valueOf(v)));
    }

    public LinkCell(Class<? extends Page> page, PageParameters parameters, Float v) {
        this(page, parameters, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public LinkCell(Class<? extends Page> page, PageParameters parameters, String v) {
        this(page, parameters, v != null ? Model.of(v) : Model.of(""));
    }

    public LinkCell(Class<? extends Page> page, PageParameters parameters, Date v, String pattern) {
        this(page, parameters, v != null ? Model.of(DateFormatUtils.format(v, pattern)) : Model.of(""));
    }

    public LinkCell(Class<? extends Page> page, PageParameters parameters, BigDecimal v) {
        this(page, parameters, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public LinkCell(Class<? extends Page> page, PageParameters parameters, BigInteger v) {
        this(page, parameters, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public LinkCell(Class<? extends Page> page, PageParameters parameters, Character v) {
        this(page, parameters, v != null ? Model.of(String.valueOf(v)) : Model.of(""));
    }

    public LinkCell(Class<? extends Page> page, PageParameters parameters, Number v) {
        this(page, parameters, v != null ? Model.of(String.valueOf(v.doubleValue())) : Model.of(""));
    }

    public LinkCell(Class<? extends Page> page, PageParameters parameters, char v) {
        this(page, parameters, Model.of(String.valueOf(v)));
    }

    public LinkCell(Class<? extends Page> page, PageParameters parameters, IModel<?> model) {
        super(model);
        this.page = page;
        this.model = model;
        this.parameters = parameters;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        BookmarkablePageLink<Void> link = new BookmarkablePageLink<Void>("link", this.page, this.parameters);
        add(link);
        Label text = new Label("text", this.model);
        link.add(text);
    }
}
