package com.carplatform.util;

import com.carplatform.model.Inquiry;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles file I/O for Inquiry records (inquiries.txt).
 * Member 3 – Purchase & Inquiry Management Module
 */
public class InquiryFileHandler {

    private final String filePath;

    public InquiryFileHandler(String dataDir) {
        this.filePath = dataDir + File.separator + "inquiries.txt";
        ensureFileExists();
    }

    private void ensureFileExists() {
        try { File f = new File(filePath); f.getParentFile().mkdirs(); if (!f.exists()) f.createNewFile(); }
        catch (IOException e) { System.err.println("[InquiryFileHandler] " + e.getMessage()); }
    }

    public boolean saveInquiry(Inquiry inq) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(filePath, true))) {
            w.write(inq.toFileString()); w.newLine(); return true;
        } catch (IOException e) { return false; }
    }

    public List<Inquiry> getAllInquiries() {
        List<Inquiry> list = new ArrayList<>();
        try (BufferedReader r = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = r.readLine()) != null) {
                if (!line.isBlank()) { Inquiry i = Inquiry.fromFileString(line); if (i != null) list.add(i); }
            }
        } catch (IOException e) { System.err.println("[InquiryFileHandler] Read error: " + e.getMessage()); }
        return list;
    }

    public Inquiry findById(String id) {
        return getAllInquiries().stream().filter(i -> i.getInquiryId().equals(id)).findFirst().orElse(null);
    }

    public List<Inquiry> findByBuyer(String buyerId) {
        return getAllInquiries().stream().filter(i -> i.getBuyerId().equals(buyerId)).collect(Collectors.toList());
    }

    public List<Inquiry> findByCar(String carId) {
        return getAllInquiries().stream().filter(i -> i.getCarId().equals(carId)).collect(Collectors.toList());
    }

    public boolean updateInquiry(Inquiry updated) {
        List<Inquiry> all = getAllInquiries();
        boolean found = false;
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getInquiryId().equals(updated.getInquiryId())) { all.set(i, updated); found = true; break; }
        }
        return found && rewrite(all);
    }

    public boolean deleteInquiry(String id) {
        List<Inquiry> all = getAllInquiries();
        boolean r = all.removeIf(i -> i.getInquiryId().equals(id));
        return r && rewrite(all);
    }

    private boolean rewrite(List<Inquiry> list) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(filePath, false))) {
            for (Inquiry i : list) { w.write(i.toFileString()); w.newLine(); } return true;
        } catch (IOException e) { return false; }
    }
}
