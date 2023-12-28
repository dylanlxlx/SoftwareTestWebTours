package com.dylanlxlx.st.experiment7;

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

public class WebToursFlightTest<cost> {
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
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    /**
     * 订票功能测试.
     *
     * @param username      用户名
     * @param password      密码
     * @param depart        出发城市
     * @param arrive        到达城市
     * @param departDate    起飞日期
     * @param returnDate    返航日期
     * @param numPassengers 乘客数
     * @param roundtrip     是否往返
     * @param address1      居住地信息
     * @param address2      居住城市信息
     * @param passengers    乘客民
     * @param creditCard    信用卡号
     * @param expDate       有效日期
     * @param info1         检查信息1
     * @param info2         检查信息2
     * @param info3         检查信息3
     * @param info4         检查信息4
     * @throws Exception 无
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/flight_test_case/订票功能测试用例.csv")
    public void test(String username, String password, String depart, String arrive, String departDate, String returnDate,
                     String numPassengers, int roundtrip, String address1, String address2, String passengers,
                     String creditCard, String expDate, String info1, String info2, String info3, String info4) throws Exception {
        String[] passenger = passengers.split(" ");
        String[] info = info2.split("#");
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
        driver.findElement(By.name("findFlights")).click();
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
