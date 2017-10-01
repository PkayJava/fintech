package com.angkorteam.fintech.pages.client.center;

import java.util.Arrays;

import org.apache.wicket.extensions.markup.html.tabs.ITab;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.widget.center.CenterGeneral;
import com.angkorteam.fintech.widget.center.CenterNote;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;

public class CenterPreviewPage extends Page {

    private AjaxTabbedPanel<ITab> tab;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.tab = new AjaxTabbedPanel<>("tab", Arrays.asList(new CenterGeneral(), new CenterNote()));

        add(this.tab);
    }

}
