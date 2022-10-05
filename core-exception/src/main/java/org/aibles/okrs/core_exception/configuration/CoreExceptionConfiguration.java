package org.aibles.okrs.core_exception.configuration;

import java.util.List;
import java.util.Locale;
import org.aibles.okrs.core_exception.component.MessageHelper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

/**
 * @author ToanNS
 */
@Configuration
@ComponentScan(basePackages = {"org.aibles.okrs.core_exception.controller.advice"})
public class CoreExceptionConfiguration {
  @Bean
  public LocaleResolver localeResolver() {
    AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
    localeResolver.setSupportedLocales(List.of(Locale.ENGLISH, new Locale("vi")));
    localeResolver.setDefaultLocale(new Locale("vi", "VN"));
    return localeResolver;
  }

  @Bean
  public ReloadableResourceBundleMessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource =
        new ReloadableResourceBundleMessageSource();
    messageSource.setBasenames("classpath:i18n/message");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }

  @Bean
  public MessageHelper messageHelper(MessageSource messageSource) {
    return new MessageHelper(messageSource);
  }
}
