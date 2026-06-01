package com.proyectovecinal.service;

import com.proyectovecinal.entity.Reporte;
import com.proyectovecinal.entity.enums.EstatusReporte;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.email.from}")
    private String fromEmail;

    @Value("${app.base-url}")
    private String baseUrl;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void notificarReporteCritico(Reporte reporte) {
        if (reporte.getUsuario().getEmail() == null) return;

        String subject = "[Alerta] Reporte crítico registrado - Proyecto Vecinal";
        String body = buildReporteCreadoHtml(reporte);

        sendEmail(reporte.getUsuario().getEmail(), subject, body);
    }

    @Async
    public void notificarCambioEstatus(Reporte reporte, EstatusReporte estatusAnterior) {
        if (reporte.getUsuario().getEmail() == null) return;

        String subject = "[Actualización] Tu reporte #" + reporte.getIdReporte() + " cambió de estatus";
        String body = buildCambioEstatusHtml(reporte, estatusAnterior);

        sendEmail(reporte.getUsuario().getEmail(), subject, body);
    }

    private void sendEmail(String to, String subject, String htmlBody) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String buildReporteCreadoHtml(Reporte reporte) {
        return """
                <html>
                <body style="font-family: Arial, sans-serif;">
                    <h2 style="color: #d32f2f;">Nuevo reporte registrado</h2>
                    <p>Tu reporte ha sido registrado exitosamente en <strong>Proyecto Vecinal</strong>.</p>
                    <table style="border-collapse: collapse; width: 100%%;">
                        <tr><td><strong>ID Reporte:</strong></td><td>%d</td></tr>
                        <tr><td><strong>Categoría:</strong></td><td>%s</td></tr>
                        <tr><td><strong>Subcategoría:</strong></td><td>%s</td></tr>
                        <tr><td><strong>Nivel de Urgencia:</strong></td><td>%s</td></tr>
                        <tr><td><strong>Estatus:</strong></td><td>%s</td></tr>
                        <tr><td><strong>Colonia:</strong></td><td>%s</td></tr>
                    </table>
                    <p>Te notificaremos cuando haya cambios en el estatus de tu reporte.</p>
                </body>
                </html>
                """
                .formatted(
                        reporte.getIdReporte(),
                        reporte.getSubcategoria().getCategoria().getNombre(),
                        reporte.getSubcategoria().getNombre(),
                        reporte.getNivelUrgencia(),
                        reporte.getEstatus(),
                        reporte.getColonia().getNombre()
                );
    }

    private String buildCambioEstatusHtml(Reporte reporte, EstatusReporte estatusAnterior) {
        return """
                <html>
                <body style="font-family: Arial, sans-serif;">
                    <h2 style="color: #1976d2;">Actualización de estatus</h2>
                    <p>El estatus de tu reporte <strong>#%d</strong> ha cambiado.</p>
                    <table style="border-collapse: collapse; width: 100%%;">
                        <tr><td><strong>Estatus anterior:</strong></td><td>%s</td></tr>
                        <tr><td><strong>Nuevo estatus:</strong></td><td>%s</td></tr>
                    </table>
                    <p>Consulta más detalles en la plataforma.</p>
                </body>
                </html>
                """
                .formatted(
                        reporte.getIdReporte(),
                        estatusAnterior,
                        reporte.getEstatus()
                );
    }
}
