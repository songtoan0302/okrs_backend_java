package org.aibles.okrs.core_business.configuration;

import org.aibles.okrs.core_business.component.MappingHelper;
import org.aibles.okrs.core_business.repository.KeyResultRepository;
import org.aibles.okrs.core_business.repository.ObjectiveRepository;
import org.aibles.okrs.core_business.service.KeyResultService;
import org.aibles.okrs.core_business.service.ObjectiveService;
import org.aibles.okrs.core_business.service.OkrsService;
import org.aibles.okrs.core_business.service.impl.KeyResultServiceImpl;
import org.aibles.okrs.core_business.service.impl.ObjectiveServiceImpl;
import org.aibles.okrs.core_business.service.impl.OkrsServiceImpl;
import org.aibles.okrs.core_exception.component.MessageHelper;
import org.aibles.okrs.core_exception.configuration.EnableCoreException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@ComponentScan(basePackages = "org.aibles.okrs.core_business.repository")
@EnableCaching
@EnableCoreException
@EntityScan("org.aibles.okrs.core_business.entity")
@EnableJpaRepositories(basePackages = "org.aibles.okrs.core_business.repository")
public class OkrsConfiguration {

  @Bean
  public KeyResultService keyResultService(
      KeyResultRepository repository, MappingHelper mappingHelper, ObjectiveService service, MessageHelper messageHelper) {
    return new KeyResultServiceImpl(repository, mappingHelper, service, messageHelper);
  }

  @Bean
  public ObjectiveService objectiveService(
      ObjectiveRepository repository, @Lazy KeyResultService service, MappingHelper mappingHelper, MessageHelper messageHelper) {
    return new ObjectiveServiceImpl(repository, service, mappingHelper, messageHelper);
  }

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    return modelMapper;
  }

  @Bean
  public MappingHelper mappingHelper(ModelMapper modelMapper) {
    return new MappingHelper(modelMapper);
  }

  @Bean
  public OkrsService okrsService(
      ObjectiveService objectiveService,
      KeyResultService keyResultService,
      ModelMapper modelMapper) {
    return new OkrsServiceImpl(objectiveService, keyResultService, modelMapper);
  }
}
