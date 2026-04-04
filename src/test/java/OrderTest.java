import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pages.MainPage;
import pages.OrderPage;
import pages.OrderSuccessModal;

public class OrderTest extends BaseTest {

    @ParameterizedTest(name = "Заказ через {0} кнопку. Данные: {1} {2}, {3}, {4}, {5} -> {6}")
    @CsvSource({
            // buttonPosition, name, surname, address, metro, phone, date, rentalPeriod, color, comment
            "top, Иван, Петров, ул. Ленина 1, Тверская, 89991112233, 08.05.2026, сутки, чёрный жемчуг, Позвоните за 10 минут",
            "top, Коля, Петров, ул. Ленина 23, Белорусская, 89997772233, 08.05.2026, трое суток, серая безысходность, Не звонить",
            "bottom, Анна, Сидорова, пр. Мира 2, Комсомольская, 89261234567, 10.05.2026, двое суток, серая безысходность, Домофон 123",
            "bottom, Витя, Мишин, ул. Анны Ахматовой 23, Парк Победы, 89997772233, 09.05.2026, трое суток, серая безысходность, Не звонить"
    })
    @DisplayName("Позитивный сценарий заказа самоката с разными данными и разными кнопками")
    public void positiveOrderFlow(String buttonPosition, String name, String surname, String address,
                                  String metro, String phone, String date, String rentalPeriod,
                                  String color, String comment) {
        MainPage MainPage = new MainPage(driver);
        // Выбираем кнопку в зависимости от параметра
        if (buttonPosition.equals("top")) {
            MainPage.clickTopOrderButton();
        } else {
            MainPage.clickBottomOrderButton();
        }

        OrderPage orderPage = new OrderPage(driver);
        orderPage.fillFirstForm(name, surname, address, metro, phone);
        orderPage.fillSecondForm(date, rentalPeriod, color, comment);
        orderPage.confirmOrder();

        OrderSuccessModal successModal = new OrderSuccessModal(driver);
        boolean isSuccess = successModal.isOrderSuccessDisplayed();
        Assertions.assertTrue(isSuccess, "Окно успешного оформления заказа не появилось");
    }
}