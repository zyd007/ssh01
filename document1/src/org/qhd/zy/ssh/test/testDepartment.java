package org.qhd.zy.ssh.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.qhd.zy.ssh.model.Department;
import org.qhd.zy.ssh.service.IDepartmentService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class testDepartment {
	@Resource
	private IDepartmentService departmentService;
	@Test
	public void testadd(){
		Department dep=new Department();
		dep.setName("健身处");
		departmentService.add(dep);
	}
	@Test
	public void testDeleteDepartment(){
		departmentService.delete(9);
	}
	@Test
	public void testaddDepScope(){
		departmentService.addDepScope(9,2);
	}
	@Test
	public void testAddDepScopes(){
		departmentService.addDepScopes(2, new int[]{1,3,4,5,6,7,8});
	}
	@Test
	public void testDeleteDepScope(){
		departmentService.deleteDepScope(1,2);
	}
	@Test
	public void testDeleteDepScopes(){
		departmentService.deleteDepScopes(2);
	}
	@Test
	public void testlistDepScope(){
		List<Department>ls=departmentService.listDepScope(2);
		for(Department d:ls){
			System.out.println(d);
		}
	}
	@Test
	public void testByUserId(){
		List<Department>ls=departmentService.ByUserId(2);
		for(Department d:ls){
			System.out.println(d.getId()+","+d.getName());
		}
	}
}
