package by.lozovenko.finalproject.model.service;

import java.util.Calendar;

public class AssistantService {
    Calendar calendar = Calendar.getInstance();
    public void getDate(){
        calendar.set(1, 2);
    }
}
