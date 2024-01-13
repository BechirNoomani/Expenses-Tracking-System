package com.project.expensets.service;
import com.project.expensets.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface HomeService {

    Page<Transaction> findAllFiltered(Pageable pageable, String startDate, String endDate, String descriptionFilter, Double minAmount, Double maxAmount);

    List<Transaction> findAll();

}
