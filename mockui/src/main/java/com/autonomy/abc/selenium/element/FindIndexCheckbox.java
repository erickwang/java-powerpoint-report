package com.autonomy.abc.selenium.element;

import com.autonomy.abc.selenium.util.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FindIndexCheckbox extends Checkbox {
    public FindIndexCheckbox(WebElement element, WebDriver driver) {
        super(element, driver);
        box = findElement(By.className("database-icon"));
    }

    @Override
    public String getName() {
        return findElement(By.className("database-name")).getText();
    }

    @Override
    public boolean isChecked() {
        return ElementUtil.hasClass("fa-check", box);
    }
}
