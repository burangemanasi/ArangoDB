package mydb.firstproject;

import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.entity.BaseDocument;

public class FirstProject {
	
	Logger logger = LoggerFactory.getLogger(FirstProject.class);
	 
  public static void main(final String[] args) {
	  
	  
	  System.out.println("Initializing ArangoDb......");
	  final ArangoDB arangoDB = new ArangoDB.Builder().build();
	  String dbName = "studentDB";
	  String collectionName = "studentCol";
	  
	  
	  Scanner input= new Scanner(System.in);
	  int choice = 0;
	  
	  while(choice != 6) {
		 
		  System.out.println("1. Create record");
		  System.out.println("2. Read record");
		  System.out.println("3. Update record");
		  System.out.println("4. Delete record");
		  System.out.println("5. Reinitialize Database and Collection");
		  System.out.println("6. Exit");
	
		  System.out.println("Enter your choice : ");
		  choice = input.nextInt();
		  
		  switch(choice)
		  {
		  case 1:
			  //creating a document
			  BaseDocument student = new BaseDocument();
			  System.out.println("Enter ID");
			  String ID = input.next();
			  student.setKey(ID);
			  System.out.println("Enter First Name");
			  String fname = input.next();
			  student.addAttribute("FirstName", fname);
			  System.out.println("Enter Last Name");
			  String lname = input.next();
			  student.addAttribute("LastName", lname);
			  System.out.println("Enter University's Name");
			  String uname = input.next();
			  student.addAttribute("UniName", uname);
			  System.out.println("Program Name");
			  String pname = input.next();
			  student.addAttribute("ProgName", pname);
			  System.out.println("Enter GPA");
			  String gpa = input.next();
			  student.addAttribute("GPA", gpa);
			  try {
				  arangoDB.db(dbName).collection(collectionName).insertDocument(student);
				  System.out.println("Document created");
			  } catch (ArangoDBException e) {
				  System.err.println("Failed to create document. " + e.getMessage());
			  }
			  break;
		  
		  case 2:
			  //reading the created document
			  try {
				  System.out.println("Enter ID of student to check records :");
				  String checkID = input.next();
				 
				  BaseDocument rdStudent = arangoDB.db(dbName).collection(collectionName).getDocument(checkID,
						  BaseDocument.class);
				  System.out.println("ID: " + rdStudent.getKey());
				  System.out.println("FirstName: " + rdStudent.getAttribute("FirstName"));
				  System.out.println("Lastname: " + rdStudent.getAttribute("LastName"));
				  System.out.println("UniName: " + rdStudent.getAttribute("UniName"));
				  System.out.println("ProgName: " + rdStudent.getAttribute("ProgName"));
				  System.out.println("GPA: " + rdStudent.getAttribute("GPA"));
			  } catch (NullPointerException e) {
				  System.err.println("Record doesn't exist. ");
			  }
			  break;
			  
		  case 3:
			//updating the document
			  System.out.println("Enter the ID of student to update records : ");
			  String upID = input.next();
			  if(arangoDB.db(dbName).collection(collectionName).documentExists(upID)) {

				  BaseDocument upStudent = arangoDB.db(dbName).collection(collectionName).getDocument(upID, BaseDocument.class);
				  
				  upStudent.setKey(upID);
				  System.out.println("Enter First Name");
				  String upFname = input.next();
				  upStudent.addAttribute("FirstName", upFname);
				  System.out.println("Enter Last Name");
				  String upLname = input.next();
				  upStudent.addAttribute("LastName", upLname);
				  System.out.println("Enter University's Name");
				  String upUniname = input.next();
				  upStudent.addAttribute("UniName", upUniname);
				  System.out.println("Program Name");
				  String upPname = input.next();
				  upStudent.addAttribute("ProgName", upPname);
				  System.out.println("Enter GPA");
				  String upGpa = input.next();
				  upStudent.addAttribute("GPA", upGpa);
				  
				  try {
					  arangoDB.db(dbName).collection(collectionName).updateDocument(upID, upStudent);
				  } catch (ArangoDBException e) {
					  System.err.println("Failed to update document. " + e.getMessage());
				  }
			  }
			  else {
				  System.out.println("Record #" + upID + " doesn't exist. Failed to update record.");
			  }
			  break;
			  
		  case 4:
			//delete a document
			  System.out.println("Enter ID of record to delete : ");
			  String delID = input.next();
			  
			  try {
				  arangoDB.db(dbName).collection(collectionName).deleteDocument(delID);
				  System.out.println("Record #" + delID + " deleted successfully.");
				} catch (ArangoDBException e) {
				  System.err.println("The record ID does not exist. Failed to delete record.");
				}
			  break;
			  
		  case 5:
			  //re-initializing database and collection
			  try {
			  arangoDB.createDatabase(dbName);
			  arangoDB.db(dbName).createCollection(collectionName);
			  } catch (ArangoDBException e) {
				  System.err.println("Database already created.");
			  }
			  break;
			  
		  case 6:
			  input.close();
			  break;
			 
		  default:
			  System.out.println("Enter correct choice...");  
		  
		  }
		  
	  }
	
	
	 /* //create database
	  try {
		  arangoDB.createDatabase(dbName);
		  System.out.println("Database created: " + dbName);
	  }
	  catch(final ArangoDBException e) {
		  System.err.println("Failed to create database: " + dbName + "; " + e.getMessage());
	  }
	  
	  //create collection
	  String collectionName = "StudentCollection";
	  try {
	    CollectionEntity myArangoCollection = arangoDB.db(dbName).createCollection(collectionName);
	    System.out.println("Collection created: " + myArangoCollection.getName());
	  } catch (ArangoDBException e) {
	    System.err.println("Failed to create collection: " + collectionName + "; " + e.getMessage());
	  }*/
	  
	  
	  
	 
	    
	  
  }
}