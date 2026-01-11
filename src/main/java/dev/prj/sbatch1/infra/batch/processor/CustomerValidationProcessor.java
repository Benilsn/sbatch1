package dev.prj.sbatch1.infra.batch.processor;

import dev.prj.sbatch1.domain.model.Customer;
import dev.prj.sbatch1.domain.model.InvalidCustomer;
import dev.prj.sbatch1.domain.model.ValidCustomer;
import dev.prj.sbatch1.infra.batch.dto.CustomerCsvDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Set;

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

    Set<ConstraintViolation<CustomerCsvDTO>> violations = validator.validate(dto);

    if (!violations.isEmpty()) {
      String error =
        violations.stream()
          .map(v -> v.getPropertyPath() + " " + v.getMessage())
          .reduce((a, b) -> a + ", " + b)
          .orElse("Validation error");

      InvalidCustomer invalidCustomer = new InvalidCustomer();
      BeanUtils.copyProperties(dto, invalidCustomer);
      invalidCustomer.setInconsistency(error);

      return invalidCustomer;
    }

    ValidCustomer customer = new ValidCustomer();
    BeanUtils.copyProperties(dto, customer);
    return customer;
  }
}
