package kr.co.uclick.service;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.uclick.entity.UcPhone;
import kr.co.uclick.entity.UcUser;
import kr.co.uclick.repository.PhoneRepository;
import kr.co.uclick.repository.UserRepository;

@Service
@Transactional
public class UcService {
	
	private static int numPerPage = 10;

	@Autowired // SampleService에 SampleRepository를 주입하기 위하여 사용
	private UserRepository userRepository; // SampleRepository를 전달 받음

	@Autowired 
	private PhoneRepository phoneRepository; 

	//find all
	@Transactional(readOnly = true)		
	public List<UcUser> findAll() {
		List<UcUser> ucuser = userRepository.findAll();
		for (UcUser tmp : ucuser) {
			Hibernate.initialize(tmp.getPhones());
		}
		return ucuser;
	}
	
	@Transactional(readOnly = true)	
	public Page<UcUser> findAllPage(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	//delete
	@Transactional
	public void deleteUcUserById(long id) {
		userRepository.deleteById(id);
	}
	
	@Transactional
	public void deletePhone(long id) {
		phoneRepository.findUcPhoneById(id).getUcUser().setPhones(null);
		phoneRepository.deleteById(id);
	}

	//id로 찾기
	@Transactional
	public UcUser findUcUserById(long id) {
		UcUser ucuser = userRepository.findUcUserById(id);
		Hibernate.initialize(ucuser.getPhones()); //fetch type lazy
		return ucuser;
	}

	@Transactional
	public UcPhone findUcPhoneById(long id) {
		UcPhone ucphone = phoneRepository.findUcPhoneById(id);
		Hibernate.initialize(ucphone.getPhone());
		return ucphone;
	}

	//insert & update
	@Transactional
	public void save(UcUser ucUser) {
		userRepository.save(ucUser);
	}
	
	@Transactional
	public void save2(UcPhone ucPhone) {
		phoneRepository.save(ucPhone);
	}
	
	//검색창
	public List<UcUser> findAllByNameContaining(String name){
		List<UcUser> ucuser = userRepository.findAllByNameContaining(name);
		for (UcUser tmp : ucuser) {
			Hibernate.initialize(tmp.getPhones());
		}
		return userRepository.findAllByNameContaining(name);
	}
	
	public List<UcUser> findAllByBusinessNameContaining(String businessname) {
		List<UcUser> ucuser = userRepository.findAllByBusinessNameContaining(businessname);
		for (UcUser tmp : ucuser) {
			Hibernate.initialize(tmp.getPhones());
		}
		return ucuser;
	}
	
	public List<UcUser> findAllByDepartmentNameContaining(String departmentname) {
		List<UcUser> ucuser = userRepository.findAllByDepartmentNameContaining(departmentname);
		for (UcUser tmp : ucuser) {
			Hibernate.initialize(tmp.getPhones());
		}
		return ucuser;
	}
	
	public List<UcUser> findAllByAddressContaining(String address) {
		List<UcUser> ucuser = userRepository.findAllByAddressContaining(address);
		for (UcUser tmp : ucuser) {
			Hibernate.initialize(tmp.getPhones());
		}
		return ucuser;
	}

	public List<UcPhone> findAllUcPhoneByPhoneContaining(String phone) {	
		return 	phoneRepository.findAllUcPhoneByPhoneContaining(phone);
	}
	
	public Long userCount() {
		return userRepository.count();
	}

}
