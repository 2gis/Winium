namespace OpenQA.Selenium.Winium
{
    #region using

    using System.Collections.Generic;

    using OpenQA.Selenium;
    using OpenQA.Selenium.Remote;

    #endregion

    /// <summary>
    /// Class to manage options specific to <see cref="WiniumDriver"/> 
    /// wich uses <see href="https://github.com/2gis/winphonedrive">Windows Phone Driver</see>.
    /// </summary>
    public class SilverlightOptions : IWiniumOptions
    {
        #region Constants

        private const string ApplicationPathOption = "app";

        private const string DebugConnectToRunningAppOption = "debugConnectToRunningApp";

        private const string DeviceNameOption = "deviceName";

        private const string InnerPortOption = "innerPort";

        private const string LaunchDelayOption = "launchDelay";

        private const string LaunchTimeoutOption = "launchTimeout";

        #endregion

        #region Fields

        private string applicationPath;

        private bool? debugConnectToRunningApp;

        private string deviceName;

        private int? innerPort;

        private int? launchDelay;

        private int? launchTimeout;

        #endregion

        #region Public Properties

        /// <summary>
        /// Gets or sets the absolute local path to an .xap file to be installed and launched.
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
        /// Gets or sets name of emulator to use for running test. 
        /// Note that this capability is not required, if no device name is specified, 
        /// then a default emulator is used.You can specify only partial name, 
        /// first emulator that starts with specified deviceName will be selected. 
        /// </summary>
        public string DeviceName
        {
            set
            {
                this.deviceName = value;
            }
        }

        /// <summary>
        /// Gets or sets the inner port used to communicate between OuterDriver and InnerDrive (injected into Windows Phone app). 
        /// Required only if non-default port was specified in tested app in AutomationServer.Instance.InitializeAndStart call.
        /// </summary>
        public int InnerPort
        {
            set
            {
                this.innerPort = value;
            }
        }

        /// <summary>
        /// Gets or sets launch delay in milliseconds, to be waited to let visuals to initialize after application launched (after successful ping or timeout). 
        /// Use it if the system running emulator is slow.
        /// </summary>
        public int LaunchDelay
        {
            set
            {
                this.launchDelay = value;
            }
        }

        /// <summary>
        /// Gets or sets maximum timeout in milliseconds, to be waited for application to launch.
        /// </summary>
        public int LaunchTimeout
        {
            set
            {
                this.launchTimeout = value;
            }
        }

        #endregion

        #region Public Methods and Operators

        /// <summary>
        /// Convert options to DesiredCapabilities for Windows Phone Driver 
        /// </summary>
        /// <returns>The DesiredCapabilities for Windows Phone Driver with these options.</returns>
        public ICapabilities ToCapabilities()
        {
            var capabilityDictionary = new Dictionary<string, object>
                                           {
                                               { ApplicationPathOption, this.applicationPath }
                                           };

            if (this.debugConnectToRunningApp.HasValue)
            {
                capabilityDictionary.Add(DebugConnectToRunningAppOption, this.debugConnectToRunningApp);
            }

            if (!string.IsNullOrEmpty(this.deviceName))
            {
                capabilityDictionary.Add(DeviceNameOption, this.deviceName);
            }

            if (this.launchTimeout.HasValue)
            {
                capabilityDictionary.Add(LaunchTimeoutOption, this.launchTimeout);
            }

            if (this.launchDelay.HasValue)
            {
                capabilityDictionary.Add(LaunchDelayOption, this.launchDelay);
            }

            if (!string.IsNullOrEmpty(this.deviceName))
            {
                capabilityDictionary.Add(DeviceNameOption, this.deviceName);
            }

            if (this.innerPort.HasValue)
            {
                capabilityDictionary.Add(InnerPortOption, this.innerPort);
            }

            return new DesiredCapabilities(capabilityDictionary);
        }

        #endregion
    }
}
