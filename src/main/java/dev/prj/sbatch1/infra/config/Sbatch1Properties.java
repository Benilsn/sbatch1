package dev.prj.sbatch1.infra.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("sbatch1")
public class Sbatch1Properties {

   private int chunkSize;
   private String filePath;
   private String inconsistentDataFilePath;
   private boolean scheduled;

}
