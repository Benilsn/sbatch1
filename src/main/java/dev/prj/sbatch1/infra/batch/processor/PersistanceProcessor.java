package dev.prj.sbatch1.infra.batch.processor;

import dev.prj.sbatch1.domain.model.Customer;
import dev.prj.sbatch1.infra.batch.dto.CustomerCsvDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.infrastructure.item.ItemProcessor;

@Log4j2
public class PersistanceProcessor {

  public static ItemProcessor<CustomerCsvDTO, Customer> persistData() {
    return chunk -> {
      log.debug("Persisting data from file...");


      return null;
    };
  }

}
