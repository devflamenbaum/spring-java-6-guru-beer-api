package br.com.byteartworks.services;

import br.com.byteartworks.records.BeerCSVRecord;

import java.io.File;
import java.util.List;

/*
 *   @author: gabflbm. created on 18/08/2023 !
 */
public interface BeerCsvService {

    List<BeerCSVRecord> convertCSV(File csvFile);
}
