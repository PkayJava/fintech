package com.angkorteam.fintech.widget;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

public class WebMarkupBlock extends WebMarkupContainer {

    public WebMarkupBlock(String id) {
        super(id);
        setOutputMarkupId(true);
    }

    public WebMarkupBlock(String id, IModel<?> model) {
        super(id, model);
        setOutputMarkupId(true);
    }

}
