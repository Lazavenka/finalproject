package by.lozovenko.finalproject.model.service;

import by.lozovenko.finalproject.exception.ServiceException;

import java.util.Optional;

public interface LaboratoryService {
    Optional<String> findLaboratoryNameById(Long id) throws ServiceException;
}
