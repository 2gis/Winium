package org.openqa.selenium.winium;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;

/**
 * Class to manage options specific to {@link WiniumDriver}
 * which uses <a href="https://github.com/2gis/winphonedrive">Windows Phone Driver</a>
 */
public class SilverlightOptions implements WiniumOptions {
    private static final String APPLICATION_PATH_OPTION = "app";
    private static final String DEBUG_CONNECT_TO_RUNNING_APP_OPTION = "debugConnectToRunningApp";
    private static final String DEVICE_NAME_OPTION = "deviceName";
    private static final String INNER_PORT_OPTION = "innerPort";
    private static final String LAUNCH_DELAY_OPTION = "launchDelay";
    private static final String LAUNCH_TIMEOUT_OPTION = "launchTimeout";

    private String applicationPath;
    private Boolean debugConnectToRunningApp;
    private String deviceName;
    private Integer innerPort;
    private Integer launchDelay;
    private Integer launchTimeout;

    /**
     * Sets the absolute local path to an .xap file to be installed and launched.
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
     * Sets the inner port used to communicate between OuterDriver and InnerDrive (injected into Windows Phone app).
     * Required only if non-default port was specified in tested app in AutomationServer.Instance.InitializeAndStart call.
     * @param innerPort The inner port used to communicate between OuterDriver and InnerDrive
     */
    public void setInnerPort(Integer innerPort) {
        this.innerPort = innerPort;
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

        if (innerPort != null) {
            capabilityDictionary.put(INNER_PORT_OPTION, innerPort);
        }

        return new DesiredCapabilities(capabilityDictionary);
    }
}
