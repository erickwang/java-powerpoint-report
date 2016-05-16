package com.autonomy.abc.selenium.users;

import com.autonomy.abc.selenium.auth.HsodUserBuilder;
import com.autonomy.abc.selenium.users.table.HsodDeveloperTable;
import com.autonomy.abc.selenium.users.table.HsodDeveloperTableRow;
import com.hp.autonomy.frontend.selenium.users.NewUser;
import com.hp.autonomy.frontend.selenium.users.Role;
import com.hp.autonomy.frontend.selenium.users.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HsodDevelopersPage extends HsodUserManagementBase<HsodDeveloperTableRow> {
    private HsodDevelopersPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public UserCreationModal userCreationModal() {
        throw new UnsupportedOperationException("Cannot add new developers to a tenancy");
    }

    @Override
    public User addNewUser(NewUser newUser, Role role) {
        throw new UnsupportedOperationException("Cannot add new developers to a tenancy");
    }

    @Override
    public HsodDeveloperTable getTable() {
        return new HsodDeveloperTable(findElement(By.cssSelector("#users-current-admins")), getDriver());
    }

    public User getUser(int index) {
        WebElement row = findElement(By.cssSelector(".users-table tbody tr:nth-of-type(" + (index+1) + ")"));
        String username = row.findElement(By.className("user-name")).getText();
        Role role = Role.fromString(row.findElement(By.className("user-role")).getText());
        return new HsodUserBuilder(username)
                .setRole(role)
                .build();
    }

    public void editUsername(User dev, String devUsername) {
        editUsernameLink(dev).click();
        editUsernameInput(dev).setAndSubmit(devUsername);
    }

    public static class Factory extends SOPageFactory<HsodDevelopersPage> {
        public Factory() {
            super(HsodDevelopersPage.class);
        }

        public HsodDevelopersPage create(WebDriver context) {
            return new HsodDevelopersPage(context);
        }
    }
}
