package com.angkorteam.fintech.pages;

import com.angkorteam.fintech.dto.Function;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.Model;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.pages.account.AccountBrowsePage;
import com.angkorteam.fintech.pages.account.AccrualAccountingPage;
import com.angkorteam.fintech.pages.account.ClosureBrowsePage;
import com.angkorteam.fintech.pages.account.FinancialActivityBrowsePage;
import com.angkorteam.fintech.pages.account.JournalPostPage;
import com.angkorteam.fintech.pages.account.RuleBrowsePage;
import com.angkorteam.fintech.pages.account.RuleSelectPage;
import com.angkorteam.fintech.pages.account.SearchJournalPage;
import com.angkorteam.framework.BackgroundColor;
import com.angkorteam.framework.Emoji;
import com.angkorteam.framework.models.InfoBox;
import com.angkorteam.framework.panels.InfoBoxPanel;

/**
 * Created by socheatkhauv on 6/27/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class AccountingPage extends Page {

    @Override
    protected void onInitialize() {
        super.onInitialize();

        InfoBoxPanel frequentPostingPage = new InfoBoxPanel("frequentPostingPage",
                Model.of(new InfoBox().setPage(RuleSelectPage.class).setTitle("Frequent Postings")
                        .setDescription("These are predefined postings").setBackgroundColor(BackgroundColor.AquaActive)
                        .setIcon(Emoji.ion_alert)));
        add(frequentPostingPage);

        InfoBoxPanel chartOfAccountPage = new InfoBoxPanel("chartOfAccountPage",
                Model.of(new InfoBox().setPage(AccountBrowsePage.class).setTitle("Chart of Accounts")
                        .setDescription("List of the accounts used by the organization")
                        .setBackgroundColor(BackgroundColor.AquaActive).setIcon(Emoji.ion_alert)));
        add(chartOfAccountPage);

        InfoBoxPanel addJournalEntryPage = new InfoBoxPanel("addJournalEntryPage",
                Model.of(new InfoBox().setPage(JournalPostPage.class).setTitle("Add Journal Entries")
                        .setDescription("Manual journal entry transactions recorded in a journal")
                        .setBackgroundColor(BackgroundColor.AquaActive).setIcon(Emoji.ion_alert)));
        add(addJournalEntryPage);

        InfoBoxPanel closingEntryPage = new InfoBoxPanel("closingEntryPage",
                Model.of(new InfoBox().setPage(ClosureBrowsePage.class).setTitle("Closing Entries")
                        .setDescription("Journal entries made at the end of an accounting period")
                        .setBackgroundColor(BackgroundColor.AquaActive).setIcon(Emoji.ion_alert)));
        add(closingEntryPage);

        InfoBoxPanel searchJournalEntryPage = new InfoBoxPanel("searchJournalEntryPage",
                Model.of(new InfoBox().setPage(SearchJournalPage.class).setTitle("Search Journal Entries")
                        .setDescription("Advance search option for journal entries")
                        .setBackgroundColor(BackgroundColor.AquaActive).setIcon(Emoji.ion_alert)));
        add(searchJournalEntryPage);

        InfoBoxPanel accountingRulePage = new InfoBoxPanel("accountingRulePage",
                Model.of(new InfoBox().setPage(RuleBrowsePage.class).setTitle("Accounting Rules")
                        .setDescription("Lists all accounting rules").setBackgroundColor(BackgroundColor.AquaActive)
                        .setIcon(Emoji.ion_alert)));
        add(accountingRulePage);

        InfoBoxPanel accountLinkedPage = new InfoBoxPanel("accountLinkedPage",
                Model.of(new InfoBox().setPage(FinancialActivityBrowsePage.class)
                        .setTitle("Accounts Linked to Financial Activities")
                        .setDescription("List of Financial Activity and GL Account Mappings")
                        .setBackgroundColor(BackgroundColor.AquaActive).setIcon(Emoji.ion_alert)));
        add(accountLinkedPage);

        InfoBoxPanel accrualsPage = new InfoBoxPanel("accrualsPage",
                Model.of(new InfoBox().setPage(AccrualAccountingPage.class).setTitle("Accruals")
                        .setDescription("Accrues income, expenses, and liabilities as on the provided date")
                        .setBackgroundColor(BackgroundColor.AquaActive).setIcon(Emoji.ion_alert)));
        add(accrualsPage);

        InfoBoxPanel migrateOpeningPage = new InfoBoxPanel("migrateOpeningPage",
                Model.of(new InfoBox().setPage(IndexPage.class).setTitle("Migrate Opening Balances (Office-wise)")
                        .setDescription("Set or update office-level opening balances for GL Accounts")
                        .setIcon(Emoji.ion_alert)));
        add(migrateOpeningPage);

        InfoBoxPanel provisionEntryPage = new InfoBoxPanel("provisionEntryPage",
                Model.of(new InfoBox().setPage(IndexPage.class).setTitle("Provisioning Entries")
                        .setDescription("Create Provision Entries").setIcon(Emoji.ion_alert)));
        add(provisionEntryPage);

    }
}
