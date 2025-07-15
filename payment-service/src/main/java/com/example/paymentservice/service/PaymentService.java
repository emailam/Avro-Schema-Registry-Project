package com.example.paymentservice.service;

import com.example.avro.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${notification.topic}")
    private String topic;

    public void sendNotification() {
        NotificationEvent event = createNotificationEvent();

        log.info("Sending notification event with ID: {}", event.getEventId());
        kafkaTemplate.send(topic, event.getEventId().toString(), event);
    }
    private NotificationEvent createNotificationEvent() {
        EmailReceiver emailReceiver = EmailReceiver.newBuilder()
                .setTo(List.of("mostafadfrg@gmail.com"))
                .setCc(Arrays.asList("mostafa.hussien@ittovative.com", "mostafahass314@gmail.com"))
                .setBcc(List.of("jobs@ittovative.com"))
                .build();

        SmsReceiver smsReceiver = SmsReceiver.newBuilder()
                .setPhoneNumber("+201006332994")
                .build();

        WhatsappReceiver whatsappReceiver = WhatsappReceiver.newBuilder()
                .setPhoneNumber("+201006332994")
                .build();

        PushReceiver pushReceiver = PushReceiver.newBuilder()
                .setAppUserId("fcm_token_or_apns_token_here")
                .build();

        List<Receiver> receivers = Arrays.asList(
                Receiver.newBuilder().setEmailReceiver(emailReceiver).build(),
                Receiver.newBuilder().setSmsReceiver(smsReceiver).build(),
                Receiver.newBuilder().setWhatsappReceiver(whatsappReceiver).build(),
                Receiver.newBuilder().setPushReceiver(pushReceiver).build()
        );

        List<Attachment> attachments = Collections.singletonList(
                Attachment.newBuilder()
                        .setUrl("https://s3.amazonaws.com/bucket-name/invoice-123.pdf")
                        .setName("invoice-123.pdf")
                        .build()
        );

        List<NotificationChannel> notificationChannels = Arrays.asList(
                NotificationChannel.newBuilder()
                        .setChannel("EMAIL")
                        .setSender("noreply@example.com")
                        .setOverridingBody("Hello Mostafa, welcome to our platform!")
                        .setProperties(Map.of(
                                "subject", "Test Code",
                                "designTemplateURL", "https://s3.amazonaws.com/bucket-name/email-template.html",
                                "attachmentURL", "https://s3.amazonaws.com/bucket-name/invoice-123.pdf"
                        ))
                        .build(),
                NotificationChannel.newBuilder()
                        .setChannel("SMS")
                        .setSender("+201000000000")
                        .setOverridingBody("Welcome Samy! Your account is ready.")
                        .setProperties(new HashMap<>())
                        .build(),
                NotificationChannel.newBuilder()
                        .setChannel("WHATSAPP")
                        .setSender("+201000000000")
                        .setOverridingBody("Welcome Mostafa! Your account has been successfully created.")
                        .setProperties(Map.of("attachmentURL", "https://s3.amazonaws.com/bucket-name/welcome-image.jpg"))
                        .build(),
                NotificationChannel.newBuilder()
                        .setChannel("PUSH_NOTIFICATION")
                        .setBody("Hey Mostafa! You're all set to explore the app.")
                        .setProperties(Map.of(
                                "deepLink", "app://welcome-screen",
                                "sound_name", "default"
                        ))
                        .build()
        );

        return NotificationEvent.newBuilder()
                .setEventId("550e8400-e29b-41d4-a716-446655440002")
                .setTimestamp("2025-06-22T10:00:00Z")
                .setEventSource("user-service")
                .setPriority("HIGH")
                .setCategory("authentication")
                .setScheduledSendTime(null)
                .setClientId(null)
                .setChannels(Arrays.asList("EMAIL", "SMS", "WHATSAPP", "PUSH_NOTIFICATION"))
                .setHref("https://example.com/tmf")
                .setLanguage("ENGLISH")
                .setUseCommonContent(true)
                .setContent("Common body. If not overridden in notificationChannel, it would be effective")
                .setMessageType("")
                .setReceiver(receivers)
                .setSender(new HashMap<>())
                .setAttachment(attachments)
                .setNotificationChannels(notificationChannels)
                .setExternalField("aaaaaaa")
                .build();
    }


}
