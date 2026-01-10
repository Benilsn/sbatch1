package dev.prj.sbatch1.infra.batch.listener;

import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class MainJobListener implements JobExecutionListener {

  @Override
  public void beforeJob(JobExecution jobExecution) {
    jobExecution.getExecutionContext()
      .putLong("startTime", System.currentTimeMillis());

    log.info("Job {} started at {}",
      jobExecution.getJobInstance().getJobName(),
      jobExecution.getStartTime());
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    long start = jobExecution.getExecutionContext()
      .getLong("startTime");

    long duration = System.currentTimeMillis() - start;

    log.info(
      "Job {} finished with status {} in {} ms",
      jobExecution.getJobInstance().getJobName(),
      jobExecution.getStatus(),
      duration
    );
  }
}
