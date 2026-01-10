package dev.prj.sbatch1.domain.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

  private Long customerId;
  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private String city;
  private String state;
  private String country;
  private LocalDate createdAt;
  private CustomerStatus status;

  public enum CustomerStatus {
    ACTIVE,
    INACTIVE,
    PENDING
  }

}