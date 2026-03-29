package org.example.candidatservice.services;

import org.example.candidatservice.dtos.CandidatResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    private final KafkaTemplate<String, CandidatResponse> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, CandidatResponse> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendCandidateCreatedEvent(CandidatResponse candidate) {
        candidate.setEventType("CREATED");
        kafkaTemplate.send(topicName, candidate.getIdCandidat().toString(), candidate)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        logger.info("Message CREATED envoyé avec succès au topic {} [Partition: {}, Offset: {}]",
                                topicName, result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
                    } else {
                        logger.error("Échec de l'envoi du message au topic {} : {}", topicName, ex.getMessage());
                    }
                });
    }

    public void sendCandidateUpdatedEvent(CandidatResponse candidate) {
        candidate.setEventType("UPDATED");
        kafkaTemplate.send(topicName, candidate.getIdCandidat().toString(), candidate)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        logger.info("Message UPDATED envoyé avec succès au topic {} [Partition: {}, Offset: {}]",
                                topicName, result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
                    } else {
                        logger.error("Échec de l'envoi du message de mise à jour au topic {} : {}", topicName, ex.getMessage());
                    }
                });
    }
}

