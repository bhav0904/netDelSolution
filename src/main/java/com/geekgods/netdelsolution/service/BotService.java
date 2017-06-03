package com.geekgods.netdelsolution.service;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BotService {
    public List<Map<String,String>> getresources(String searchString) {
        System.setProperty("webdriver.chrome.driver", "/Users/gauravkhurana/Downloads/chromedriver 2");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS) ;
        driver.get("https://joblink.delaware.gov/ada/services/schools/SchSearch.cfm");
        Select select = new Select(driver.findElement(By.cssSelector("#cip")));
        List<WebElement> options = select.getOptions();
        for (int i = 0; i <= options.size() - 1; i++) {
            if (options.get(i).getText().contains(searchString)){
                select.selectByIndex(i);
                break;
            }
        }
        driver.findElement(By.cssSelector(".cfInputButton")).click();
        try {
            driver.findElement(By.id("cfOutputTableSmall"));
        }catch (NoSuchElementException e){
            driver.findElement(By.cssSelector(".cfInputButton")).click();
        }
        List<WebElement> elements = driver.findElement(By.id("cfOutputTableSmall")).findElements(By.tagName("tr"));
        List<String> keys = new ArrayList<>();
        List<Map<String,String>> listOfMap = new ArrayList<>();
        for (int j = 0; j <= elements.size() - 1; j++) {
            if (j == 0){
                List<WebElement> th = elements.get(j).findElements(By.tagName("th"));
                for (WebElement webElement : th) {
                    keys.add(webElement.getText());
                }
            } else{
                Map<String,String> map = new HashMap<>();
                List<WebElement> td = elements.get(j).findElements(By.tagName("td"));
                if(td.size() == keys.size()) {
                    for (int k = 0; k <= keys.size() - 1; k++) {
                        if (!td.get(k).getText().trim().isEmpty()) {
                            map.put(keys.get(k), td.get(k).getText());
                        }
                    }
                    map.put("URL", td.get(0).findElement(By.tagName("a")).getAttribute("href"));
                }
                if(!map.isEmpty()) {
                    listOfMap.add(map);
                }
            }
        }
        driver.quit();
        return listOfMap;
    }
}
