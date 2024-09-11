package ru.itmentor.spring.boot_security.demo.repository;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.model.Employee;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;


@Repository
@Transactional
public class EmployeeDAOImpl implements EmployeeDAO {
    @PersistenceContext
    private EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    @Override
    public List<Employee> getAllEmployees() {
        Session session = getSession();
        List<Employee> allEmployee = session.createQuery("from Employee", Employee.class).getResultList();
        return allEmployee;
    }

    @Override
    public void saveEmployee(Employee employee) {
        Session session = getSession();
        session.saveOrUpdate(employee);
    }

    @Override
    public Employee getEmployee(int id) {
        Session session = getSession();

        Employee employee = session.get(Employee.class, id);
        return employee;
    }

    @Override
    public void deleteEmployee(int id) {
        Session session = getSession();

        Query<Employee> query = session.createQuery("delete from Employee " +
                "where id =:employeeId");
        query.setParameter("employeeId", id);
        query.executeUpdate();
    }

    @Override
    public Optional<Employee> findByName(String name) {
        try {
            Employee employee = entityManager.createQuery("from Employee where name = :name", Employee.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return Optional.of(employee);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}

