package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {
    private final WebDriver driver;

    // ========== Локаторы первого шага ==========
    private final By nameInput = By.xpath(".//input[@placeholder='* Имя']");
    private final By surnameInput = By.xpath(".//input[@placeholder='* Фамилия']");
    private final By addressInput = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metroInput = By.xpath(".//input[@placeholder='* Станция метро']");
    private final By phoneInput = By.xpath(".//input[contains(@placeholder, 'Телефон')]");
    private final By nextButton = By.xpath(".//button[text()='Далее']");

    // ========== Локаторы второго шага ==========
    private final By dateInput = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    private final By rentalPeriodDropdown = By.className("Dropdown-control");
    private final String rentalPeriodOptionTemplate = ".//div[@class='Dropdown-option' and text()='%s']";
    private final String colorCheckboxTemplate = ".//label[text()='%s']/input";
    private final By commentInput = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    private final By orderButton = By.xpath("//div[contains(@class,'Order_Buttons')]//button[text()='Заказать']");
    private final By confirmButton = By.xpath(".//button[text()='Да']");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        // Ждём загрузки формы (появление поля Имя)
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(nameInput));
    }

    // Заполнение первого шага
    public void fillFirstForm(String name, String surname, String address, String metroStation, String phone) {
        driver.findElement(nameInput).sendKeys(name);
        driver.findElement(surnameInput).sendKeys(surname);
        driver.findElement(addressInput).sendKeys(address);
        // Выбор станции метро из выпадающего списка
        WebElement metro = driver.findElement(metroInput);
        metro.sendKeys(metroStation);
        metro.sendKeys(Keys.DOWN, Keys.ENTER);
        driver.findElement(phoneInput).sendKeys(phone);
        driver.findElement(nextButton).click();
    }

    // Заполнение второго шага
    public void fillSecondForm(String date, String rentalPeriod, String color, String comment) {
        // Поле даты
        WebElement dateField = driver.findElement(dateInput);
        dateField.sendKeys(date);
        dateField.sendKeys(Keys.ENTER);
        // Срок аренды
        driver.findElement(rentalPeriodDropdown).click();
        driver.findElement(By.xpath(String.format(rentalPeriodOptionTemplate, rentalPeriod))).click();
        driver.findElement(By.xpath(String.format(colorCheckboxTemplate, color))).click();
        // Комментарий
        driver.findElement(commentInput).sendKeys(comment);
        // Клик по кнопке "Заказать" (на втором шаге)
        driver.findElement(orderButton).click();
    }

    // Подтверждение заказа (кнопка "Да")
    public void confirmOrder() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.elementToBeClickable(confirmButton)).click();
    }
}
