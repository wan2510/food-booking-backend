package com.app.food_booking_backend.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.food_booking_backend.model.dto.FoodDTO;
import com.app.food_booking_backend.service.FoodService;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping
    public List<FoodDTO> getAllFoods() {
        return foodService.getAllFoods();
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getFoodByUuid(@PathVariable String uuid) {
        if (uuid == null || uuid.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "UUID không hợp lệ"));
        }
        FoodDTO foodDTO = foodService.getFoodByUuid(uuid);
        if (foodDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foodDTO);
    }

    @GetMapping("/search")
    public List<FoodDTO> searchFoods(
            @RequestParam(required = false) String query,
            @RequestParam(required = false, defaultValue = "all") String categoryUuid,
            @RequestParam(required = false, defaultValue = "all") String priceFilter
    ) {
        return foodService.searchFoods(query, categoryUuid, priceFilter);
    }

    // ===========================
    //  API UPLOAD ẢNH CHO FOOD
    // ===========================
    @PostMapping("/{uuid}/upload-image")
    public ResponseEntity<?> uploadImage(
            @PathVariable String uuid,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            // 1) Kiểm tra Food tồn tại
            FoodDTO foodDTO = foodService.getFoodByUuid(uuid);
            if (foodDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body(Map.of("error", "Food không tồn tại"));
            }

            // 2) Kiểm tra tên file hợp lệ
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.isBlank()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Tên file không hợp lệ"));
            }

            // 3) Lưu file vào thư mục src/Image
            //    - Tạo thư mục nếu chưa có
            Path imageDir = Paths.get("src", "Image").toAbsolutePath().normalize();
            Files.createDirectories(imageDir);

            //    - Tạo đường dẫn file đích
            //      => Có thể dùng UUID.randomUUID() để tránh trùng tên
            Path targetPath = imageDir.resolve(originalFilename);

            //    - Copy file vào folder
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            // 4) Tạo URL để lưu vào DB (tùy cấu hình hiển thị ảnh)
            //    Ví dụ: http://localhost:8080/Image/burgerbo.jpg
            //    => Muốn ảnh truy cập được, bạn phải cấu hình static resource / Security
            String imageUrl = "http://localhost:8080/Image/" + originalFilename;

            // 5) Cập nhật imageUrl trong DB
            foodDTO.setImageUrl(imageUrl);
            // Giả sử bạn có hàm updateFood trong service
            foodService.updateFood(foodDTO);

            return ResponseEntity.ok(Map.of(
                    "message", "Upload thành công",
                    "imageUrl", imageUrl
            ));

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "Lỗi upload file: " + e.getMessage()));
        }
    }

}