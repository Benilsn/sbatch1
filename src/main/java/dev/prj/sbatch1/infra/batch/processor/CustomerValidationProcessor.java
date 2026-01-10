package dev.prj.sbatch1.infra.batch.processor;

import dev.prj.sbatch1.domain.model.Customer;
import dev.prj.sbatch1.infra.batch.dto.CustomerCsvDTO;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.validator.ValidatingItemProcessor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@SuppressWarnings("all")
@RequiredArgsConstructor
public class CustomerValidationProcessor implements ItemProcessor<CustomerCsvDTO, Customer> {

  private final ValidatingItemProcessor<CustomerCsvDTO> validator;

  @Override
  public Customer process(CustomerCsvDTO dto) throws Exception {

    try {
      validator.process(dto);
    } catch (ConstraintViolationException e) {
      log.warn("Skipping invalid record: {}", dto, e);
      return null;
    }

    log.debug("Mapping DTO to entity: {}", dto);

    Customer entity = new Customer();
    BeanUtils.copyProperties(dto, entity);

    return entity;
  }

}
