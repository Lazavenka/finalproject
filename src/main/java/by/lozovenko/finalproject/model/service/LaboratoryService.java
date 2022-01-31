package by.lozovenko.finalproject.model.service;

import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Laboratory;

import java.util.Optional;

public interface LaboratoryService {
    Optional<String> findLaboratoryNameById(Long id) throws ServiceException;

}
