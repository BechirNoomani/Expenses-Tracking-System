package com.project.expensets.service;

import com.project.expensets.model.Transaction;
import com.project.expensets.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ExpenceServiceImp implements ExpenseService{
    @Autowired
    private TransactionRepository expenseRepository;

    @Override
    public void addExpense(Transaction expense) {
        expenseRepository.save(expense);
    }

    @Override
    public void updateExpense(Transaction expense) {
        expenseRepository.save(expense);
    }

    @Override
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }



    @Override
    public Transaction findById(Long id) {
        Optional<Transaction> optionalExpense = expenseRepository.findById(id);
        return optionalExpense.orElse(null);
    }
}
