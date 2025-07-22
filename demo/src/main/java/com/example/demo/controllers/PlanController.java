package com.example.demo.controllers;

import com.example.demo.model.Plan;
import com.example.demo.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/plans")
public class PlanController {

    @Autowired
    private PlanRepository planRepository;

    @GetMapping
    public List<Plan> getAllPlans() {
        return planRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plan> getPlanById(@PathVariable String id) {
        Optional<Plan> plan = planRepository.findById(id);
        return plan.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Plan createPlan(@RequestBody Plan plan) {
        return planRepository.save(plan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Plan> updatePlan(@PathVariable String id, @RequestBody Plan planDetails) {
        Optional<Plan> optionalPlan = planRepository.findById(id);
        if (optionalPlan.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Plan plan = optionalPlan.get();
        plan.setTitle(planDetails.getTitle());
        plan.setDate(planDetails.getDate());
        plan.setStatus(planDetails.getStatus());
        plan.setDesription(planDetails.getDescription());
        Plan updatedPlan = planRepository.save(plan);

        return ResponseEntity.ok(updatedPlan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable String id) {
        if (!planRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        planRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
