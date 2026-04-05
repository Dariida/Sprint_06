package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderSuccessModal {
    private final WebDriver driver;
    private final By successMessage = By.xpath(".//div[contains(text(),'Заказ оформлен')]");

    public OrderSuccessModal(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isOrderSuccessDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).isDisplayed();
    }
}
