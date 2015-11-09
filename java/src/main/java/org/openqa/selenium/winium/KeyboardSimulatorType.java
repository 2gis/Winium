package org.openqa.selenium.winium;

/**
 * The keyboard simulator type.
 */
public enum KeyboardSimulatorType {
    /**
     * Based on SendKeys Class.
     * <a href="https://msdn.microsoft.com/en-us/library/system.windows.forms.sendkeys(v=vs.110).aspx">See more</a>
     */
    BasedOnWindowsFormsSendKeysClass,

    /**
     * Based on Windows Input Simulator.
     * For additional methods should be cast to the KeyboardSimulatorExt.
     * <a href="http://inputsimulator.codeplex.com/">See more</a>
     */
    BasedOnInputSimulatorLib
}
