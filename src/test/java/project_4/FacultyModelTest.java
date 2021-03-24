package project_4;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project_4.bean.FacultyBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.model.FacultyModel;

public class FacultyModelTest {
	FacultyModel model = new FacultyModel();
	FacultyBean bean = new FacultyBean();
	public static void main(String[] args) throws ApplicationException, DuplicateRecordException {
		FacultyModelTest test= new FacultyModelTest();
		//test.testAdd();//works
		test.testUpdate();//works
		//test.testDelete(9);//works
		//test.testFindByEmail();//works
		//test.testFindByPk();//works
		//test.testList();//works
		test.testSearch();
	}
	public void testAdd() throws ApplicationException, DuplicateRecordException{
		bean.setFirstName("Anshul");
		bean.setLastName("Jain");
		bean.setGender("male");
		bean.setDob(new Date());
		bean.setQualification(null);
		bean.setEmailId("aditya448@gmail.com");
		bean.setMobileNo("8827903818");
		bean.setCollegeId(3);
		//bean.setCollegeName("Aitr");
		bean.setSubjectId(4);
		//bean.setSubjectName("computers");
		bean.setCourseId(2);
		//bean.setCourseName("computers");
		bean.setCreatedBy("Aditya");
		bean.setModifiedBy("Aditya Joshi");
		bean.setCreatedDatetime(null);
		bean.setModifiedDatetime(null);
		model.add(bean);		
	}
	public void testUpdate() throws ApplicationException, DuplicateRecordException{
		bean.setId(5);
		bean.setFirstName("Atul");
		bean.setLastName("Joshi");
		bean.setGender("Male");
		bean.setDob(null);
		bean.setQualification(null);
		bean.setEmailId("aditya91@gmail.com");
		bean.setMobileNo("8827900000");
		bean.setCollegeId(4);
		//bean.setCollegeName("AITR");
		bean.setSubjectId(5);
		//bean.setSubjectName("COMPUTER");
		bean.setCourseId(5);
		//bean.setCourseName("SYSTEM");
		bean.setCreatedBy("Aditya JOSHI");
		bean.setModifiedBy("atul khatri");
		bean.setCreatedDatetime(null);
		bean.setModifiedDatetime(null);
		model.update(bean);
	}
	public void testDelete(int pk) throws ApplicationException{
		bean.setId(pk);
		model.delete(bean);
	}
	public void testFindByEmail() throws ApplicationException{
		bean = model.findByEmailId("aditya@gmail.com");
		printAll(bean);
	}
	public void testFindByPk() throws ApplicationException{
		bean = model.findByPk(2);
		printAll(bean);
	}
	public void testList() throws ApplicationException{
		List<FacultyBean>list = new ArrayList<FacultyBean>();
		list= model.list(2, 10);
		Iterator<FacultyBean> it = list.iterator();
		while(it.hasNext()){
			printAll(it.next());
		}
	}
	public void testSearch() throws ApplicationException{
		//bean.setId(1);
		bean.setFirstName("Anshul");
		/*bean.setLastName("Jain");
		bean.setGender("male");
		bean.setDob(null);
		bean.setQualification(null);
		bean.setEmailId("aditya48@gmail.com");
		bean.setMobileNo("8827903818");
		bean.setCollegeId(3);
		bean.setCollegeName("Aitr");
		bean.setSubjectId(4);
		bean.setSubjectName("computers");
		bean.setCourseId(2);
		bean.setCourseName("computers");
		bean.setCreatedBy("Aditya");
		bean.setModifiedBy("Aditya Joshi");
		bean.setCreatedDatetime(null);
		bean.setModifiedDatetime(null);*/
		List<FacultyBean>list= new ArrayList<FacultyBean>();
		list = model.search(bean, 1, 10);
		Iterator<FacultyBean>it= list.iterator();
		while(it.hasNext()){
			printAll(it.next());
		}
		
	}
	private void printAll(FacultyBean bean){
		System.out.println(bean.getId()+"\t"+bean.getFirstName()+"\t"+bean.getLastName()+"\t"+bean.getGender()+"\t"+bean.getDob()
		+"\t"+bean.getQualification()+"\t"+bean.getEmailId()+"\t"+bean.getMobileNo()+"\t"+bean.getCollegeId()+"\t"+bean.getCollegeName()
		+"\t"+bean.getSubjectId()+"\t"+bean.getSubjectName()+"\t"+bean.getCourseId()+"\t"+bean.getCourseName()+"\t"+
		bean.getCreatedBy()+"\t"+bean.getModifiedBy()+"\t"+bean.getCreatedDatetime()+"\t"+bean.getModifiedDatetime());
	}
}
