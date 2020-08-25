package com.angkorteam.fintech.provider.ui;

import com.angkorteam.fintech.boot.AppProperties;
import com.angkorteam.fintech.factory.WebSession;
import com.angkorteam.webui.frmk.common.WicketFactory;
import com.angkorteam.webui.frmk.model.Footer;
import com.angkorteam.webui.frmk.provider.IFooterProvider;
import org.apache.wicket.markup.html.WebPage;
import org.springframework.context.ApplicationContext;

public class MemoryFooterProvider implements IFooterProvider {

    private final WebSession session;

    public MemoryFooterProvider(WebSession session) {
        this.session = session;
    }

    @Override
    public Footer fetchFooter() {
        ApplicationContext context = WicketFactory.getApplicationContext();
        AppProperties properties = context.getBean(AppProperties.class);
        return new Footer(properties.getName(), "3.0", (Class<? extends WebPage>) this.session.getApplication().getHomePage());
    }

}
