package com.angkorteam.fintech.pages.client.center;

import java.util.Arrays;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.DeprecatedPage;
import com.angkorteam.fintech.DeprecatedPage;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.widget.center.CenterPreviewGeneral;
import com.angkorteam.fintech.widget.center.CenterPreviewNote;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class CenterPreviewPage extends DeprecatedPage {

    private AjaxTabbedPanel<ITab> tab;

    protected String centerId;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        PageParameters parameters = getPageParameters();

        this.centerId = parameters.get("centerId").toString();

        this.tab = new AjaxTabbedPanel<>("tab", Arrays.asList(new CenterPreviewGeneral(this), new CenterPreviewNote(this)));

        add(this.tab);
    }

}
