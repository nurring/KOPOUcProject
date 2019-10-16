package kr.ac.kopo.repository;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import kr.ac.kopo.entity.UcPhone;

@Repository
public interface PhoneRepository 
	extends JpaRepository<UcPhone, Long>, QuerydslPredicateExecutor<UcPhone>{
	public List<UcPhone> findUcPhonesByUcUser_Id(Long userId);
	public UcPhone findUcPhoneById(Long id);
	
	//query cache
	@QueryHints(value= {
			@QueryHint(name="org.hibernate.cacheable", value="true"),
			@QueryHint(name="org.hibernate.cacheMode", value="NORMAL")
	})
	public List<UcPhone> findAllUcPhoneByPhoneContaining(String phone);	
}

