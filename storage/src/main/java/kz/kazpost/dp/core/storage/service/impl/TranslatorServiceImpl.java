package kz.kazpost.dp.core.storage.service.impl;


import kz.kazpost.dp.core.storage.service.TranslatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class TranslatorServiceImpl implements TranslatorService {

    private final MessageSource messageSource;

    @Override
    public String getMessage(String s) {
        if (s == null) {
            return null;
        }
        try {
            return messageSource.getMessage(s, null, s, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException exception) {
            log.warn("getMessage({}) -> {}", s, exception.getMessage());
            return s;
        }
    }
}
