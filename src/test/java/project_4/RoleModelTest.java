package project_4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project_4.bean.RoleBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DatabaseException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.model.RoleModel;

public class RoleModelTest {
	RoleModel model = new RoleModel();
public static void main(String[] args) throws DatabaseException, ApplicationException, DuplicateRecordException {
	RoleModelTest test = new RoleModelTest();
	//test.testNextPk();//works
	//test.testAdd();//add works
	//test.testFindByPK();//findByPK works
	//test.testFindByName();//findByName works
	test.testUpdate();//update works
	//test.testDelete();//delete works
	//test.testList();//list works
	//test.testSearch();//Search works
}
public void testDelete() throws ApplicationException{
	RoleBean bean = new RoleBean();
	bean.setId(1);
	model.delete(bean);
}
public void testUpdate() throws ApplicationException, DuplicateRecordException{
	RoleBean bean = new RoleBean();
	bean.setId(23);
	bean.setName("Admin");
	bean.setDescription("Highest priority");
	bean.setCreatedBy("Aditya virendra Joshi");
	bean.setModifiedBy("Aditya Joshi");
	bean.setCreatedDatetime(null);
	bean.setModifiedDatetime(null);
	model.update(bean);
}
public void testNextPk() throws DatabaseException, ApplicationException{
	
	System.out.println(model.nextPK());
}
public void testAdd() throws ApplicationException, DuplicateRecordException{
	RoleBean bean = new RoleBean();
	bean.setName("Admin");
	bean.setDescription("Administrator of the System");
	bean.setCreatedBy("Aditya Joshi");
	bean.setModifiedBy("Atul Jadhav");
	bean.setCreatedDatetime(null);
	bean.setModifiedDatetime(null);
	long i =model.add(bean);
	System.out.println(i);
}
public void testFindByPK() throws ApplicationException{
	RoleBean  bean = model.findByPk(1L);
	printAll(bean);
}
public void testFindByName() throws ApplicationException{
	RoleBean bean = new RoleBean();
	bean = model.findByName("Admin");
	printAll(bean);
}
public void testList() throws ApplicationException{
	RoleBean bean = new RoleBean();
	List<RoleBean>list = new ArrayList<RoleBean>();
	list = model.list(2, 10);
	Iterator<RoleBean>it = list.iterator();
	while(it.hasNext()){
		bean = it.next();
		printAll(bean);
		if(bean.getId()==(model.nextPK()-1)){
			System.out.println("Last Record");
		}
	}
	}
 public void testSearch() throws ApplicationException{
	 List<RoleBean>list = new ArrayList<RoleBean>();
	 RoleBean bean = new RoleBean();
//	    bean.setId(1);
//	    bean.setName("Admin");
//		bean.setDescription("College");
//  	bean.setCreatedBy("Aditya Joshi");
		bean.setModifiedBy("Atul");
//		bean.setCreatedDatetime(null);
//		bean.setModifiedDatetime(null);
	 list = model.search(bean, 1, 10);
	 Iterator<RoleBean>it = list.iterator();
		while(it.hasNext()){
			bean = it.next();
			printAll(bean);
			if(bean.getId()==(model.nextPK()-1)){
				System.out.println("Last Record");
			}
		}
 }
public static void printAll(RoleBean bean){
	System.out.print(bean.getId());
	System.out.print(bean.getName());
	System.out.print(bean.getDescription());
    System.out.print(bean.getCreatedBy()+"\t");
    System.out.print(bean.getModifiedBy()+"\t");
    System.out.print(bean.getCreatedDatetime()+"\t");
    System.out.print(bean.getModifiedDatetime()+"\t");
    System.out.println();
}
}
