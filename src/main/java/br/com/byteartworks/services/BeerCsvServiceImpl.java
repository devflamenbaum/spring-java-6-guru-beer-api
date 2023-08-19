package br.com.byteartworks.services;

import br.com.byteartworks.records.BeerCSVRecord;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

/*
 *   @author: gabflbm. created on 18/08/2023 !
 */
@Service
public class BeerCsvServiceImpl implements BeerCsvService {

    @Override
    public List<BeerCSVRecord> convertCSV(File csvFile) {

        try {

            return new CsvToBeanBuilder<BeerCSVRecord>(new FileReader(csvFile))
                    .withType(BeerCSVRecord.class)
                    .build().parse();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
