package com.project.expensets.controller;

import com.project.expensets.model.Transaction;
import com.project.expensets.service.IncomeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/incomes")
public class IncomeController {
    private static final Logger log = LoggerFactory.getLogger(IncomeController.class);
    @Autowired
    private IncomeService incomeService;


    @GetMapping("/add")
    public String showAddIncomeForm(Model model) {
        model.addAttribute("income", new Transaction());
        return "Transactions/add_income";
    }

    @PostMapping("/add")
    public String modifyIncome(@Valid @ModelAttribute("income") Transaction income, BindingResult result, Model model) {
        try {
            income.setType("Income");
            if (result.hasErrors()) {
                return "Transactions/add_income";
            }

            Long id = income.getId();


            if (id != null) {

                incomeService.updateIncome(income);
            } else {

                incomeService.addIncome(income);
            }
            return "redirect:/";
        } catch (Exception e) {

            log.error("Error while modifying income", e);
            model.addAttribute("errorMessage", "Error modifying income");
            return "error";
        }
    }


    @GetMapping("/update/{id}")
    public String showUpdateIncomeForm(@PathVariable Long id, Model model) {
        try {
            Transaction income = incomeService.findById(id);
            if (income != null) {
                model.addAttribute("income", income);
                return "Transactions/add_income";
            } else {

                model.addAttribute("errorMessage", "Income not found for update");
                return "error";
            }
        } catch (Exception e) {

            log.error("Error while loading income for update", e);
            model.addAttribute("errorMessage", "Error loading income for update");
            return "error";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteIncome(@PathVariable Long id, Model model) {
        try {
            incomeService.deleteIncome(id);
            return "redirect:/";
        } catch (Exception e) {

            log.error("Error while deleting income", e);
            model.addAttribute("errorMessage", "Error deleting income");
            return "error";
        }
    }




}
