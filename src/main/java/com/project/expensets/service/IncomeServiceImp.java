package com.project.expensets.service;

import com.project.expensets.model.Transaction;
import com.project.expensets.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
public class IncomeServiceImp implements IncomeService{
    @Autowired
    private TransactionRepository incomeRepository;

    @Override
    public void addIncome(Transaction income) {
        incomeRepository.save(income);
    }

    @Override
    public void updateIncome(Transaction income) {
        incomeRepository.save(income);
    }

    @Override
    public void deleteIncome(Long id) {
        incomeRepository.deleteById(id);
    }



    @Override
    public Transaction findById(Long id) {
        Optional<Transaction> optionalIncome = incomeRepository.findById(id);
        return optionalIncome.orElse(null);
    }
}
