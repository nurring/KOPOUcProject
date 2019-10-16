package kr.ac.kopo.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cacheable
@Cache(region="UcUser", usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
public class UcUser implements Serializable {
	
	@Id //pk생성	
	@TableGenerator(name = "ucUser", allocationSize = 1) //sample 테이블 생성 : JPA	
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ucUser") //pk생성(@Id 어노테이션과 함께)		
	@Column(name="id", unique=true, nullable=false)
	private Long id; //id값 (pk)

	@Column(length=20) //이름
	private String name;
	
	@Column //주소
	private String address;
	
	@Column //회사명
	private String businessName;
	
	@Column //부서명
	private String departmentName;
	
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false, insertable = true) //등록일
	private Date registeredOn;
	
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = true, insertable = true) //마지막 수정일
	private Date lastUpdatedOn;
	
	@Cache(region="UcUser.phones", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="ucUser")
	private List<UcPhone> phones;//전화 테이블

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Date getRegisteredOn() {
		return registeredOn;
	}

	public void setRegisteredOn(Date registeredOn) {
		this.registeredOn = registeredOn;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public List<UcPhone> getPhones() {
		return phones;
	}

	public void setPhones(List<UcPhone> phones) {
		this.phones = phones;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", address=" + address + ", businessName=" + businessName + ", departmentName=" + departmentName + ", registeredOn=" + registeredOn + ", lastUpdatedOn=" + lastUpdatedOn + "]";
	}

	


}
