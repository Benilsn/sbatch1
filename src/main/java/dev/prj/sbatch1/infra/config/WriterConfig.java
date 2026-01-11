package dev.prj.sbatch1.infra.config;

import dev.prj.sbatch1.domain.model.Customer;
import dev.prj.sbatch1.domain.model.InvalidCustomer;
import dev.prj.sbatch1.domain.model.ValidCustomer;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.database.JpaItemWriter;
import org.springframework.batch.infrastructure.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.infrastructure.item.file.FlatFileItemWriter;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.infrastructure.item.support.ClassifierCompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class WriterConfig {

  private final Sbatch1Properties batchProperties;

  @Bean
  public JpaItemWriter<ValidCustomer> validCustomerJpaWriter(
    EntityManagerFactory emf) {

    return new JpaItemWriterBuilder<ValidCustomer>()
      .entityManagerFactory(emf)
      .usePersist(true)
      .build();
  }

  @Bean
  public FlatFileItemWriter<InvalidCustomer> invalidCustomerFileWriter() {

    return new FlatFileItemWriterBuilder<InvalidCustomer>()
      .name("invalidCustomerWriter")
      .resource(new FileSystemResource(
        batchProperties.getInconsistentDataFilePath() + "/inconsistentData.csv"))
      .delimited()
      .delimiter(";")
      .names("dto", "error")
      .build();
  }

  @Bean
  public ItemWriter<Customer> validCustomerWriter(
    JpaItemWriter<ValidCustomer> delegate) {

    return items -> {
      Chunk<ValidCustomer> chunk = new Chunk<>(
        items.getItems().stream()
          .filter(ValidCustomer.class::isInstance)
          .map(ValidCustomer.class::cast)
          .toList()
      );
      delegate.write(chunk);
    };
  }

  @Bean
  public ItemWriter<Customer> invalidCustomerWriter(
    FlatFileItemWriter<InvalidCustomer> delegate) {

    return items -> {
      Chunk<InvalidCustomer> chunk = new Chunk<>(
        items.getItems().stream()
          .filter(InvalidCustomer.class::isInstance)
          .map(InvalidCustomer.class::cast)
          .toList()
      );
      delegate.write(chunk);
    };
  }

  @Bean
  public ClassifierCompositeItemWriter<Customer> customerClassifierWriter(
    ItemWriter<Customer> validCustomerWriter,
    ItemWriter<Customer> invalidCustomerWriter) {

    ClassifierCompositeItemWriter<Customer> writer =
      new ClassifierCompositeItemWriter<>();

    writer.setClassifier(customer ->
      customer instanceof ValidCustomer
        ? validCustomerWriter
        : invalidCustomerWriter
    );

    return writer;
  }


}
