package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BuyByCard {
    private SelenideElement heading = $$(".heading").find(exactText("Оплата по карте"));
    private SelenideElement cardNumberCard = $(".input__control[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthCard = $(".input__control[placeholder='08']");
    private SelenideElement yearCard = $(".input__control[placeholder='22']");
    private SelenideElement ownerCard = $$(".input__top").findBy(text("Владелец")).parent().find(".input__control");
    private SelenideElement cvcCard = $(".input__control[placeholder='999']");
    private SelenideElement buttonBuyCard = $$("button").findBy(text("Купить"));
    private SelenideElement buttonBuyInCredit = $$("button").findBy(text("Купить в кредит"));
    private SelenideElement buttonContinue = $$("button").findBy(text("Продолжить"));
    private ElementsCollection operation = $$(".notification__content");
    private SelenideElement error = $(".input__sub");


    public BuyByCard() {
        heading.shouldBe(visible);
    }

    public void enterCardValues (String cardNumber, String month, String year, String owner, String cvc) {
        cardNumberCard.setValue(cardNumber);
        monthCard.setValue(month);
        yearCard.setValue(year);
        ownerCard.setValue(owner);
        cvcCard.setValue(cvc);
        buttonContinue.click();
    }

    public BuyInCredit jumpToCredit() {
        buttonBuyInCredit.click();
        $$(".heading").findBy(text("Кредит по данным карты")).shouldBe(visible);
        return new BuyInCredit();
    }

    public void getSuccessMessage() {
        operation.get(0).shouldBe(visible, Duration.ofSeconds(20));
        operation.get(0).shouldHave(exactText("Операция одобрена Банком."));
    }

    public void getErrorMessage() {
        operation.get(1).shouldBe(visible, Duration.ofSeconds(20));
        operation.get(1).shouldHave(exactText("Ошибка! Банк отказал в проведении операции."));
    }

    public void getErrorValidaty() {
        error.shouldHave(text("Неверно указан срок действия карты"));
    }

    public void getErrorEmpty() {
        error.shouldHave(text("Поле обязательно для заполнения"));
    }

    public void getErrorFormat() {
        error.shouldHave(text("Неверный формат"));
    }

    public void getErrorExpired() {
        error.shouldHave(text("Истёк срок действия карты"));
    }

    public void clearForm () {
        cardNumberCard.sendKeys(Keys.CONTROL + "A");
        cardNumberCard.sendKeys(Keys.BACK_SPACE);
        monthCard.sendKeys(Keys.CONTROL + "A");
        monthCard.sendKeys(Keys.BACK_SPACE);
        yearCard.sendKeys(Keys.CONTROL + "A");
        yearCard.sendKeys(Keys.BACK_SPACE);
        ownerCard.sendKeys(Keys.CONTROL + "A");
        ownerCard.sendKeys(Keys.BACK_SPACE);
        cvcCard.sendKeys(Keys.CONTROL + "A");
        cvcCard.sendKeys(Keys.BACK_SPACE);
    }
}
