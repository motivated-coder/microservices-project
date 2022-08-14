package com.skd.repository;

import com.skd.entity.FraudCheckHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FraudCheckRepository extends JpaRepository<FraudCheckHistory,Integer> {
//    @Query(
//            value = "SELECT f FROM FraudCheckHistory f WHERE f.email = ?1",
//            nativeQuery = false)
    Optional<FraudCheckHistory> findByEmail(String email);
}
