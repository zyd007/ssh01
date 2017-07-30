package org.qhd.zy.ssh.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="s_dep")
public class Department {
	private int id;
	private String name;
	/**
	 * 0为启用，1为停用
	 */
	private int status;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + ", status=" + status
				+ "]";
	}
	@Override
	public int hashCode() {
		
		return 10000+id;
	}
	@Override
	public boolean equals(Object obj) {
		Department dep=(Department)obj;
		if(dep.getId()==this.getId())return true;
		return false;
	}
	public Department(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public Department() {
	}
}
