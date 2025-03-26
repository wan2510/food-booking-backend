package com.app.food_booking_backend.service;


import com.app.food_booking_backend.model.dto.PaidByDateStats;
import com.app.food_booking_backend.model.entity.Invoice;
import com.app.food_booking_backend.model.request.InvoiceRequest;
import com.app.food_booking_backend.model.response.InvoiceResponse;
import com.app.food_booking_backend.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    // Sửa phương thức createInvoice để nhận InvoiceRequest thay vì InvoiceDTO
    public InvoiceResponse createInvoice(InvoiceRequest invoiceRequest) {
        Invoice invoice = new Invoice();
        invoice.setCustomer(invoiceRequest.getCustomer());
        invoice.setAmount(invoiceRequest.getAmount());
        invoice.setType(invoiceRequest.getType());
        invoice.setDate(LocalDate.now());
        invoice.setStatus("Chưa thanh toán"); // Trạng thái mặc định

        Invoice savedInvoice = invoiceRepository.save(invoice);
        return mapToResponse(savedInvoice);
    }

    // Thêm phương thức getInvoiceById để lấy hóa đơn theo uuid (Long)
    public InvoiceResponse getInvoiceById(Long uuid) {
        Invoice invoice = invoiceRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại với UUID: " + uuid));
        return mapToResponse(invoice);
    }

    // Sửa phương thức updateStatus để nhận (Long, String) thay vì (String, String)
    public InvoiceResponse updateStatus(Long uuid, String status) {
        Invoice invoice = invoiceRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại với UUID: " + uuid));
        invoice.setStatus(status);
        Invoice updatedInvoice = invoiceRepository.save(invoice);
        return mapToResponse(updatedInvoice);
    }

    // Sửa phương thức deleteInvoice để nhận Long thay vì String
    public void deleteInvoice(Long uuid) {
        Invoice invoice = invoiceRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại với UUID: " + uuid));
        invoiceRepository.delete(invoice);
    }

    // Các phương thức khác (đã có sẵn từ trước)
    public List<InvoiceResponse> getInvoices(String status, String type, String searchTerm, LocalDate startDate, LocalDate endDate) {
        List<Invoice> invoices = invoiceRepository.findAll();

        return invoices.stream()
                .filter(invoice -> (status == null || status.equals("Tất cả") || invoice.getStatus().equals(status)))
                .filter(invoice -> (type == null || type.equals("Tất cả") || invoice.getType().equals(type)))
                .filter(invoice -> (searchTerm == null ||
                        invoice.getCustomer().contains(searchTerm) ||
                        String.valueOf(invoice.getUuid()).contains(searchTerm)))
                .filter(invoice -> {
                    if (startDate == null || endDate == null) return true;
                    LocalDate invoiceDate = invoice.getDate();
                    return !invoiceDate.isBefore(startDate) && !invoiceDate.isAfter(endDate);
                })
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<InvoiceResponse> getPastPaidInvoices(String searchTerm, LocalDate startDate, LocalDate endDate) {
        List<Invoice> invoices = invoiceRepository.findAll();

        return invoices.stream()
                .filter(invoice -> invoice.getStatus().equals("Đã thanh toán"))
                .filter(invoice -> (searchTerm == null ||
                        invoice.getCustomer().contains(searchTerm) ||
                        String.valueOf(invoice.getUuid()).contains(searchTerm)))
                .filter(invoice -> {
                    if (startDate == null || endDate == null) return true;
                    LocalDate invoiceDate = invoice.getDate();
                    return !invoiceDate.isBefore(startDate) && !invoiceDate.isAfter(endDate);
                })
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<PaidByDateStats> getPaidByDateStats(LocalDate startDate, LocalDate endDate) {
        List<Invoice> invoices = invoiceRepository.findAll();

        return invoices.stream()
                .filter(invoice -> invoice.getStatus().equals("Đã thanh toán"))
                .filter(invoice -> {
                    if (startDate == null || endDate == null) return true;
                    LocalDate invoiceDate = invoice.getDate();
                    return !invoiceDate.isBefore(startDate) && !invoiceDate.isAfter(endDate);
                })
                .collect(Collectors.groupingBy(
                        Invoice::getDate,
                        Collectors.summarizingDouble(Invoice::getAmount)))
                .entrySet().stream()
                .map(entry -> new PaidByDateStats(
                        entry.getKey(),
                        (int) entry.getValue().getCount(),
                        entry.getValue().getSum()))
                .collect(Collectors.toList());
    }

    // Phương thức hỗ trợ để ánh xạ từ Invoice sang InvoiceResponse
    private InvoiceResponse mapToResponse(Invoice invoice) {
        InvoiceResponse response = new InvoiceResponse();
        response.setUuid(invoice.getUuid());
        response.setCustomer(invoice.getCustomer());
        response.setAmount(invoice.getAmount());
        response.setDate(invoice.getDate());
        response.setType(invoice.getType());
        response.setStatus(invoice.getStatus());
        return response;
    }
}