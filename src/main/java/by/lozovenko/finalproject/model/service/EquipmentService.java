package by.lozovenko.finalproject.model.service;

import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Equipment;
import by.lozovenko.finalproject.model.entity.EquipmentType;

import java.util.List;
import java.util.Optional;

public interface EquipmentService {
    List<Equipment> findAll() throws ServiceException;
    List<Equipment> findAllByType(EquipmentType equipmentType) throws ServiceException;
    Optional<Equipment> findById(long equipmentId) throws ServiceException;

    List<Equipment> findEquipmentByLaboratoryId(long laboratoryId) throws ServiceException;
}
