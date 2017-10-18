package com.angkorteam.fintech;

import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.angkorteam.framework.models.UserInfo;
import com.angkorteam.framework.panels.UserInfoPanel;

/**
 * Created by socheatkhauv on 6/17/17.
 */
public class TabA implements ITab {

    @Override
    public IModel<String> getTitle() {
        return Model.of("Test");
    }

    @Override
    public WebMarkupContainer getPanel(String containerId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setDescription("ss");
        userInfo.setTitle("sdsds");
        return new UserInfoPanel(containerId, Model.of(userInfo));
    }

    @Override
    public boolean isVisible() {
        return true;
    }
}
