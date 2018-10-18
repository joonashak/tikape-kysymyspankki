package fi.basse.tikape.kysymyspankki.utils;

import java.util.HashMap;
import java.util.Map;

public class Validator {
  
  public static Map<String, Boolean> validate(Map<String, String> data) {
    Map<String, Boolean> results = new HashMap<>();
    
    for (Map.Entry<String, String> entry : data.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      
      if (value == null) {
        results.put(key, false);
        continue;
      }
      
      results.put(key, !value.trim().equals(""));
    }
    
    return results;
  }
}
