package com.autonomy.abc.selenium.page.keywords;

import com.hp.autonomy.frontend.selenium.util.AppElement;
import com.hp.autonomy.frontend.selenium.util.AppPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public abstract class CreateNewKeywordsPage extends AppElement implements AppPage {

	public CreateNewKeywordsPage(final WebDriver driver) {
		super(new WebDriverWait(driver,30).until(ExpectedConditions.visibilityOfElementLocated(By.className("wrapper-content"))),driver);
	}

	@Override
	public void waitForLoad(){
		new WebDriverWait(getDriver(),30).until(ExpectedConditions.visibilityOfElementLocated(By.className("pd-wizard")));
	}

	public WebElement keywordsType(final KeywordType type) {
		return findElement(By.xpath(".//h4[contains(text(), '" + type.getTitle() + "')]/../.."));
	}

	public WebElement keywordsType(final KeywordType type, final WebDriverWait wait) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath(".//h4[contains(text(), '" + type.getTitle() + "')]/../..")));
	}

	public enum KeywordType {
		SYNONYM("Synonyms"),
		BLACKLIST("Blacklisted Terms");

		private final String title;

		KeywordType(final String title) {
			this.title = title;
		}

		public String getTitle() {
			return title;
		}
	}

	public WebElement cancelWizardButton(final WizardStep dataType) {
		return findElement(By.cssSelector("[data-step='" + dataType.getTitle() + "']")).findElement(By.xpath(".//button[contains(text(), 'Cancel')]"));
	}

	public enum WizardStep {
		TYPE("type"),
		TRIGGERS("triggers"),
		FINISH("finish-step");

		private final String title;

		WizardStep(final String title) {
			this.title = title;
		}

		public String getTitle() {
			return title;
		}
	}

	public WebElement continueWizardButton(final WizardStep dataType) {
		return findElement(By.cssSelector("[data-step='" + dataType.getTitle() + "']")).findElement(By.xpath(".//button[contains(text(), 'Continue')]"));
	}

	public WebElement addSynonymsButton() {
		return findElement(By.cssSelector(".synonyms-input-view [type='submit']"));
	}

	public WebElement addBlacklistTermsButton() {
		return findElement(By.cssSelector("[data-branch='blacklisted']")).findElement(By.xpath(".//i[contains(@class, 'fa-plus')]/.."));
	}

	public WebElement addSynonymsTextBox() {
		return findElement(By.cssSelector(".synonyms-input-view [name='words']"));
	}

	public WebElement addBlacklistedTextBox() {
		return findElement(By.cssSelector("[data-branch='blacklisted'] .form-group [name='words']"));
	}

	public WebElement finishWizardButton() {
		return findElement(By.cssSelector(".current-step .finish-step"));
	}

	public void addSynonyms(final String synonyms) {
		final WebElement addSynonymsTextBox = addSynonymsTextBox();
		addSynonymsTextBox.clear();
		addSynonymsTextBox.sendKeys(synonyms);
		tryClickThenTryParentClick(addSynonymsButton());
		loadOrFadeWait();
	}

	public void addBlacklistedTerms(final String blacklistedTerms) {
		final WebElement addBlacklistedTextBox = addBlacklistedTextBox();
		addBlacklistedTextBox.clear();
		addBlacklistedTextBox.sendKeys(blacklistedTerms);
		tryClickThenTryParentClick(addBlacklistTermsButton());
	}

	public int countKeywords() {
		loadOrFadeWait();
		return findElements(By.cssSelector(".remove-word")).size();
	}

    public int countKeywords(KeywordsPage.KeywordsFilter keywordType) {
        WebElement keywords = null;

        if (keywordType == KeywordsPage.KeywordsFilter.BLACKLIST){
            keywords = findElement(By.xpath("//div[@data-branch='blacklisted']"));
        } else if (keywordType == KeywordsPage.KeywordsFilter.SYNONYMS){
			keywords = findElement(By.xpath("//div[@data-branch='synonyms']"));
        }

        return keywords.findElements(By.cssSelector(".remove-word")).size();
    }

	public void createSynonymGroup(final String synonymGroup, final String language) throws InterruptedException {
		loadOrFadeWait();

		keywordsType(KeywordType.SYNONYM, new WebDriverWait(getDriver(),15)).click();
		selectLanguage(language);
		Thread.sleep(2000);
		continueWizardButton(WizardStep.TYPE).click();
		loadOrFadeWait();
		addSynonyms(synonymGroup);
		loadOrFadeWait();
		(new WebDriverWait(getDriver(),10)).until(ExpectedConditions.elementToBeClickable(finishWizardButton())).click();
		Thread.sleep(5000);
	}

	public void createBlacklistedTerm(final String blacklistedTerm, final String language) throws InterruptedException {
		keywordsType(KeywordType.BLACKLIST).click();
        selectLanguage(language);
        continueWizardButton(WizardStep.TYPE).click();
		loadOrFadeWait();
		addBlacklistedTerm(blacklistedTerm);
		loadOrFadeWait();
		finishWizardButton().click();
		//new WebDriverWait(getDriver(), 30).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".keywords-filters .dropdown-toggle")));
		Thread.sleep(5000); //TODO This isn't a great way to do it - but it's being used in createSynonymGroup, and the commented
								//out line above doesn't seem to be working
	}

	private void addBlacklistedTerm(final String blacklistedTerm) {
		addBlacklistedTextBox().clear();
		addBlacklistedTextBox().sendKeys(blacklistedTerm);
		loadOrFadeWait();
		addBlacklistTermsButton().click();
		loadOrFadeWait();
	}

	public void deleteKeyword(final String keyword) {
		findElement(By.xpath(".//span[contains(text(), '" + keyword + "')]/i")).click();
		loadOrFadeWait();
	}

	public List<String> getProspectiveKeywordsList() {
		final List<String> keywordsList = new ArrayList<>();

		for (final WebElement word : findElements(By.xpath(".//i[contains(@class, 'remove-word')]/.."))) {
			keywordsList.add(word.getText());
		}

		return keywordsList;
	}

	public WebElement languagesSelectBox() {
		return findElement(By.cssSelector("[data-step='type'] .dropdown-toggle"));
	}

	public abstract void selectLanguage(final String language);
}
