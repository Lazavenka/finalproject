package by.lozovenko.finalproject.dao;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.entity.Equipment;
import by.lozovenko.finalproject.entity.EquipmentState;
import by.lozovenko.finalproject.entity.EquipmentType;
import by.lozovenko.finalproject.entity.Laboratory;

import java.util.List;
import java.util.Optional;

public interface EquipmentDao extends BaseDao<Long, Equipment> {
    boolean updateEquipmentStateById(Long id, EquipmentState state) throws DaoException;

    List<Equipment> findEquipmentByType(EquipmentType type) throws DaoException;

    List<Equipment> findEquipmentByLaboratory(Laboratory laboratory) throws DaoException;

    Optional<Equipment> findEquipmentByName(String patternName) throws DaoException;

    List<Equipment> findAllActiveEquipmentByLaboratory(Laboratory laboratory) throws DaoException;

}
