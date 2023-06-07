package com.dungeonchaos.dungeonchaos.request;

public class InventoryRequest {
    private Long itemId;

    public Long getItemId() {
        return itemId;
    }

    public InventoryRequest() {}

    public InventoryRequest(Long itemId) {
        this.itemId = itemId;
    }
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
}
