package project_4;

import java.util.Iterator;
import java.util.List;

import in.co.rays.project_4.bean.SubjectBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.model.SubjectModel;

public class SubjectModelTest {
	SubjectBean bean = new SubjectBean();
	SubjectModel model = new SubjectModel();
	public static void main(String[] args) throws ApplicationException, DuplicateRecordException {
		SubjectModelTest test = new SubjectModelTest();
		//test.testAdd();//works
		test.testUpdate();//works
		//test.testDelete();//works
		//test.testFindByPk();//works
		//test.testFindByName();//works
		//test.testList();//works
		//test.testSearch();
	}
	public void testAdd() throws ApplicationException, DuplicateRecordException{
		bean.setName("maths");
		bean.setDescription("mathematics");
		bean.setCourseId(2);
		//bean.setCourseName("enggnering");
		bean.setCreatedBy("Aditya JOshi");
		bean.setModifiedBy("Anshul Jain");
		bean.setCreatedDatetime(null);
		bean.setModifiedDatetime(null);
		System.out.println(model.add(bean));
	}
	public void testUpdate() throws ApplicationException, DuplicateRecordException{
		bean.setId(4);
		bean.setName("maths");
		bean.setDescription("aaaaaaaaa");
		bean.setCourseId(5);
		//bean.setCourseName("jaby koay");
		bean.setCreatedBy("Adity");
		bean.setModifiedBy("Anshu");
		bean.setCreatedDatetime(null);
		bean.setModifiedDatetime(null);
		model.update(bean);
	}
	public void testDelete() throws ApplicationException{
		bean.setId(2);
		model.delete(bean);
	}
	public void testFindByPk() throws ApplicationException{
		printAll(model.findByPk(1));
	}
	public void testFindByName() throws ApplicationException{
		printAll(model.findByName("science"));
	}
	public void testList() throws ApplicationException{
		List<SubjectBean>list = model.list(2, 10);
		Iterator<SubjectBean>it = list.iterator();
		while(it.hasNext()){
			printAll(it.next());
		}
	}
	public void testSearch() throws ApplicationException{
		//bean.setId(1);
		bean.setName("j");
		/*bean.setDescription("aaaaaaaaa");
		bean.setCourseId(5);
		bean.setCourseName("jaby koay");
		bean.setCreatedBy("Adity");
		bean.setModifiedBy("Anshu");
		bean.setCreatedDatetime(null);
		bean.setModifiedDatetime(null);*/
		List<SubjectBean>list = model.search(bean,1, 10);
		Iterator<SubjectBean>it = list.iterator();
		while(it.hasNext()){
			printAll(it.next());
		}
	}
	private static void printAll(SubjectBean bean){
		System.out.println(bean.getId()+"\t"+bean.getName()+"\t"+bean.getCourseId()+"\t"+bean.getCourseName()+"\t"+
				bean.getCreatedBy()+"\t"+bean.getModifiedBy()+"\t"+bean.getCreatedDatetime()+"\t"+bean.getModifiedDatetime());
	}
}
