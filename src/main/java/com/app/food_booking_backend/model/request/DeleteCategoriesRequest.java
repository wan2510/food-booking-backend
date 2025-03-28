package com.app.food_booking_backend.model.request;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeleteCategoriesRequest {
    private List<String> ids;
}
