package assessment;

import org.openqa.selenium.By;

/**
 * Centralized locator repository for all web elements across the application.
 * This class maintains all XPath, CSS Selector, and ID locators used in tests.
 */
public class Locators {

    // ============ Book Search Page Locators ============
    public static final By SEARCH_BOX = By.xpath("//input[@id='searchBox']");
    public static final String BOOK_LINK_TEMPLATE = "//span[@id='see-book-{0}']";
    public static final String BOOK_NAME_BY_ID_TEMPLATE = "see-book-{0}";
    public static final By AUTHOR_XPATH_TEMPLATE = By.xpath("//div[contains(text(), '{0}')]");

    // ============ Navigation Locators ============
    public static final By HEADER_WRAPPER_SECOND = By.xpath("(//div[contains(@class,'header-wrapper')])[2]");
    public static final By HEADER_WRAPPER_FIRST = By.xpath("(//div[contains(@class,'header-wrapper')])[1]");
    public static final By HEADER_WRAPPER_FIFTH = By.xpath("(//div[contains(@class,'header-wrapper')])[5]");

    // ============ Menu Item Locators ============
    public static final By MENU_ITEM_SECOND_ZERO = By.xpath("(//li[@id='item-0'])[2]");
    public static final By MENU_ITEM_FIRST_THREE = By.xpath("(//li[@id='item-3'])[1]");
    public static final By MENU_ITEM_FIFTH_ZERO = By.xpath("(//li[@id='item-0'])[5]");

    // ============ Form Locators ============
    public static final By FIRST_NAME = By.cssSelector("#firstName");
    public static final By LAST_NAME = By.cssSelector("#lastName");
    public static final By USER_NUMBER = By.cssSelector("#userNumber");
    public static final By GENDER_RADIO_MALE = By.cssSelector("label[for='gender-radio-1']");
    public static final By GENDER_RADIO_FEMALE = By.cssSelector("label[for='gender-radio-2']");
    public static final By GENDER_RADIO_OTHER = By.cssSelector("label[for='gender-radio-3']");
    public static final By SUBMIT_BUTTON = By.id("submit");
    public static final By CLOSE_MODAL = By.id("closeLargeModal");

    // ============ Table Locators ============
    public static final By WEB_TABLE = By.xpath("//div[contains(@class,'ReactTable')]");
    public static final By TABLE_ROW = By.className("rt-tr");
    public static final By TABLE_CELL = By.className("rt-td");

    // ============ Drag and Drop Locators ============
    public static final By VERTICAL_LIST = By.cssSelector(".vertical-list-container .list-group-item");

    // ============ Scroll Locators ============
    public static final String SCROLL_INTO_VIEW = "arguments[0].scrollIntoView(true);";
    public static final String SCROLL_BY_PIXELS = "window.scrollBy(0,{0})";
    public static final String JAVASCRIPT_CLICK = "arguments[0].click();";
}
