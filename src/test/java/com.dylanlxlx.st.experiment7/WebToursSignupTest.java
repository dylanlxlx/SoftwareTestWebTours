package com.dylanlxlx.st.experiment7;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class WebToursSignupTest {
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

    @ParameterizedTest
    @CsvFileSource(resources = "/signuptestcase/注册新用户信息用例.csv")
    public void testSignupSuccess(String username, String password, String confirmPassword, String firstName, String lastName, String streetAddress, String cityStateZip) {
        driver.get(baseUrl);
        driver.switchTo().frame(1);
        driver.switchTo().frame(1);
        driver.findElement(By.linkText("sign up now")).click();
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).click();
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("passwordConfirm")).click();
        driver.findElement(By.name("passwordConfirm")).clear();
        driver.findElement(By.name("passwordConfirm")).sendKeys(confirmPassword);
        driver.findElement(By.name("firstName")).click();
        driver.findElement(By.name("firstName")).clear();
        driver.findElement(By.name("firstName")).sendKeys(firstName);
        driver.findElement(By.name("lastName")).click();
        driver.findElement(By.name("lastName")).clear();
        driver.findElement(By.name("lastName")).sendKeys(lastName);
        driver.findElement(By.name("address1")).click();
        driver.findElement(By.name("address1")).clear();
        driver.findElement(By.name("address1")).sendKeys(streetAddress);
        driver.findElement(By.name("address2")).click();
        driver.findElement(By.name("address2")).clear();
        driver.findElement(By.name("address2")).sendKeys(cityStateZip);
        driver.findElement(By.name("register")).click();

        driver.switchTo().parentFrame();
        driver.switchTo().frame(1);

        String bodyText = driver.findElement(By.tagName("body")).getText();
        assertTrue(bodyText.contains("Thank you, " + username), "注册成功的断言信息");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/signuptestcase/注册新用户无效信息用例.csv")
    public void testSignupFail(String username, String password, String confirmPassword, String warning) {
        driver.get(baseUrl);
        driver.switchTo().frame(1);
        driver.switchTo().frame(1);
        driver.findElement(By.linkText("sign up now")).click();
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).click();
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("passwordConfirm")).click();
        driver.findElement(By.name("passwordConfirm")).clear();
        driver.findElement(By.name("passwordConfirm")).sendKeys(confirmPassword);
        driver.findElement(By.name("register")).click();

        driver.switchTo().parentFrame();
        driver.switchTo().frame(1);

        String bodyText = driver.findElement(By.tagName("body")).getText();
        assertTrue(bodyText.contains(warning), "注册失败的断言信息");
    }
}

