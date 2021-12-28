package ru.netology.test;


import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SqlUtils;
import ru.netology.page.BuyByCard;
import ru.netology.page.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;
import static ru.netology.data.SqlUtils.getOrder;
import static ru.netology.data.SqlUtils.getPayment;

public class TestBuyByCard {
    private MainPage mainPage = new MainPage();
    private BuyByCard buyByCard = mainPage.getBuyByCard();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeAll
    static void setUp() {
        open("http://localhost:8080");
    }

    @BeforeEach
    void clearAll() {
        var buyInCredit = mainPage.getBuyInCredit();
        buyByCard.jumpToCredit();
        buyInCredit.jumpToCard();
    }

    @Test
    void shouldBuyByApprovedCard() {
        buyByCard.enterCardValues(getApprovedCardNumber(), getValidMonthNumber(), getValidYear(), getValidOwner(), getValidCvc());
        buyByCard.getSuccessMessage();
        assertEquals(getOrder().getPayment_id(), getPayment().getTransaction_id());
        assertEquals("APPROVED", getPayment().getStatus());
    }

    @Test
    void shouldBuyByDeclinedCard() {
        buyByCard.enterCardValues(getDeclinedCardNumber(), getValidMonthNumber(), getValidYear(), getValidOwner(), getValidCvc());
        buyByCard.getErrorMessage();
        assertEquals(getOrder().getPayment_id(), getPayment().getTransaction_id());
        assertEquals("DECLINED", getPayment().getStatus());
    }

    @Test
    void shouldBuyByInvalidCardNumber() {
        buyByCard.enterCardValues(getInvalidCardNumber(), getValidMonthNumber(), getValidYear(), getValidOwner(), getValidCvc());
        buyByCard.getErrorFormat();
    }

    @Test
    void shouldBuyByEmptyCardNumber() {
        buyByCard.enterCardValues(getEmptyCardNumber(), getValidMonthNumber(), getValidYear(), getValidOwner(), getValidCvc());
        buyByCard.getErrorFormat();
    }

    @Test
    void shouldBuyWithInvalidMonthNumber() {
        buyByCard.enterCardValues(getApprovedCardNumber(), getInvalidMonthNumber(), getValidYear(), getValidOwner(), getValidCvc());
        buyByCard.getErrorValidaty();
    }

    @Test
    void shouldBuyWithEmptyMonthNumber() {
        buyByCard.enterCardValues(getApprovedCardNumber(), getEmptyMonthNumber(), getValidYear(), getValidOwner(), getValidCvc());
        buyByCard.getErrorFormat();
    }

    @Test
    void shouldBuyWithInvalidLastYear() {
        buyByCard.enterCardValues(getApprovedCardNumber(), getValidMonthNumber(), getInvalidLastYear(), getValidOwner(), getValidCvc());
        buyByCard.getErrorExpired();

    }

    @Test
    void shouldBuyWithInvalidYear() {
        buyByCard.enterCardValues(getApprovedCardNumber(), getValidMonthNumber(), getInvalidYear(), getValidOwner(), getValidCvc());
        buyByCard.getErrorFormat();
    }

    @Test
    void shouldBuyWithEmptyYear() {
        buyByCard.enterCardValues(getApprovedCardNumber(), getValidMonthNumber(), getEmptyYear(), getValidOwner(), getValidCvc());
        buyByCard.getErrorFormat();
    }

    @Test
    void shouldBuyWithInvalidOwnerWithNumber() {
        buyByCard.enterCardValues(getApprovedCardNumber(), getValidMonthNumber(), getValidYear(), getInvalidOwnerWithNumber(), getValidCvc());
        buyByCard.getErrorFormat();
    }

    @Test
    void shouldBuyWithInvalidOwnerWithCyrillic() {
        buyByCard.enterCardValues(getApprovedCardNumber(), getValidMonthNumber(), getValidYear(), getInvalidOwnerWithCir(), getValidCvc());
        buyByCard.getErrorFormat();
    }

    @Test
    void shouldBuyWithEmptyOwner() {
        buyByCard.enterCardValues(getApprovedCardNumber(), getValidMonthNumber(), getValidYear(), getEmptyOwner(), getValidCvc());
        buyByCard.getErrorFormat();
    }

    @Test
    void shouldBuyWithInvalidCvc() {
        buyByCard.enterCardValues(getApprovedCardNumber(), getValidMonthNumber(), getValidYear(), getValidOwner(), getInvalidCvc());
        buyByCard.getErrorFormat();
    }

    @Test
    void shouldBuyWithEmptyCvc() {
        buyByCard.enterCardValues(getApprovedCardNumber(), getValidMonthNumber(), getValidYear(), getValidOwner(), getEmptyCvc());
        buyByCard.getErrorEmpty();
    }
}
