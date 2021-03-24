package project_4;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project_4.bean.TimeTableBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.model.TimeTableModel;
import in.co.rays.project_4.util.DataUtility;

public class Timetabletest {
	TimeTableModel model = new TimeTableModel();
	TimeTableBean bean = new TimeTableBean();
	public static void main(String[] args) throws ApplicationException, DuplicateRecordException {
		Timetabletest test = new Timetabletest();
		//test.testAdd();//works
		//test.testUpdate();//works
		//test.testDelete();//works
		//test.testFindByPk();//works
		//test.testList();//works
		test.testSearch();
	}
	public void testAdd() throws ApplicationException, DuplicateRecordException{
		bean.setCourseId(2);
		bean.setSubjectId(2);
		bean.setSemester("first");
		bean.setExamDate(null);
		bean.setTime("08:30");
		bean.setCreatedBy("Aditya Joshi");
		bean.setModifiedBy("Anshul Mehta");
		bean.setCreatedDatetime(null);
		bean.setModifiedDatetime(null);
		model.add(bean);
		
	}
	public void testUpdate() throws ApplicationException, DuplicateRecordException{
		bean.setId(1);
		bean.setCourseId(3);
		bean.setSubjectId(5);
		bean.setSemester("second");
		bean.setExamDate(null);
		bean.setTime("08:00");
		bean.setCreatedBy("Aditya Joshi");
		bean.setModifiedBy("Anshul Mehta");
		bean.setCreatedDatetime(null);
		bean.setModifiedDatetime(null);
		model.update(bean);
	}
	public void testDelete() throws ApplicationException{
		bean.setId(1);
		model.delete(bean);
	}
	public void testFindByPk() throws ApplicationException{
		printAll(model.findByPk(1));	
	}
	public void testList() throws ApplicationException{
		List<TimeTableBean>list = model.list(1, 10);
		Iterator<TimeTableBean>it = list.iterator();
		while(it.hasNext()){
			printAll(it.next());
		}	
	}
	public void testSearch() throws ApplicationException{
		//bean.setId(2);
		//bean.setCourseId(3);
		//bean.setCourseName("eng");
		//bean.setSubjectId(3);
		//bean.setSubjectName("worl");
		//bean.setSemester("second");
		//bean.setTime("10");
		//bean.setCreatedBy("a");
		//bean.setModifiedBy("j");
		//bean.setCreatedDatetime(null);
		//bean.setModifiedBy(null);
		bean.setExamDate(DataUtility.getSDate("2021-10-14"));
		List<TimeTableBean>list = model.search(bean, 1, 10);
		Iterator<TimeTableBean>it = list.iterator();
		while(it.hasNext()){
			printAll(it.next());
		}
	}
	private static void printAll(TimeTableBean bean){
		System.out.println(bean.getId()+"\t"+bean.getCourseId()+"\t"+bean.getCourseName()+"\t"+bean.getSubjectId()+bean.getSubjectName()
		+"\t"+bean.getSemester()+"\t"+bean.getExamDate()+"\t"+bean.getTime()+"\t"+bean.getCreatedBy()+"\t"+bean.getModifiedBy()+"\t"+bean.getCreatedDatetime()+"\t"+bean.getModifiedDatetime());
	}
	
}
