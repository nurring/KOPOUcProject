package kr.co.uclick.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.uclick.entity.QSample;
import kr.co.uclick.entity.Sample;
import kr.co.uclick.repository.SampleRepository;

@Service
@Transactional //클래스에 트랜잭션 기능이 적용된 프록시 객체가 생성된다
public class SampleService {

	@Autowired //SampleService에 SampleRepository를 주입하기 위하여 사용
	private SampleRepository sampleRepository; //SampleRepository를 전달 받음

	public void delete(Sample entity) {
		sampleRepository.delete(entity);
	}

	@Transactional(readOnly = true)
	public List<Sample> findAll() { //jpa reference~ 모든 데이터 반환
		return sampleRepository.findAll();
	}

	@Transactional(readOnly = true)
	public List<Sample> findSampleByName(String name) {

		sampleRepository.findAll(QSample.sample.name.eq(name)); //Q샘플의 샘플은
		//sample.java의 변수를 모두 가져오는 메서드..의 이름이 매개변수와 같은 것들을 findAll)
		sampleRepository.doSample(name); //로그찍는친구

		return sampleRepository.findSampleByName(name);//서비스에서 레퍼지토리로 리턴
	}

	public void save(Sample sample) {
		sampleRepository.save(sample);//save() : 레코드 저장(insert,update)
	}

	public void findById(Long sampleId) {
		sampleRepository.findById(sampleId);//findBy~:쿼리를 요청하는 메서드임을 알린다.
	}
}
