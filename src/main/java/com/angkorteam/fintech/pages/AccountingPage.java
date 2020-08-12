package com.angkorteam.fintech.pages;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.dto.Function;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class AccountingPage extends MasterPage {

    private BookmarkablePageLink<Void> frequentPostingsLink;

    private BookmarkablePageLink<Void> chartAccountsLink;

    private BookmarkablePageLink<Void> addJournalEntriesLink;

    private BookmarkablePageLink<Void> closingEntriesLink;

    private BookmarkablePageLink<Void> searchJournalEntriesLink;

    private BookmarkablePageLink<Void> accountingRulesLink;

    private BookmarkablePageLink<Void> accountsLinkedToFinancialActivitiesLink;

    private BookmarkablePageLink<Void> accrualsLink;

    private BookmarkablePageLink<Void> migrateOpeningBalancesLink;

    private BookmarkablePageLink<Void> provisioningEntriesLink;

    @Override
    protected void onInitHtml(MarkupContainer body) {
        this.frequentPostingsLink = new BookmarkablePageLink<>("frequentPostingsLink", TodoPage.class);
        body.add(this.frequentPostingsLink);

        this.chartAccountsLink = new BookmarkablePageLink<>("chartAccountsLink", TodoPage.class);
        body.add(this.chartAccountsLink);

        this.addJournalEntriesLink = new BookmarkablePageLink<>("addJournalEntriesLink", TodoPage.class);
        body.add(this.addJournalEntriesLink);

        this.closingEntriesLink = new BookmarkablePageLink<>("closingEntriesLink", TodoPage.class);
        body.add(this.closingEntriesLink);

        this.searchJournalEntriesLink = new BookmarkablePageLink<>("searchJournalEntriesLink", TodoPage.class);
        body.add(this.searchJournalEntriesLink);

        this.accountingRulesLink = new BookmarkablePageLink<>("accountingRulesLink", TodoPage.class);
        body.add(this.accountingRulesLink);

        this.accountsLinkedToFinancialActivitiesLink = new BookmarkablePageLink<>("accountsLinkedToFinancialActivitiesLink", TodoPage.class);
        body.add(this.accountsLinkedToFinancialActivitiesLink);

        this.accrualsLink = new BookmarkablePageLink<>("accrualsLink", TodoPage.class);
        body.add(this.accrualsLink);

        this.migrateOpeningBalancesLink = new BookmarkablePageLink<>("migrateOpeningBalancesLink", TodoPage.class);
        body.add(this.migrateOpeningBalancesLink);

        this.provisioningEntriesLink = new BookmarkablePageLink<>("provisioningEntriesLink", TodoPage.class);
        body.add(this.provisioningEntriesLink);
    }

}
