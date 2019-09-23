package kr.co.uclick.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import kr.co.uclick.entity.Sample;
import kr.co.uclick.service.SampleService;

@Controller
public class SampleController {

//	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);
//
//	@Autowired
//	private SampleService sampleService;
//
//	@GetMapping(value = "list.html") //list.html일 때 get방식으로 가져온다
//	public String list(Model model) {
//		model.addAttribute("users", sampleService.findAll());
//		return "list";
//	}
//
//	@GetMapping(value = "newForm.html") //newForm.html일 때 get방식으로 가져온다
//	public String newForm(Model model) {
//		return "newForm";
//	}
//
//	@GetMapping(value = "editForm.html") //editForm.html일 때 get방식으로 가져온다
//	public String editForm(Long sampleId, Model model) {
//		sampleService.findById(sampleId);
//		return "editForm";
//	}
//
//	@PostMapping(value = "save.html") //save.html일 때 get방식으로 가져온다
//	public String save(Sample sample, Model model) {
//		return "redirect:list.html";
//	}
//
//	@DeleteMapping(value = "delete.html") //delete.html일 때 get방식으로 가져온다
//	public String delete(Long sampleId, Model model) {
//		return "redirect:list.html";
//	}
//
//	@GetMapping(value = "sample.html") //sample.html일 때 get방식으로 가져온다
//	public String sample(String name, Sample sample, Model model) {
//
//		logger.debug("sample name : {}", name);
//		logger.debug("Sample.name : {}", sample.getName()); //위 로그와 같은 값이 찍힘
//
//		model.addAttribute("samples", sampleService.findAll());
//		model.addAttribute("sample", sample);
//		model.addAttribute("findSampleByName", sampleService.findSampleByName(name));
//		return "sample";
//	}
}
