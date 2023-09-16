package kz.shakhuali.springproject.testassignment.repository;

import kz.shakhuali.springproject.testassignment.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
