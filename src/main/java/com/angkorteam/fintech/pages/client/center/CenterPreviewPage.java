package com.angkorteam.fintech.pages.client.center;

import java.util.Arrays;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.widget.center.CenterGeneral;
import com.angkorteam.fintech.widget.center.CenterNote;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class CenterPreviewPage extends Page {

    private AjaxTabbedPanel<ITab> tab;

    protected String centerId;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        PageParameters parameters = getPageParameters();

        this.centerId = parameters.get("centerId").toString();

        this.tab = new AjaxTabbedPanel<>("tab", Arrays.asList(new CenterGeneral(this), new CenterNote(this)));

        add(this.tab);
    }

}
