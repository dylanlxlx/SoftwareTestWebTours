package com.dylanlxlx.st.experiment7;


import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class WebToursSignoffTest {
    private WebDriver driver;
    private String baseUrl;
    JavascriptExecutor js;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        baseUrl = "http://localhost:1080/WebTours/";
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        js = (JavascriptExecutor) driver;
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testSignoff() {
        driver.get(baseUrl);
        driver.switchTo().frame(1);
        driver.switchTo().frame(0);
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("jojo");
        driver.findElement(By.name("password")).click();
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("bean");
        driver.findElement(By.name("login")).click();
        driver.switchTo().parentFrame();
        driver.switchTo().frame(0);
        driver.findElement(By.xpath("//img[@alt='SignOff Button']")).click();
        driver.switchTo().parentFrame();
        driver.switchTo().frame(1);
        String bodyText = driver.findElement(By.tagName("body")).getText();
        assertTrue(bodyText.contains("Welcome to the Web Tours site."), "退出登录成功返回登录界面");
    }
}
