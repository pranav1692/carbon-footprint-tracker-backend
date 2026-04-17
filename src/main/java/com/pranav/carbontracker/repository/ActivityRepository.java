package com.pranav.carbontracker.repository;

import com.pranav.carbontracker.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    List<Activity> findByUserId(Long userId);
    Optional<Activity> findByIdAndUserId(Long id, Long userId);
    // Category-wise sum of emissions
    @Query("SELECT a.vehicleType, SUM(a.carbonEmission) FROM Activity a WHERE a.userId = :userId GROUP BY a.vehicleType")
    List<Object[]> findCategorySummary(@Param("userId") Long userId);

    // Monthly sum of emissions (assuming Activity has a LocalDate field named 'date')
    @Query(value = "SELECT MONTH(date), SUM(carbon_emission) FROM activity WHERE user_id = :userId GROUP BY MONTH(date)", nativeQuery = true)
    List<Object[]> findMonthlySummary(Long userId);
}