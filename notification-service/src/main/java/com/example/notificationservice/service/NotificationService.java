package com.example.notificationservice.service;

import com.example.avro.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {

    @KafkaListener(topics = "${notification.topic}")
    public void consumeNotification(@Payload NotificationEvent event,
                                    @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                    @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                                    @Header(KafkaHeaders.OFFSET) long offset,
                                    Acknowledgment acknowledgment) {
        try {
            log.info("Received notification from topic: {}, partition: {}, offset: {}",
                    topic, partition, offset);

            // Print the received event details
            log.info("Event ID: {}", event.getEventId());
            log.info("Timestamp: {}", event.getTimestamp());
            log.info("Event Source: {}", event.getEventSource());
            log.info("Priority: {}", event.getPriority());
            log.info("Category: {}", event.getCategory());
            log.info("Channels: {}", event.getChannels());
            log.info("Language: {}", event.getLanguage());
            log.info("Content: {}", event.getContent());

            event.getReceiver().forEach(receiver -> {
                if (receiver.getEmailReceiver() != null) {
                    EmailReceiver email = receiver.getEmailReceiver();
                    log.info("Email - To: {}, CC: {}, BCC: {}",
                            email.getTo(), email.getCc(), email.getBcc());
                }
                if (receiver.getSmsReceiver() != null) {
                    log.info("SMS - Phone: {}", receiver.getSmsReceiver().getPhoneNumber());
                }
                if (receiver.getWhatsappReceiver() != null) {
                    log.info("WhatsApp - Phone: {}", receiver.getWhatsappReceiver().getPhoneNumber());
                }
                if (receiver.getPushReceiver() != null) {
                    log.info("Push - App User ID: {}", receiver.getPushReceiver().getAppUserId());
                }
            });


            event.getNotificationChannels().forEach(channel -> {
                log.info("Channel: {}, Sender: {}, Body: {}",
                        channel.getChannel(),
                        channel.getSender(),
                        channel.getOverridingBody() != null ? channel.getOverridingBody() : channel.getBody());
            });


            // Acknowledge the message
            acknowledgment.acknowledge();
            log.info("Message processing completed successfully");

        } catch (Exception e) {
            log.error("Error processing notification: ", e);
            acknowledgment.acknowledge();
        }
    }
}