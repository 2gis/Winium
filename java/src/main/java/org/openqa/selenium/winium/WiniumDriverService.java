package org.openqa.selenium.winium;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.Beta;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.net.PortProber;
import org.openqa.selenium.remote.service.DriverService;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Exposes the service provided by the native Winium Driver executable.
 * <pre>{@code
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
 *             .usingDriverExecutable("path_to_driver_executable")
 *             .usingAnyFreePort()
 *             .withVerbose(true)
 *             .withSilent(false);
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
public class WiniumDriverService extends DriverService {

    /**
     * Boolean system property that defines whether the WiniumDriver executable should be started
     * with verbose logging.
     */
    public static final String WINIUM_DRIVER_VERBOSE_LOG = "webdriver.winium.verbose";

    /**
     * Boolean system property that defines whether the WiniumDriver executable should be started
     * without any output.
     */
    public static final String WINIUM_DRIVER_SILENT = "webdriver.winium.silent";

    /**
     * System property that defines the location of the log that will be written by service
     */
    public static final String WINIUM_DRIVER_LOG_PATH_PROPERTY = "webdriver.winium.logpath";

    /**
     * Creates a default instance of the WiniumDriverService using a default path to the Winium Desktop Driver.
     * @return A {@link WiniumDriverService} using Winium Desktop and random port
     */
    public static WiniumDriverService createDesktopService() {
        return new Builder().usingAnyFreePort().buildDesktopService();
    }

    /**
     * Creates a default instance of the WiniumDriverService using a default path to the Winium WindowsPhone Driver.
     * @return A {@link WiniumDriverService} using Winium WindowsPhone and random port
     */
    public static WiniumDriverService createSilverlightService() {
        return new Builder().usingAnyFreePort().buildSilverlightService();
    }

    /**
     * Creates a default instance of the WiniumDriverService using a default path to the Winium StoreApps Driver.
     * @return A {@link WiniumDriverService} using Winium StoreApps and random port
     */
    public static WiniumDriverService createStoreAppsService() {
        return new Builder().usingAnyFreePort().buildStoreAppsService();
    }

    protected WiniumDriverService(File executable, int port, ImmutableList<String> args,
                                  ImmutableMap<String, String> environment) throws IOException {
        super(executable, port, args, environment);
    }

    public static class Builder extends DriverService.Builder<WiniumDriverService, WiniumDriverService.Builder> {
        private static final String DESKTOP_DRIVER_SERVICE_FILENAME = "Winium.Desktop.Driver.exe";
        private static final String SILVERLIGHT_DRIVER_SERVICE_FILENAME = "WindowsPhoneDriver.OuterDriver.exe";
        private static final String STORE_APPS_DRIVER_SERVICE_FILENAME = "Winium.StoreApps.Driver.exe";

        private static final String DESKTOP_DRIVER_EXE_PROPERTY = "webdriver.winium.driver.desktop";
        private static final String SILVERLIGHT_DRIVER_EXE_PROPERTY = "webdriver.winium.driver.silverlight";
        private static final String STORE_APPS_DRIVER_EXE_PROPERTY = "webdriver.winium.driver.storeaps";

        private static final String DESKTOP_DRIVER_DOCS_URL = "https://github.com/2gis/Winium.Desktop";
        private static final String SILVERLIGHT_DRIVER_DOCS_URL = "https://github.com/2gis/winphonedriver";
        private static final String STORE_APPS_DRIVER_DOCS_URL = "https://github.com/2gis/Winium.StoreApps";

        private static final String DESKTOP_DRIVER_DOWNLOAD_URL = "https://github.com/2gis/Winium.Desktop/releases";
        private static final String SILVERLIGHT_DRIVER_DOWNLOAD_URL = "https://github.com/2gis/winphonedriver/releases";
        private static final String STORE_APPS_DRIVER_DOWNLOAD_URL = "https://github.com/2gis/Winium.StoreApps/releases";

        private File exe = null;
        private boolean verbose = Boolean.getBoolean(WINIUM_DRIVER_VERBOSE_LOG);
        private boolean silent = Boolean.getBoolean(WINIUM_DRIVER_SILENT);

        /**
         * Sets which driver executable the builder will use.
         *
         * @param file The executable to use.
         * @return A self reference.
         */
        @Override
        public Builder usingDriverExecutable(File file) {
            checkNotNull(file);
            checkExecutable(file);
            this.exe = file;
            return this;
        }

        /**
         * Configures the driver server verbosity.
         *
         * @param verbose true for verbose output, false otherwise.
         * @return A self reference.
         */
        public Builder withVerbose(boolean verbose) {
            this.verbose = verbose;
            return this;
        }

        /**
         * Configures the driver server for silent output.
         *
         * @param silent true for silent output, false otherwise.
         * @return A self reference.
         */
        public Builder withSilent(boolean silent) {
            this.silent = silent;
            return this;
        }

        /**
         * Creates a new {@link WiniumDriverService} to manage the Winium Desktop Driver server.
         * Before creating a new service, the builder will find a port for the server to listen to.
         *
         * @return The new {@link WiniumDriverService} object.
         */
        public WiniumDriverService buildDesktopService() {
            int port = getPort();
            if (port == 0) {
                port = PortProber.findFreePort();
            }

            if (exe == null) {
                exe = findDesktopDriverExecutable();
            }

            try {
                return new WiniumDriverService(exe, port, createArgs(), ImmutableMap.<String, String>of());
            } catch (IOException e) {
                throw new WebDriverException(e);
            }
        }

        /**
         * Creates a new {@link WiniumDriverService} to manage the Winium WindowsPhone Driver server.
         * Before creating a new service, the builder will find a port for the server to listen to.
         *
         * @return The new {@link WiniumDriverService} object.
         */
        public WiniumDriverService buildSilverlightService() {
            int port = getPort();
            if (port == 0) {
                port = PortProber.findFreePort();
            }

            if (exe == null) {
                exe = findSilverlightDriverExecutable();
            }

            try {
                return new WiniumDriverService(exe, port, createArgs(), ImmutableMap.<String, String>of());
            } catch (IOException e) {
                throw new WebDriverException(e);
            }
        }

        /**
         * Creates a new {@link WiniumDriverService} to manage the Winium StoreApps Driver server.
         * Before creating a new service, the builder will find a port for the server to listen to.
         *
         * @return The new {@link WiniumDriverService} object.
         */
        public WiniumDriverService buildStoreAppsService() {
            int port = getPort();
            if (port == 0) {
                port = PortProber.findFreePort();
            }

            if (exe == null) {
                exe = findStoreAppsDriverExecutable();
            }

            try {
                return new WiniumDriverService(exe, port, createArgs(), ImmutableMap.<String, String>of());
            } catch (IOException e) {
                throw new WebDriverException(e);
            }
        }

        @Override
        protected File findDefaultExecutable() {
            return findDesktopDriverExecutable();
        }

        @Override
        protected ImmutableList<String> createArgs() {
            if (getLogFile() == null) {
                String logFilePath = System.getProperty(WINIUM_DRIVER_LOG_PATH_PROPERTY);
                if (logFilePath != null) {
                    withLogFile(new File(logFilePath));
                }
            }

            ImmutableList.Builder<String> argsBuidler = new ImmutableList.Builder<String>();
            if (silent) {
                argsBuidler.add("--silent");
            }
            if (verbose) {
                argsBuidler.add("--verbose");
            }
            if (getLogFile() != null) {
                argsBuidler.add(String.format("--log-path=%s", getLogFile().getAbsolutePath()));
            }

            return argsBuidler.build();
        }

        @Override
        protected WiniumDriverService createDriverService(File exe, int port, ImmutableList<String> args,
                                                          ImmutableMap<String, String> environment) {
            try {
                return new WiniumDriverService(exe, port, args, environment);
            } catch (IOException e) {
                throw new WebDriverException(e);
            }
        }

        private File findDesktopDriverExecutable() {
            return findExecutable(DESKTOP_DRIVER_SERVICE_FILENAME, DESKTOP_DRIVER_EXE_PROPERTY,
                    DESKTOP_DRIVER_DOCS_URL, DESKTOP_DRIVER_DOWNLOAD_URL);
        }

        private File findSilverlightDriverExecutable() {
            return findExecutable(SILVERLIGHT_DRIVER_SERVICE_FILENAME, SILVERLIGHT_DRIVER_EXE_PROPERTY,
                    SILVERLIGHT_DRIVER_DOCS_URL, SILVERLIGHT_DRIVER_DOWNLOAD_URL);
        }

        private File findStoreAppsDriverExecutable() {
            return findExecutable(STORE_APPS_DRIVER_SERVICE_FILENAME, STORE_APPS_DRIVER_EXE_PROPERTY,
                    STORE_APPS_DRIVER_DOCS_URL, STORE_APPS_DRIVER_DOWNLOAD_URL);
        }
    }
}
