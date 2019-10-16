package kr.ac.kopo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import kr.ac.kopo.entity.Sample;

public interface SampleRepository
		extends JpaRepository<Sample, Long>, QuerydslPredicateExecutor<Sample>, CustomSampleRepository {

	public List<Sample> findSampleByName(String name);

}
