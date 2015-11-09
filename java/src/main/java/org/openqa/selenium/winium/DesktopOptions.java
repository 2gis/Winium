package org.openqa.selenium.winium;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;

/**
 * Class to manage options specific to {@link WiniumDriver}
 * which uses <a href="https://github.com/2gis/Winium.Desktop">Winium.Desktop</a>
 */
public class DesktopOptions implements WiniumOptions {
    private static final String APPLICATION_PATH_OPTION = "app";
    private static final String ARGUMENTS_OPTION = "args";
    private static final String DEBUG_CONNECT_TO_RUNNING_APP_OPTION = "debugConnectToRunningApp";
    private static final String KEYBOARD_SIMULATOR_OPTION = "keyboardSimulator";
    private static final String LAUNCH_DELAY_OPTION = "launchDelay";

    private String applicationPath;
    private String arguments;
    private Boolean debugConnectToRunningApp;
    private KeyboardSimulatorType keyboardSimulator;
    private Integer launchDelay;

    /**
     * Sets the absolute local path to an .exe file to be started.
     * This capability is not required if debugConnectToRunningApp is specified.
     * @param applicationPath Absolute local path to an .exe file to be started.
     */
    public void setApplicationPath(String applicationPath) {
        this.applicationPath = applicationPath;
    }

    /**
     * Sets startup argunments of the application under test.
     * @param arguments Startup argunments of the application under test.
     */
    public void setArguments(String arguments) {
        this.arguments = arguments;
    }

    /**
     * Sets a value indicating whether debug connect to running app.
     * If true, then application starting step are skipped.
     * @param debugConnectToRunningApp Value indicating whether debug connect to running app.
     */
    public void setDebugConnectToRunningApp(Boolean debugConnectToRunningApp) {
        this.debugConnectToRunningApp = debugConnectToRunningApp;
    }

    /**
     * Sets the keyboard simulator type.
     * @param keyboardSimulator Keyboard simulator type
     */
    public void setKeyboardSimulator(KeyboardSimulatorType keyboardSimulator) {
        this.keyboardSimulator = keyboardSimulator;
    }

    /**
     * Sets the launch delay in milliseconds, to be waited to let visuals to initialize after application started.
     * @param launchDelay Launch delay in milliseconds
     */
    public void setLaunchDelay(Integer launchDelay) {
        this.launchDelay = launchDelay;
    }

    /**
     * Convert options to DesiredCapabilities for Winium Desktop Driver
     * @return The DesiredCapabilities for Winium Desktop Driver with these options.
     */
    public Capabilities toCapabilities() {
        HashMap<String, Object> capabilityDictionary = new HashMap<String, Object>();
        capabilityDictionary.put(APPLICATION_PATH_OPTION, applicationPath);

        if ((arguments != null) && (arguments.length() > 0)) {
            capabilityDictionary.put(ARGUMENTS_OPTION, arguments);
        }

        if (debugConnectToRunningApp != null) {
            capabilityDictionary.put(DEBUG_CONNECT_TO_RUNNING_APP_OPTION, debugConnectToRunningApp);
        }

        if (keyboardSimulator != null) {
            capabilityDictionary.put(KEYBOARD_SIMULATOR_OPTION, keyboardSimulator);
        }

        if (launchDelay != null) {
            capabilityDictionary.put(LAUNCH_DELAY_OPTION, launchDelay);
        }

        return new DesiredCapabilities(capabilityDictionary);
    }
}
