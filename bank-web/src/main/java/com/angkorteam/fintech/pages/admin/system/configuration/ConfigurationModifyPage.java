package com.angkorteam.fintech.pages.admin.system.configuration;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.client.Function;
import com.angkorteam.webui.frmk.common.Bookmark;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
@Bookmark("/admin/system/configuration/modify")
public class ConfigurationModifyPage extends MasterPage {

    @Override
    protected void onInitHtml(MarkupContainer markupContainer) {

    }
    
}
