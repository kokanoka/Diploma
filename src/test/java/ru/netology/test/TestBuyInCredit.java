package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.page.BuyInCredit;
import ru.netology.page.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;
import static ru.netology.data.DataHelper.getValidCvc;
import static ru.netology.data.SqlUtils.*;

public class TestBuyInCredit {
    private MainPage mainPage = new MainPage();
    private BuyInCredit buyInCredit = mainPage.getBuyInCredit();

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
        var buyByCard = mainPage.getBuyByCard();
        buyInCredit.jumpToCard();
        buyByCard.jumpToCredit();
    }

    @Test
    void shouldBuyByApprovedCard() {
        buyInCredit.enterCardValues(getApprovedCardNumber(), getValidMonthNumber(), getValidYear(), getValidOwner(), getValidCvc());
        buyInCredit.getSuccessMessage();
        assertEquals(getOrder().getPayment_id(), getCreditRequest().getBank_id());
        assertEquals("APPROVED", getCreditRequest().getStatus());
    }

    @Test
    void shouldBuyByDeclinedCard() {
        buyInCredit.enterCardValues(getDeclinedCardNumber(), getValidMonthNumber(), getValidYear(), getValidOwner(), getValidCvc());
        buyInCredit.getErrorMessage();
        assertEquals(getOrder().getPayment_id(), getCreditRequest().getBank_id());
        assertEquals("DECLINED", getCreditRequest().getStatus());
    }

    @Test
    void shouldBuyByInvalidCardNumber() {
        buyInCredit.enterCardValues(getInvalidCardNumber(), getValidMonthNumber(), getValidYear(), getValidOwner(), getValidCvc());
        buyInCredit.getErrorFormat();
    }

    @Test
    void shouldBuyByEmptyCardNumber() {
        buyInCredit.enterCardValues(getEmptyCardNumber(), getValidMonthNumber(), getValidYear(), getValidOwner(), getValidCvc());
        buyInCredit.getErrorEmpty();
    }

    @Test
    void shouldBuyWithInvalidMonthNumber() {
        buyInCredit.enterCardValues(getApprovedCardNumber(), getInvalidMonthNumber(), getValidYear(), getValidOwner(), getValidCvc());
        buyInCredit.getErrorValidaty();
    }

    @Test
    void shouldBuyWithEmptyMonthNumber() {
        buyInCredit.enterCardValues(getApprovedCardNumber(), getEmptyMonthNumber(), getValidYear(), getValidOwner(), getValidCvc());
        buyInCredit.getErrorEmpty();
    }

    @Test
    void shouldBuyWithInvalidLastYear() {
        buyInCredit.enterCardValues(getApprovedCardNumber(), getValidMonthNumber(), getInvalidLastYear(), getValidOwner(), getValidCvc());
        buyInCredit.getErrorExpired();
    }

    @Test
    void shouldBuyWithInvalidYear() {
        buyInCredit.enterCardValues(getApprovedCardNumber(), getValidMonthNumber(), getInvalidYear(), getValidOwner(), getValidCvc());
        buyInCredit.getErrorFormat();
    }

    @Test
    void shouldBuyWithEmptyYear() {
        buyInCredit.enterCardValues(getApprovedCardNumber(), getValidMonthNumber(), getEmptyYear(), getValidOwner(), getValidCvc());
        buyInCredit.getErrorEmpty();
    }

    @Test
    void shouldBuyWithInvalidOwnerWithNumber() {
        buyInCredit.enterCardValues(getApprovedCardNumber(), getValidMonthNumber(), getValidYear(), getInvalidOwnerWithNumber(), getValidCvc());
        buyInCredit.getErrorFormat();
    }

    @Test
    void shouldBuyWithInvalidOwnerWithCyrillic() {
        buyInCredit.enterCardValues(getApprovedCardNumber(), getValidMonthNumber(), getValidYear(), getInvalidOwnerWithCir(), getValidCvc());
        buyInCredit.getErrorFormat();
    }

    @Test
    void shouldBuyWithEmptyOwner() {
        buyInCredit.enterCardValues(getApprovedCardNumber(), getValidMonthNumber(), getValidYear(), getEmptyOwner(), getValidCvc());
        buyInCredit.getErrorEmpty();
    }

    @Test
    void shouldBuyWithInvalidCvc() {
        buyInCredit.enterCardValues(getApprovedCardNumber(), getValidMonthNumber(), getValidYear(), getValidOwner(), getInvalidCvc());
        buyInCredit.getErrorFormat();
    }

    @Test
    void shouldBuyWithEmptyCvc() {
        buyInCredit.enterCardValues(getApprovedCardNumber(), getValidMonthNumber(), getValidYear(), getValidOwner(), getEmptyCvc());
        buyInCredit.getErrorEmpty();
    }
}
