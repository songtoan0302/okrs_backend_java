package org.aibles.okrs.core_exception.component;

import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @author ToanNS
 */
@Slf4j
@RequiredArgsConstructor
public class MessageHelper {

  private final MessageSource messageSource;

  public String getErrorMessage(String messageKey, Object... args) {
    Locale locale = LocaleContextHolder.getLocale();
    return getMessage(messageKey, args, locale, null);
  }

  public String getMessage(String messageKey, Object... args) {
    Locale locale = LocaleContextHolder.getLocale();
    return getMessage(messageKey, args, locale, null);
  }

  public String getMessage(String messageKey, Object[] args, Locale locale, String defaultMsg) {
    try {
      return messageSource.getMessage(messageKey, args, locale);
    } catch (Exception ex) {
      log.error("Can not find message", ex);
      return defaultMsg;
    }
  }
}
