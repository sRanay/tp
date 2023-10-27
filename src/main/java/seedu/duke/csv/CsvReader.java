package seedu.duke.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import seedu.duke.exception.DukeException;

import java.io.FileReader;
import java.io.IOException;

public class CsvReader {
    private CSVReader reader;

    public CsvReader(String filePath) throws DukeException {
        try {
            FileReader fileReader = new FileReader(filePath);
            this.reader = new CSVReaderBuilder(fileReader).withSkipLines(1).build();
        } catch (IOException e) {
            throw new DukeException("File cannot be read");
        }
    }

    public String[] readLine() throws DukeException{
        try {
            String[] line = reader.readNext();
            return line;
        } catch (IOException | CsvValidationException e) {
            throw new DukeException("Cannot read file");
        }
    }

    public void close() throws DukeException {
        try {
            reader.close();
        } catch (IOException e) {
            throw new DukeException("Error Closing File");
        }
    }
}
