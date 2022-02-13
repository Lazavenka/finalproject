package by.lozovenko.finalproject.model.dao;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.entity.Equipment;
import by.lozovenko.finalproject.model.entity.EquipmentState;
import by.lozovenko.finalproject.model.entity.EquipmentType;
import by.lozovenko.finalproject.model.entity.Laboratory;

import java.util.List;
import java.util.Optional;

public interface EquipmentDao extends BaseDao<Long, Equipment> {

    List<Equipment> findEquipmentByType(EquipmentType type, int offset, int recordsPerPage) throws DaoException;

    List<Equipment> findEquipmentByLaboratoryId(long laboratoryId) throws DaoException;

    int updateEquipmentPhoto(long id, String databasePath) throws DaoException;

    int countEquipment() throws DaoException;

    int countEquipmentByType(EquipmentType equipmentType) throws DaoException;

    List<Equipment> findAllLimited(int offset, int recordsPerPage) throws DaoException;
}
