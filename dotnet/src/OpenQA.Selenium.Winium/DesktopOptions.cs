namespace OpenQA.Selenium.Winium
{
    #region using

    using System.Collections.Generic;

    using OpenQA.Selenium;
    using OpenQA.Selenium.Remote;

    #endregion

    /// <summary>
    /// The keyboard simulator type.
    /// </summary>
    public enum KeyboardSimulatorType
    {
        /// <summary>
        /// Based on SendKeys Class.<see href="https://msdn.microsoft.com/en-us/library/system.windows.forms.sendkeys(v=vs.110).aspx">See more</see>.
        /// </summary>
        BasedOnWindowsFormsSendKeysClass, 

        /// <summary>
        /// Based on Windows Input Simulator.
        /// For additional methods should be cast to the KeyboardSimulatorExt. <see href="http://inputsimulator.codeplex.com/">See more</see>.
        /// </summary>
        BasedOnInputSimulatorLib
    }

    /// <summary>
    /// Class to manage options specific to <see cref="WiniumDriver"/> 
    /// wich uses <see href="https://github.com/2gis/Winium.Desktop">Winium.Desktop</see>.
    /// </summary>
    public class DesktopOptions : IWiniumOptions
    {
        #region Constants

        private const string ApplicationPathOption = "app";

        private const string ArgumentsOption = "args";

        private const string DebugConnectToRunningAppOption = "debugConnectToRunningApp";

        private const string KeyboardSimulatorOption = "keyboardSimulator";

        private const string LaunchDelayOption = "launchDelay";

        #endregion

        #region Fields

        private string applicationPath;

        private string arguments;

        private bool? debugConnectToRunningApp;

        private KeyboardSimulatorType? keyboardSimulator;

        private int? launchDelay;

        #endregion

        #region Public Properties

        /// <summary>
        /// Gets or sets the absolute local path to an .exe file to be started. 
        /// This capability is not required if debugConnectToRunningApp is specified.
        /// </summary>
        public string ApplicationPath
        {
            set
            {
                this.applicationPath = value;
            }
        }

        /// <summary>
        /// Gets or sets startup argunments of the application under test.
        /// </summary>
        public string Arguments
        {
            set
            {
                this.arguments = value;
            }
        }

        /// <summary>
        /// Gets or sets a value indicating whether debug connect to running app.
        /// If true, then application starting step are skipped.
        /// </summary>
        public bool DebugConnectToRunningApp
        {
            set
            {
                this.debugConnectToRunningApp = value;
            }
        }

        /// <summary>
        /// Gets or sets the keyboard simulator type.
        /// </summary>
        public KeyboardSimulatorType KeyboardSimulator
        {
            set
            {
                this.keyboardSimulator = value;
            }
        }

        /// <summary>
        /// Gets or sets the launch delay in milliseconds, to be waited to let visuals to initialize after application started.
        /// </summary>
        public int LaunchDelay
        {
            set
            {
                this.launchDelay = value;
            }
        }

        #endregion

        #region Public Methods and Operators

        /// <summary>
        /// Convert options to DesiredCapabilities for Winium Desktop Driver 
        /// </summary>
        /// <returns>The DesiredCapabilities for Winium Desktop Driver with these options.</returns>
        public ICapabilities ToCapabilities()
        {
            var capabilityDictionary = new Dictionary<string, object>
                                           {
                                               { ApplicationPathOption, this.applicationPath }
                                           };

            if (!string.IsNullOrEmpty(this.arguments))
            {
                capabilityDictionary.Add(ArgumentsOption, this.arguments);
            }

            if (this.debugConnectToRunningApp.HasValue)
            {
                capabilityDictionary.Add(DebugConnectToRunningAppOption, this.debugConnectToRunningApp);
            }

            if (this.keyboardSimulator.HasValue)
            {
                capabilityDictionary.Add(KeyboardSimulatorOption, this.keyboardSimulator);
            }

            if (this.launchDelay.HasValue)
            {
                capabilityDictionary.Add(LaunchDelayOption, this.launchDelay);
            }

            return new DesiredCapabilities(capabilityDictionary);
        }

        #endregion
    }
}
