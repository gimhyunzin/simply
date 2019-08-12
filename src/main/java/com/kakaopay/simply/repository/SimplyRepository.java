package com.kakaopay.simply.repository;

import com.kakaopay.simply.dto.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hjk
 */
@Repository
public interface SimplyRepository extends JpaRepository<Link, Integer> {

    List<Link> findAll();

    Link findByShortCode(String shortCode);

    Link findByOriginUrl(String originUrl);

    boolean existsByShortCode(String shortCode);
}
