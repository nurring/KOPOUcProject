package kr.ac.kopo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity
public class Sample {

	@Id //pk생성
	@TableGenerator(name = "Sample") //sample 테이블 생성 : JPA
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Sample") //pk생성(@Id 어노테이션과 함께)
	@Column(name="id", unique=true, nullable=false)
	private Long id;

	@Column(name="name")
	private String name;
	
	@Column(name="number")
	private int number;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}




}
