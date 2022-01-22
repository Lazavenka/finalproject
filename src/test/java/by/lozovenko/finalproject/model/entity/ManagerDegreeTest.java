package by.lozovenko.finalproject.model.entity;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ManagerDegreeTest {

    @Test
    public void testGetDegreeByString() {
        String bachelor = "B.Sc.";

        ManagerDegree expected = ManagerDegree.BACHELOR;

        ManagerDegree actual = ManagerDegree.getDegreeByString(bachelor);

        assertEquals(actual, expected);
    }
}