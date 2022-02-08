package by.lozovenko.finalproject.model.service;

import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Laboratory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LaboratoryService {
    Optional<String> findLaboratoryNameById(Long id) throws ServiceException;

    List<Laboratory> findLaboratoriesByDepartmentId(String selectedDepartmentId) throws ServiceException;
    List<Laboratory> findLaboratoriesByDepartmentId(Long selectedDepartmentId) throws ServiceException;

    Optional<Laboratory> findLaboratoryById(String laboratoryIdString) throws ServiceException;
    Optional<Laboratory> findLaboratoryById(Long laboratoryId) throws ServiceException;

    boolean addNewLaboratory(Map<String, String> laboratoryData) throws ServiceException;

    List<Laboratory> findAll() throws ServiceException;

    List<Laboratory> findLaboratoriesWithoutManager() throws ServiceException;

    long countLaboratories() throws ServiceException;
}
