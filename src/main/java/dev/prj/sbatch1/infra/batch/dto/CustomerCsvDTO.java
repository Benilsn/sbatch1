package dev.prj.sbatch1.infra.batch.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCsvDTO {

  @NotNull(message = "Customer ID is required")
  private Long customerId;

  @NotBlank(message = "First name is required")
  private String firstName;

  @NotBlank(message = "Last name is required")
  private String lastName;

  @Email(message = "Email must be valid")
  @NotBlank(message = "Email is required")
  private String email;

  @Pattern(
    regexp = "\\+?[0-9]{10,15}",
    message = "Phone must be 10 to 15 digits, optionally starting with +"
  )
  private String phone;

  @NotBlank(message = "City is required")
  private String city;

  @NotBlank(message = "State is required")
  private String state;

  @NotBlank(message = "Country is required")
  private String country;

  @Pattern(
    regexp = "\\d{4}-\\d{2}-\\d{2}",
    message = "CreatedAt must be in the format yyyy-MM-dd"
  )
  private String createdAt; // Keep as String if CSV comes as text, validate format

  @NotBlank(message = "Status is required")
  @Pattern(regexp = "ACTIVE|INACTIVE", message = "Status must be ACTIVE or INACTIVE")
  private String status;

}
