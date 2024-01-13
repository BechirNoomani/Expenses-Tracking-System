package com.project.expensets.repository;

import com.project.expensets.model.Transaction;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface HomeRepository extends JpaRepository<Transaction, Long> {
    @NotNull Page<Transaction> findAll(@NotNull Pageable pageable);
    @NotNull List<Transaction> findAll();

    @Query("SELECT t FROM Transaction t WHERE " +
            "(:startDate IS NULL OR t.date >= :startDate) AND " +
            "(:endDate IS NULL OR t.date <= :endDate) AND " +
            "(:descriptionFilter IS NULL OR LOWER(t.description) LIKE LOWER(CONCAT('%', :descriptionFilter, '%'))) AND " +
            "(:minAmount IS NULL OR t.amount >= :minAmount) AND " +
            "(:maxAmount IS NULL OR t.amount <= :maxAmount)")
    Page<Transaction> findAllFiltered(Pageable pageable,
                                      @Param("startDate") LocalDate startDate,
                                      @Param("endDate") LocalDate endDate,
                                      @Param("descriptionFilter") String descriptionFilter,
                                      @Param("minAmount") Double minAmount,
                                      @Param("maxAmount") Double maxAmount);
}
