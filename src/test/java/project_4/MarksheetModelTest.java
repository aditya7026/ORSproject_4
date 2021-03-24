package project_4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project_4.bean.MarksheetBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DatabaseException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.exception.RecordNotFoundException;
import in.co.rays.project_4.model.MarksheetModel;

public class MarksheetModelTest {
	MarksheetModel model = new MarksheetModel();
	public static void main(String[] args) throws DatabaseException, ApplicationException, DuplicateRecordException, RecordNotFoundException {
		MarksheetModelTest test = new MarksheetModelTest();
		//test.testNextPk();//works
		//test.testAdd();//add works
		//test.testFindByPK();//findByPK works
		//test.testFindByRollNo();//findByName works
		test.testUpdate();//update works
		//test.testDelete();//delete works
		//test.testList();//list works
		//test.testSearch();//Search works
		//test.testMeritList();
	}
	public void testDelete() throws ApplicationException{
		MarksheetBean bean = new MarksheetBean();
		bean.setId(1);
		model.delete(bean);
	}
	public void testUpdate() throws ApplicationException, DuplicateRecordException, RecordNotFoundException{
		MarksheetBean bean = new MarksheetBean();
		bean.setId(3);
		bean.setRollNo("Rays103");
		bean.setStudentId(1111);
		bean.setName("Aditya Joshi");
		bean.setPhysics(45);
		bean.setChemistry(56);
		bean.setMaths(48);
		bean.setCreatedBy("Aditya virendra Joshi");
		bean.setModifiedBy("Aditya Joshi");
		bean.setCreatedDatetime(null);
		bean.setModifiedDatetime(null);
		model.update(bean);
	}
	public void testNextPk() throws DatabaseException, ApplicationException{
		System.out.println(model.nextPK());
	}
	public void testAdd() throws ApplicationException, DuplicateRecordException, RecordNotFoundException{
		MarksheetBean bean = new MarksheetBean();
		bean.setRollNo("Rays103");
		bean.setStudentId(45856);
		bean.setName("Aditya Joshi");
		bean.setPhysics(48);
		bean.setChemistry(45);
		bean.setMaths(48);
		bean.setCreatedBy("Aditya Joshi");
		bean.setModifiedBy("Atul Jadhav");
		bean.setCreatedDatetime(null);
		bean.setModifiedDatetime(null);
		long i =model.add(bean);
		System.out.println(i);
	}
	public void testFindByPK() throws ApplicationException{
		MarksheetBean  bean = model.findByPk(2);
		printAll(bean);
	}
	public void testFindByRollNo() throws ApplicationException, RecordNotFoundException{
		MarksheetBean bean = new MarksheetBean();
		bean = model.findByRollNo("Rays101");
		printAll(bean);
	}
	public void testList() throws ApplicationException{
		MarksheetBean bean = new MarksheetBean();
		List<MarksheetBean>list = new ArrayList<MarksheetBean>();
		list = model.list(1, 10);
		Iterator<MarksheetBean>it = list.iterator();
		while(it.hasNext()){
			bean = it.next();
			printAll(bean);
			if(bean.getId()==(model.nextPK()-1)){
				System.out.println("Last Record");
			}
		}
		}
	 public void testSearch() throws ApplicationException{
		 List<MarksheetBean>list = new ArrayList<MarksheetBean>();
		 MarksheetBean bean = new MarksheetBean();
//		    bean.setId(1);
//		    bean.setName("Admin");
//			bean.setDescription("College");
//	  	bean.setCreatedBy("Aditya Joshi");
			bean.setModifiedBy("Atul");
//			bean.setCreatedDatetime(null);
//			bean.setModifiedDatetime(null);
		 list = model.search(bean, 1, 10);
		 Iterator<MarksheetBean>it = list.iterator();
			while(it.hasNext()){
				bean = it.next();
				printAll(bean);
				if(bean.getId()==(model.nextPK()-1)){
					System.out.println("Last Record");
				}
			}
	 }
	 public void testMeritList() throws ApplicationException{
		 List<MarksheetBean>list = new ArrayList<MarksheetBean>();
		 MarksheetBean bean = new MarksheetBean();
		 list = model.getMeritList(1,10);
		 Iterator<MarksheetBean>it = list.iterator();
			while(it.hasNext()){
				bean = it.next();
				printAll(bean);
				if(bean.getId()==(model.nextPK()-1)){
					System.out.println("Last Record");
				}
			}
	 }
	public static void printAll(MarksheetBean bean){
		System.out.print(bean.getId()+"\t");
		System.out.print(bean.getRollNo()+"\t");
		System.out.print(bean.getStudentId()+"\t");
		System.out.print(bean.getName()+"\t");
		System.out.print(bean.getPhysics()+"\t");
		System.out.print(bean.getChemistry()+"\t");
		System.out.print(bean.getMaths()+"\t");
	    System.out.print(bean.getCreatedBy()+"\t");
	    System.out.print(bean.getModifiedBy()+"\t");
	    System.out.print(bean.getCreatedDatetime()+"\t");
	    System.out.print(bean.getModifiedDatetime()+"\t");
	    System.out.println();
	}
}
