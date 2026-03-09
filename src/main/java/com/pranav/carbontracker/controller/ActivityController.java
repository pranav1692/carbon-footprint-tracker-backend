package com.pranav.carbontracker.controller;
import com.pranav.carbontracker.model.Activity;
import com.pranav.carbontracker.model.User;
import com.pranav.carbontracker.repository.ActivityRepository;
import com.pranav.carbontracker.service.CarbonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.pranav.carbontracker.repository.UserRepository;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private CarbonService carbonService;

    @PostMapping
    public Activity addActivity(@RequestBody Activity activity){

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        activity.setUserId(user.getId());

        double emission = carbonService.calculateEmission(
                activity.getCategory(),
                activity.getValue()
        );

        activity.setCarbonEmission(emission);

        activity.setDate(LocalDate.now());

        return activityRepository.save(activity);
    }

    @GetMapping("/category-summary")
    public Map<String, Double> getCategorySummary(){

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Object[]> results = activityRepository.findCategorySummary(user.getId());

        Map<String, Double> summary = new HashMap<>();

        for(Object[] row : results){
            summary.put((String) row[0], (Double) row[1]);
        }

        return summary;
    }

    @GetMapping("/monthly")
    public Map<Integer, Double> getMonthlySummary(){

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Object[]> results = activityRepository.findMonthlySummary(user.getId());

        Map<Integer, Double> summary = new HashMap<>();

        for(Object[] row : results){
            summary.put((Integer) row[0], (Double) row[1]);
        }

        return summary;
    }

    @GetMapping("/total")
    public double getTotalEmission(){

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Activity> activities = activityRepository.findByUserId(user.getId());

        return activities.stream()
                .mapToDouble(Activity::getCarbonEmission)
                .sum();
    }

    @GetMapping("/my")
    public List<Activity> getMyActivities(){

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return activityRepository.findByUserId(user.getId());
    }
}