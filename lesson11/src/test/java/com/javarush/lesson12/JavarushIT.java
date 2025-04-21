package com.javarush.lesson12;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class JavarushIT {

    public static final String DIV_CLASS_SEARCH_PANEL = "//textarea[@class=\"gLFyf\"]";

    @Test
    @DisplayName("When open search in AUT then show many result")
    void whenOpenSearchInAutThenShowManyResult() throws InterruptedException {
        Playwright playwright = Playwright.create();
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setHeadless(false);
        BrowserType chromium = playwright.chromium();
        Browser browser = chromium.launch(launchOptions);
        try (playwright; browser) {
            Page page = browser.newContext(new Browser.NewContextOptions()).newPage();
            page.navigate("https://javarush.com/search/lecture?query=WebDriver");
            int timeout = 5_000;
            ElementHandle panel = page.waitForSelector(DIV_CLASS_SEARCH_PANEL, getTimeout(timeout));
            panel.fill("WebDriver");
            panel.press("\n");
            Thread.sleep(1);
//            page.waitForLoadState();
//            var timeout = new Page.WaitForSelectorOptions().setTimeout(5_000);
//            page.waitForSelector(email_field,timeout).fill(emailValue);
//            page.waitForSelector(password_field).fill(passwordValue);
//            page.waitForSelector(button_submit).click();
//            // ...... and so on ......
        }
    }

    private static Page.WaitForSelectorOptions getTimeout(int timeout) {
        return new Page.WaitForSelectorOptions().setTimeout(timeout);
    }
}
