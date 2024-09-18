package ci.digitalacademy.bibliotheque.service.impl;

import ci.digitalacademy.bibliotheque.service.NotificationMailService;
import ci.digitalacademy.bibliotheque.service.dto.LoanDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationMailServiceImp implements NotificationMailService {
    private final JavaMailSender javaMailSender;
    @Override
    public void sendNotificationMailReservation(LoanDTO loanDTO) {
        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);

            helper.setFrom("noreply@delmas-gpt.tech");
            helper.setTo(loanDTO.getUser().getEmail());
            helper.setSubject("Confirmation de votre réservation de livre");
            String content = "<html>" +
                    "<body>" +
                    "    <div style=\"font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #edf2f7; padding: 20px; text-align: center;\">" +
                    "        <div style=\"background-color: #ffffff; width: 100%; max-width: 480px; margin: auto; box-shadow: 0 8px 16px rgba(0,0,0,0.1); border-radius: 10px; overflow: hidden; border-left: 5px solid #4a90e2;\">" +
                    "            <div style=\"background-color: #4a90e2; color: white; padding: 20px; font-size: 18px; text-align: center;\">Confirmation de réservation de livre</div>" +
                    "            <div style=\"padding: 20px; color: #333333; line-height: 1.6; text-align: center;\">" +
                    "                Bonjour <strong>" + loanDTO.getUser().getFirstName() + " " + loanDTO.getUser().getLastName() + "</strong>,<br><br>" +
                    "                Vous avez réservé le livre suivant :<br>" +
                    "                <div style=\"font-size: 16px; margin: 20px 0;\">" +
                    "                    <strong>Titre : </strong>" + loanDTO.getBook().getTitle() + "<br>" +
                    "                    <strong>Auteur : </strong>" + loanDTO.getBook().getAuthor() + "<br>" +
                    "                    <strong>Catégorie : </strong>" + loanDTO.getBook().getCategory() + "<br>" +
                    "                </div>" +
                    "                <br>" +
                    "                Votre réservation est confirmée pour la période suivante :<br>" +
                    "                <div style=\"font-size: 16px; margin: 20px 0;\">" +
                    "                    <strong>Date de réservation : </strong>" + loanDTO.getDate_loan() + "<br>" +
                    "                    <strong>Date limite  : </strong>" + loanDTO.getDeadline() + "<br>" +
                    "                </div>" +
                    "                <br>" +
                    "                Veuillez vous rendre à la bibliothèque avant la date limite pour récupérer votre livre." +
                    "            </div>" +
                    "            <div style=\"background-color: #f7f7f7; color: #666666; text-align: center; padding: 12px 20px; font-size: 14px;\">© 2024 Angaman Cedrick Tous droits réservés.</div>" +
                    "        </div>" +
                    "    </div>" +
                    "</body>" +
                    "</html>";

            helper.setText(content, true);
            javaMailSender.send(mail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void sendNotificationMailConfirmLoan(LoanDTO loanDTO) {
        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);

            helper.setFrom("noreply@delmas-gpt.tech");
            helper.setTo(loanDTO.getUser().getEmail());
            helper.setSubject("Confirmation de prêt de livre");


            String content = "<html>" +
                    "<body>" +
                    "    <div style=\"font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #edf2f7; padding: 20px; text-align: center;\">" +
                    "        <div style=\"background-color: #ffffff; width: 100%; max-width: 480px; margin: auto; box-shadow: 0 8px 16px rgba(0,0,0,0.1); border-radius: 10px; overflow: hidden; border-left: 5px solid #4a90e2;\">" +
                    "            <div style=\"background-color: #4a90e2; color: white; padding: 20px; font-size: 18px; text-align: center;\">Confirmation de prêt de livre</div>" +
                    "            <div style=\"padding: 20px; color: #333333; line-height: 1.6; text-align: center;\">" +
                    "                Bonjour <strong>" + loanDTO.getUser().getFirstName() + " " + loanDTO.getUser().getLastName() + "</strong>,<br><br>" +
                    "                Votre réservation a été confirmée et le livre suivant vous a été prêté :<br>" +
                    "                <div style=\"font-size: 16px; margin: 20px 0;\">" +
                    "                    <strong>Titre : </strong>" + loanDTO.getBook().getTitle() + "<br>" +
                    "                    <strong>Auteur : </strong>" + loanDTO.getBook().getAuthor() + "<br>" +
                    "                    <strong>Catégorie : </strong>" + loanDTO.getBook().getCategory() + "<br>" +
                    "                </div>" +
                    "                <br>" +
                    "                Détails du prêt :<br>" +
                    "                <div style=\"font-size: 16px; margin: 20px 0;\">" +
                    "                    <strong>Date de prêt : </strong>" + loanDTO.getDate_loan() + "<br>" +
                    "                    <strong>Date limite de retour : </strong>" + loanDTO.getDeadline() + "<br>" +
                    "                </div>" +
                    "                <br>" +
                    "                Veuillez vous assurer de retourner le livre avant la date limite indiquée." +
                    "            </div>" +
                    "            <div style=\"background-color: #f7f7f7; color: #666666; text-align: center; padding: 12px 20px; font-size: 14px;\">© 2024 Angaman Cedrick Tous droits réservés.</div>" +
                    "        </div>" +
                    "    </div>" +
                    "</body>" +
                    "</html>";

            helper.setText(content, true);
            javaMailSender.send(mail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void sendNotificationMailLoan(LoanDTO loanDTO) {
        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);

            helper.setFrom("noreply@delmas-gpt.tech");
            helper.setTo(loanDTO.getUser().getEmail());
            helper.setSubject("Notification de prêt de livre");

            String content = "<html>" +
                    "<body>" +
                    "    <div style=\"font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #edf2f7; padding: 20px; text-align: center;\">" +
                    "        <div style=\"background-color: #ffffff; width: 100%; max-width: 480px; margin: auto; box-shadow: 0 8px 16px rgba(0,0,0,0.1); border-radius: 10px; overflow: hidden; border-left: 5px solid #4a90e2;\">" +
                    "            <div style=\"background-color: #4a90e2; color: white; padding: 20px; font-size: 18px; text-align: center;\">Détails du prêt de livre</div>" +
                    "            <div style=\"padding: 20px; color: #333333; line-height: 1.6; text-align: center;\">" +
                    "                Bonjour <strong>" + loanDTO.getUser().getFirstName() + " " + loanDTO.getUser().getLastName() + "</strong>,<br><br>" +
                    "                Nous vous informons que le livre suivant vous a été prêté :<br>" +
                    "                <div style=\"font-size: 16px; margin: 20px 0;\">" +
                    "                    <strong>Titre : </strong>" + loanDTO.getBook().getTitle() + "<br>" +
                    "                    <strong>Auteur : </strong>" + loanDTO.getBook().getAuthor() + "<br>" +
                    "                    <strong>Catégorie : </strong>" + loanDTO.getBook().getCategory() + "<br>" +
                    "                </div>" +
                    "                <br>" +
                    "                Détails du prêt :<br>" +
                    "                <div style=\"font-size: 16px; margin: 20px 0;\">" +
                    "                    <strong>Date de prêt : </strong>" + loanDTO.getDate_loan() + "<br>" +
                    "                    <strong>Date limite de retour : </strong>" + loanDTO.getDeadline() + "<br>" +
                    "                </div>" +
                    "                <br>" +
                    "                Veuillez retourner le livre avant la date limite indiquée." +
                    "            </div>" +
                    "            <div style=\"background-color: #f7f7f7; color: #666666; text-align: center; padding: 12px 20px; font-size: 14px;\">© 2024 Angaman Cedrick Tous droits réservés.</div>" +
                    "        </div>" +
                    "    </div>" +
                    "</body>" +
                    "</html>";

            helper.setText(content, true);
            javaMailSender.send(mail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendNotificationMailReturnConfirmation(LoanDTO loanDTO) {
        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);

            helper.setFrom("noreply@delmas-gpt.tech");
            helper.setTo(loanDTO.getUser().getEmail());
            helper.setSubject("Confirmation de retour de livre");
            String content = "<html>" +
                    "<body>" +
                    "    <div style=\"font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #edf2f7; padding: 20px; text-align: center;\">" +
                    "        <div style=\"background-color: #ffffff; width: 100%; max-width: 480px; margin: auto; box-shadow: 0 8px 16px rgba(0,0,0,0.1); border-radius: 10px; overflow: hidden; border-left: 5px solid #4a90e2;\">" +
                    "            <div style=\"background-color: #4a90e2; color: white; padding: 20px; font-size: 18px; text-align: center;\">Confirmation du retour de votre livre</div>" +
                    "            <div style=\"padding: 20px; color: #333333; line-height: 1.6; text-align: center;\">" +
                    "                Bonjour <strong>" + loanDTO.getUser().getFirstName() + " " + loanDTO.getUser().getLastName() + "</strong>,<br><br>" +
                    "                Nous vous confirmons la réception du livre suivant, que vous avez retourné :<br>" +
                    "                <div style=\"font-size: 16px; margin: 20px 0;\">" +
                    "                    <strong>Titre : </strong>" + loanDTO.getBook().getTitle() + "<br>" +
                    "                    <strong>Auteur : </strong>" + loanDTO.getBook().getAuthor() + "<br>" +
                    "                </div>" +
                    "                <br>" +
                    "                Merci d'avoir utilisé nos services.<br>" +
                    "                Nous espérons vous revoir bientôt dans notre bibliothèque.<br><br>" +
                    "                Cordialement,<br>" +
                    "                L'équipe de la bibliothèque" +
                    "            </div>" +
                    "            <div style=\"background-color: #f7f7f7; color: #666666; text-align: center; padding: 12px 20px; font-size: 14px;\">© 2024 Angaman Cedrick Tous droits réservés.</div>" +
                    "        </div>" +
                    "    </div>" +
                    "</body>" +
                    "</html>";

            helper.setText(content, true);
            javaMailSender.send(mail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendNotificationMailCancel(LoanDTO loanDTO) {

    }

    @Override
    public void sendNotificationMailRejete(LoanDTO loanDTO) {

    }


}
