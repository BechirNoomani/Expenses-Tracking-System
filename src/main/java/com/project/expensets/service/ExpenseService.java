package com.project.expensets.service;

import com.project.expensets.model.Transaction;


public interface ExpenseService {
    void addExpense(Transaction expense);

    void updateExpense(Transaction expense);

    void deleteExpense(Long id);



    Transaction findById(Long id);
}
