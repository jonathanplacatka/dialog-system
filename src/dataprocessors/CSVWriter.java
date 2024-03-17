package dataprocessors;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import datatypes.Data;
import datatypes.DoubleData;
import eventsystem.EventGenerator;

public class CSVWriter extends DataProcessor {

    private String filepath;
    private BufferedWriter writer;

    public CSVWriter(String filepath) {
        this.filepath = filepath;
    }

    private void initWriter() throws IOException  {
        if(this.writer == null) {
            this.writer = Files.newBufferedWriter(Paths.get(this.filepath));
        }
    }

    @Override
    protected Data process(Data input, EventGenerator sender) {
        DoubleData doubleInput = (DoubleData)input; 
        double[] data = doubleInput.data;
        
        try {
            initWriter();
            for(double d : data) {
                writer.write(String.valueOf(d)+",");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    
}
