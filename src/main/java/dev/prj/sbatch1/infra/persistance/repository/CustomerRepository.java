package dev.prj.sbatch1.infra.persistance.repository;

import dev.prj.sbatch1.domain.model.ValidCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<ValidCustomer, Long> {
}
