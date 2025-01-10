package br.com.desafioresidencia.gerenciadoreventos.security.exceptions;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class AuthenticationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final MessageSource messageSource;

    public AuthenticationException(String messageKey, MessageSource messageSource) {
        super(messageKey);
        this.messageSource = messageSource;
    }

    @Override
    public String getMessage() {
        return messageSource.getMessage(super.getMessage(), null, LocaleContextHolder.getLocale());
    }
}
