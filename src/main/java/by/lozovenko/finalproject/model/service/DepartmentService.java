package by.lozovenko.finalproject.model.service;

import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Department;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DepartmentService {
    Optional<String> findDepartmentNameById(String id) throws ServiceException;

    Optional<String> findDepartmentNameById(Long id) throws ServiceException;

    List<Department> findAll() throws ServiceException;

    Optional<Department> findDepartmentById(String departmentId) throws ServiceException;

    Optional<Department> findDepartmentById(Long departmentId) throws ServiceException;

    boolean addNewDepartment(Map<String, String> departmentData) throws ServiceException;

    long countDepartments() throws ServiceException;
}
