package project_4;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project_4.bean.UserBean;
import in.co.rays.project_4.exception.ApplicationException;
import in.co.rays.project_4.exception.DatabaseException;
import in.co.rays.project_4.exception.DuplicateRecordException;
import in.co.rays.project_4.exception.RecordNotFoundException;
import in.co.rays.project_4.model.UserModel;

public class UserModelTest {
	UserModel model = new UserModel();
	public static void main(String[] args) throws SQLException, DuplicateRecordException, ApplicationException, DatabaseException, RecordNotFoundException {
		  // TODO Auto-generated method stub
		  //System.out.println(model.nextPK());//nextPK works
		  UserModelTest test = new UserModelTest();
          //test.testAdd();//add works
		  //test.testUpdate();//update works
		  //test.testAuthenticate();//authenticate works  
		  test.testFindByLogin();//findByLogin works
		  //test.testFindByPK();//findByPk works
		  //test.testChangePassword();//changePassword works
		  //test.testRegister();//register works
		  //test.testList();//list works
		  //test.testSearch();//Search works
		
	}
	public void testSearch() throws DatabaseException, ApplicationException {
		UserBean bean = new UserBean();
	     // bean.setId(2);
	    //  bean.setFirstName("An");
    // bean.setLastName("ja");
	 //     bean.setLogin("A");
//	      bean.setPassword("1");
//	      bean.setDob(null);
//	      bean.setMobileNo("85");
//	      bean.setRoleId(2);
//	      bean.setUnSuccessfullLogin(0);
//	      bean.setGender("mal");
//	      bean.setLastLogin(null);
//	      bean.setRegisteredIP("10");
//	      bean.setLastLoginIP("10");
//	      bean.setCreatedBy("Aditya Joshi");
//	      bean.setModifiedBy("Aditya Joshi");
//	      bean.setCreatedDatetime(null);
	      bean.setModifiedDatetime(null);
	      UserModel model = new UserModel();
	      List<UserBean>list = new ArrayList<UserBean>();
	      list  = model.search(bean, 1, 10);
	      Iterator<UserBean>it = list.iterator();
	      while(it.hasNext()){
				bean = it.next();
				PrintAll(bean);
			}
	      
	}
	public void testList() throws ApplicationException{
		UserBean bean = new UserBean();
		UserModel model = new UserModel();
		List<UserBean>list = new ArrayList<UserBean>();
		list = model.list(1, 10);
		Iterator<UserBean>it = list.iterator();
		while(it.hasNext()){
			bean = it.next();
			PrintAll(bean);
			if(bean.getId()==(model.nextPK()-1)){
				System.out.println("Last Record");
			}
		}
	}
	
	public void testChangePassword() throws DatabaseException, DuplicateRecordException{
		boolean b=false;
		try {
			b = model.changePassword(1, "password", "123456");
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(b);
	}
	public void testFindByPK() throws DatabaseException, ApplicationException, SQLException{
		UserBean bean = new UserBean();
		bean = model.findByPk(2);
		PrintAll(bean);
	}
	public void testFindByLogin() throws DatabaseException, SQLException, ApplicationException{
		UserBean bean = new UserBean();
		bean = model.findByLogin("Anshul12@gmail.com");
		PrintAll(bean);
	}
	public void testAuthenticate() throws RecordNotFoundException, ApplicationException, SQLException{
		UserBean bean = new UserBean();
		bean = 	model.authenticate("Anshu12", "123456");
	if(bean!=null){
	PrintAll(bean);
	System.out.println("Valid User");
	}
	else if(bean==null){
		System.out.println("not a valid user");
	}	
	}
	public void testUpdate() throws DuplicateRecordException, ApplicationException, SQLException{
		UserBean bean = new UserBean();
	      bean.setId(39);
	      bean.setFirstName("Anshulji");
	      bean.setLastName("Shastri");
	      bean.setLogin("Anshul12@gmail.com");
	      bean.setPassword("123456");
	      bean.setDob(new Date());
	      bean.setMobileNo("8827463818");
	      bean.setRoleId(5);
	      bean.setUnSuccessfullLogin(0);
	      bean.setGender("Male");
	      bean.setLastLogin(null);
	      bean.setRegisteredIP("101.3401");
	      bean.setLastLoginIP("1542.2932");
	      bean.setCreatedBy("Aditya Joshi");
	      bean.setModifiedBy("Aditya Joshi");
	      bean.setCreatedDatetime(null);
	      bean.setModifiedDatetime(null);
	      model.update(bean);
	}
	public void testDelete() throws ApplicationException, SQLException{
		
		UserBean bean = new UserBean();
		bean.setId(2);
		model.delete(bean);
		
		
	}
	public void testAdd() throws SQLException, ApplicationException, DuplicateRecordException{
	  UserBean bean = new UserBean();	
     // bean.setId(2);
      bean.setFirstName("Aditya");
      bean.setLastName("Joshi");
      bean.setLogin("Aditya60@gmail.com");
      bean.setPassword("password");
      bean.setDob(new Date());
      bean.setMobileNo("8827463818");
      bean.setRoleId(5);
      bean.setUnSuccessfullLogin(0);
      bean.setGender("male");
      bean.setLastLogin(null);
      bean.setRegisteredIP("101.3401");
      bean.setLastLoginIP("1542.2932");
      bean.setCreatedBy("Aditya Joshi");
      bean.setModifiedBy("Aditya Joshi");
      bean.setCreatedDatetime(null);
      bean.setModifiedDatetime(null);
      model.add(bean);	
	}
	public void testRegister() throws DuplicateRecordException, ApplicationException{
		  UserBean bean = new UserBean();
		  UserModel model = new UserModel();
	      bean.setId(2);
	      bean.setFirstName("Anuj");
	      bean.setLastName("mehta");
	      bean.setLogin("Anuj@gmail.com");
	      bean.setPassword("password");
	      bean.setDob(null);
	      bean.setMobileNo("8827463818");
	      bean.setRoleId(5);
	      bean.setUnSuccessfullLogin(0);
	      bean.setGender("male");
	      bean.setLastLogin(null);
	      bean.setRegisteredIP("101.3401");
	      bean.setLastLoginIP("1542.2932");
	      bean.setCreatedBy("Aditya Joshi");
	      bean.setModifiedBy("Aditya Joshi");
	      bean.setCreatedDatetime(null);
	      bean.setModifiedDatetime(null);
	      long l = model.registerUser(bean);
	      System.out.println(l);
	}
	public static void PrintAll(UserBean bean){
	       System.out.print(bean.getId()+"\t");
	       System.out.print(bean.getFirstName()+"\t");
	       System.out.print(bean.getLastName()+"\t");
	       System.out.print(bean.getLogin()+"\t");
	       System.out.print(bean.getPassword()+"\t");
	       System.out.print(bean.getDob()+"\t");
	       System.out.print(bean.getMobileNo()+"\t");
	       System.out.print(bean.getRoleId()+"\t");
	       System.out.print(bean.getUnSuccessfullLogin()+"\t");
	       System.out.print(bean.getGender()+"\t");
	       System.out.print(bean.getLastLogin()+"\t");
	       System.out.print(bean.getRegisteredIP()+"\t");
	       System.out.print(bean.getLastLoginIP()+"\t");
	       System.out.print(bean.getCreatedBy()+"\t");
	       System.out.print(bean.getModifiedBy()+"\t");
	       System.out.print(bean.getCreatedDatetime()+"\t");
	       System.out.print(bean.getModifiedDatetime()+"\t");
	       System.out.println();
	}
}
