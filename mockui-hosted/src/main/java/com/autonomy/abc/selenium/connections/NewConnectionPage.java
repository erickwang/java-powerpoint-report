package com.autonomy.abc.selenium.connections;

import com.autonomy.abc.selenium.icma.ICMAPageBase;
import com.hp.autonomy.frontend.selenium.util.AppElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NewConnectionPage extends ICMAPageBase {
    private NewConnectionPage(WebDriver driver) {
        super(driver);
    }

    public ConnectorTypeStepTab getConnectorTypeStep(){
        return ConnectorTypeStepTab.make(getDriver());
    }

    public ConnectorIndexStepTab getIndexStep(){
        return ConnectorIndexStepTab.make(getDriver());
    }

    public ConnectorConfigStepTab getConnectorConfigStep(){
        return ConnectorConfigStepTab.make(getDriver());
    }

    private WebElement menuButton(String text) {
        return findElement(By.className("actions")).findElement(By.xpath(".//a[contains(text(), '" + text + "')]"));
    }

    public WebElement nextButton() {
        return menuButton("Next");
    }

    WebElement finishButton() {
        return menuButton("Finish");
    }

    WebElement cancelButton() {
        return menuButton("Cancel");
    }

    public AppElement connectorTypeStepTab() {
        return new AppElement(findElement(By.id("stepAnchor1")), getDriver());
    }

    public static class Factory extends ICMAPageFactory<NewConnectionPage> {
        public Factory() {
            super(NewConnectionPage.class);
        }

        @Override
        public NewConnectionPage create(WebDriver context) {
            new WebDriverWait(context, 30).until(ExpectedConditions.visibilityOfElementLocated(By.className("wizard")));
            return new NewConnectionPage(context);
        }
    }
}
