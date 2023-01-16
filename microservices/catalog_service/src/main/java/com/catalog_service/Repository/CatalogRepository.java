package com.catalog_service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.catalog_service.Model.CatalogModel;

public interface CatalogRepository extends JpaRepository<CatalogModel, Long>{

}
