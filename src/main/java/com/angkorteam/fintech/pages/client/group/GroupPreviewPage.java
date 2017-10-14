package com.angkorteam.fintech.pages.client.group;

import java.util.Arrays;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.widget.group.GroupPreviewCommittee;
import com.angkorteam.fintech.widget.group.GroupPreviewGeneral;
import com.angkorteam.fintech.widget.group.GroupPreviewNote;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class GroupPreviewPage extends Page {

    private AjaxTabbedPanel<ITab> tab;

    protected String groupId;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        PageParameters parameters = getPageParameters();

        this.groupId = parameters.get("groupId").toString();

        this.tab = new AjaxTabbedPanel<>("tab", Arrays.asList(new GroupPreviewGeneral(this), new GroupPreviewNote(this), new GroupPreviewCommittee(this)));

        add(this.tab);
    }

}
