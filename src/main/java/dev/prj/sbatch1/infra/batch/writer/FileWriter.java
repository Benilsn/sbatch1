package dev.prj.sbatch1.infra.batch.writer;

import dev.prj.sbatch1.domain.model.Customer;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.infrastructure.item.ItemWriter;

@Log4j2
public class FileWriter {

  public static ItemWriter<Customer> write() {
    return chunk -> {
      log.debug("Writing file...");
    };
  }

}
