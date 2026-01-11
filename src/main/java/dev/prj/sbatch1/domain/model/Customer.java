package dev.prj.sbatch1.domain.model;

public sealed abstract class Customer permits ValidCustomer, InvalidCustomer { }
