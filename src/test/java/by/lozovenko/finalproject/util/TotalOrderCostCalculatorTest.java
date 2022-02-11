package by.lozovenko.finalproject.util;

import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalTime;

import static org.testng.Assert.*;

public class TotalOrderCostCalculatorTest {

    @Test
    public void testCalculateTotalCost() {
        BigDecimal pricePerHour = new BigDecimal("10.50");
        LocalTime researchTime = LocalTime.of(0, 30);
        BigDecimal expectedCost = new BigDecimal("5.25");

        BigDecimal actual = TotalOrderCostCalculator.calculateTotalCost(pricePerHour, researchTime);

        assertEquals(actual, expectedCost);
    }
}