package com.angkorteam.fintech.client.dto;

import java.util.Map;

public class FineractResponse {

    private Long commandId;
    private Long officeId;
    private Long groupId;
    private Long clientId;
    private Long loanId;
    private Long savingsId;
    private Long resourceId;
    private Long subResourceId;
    private String transactionId;
    private Map<String, Object> changes;
    private String resourceIdentifier;
    private Long productId;
    private Long gsimId;
    private Long glimId;
    private Boolean rollbackTransaction;

    public Long getCommandId() {
        return commandId;
    }

    public void setCommandId(Long commandId) {
        this.commandId = commandId;
    }

    public Long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Long officeId) {
        this.officeId = officeId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public Long getSavingsId() {
        return savingsId;
    }

    public void setSavingsId(Long savingsId) {
        this.savingsId = savingsId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Long getSubResourceId() {
        return subResourceId;
    }

    public void setSubResourceId(Long subResourceId) {
        this.subResourceId = subResourceId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Map<String, Object> getChanges() {
        return changes;
    }

    public void setChanges(Map<String, Object> changes) {
        this.changes = changes;
    }

    public String getResourceIdentifier() {
        return resourceIdentifier;
    }

    public void setResourceIdentifier(String resourceIdentifier) {
        this.resourceIdentifier = resourceIdentifier;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getGsimId() {
        return gsimId;
    }

    public void setGsimId(Long gsimId) {
        this.gsimId = gsimId;
    }

    public Long getGlimId() {
        return glimId;
    }

    public void setGlimId(Long glimId) {
        this.glimId = glimId;
    }

    public Boolean getRollbackTransaction() {
        return rollbackTransaction;
    }

    public void setRollbackTransaction(Boolean rollbackTransaction) {
        this.rollbackTransaction = rollbackTransaction;
    }

}
