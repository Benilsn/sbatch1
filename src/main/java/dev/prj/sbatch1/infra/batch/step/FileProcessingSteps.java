package dev.prj.sbatch1.infra.batch.step;

import dev.prj.sbatch1.domain.model.Customer;
import dev.prj.sbatch1.infra.batch.dto.CustomerCsvDTO;
import dev.prj.sbatch1.infra.batch.processor.CustomerValidationProcessor;
import dev.prj.sbatch1.infra.batch.reader.FileReader;
import dev.prj.sbatch1.infra.config.Sbatch1Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.listener.JobExecutionListener;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.support.ClassifierCompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
@RequiredArgsConstructor
public class FileProcessingSteps {

  private final JobExecutionListener jobExecutionListener;
  private final Sbatch1Properties batchProperties;

  @Bean
  public Step processAndValidateStep(JobRepository jobRepository,
                                     PlatformTransactionManager transactionManager,
                                     FileReader fileReader,
                                     CustomerValidationProcessor customerValidationProcessor,
                                     ClassifierCompositeItemWriter<Customer> writer) {
    return new StepBuilder("processAndValidateStep", jobRepository)
      .<CustomerCsvDTO, Customer>chunk(10)
      .transactionManager(transactionManager)
      .listener(jobExecutionListener)
      .reader(fileReader.read(batchProperties.getFilePath()))
      .processor(customerValidationProcessor)
      .writer(writer)
      .build();
  }

}
