package org.springframework.samples.petclinic.it;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewPetFirstVisitIT {

    private static final Logger logger = LoggerFactory.getLogger(NewPetFirstVisitIT.class);

    private static boolean RUN_HTMLUNIT;

    private static boolean RUN_IE;

    private static boolean RUN_FIREFOX;

    private static boolean RUN_CHROME;

    private static boolean RUN_OPERA;

    private static boolean RUN_ANDROID;

    private static String SELENIUM_HUB_URL;

    private static String SELENIUM_HUB_URL_ANDROID;

    private static String TARGET_SERVER_URL;

    private static String TARGET_SERVER_URL_ANDROID;

    @BeforeClass
    public static void initEnvironment() {

        RUN_HTMLUNIT = getConfigurationProperty(
            "RUN_HTMLUNIT",
            "test.run.htmlunit",
            true);

        logger.info("running the tests in HtmlUnit: " + RUN_HTMLUNIT);

        RUN_IE = getConfigurationProperty(
            "RUN_IE",
            "test.run.ie",
            false);

        logger.info("running the tests in Internet Explorer: " + RUN_IE);

        RUN_FIREFOX = getConfigurationProperty(
            "RUN_FIREFOX",
            "test.run.firefox",
            false);

        logger.info("running the tests in Firefox: " + RUN_FIREFOX);

        RUN_CHROME = getConfigurationProperty(
            "RUN_CHROME",
            "test.run.chrome",
            false);

        logger.info("running the tests in Chrome: " + RUN_CHROME);

        RUN_OPERA = getConfigurationProperty(
            "RUN_OPERA",
            "test.run.opera",
            false);

        logger.info("running the tests in Opera: " + RUN_OPERA);

        RUN_ANDROID = getConfigurationProperty(
            "RUN_ANDROID",
            "test.run.android",
            false);

        logger.info("running the tests in Android: " + RUN_ANDROID);

        SELENIUM_HUB_URL = getConfigurationProperty(
            "SELENIUM_HUB_URL",
            "test.selenium.hub.url",
            "http://localhost:4444/wd/hub");

        logger.info("using Selenium hub at: " + SELENIUM_HUB_URL);

        SELENIUM_HUB_URL_ANDROID = getConfigurationProperty(
            "SELENIUM_HUB_URL_ANDROID",
            "test.selenium.hub.url.android",
            "http://localhost:4448/wd/hub");

        logger.info("using Selenium hub for Android at: " + SELENIUM_HUB_URL_ANDROID);

        TARGET_SERVER_URL = getConfigurationProperty(
            "TARGET_SERVER_URL",
            "test.target.server.url",
            "http://localhost:58080/petclinic");

        logger.info("using target server at: " + TARGET_SERVER_URL);

        TARGET_SERVER_URL_ANDROID = getConfigurationProperty(
            "TARGET_SERVER_URL_ANDROID",
            "test.target.server.url.android",
            "http://localhost:58080/petclinic");

        logger.info("using target server for Android at: " + TARGET_SERVER_URL_ANDROID);
    }

    private static String getConfigurationProperty(String envKey, String sysKey, String defValue) {

        String retValue = defValue;
        String envValue = System.getenv(envKey);
        String sysValue = System.getProperty(sysKey);
        // system property prevails over environment variable
        if (sysValue != null) {
            retValue = sysValue;
        } else if (envValue != null) {
            retValue = envValue;
        }
        return retValue;
    }

    private static boolean getConfigurationProperty(String envKey, String sysKey, boolean defValue) {

        boolean retValue = defValue;
        String envValue = System.getenv(envKey);
        String sysValue = System.getProperty(sysKey);
        // system property prevails over environment variable
        if (sysValue != null) {
            retValue = Boolean.parseBoolean(sysValue);
        } else if (envValue != null) {
            retValue = Boolean.parseBoolean(envValue);
        }
        return retValue;
    }

    @Test
    public void testHtmlUnit()
        throws MalformedURLException, IOException {

        Assume.assumeTrue(RUN_HTMLUNIT);

        WebDriver driver = new HtmlUnitDriver();
        testNewPetFirstVisit(driver, TARGET_SERVER_URL);
    }

    @Test
    public void testIE()
        throws MalformedURLException, IOException {

        Assume.assumeTrue(RUN_IE);

        Capabilities browser = DesiredCapabilities.internetExplorer();
        WebDriver driver = new RemoteWebDriver(new URL(SELENIUM_HUB_URL), browser);
        testNewPetFirstVisit(driver, TARGET_SERVER_URL);
    }

    @Test
    public void testFirefox()
        throws MalformedURLException, IOException {

        Assume.assumeTrue(RUN_FIREFOX);

        Capabilities browser = DesiredCapabilities.firefox();
        WebDriver driver = new RemoteWebDriver(new URL(SELENIUM_HUB_URL), browser);
        testNewPetFirstVisit(driver, TARGET_SERVER_URL);
    }

    @Test
    public void testChrome()
        throws MalformedURLException, IOException {

        Assume.assumeTrue(RUN_CHROME);

        Capabilities browser = DesiredCapabilities.chrome();
        WebDriver driver = new RemoteWebDriver(new URL(SELENIUM_HUB_URL), browser);
        testNewPetFirstVisit(driver, TARGET_SERVER_URL);
    }

    @Test
    public void testOpera()
        throws MalformedURLException, IOException {

        Assume.assumeTrue(RUN_OPERA);

        Capabilities browser = DesiredCapabilities.operaBlink();
        WebDriver driver = new RemoteWebDriver(new URL(SELENIUM_HUB_URL), browser);
        testNewPetFirstVisit(driver, TARGET_SERVER_URL);
    }

    @Ignore @Test
    public void testAndroid()
        throws MalformedURLException, IOException {

        Assume.assumeTrue(RUN_ANDROID);

        //WebDriver driver = new AndroidDriver(new URL(SELENIUM_HUB_URL_ANDROID));
        //testNewPetFirstVisit(driver, TARGET_SERVER_URL_ANDROID);
    }

    public void testNewPetFirstVisit(final WebDriver driver, final String baseUrl) {

        try {
            // wait for the application to get fully loaded
            WebElement findOwnerLink = (new WebDriverWait(driver, 5)).until(new ExpectedCondition<WebElement>() {
                public WebElement apply(WebDriver d) {
                    d.get(baseUrl);
                    return d.findElement(By.linkText("Find owner"));
                }
            });

            findOwnerLink.click();

            (new WebDriverWait(driver, 5)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    return d.getCurrentUrl().startsWith(baseUrl + "/owners/search");
                }
            });

            driver.findElement(By.id("lastName")).clear();
            driver.findElement(By.id("lastName")).sendKeys("S");
            driver.findElement(By.id("lastName")).sendKeys("chroeder");
            driver.findElement(By.id("findowners")).click();

            (new WebDriverWait(driver, 5)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    return d.getCurrentUrl().equals(baseUrl + "/owners/9");
                }
            });

            assertTrue(driver.findElement(By.id("main")).getText().contains("David Schroeder"));
            driver.findElement(By.linkText("Add New Pet")).click();

            (new WebDriverWait(driver, 5)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    return d.getCurrentUrl().equals(baseUrl + "/owners/9/pets/new");
                }
            });

            driver.findElement(By.id("name")).clear();
            driver.findElement(By.id("name")).sendKeys("M");
            driver.findElement(By.id("name")).sendKeys("imi");
            driver.findElement(By.id("birthDate")).clear();
            driver.findElement(By.id("birthDate")).sendKeys("2011-10-02");
            driver.findElement(By.id("addpet")).click();

            (new WebDriverWait(driver, 5)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    return d.getCurrentUrl().startsWith(baseUrl + "/owners/9")
                        && !d.getCurrentUrl().contains("pets/new");
                }
            });

            assertTrue(driver.findElement(By.id("main")).getText().contains("David Schroeder"));
            assertTrue(driver.findElement(By.id("main")).getText().contains("Mimi"));
            assertTrue(driver.findElement(By.id("main")).getText().contains("2011-10-02"));
            driver.findElement(By.linkText("Add Visit")).click();

            (new WebDriverWait(driver, 5)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    return d.getCurrentUrl().startsWith(baseUrl + "/owners/9/pets")
                        && d.getCurrentUrl().contains("visits/new");
                }
            });

            driver.findElement(By.id("date")).clear();
            driver.findElement(By.id("date")).sendKeys("2012-03-15");
            driver.findElement(By.id("description")).clear();
            driver.findElement(By.id("description")).sendKeys("rabies shot");
            driver.findElement(By.id("addvisit")).click();

            (new WebDriverWait(driver, 5)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    return d.getCurrentUrl().startsWith(baseUrl + "/owners/9")
                        && !d.getCurrentUrl().contains("visits/new");
                }
            });

            assertTrue(driver.findElement(By.id("main")).getText().contains("David Schroeder"));
            assertTrue(driver.findElement(By.id("main")).getText().contains("Mimi"));
            assertTrue(driver.findElement(By.id("main")).getText().contains("2011-10-02"));
            assertTrue(driver.findElement(By.id("main")).getText().contains("rabies shot"));
            driver.findElement(By.linkText("Edit Pet")).click();

            (new WebDriverWait(driver, 5)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    return d.getCurrentUrl().startsWith(baseUrl + "/owners/9/pets")
                        && d.getCurrentUrl().contains("edit");
                }
            });

            driver.findElement(By.id("deletepet")).click();

            (new WebDriverWait(driver, 5)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    return d.getCurrentUrl().equals(baseUrl + "/owners/9");
                }
            });

        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
