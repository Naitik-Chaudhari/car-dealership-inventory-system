package com.naitik.car_dealership_api.specification;

import com.naitik.car_dealership_api.entity.Vehicle;
import com.naitik.car_dealership_api.entity.VehicleCategory;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VehicleSpecification {

    public static Specification<Vehicle> search(
            String make,
            String model,
            VehicleCategory category,
            BigDecimal minPrice,
            BigDecimal maxPrice) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (make != null) {
                predicates.add(
                        cb.equal(root.get("make"), make));
            }

            if (model != null) {
                predicates.add(
                        cb.equal(root.get("model"), model));
            }

            if (category != null) {
                predicates.add(
                        cb.equal(root.get("category"), category));
            }

            if (minPrice != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(
                                root.get("price"),
                                minPrice));
            }

            if (maxPrice != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(
                                root.get("price"),
                                maxPrice));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}