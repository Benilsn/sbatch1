package dev.prj.sbatch1.infra.batch.writer;

import dev.prj.sbatch1.domain.model.Customer;
import dev.prj.sbatch1.infra.config.Sbatch1Properties;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.infrastructure.item.database.JpaItemWriter;
import org.springframework.batch.infrastructure.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.infrastructure.item.file.FlatFileItemWriter;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.infrastructure.item.support.ClassifierCompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class DataWriter {

  private final Sbatch1Properties batchProperties;

  @Bean
  public ClassifierCompositeItemWriter<Customer> customerWriter(
    JpaItemWriter<Customer> validWriter,
    FlatFileItemWriter<Customer> invalidWriter) {

    ClassifierCompositeItemWriter<Customer> writer =
      new ClassifierCompositeItemWriter<>();

    writer.setClassifier(customer -> {
      if (customer.isValid()) {
        log.debug(
          "Customer [{}] is VALID → Persisting...",
          customer.getCustomerId()
        );
        return validWriter;
      } else {
        log.warn(
          "Customer [{}] is INVALID → Reason: {}",
          customer.getCustomerId(),
          customer.getInconsistency()
        );
        return invalidWriter;
      }
    });

    return writer;
  }

  @Bean
  public JpaItemWriter<Customer> validCustomerWriter(EntityManagerFactory emf) {
    return new JpaItemWriterBuilder<Customer>()
      .entityManagerFactory(emf)
      .usePersist(true)
      .build();
  }

  @Bean
  public FlatFileItemWriter<Customer> invalidCustomerWriter() {
    return new FlatFileItemWriterBuilder<Customer>()
      .name("invalidCustomerWriter")
      .resource(new FileSystemResource(batchProperties.getInconsistentDataFilePath()))
      .delimited()
      .delimiter(";")
      .names("customerId", "email", "inconsistency")
      .headerCallback(writer ->
        writer.write("customerId;email;inconsistency")
      )
      .saveState(true)
      .build();
  }
}
