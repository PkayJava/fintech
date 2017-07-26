package com.angkorteam.fintech.pages;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class LogoutPage extends Page {

    @Override
    protected void onInitialize() {
        super.onInitialize();
        Session session = (Session) getSession();
        session.signOut();
        setResponsePage(LoginPage.class);
    }

}
