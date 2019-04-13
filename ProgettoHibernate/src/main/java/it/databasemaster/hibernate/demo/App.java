package it.databasemaster.hibernate.demo;

import java.util.List;
import org.hibernate.Session;

import it.databasemaster.hibernate.demo.model.Employee;

/**
 * Class used to perform CRUD operation on database with Hibernate API's
 */
public class App {
	@SuppressWarnings("unused")
	public static void main(String[] args) {

		App application = new App();

		/*
		 * Save few objects with hibernate
		 */
		int employeeId1 = application.saveEmployee("Sam", "Disilva", "2017-05-10");
		int employeeId2 = application.saveEmployee("Joshua", "Brill", "2017-05-15");
		int employeeId3 = application.saveEmployee("Peter", "Pan", "2017-05-20");
		int employeeId4 = application.saveEmployee("Bill", "Laurent", "2017-05-12");

		/*
		 * Retrieve all saved objects
		 */
		List<Employee> employees = application.getAllEmployees();
		System.out.println("List of all persisted employees >>>");
		for (Employee employee : employees) {
			System.out.println("Persisted Employee :" + employee);
		}

		/*
		 * Update an object
		 */
		application.updateEmployee(employeeId4, "2017-05-18");

		/*
		 * Deletes an object
		 */
		application.deleteEmployee(employeeId2);

		/*
		 * Retrieve all saved objects
		 */
		List<Employee> remaingEmployees = application.getAllEmployees();
		System.out.println("List of all remained persisted employees >>>");
		for (Employee employee : remaingEmployees) {
			System.out.println("Persisted Employee :" + employee);
		}

	}

	/**
	 * This method saves a Employee object in database
	 */
	public int saveEmployee(String firstName, String lastName, String hireDate) {
		Employee employee = new Employee();
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		employee.setHireDate(hireDate);

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		int id = (Integer) session.save(employee);
		session.getTransaction().commit();
		session.close();
		return id;
	}

	/**
	 * This method returns list of all persisted Employee objects/tuples from
	 * database
	 */
	public List<Employee> getAllEmployees() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		@SuppressWarnings("unchecked")
		List<Employee> employees = (List<Employee>) session.createQuery(
				"FROM Employee s ORDER BY s.lastName ASC").list();

		session.getTransaction().commit();
		session.close();
		return employees;
	}

	/**
	 * This method updates a specific Employee object
	 */
	public void updateEmployee(int id, String hireDate) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Employee employee = (Employee) session.get(Employee.class, id);
		employee.setHireDate(hireDate);
		//session.update(employee);//No need to update manually as it will be updated automatically on transaction close.
		session.getTransaction().commit();
		session.close();
	}

	/**
	 * This method deletes a specific Employee object
	 */
	public void deleteEmployee(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Employee employee = (Employee) session.get(Employee.class, id);
		session.delete(employee);
		session.getTransaction().commit();
		session.close();
	}
}
