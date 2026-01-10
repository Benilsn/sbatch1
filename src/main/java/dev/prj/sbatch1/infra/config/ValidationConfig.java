package dev.prj.sbatch1.infra.config;

import dev.prj.sbatch1.infra.batch.dto.CustomerCsvDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.infrastructure.item.validator.ValidatingItemProcessor;
import org.springframework.batch.infrastructure.item.validator.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class ValidationConfig {

  private final jakarta.validation.Validator jakartaValidator;

  @Bean
  public ValidatingItemProcessor<CustomerCsvDTO> validatingProcessor(Validator<CustomerCsvDTO> validator) {
    ValidatingItemProcessor<CustomerCsvDTO> processor = new ValidatingItemProcessor<>(validator);

    processor.setFilter(true);
    return processor;
  }


  @Bean
  public Validator<CustomerCsvDTO> validator() {
    return item -> {
      Set<ConstraintViolation<CustomerCsvDTO>> violations = jakartaValidator.validate(item);
      if (!violations.isEmpty()) {
        String error = violations.stream()
          .map(v -> v.getPropertyPath() + " " + v.getMessage())
          .reduce((a, b) -> a + ", " + b)
          .orElse("");
        throw new ValidationException(error);
      }
    };
  }

}