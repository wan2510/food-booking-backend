package com.app.food_booking_backend.service;

import com.app.food_booking_backend.exception.ResourceNotFoundException;

import com.app.food_booking_backend.model.dto.VoucherDTO;
import com.app.food_booking_backend.model.entity.Voucher;
import com.app.food_booking_backend.repository.VoucherRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    public List<VoucherDTO> getAllVouchers() {
        return voucherRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public VoucherDTO getVoucherByUuid(String uuid) {
        Voucher voucher = voucherRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Voucher not found with UUID: " + uuid));
        return convertToDTO(voucher);
    }

    @Transactional
    public VoucherDTO createVoucher(VoucherDTO voucherDTO) {
        validateVoucherDTO(voucherDTO);

        Voucher voucher = Voucher.builder()
                .uuid(UUID.randomUUID().toString())
                .code(voucherDTO.getCode())
                .discount(voucherDTO.getDiscount())
                .startDate(voucherDTO.getStartDate())
                .endDate(voucherDTO.getEndDate())
                .status(voucherDTO.getStatus())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Voucher savedVoucher = voucherRepository.save(voucher);
        return convertToDTO(savedVoucher);
    }

    @Transactional
    public VoucherDTO updateVoucher(String uuid, VoucherDTO voucherDTO) {
        validateVoucherDTO(voucherDTO);

        Voucher voucher = voucherRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Voucher not found with UUID: " + uuid));

        voucher.setCode(voucherDTO.getCode());
        voucher.setDiscount(voucherDTO.getDiscount());
        voucher.setStartDate(voucherDTO.getStartDate());
        voucher.setEndDate(voucherDTO.getEndDate());
        voucher.setStatus(voucherDTO.getStatus());
        voucher.setUpdatedAt(LocalDateTime.now());

        Voucher updatedVoucher = voucherRepository.save(voucher);
        return convertToDTO(updatedVoucher);
    }

    @Transactional
    public void deleteVoucher(String uuid) {
        Voucher voucher = voucherRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Voucher not found with UUID: " + uuid));
        voucherRepository.delete(voucher);
    }

    private VoucherDTO convertToDTO(Voucher voucher) {
        VoucherDTO dto = new VoucherDTO();
        dto.setUuid(voucher.getUuid());
        dto.setCode(voucher.getCode());
        dto.setDiscount(voucher.getDiscount());
        dto.setStartDate(voucher.getStartDate());
        dto.setEndDate(voucher.getEndDate());
        dto.setStatus(voucher.getStatus());
        dto.setCreatedAt(voucher.getCreatedAt());
        dto.setUpdatedAt(voucher.getUpdatedAt());
        return dto;
    }

    private void validateVoucherDTO(VoucherDTO voucherDTO) {
        if (voucherDTO.getCode() == null || voucherDTO.getCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Voucher code cannot be empty");
        }
        if (voucherDTO.getDiscount() == null || voucherDTO.getDiscount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Discount must be greater than 0");
        }
        if (voucherDTO.getStartDate() == null || voucherDTO.getEndDate() == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null");
        }
        if (voucherDTO.getEndDate().isBefore(voucherDTO.getStartDate())) {
            throw new IllegalArgumentException("End date must be after start date");
        }
        if (voucherDTO.getStatus() == null || voucherDTO.getStatus().trim().isEmpty()) {
            throw new IllegalArgumentException("Voucher status cannot be empty");
        }
    }
}