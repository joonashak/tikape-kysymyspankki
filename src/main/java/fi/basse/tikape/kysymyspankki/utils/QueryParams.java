package fi.basse.tikape.kysymyspankki.utils;

import java.util.HashMap;
import java.util.Map;
import spark.Request;

public class QueryParams {
  
  public static Map<String, String> paramMap(Request req, String[] keys) {
    Map<String, String> data = new HashMap<>();
    
    for (String key : keys) {
      data.put(key, req.queryParams(key));
    }
    
    return data;
  }
}
