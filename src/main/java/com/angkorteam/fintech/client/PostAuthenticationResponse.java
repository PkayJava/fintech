package com.angkorteam.fintech.client;

import java.util.List;

public class PostAuthenticationResponse {

    private String username;

    private Long userId;

    private String base64EncodedAuthenticationKey;

    private boolean authenticated;

    private Long officeId;

    private String officeName;

    private Long staffId;

    private String staffDisplayName;

    private EnumOptionData organisationalRole;

    private List<RoleData> roles;

    private List<String> permissions;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBase64EncodedAuthenticationKey() {
        return base64EncodedAuthenticationKey;
    }

    public void setBase64EncodedAuthenticationKey(String base64EncodedAuthenticationKey) {
        this.base64EncodedAuthenticationKey = base64EncodedAuthenticationKey;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public Long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Long officeId) {
        this.officeId = officeId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getStaffDisplayName() {
        return staffDisplayName;
    }

    public void setStaffDisplayName(String staffDisplayName) {
        this.staffDisplayName = staffDisplayName;
    }

    public EnumOptionData getOrganisationalRole() {
        return organisationalRole;
    }

    public void setOrganisationalRole(EnumOptionData organisationalRole) {
        this.organisationalRole = organisationalRole;
    }

    public List<RoleData> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleData> roles) {
        this.roles = roles;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

}