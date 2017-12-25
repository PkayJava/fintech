package com.angkorteam.fintech.pages.client.center;

import java.util.Arrays;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.widget.client.center.CenterPreviewGeneral;
import com.angkorteam.fintech.widget.client.center.CenterPreviewNote;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class CenterPreviewPage extends Page {

    protected AjaxTabbedPanel<ITab> tab;

    protected String centerId;

    @Override
    protected void initData() {
        PageParameters parameters = getPageParameters();
        this.centerId = parameters.get("centerId").toString();
    }

    @Override
    protected void initComponent() {
        this.tab = new AjaxTabbedPanel<>("tab", Arrays.asList(new CenterPreviewGeneral(this), new CenterPreviewNote(this)));
        add(this.tab);
    }

    @Override
    protected void configureRequiredValidation() {

    }

    @Override
    protected void configureMetaData() {

    }

}
