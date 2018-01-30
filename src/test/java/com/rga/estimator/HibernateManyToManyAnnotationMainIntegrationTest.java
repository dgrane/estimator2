package com.rga.estimator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.rga.estimator2.Employee;
import com.rga.estimator2.Project;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HibernateManyToManyAnnotationMainIntegrationTest {


    private SessionFactory sessionFactory;

    private Session session;

    @Before
    public final void before() {
        session = sessionFactory.openSession();
    }

    @After
    public final void after() {
        session.close();
    }
	
    @Test
    public void givenData_whenInsert_thenCreatesMtoMrelationship() {
    session =	sessionFactory.getCurrentSession();
        String[] employeeData = { "Peter Oven", "Allan Norman" };
        String[] projectData = { "IT Project", "Networking Project" };
        Set<Project> projects = new HashSet<>();
 
        for (String proj : projectData) {
            projects.add(new Project());
        }
 
        for (String emp : employeeData) {
            Employee employee = new Employee(emp.split(" ")[0], 
              emp.split(" ")[1]);
  
            assertEquals(0, employee.getProjects().size());
            employee.setProjects(projects);
            session.persist(employee);
  
            assertNotNull(employee);
        }
    }
 
    @Test
    public void givenSession_whenRead_thenReturnsMtoMdata() {
        @SuppressWarnings("unchecked")
        List<Employee> employeeList = session.createQuery("FROM Employee")
          .list();
  
        assertNotNull(employeeList);
  
        for(Employee employee : employeeList) {
            assertNotNull(employee.getProjects());
        }
    }
 
    // ...
}