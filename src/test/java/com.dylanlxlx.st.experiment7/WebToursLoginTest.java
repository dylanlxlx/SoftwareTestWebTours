package com.dylanlxlx.st.experiment7;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.*;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class WebToursLoginTest {
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

    @ParameterizedTest
    @CsvFileSource(resources = "/logintestcase/正确用户名密码用例.csv")
    public void testLoginSuccess(String userName, String password) {
        // 打开登录页面
        driver.get(baseUrl);
        driver.switchTo().frame(1);
        driver.switchTo().frame(0);

        // 输入用户名和密码
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys(userName);
        driver.findElement(By.name("password")).click();
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys(password);

        // 提交表单
        driver.findElement(By.name("login")).click();

        driver.switchTo().parentFrame();
        driver.switchTo().frame(1);
        // 检查是否登录成功
        String bodyText = driver.findElement(By.tagName("body")).getText();
        assertTrue(bodyText.contains("Welcome,"), "登录成功的断言信息");
        assertTrue(bodyText.contains(userName), "登陆成功用户名信息");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/logintestcase/不正确用户名密码用例.csv")
    public void testLoginFailure(String userName, String password) {
        // 打开登录页面
        driver.get(baseUrl);
        driver.switchTo().frame(1);
        driver.switchTo().frame(0);

        // 输入错误的用户名和密码
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        usernameField.sendKeys(userName);
        passwordField.sendKeys(password);

        // 提交表单
        driver.findElement(By.name("login")).click();
        driver.switchTo().parentFrame();
        driver.switchTo().frame(1);
        // 检查是否显示错误信息
        String bodyText = driver.findElement(By.tagName("body")).getText();
        assertTrue(bodyText.contains("Error - Incorrect Password"), "登录失败的断言信息");
    }


    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
