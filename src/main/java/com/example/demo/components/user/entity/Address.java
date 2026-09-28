package com.example.demo.components.user.entity;

public record Address(
        String street,
        String suite,
        String city,
        String zipcode,
        Geo geo
) {
}
