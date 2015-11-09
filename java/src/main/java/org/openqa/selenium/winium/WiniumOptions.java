package org.openqa.selenium.winium;

import org.openqa.selenium.Capabilities;

/**
 * Defines the interface to manage options specific to {@link WiniumDriver}
 */
public interface WiniumOptions {
    /**
     * Convert options to DesiredCapabilities for one of Winium Drivers
     * @return The DesiredCapabilities for Winium Driver with these options.
     */
    Capabilities toCapabilities();
}
