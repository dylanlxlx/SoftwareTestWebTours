package com.dylanlxlx.st.experiment7;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class WebToursCheckTest {
    private WebDriver driver;
    private String baseUrl;
    private final StringBuffer verificationErrors = new StringBuffer();
    JavascriptExecutor js;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\71554\\Desktop\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
        baseUrl = "http://localhost:1080/WebTours/";
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        js = (JavascriptExecutor) driver;
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/check_test_case/查询功能测试用例.csv")
    public void testWebToursCheck(String id, String username, String password, String numPassengers, String Seat, String address1, String address2, String passengers, String info, String totalCharge, String cardNum) {
        driver.get(baseUrl);
        driver.switchTo().frame(1);
        driver.switchTo().frame(0);
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).click();
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("login")).click();
        driver.switchTo().defaultContent();
        driver.switchTo().frame(1);
        driver.switchTo().frame(0);
        driver.findElement(By.xpath("//img[@alt='Itinerary Button']")).click();
        driver.switchTo().parentFrame();
        driver.switchTo().frame(1);
        String peopleNum = Objects.equals(numPassengers, "1") ? "A" : numPassengers;
        String[] passagerName = passengers.split("#");
        passengers = passagerName[0];
        for (int i = 1; i < passagerName.length; i++) {
            passengers += "\n" + passagerName[i];
        }
        assertEquals("  " + peopleNum + " " + Seat + " class tickets for :", driver.findElement(By.xpath("/html/body/blockquote/form/center/table[1]/tbody/tr["+id+"]/td[2]/b")).getText());
        assertEquals(passengers, driver.findElement(By.xpath("/html/body/blockquote/form/center/table[1]/tbody/tr["+id+"]/td[2]/center")).getText());
        assertEquals("Jojo Bean\n" + address1 + "\n" + address2 + "\n\nTotal Charge: " + totalCharge + "\n(CC: x-" + cardNum + ")", driver.findElement(By.xpath("/html/body/blockquote/form/center/table[1]/tbody/tr["+id+"]/td[4]/center/table/tbody/tr/td")).getText());
        String[] infoTemp = info.split("#");
        if (infoTemp.length > 1)
            info = infoTemp[0] + "\n" + infoTemp[1];
        id = Integer.parseInt(id)+1+"";
        assertEquals(info, driver.findElement(By.xpath("/html/body/blockquote/form/center/table[1]/tbody/tr["+id+"]/td/center")).getText());
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
