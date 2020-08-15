package com.angkorteam.fintech.pages.admin;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.client.Function;
import com.angkorteam.fintech.pages.TodoPage;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ProductsPage extends MasterPage {

    private BookmarkablePageLink<Void> loanProductsLink;

    private BookmarkablePageLink<Void> productsMixLink;

    private BookmarkablePageLink<Void> savingsProductsLink;

    private BookmarkablePageLink<Void> fixedDepositProductsLink;

    private BookmarkablePageLink<Void> shareProductsLink;

    private BookmarkablePageLink<Void> recurringDepositProductsLink;

    private BookmarkablePageLink<Void> chargesLink;

    private BookmarkablePageLink<Void> manageTaxConfigurationsLink;

    private BookmarkablePageLink<Void> floatingRatesLink;

    @Override
    protected void onInitHtml(MarkupContainer body) {
        this.loanProductsLink = new BookmarkablePageLink<>("loanProductsLink", TodoPage.class);
        body.add(this.loanProductsLink);

        this.productsMixLink = new BookmarkablePageLink<>("productsMixLink", TodoPage.class);
        body.add(this.productsMixLink);

        this.savingsProductsLink = new BookmarkablePageLink<>("savingsProductsLink", TodoPage.class);
        body.add(this.savingsProductsLink);

        this.fixedDepositProductsLink = new BookmarkablePageLink<>("fixedDepositProductsLink", TodoPage.class);
        body.add(this.fixedDepositProductsLink);

        this.shareProductsLink = new BookmarkablePageLink<>("shareProductsLink", TodoPage.class);
        body.add(this.shareProductsLink);

        this.recurringDepositProductsLink = new BookmarkablePageLink<>("recurringDepositProductsLink", TodoPage.class);
        body.add(this.recurringDepositProductsLink);

        this.chargesLink = new BookmarkablePageLink<>("chargesLink", TodoPage.class);
        body.add(this.chargesLink);

        this.manageTaxConfigurationsLink = new BookmarkablePageLink<>("manageTaxConfigurationsLink", TodoPage.class);
        body.add(this.manageTaxConfigurationsLink);

        this.floatingRatesLink = new BookmarkablePageLink<>("floatingRatesLink", TodoPage.class);
        body.add(this.floatingRatesLink);
    }

}
