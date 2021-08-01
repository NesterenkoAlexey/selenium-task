package org.appline;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class SeleniumTaskTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void before() {

        System.setProperty("webdriver.chrome.driver" , "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        wait = new WebDriverWait(driver, 10, 1000);

        String baseUrl = "https://www.rgs.ru/";
        driver.get(baseUrl);
    }


    @Test
    public void test(){

        WebElement menu = driver.findElement(By.xpath("//a[@class='hidden-xs' and text()[contains(.,'Меню')]]"));
        menu.click();

        WebElement health = driver.findElement(By.xpath("//a[@class='hidden-xs' and text()[contains(.,'Здоровье')]]"));
        health.click();

        WebElement voluntaryHealthInsurance = driver.findElement(By.xpath("//a[text()[contains(.,'Добровольное медицинское страхование')]]"));
        voluntaryHealthInsurance.click();

        WebElement titleName = driver.findElement(By.xpath("//h1[text()[contains(.,'добровольное медицинское страхование')]]"));
        Assert.assertEquals("We are on voluntary health insurance page" , "ДМС — добровольное медицинское страхование", titleName.getText() );

        WebElement sendRequest = driver.findElement(By.xpath("//a[text()[contains(.,'Отправить заявку')]]"));
        sendRequest.click();

        waitTime(2000);

        WebElement аpplicationHealthInsurance = driver.findElement(By.xpath("//b[text()[contains(.,'Заявка на добровольное медицинское страхование')]]"));
        Assert.assertEquals("We open new window with application for voluntary health insurance" , "Заявка на добровольное медицинское страхование", аpplicationHealthInsurance.getText() );

        String lastName = "Иванов";
        String firstName = "Иван";
        String middleName = "Иванович";
        String phoneNumber = "9999999999";
        String email = "qwertyqwerty";
        String comment = "Я учусь Selenium'у :)";

        fillInputField(driver.findElement(By.xpath(String.format("//input[@name = 'LastName']"))), lastName);
        fillInputField(driver.findElement(By.xpath(String.format("//input[@name = 'FirstName']"))), firstName);
        fillInputField(driver.findElement(By.xpath(String.format("//input[@name = 'MiddleName']"))), middleName);


        WebElement region = driver.findElement(By.xpath("//select"));
        region.click();
        waitTime(500);
        Select selectRegion = new Select(region);
        selectRegion.selectByValue("77");


        WebElement phone = driver.findElement(By.xpath("//div[@class = 'form-group col-md-6 col-xs-12']//input[@data-bind[contains(.,'Phone')]]"));
        phone.click();
        waitTime(500);
        phone.sendKeys(phoneNumber);
        System.out.println(phone.getText());

        fillInputField(driver.findElement(By.xpath(String.format("//input[@name = 'Email']"))), email);
        fillInputField(driver.findElement(By.xpath(String.format("//textarea"))), comment);

        WebElement checBox = driver.findElement(By.xpath("//input[@type = 'checkbox']"));
        checBox.click();

        waitTime(500);

        WebElement buttonSend = driver.findElement(By.xpath("//button[@id='button-m']"));
        buttonSend.click();

        WebElement printCorrectField = driver.findElement(By.xpath("//span[@class=\"validation-error-text\" and text() ='Введите адрес электронной почты']"));
        Assert.assertEquals("We open sending user data window" , "Введите адрес электронной почты", printCorrectField.getText() );

        waitTime(4000);

    }

    @After
    public void after(){
        driver.quit();
    }
    private void waitTime(int timeSec){
        try {
            Thread.sleep(timeSec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    private void fillInputField(WebElement element, String value) {
        element.click();
      //  waitTime(500);
        element.clear();
        element.sendKeys(value);
        boolean checkFlag = wait.until(ExpectedConditions.attributeContains(element, "value", value));
        Assert.assertTrue("Поле было заполнено некорректно", checkFlag);
    }
}
