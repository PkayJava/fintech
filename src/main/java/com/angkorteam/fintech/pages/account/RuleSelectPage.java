package com.angkorteam.fintech.pages.account;

import java.util.List;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.ddl.AccAccountingRule;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.AccountingPage;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;

/**
 * Created by socheatkhauv on 6/11/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class RuleSelectPage extends Page {

    protected static final Logger LOGGER = LoggerFactory.getLogger(RuleSelectPage.class);

    protected Button nextButton;
    protected Form<Void> form;

    protected UIRow row1;

    protected UIBlock ruleBlock;
    protected UIContainer ruleIContainer;
    protected SingleChoiceProvider ruleProvider;
    protected Option ruleValue;
    protected Select2SingleChoice<Option> ruleField;

    protected UIBlock row1Block1;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Accounting");
            breadcrumb.setPage(AccountingPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Accounting Rule Selection");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initData() {
        this.ruleProvider = new SingleChoiceProvider(AccAccountingRule.NAME, AccAccountingRule.Field.ID, AccAccountingRule.Field.NAME);
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.ruleBlock = this.row1.newUIBlock("ruleBlock", Size.Four_4);
        this.ruleIContainer = this.ruleBlock.newUIContainer("ruleIContainer");
        this.ruleField = new Select2SingleChoice<>("ruleField", new PropertyModel<>(this, "ruleValue"), this.ruleProvider);
        this.ruleIContainer.add(this.ruleField);
        this.ruleIContainer.newFeedback("ruleFeedback", this.ruleField);

        this.row1Block1 = this.row1.newUIBlock("row1Block1", Size.Eight_8);

        this.nextButton = new Button("nextButton");
        this.nextButton.setOnSubmit(this::nextButtonClick);
        this.form.add(this.nextButton);
    }

    @Override
    protected void configureMetaData() {
        this.ruleField.setRequired(true);
    }

    protected void nextButtonClick(Button button) {
        PageParameters parameters = new PageParameters();
        parameters.add("ruleId", this.ruleValue.getId());
        setResponsePage(FrequentPostPage.class, parameters);
    }

}
