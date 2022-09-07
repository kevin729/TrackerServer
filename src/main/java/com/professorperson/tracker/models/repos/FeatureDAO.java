package com.professorperson.tracker.models.repos;

import com.professorperson.tracker.models.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;

public interface FeatureDAO extends JpaRepository<Feature, Integer> {
}
