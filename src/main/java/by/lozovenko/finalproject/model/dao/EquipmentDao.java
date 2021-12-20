package by.lozovenko.finalproject.model.dao;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.entity.Equipment;
import by.lozovenko.finalproject.model.entity.EquipmentState;
import by.lozovenko.finalproject.model.entity.EquipmentType;
import by.lozovenko.finalproject.model.entity.Laboratory;

import java.util.List;
import java.util.Optional;

public interface EquipmentDao extends BaseDao<Long, Equipment> {
    boolean updateEquipmentStateById(Long id, EquipmentState state) throws DaoException;

    List<Equipment> findEquipmentByType(EquipmentType type) throws DaoException;

    List<Equipment> findEquipmentByLaboratory(Laboratory laboratory) throws DaoException;

    Optional<Equipment> findEquipmentByName(String patternName) throws DaoException;

    List<Equipment> findAllActiveEquipmentByLaboratory(Laboratory laboratory) throws DaoException;

}
