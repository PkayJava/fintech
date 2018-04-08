package com.angkorteam.fintech.layout;

import org.apache.wicket.markup.html.WebMarkupContainer;

public class SectionContainer extends WebMarkupContainer {

    protected SectionContainer(String id) {
        super(id);
        setOutputMarkupId(true);
    }

    public UIRow newUIRow(String id) {
        UIRow row = new UIRow(id);
        add(row);
        return row;
    }

}
