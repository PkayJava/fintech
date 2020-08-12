package com.angkorteam.fintech.pages;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.dto.Function;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class LogoutPage extends MasterPage {

    @Override
    protected void onInitHtml(MarkupContainer markupContainer) {
        getSession().signOut();
        setResponsePage(LoginPage.class);
    }

}
