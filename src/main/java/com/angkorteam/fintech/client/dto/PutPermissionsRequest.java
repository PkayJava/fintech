package com.angkorteam.fintech.client.dto;

import java.util.List;

public class PutPermissionsRequest {

    private List<String> permissions;

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

}
