package com.angkorteam.fintech.pages.product.saving;

import java.util.Map;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.spring.JdbcTemplate;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class SavingPreviewPage extends Page {

    private String savingId;

    // Detail

    protected WebMarkupContainer detailProductNameBlock;
    protected WebMarkupContainer detailProductNameContainer;
    protected String detailProductNameValue;
    protected Label detailProductNameView;

    protected WebMarkupContainer detailShortNameBlock;
    protected WebMarkupContainer detailShortNameContainer;
    protected String detailShortNameValue;
    protected Label detailShortNameView;

    protected WebMarkupContainer detailDescriptionBlock;
    protected WebMarkupContainer detailDescriptionContainer;
    protected String detailDescriptionValue;
    protected Label detailDescriptionView;

    @Override
    protected void initComponent() {
        this.detailProductNameBlock = new WebMarkupContainer("detailProductNameBlock");
        add(this.detailProductNameBlock);
        this.detailProductNameContainer = new WebMarkupContainer("detailProductNameContainer");
        this.detailProductNameBlock.add(this.detailProductNameContainer);
        this.detailProductNameView = new Label("detailProductNameView", new PropertyModel<>(this, "detailProductNameValue"));
        this.detailProductNameContainer.add(this.detailProductNameView);

        this.detailShortNameBlock = new WebMarkupContainer("detailShortNameBlock");
        add(this.detailShortNameBlock);
        this.detailShortNameContainer = new WebMarkupContainer("detailShortNameContainer");
        this.detailShortNameBlock.add(this.detailShortNameContainer);
        this.detailShortNameView = new Label("detailShortNameView", new PropertyModel<>(this, "detailShortNameValue"));
        this.detailShortNameContainer.add(this.detailShortNameView);

        this.detailDescriptionBlock = new WebMarkupContainer("detailDescriptionBlock");
        add(this.detailDescriptionBlock);
        this.detailDescriptionContainer = new WebMarkupContainer("detailDescriptionContainer");
        this.detailDescriptionBlock.add(this.detailDescriptionContainer);
        this.detailDescriptionView = new Label("detailDescriptionView", new PropertyModel<>(this, "detailDescriptionValue"));
        this.detailDescriptionContainer.add(this.detailDescriptionView);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    @Override
    protected void initData() {
        this.savingId = getPageParameters().get("savingId").toString();

        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);

        Map<String, Object> savingObject = jdbcTemplate.queryForMap("select name, short_name, description from m_savings_product where id = ?", this.savingId);
        this.detailProductNameValue = (String) savingObject.get("name");
        this.detailShortNameValue = (String) savingObject.get("short_name");
        this.detailDescriptionValue = (String) savingObject.get("description");

    }

}
