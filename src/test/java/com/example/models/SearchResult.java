package com.example.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResult {
    private String id;
    private String name;
    private String description;
    private Float price;
    private Float deluxePrice;
    private String image;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
}
