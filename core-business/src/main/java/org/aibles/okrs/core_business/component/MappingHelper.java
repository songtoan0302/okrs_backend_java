package org.aibles.okrs.core_business.component;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;


public class MappingHelper {
  private final ModelMapper modelMapper;

  public MappingHelper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  public <T, H> Page<T> mapPage(Page<H> inputData, Class<T> clazz) {
    return inputData.map(i -> modelMapper.map(i, clazz));
  }

  public <T, H> T map(H inputData, Class<T> clazz) {
    return modelMapper.map(inputData, clazz);
  }

  public <H, T> void mapIgnoreNull(T input, H destination) {
    var mapper = new ModelMapper();
    mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
    mapper.map(input, destination);
  }
}
