package com.angkorteam.fintech.table;

import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Created by socheatkhauv on 6/17/17.
 */
public class LinkCell extends ItemPanel {

    private Class<? extends Page> page;

    private PageParameters parameters;

    public LinkCell(Class<? extends Page> page, PageParameters parameters, IModel<String> model) {
        super(model);
        this.page = page;
        this.parameters = parameters;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        BookmarkablePageLink<Void> link = new BookmarkablePageLink<Void>("link", this.page, this.parameters);
        add(link);
        String object = (String) getDefaultModelObject();
        Label text = new Label("text", object);
        link.add(text);
    }
}
