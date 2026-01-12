package dev.prj.sbatch1.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
public final class Customer {

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

  @Transient
  private boolean valid;

  @Transient
  private String inconsistency;

  @Enumerated(EnumType.STRING)
  private CustomerStatus status;

  public enum CustomerStatus {
    ACTIVE,
    INACTIVE,
    PENDING
  }
}