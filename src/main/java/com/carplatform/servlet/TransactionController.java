package com.carplatform.servlet;

import com.carplatform.model.*;
import com.carplatform.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller for Inquiry and Purchase transactions.
 * Member 3 – Purchase and Inquiry Management Module
 */
@Controller
public class TransactionController {

    private final TransactionService transactionService;
    private final CarService carService;

    public TransactionController(TransactionService ts, CarService cs) {
        this.transactionService = ts;
        this.carService         = cs;
    }

    // ── INQUIRY SUBMISSION ────────────────────────────────────────────────

    @GetMapping("/inquiry/{carId}")
    public String showInquiry(@PathVariable String carId, HttpSession session, Model model) {
        if (session.getAttribute("loggedUser") == null) return "redirect:/login";
        Car car = carService.getCarById(carId);
        if (!carService.mayBuyerTransact(car)) return "redirect:/cars";
        model.addAttribute("car", car);
        model.addAttribute("loggedUser", session.getAttribute("loggedUser"));
        return "inquiry-form";
    }

    @PostMapping("/inquiry/submit")
    public String submitInquiry(@RequestParam String carId,
                                @RequestParam String sellerId,
                                @RequestParam String message,
                                @RequestParam String contactEmail,
                                @RequestParam String contactPhone,
                                HttpSession session, RedirectAttributes ra) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";
        Car car = carService.getCarById(carId);
        if (!carService.mayBuyerTransact(car)) {
            ra.addFlashAttribute("error", "That listing is not available for inquiry.");
            return "redirect:/cars";
        }
        String result = transactionService.submitInquiry(carId, user.getUserId(),
                sellerId, message, contactEmail, contactPhone);
        if (result.startsWith("INQ-")) {
            ra.addFlashAttribute("success", "Inquiry submitted successfully! Reference: " + result);
        } else {
            ra.addFlashAttribute("error", "Failed to submit inquiry: " + result);
        }
        return "redirect:/cars/" + carId;
    }

    // ── MY INQUIRIES ──────────────────────────────────────────────────────

    @GetMapping("/inquiry/my")
    public String myInquiries(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";
        model.addAttribute("inquiries", transactionService.getInquiriesForBuyer(user.getUserId()));
        model.addAttribute("loggedUser", user);
        return "inquiry-list";
    }

    @PostMapping("/inquiry/delete/{id}")
    public String deleteInquiry(@PathVariable String id, HttpSession session, RedirectAttributes ra) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";
        transactionService.deleteInquiry(id);
        ra.addFlashAttribute("success", "Inquiry deleted.");
        return "redirect:/inquiry/my";
    }

    // ── PURCHASE ──────────────────────────────────────────────────────────

    @GetMapping("/purchase/{carId}")
    public String showPurchase(@PathVariable String carId, HttpSession session, Model model) {
        if (session.getAttribute("loggedUser") == null) return "redirect:/login";
        Car car = carService.getCarById(carId);
        if (!carService.mayBuyerTransact(car)) return "redirect:/cars";
        model.addAttribute("car", car);
        model.addAttribute("loggedUser", session.getAttribute("loggedUser"));
        return "purchase-form";
    }

    @PostMapping("/purchase/submit")
    public String submitPurchase(@RequestParam String carId,
                                 @RequestParam String sellerId,
                                 @RequestParam double agreedPrice,
                                 @RequestParam String paymentMethod,
                                 @RequestParam(defaultValue = "") String notes,
                                 HttpSession session, RedirectAttributes ra) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";
        Car car = carService.getCarById(carId);
        if (!carService.mayBuyerTransact(car)) {
            ra.addFlashAttribute("error", "That listing is not available for purchase.");
            return "redirect:/cars";
        }
        String result = transactionService.createPurchase(carId, user.getUserId(),
                sellerId, agreedPrice, paymentMethod);
        if (result.startsWith("PUR-")) {
            ra.addFlashAttribute("success", "Purchase request submitted! Reference: " + result);
            return "redirect:/purchase/history";
        }
        ra.addFlashAttribute("error", "Purchase failed: " + result);
        return "redirect:/purchase/" + carId;
    }

    // ── PURCHASE HISTORY ──────────────────────────────────────────────────

    @GetMapping("/purchase/history")
    public String purchaseHistory(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";
        model.addAttribute("purchases", transactionService.getPurchasesForBuyer(user.getUserId()));
        model.addAttribute("loggedUser", user);
        return "purchase-history";
    }

    @PostMapping("/purchase/cancel/{id}")
    public String cancelPurchase(@PathVariable String id, HttpSession session, RedirectAttributes ra) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";
        transactionService.cancelPurchase(id);
        ra.addFlashAttribute("success", "Purchase cancelled.");
        return "redirect:/purchase/history";
    }
}
