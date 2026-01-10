package dev.prj.sbatch1.infra.batch.step;

import dev.prj.sbatch1.domain.model.Customer;
import dev.prj.sbatch1.infra.batch.dto.CustomerCsvDTO;
import dev.prj.sbatch1.infra.batch.processor.CustomerValidationProcessor;
import dev.prj.sbatch1.infra.batch.processor.PersistanceProcessor;
import dev.prj.sbatch1.infra.batch.reader.FileReader;
import dev.prj.sbatch1.infra.batch.writer.FileWriter;
import dev.prj.sbatch1.infra.config.Sbatch1Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.listener.JobExecutionListener;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileProcessingSteps {

  private final JobExecutionListener jobExecutionListener;
  private final Sbatch1Properties batchProperties;
  private final CustomerValidationProcessor validationProcessor;


  @Bean
  public Step processAndValidateStep(JobRepository jobRepository) {
    return new StepBuilder("processAndValidateStep", jobRepository)
      .<CustomerCsvDTO, Customer>chunk(10)
      .listener(jobExecutionListener)
      .reader(FileReader.read(batchProperties.getFilePath()))
      .processor(validationProcessor)
      .writer(FileWriter.write())
      .processor(PersistanceProcessor.persistData())
      .build();
  }

}
