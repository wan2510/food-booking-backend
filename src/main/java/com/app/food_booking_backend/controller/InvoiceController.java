package com.app.food_booking_backend.controller;

import com.app.food_booking_backend.model.dto.PaidByDateStats;
import com.app.food_booking_backend.model.request.InvoiceRequest;
import com.app.food_booking_backend.model.response.InvoiceResponse;
import com.app.food_booking_backend.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public ResponseEntity<List<InvoiceResponse>> getInvoices(
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "searchTerm", required = false) String searchTerm,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        List<InvoiceResponse> invoices = invoiceService.getInvoices(status, type, searchTerm, startDate, endDate);
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/past-paid")
    public ResponseEntity<List<InvoiceResponse>> getPastPaidInvoices(
            @RequestParam(name = "searchTerm", required = false) String searchTerm,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        List<InvoiceResponse> invoices = invoiceService.getPastPaidInvoices(searchTerm, startDate, endDate);
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/stats/paid-by-date")
    public ResponseEntity<List<PaidByDateStats>> getPaidByDateStats(
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        List<PaidByDateStats> stats = invoiceService.getPaidByDateStats(startDate, endDate);
        return ResponseEntity.ok(stats);
    }

    @PostMapping
    public ResponseEntity<InvoiceResponse> createInvoice(@RequestBody InvoiceRequest invoiceRequest) {
        InvoiceResponse invoice = invoiceService.createInvoice(invoiceRequest);
        return ResponseEntity.ok(invoice);
    }

    @GetMapping("/{uuid}")
public ResponseEntity<InvoiceResponse> getInvoiceById(@PathVariable(name = "uuid") Long uuid) {
    InvoiceResponse invoice = invoiceService.getInvoiceById(uuid);
    return ResponseEntity.ok(invoice);
}

    @PutMapping("/{uuid}/status")
public ResponseEntity<InvoiceResponse> updateStatus(
        @PathVariable(name = "uuid") Long uuid,
        @RequestParam(name = "status") String status) {
    InvoiceResponse updatedInvoice = invoiceService.updateStatus(uuid, status);
    return ResponseEntity.ok(updatedInvoice);
}

@DeleteMapping("/{uuid}")
public ResponseEntity<Void> deleteInvoice(@PathVariable(name = "uuid") Long uuid) {
    invoiceService.deleteInvoice(uuid);
    return ResponseEntity.ok().build();
}
}