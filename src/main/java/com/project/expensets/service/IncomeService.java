package com.project.expensets.service;

import com.project.expensets.model.Transaction;

public interface IncomeService {
    void addIncome(Transaction income);

    void updateIncome(Transaction income);

    void deleteIncome(Long id);



    Transaction findById(Long id);
}
