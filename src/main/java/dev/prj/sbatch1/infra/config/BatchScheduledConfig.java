package dev.prj.sbatch1.infra.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.concurrent.TimeUnit;

@Log4j2
@Configuration
@EnableScheduling
@ConditionalOnProperties(
  {
    @ConditionalOnProperty(
      prefix = "sbatch1",
      name = "scheduled",
      havingValue = "true"
    ),
    @ConditionalOnProperty(
      prefix = "spring.batch.job",
      name = "enabled",
      havingValue = "false"
    )
  }
)
@RequiredArgsConstructor
public class BatchScheduledConfig {

  private Job job;
  private final JobOperator jobOperator;

  @Scheduled(fixedDelay = 30, timeUnit = TimeUnit.SECONDS)
  public void scheduledJob() {

    try {
      JobParameters jobParameters = new JobParametersBuilder()
        .addLong("run.id", System.currentTimeMillis())
        .toJobParameters();

      jobOperator.start(job, jobParameters);
    } catch (Exception e) {
      final String msg = "Failed to execute batch job {}";
      log.error(msg, e.getMessage());
      throw new RuntimeException(msg, e);
    }
  }
}