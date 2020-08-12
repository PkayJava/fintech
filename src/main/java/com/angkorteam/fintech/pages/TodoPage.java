package com.angkorteam.fintech.pages;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.boot.AppProperties;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.provider.ui.MemoryContentHeaderProvider;
import com.angkorteam.webui.frmk.common.WicketFactory;
import com.angkorteam.webui.frmk.model.Breadcrumb;
import com.angkorteam.webui.frmk.model.ContentHeader;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.springframework.context.ApplicationContext;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class TodoPage extends MasterPage {

    @Override
    protected void onInitData() {
        super.onInitData();
        ApplicationContext context = WicketFactory.getApplicationContext();
        AppProperties properties = context.getBean(AppProperties.class);
        this.contentHeaderProvider = new MemoryContentHeaderProvider(new ContentHeader("TODO", new Breadcrumb("TODO", TodoPage.class)));
    }

    @Override
    protected void onInitHtml(MarkupContainer body) {
    }

}
