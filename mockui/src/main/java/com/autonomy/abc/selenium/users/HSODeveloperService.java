package com.autonomy.abc.selenium.users;

import com.autonomy.abc.selenium.config.SearchOptimizerApplication;
import com.autonomy.abc.selenium.config.HSOApplication;
import com.autonomy.abc.selenium.menu.NavBarTabId;
import com.autonomy.abc.selenium.page.AppBody;
import com.autonomy.abc.selenium.page.ElementFactory;
import com.autonomy.abc.selenium.page.HSOElementFactory;
import com.autonomy.abc.selenium.page.admin.HSODevelopersPage;
import com.autonomy.abc.selenium.util.Waits;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HSODeveloperService {
    private final HSOApplication application;
    private final HSOElementFactory elementFactory;
    private HSODevelopersPage devsPage;

    private AppBody getBody() {
        return application.createAppBody(getDriver());
    }

    private WebDriver getDriver() {
        return elementFactory.getDriver();
    }

    public HSODeveloperService(SearchOptimizerApplication application, ElementFactory elementFactory){
        this.application = (HSOApplication) application;
        this.elementFactory = (HSOElementFactory) elementFactory;
    }

    public HSODevelopersPage goToDevs(){
        getBody().getSideNavBar().switchPage(NavBarTabId.DEVELOPERS);
        devsPage = elementFactory.getDevsPage();
        return devsPage;
    }

    public User editUsername(User user, String newUsername) {
        devsPage = goToDevs();
        WebElement pencil = devsPage.editUsernameLink(user);
        pencil.click();
        devsPage.editUsernameInput(user).setAndSubmit(newUsername);
        new WebDriverWait(getDriver(),10).until(ExpectedConditions.visibilityOf(pencil));
        Waits.loadOrFadeWait();
        ((HSOUser) user).setUsername(newUsername);
        return user;
    }
}
