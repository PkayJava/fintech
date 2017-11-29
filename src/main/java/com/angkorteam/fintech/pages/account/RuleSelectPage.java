package com.angkorteam.fintech.pages.account;

import java.util.List;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.pages.AccountingPage;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
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

    protected WebMarkupBlock ruleBlock;
    protected WebMarkupContainer ruleIContainer;
    protected SingleChoiceProvider ruleProvider;
    protected Option ruleValue;
    protected Select2SingleChoice<Option> ruleField;
    protected TextFeedbackPanel ruleFeedback;

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
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.nextButton = new Button("nextButton");
        this.nextButton.setOnSubmit(this::nextButtonClick);
        this.form.add(this.nextButton);

        initRuleBlock();
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected void initRuleBlock() {
        this.ruleBlock = new WebMarkupBlock("ruleBlock", Size.Twelve_12);
        this.form.add(this.ruleBlock);
        this.ruleIContainer = new WebMarkupContainer("ruleIContainer");
        this.ruleBlock.add(this.ruleIContainer);
        this.ruleProvider = new SingleChoiceProvider("acc_accounting_rule", "id", "name");
        this.ruleField = new Select2SingleChoice<>("ruleField", new PropertyModel<>(this, "ruleValue"), this.ruleProvider);
        this.ruleField.setRequired(true);
        this.ruleIContainer.add(this.ruleField);
        this.ruleFeedback = new TextFeedbackPanel("ruleFeedback", this.ruleField);
        this.ruleIContainer.add(this.ruleFeedback);
    }

    protected void nextButtonClick(Button button) {
        PageParameters parameters = new PageParameters();
        parameters.add("ruleId", this.ruleValue.getId());
        setResponsePage(FrequentPostPage.class, parameters);
    }

}
