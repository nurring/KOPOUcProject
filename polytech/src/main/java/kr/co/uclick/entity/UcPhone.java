package kr.co.uclick.entity;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cacheable
@Cache(region="UcPhone", usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
public class UcPhone implements Serializable {

	@Id //pk생성
	@TableGenerator(name = "ucPhone", allocationSize = 1) //sample 테이블 생성 : JPA
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ucPhone") //pk생성(@Id 어노테이션과 함께)
	@Column(name="id", unique=true, nullable=false)
	private Long id; //id값 (pk)
	
	@Column(name="phone", unique=true)
	private String phone; //전화번호
	
	@Column(name="carrier")
	private String carrier; //통신사	
	
	@ManyToOne
	@JoinColumn(name="userid")
	private UcUser ucUser; //user id

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public UcUser getUcUser() {
		return ucUser;
	}

	public void setUcUser(UcUser ucUser) {
		this.ucUser = ucUser;
	}


}
