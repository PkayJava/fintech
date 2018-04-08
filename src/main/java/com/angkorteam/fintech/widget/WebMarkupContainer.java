package com.angkorteam.fintech.widget;

import org.apache.wicket.model.IModel;

@Deprecated
public class WebMarkupContainer extends org.apache.wicket.markup.html.WebMarkupContainer {

    public WebMarkupContainer(String id, IModel<?> model) {
        super(id, model);
    }

    public WebMarkupContainer(String id) {
        super(id);
    }

}
