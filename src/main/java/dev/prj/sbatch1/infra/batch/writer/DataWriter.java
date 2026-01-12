package dev.prj.sbatch1.infra.batch.writer;

import dev.prj.sbatch1.domain.model.Customer;
import dev.prj.sbatch1.infra.config.Sbatch1Properties;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.database.JpaItemWriter;
import org.springframework.batch.infrastructure.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.infrastructure.item.file.FlatFileItemWriter;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class DataWriter {

  private final Sbatch1Properties batchProperties;

  public ItemWriter<Customer> write(EntityManagerFactory emf){
    return e -> {

    };
  }

  public JpaItemWriter<Customer> validCustomerWriter() {
    return new JpaItemWriterBuilder<Customer>()
      .entityManagerFactory(emf)
      .usePersist(true)
      .build();
  }

  public FlatFileItemWriter<Customer> invalidCustomerWriter() {
    return new FlatFileItemWriterBuilder<Customer>()
      .name("invalidCustomerWriter")
      .resource(new FileSystemResource(
        batchProperties.getInconsistentDataFilePath() + "/inconsistentData.csv"))
      .delimited()
      .delimiter(";")
      .names("dto", "error")
      .saveState(true)
      .build();
  }


}
