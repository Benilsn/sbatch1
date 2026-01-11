package dev.prj.sbatch1.infra.batch.reader;

import dev.prj.sbatch1.infra.batch.dto.CustomerCsvDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.infrastructure.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.infrastructure.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class FileReader {

  public FlatFileItemReader<CustomerCsvDTO> read(String filePath) {
    log.debug("Reading file: {}", filePath);

    DefaultLineMapper<CustomerCsvDTO> lineMapper = new DefaultLineMapper<>();

    DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
    tokenizer.setDelimiter(",");
    tokenizer.setNames(
      "customerId",
      "firstName",
      "lastName",
      "email",
      "phone",
      "city",
      "state",
      "country",
      "createdAt",
      "status"
    );

    BeanWrapperFieldSetMapper<CustomerCsvDTO> mapper = new BeanWrapperFieldSetMapper<>();
    mapper.setTargetType(CustomerCsvDTO.class);

    lineMapper.setLineTokenizer(tokenizer);
    lineMapper.setFieldSetMapper(mapper);

    FlatFileItemReader<CustomerCsvDTO> reader = new FlatFileItemReader<>(lineMapper);
    reader.setResource(new FileSystemResource(filePath));
    reader.setLinesToSkip(1);

    return reader;
  }


}
