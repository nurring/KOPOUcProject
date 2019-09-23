package kr.co.uclick.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Cache;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ignite.configuration.CacheConfiguration;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import kr.co.uclick.entity.UcPhone;
import kr.co.uclick.entity.UcUser;
import kr.co.uclick.service.UcService;

@Controller
public class UcController {

	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

	@Autowired
	private UcService ucService;

	// 메인화면 (전체 회원 리스트)
	@GetMapping(value = "/{page}")
	public String list(@PathVariable("page") Integer page, Model model) {
		logger.info("list.jsp started!===================================");
		Pageable pageable = PageRequest.of(page, 10);				
		//오류 처리
		if (page<0 || page==null) {
			page = 0;
		}		
		//뽑혀야 할 전체 페이지버튼 수
		long cntTmp = ucService.userCount();
		double cntTmp2 = cntTmp/10.0;
		long finalVal = (long) (Math.ceil(cntTmp2));
		
		//한 화면에 보여줄 첫번째 버튼과 마지막 버튼(첫째숫자,마지막숫자 ex.1과5, 6과 10..)
		long startTmp = page/5;
		long startRange = startTmp*5;
		long endRange =((startTmp+1)*5)-1;
		if (endRange > finalVal) { //맨 마지막 화면에서는 다섯개 뿌리면 안되니 마지막 숫자에서 끊어주는 작업
			endRange = finalVal-1;
		}
		if (endRange < 0) { //빈테이블일 경우
			endRange = 0;
		}		
		model.addAttribute("page", page);
		model.addAttribute("totalCount", cntTmp);
		model.addAttribute("startRange", startRange);
		model.addAttribute("endRange", endRange);
		model.addAttribute("totalPage", finalVal);
		model.addAttribute("users", ucService.findAllPage(pageable));
		return "list";
	}

