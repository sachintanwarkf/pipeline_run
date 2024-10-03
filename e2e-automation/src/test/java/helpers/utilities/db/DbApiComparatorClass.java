package helpers.utilities.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class DbApiComparatorClass {
    private static final Logger LOGGER = LoggerFactory.getLogger(DbApiComparatorClass.class);

    public void compareDbApiValues(String primaryKey,List<Map<String, Object>> responseList, Map<Object, Map<String, Object>> projectDataMap)
    {
        int y=0;
        // we are going to get first key and its related values from db
        String firstKeyprojectDataMap= (String) projectDataMap.keySet().iterator().next();
        // we are going to check that this value index in the list we are getting from api
        for(int i=0;i<responseList.size();i++)
        {
            Map<String, Object> map = responseList.get(i); // Get each Map from the List
            String primaryKeyValue = String.valueOf(map.get(primaryKey)); // Convert to String
            if(primaryKeyValue.contentEquals(firstKeyprojectDataMap)){
                System.out.println("Match found at index: "+i);
                y=i;
                break;
            }
        }
        //comparing every value of the list
        Map<String,Object> apiMapToCompare=responseList.get(y);
        Map<String,Object> dbMapToCompare=projectDataMap.get(firstKeyprojectDataMap);
        getDbApiValues(apiMapToCompare,dbMapToCompare);
    }


    public void getDbApiValues(Map<String, Object> apiResponseMap, Map<String, Object> dbMap) {
        // Log the sizes of the maps
        LOGGER.info("API map size: " + apiResponseMap.size() + ", DB map size: " + dbMap.size());

        if (apiResponseMap.size() == dbMap.size()) {
            for (String key : apiResponseMap.keySet()) {
                Object apiValue = apiResponseMap.get(key);
                Object dbValue = dbMap.get(key);

                if (apiValue == null && dbValue == null) {
                    continue; // Both are null, consider them equal
                }

                if (apiValue == null || dbValue == null) {
                    // One is null and the other is not, they are not equal
                    Assert.fail(String.format("Mismatch for key '%s': API value = %s, DB value = %s", key, apiValue, dbValue));
                }

                // Assert that values match
                Assert.assertEquals(apiValue, dbValue, String.format("Mismatch for key '%s': API value = %s, DB value = %s", key, apiValue, dbValue));
            }
        } else {
            LOGGER.info("Field counts are not equal: API map size = " + apiResponseMap.size() + ", DB map size = " + dbMap.size());
            Assert.fail("Field counts are not equal");
        }
    }

}
