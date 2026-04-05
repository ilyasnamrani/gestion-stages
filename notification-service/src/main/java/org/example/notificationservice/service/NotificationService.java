package org.example.notificationservice.service;

import org.example.notificationservice.dto.CandidatResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);


    private final JavaMailSender mailSender;

    @Value("${spring.mail.from:no-reply@gestion-stages.com}")
    private String fromEmail;

    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @KafkaListener(topics = "candidat-topic", groupId = "notification-group")
    public void consumeCandidatEvent(CandidatResponse candidat) {
        logger.info("Événement Kafka reçu : eventType={}, candidat={} {}",
                candidat.getEventType(), candidat.getNomCandidat(), candidat.getPrenomCandidat());

        if (candidat.getEmailCandidat() == null || candidat.getEmailCandidat().isBlank()) {
            logger.warn("Email manquant pour le candidat id={}, notification ignorée.", candidat.getIdCandidat());
            return;
        }

        String eventType = candidat.getEventType();
        if ("CREATED".equals(eventType)) {
            sendCreationEmail(candidat);
        } else if ("UPDATED".equals(eventType)) {
            sendUpdateEmail(candidat);
        } else {
            logger.warn("eventType inconnu '{}' pour le candidat id={}", eventType, candidat.getIdCandidat());
        }
    }

    private void sendCreationEmail(CandidatResponse candidat) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(candidat.getEmailCandidat());
            message.setSubject("Bienvenue sur la plateforme Gestion des Stages !");
            message.setText(
                    "Bonjour " + candidat.getPrenomCandidat() + " " + candidat.getNomCandidat() + ",\n\n" +
                            "Votre profil candidat a été créé avec succès sur notre plateforme de gestion des stages.\n\n"
                            +
                            "Voici un résumé de votre profil :\n" +
                            "  - Niveau : " + (candidat.getNiveau() != null ? candidat.getNiveau() : "Non renseigné")
                            + "\n" +
                            "  - Filière : "
                            + (candidat.getFiliere() != null ? candidat.getFiliere() : "Non renseignée") + "\n\n" +
                            "Vous pouvez dès maintenant consulter les offres de stage disponibles et postuler.\n\n" +
                            "Cordialement,\n" +
                            "L'équipe Gestion des Stages");
            mailSender.send(message);
            logger.info("Email de création envoyé à {}", candidat.getEmailCandidat());
        } catch (Exception e) {
            logger.error("Erreur lors de l'envoi de l'email de création à {} : {}",
                    candidat.getEmailCandidat(), e.getMessage());
        }
    }

    private void sendUpdateEmail(CandidatResponse candidat) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(candidat.getEmailCandidat());
            message.setSubject("Votre profil candidat a été mis à jour");
            message.setText(
                    "Bonjour " + candidat.getPrenomCandidat() + " " + candidat.getNomCandidat() + ",\n\n" +
                            "Votre profil candidat a été mis à jour avec succès sur notre plateforme.\n\n" +
                            "Voici le résumé de votre profil mis à jour :\n" +
                            "  - Niveau : " + (candidat.getNiveau() != null ? candidat.getNiveau() : "Non renseigné")
                            + "\n" +
                            "  - Filière : "
                            + (candidat.getFiliere() != null ? candidat.getFiliere() : "Non renseignée") + "\n\n" +
                            "Si vous n'êtes pas à l'origine de cette modification, veuillez contacter notre support.\n\n"
                            +
                            "Cordialement,\n" +
                            "L'équipe Gestion des Stages");
            mailSender.send(message);
            logger.info("Email de mise à jour envoyé à {}", candidat.getEmailCandidat());
        } catch (Exception e) {
            logger.error("Erreur lors de l'envoi de l'email de mise à jour à {} : {}",
                    candidat.getEmailCandidat(), e.getMessage());
        }
    }
}
