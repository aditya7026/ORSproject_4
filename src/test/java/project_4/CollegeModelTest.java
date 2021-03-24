package project_4;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project_4.bean.CollegeBean;

import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.model.CollegeModel;


public class CollegeModelTest {
CollegeModel model = new CollegeModel();

public static void main(String[] args) throws ApplicationException, SQLException, DuplicateRecordException {
	CollegeModelTest test = new CollegeModelTest();
	//test.testAdd();//add works
	test.testUpdate();//update works
	//test.testFindByPK();//findByPk works
	//test.testFindByName();//findByName works
	//test.testDelete();//delete Works
	//test.testList();//list works
	//test.testSearch();
}
public void testDelete() throws ApplicationException, SQLException{
	
	CollegeBean bean = new CollegeBean();
	bean.setId(13);
	model.delete(bean);
	
	
}
public void testAdd() throws ApplicationException, DuplicateRecordException{
	CollegeBean bean = new CollegeBean();
	bean.setName("Astral");
	bean.setAddress("byPass");
	bean.setState("M.p.");
	bean.setCity("indore");
	bean.setPhNo("4589632145");
	bean.setCreatedBy("Aditya Joshi");
	bean.setModifiedBy("Atul Jadhav");
	bean.setCreatedDatetime(null);
	bean.setModifiedDatetime(null);
	long i =model.add(bean);
	System.out.println(i+" pk of record inserted");
}
public void testUpdate() throws ApplicationException, DuplicateRecordException{
	CollegeBean bean = new CollegeBean();
	bean.setId(12);
	bean.setName("Astral2");
	bean.setAddress("Tejaji Nagar By-Pass");
	bean.setState("M.P.");
	bean.setCity("Indore");
	bean.setPhNo("1234567890");
	bean.setCreatedBy("Aditya");
	bean.setModifiedBy("Rishikesh");
	bean.setCreatedDatetime(null);
	bean.setModifiedDatetime(null);
	model.update(bean);
}
public void testFindByPK() throws ApplicationException{
	CollegeBean bean = new CollegeBean();
	bean= model.findByPK(1L);
	printAll(bean);
}
public void testFindByName() throws ApplicationException{
CollegeBean bean = new CollegeBean();
bean= model.findByName("Astral College");
printAll(bean);
}
public void testList() throws ApplicationException{
	CollegeBean bean = new CollegeBean();
	List<CollegeBean>list = new ArrayList<CollegeBean>();
	list = model.list(1, 10);
	Iterator<CollegeBean>it = list.iterator();
	while(it.hasNext()){
		bean = it.next();
		printAll(bean);
		if(bean.getId()==(model.nextPK()-1)){
			System.out.println("Last Record");
		}
	}
	}
 public void testSearch() throws ApplicationException{
	 List<CollegeBean>list = new ArrayList<CollegeBean>();
	 CollegeBean bean = new CollegeBean();
//	    bean.setId(1);
//	    bean.setName("Astral");
		bean.setAddress("byPass");
//		bean.setState("MP");
//		bean.setCity("indore");
//		bean.setPhnoNo("4589632145");
//		bean.setCreatedBy("Aditya Joshi");
//		bean.setModifiedBy("Atul Jadhav");
//		bean.setCreatedDatetime(null);
//		bean.setModifiedDatetime(null);
	 list = model.search(bean, 1, 10);
	 Iterator<CollegeBean>it = list.iterator();
		while(it.hasNext()){
			bean = it.next();
			printAll(bean);
			if(bean.getId()==(model.nextPK()-1)){
				System.out.println("Last Record");
			}
		}
 }
public static void printAll(CollegeBean bean){
	System.out.print(bean.getId()+"\t");
	System.out.print(bean.getName()+"\t");
	System.out.print(bean.getAddress()+"\t");
	System.out.print(bean.getCity()+"\t");
	System.out.print(bean.getPhNo()+"\t");
    System.out.print(bean.getCreatedBy()+"\t");
    System.out.print(bean.getModifiedBy()+"\t");
    System.out.print(bean.getCreatedDatetime()+"\t");
    System.out.print(bean.getModifiedDatetime()+"\t");
    System.out.println();
}
}
