package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class Test {
    SelenideElement form = $x(".//form");
    SelenideElement error = $x(".//div[@data-test-id='error-notification']");

    @BeforeEach
    public void setup() {
        open("http://localhost:9999/");
    }

    @org.junit.jupiter.api.Test
    public void TestUserActive() {
        UserData userActive = UserGenerator.generateUser("active");
        UserRegistration.registration(userActive);
        form.$x(".//span[@data-test-id='login']//input").val(userActive.getLogin());
        form.$x(".//span[@data-test-id='password']//input").val(userActive.getPassword());
        form.$x(".//button").click();
        $x(".//h2").should(text("Личный кабинет"));
    }

    @org.junit.jupiter.api.Test
    void TestUserBlocked() {
        UserData userBlocked = UserGenerator.generateUser("blocked");
        UserRegistration.registration(userBlocked);
        form.$x(".//span[@data-test-id='login']//input").val(userBlocked.getLogin());
        form.$x(".//span[@data-test-id='password']//input").val(userBlocked.getPassword());
        form.$x(".//button").click();
        error.should(visible);
        error.$x(".//div[@class='notification__content']").should(text("Пользователь заблокирован"));
        error.$x(".//button").click();
        error.should(hidden);
    }
}
