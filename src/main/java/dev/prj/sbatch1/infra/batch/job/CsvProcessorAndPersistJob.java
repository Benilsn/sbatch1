package dev.prj.sbatch1.infra.batch.job;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CsvProcessorAndPersistJob {

  @Bean
  public Job fileProcessorJob(JobRepository jobRepository, Step processAndValidateStep) {
    return new JobBuilder("fileProcessorJob", jobRepository)
      .start(processAndValidateStep)
      .build();
  }

}
