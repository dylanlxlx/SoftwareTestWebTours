package com.dylanlxlx.st.experiment7;


import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class WebToursLoginTest {
    private WebDriver driver;
    private String baseUrl;
    private StringBuffer verificationErrors = new StringBuffer();
    JavascriptExecutor js;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        baseUrl = "http://localhost:1080/WebTours/";
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        js = (JavascriptExecutor) driver;
    }

    @Test
    public void testWebToursLogin() {
        driver.get(baseUrl);
        driver.switchTo().frame(1);
        driver.switchTo().frame(0);
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("5120216063");
        driver.findElement(By.name("password")).click();
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("ww");
        driver.findElement(By.name("login")).click();
        //ERROR: Caught exception [ERROR: Unsupported command [selectFrame | relative=parent | ]]
        //ERROR: Caught exception [ERROR: Unsupported command [selectFrame | index=1 | ]]
        driver.switchTo().parentFrame();
        driver.switchTo().frame(1);
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Error'])[1]/following::h3[1]")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Error'])[1]/following::h3[1]")).click();
        //ERROR: Caught exception [ERROR: Unsupported command [selectFrame | relative=parent | ]]
        //ERROR: Caught exception [ERROR: Unsupported command [selectFrame | index=0 | ]]
        driver.switchTo().parentFrame();
        driver.switchTo().frame(0);
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).click();
        //ERROR: Caught exception [ERROR: Unsupported command [doubleClick | name=username | ]]
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("jojo");
        driver.findElement(By.name("password")).click();
        driver.findElement(By.name("password")).click();
        //ERROR: Caught exception [ERROR: Unsupported command [doubleClick | name=password | ]]
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("bean");
        driver.findElement(By.name("password")).sendKeys(Keys.ENTER);
        //ERROR: Caught exception [ERROR: Unsupported command [selectFrame | relative=parent | ]]
        //ERROR: Caught exception [ERROR: Unsupported command [selectFrame | index=1 | ]]
        driver.switchTo().parentFrame();
        driver.switchTo().frame(1);
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='jojo'])[1]/preceding::p[1]")).click();
        //ERROR: Caught exception [ERROR: Unsupported command [selectFrame | relative=parent | ]]
        //ERROR: Caught exception [ERROR: Unsupported command [selectFrame | index=0 | ]]
        driver.switchTo().parentFrame();
        driver.switchTo().frame(0);
        driver.findElement(By.xpath("//img[@alt='SignOff Button']")).click();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!verificationErrorString.isEmpty()) {
            fail(verificationErrorString);
        }
    }
}
