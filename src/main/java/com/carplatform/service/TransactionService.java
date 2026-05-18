package com.carplatform.service;

import com.carplatform.model.*;
import com.carplatform.util.InquiryFileHandler;
import com.carplatform.util.PurchaseFileHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Service managing vehicle inquiries and purchase transactions.
 *
 * OOP: Encapsulation – all logic hidden from controllers.
 *      Abstraction   – hides file handler complexity.
 * Member 3 – Purchase & Inquiry Management Module
 */
@Service
public class TransactionService {

    private final InquiryFileHandler  inquiryHandler;
    private final PurchaseFileHandler purchaseHandler;

    public TransactionService(@Value("${app.data.directory:data}") String dataDir) {
        this.inquiryHandler  = new InquiryFileHandler(dataDir);
        this.purchaseHandler = new PurchaseFileHandler(dataDir);
    }

    // ── INQUIRY CRUD ──────────────────────────────────────────────────────

    public String submitInquiry(String carId, String buyerId, String sellerId,
                                String message, String email, String phone) {
        if (message == null || message.isBlank()) return "EMPTY_MESSAGE";
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) return "INVALID_EMAIL";

        String id = "INQ-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Inquiry inq = new Inquiry(id, carId, buyerId, sellerId, message, email, phone,
                LocalDate.now().toString());
        return inquiryHandler.saveInquiry(inq) ? id : "SAVE_ERROR";
    }

    public List<Inquiry> getInquiriesForBuyer(String buyerId) {
        return inquiryHandler.findByBuyer(buyerId);
    }

    public List<Inquiry> getInquiriesForCar(String carId) {
        return inquiryHandler.findByCar(carId);
    }

    public List<Inquiry> getAllInquiries() {
        return inquiryHandler.getAllInquiries();
    }

    public boolean respondToInquiry(String inquiryId, String responseMessage) {
        Inquiry inq = inquiryHandler.findById(inquiryId);
        if (inq == null) return false;
        inq.setResponseMessage(responseMessage);
        inq.setResponseDate(LocalDate.now().toString());
        inq.setStatus(Inquiry.Status.RESPONDED);
        return inquiryHandler.updateInquiry(inq);
    }

    public boolean closeInquiry(String inquiryId) {
        Inquiry inq = inquiryHandler.findById(inquiryId);
        if (inq == null) return false;
        inq.setStatus(Inquiry.Status.CLOSED);
        return inquiryHandler.updateInquiry(inq);
    }

    public boolean deleteInquiry(String inquiryId) {
        return inquiryHandler.deleteInquiry(inquiryId);
    }

    // ── PURCHASE CRUD ─────────────────────────────────────────────────────

    public String createPurchase(String carId, String buyerId, String sellerId,
                                 double agreedPrice, String paymentMethodStr) {
        if (agreedPrice <= 0) return "INVALID_PRICE";

        Purchase.PaymentMethod pm;
        try { pm = Purchase.PaymentMethod.valueOf(paymentMethodStr.toUpperCase()); }
        catch (Exception e) { pm = Purchase.PaymentMethod.CASH; }

        String id = "PUR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Purchase p = new Purchase(id, carId, buyerId, sellerId, agreedPrice, pm,
                LocalDate.now().toString());
        return purchaseHandler.savePurchase(p) ? id : "SAVE_ERROR";
    }

    public List<Purchase> getPurchasesForBuyer(String buyerId) {
        return purchaseHandler.findByBuyer(buyerId);
    }

    public List<Purchase> getPurchasesForSeller(String sellerId) {
        return purchaseHandler.findBySeller(sellerId);
    }

    public List<Purchase> getAllPurchases() {
        return purchaseHandler.getAllPurchases();
    }

    public boolean confirmPurchase(String purchaseId) {
        Purchase p = purchaseHandler.findById(purchaseId);
        if (p == null) return false;
        p.setPurchaseStatus(Purchase.PurchaseStatus.CONFIRMED);
        return purchaseHandler.updatePurchase(p);
    }

    public boolean completePurchase(String purchaseId) {
        Purchase p = purchaseHandler.findById(purchaseId);
        if (p == null) return false;
        p.setPurchaseStatus(Purchase.PurchaseStatus.COMPLETED);
        p.setCompletionDate(LocalDate.now().toString());
        return purchaseHandler.updatePurchase(p);
    }

    public boolean cancelPurchase(String purchaseId) {
        Purchase p = purchaseHandler.findById(purchaseId);
        if (p == null) return false;
        p.setPurchaseStatus(Purchase.PurchaseStatus.CANCELLED);
        return purchaseHandler.updatePurchase(p);
    }

    public boolean deletePurchaseRecord(String purchaseId) {
        return purchaseHandler.deletePurchase(purchaseId);
    }
}
