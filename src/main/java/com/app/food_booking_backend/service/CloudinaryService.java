package com.app.food_booking_backend.service;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File không được để trống");
        }

        // Tạo tên file ngẫu nhiên để tránh trùng lặp
        String fileName = UUID.randomUUID().toString();
        
        // Upload file lên Cloudinary
        Map<?, ?> uploadResult = cloudinary.uploader().upload(
            file.getBytes(),
            ObjectUtils.asMap(
                "public_id", "food-booking/" + fileName,
                "overwrite", true,
                "resource_type", "auto"
            )
        );
        
        // Trả về URL của ảnh đã upload
        return uploadResult.get("secure_url").toString();
    }
    
    public void deleteImage(String imageUrl) {
        try {
            if (imageUrl == null || imageUrl.isEmpty()) {
                return;
            }
            
            // Lấy public_id từ URL
            String publicId = extractPublicIdFromUrl(imageUrl);
            
            if (publicId != null) {
                // Xóa ảnh từ Cloudinary
                cloudinary.uploader().destroy(
                    publicId,
                    ObjectUtils.emptyMap()
                );
            }
        } catch (Exception e) {
            // Log lỗi nhưng không throw exception để không ảnh hưởng đến luồng chính
            System.err.println("Lỗi khi xóa ảnh từ Cloudinary: " + e.getMessage());
        }
    }
    
    private String extractPublicIdFromUrl(String imageUrl) {
        // Ví dụ URL: https://res.cloudinary.com/your-cloud-name/image/upload/v1234567890/food-booking/abc123.jpg
        // Public ID: food-booking/abc123
        try {
            if (imageUrl.contains("/upload/")) {
                String[] parts = imageUrl.split("/upload/");
                if (parts.length > 1) {
                    String part = parts[1];
                    // Loại bỏ version nếu có
                    if (part.startsWith("v")) {
                        int slashIndex = part.indexOf("/");
                        if (slashIndex != -1) {
                            part = part.substring(slashIndex + 1);
                        }
                    }
                    // Loại bỏ phần mở rộng file
                    int dotIndex = part.lastIndexOf(".");
                    if (dotIndex != -1) {
                        part = part.substring(0, dotIndex);
                    }
                    return part;
                }
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi trích xuất public_id: " + e.getMessage());
        }
        return null;
    }
} 