	//사용자 삭제
	@GetMapping(value = "/delete.html/{id}")
	public String delete(@PathVariable("id") Long id) {
		logger.info("delete started targeting ->" + id + "===================================");
		try {
			ucService.deleteUcUserById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/0";
	}

	//사용자 정보 수정
	@GetMapping(value = "/oneview.html/{id}/editform")
	public String editForm(@PathVariable("id") Long id, Model model) {
		logger.info("editform started targeting ->" + id + "===================================");
		UcUser user = ucService.findUcUserById(id);
		model.addAttribute("user", user);
		return "editform";
	}

	//사용자 저장
	@PostMapping(value = "/save.html")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public String save(UcUser ucUser) {
		logger.info("save started targeting ->" + ucUser.getName() + "===================================");
		try {
			Calendar calt = Calendar.getInstance();
			ucUser.setLastUpdatedOn(calt.getTime());
			ucUser.setRegisteredOn(calt.getTime());
			ucService.save(ucUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/0";
	}
	
	//연락처 저장
	@PostMapping(value = "/save2.html")
	public String save2(UcPhone ucPhone, Long userid) throws IOException {
		logger.info("save2 started targeting ->" + ucPhone.getId() + "===================================");
		if(ucPhone.getId() == null) {
			// 입력
			logger.info("insert phone===================================");		

			UcUser tmpUser = new UcUser();
			tmpUser.setId(userid);
			ucPhone.setUcUser(tmpUser);
			
			UcUser ucUser = ucService.findUcUserById(userid);
			ucUser.getPhones().add(ucPhone);
			ucService.save(ucUser);
			
			
		} else {
			logger.info("update phone===================================");
			// 수정
			UcUser ucUser = new UcUser();
			ucUser.setId(userid);
			ucPhone.setUcUser(ucUser);
			ucService.save2(ucPhone);
		}
		return "redirect:/oneview.html/" + ucPhone.getUcUser().getId();
	} 
	
	//새 사용자 추가 폼
	@GetMapping(value = "/newform.html") // list일 때 get방식으로 가져온다
	public String newForm(Model model) {
		logger.info("newform started===================================");
		return "newform";
	}

	//개인 별 정보 보기
	@GetMapping(value = "/oneview.html/{id}")
	public String oneView(@PathVariable("id") Long id, Model model) {
		logger.info("oneview started targeting ->" + id + "===================================");
		UcUser user = ucService.findUcUserById(id);
		List<UcPhone> phones = user.getPhones();
		model.addAttribute("user", user);
		model.addAttribute("phones", phones);
		return "oneview";
	}

	//새 연락처 추가 폼
	@GetMapping(value = "/oneview.html/{id}/newform2")
	public String newForm2(@PathVariable("id") Long id, Model model) {
		logger.info("newform2 started targeting ->" + id + "===================================");
		model.addAttribute("user", id);
		return "newform2";
	}
	
	//연락처 수정
	@GetMapping(value = "/oneview.html/{id}/editform2/{phoneid}")
	public String editForm2(@PathVariable("id") Long id, @PathVariable("phoneid") Long phoneid, Model model) {
		logger.info("editform2 started targeting ->" + phoneid + "===================================");
		UcPhone ucphone = ucService.findUcPhoneById(phoneid);	
		model.addAttribute("phone", ucService.findUcPhoneById(phoneid));
		model.addAttribute("id", id);
		return "editform2";
	}

	//연락처 삭제
	@GetMapping(value = "/oneview.html/{id}/delete2/{phoneid}")
	public String delete2(@PathVariable("id") Long id, @PathVariable("phoneid") Long phoneid) {
		logger.info("delete2 started targeting ->" + phoneid + "===================================");
		try {
			ucService.deletePhone(phoneid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/oneview.html/" + id;
	}

	//검색
	@PostMapping(value = "/search.html")
	public String search(String type, String search, Model model) {
		logger.info("search started targeting ->" + search + "===================================");
		model.addAttribute("search", search);
		if (type.contentEquals("byall")) { // 전체 검색
			
			List<UcUser> result = new ArrayList<UcUser>(); //최종 결과물 들어갈 리스트
			List<UcUser> ucuserAll = ucService.findAll(); //전체 유저 정보 몽땅 가져와서			
			for (UcUser usertmp : ucuserAll) { //유저마다 살펴보는데
				if(usertmp.getName().toLowerCase().contains(search.toLowerCase())) { //대소문자 구분 없이 검색
					result.add(usertmp);
				} else if(usertmp.getAddress().toLowerCase().contains(search.toLowerCase())) {
					result.add(usertmp);
				} else if(usertmp.getBusinessName().toLowerCase().contains(search.toLowerCase())) {
					result.add(usertmp);
				} else if(usertmp.getDepartmentName().toLowerCase().contains(search.toLowerCase())) {
					result.add(usertmp);
				} else {
					List<UcPhone> ucphone = usertmp.getPhones(); //유저가 가지고있는 연락처 정보를 리스트에 넣고					
					for (UcPhone phonetmp : ucphone) { //그 리스트를 볼거예용						
						if(phonetmp.getPhone().contains(search)) {
							result.add(usertmp);
							break;
						}						
					}
				}
			}
			model.addAttribute("searches", result);
		} else if (type.contentEquals("byname")) {
			model.addAttribute("searches", ucService.findAllByNameContaining(search));
		} else if (type.contentEquals("bybusiness")) {
			model.addAttribute("searches", ucService.findAllByBusinessNameContaining(search));
		} else if (type.contentEquals("bydepartment")) {
			model.addAttribute("searches", ucService.findAllByDepartmentNameContaining(search));
		} else if (type.contentEquals("byaddress")) {
			model.addAttribute("searches", ucService.findAllByAddressContaining(search));
		} else if (type.contentEquals("byphone")) {
			List<UcUser> ucuser = new ArrayList<UcUser>();
			List<UcPhone> phones = ucService.findAllUcPhoneByPhoneContaining(search);
			for (UcPhone ucPhone : phones) { 
				ucuser.add(ucPhone.getUcUser());
			}
			model.addAttribute("searches", ucuser);
		}
		return "search";
	}
	
	//복수 사용자 삭제
	@PostMapping(value = "/deletes.html")
	public String deletes(HttpServletRequest request, Model model) {
		logger.info("deletes started===================================");
		String[] arr = request.getParameterValues("select");
		for (String i : arr) {
			ucService.deleteUcUserById(Long.parseLong(i));
		}		
		return "redirect:/0";
	}
}


