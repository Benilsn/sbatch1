package dev.prj.sbatch1.infra.batch.processor;

import dev.prj.sbatch1.domain.model.Customer;
import dev.prj.sbatch1.infra.batch.dto.CustomerCsvDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
@SuppressWarnings("all")
public class CustomerValidationProcessor implements ItemProcessor<CustomerCsvDTO, Customer> {

  private final jakarta.validation.Validator validator;

  public CustomerValidationProcessor() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    this.validator = factory.getValidator();
  }

  @Override
  public Customer process(CustomerCsvDTO dto) {
    log.debug("Validating data, id: {},", dto.getCustomerId());

    Set<ConstraintViolation<CustomerCsvDTO>> violations = validator.validate(dto);
    Customer customer = new Customer();
    BeanUtils.copyProperties(dto, customer);

    if (!violations.isEmpty()) {
      String error =
        violations.stream()
          .map(v -> v.getPropertyPath() + " " + v.getMessage())
          .reduce((a, b) -> a + ", " + b)
          .orElse("Validation error");

      customer.setInconsistency(error);
    } else {
      customer.setValid(true);
    }

    return customer;
  }
}
