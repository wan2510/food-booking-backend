package com.app.food_booking_backend.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.app.food_booking_backend.repository.VoucherRepository;
import com.app.food_booking_backend.model.dto.VoucherDTO;
import com.app.food_booking_backend.model.entity.Voucher;
import com.app.food_booking_backend.model.entity.enums.VoucherStatusEnum;

@Service
@Transactional
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final ModelMapper modelMapper;

    public VoucherService(VoucherRepository voucherRepository, ModelMapper modelMapper) {
        this.voucherRepository = voucherRepository;
        this.modelMapper = modelMapper;
    }

    // Chuyển từ VoucherDTO sang Voucher (entity)
    private Voucher toVoucher(VoucherDTO voucherDTO) {
        // Kiểm tra null cho voucherDTO
        if (voucherDTO == null) {
            throw new IllegalArgumentException("Không nhận được thông tin voucher!");
        }
        Voucher voucher = modelMapper.map(voucherDTO, Voucher.class);
        return voucher;
    }

    // Chuyển từ Voucher (entity) sang VoucherDTO
    private VoucherDTO toVoucherDTO(Voucher voucher) {
        // Kiểm tra null
        if (voucher == null) {
            throw new IllegalArgumentException("Thông tin Voucher không nhận diện được!");
        }
        return modelMapper.map(voucher, VoucherDTO.class);
    }

    // Lấy danh sách tất cả voucher từ DB và chuyển thành DTO
    public List<VoucherDTO> getVouchers() {
        try {
            return voucherRepository.findAll().stream()
                .map(this::toVoucherDTO)
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy danh sách voucher: " + e.getMessage());
        }
    }

    // Thêm một voucher mới vào DB
    public Voucher addVoucher(VoucherDTO voucherDTO) {
        try {
            Voucher voucher = toVoucher(voucherDTO);
            return voucherRepository.save(voucher);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi thêm voucher: " + e.getMessage());
        }
    }

    // Cập nhật thông tin một voucher
    public Voucher updateVoucher(VoucherDTO voucherDTO) {
        try {
            Optional<Voucher> existingVoucher = voucherRepository.findById(voucherDTO.getId());
            if (existingVoucher.isPresent()) {
                Voucher voucher = existingVoucher.get();
                modelMapper.map(voucherDTO, voucher);
                return voucherRepository.save(voucher);
            } else {
                throw new RuntimeException("Không tìm thấy voucher với id: " + voucherDTO.getId());
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi cập nhật voucher: " + e.getMessage());
        }
    }

    // Chạy tự động mỗi ngày lúc 0h để cập nhật trạng thái voucher hết hạn
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void updateExpiredVouchers() {
        try {
            List<Voucher> expiredVouchers = voucherRepository.findExpiredVouchers(LocalDateTime.now());
            expiredVouchers.forEach(voucher -> voucher.setStatus(VoucherStatusEnum.INACTIVE));
            voucherRepository.saveAll(expiredVouchers);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi cập nhật trạng thái voucher hết hạn: " + e.getMessage());
        }
    }

    // Getter để controller có thể truy cập repository nếu cần
    public VoucherRepository getVoucherRepository() {
        return voucherRepository;
    }
}