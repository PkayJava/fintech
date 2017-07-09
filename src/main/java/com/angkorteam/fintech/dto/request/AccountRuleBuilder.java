package com.angkorteam.fintech.dto.request;

import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;

import java.io.Serializable;
import java.util.List;

/**
 * Created by socheatkhauv on 7/3/17.
 */
public class AccountRuleBuilder implements Serializable {

    private String id;
    private boolean hasId;

    private String name;
    private boolean hasName;

    private String officeId;
    private boolean hasOfficeId;

    private String description;
    private boolean hasDescription;

    private String accountToCredit;
    private boolean hasAccountToCredit;

    private List<String> creditTags = Lists.newArrayList();
    private boolean hasCreditTags;

    private boolean allowMultipleCreditEntries;
    private boolean hasAllowMultipleCreditEntries;

    private String accountToDebit;
    private boolean hasAccountToDebit;

    private List<String> debitTags = Lists.newArrayList();
    private boolean hasDebitTags;

    private boolean allowMultipleDebitEntries;
    private boolean hasAllowMultipleDebitEntries;

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();
        if (this.hasAccountToCredit) {
            object.getObject().put("accountToCredit", this.accountToCredit);
        }
        if (this.hasCreditTags) {
            object.getObject().put("creditTags", this.creditTags);
        }
        if (this.hasAllowMultipleCreditEntries) {
            object.getObject().put("allowMultipleCreditEntries", this.allowMultipleCreditEntries);
        }
        if (this.hasAccountToDebit) {
            object.getObject().put("accountToDebit", this.accountToDebit);
        }
        if (this.hasDebitTags) {
            object.getObject().put("debitTags", this.debitTags);
        }
        if (this.hasAllowMultipleDebitEntries) {
            object.getObject().put("allowMultipleDebitEntries", this.allowMultipleDebitEntries);
        }
        if (this.hasName) {
            object.getObject().put("name", this.name);
        }
        if (this.hasOfficeId) {
            object.getObject().put("officeId", this.officeId);
        }
        if (this.hasDescription) {
            object.getObject().put("description", this.description);
        }
        if (this.hasId) {
            object.getObject().put("id", this.id);
        }
        return object;
    }

    public AccountRuleBuilder withName(String name) {
        this.name = name;
        this.hasName = true;
        return this;
    }

    public AccountRuleBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    public AccountRuleBuilder withOfficeId(String officeId) {
        this.officeId = officeId;
        this.hasOfficeId = true;
        return this;
    }

    public AccountRuleBuilder withDescription(String description) {
        this.description = description;
        this.hasDescription = true;
        return this;
    }

    public AccountRuleBuilder withAccountToCredit(String accountToCredit) {
        this.accountToCredit = accountToCredit;
        this.hasAccountToCredit = true;
        this.hasCreditTags = false;
        this.hasAllowMultipleCreditEntries = false;
        return this;
    }

    public AccountRuleBuilder withCreditTags(String tag) {
        this.creditTags.add(tag);
        this.hasCreditTags = true;
        this.hasAccountToCredit = false;
        return this;
    }

    public AccountRuleBuilder withAllowMultipleCreditEntries(boolean allowMultipleCreditEntries) {
        this.allowMultipleCreditEntries = allowMultipleCreditEntries;
        this.hasAllowMultipleCreditEntries = true;
        this.hasAccountToCredit = false;
        return this;
    }

    public AccountRuleBuilder withAccountToDebit(String accountToDebit) {
        this.accountToDebit = accountToDebit;
        this.hasAccountToDebit = true;
        this.hasDebitTags = false;
        this.hasAllowMultipleDebitEntries = false;
        return this;
    }

    public AccountRuleBuilder withDebitTags(String tag) {
        this.debitTags.add(tag);
        this.hasDebitTags = true;
        this.hasAccountToDebit = false;
        return this;
    }

    public AccountRuleBuilder withAllowMultipleDebitEntries(boolean allowMultipleDebitEntries) {
        this.allowMultipleDebitEntries = allowMultipleDebitEntries;
        this.hasAllowMultipleDebitEntries = true;
        this.hasAccountToDebit = false;
        return this;
    }

}
