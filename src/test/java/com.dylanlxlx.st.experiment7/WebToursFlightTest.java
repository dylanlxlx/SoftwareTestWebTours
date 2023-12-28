package com.dylanlxlx.st.experiment7;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebToursFlightTest {
    /**
     * 驱动声明.
     */
    private WebDriver driver;

    /**
     * 路由地址.
     */
    private String baseUrl;

    /**
     * 用于存储错误信息.
     */
    private final StringBuffer verificationErrors = new StringBuffer();

    /**
     * 初始化driver以及baseUrl.
     *
     * @throws Exception 无
     */
    @BeforeEach
    public void setUp() throws Exception {
        baseUrl = "http://localhost:1080/WebTours/";
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    /**
     * 订票功能测试.
     *
     * @param username       用户名
     * @param password       密码
     * @param depart         出发城市
     * @param arrive         到达城市
     * @param departDate     起飞日期
     * @param returnDate     返航日期
     * @param numPassengers  乘客数
     * @param roundtrip      是否往返
     * @param seatPreference 座位喜好
     * @param seat           座位类型
     * @param findFlight     航班信息
     * @param address1       居住地信息
     * @param address2       居住城市信息
     * @param passengers     乘客名
     * @param creditCard     信用卡号
     * @param expDate        有效日期
     * @param info1          检查信息1
     * @param info2          检查信息2
     * @param info3          检查信息3
     * @param info4          检查信息4
     * @throws Exception 无
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/flight_test_case/订票功能测试用例.csv")
    public void test(String username, String password, String depart, String arrive, String departDate, String returnDate,
                     String numPassengers, int roundtrip, String seatPreference, String seat, String findFlight, String address1, String address2, String passengers,
                     String creditCard, String expDate, String info1, String info2, String info3, String info4) throws Exception {
        String[] passenger = passengers.split("#");
        String[] info = info2.split("#");
        String[] flight = findFlight.split("#");
        if (info.length > 1)
            info2 = info[0] + "\n" + info[1];
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
        driver.findElement(By.xpath("/html/body/center/center/a[1]/img")).click();
        driver.switchTo().parentFrame();
        driver.switchTo().frame(1);
        driver.findElement(By.name("depart")).click();
        new Select(driver.findElement(By.name("depart"))).selectByVisibleText(depart);
        driver.findElement(By.name("arrive")).click();
        new Select(driver.findElement(By.name("arrive"))).selectByVisibleText(arrive);
        driver.findElement(By.name("departDate")).click();
        driver.findElement(By.name("departDate")).clear();
        driver.findElement(By.name("departDate")).sendKeys(departDate);
        driver.findElement(By.name("returnDate")).click();
        driver.findElement(By.name("returnDate")).clear();
        driver.findElement(By.name("returnDate")).sendKeys(returnDate);
        driver.findElement(By.name("numPassengers")).click();
        driver.findElement(By.name("numPassengers")).clear();
        driver.findElement(By.name("numPassengers")).sendKeys(numPassengers);
        if (roundtrip == 1)
            driver.findElement(By.name("roundtrip")).click();
        if (Objects.equals(seatPreference, "None"))
            driver.findElement(By.xpath("//label[3]/input")).click();
        else if (Objects.equals(seatPreference, "Window"))
            driver.findElement(By.xpath("//label[2]/input")).click();
        else if (Objects.equals(seatPreference, "Aisle"))
            driver.findElement(By.name("seatPref")).click();
        if (Objects.equals(seat, "First"))
            driver.findElement(By.name("seatType")).click();
        else if (Objects.equals(seat, "Business"))
            driver.findElement(By.xpath("//td[3]/label[2]/input")).click();
        else if (Objects.equals(seat, "Coach"))
            driver.findElement(By.xpath("//td[3]/label[3]/input")).click();
        driver.findElement(By.name("findFlights")).click();
        if (Objects.equals(flight[0], "000"))
            driver.findElement(By.name("outboundFlight")).click();
        else if (Objects.equals(flight[0], "001"))
            driver.findElement(By.xpath("//tr[3]/td/input")).click();
        else if (Objects.equals(flight[0], "002"))
            driver.findElement(By.xpath("//tr[4]/td/input")).click();
        else if (Objects.equals(flight[0], "003"))
            driver.findElement(By.xpath("//tr[5]/td/input")).click();
        if (roundtrip == 1) {
            if (Objects.equals(flight[1], "000"))
                driver.findElement(By.name("returnFlight")).click();
            else if (Objects.equals(flight[1], "001"))
                driver.findElement(By.xpath("//table[2]/tbody/tr[3]/td/input")).click();
            else if (Objects.equals(flight[2], "002"))
                driver.findElement(By.xpath("//table[2]/tbody/tr[4]/td/input")).click();
            else if (Objects.equals(flight[1], "003"))
                driver.findElement(By.xpath("//table[2]/tbody/tr[5]/td/input")).click();
        }
        driver.findElement(By.name("reserveFlights")).click();
        driver.findElement(By.name("address1")).click();
        driver.findElement(By.name("address1")).clear();
        driver.findElement(By.name("address1")).sendKeys(address1);
        driver.findElement(By.name("address2")).click();
        driver.findElement(By.name("address2")).clear();
        driver.findElement(By.name("address2")).sendKeys(address2);
        for (int i = 0; i < parseInt(numPassengers); i++) {
            String pass = "pass" + (i + 1);
            driver.findElement(By.name(pass)).click();
            driver.findElement(By.name(pass)).clear();
            driver.findElement(By.name(pass)).sendKeys(passenger[i]);
        }
        driver.findElement(By.name("creditCard")).click();
        driver.findElement(By.name("creditCard")).clear();
        driver.findElement(By.name("creditCard")).sendKeys(creditCard);
        driver.findElement(By.name("expDate")).click();
        driver.findElement(By.name("expDate")).clear();
        driver.findElement(By.name("expDate")).sendKeys(expDate);
        driver.findElement(By.name("buyFlights")).click();
        assertEquals(info1, driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Cost'])[1]/following::u[1]")).getText());
        assertEquals(info2, driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Cost'])[1]/following::i[1]")).getText());
        assertEquals(info3, driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='-'])[1]/following::td[1]")).getText());
        assertEquals(info4, driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Total Cost'])[1]/following::b[1]")).getText());
        driver.switchTo().parentFrame();
        driver.switchTo().frame(0);
        driver.findElement(By.xpath("//img[@alt='Home Button']")).click();
    }

    /**
     * 输出错误信息.
     *
     * @throws Exception 无
     */
    @AfterEach
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!verificationErrorString.isEmpty()) {
            Assertions.fail(verificationErrorString);
        }
    }
}
