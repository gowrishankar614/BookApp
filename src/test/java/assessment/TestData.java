package assessment;

/**
 * Test data constants and configuration settings
 */
public class TestData {
    
    // ============ URLs ============
    public static final String BASE_URL = "https://demoqa.com/books";

    // ============ Book Search Test Data ============
    public static final String SEARCH_TERM = "JavaScript";
    public static final String EXPECTED_BOOK_NAME = "Programming JavaScript Applications";
    public static final String EXPECTED_AUTHOR = "Eric Elliott";

    // ============ Wait Times ============
    public static final long SHORT_WAIT = 2000;  // 2 seconds
    public static final long MEDIUM_WAIT = 3000; // 3 seconds
    public static final long LONG_WAIT = 5000;   // 5 seconds

    // ============ Scroll Distances ============
    public static final int SCROLL_250 = 250;
    public static final int SCROLL_300 = 300;
    public static final int SCROLL_500 = 500;

    // ============ Form Data ============
    public static final String EXCEL_DATA_PATH = "src/resources/data.xlsx";
    public static final String WEBTABLE_SHEET_NAME = "WebTable";

    // ============ Gender Options ============
    public static final String GENDER_MALE = "male";
    public static final String GENDER_FEMALE = "female";
    public static final String GENDER_OTHER = "other";
}
