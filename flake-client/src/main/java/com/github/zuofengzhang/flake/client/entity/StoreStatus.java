package com.github.zuofengzhang.flake.client.entity;

/**
 * @author averyzhang
 * @date 2021/2/21
 */
public enum StoreStatus {
    /**
     *
     */
    YES(1), NO(0);
    private int code;

    private StoreStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static StoreStatus findByCode(int code) {
        for (StoreStatus storeStatus : StoreStatus.values()) {
            if (code == storeStatus.code) {
                return storeStatus;
            }
        }
        return null;
    }
}
