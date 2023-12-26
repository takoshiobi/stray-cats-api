package ru.sds.straycats.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "rabbitmq.main.enable", havingValue = "true")
@AllArgsConstructor
public class PurchaseProducerImpl implements PurchaseProducer {

    private final AmqpTemplate template;

    public void sendPurchaseResult(Long purchaseId, PurchaseStatus status, String description) {
        PurchaseResult result = PurchaseResult.builder()
                .purchaseId(purchaseId)
                .status(status)
                .description(description)
                .build();
        template.convertAndSend("purchase-cat-response-mq", result);
    }

    @Data
    @Builder
    public static class PurchaseResult {
        private Long purchaseId;
        private PurchaseStatus status;
        private String description;
    }
}
