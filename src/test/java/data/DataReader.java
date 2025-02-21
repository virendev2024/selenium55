package data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;


public class DataReader
{
    public List<HashMap<String, String>> getJsonDataToMap() throws IOException {
                         // will scan the entire content of json and convert into string
        String jsonContent = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"\\src\\test\\java\\data\\PurchaseOrder.json"), StandardCharsets.UTF_8);
        // convert String(jsonContent) to hashmap with jackson databind dependency
        ObjectMapper mapper = new ObjectMapper();
        //ObjectMapper has a method called readvalue which reads the string and convert to hashmap
        List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
        });
                        //data variable is a list with 2 arguments(hashmap1,hashmap2)
        return data;
    }
}
