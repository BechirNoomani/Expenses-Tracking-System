package com.project.expensets.controller;

import com.project.expensets.model.Transaction;
import com.project.expensets.service.ExpenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/expenses")
public class ExpenseController {
    private static final Logger log = LoggerFactory.getLogger(ExpenseController.class);
    @Autowired
    private ExpenseService expenseService;


    @GetMapping("/add")
    public String showAddExpenseForm(Model model) {
        model.addAttribute("expense", new Transaction());
        return "Transactions/add_expense";
    }

    @PostMapping("/add")
    public String modifyExpense(@Valid @ModelAttribute("expense") Transaction expense, BindingResult result, Model model) {
        try {
            expense.setType("Expense");
            if (result.hasErrors()) {
                return "Transactions/add_expense";
            }

            Long id = expense.getId();


            if (id != null) {

                expenseService.updateExpense(expense);
            } else {

                expenseService.addExpense(expense);
            }
            return "redirect:/";
        } catch (Exception e) {

            log.error("Error while modifying expense", e);
            model.addAttribute("errorMessage", "Error modifying expense");
            return "error";
        }
    }


    @GetMapping("/update/{id}")
    public String showUpdateExpenseForm(@PathVariable Long id, Model model) {
        try {
            Transaction expense = expenseService.findById(id);
            if (expense != null) {
                model.addAttribute("expense", expense);
                return "Transactions/add_expense";
            } else {

                model.addAttribute("errorMessage", "Expense not found for update");
                return "error";
            }
        } catch (Exception e) {

            log.error("Error while loading expense for update", e);
            model.addAttribute("errorMessage", "Error loading expense for update");
            return "error";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id, Model model) {
        try {
            expenseService.deleteExpense(id);
            return "redirect:/";
        } catch (Exception e) {

            log.error("Error while deleting expense", e);
            model.addAttribute("errorMessage", "Error deleting expense");
            return "error";
        }
    }




}
