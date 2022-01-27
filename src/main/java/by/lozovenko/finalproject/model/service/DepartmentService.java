package by.lozovenko.finalproject.model.service;

import by.lozovenko.finalproject.exception.ServiceException;

import java.util.Optional;

public interface DepartmentService {
    Optional<String> findDepartmentNameById(Long id) throws ServiceException;
}
