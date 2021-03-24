package project_4;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project_4.bean.StudentBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DatabaseException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.model.StudentModel;

public class StudentModelTest {
	StudentModel model = new StudentModel();
	public static void main(String[] args) throws DatabaseException, ApplicationException, DuplicateRecordException {
		StudentModelTest test = new StudentModelTest();
		//test.testNextPk();//works
		//test.testAdd();//add works
		//test.testFindByPK();//findByPK works
		//test.testFindByEmailId();//findByName works
		test.testUpdate();//update works
		//test.testDelete();//delete works
		//test.testList();//list works
		//test.testSearch();//Search works
	}
	public void testDelete() throws ApplicationException{
		StudentBean bean = new StudentBean();
		bean.setId(1);
		model.delete(bean);
	}
	public void testUpdate() throws ApplicationException, DuplicateRecordException{
		StudentBean bean = new StudentBean();
		bean.setId(2);
		bean.setCollegeId(5);
		//bean.setCollegeName("Acropolis");
		bean.setFirstName("Tamonash");
		bean.setLastName("updadhaya");
		bean.setDob(null);
		bean.setMobileNo("8825548587");
		bean.setEmail("aditya@gmail.com");
		bean.setCreatedBy("anil");
		bean.setModifiedBy("neha");
		bean.setCreatedDatetime(null);
		bean.setModifiedDatetime(null);
		model.update(bean);
	}
	public void testNextPk() throws DatabaseException, ApplicationException{
		System.out.println(model.nextPk());
	}
	public void testAdd() throws ApplicationException, DuplicateRecordException{
		StudentBean bean = new StudentBean();
		bean.setCollegeId(2);
		//bean.setCollegeName("Astral");
		bean.setFirstName("Aditya");
		bean.setLastName("Joshi");
		bean.setDob(null);
		bean.setMobileNo("852631478"); 
		bean.setEmail("aditya7826@gmail.com");
		bean.setCreatedBy("Aditya Joshi");
		bean.setModifiedBy("Atul Jadhav");
		bean.setCreatedDatetime(null);
		bean.setModifiedDatetime(null);
		long i =model.add(bean);
		System.out.println(i);
	}
	public void testFindByPK() throws ApplicationException{
		StudentBean  bean = model.findByPk(1);
		printAll(bean);
	}
	public void testFindByEmailId() throws ApplicationException{
		
		printAll(model.findByEmail("aditya@gmail.com"));
	}
	public void testList() throws ApplicationException{
		StudentBean bean = new StudentBean();
		List<StudentBean>list = new ArrayList<StudentBean>();
		list = model.list(1, 10);
		Iterator<StudentBean>it = list.iterator();
		while(it.hasNext()){
			bean = it.next();
			printAll(bean);
			if(bean.getId()==(model.nextPk()-1)){
				System.out.println("Last Record");
			}
		}
		}
	 public void testSearch() throws ApplicationException{
		 List<StudentBean>list = new ArrayList<StudentBean>();
		 StudentBean bean = new StudentBean();
//		    bean.setId(1);
//		 	bean.setCollegeName("Acropolis");
//		 	bean.setFirstName("Aditya");
//		 	bean.setLastName("Shukla");
//		 	bean.setDob(null);
//		 	bean.setMobileNo("78");
//		 	bean.setEmail("aditya7026@gmail.com");
//	  	    bean.setCreatedBy("atul");
//			bean.setModifiedBy("Atul");
//			bean.setCreatedDatetime(null);
//			bean.setModifiedDatetime(null);
		 list = model.search(bean, 1, 10);
		 Iterator<StudentBean>it = list.iterator();
			while(it.hasNext()){
				bean = it.next();
				printAll(bean);
				if(bean.getId()==(model.nextPk()-1)){
					System.out.println("Last Record");
				}
			}
	 }
	public static void printAll(StudentBean bean){
		System.out.print(bean.getId()+"\t");
		System.out.print(bean.getCollegeId()+"\t");
		System.out.print(bean.getCollegeName()+"\t");
		System.out.print(bean.getFirstName()+"\t");
		System.out.print(bean.getLastName()+"\t");
		System.out.print(bean.getDob()+"\t");
		System.out.print(bean.getMobileNo()+"\t");
		System.out.print(bean.getEmail()+"\t");
	    System.out.print(bean.getCreatedBy()+"\t");
	    System.out.print(bean.getModifiedBy()+"\t");
	    System.out.print(bean.getCreatedDatetime()+"\t");
	    System.out.print(bean.getModifiedDatetime()+"\t");
	    System.out.println();
	}
}
