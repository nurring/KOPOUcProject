package kr.ac.kopo.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import kr.ac.kopo.configuration.SpringConfiguration;
import kr.ac.kopo.service.UcService;
import kr.ac.kopo.entity.UcPhone;
import kr.ac.kopo.entity.UcUser;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class UcServiceTest {
	
	@Autowired
	UcService ucService;
// 회원 추가
	@Test
	@Ignore
	public void test1() {
		UcUser ucUser = new UcUser();
		ucUser.setName("테스트용");
		ucUser.setBusinessName("테스트용");
		ucUser.setDepartmentName("테스트용");
		ucUser.setAddress("테스트용");
		ucService.save(ucUser);	
	}
	
// 연락처 추가
	@Ignore
	@Test
	public void test2() {
		UcUser ucUser = ucService.findUcUserById(1L);
		UcPhone ucPhone = new UcPhone();
		ucPhone.setPhone("010-0801-0801");
		ucPhone.setCarrier("U+");
		ucPhone.setUcUser(ucUser);
		ucService.save2(ucPhone);
	}
	
// 회원 정보 수정
	@Ignore
	@Test
	public void test3() {
		UcUser ucUser = new UcUser();
		ucUser = ucService.findUcUserById(1L);
		ucUser.setName("테스트용수정");
		ucUser.setBusinessName("테스트용수정");
		ucUser.setDepartmentName("테스트용수정");
		ucUser.setAddress("테스트용수정");
		ucService.save(ucUser);
	}
	
// 연락처 수정
	@Ignore
	@Test
	public void test4() {
		UcPhone ucPhone = new UcPhone();
		ucPhone = ucService.findUcPhoneById(1L);
		ucPhone.setPhone("010-1204-1004");
		ucPhone.setCarrier("SKT");
		ucService.save2(ucPhone);
	}
	
// 회원 삭제
	@Ignore
	@Test
	public void test5() {
		ucService.deleteUcUserById(207); //test1에 만들어진 테스트사용자의 id값임
	}

// 연락처 삭제
	@Ignore
	@Test
	public void test6() {
		ucService.deletePhone(22);
	}

//특정 이름으로 사용자 리스트 검색하고 결과를 확인
//	@Ignore
	@Test
	public void test7() {
		List<UcUser> ucUser = new ArrayList<UcUser>();
		ucUser = ucService.findAllByNameContaining("나");
		for (UcUser user : ucUser) {
			System.out.println("ID: "+user.getId());
			System.out.println("NAME: "+user.getName());
			System.out.println("BUSINESS: "+user.getBusinessName());
			System.out.println("DEPARTMNET: "+user.getDepartmentName());
			System.out.println("ADDRESS: "+user.getAddress());
			List<UcPhone> ucPhone = (List<UcPhone>) user.getPhones();
			if (ucPhone != null) {
				for (UcPhone phone : ucPhone) {
					System.out.println("PHONE_NUM: "+phone.getPhone());
					System.out.println("PHONE_CARRIER: "+phone.getCarrier());
				}
			}
		}
	}
	
//특정 번호로 사용자 리스트 검색하고 결과를 확인
	@Ignore
	@Test
	public void test8() {
		List<UcUser> ucusers = new ArrayList<UcUser>();
		List<UcPhone> phones = ucService.findAllUcPhoneByPhoneContaining("111");
		for (UcPhone ucPhone : phones) {
			ucusers.add(ucPhone.getUcUser());
		}
		for (UcUser user : ucusers) {
			System.out.println("ID: "+user.getId());
			System.out.println("NAME: "+user.getName());
			System.out.println("BUSINESS: "+user.getBusinessName());
			System.out.println("DEPARTMNET: "+user.getDepartmentName());
			System.out.println("ADDRESS: "+user.getAddress());
		}
	}
	
	@Ignore
	@Test
	public void test9() {
		UcUser ucUser = ucService.findUcUserById(1L);		
		for(UcPhone uc : ucUser.getPhones()) {
			System.out.println(uc.getId());
		}
		UcPhone phone = new UcPhone();
		phone.setPhone("1234557890");
		phone.setCarrier("SKT");
		phone.setUcUser(ucUser);
		
		ucUser.getPhones().add(phone);
		ucService.save(ucUser);
		
		for(UcPhone uc : ucUser.getPhones()) {
			System.out.println(uc.getId());
		}
	}
}
