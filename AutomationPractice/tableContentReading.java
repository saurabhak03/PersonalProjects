package AutomationPractice;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;
import static org.junit.Assert.assertEquals;


public class tableAssertion {
    public static void main(String args[]) throws ParseException {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/browserDriver/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.get("https://testpages.herokuapp.com/styled/tag/dynamic-table.html");
        driver.findElement(By.xpath("//summary[contains(text(),'Table Data')]")).click();
        String jsonString = "[{\"name\" : \"Bob\", \"age\" : 20, \"gender\": \"male\"}, {\"name\": \"George\", \"age\" : 42, \"gender\": \"male\"}, {\"name\":\n" +
                "\"Sara\", \"age\" : 42, \"gender\": \"female\"}, {\"name\": \"Conor\", \"age\" : 40, \"gender\": \"male\"}, {\"name\":\n" +
                "\"Jennifer\", \"age\" : 42, \"gender\": \"female\"}]";
        driver.findElement(By.id("jsondata")).clear();
        driver.findElement(By.id("jsondata")).sendKeys(jsonString);
        driver.findElement(By.id("refreshtable")).click();
        JSONArray jsonArrayOfInputData = convertStringToArray(jsonString);
        WebElement tableClass = driver.findElement(By.id("dynamictable"));
        List<WebElement> rows = tableClass.findElements(By.xpath("./tr"));
        int rowCount = 0;
        for(WebElement r : rows ){
            List<WebElement> column =r.findElements(By.xpath("./td"));
            JSONObject tempJsonObject = new JSONObject();
            int columnCount = 0;
            if(!column.isEmpty()) {
                for (WebElement c : column) {
                    if (columnCount == 0) {
                        tempJsonObject.put("name", c.getText());
                        columnCount += 1;
                    } else if (columnCount == 1) {
                        tempJsonObject.put("age", Integer.parseInt(c.getText()));
                        columnCount += 1;
                    } else {
                        tempJsonObject.put("gender", c.getText());
                    }
                }
                assertEquals(jsonArrayOfInputData.get(rowCount).toString(), tempJsonObject.toString());
                tempJsonObject.clear();
                rowCount += 1;
            }
        }
        driver.quit();

    }

    public static JSONArray convertStringToArray(String data) throws ParseException {
        JSONParser  par = new JSONParser();
        return (JSONArray) par.parse(data);

    }

}
