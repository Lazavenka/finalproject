package by.lozovenko.finalproject.model.service;

import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.Equipment;
import by.lozovenko.finalproject.model.entity.EquipmentTimeTable;
import by.lozovenko.finalproject.model.entity.EquipmentType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EquipmentService {
    List<Equipment> findAll() throws ServiceException;

    List<Equipment> findAllByType(EquipmentType equipmentType) throws ServiceException;

    Optional<Equipment> findById(long equipmentId) throws ServiceException;

    Optional<Equipment> findById(String equipmentIdString) throws ServiceException;

    List<Equipment> findEquipmentByLaboratoryId(long laboratoryId) throws ServiceException;

    boolean addNewEquipment(Map<String, String> equipmentData) throws ServiceException;

    boolean updateImageByEquipmentId(long id, String databasePath) throws ServiceException;

    boolean updateEquipmentById(String equipmentToEditId, Map<String, String> equipmentData) throws ServiceException;

    long countEquipment() throws ServiceException;

    Optional<EquipmentTimeTable> provideEquipmentTimeTable(String equipmentIdString, String date) throws ServiceException;
}
