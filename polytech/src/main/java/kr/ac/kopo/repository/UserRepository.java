package kr.ac.kopo.repository;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import kr.ac.kopo.entity.UcUser;

@Repository
public interface UserRepository 
	extends JpaRepository<UcUser, Long>, QuerydslPredicateExecutor<UcUser>{
	////query cache
	@QueryHints(value= {
			@QueryHint(name="org.hibernate.cacheable", value="true"),
			@QueryHint(name="org.hibernate.cacheMode", value="NORMAL")
	})
	public Page<UcUser> findAll(Pageable pageable);//id순서대로 정렬 ->페이징용
	
	public UcUser findUcUserById(Long id);
	
	////query cache (요구사항)
	@QueryHints(value= {
			@QueryHint(name="org.hibernate.cacheable", value="true"),
			@QueryHint(name="org.hibernate.cacheMode", value="NORMAL")
	})
	public List<UcUser> findAllByNameContaining(String name);
	public List<UcUser> findAllByAddressContaining(String address);
	public List<UcUser> findAllByBusinessNameContaining(String businessname);
	public List<UcUser> findAllByDepartmentNameContaining(String departmentname);
}
