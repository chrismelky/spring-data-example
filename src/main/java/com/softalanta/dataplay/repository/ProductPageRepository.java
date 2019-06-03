package com.softalanta.dataplay.repository;

import com.softalanta.dataplay.domain.Product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductPageRepository extends PagingAndSortingRepository<Product, Long>, JpaSpecificationExecutor<Product> {
}
