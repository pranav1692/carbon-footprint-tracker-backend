package com.pranav.carbontracker.repository;

import com.pranav.carbontracker.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    List<Activity> findByUserId(Long userId);

    // Category-wise sum of emissions
    @Query("SELECT a.category, SUM(a.carbonEmission) FROM Activity a WHERE a.userId = :userId GROUP BY a.category")
    List<Object[]> findCategorySummary(Long userId);

    // Monthly sum of emissions (assuming Activity has a LocalDate field named 'date')
    @Query(value = "SELECT MONTH(date), SUM(carbon_emission) FROM activity WHERE user_id = :userId GROUP BY MONTH(date)", nativeQuery = true)
    List<Object[]> findMonthlySummary(Long userId);
}