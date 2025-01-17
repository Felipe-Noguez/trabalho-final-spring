package com.dbc.vemser.pokestore.service;

import com.dbc.vemser.pokestore.dto.CupomDTO;
import com.dbc.vemser.pokestore.dto.PedidoDTO;
import com.dbc.vemser.pokestore.dto.UsuarioDTO;
import com.dbc.vemser.pokestore.entity.UsuarioEntity;
import com.dbc.vemser.pokestore.enums.Requisicao;
import com.dbc.vemser.pokestore.exceptions.RegraDeNegocioException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final freemarker.template.Configuration fmConfiguration;

    @Value("${spring.mail.username}")
    private String from;

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

    public void sendEmailPedido(UsuarioDTO pessoaDTO, PedidoDTO pedidoDTO, Requisicao requisicao) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(pessoaDTO.getEmail());
            mimeMessageHelper.setSubject("subject");
            mimeMessageHelper.setText(geContentFromTemplatePedido(pessoaDTO, pedidoDTO, requisicao), true);
            emailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        } catch (RegraDeNegocioException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendEmailRecuperarSenha(UsuarioEntity usuarioEntity, String token) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(usuarioEntity.getEmail());
            mimeMessageHelper.setSubject("subject");
            mimeMessageHelper.setText(geContentFromRecuperarSenha(usuarioEntity, token), true);
            emailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    public void sendEmailCupom(String email, CupomDTO cupomDTO) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("subject");
            mimeMessageHelper.setText(geContentFromTemplateCupom(cupomDTO), true);
            emailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    public void sendRelatorioSemanal(String relatorio, String email) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("subject");
            mimeMessageHelper.setText(geContentFromTemplateRelatorio(relatorio), true);
            emailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    private String geContentFromTemplateRelatorio(String relatorio) throws IOException, TemplateException{
        Map<String, Object> dados = new HashMap<>();
        Template template = null;
        dados.put("relatorio", relatorio);
        dados.put("email", from);

        template = fmConfiguration.getTemplate("email-template-relatorio.html");

        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
    }

    private String geContentFromTemplateCupom(CupomDTO cupomDTO) throws IOException, TemplateException {
        Map<String, Object> dados = new HashMap<>();
        Template template = null;
        dados.put("nomecupom", cupomDTO.getNome());
        dados.put("email", from);
        dados.put("preco", cupomDTO.getPreco());
        dados.put("data", cupomDTO.getDataVencimento());

        template = fmConfiguration.getTemplate("email-template-cupom.html");

        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
    }


    public String geContentFromTemplate(UsuarioDTO usuarioDTO, Requisicao requisicao) throws IOException, TemplateException, RegraDeNegocioException {
        Map<String, Object> dados = new HashMap<>();
        Template template = null;
        dados.put("nome", usuarioDTO.getNome());
        dados.put("email", from);
        dados.put("id", usuarioDTO.getIdUsuario());
        dados.put("pix", usuarioDTO.getPix());

        switch(requisicao){
            case CREATE -> {
                template = fmConfiguration.getTemplate("email-template.html");
            }
            case UPDATE -> {
                template = fmConfiguration.getTemplate("email-template-atualizar.html");
                dados.put("telefone", usuarioDTO.getTelefone());
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

    public String geContentFromTemplatePedido(UsuarioDTO usuarioDTO, PedidoDTO pedidoDTO ,Requisicao requisicao) throws IOException, TemplateException, RegraDeNegocioException {
        Map<String, Object> dados = new HashMap<>();
        Template template = null;
        dados.put("nome", usuarioDTO.getNome());
        dados.put("email", from);
        dados.put("idpedido", pedidoDTO.getIdPedido());
        dados.put("valor", pedidoDTO.getValorFinal());

        switch(requisicao){
            case CREATE -> {
                dados.put("corpo", "<br>Pedido realizado com sucesso" +
                        "<br>Abaixo está as informações do seu pedido:");
            }
            case UPDATE -> {
                dados.put("corpo", "Seu pedido foi Atualizado" +
                        "<br>Aguardando pagamentoa:");
            }
            case DELETE -> {
                dados.put("corpo", "Seu pedido foi deletado. " +
                        "Ficamos triste com sua decisão, nos conte o que podemos melhorar.");
            }

            default ->  {
                throw new RegraDeNegocioException("Erro");
            }
        }

        template = fmConfiguration.getTemplate("email-pedido-template.html");
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
    }

    public String geContentFromRecuperarSenha(UsuarioEntity usuarioEntity ,String token) throws IOException, TemplateException {
        Map<String, Object> dados = new HashMap<>();
        Template template = null;

        dados.put("nome", usuarioEntity.getNome());
        dados.put("email", from);
        dados.put("token", token);

        template = fmConfiguration.getTemplate("email-recuperar-senha-template.html");
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
    }
}
