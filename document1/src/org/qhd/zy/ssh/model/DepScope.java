package org.qhd.zy.ssh.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="s_depScope")
public class DepScope {
	private int id;
	/**
	 * 发送端
	 */
	private int dep;
	/**
	 * 可发送的一组(接受端)
	 */
	private Department department;
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * 要设置的部门id(发送端)
	 * @return
	 */
	public int getDep() {
		return dep;
	}
	public void setDep(int dep) {
		this.dep = dep;
	}
	/**
	 * 可以发送信息的部门对象(接受端(可以多个))
	 * @return
	 */
	@ManyToOne
	@JoinColumn(name="dep_id")
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	
}
