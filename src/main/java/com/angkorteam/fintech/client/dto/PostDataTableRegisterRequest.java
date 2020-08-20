package com.angkorteam.fintech.client.dto;

import com.angkorteam.fintech.client.renums.DataTableCategoryEnum;

public class PostDataTableRegisterRequest {

    private DataTableCategoryEnum category;

    public DataTableCategoryEnum getCategory() {
        return category;
    }

    public void setCategory(DataTableCategoryEnum category) {
        this.category = category;
    }

}
