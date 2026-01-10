package dev.prj.sbatch1.infra.persistance.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
public class CustomerEntity {

  @Id
  private Long customerId;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false, unique = true)
  private String email;

  private String phone;
  private String city;
  private String state;
  private String country;

  private LocalDate createdAt;

  @Enumerated(EnumType.STRING)
  private CustomerStatus status;

  public enum CustomerStatus {
    ACTIVE,
    INACTIVE,
    PENDING
  }

}