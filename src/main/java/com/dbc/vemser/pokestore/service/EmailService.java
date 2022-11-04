package com.dbc.vemser.pokestore.service;

import com.dbc.vemser.pokestore.dto.UsuarioDTO;
import com.dbc.vemser.pokestore.enums.Requisicao;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.ResourceUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final freemarker.template.Configuration fmConfiguration;

    @Value("${spring.mail.username}")
    private String from;

    private static final String TO = "julio.gabriel@dbccompany.com.br";

    private final JavaMailSender emailSender;

    public void sendEmailUsuario(UsuarioDTO pessoaDTO, Requisicao requisicao) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(pessoaDTO.getEmail());
            mimeMessageHelper.setSubject("subject");
            mimeMessageHelper.setText(geContentFromTemplate(pessoaDTO, requisicao), true);
            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        } catch (RegraDeNegocioException e) {
            throw new RuntimeException(e);
        }
    }

    public String geContentFromTemplate(UsuarioDTO usuarioDTO, Requisicao requisicao) throws IOException, TemplateException, RegraDeNegocioException {
        Map<String, Object> dados = new HashMap<>();
        Template template = null;
        dados.put("nome", usuarioDTO.getNome());
        dados.put("email", from);
        dados.put("id", usuarioDTO.getIdUsuario());

        switch(requisicao){
            case CREATE -> {
                template = fmConfiguration.getTemplate("email-template.html");
            }
            case UPDATE -> {
                template = fmConfiguration.getTemplate("email-template-atualizar.html");
            }
            case DELETE -> {
                template = fmConfiguration.getTemplate("email-template-excluir.html");
            }

            default ->  {
                throw new RegraDeNegocioException("Erro");
            }
        }

        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
    }
}
