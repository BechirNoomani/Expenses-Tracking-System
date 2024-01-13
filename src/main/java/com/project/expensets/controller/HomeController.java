package com.project.expensets.controller;

import com.project.expensets.model.Transaction;
import com.project.expensets.service.HomeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Locale;

@Controller
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(ExpenseController.class);

    @Autowired
    private HomeService homeService;




    @GetMapping
    public String getAllTransactions(Model model,
                                     @PageableDefault( sort = "date", direction = Sort.Direction.DESC) Pageable pageable,
                                     @RequestParam(value = "startDate", required = false) String startDate,
                                     @RequestParam(value = "endDate", required = false) String endDate,
                                     @RequestParam(value = "descriptionFilter", required = false) String descriptionFilter,
                                     @RequestParam(value = "minAmount", required = false) Double minAmount,
                                     @RequestParam(value = "maxAmount", required = false) Double maxAmount) {
        try {

            List<Transaction> allTransactions = homeService.findAll();
            Page<Transaction> transactionsPage = homeService.findAllFiltered(pageable, startDate, endDate, descriptionFilter, minAmount, maxAmount);

            model.addAttribute("transactions", transactionsPage);
            model.addAttribute("startDate", startDate);
            model.addAttribute("endDate", endDate);
            model.addAttribute("descriptionFilter", descriptionFilter);
            model.addAttribute("minAmount", minAmount);
            model.addAttribute("maxAmount", maxAmount);

            double totalExpenses = allTransactions.stream()
                    .filter(transaction -> "Expense".equals(transaction.getType()))
                    .mapToDouble(Transaction::getAmount)
                    .sum();

            double totalIncome = allTransactions.stream()
                    .filter(transaction -> "Income".equals(transaction.getType()))
                    .mapToDouble(Transaction::getAmount)
                    .sum();

            double balance = totalIncome - totalExpenses;
            balance = Double.parseDouble(String.valueOf(balance));
            String formattedTotalExpenses = String.format(Locale.US, "%.1f", totalExpenses);
            String formattedTotalIncome = String.format(Locale.US, "%.1f", totalIncome);
            String formattedBalance = String.format(Locale.US, "%.1f", balance);

            model.addAttribute("totalExpenses", formattedTotalExpenses);
            model.addAttribute("totalIncome", formattedTotalIncome);
            model.addAttribute("balance", formattedBalance);

            Double unfilteredMaxAmount = allTransactions.stream().mapToDouble(Transaction::getAmount).max().orElse(0.0);
            Double unfilteredMinAmount = allTransactions.stream().mapToDouble(Transaction::getAmount).min().orElse(0.0);
            model.addAttribute("max", unfilteredMaxAmount);
            model.addAttribute("min", unfilteredMinAmount);

            return "home";
        } catch (Exception e) {
            log.error("Error while retrieving transactions", e);
            model.addAttribute("errorMessage", "Error retrieving transactions");
            return "error";
        }
    }


}
