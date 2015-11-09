package org.openqa.selenium.winium;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to manage options specific to {@link WiniumDriver}
 * wich uses <a href="https://github.com/2gis/Winium.StoreApps">Winium.StoreApps</a>
 */
public class StoreAppsOptions implements WiniumOptions {
    private static final String APPLICATION_PATH_OPTION = "app";
    private static final String DEBUG_CONNECT_TO_RUNNING_APP_OPTION = "debugConnectToRunningApp";
    private static final String DEPENDENCIES_OPTION = "dependencies";
    private static final String DEVICE_NAME_OPTION = "deviceName";
    private static final String FILES_OPTION = "files";
    private static final String LAUNCH_DELAY_OPTION = "launchDelay";
    private static final String LAUNCH_TIMEOUT_OPTION = "launchTimeout";

    private String applicationPath;
    private Boolean debugConnectToRunningApp;
    private List<String> dependencies;
    private String deviceName;
    private Map<String, String> files;
    private Integer launchDelay;
    private Integer launchTimeout;

    /**
     * Sets the absolute local path to an .appx file to be installed and launched.
     * This capability is not required if debugConnectToRunningApp is specified.
     * @param applicationPath Absolute local path to an .xap file to be installed and launched.
     */
    public void setApplicationPath(String applicationPath) {
        this.applicationPath = applicationPath;
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
     * Sets a list of dependencies.
     * @param dependencies List of dependencies.
     */
    public void setDependencies(List<String> dependencies) {
        this.dependencies = dependencies;
    }

    /**
     * Sets name of emulator to use for running test.
     * Note that this capability is not required, if no device name is specified,
     * then a default emulator is used. You can specify only partial name,
     * first emulator that starts with specified deviceName will be selected.
     * @param deviceName Name of emulator to use for running test.
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * Sets the files.
     * Each key of the map is "local file path", each corresponding value is "remote file path"
     * @param files files map
     */
    public void setFiles(Map<String, String> files) {
        this.files = files;
    }

    /**
     * Sets launch delay in milliseconds, to be waited to let visuals to initialize
     * after application launched (after successful ping or timeout).
     * Use it if the system running emulator is slow.
     * @param launchDelay Launch delay in milliseconds
     */
    public void setLaunchDelay(Integer launchDelay) {
        this.launchDelay = launchDelay;
    }

    /**
     * Sets maximum timeout in milliseconds, to be waited for application to launch
     * @param launchTimeout Maximum timeout in milliseconds, to be waited for application to launch
     */
    public void setLaunchTimeout(Integer launchTimeout) {
        this.launchTimeout = launchTimeout;
    }

    @Override
    public Capabilities toCapabilities() {
        HashMap<String, Object> capabilityDictionary = new HashMap<String, Object>();
        capabilityDictionary.put(APPLICATION_PATH_OPTION, applicationPath);

        if (files != null && files.size() > 0) {
            capabilityDictionary.put(FILES_OPTION, files);
        }

        if (debugConnectToRunningApp != null) {
            capabilityDictionary.put(DEBUG_CONNECT_TO_RUNNING_APP_OPTION, debugConnectToRunningApp);
        }

        if ((deviceName != null) && (deviceName.length() > 0)) {
            capabilityDictionary.put(DEVICE_NAME_OPTION, deviceName);
        }

        if (launchTimeout != null) {
            capabilityDictionary.put(LAUNCH_TIMEOUT_OPTION, launchTimeout);
        }

        if (launchDelay != null) {
            capabilityDictionary.put(LAUNCH_DELAY_OPTION, launchDelay);
        }

        if (dependencies != null && dependencies.size() > 0) {
            capabilityDictionary.put(DEPENDENCIES_OPTION, dependencies);
        }

        return new DesiredCapabilities(capabilityDictionary);
    }
}
