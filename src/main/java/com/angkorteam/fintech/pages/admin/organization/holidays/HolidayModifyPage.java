package com.angkorteam.fintech.pages.admin.organization.holidays;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.client.Function;
import com.angkorteam.webui.frmk.common.Bookmark;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
@Bookmark("/admin/organization/holiday/modify")
public class HolidayModifyPage extends MasterPage {

    @Override
    protected void onInitHtml(MarkupContainer markupContainer) {

    }

}
