package com.project.expensets.service;

import com.project.expensets.model.Transaction;
import com.project.expensets.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;


@Service
public class HomeServiceImp implements HomeService {

    @Autowired
    private TransactionRepository transactionRepository;
    public LocalDate convertToLocalDate(String dateString)

    {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {



            return null;
        }
    }
    @Override
    public Page<Transaction> findAllFiltered(Pageable pageable, String startDate, String endDate, String descriptionFilter, Double minAmount, Double maxAmount) {
        LocalDate start = startDate != null && !startDate.isEmpty() ? convertToLocalDate(startDate) : null;
        LocalDate end = endDate != null && !endDate.isEmpty() ? convertToLocalDate(endDate) : null;

        return transactionRepository.findAllFiltered(pageable, start, end, descriptionFilter, minAmount, maxAmount);
    }
  public List<Transaction> findAll(){return transactionRepository.findAll();}

}
