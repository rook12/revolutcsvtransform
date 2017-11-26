package com.crm114discriminator.demo;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class CsvConverter {

    private static final Logger logger = LoggerFactory.getLogger(CsvConverter.class);

    private String csvInput;
    private String csvOutFile;

    @Autowired
    public CsvConverter(@Value("${revolut.csv.file}") String csvInput,
                        @Value("${revolut.csv.file.out}") String csvOutFile) {
        this.csvInput = csvInput;
        this.csvOutFile = csvOutFile;

        logger.info("csvInput - " + this.csvInput);

    }

    public void runConversion() throws Exception {
        logger.info("running conversion - " + this.csvInput);
        File f = new File(this.csvInput);
        if(!f.exists()) {
            throw new Exception(String.format("path doesn't exist - %s", this.csvInput));
        }

        File temp = File.createTempFile("csvTemp", ".tmp");
        temp.deleteOnExit();

        BufferedWriter tempFileWriter = new BufferedWriter(new FileWriter(temp));

        for (String line : Files.readAllLines(Paths.get(this.csvInput))) {
            String newline = line.replaceAll(" ; ", "\t");
            newline = newline.replaceAll("; ", "\t");
            System.out.println(line);
            System.out.println(newline);
            tempFileWriter.write(newline);
            tempFileWriter.newLine();
        }
        tempFileWriter.close();

        Reader in = new FileReader(temp);
        Iterable<CSVRecord> records = CSVFormat.TDF.withHeader().parse(in);

        //Target
        //Date,Payee,Category,Memo,Outflow,Inflow

        BufferedWriter outFileWriter = new BufferedWriter(new FileWriter(this.csvOutFile));

        outFileWriter.write("Date,Payee,Category,Memo,Outflow,Inflow");
        outFileWriter.newLine();

        for (CSVRecord record : records) {
            logger.info(record.toString());
            String startDate = record.get("Completed Date");
            String reference = record.get("Reference");
            String moneyIn = record.get("Paid In (GBP)");
            String moneyOut = record.get("Paid Out (GBP)");
            String category = record.get("Category");


            //TODO - Simple date format seems to be returning the same date object timestamp for all dates. Something to do with the loop maybe?
            logger.info("'" + startDate + "'");

            DateTimeFormatter df = DateTimeFormatter.ofPattern("d MMM yyyy");
            LocalDate localDate = LocalDate.parse(startDate, df);

            logger.info(localDate.toString());

            outFileWriter.write(String.format("%s,%s,%s,%s,%s,%s", localDate.toString(), reference, category, "", moneyOut, moneyIn));
            outFileWriter.newLine();
        }

        in.close();

        outFileWriter.close();


    }




}
