package project_4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project_4.bean.CourseBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.model.CourseModel;

public class CourseModelTest {
	CourseModel model = new CourseModel();
	CourseBean bean = new CourseBean();
	public static void main(String[] args) throws ApplicationException, DuplicateRecordException {
		CourseModelTest test = new CourseModelTest();
		//test.testAdd();//works
		test.testUpdate();//works
		//test.testDelete();//works
		//test.testFindByPK();//works
		//test.testFindByName();//works
		//test.testList();//works
		test.testSearch();
	}
	public void testAdd() throws ApplicationException, DuplicateRecordException{
		
		bean.setName("Civil Engineering");
		bean.setDescription("dont do it");
		bean.setDuration("eternity");
		bean.setCreatedBy("great problem solvers");
		bean.setModifiedBy("idiots");
		model.add(bean);
	}
	public void testUpdate() throws ApplicationException, DuplicateRecordException{
		
		bean.setId(3);
		bean.setName("Mechanical");
		bean.setDescription("its a good field");
		bean.setDuration("a long time");
		bean.setCreatedBy("me");
		bean.setModifiedBy("some good and some bad people");
		model.update(bean);
	}
	public void testDelete() throws ApplicationException {
		
		bean.setId(2);
		model.delete(bean);
	}
	public void testFindByPK() throws ApplicationException{
		
		bean = model.findByPk(3);
		printAll(bean);
			}
	public void testFindByName() throws ApplicationException{
		bean = model.findByName("Medical");
		printAll(bean);
	}
	public void testList() throws ApplicationException {
		List<CourseBean>list = new ArrayList<CourseBean>();
		list = model.list(1, 10);
		Iterator<CourseBean>it  = list.iterator();
		while(it.hasNext()){
			bean = it.next();
			printAll(bean);
		}
	}
	public void testSearch() throws ApplicationException{
		List<CourseBean>list = new ArrayList<CourseBean>();
		bean.setName("Eng");
		list= model.search(bean, 1, 10);
		Iterator<CourseBean>it = list.iterator();
		while(it.hasNext()){
			printAll(it.next());
		}
	}
	private void printAll(CourseBean bean){
		System.out.println(bean.getId()+"\t"+bean.getName()+"\t"+bean.getDescription()+"\t"+bean.getDuration()+"\t"+
		bean.getCreatedBy()+"\t"+bean.getModifiedBy()+"\t"+bean.getCreatedDatetime()+"\t"+bean.getModifiedDatetime());

	}
	
}
