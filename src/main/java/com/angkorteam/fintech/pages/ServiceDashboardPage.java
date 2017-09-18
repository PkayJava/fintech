package com.angkorteam.fintech.pages;

import java.util.List;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.pages.service.EMailConfigurationPage;
import com.angkorteam.fintech.pages.service.S3ConfigurationPage;
import com.angkorteam.fintech.pages.service.SMSConfigurationPage;
import com.angkorteam.framework.BackgroundColor;
import com.angkorteam.framework.Emoji;
import com.angkorteam.framework.models.InfoBox;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.panels.InfoBoxPanel;
import com.google.common.collect.Lists;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ServiceDashboardPage extends Page {

    private static final List<PageBreadcrumb> BREADCRUMB;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        return Model.ofList(BREADCRUMB);
    }

    static {
        BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Admin");
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("System");
            breadcrumb.setPage(SystemDashboardPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("External Service");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        InfoBoxPanel s3Page = new InfoBoxPanel("s3Page", Model.of(new InfoBox().setPage(S3ConfigurationPage.class).setTitle("Amazon S3").setDescription("Simple File Storage").setBackgroundColor(BackgroundColor.AquaActive).setIcon(Emoji.ion_alert)));
        add(s3Page);

        InfoBoxPanel mailPage = new InfoBoxPanel("mailPage", Model.of(new InfoBox().setPage(EMailConfigurationPage.class).setTitle("Mail").setDescription("Simple Mail Transfer Protocol").setBackgroundColor(BackgroundColor.AquaActive).setIcon(Emoji.ion_alert)));
        add(mailPage);

        InfoBoxPanel smsPage = new InfoBoxPanel("smsPage", Model.of(new InfoBox().setPage(SMSConfigurationPage.class).setTitle("SMS").setDescription("Short Message Service").setBackgroundColor(BackgroundColor.AquaActive).setIcon(Emoji.ion_alert)));
        add(smsPage);
    }
}
