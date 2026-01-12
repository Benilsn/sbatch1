# Spring Batch CSV Customer Processor

This project is a **Spring Batch** application designed to process CSV files containing customer data. It validates each customer record and routes them accordingly: valid customers are persisted to the database, while invalid ones are written to an error file for later review.

---

## Features

- Reads customer data from CSV files.
- Validates records using Jakarta Bean Validation.
- Writes valid customers to a relational database.
- Writes invalid customers to an error CSV file.
- Uses a modular Spring Batch architecture for easy maintenance and scalability.

---

## Batch Process Flow

1. **Read**  
   The batch job reads customer data from input CSV files using a `FlatFileItemReader`.

2. **Validate**  
   Each record is validated using a `Validator` (e.g., checking mandatory fields, format constraints, etc.).

3. **Write**
    - **Valid Records:** Written to the database using `JpaItemWriter`.
    - **Invalid Records:** Written to a separate CSV error file using `FlatFileItemWriter`.

This flow ensures data quality and allows easy tracking of invalid records.

---

## Technologies Used

- Java 17+
- Spring Boot
- Spring Batch
- Spring Data JPA
- Jakarta Bean Validation
- H2 / PostgreSQL / MySQL (configurable database)
- CSV files

---
