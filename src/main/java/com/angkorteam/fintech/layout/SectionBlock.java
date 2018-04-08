package com.angkorteam.fintech.layout;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebMarkupContainer;

public class SectionBlock extends WebMarkupContainer {

    protected SectionBlock(String id) {
        super(id);
        setOutputMarkupId(true);
    }

    public static SectionBlock newSectionBlock(String id, WebMarkupContainer parent) {
        SectionBlock block = new SectionBlock(id);
        parent.add(block);
        return block;
    }

    public static SectionBlock newSectionBlock(String id, Page parent) {
        SectionBlock block = new SectionBlock(id);
        parent.add(block);
        return block;
    }

    public SectionContainer newSectionContainer(String id) {
        SectionContainer container = new SectionContainer(id);
        add(container);
        return container;
    }

}
