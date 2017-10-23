package com.angkorteam.fintech.pages;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.framework.wicket.ajax.markup.html.AjaxLink;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class IndexPage extends Page {

    private Option test;

    private Label pp;

    private AjaxLink<Void> ss;

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.test = new Option("ss", "sssssssssssa");
        this.pp = new Label("pp", new PropertyModel<>(this.test, "text"));
        this.pp.setOutputMarkupId(true);
        add(this.pp);

        this.ss = new AjaxLink<>("ss");
        this.ss.setOnClick(this::ssOnClick);
        this.add(this.ss);
    }

    protected boolean ssOnClick(AjaxLink<Void> link, AjaxRequestTarget target) {
        // this.test = new Option("ss", "vvvvvvvvvvv");
        this.test.setText("ssssssssssssssss");
        target.add(this.pp);
        return false;
    }

}
