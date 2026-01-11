//package dev.prj.sbatch1.infra.batch.writer;
//
//import dev.prj.sbatch1.domain.model.Customer;
//import dev.prj.sbatch1.domain.model.InvalidCustomer;
//import dev.prj.sbatch1.domain.model.ValidCustomer;
//import dev.prj.sbatch1.infra.config.Sbatch1Properties;
//import jakarta.persistence.EntityManagerFactory;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.batch.infrastructure.item.Chunk;
//import org.springframework.batch.infrastructure.item.ItemWriter;
//import org.springframework.batch.infrastructure.item.database.JpaItemWriter;
//import org.springframework.batch.infrastructure.item.database.builder.JpaItemWriterBuilder;
//import org.springframework.batch.infrastructure.item.file.FlatFileItemWriter;
//import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemWriterBuilder;
//import org.springframework.batch.infrastructure.item.support.ClassifierCompositeItemWriter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.stereotype.Component;
//
//@Log4j2
//@Component
//@RequiredArgsConstructor
//public class FileWriter {
//
//
//  @Bean
//  public ClassifierCompositeItemWriter<Customer> customerClassifierWriter(EntityManagerFactory emf) {
//
//    ClassifierCompositeItemWriter<Customer> writer =
//      new ClassifierCompositeItemWriter<>();
//
//    writer.setClassifier(customer ->
//      customer instanceof ValidCustomer
//        ? validCustomerWriter(emf)
//        : invalidCustomerWriter()
//    );
//
//    return writer;
//  }
//
//  public ItemWriter<Customer> validCustomerWriter(EntityManagerFactory emf) {
//
//    JpaItemWriter<ValidCustomer> delegate =
//      new JpaItemWriterBuilder<ValidCustomer>()
//        .entityManagerFactory(emf)
//        .usePersist(true)
//        .build();
//
//    return items -> {
//      Chunk<ValidCustomer> validChunk = new Chunk<>(
//        items.getItems().stream()
//          .filter(ValidCustomer.class::isInstance)
//          .map(ValidCustomer.class::cast)
//          .toList()
//      );
//
//      delegate.write(validChunk);
//    };
//  }
//
//  public ItemWriter<Customer> invalidCustomerWriter() {
//
//    FlatFileItemWriter<InvalidCustomer> delegate =
//      new FlatFileItemWriterBuilder<InvalidCustomer>()
//        .name("invalidCustomerWriter")
//        .resource(new FileSystemResource(
//          batchProperties.getInconsistentDataFilePath() + "/inconsistentData.csv"))
//        .delimited()
//        .delimiter(";")
//        .names("dto", "error")
//        .build();
//
//    return items -> {
//      Chunk<InvalidCustomer> validChunk = new Chunk<>(
//        items.getItems().stream()
//          .filter(InvalidCustomer.class::isInstance)
//          .map(InvalidCustomer.class::cast)
//          .toList()
//      );
//
//      delegate.write(validChunk);
//    };
//  }
//
//
//}
