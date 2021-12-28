package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {
    private SelenideElement heading = $("h2.heading");
    private SelenideElement buttonBuyCard = $$("button").findBy(text("Купить"));
    private SelenideElement buttonBuyInCredit = $$("button").findBy(text("Купить в кредит"));

    public MainPage() {
        heading.shouldBe(visible);
    }

    public BuyByCard getBuyByCard() {
        buttonBuyCard.click();
        return new BuyByCard();
    }

    public BuyInCredit getBuyInCredit() {
        buttonBuyInCredit.click();
        return new BuyInCredit();
    }

}
