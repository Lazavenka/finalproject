package by.lozovenko.finalproject.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;

public class TotalOrderCostCalculator {
    final static int MINUTES_IN_HOUR = 60;

    public static BigDecimal calculateTotalCost(BigDecimal pricePerHour, LocalTime researchTime) {
        int hours = researchTime.getHour();
        int minutes = researchTime.getMinute();
        BigDecimal bigDecimalHoursPart = BigDecimal.valueOf(hours);
        BigDecimal bigDecimalMinutesPart = BigDecimal.valueOf(minutes).divide(BigDecimal.valueOf(MINUTES_IN_HOUR), 4, RoundingMode.CEILING);
        BigDecimal timeInHours = bigDecimalHoursPart.add(bigDecimalMinutesPart);
        return pricePerHour.multiply(timeInHours).setScale(2, RoundingMode.CEILING);
    }
}
