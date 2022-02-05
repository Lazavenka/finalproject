package by.lozovenko.finalproject;

import by.lozovenko.finalproject.exception.ServiceException;
import by.lozovenko.finalproject.model.entity.*;
import by.lozovenko.finalproject.model.service.EquipmentTimeTableService;
import by.lozovenko.finalproject.model.service.impl.EquipmentTimeTableServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main { //todo DELETE NAFIG!
    public static void main(String[] args) {
        LocalTime localtime = LocalTime.parse("01:01");
        System.out.println(localtime);
        System.out.println(""+localtime.getHour()+"h "+localtime.getMinute()+"m "+localtime.getSecond()+"s");
    }
}
