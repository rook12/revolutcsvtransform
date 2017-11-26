package com.crm114discriminator.demo;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.Assert.*;

public class CsvConverterTest {

    @Test
    public void dateTest() throws ParseException {

        String startDate = "20 Nov 2017";

        DateFormat df = new SimpleDateFormat("d MMM YYYY");
        Date startDateObj = df.parse(startDate);


        System.out.println(startDateObj.toString());
    }

    @Test
    public void dateTestTwo() throws ParseException {
        String startDate = "2 Mar 2016";

        DateTimeFormatter df = DateTimeFormatter.ofPattern("d MMM yyyy");
        //LocalDate.parse(startDate, df);


        System.out.println(LocalDate.parse(startDate, df));

    }

}