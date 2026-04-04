package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private WebDriver driver;

    // ========== ЛОКАТОРЫ ==========
    // Кнопка "Заказать" вверху страницы
    private final By topOrderButton = By.xpath(".//button[text()='Заказать']");
    // Кнопка "Заказать" внизу страницы (в блоке "Как это работает")
    private final By bottomOrderButton = By.xpath(".//button[text()='Заказать']");
    // Заголовки вопросов
    private final By accordionQuestions = By.className("accordion__button");
    // Текст ответа для конкретного вопроса – формируем динамически
    private String accordionAnswerLocator(int index) {
        return String.format("//div[@id='accordion__panel-%d']/p", index);
    }

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    // Клик по верхней кнопке "Заказать"
    public void clickTopOrderButton() {
        driver.findElement(topOrderButton).click();
    }

    // Клик по нижней кнопке "Заказать" (предварительно скроллим к ней)
    public void clickBottomOrderButton() {
        WebElement button = driver.findElement(bottomOrderButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
        button.click();
    }

    // Открыть вопрос с заданным индексом (от 0 до 7)
    public void clickQuestion(int index) {
        WebElement question = driver.findElements(accordionQuestions).get(index);
        // Прокручиваем к элементу, чтобы он точно попал в видимую область
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", question);
        // Небольшая пауза для завершения скролла
        try { Thread.sleep(300);
        } catch (InterruptedException e) { }
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", question);
    }

    // Получить текст ответа на вопрос с индексом index
    public String getAnswerText(int index) {
        // Ждём, пока панель с ответом станет видимой
        By answerLocator = By.xpath(accordionAnswerLocator(index));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement answer = wait.until(ExpectedConditions.visibilityOfElementLocated(answerLocator));
        return answer.getText();
    }
}