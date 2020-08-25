//package com.angkorteam.fintech.widget.client.common.saving;
//
//import org.apache.wicket.Page;
//import org.apache.wicket.markup.html.WebMarkupContainer;
//import org.apache.wicket.model.IModel;
//import org.apache.wicket.model.Model;
//
//import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
//
//public class Review extends ITab {
//
//    protected Page itemPage;
//
//    public Review(Page itemPage) {
//        this.itemPage = itemPage;
//    }
//
//    @Override
//    public IModel<String> getTitle() {
//        return Model.of("4. Review");
//    }
//
//    @Override
//    public WebMarkupContainer getPanel(String containerId) {
//        return new ReviewPanel(containerId, this.itemPage);
//    }
//
//    @Override
//    public boolean isVisible() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
//}
