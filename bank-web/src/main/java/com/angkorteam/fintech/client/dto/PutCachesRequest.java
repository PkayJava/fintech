package com.angkorteam.fintech.client.dto;

import com.angkorteam.fintech.client.enums.CacheType;

public class PutCachesRequest {

    private CacheType cacheType;

    public CacheType getCacheType() {
        return cacheType;
    }

    public void setCacheType(CacheType cacheType) {
        this.cacheType = cacheType;
    }

}
