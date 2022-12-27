package com.professorperson.tracker.models.repos;

import com.professorperson.tracker.models.Feature;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;

@Repository
public interface FeatureDAO extends JpaRepository<Feature, Integer> {
}
