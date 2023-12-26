package ru.sds.straycats.rabbitmq;

public interface PurchaseProducer {

    public enum PurchaseStatus {
        ERROR,
        SUCCESS
    }

    void sendPurchaseResult(Long purchaseId, PurchaseStatus status, String description);
}
