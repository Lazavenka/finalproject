package by.lozovenko.finalproject.model.service;

import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.EquipmentType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EquipmentTypeService {
    List<EquipmentType> findAll() throws ServiceException;

    Optional<EquipmentType> findById(String equipmentId) throws ServiceException;

    boolean addNewEquipmentType(Map<String, String> equipmentTypeData) throws ServiceException;
}
