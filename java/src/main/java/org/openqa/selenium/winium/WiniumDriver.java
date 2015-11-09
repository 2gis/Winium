package org.openqa.selenium.winium;

import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

/**
 * Provides a mechanism to write tests using Winium driver.
 * For example: <pre>{@code
 * import static org.junit.Assert.assertEquals;
 *
 * import org.junit.*;
 * import org.junit.runner.RunWith;
 * import org.junit.runners.JUnit4;
 * import org.openqa.selenium.winium.WiniumDriver;
 * import org.openqa.selenium.winium.WiniumDriverService;
 * import org.openqa.selenium.winium.DesktopOptions;
 *
 * {@literal @RunWith(JUnit4.class)}
 * public class WiniumTest extends TestCase {
 *     private static WiniumDriverService service;
 *     private WebDriver driver;
 *
 *     {@literal @BeforeClass}
 *     public static void createAndStartService() {
 *         service = new WiniumDriverService.Builder()
 *             .usingAnyFreePort()
 *             .buildDesktopService();
 *         service.start();
 *     }
 *
 *     {@literal @AfterClass}
 *     public static void createAndStopService() {
 *         service.stop();
 *     }
 *
 *     {@literal @Before}
 *     public void createDriver() {
 *         DesktopOptions options = DesktopOptions();
 *         options.setApplicationPath("C:\\Windows\\System32\\notepad.exe");
 *
 *         driver = new WiniumDriver(service, options);
 *     }
 *
 *     {@literal @After}
 *     public void quitDriver() {
 *         driver.quit();
 *     }
 *
 *     {@literal @Test}
 *     public void testSomething() {
 *         // Rest of test
 *     }
 * }
 * }</pre>
 */
public class WiniumDriver extends RemoteWebDriver {

    /**
     * Initializes a new instance of the {@link WiniumDriver} class using the specified options
     * @param options Thre {@link WiniumOptions} to be used with the Winium driver.
     */
    public WiniumDriver(WiniumOptions options) {
        this(createDefaultService(options.getClass()), options);
    }

    /**
     * Initializes a new instance of the {@link WiniumDriver} class using the specified {@link WiniumDriverService}
     * and options.
     *
     * @param service The {@link WiniumDriverService} to use.
     * @param options The {@link WiniumOptions} used to initialize the driver.
     */
    public WiniumDriver(WiniumDriverService service, WiniumOptions options) {
        super(new WiniumDriverCommandExecutor(service), options.toCapabilities());
    }

    /**
     * Initializes a new instance of the {@link WiniumDriver} lass using the specified remote address and options.
     * @param remoteAddress URL containing the address of the WiniumDriver remote server (e.g. http://127.0.0.1:4444/wd/hub).
     * @param options The {@link WiniumOptions} object to be used with the Winium driver.
     */
    public WiniumDriver(URL remoteAddress, WiniumOptions options) {
        super(new WiniumDriverCommandExecutor(remoteAddress), options.toCapabilities());
    }

    private static WiniumDriverService createDefaultService(Class<? extends WiniumOptions> optionsType) {
        if (optionsType == DesktopOptions.class) {
            return WiniumDriverService.createDesktopService();
        } else if (optionsType == StoreAppsOptions.class) {
            return WiniumDriverService.createStoreAppsService();
        } else if (optionsType == SilverlightOptions.class) {
            return WiniumDriverService.createSilverlightService();
        }

        throw new IllegalArgumentException(
                "Option type must be type of DesktopOptions, StoreAppsOptions or SilverlightOptions");
    }
}
