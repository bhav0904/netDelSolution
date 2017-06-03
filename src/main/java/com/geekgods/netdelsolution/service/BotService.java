package com.geekgods.netdelsolution.service;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class BotService {
    public static List<Map<String,String>> getresources(String searchString) {
        System.setProperty("webdriver.chrome.driver", "/Users/bhavanahindupur/Downloads/chromedriver");
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


    public static List<Map<String,String>> getLawyers(String city) {
        System.setProperty("webdriver.chrome.driver", "/Users/bhavanahindupur/Downloads/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS) ;
        driver.get("https://www.avvo.com/search/lawyer_search?utf8=%E2%9C%93&q=General%20Practice&loc=" + city + "&sort=relevancy&free_consultation=1");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> li = driver.findElement(By.cssSelector(".ruled-list.lawyer-search-results"))
                .findElements(By.tagName("li"));
        List<String> names = new ArrayList<>();
        List<String> ratings = new ArrayList<>();
        Set<String> href = new HashSet();
        List<Map<String,String>> lawyerData= new ArrayList<>();
        for (WebElement webElement : li) {
            WebElement element = null;
            WebElement ratingElement = null;
            WebElement tagElement = null;
            try{
                element = webElement.findElement(By.cssSelector(".u-vertical-margin-0"));
            }catch (NoSuchElementException e){
                continue;
            }
            try {
                ratingElement = webElement.findElement(By.tagName("strong"));
            }catch (NoSuchElementException w){
                continue;
            }
            try {
                tagElement = webElement.findElement(By.tagName("a"));
            }catch (NoSuchElementException w){
                continue;
            }
            ratings.add(ratingElement.getText());
            names.add(element.getText());
            href.add(tagElement.getAttribute("href"));
        }
        ArrayList<String> list = new ArrayList<>(href);
        for (int j = 0; j <= ratings.size() - 1; j++) {
                Map<String,String> tempMap = new HashMap<>();
                tempMap.put("Name",names.get(j));
                tempMap.put("Rating",ratings.get(j));
                tempMap.put("URL",list.get(j));
                lawyerData.add(tempMap);
        }
        driver.quit();
        System.out.println("========================================" + lawyerData);
        return lawyerData;
    }

    //TODO remove this method, was added for local testing
    public static void main(String[] args) {
        BotService botService = new BotService();
        botService.getLawyers("new york");
    }
}
