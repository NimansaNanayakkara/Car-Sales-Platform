package com.carplatform.model;

/**
 * Represents a completed vehicle purchase transaction.
 *
 * OOP Concepts:
 *  - Encapsulation : All transaction data hidden behind accessors
 *  - Abstraction   : Status transitions managed via service
 *
 * Member 3 – Purchase & Inquiry Management Module
 */
public class Purchase {

    public enum PaymentMethod { CASH, BANK_TRANSFER, INSTALLMENT }
    public enum PurchaseStatus { PENDING, CONFIRMED, COMPLETED, CANCELLED }

    private String         purchaseId;
    private String         carId;
    private String         buyerId;
    private String         sellerId;
    private double         agreedPrice;
    private PaymentMethod  paymentMethod;
    private PurchaseStatus purchaseStatus;
    private String         purchaseDate;
    private String         completionDate;
    private String         notes;

    // ─── Constructors ──────────────────────────────────────────────────────

    public Purchase() {
        this.purchaseStatus = PurchaseStatus.PENDING;
        this.paymentMethod  = PaymentMethod.CASH;
    }

    public Purchase(String purchaseId, String carId, String buyerId, String sellerId,
                    double agreedPrice, PaymentMethod paymentMethod, String purchaseDate) {
        this.purchaseId     = purchaseId;
        this.carId          = carId;
        this.buyerId        = buyerId;
        this.sellerId       = sellerId;
        this.agreedPrice    = agreedPrice;
        this.paymentMethod  = paymentMethod;
        this.purchaseDate   = purchaseDate;
        this.purchaseStatus = PurchaseStatus.PENDING;
    }

    // ─── File Serialisation ───────────────────────────────────────────────

    public String toFileString() {
        return purchaseId + "|" + carId + "|" + buyerId + "|" + sellerId + "|"
                + agreedPrice + "|" + paymentMethod.name() + "|"
                + purchaseStatus.name() + "|" + purchaseDate + "|"
                + (completionDate != null ? completionDate : "N/A") + "|"
                + (notes          != null ? notes          : "N/A");
    }

    public static Purchase fromFileString(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length < 10) return null;
        Purchase pur = new Purchase(p[0], p[1], p[2], p[3],
                Double.parseDouble(p[4]),
                PaymentMethod.valueOf(p[5]),
                p[7]);
        pur.setPurchaseStatus(PurchaseStatus.valueOf(p[6]));
        pur.setCompletionDate("N/A".equals(p[8]) ? null : p[8]);
        pur.setNotes         ("N/A".equals(p[9]) ? null : p[9]);
        return pur;
    }

    // ─── Getters & Setters ────────────────────────────────────────────────

    public String getPurchaseId()                            { return purchaseId; }
    public void   setPurchaseId(String id)                   { this.purchaseId = id; }

    public String getCarId()                                 { return carId; }
    public void   setCarId(String carId)                     { this.carId = carId; }

    public String getBuyerId()                               { return buyerId; }
    public void   setBuyerId(String buyerId)                 { this.buyerId = buyerId; }

    public String getSellerId()                              { return sellerId; }
    public void   setSellerId(String sellerId)               { this.sellerId = sellerId; }

    public double getAgreedPrice()                           { return agreedPrice; }
    public void   setAgreedPrice(double p)                   { this.agreedPrice = p; }

    public PaymentMethod getPaymentMethod()                  { return paymentMethod; }
    public void          setPaymentMethod(PaymentMethod m)   { this.paymentMethod = m; }

    public PurchaseStatus getPurchaseStatus()                { return purchaseStatus; }
    public void           setPurchaseStatus(PurchaseStatus s){ this.purchaseStatus = s; }

    public String getPurchaseDate()                          { return purchaseDate; }
    public void   setPurchaseDate(String d)                  { this.purchaseDate = d; }

    public String getCompletionDate()                        { return completionDate; }
    public void   setCompletionDate(String d)                { this.completionDate = d; }

    public String getNotes()                                 { return notes; }
    public void   setNotes(String n)                         { this.notes = n; }
}
