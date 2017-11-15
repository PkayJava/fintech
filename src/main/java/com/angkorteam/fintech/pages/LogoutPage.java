package com.angkorteam.fintech.pages;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class LogoutPage extends Page {

    @Override
    protected void initData() {
    }

    @Override
    protected void initComponent() {
        Session session = (Session) getSession();
        session.signOut();
        setResponsePage(LoginPage.class);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

}
