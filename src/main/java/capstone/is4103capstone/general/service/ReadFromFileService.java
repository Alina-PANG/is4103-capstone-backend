package capstone.is4103capstone.general.service;

import capstone.is4103capstone.general.controller.FileController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ReadFromFileService {
    private static final Logger logger = LoggerFactory.getLogger(ReadFromFileService.class);
    public List<List<String>> readFromExcel(String filePath) {
        try{
            List<List<String>> records = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    for (int i=0;i<values.length;i++){
                        values[i] = values[i].trim();
                    }
                    records.add(Arrays.asList(values));
                }
            }
            return records;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }


}
