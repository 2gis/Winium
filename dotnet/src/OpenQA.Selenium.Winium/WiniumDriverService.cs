namespace OpenQA.Selenium.Winium
{
    #region using

    using System;
    using System.Globalization;
    using System.Net;
    using System.Net.Sockets;
    using System.Text;

    #endregion

    /// <summary>
    /// Exposes the service provided by the native Winium Driver executable.
    /// </summary>
    /// <example>
    /// <code>
    /// [TestFixture]
    /// public class Testing
    /// {
    ///     private IWebDriver driver;
    ///     <para></para>
    ///     [SetUp]
    ///     public void SetUp()
    ///     {
    ///         var service =
    ///                WiniumDriverService.CreateDesktopService("path_to_driver_executible_folder");
    ///
    ///        service.SuppressInitialDiagnosticInformation = false;
    ///        service.EnableVerboseLogging = true;
    ///
    ///         var options = new DesktopOptions { ApplicationPath = @"‪C:\Windows\System32\notepad.exe" };
    ///         driver = new WiniumDriver(service, options, TimeSpan.FromMinutes(30));
    ///     }
    ///     <para></para>
    ///     [Test]
    ///     public void TestGoogle()
    ///     {
    ///        /*
    ///         *   Rest of the test
    ///         */
    ///     }
    ///     <para></para>
    ///     [TearDown]
    ///     public void TearDown()
    ///     {
    ///         driver.Quit();
    ///     } 
    /// }
    /// </code>
    /// </example>
    public class WiniumDriverService : DriverService
    {
        #region Constants

        private const string DesktopDriverServiceFileName = "Winium.Desktop.Driver.exe";

        private const string SilverlightDriverServiceFileName = "WindowsPhoneDriver.OuterDriver.exe";

        private const string StoreAppsDriverServiceFileName = "Winium.StoreApps.Driver.exe";

        #endregion

        #region Static Fields

        private static readonly Uri DesktopDriverDownloadUrl = new Uri(
            "https://github.com/2gis/Winium.Desktop/releases");

        private static readonly Uri SilverlightDriverDownloadUrl =
            new Uri("https://github.com/2gis/winphonedriver/releases");

        private static readonly Uri StoreAppsDriverDownloadUrl =
            new Uri("https://github.com/2gis/Winium.StoreApps/releases");

        private static readonly Uri WiniumDownloUrl = new Uri("https://github.com/2gis/Winium");

        #endregion

        #region Fields

        private bool enableVerboseLogging;

        private string logPath = string.Empty;

        #endregion

        #region Constructors and Destructors

        private WiniumDriverService(string executablePath, string executableFileName, int port, Uri downloadUrl)
            : base(executablePath, port, executableFileName, downloadUrl)
        {
        }

        #endregion

        #region Public Properties

        /// <summary>
        /// Gets or sets a value indicating whether to enable verbose logging for the Winium Driver executable.
        /// Defaults to <see langword="false"/>.
        /// </summary>
        public bool EnableVerboseLogging
        {
            get
            {
                return this.enableVerboseLogging;
            }

            set
            {
                this.enableVerboseLogging = value;
            }
        }

        /// <summary>
        /// Gets or sets the location of the log file written to by the Winium Driver executable.
        /// </summary>
        public string LogPath
        {
            get
            {
                return this.logPath;
            }

            set
            {
                this.logPath = value;
            }
        }

        #endregion

        #region Properties

        /// <summary>
        /// Gets the command-line arguments for the driver service.
        /// </summary>
        protected override string CommandLineArguments
        {
            get
            {
                StringBuilder argsBuilder = new StringBuilder(base.CommandLineArguments);

                if (this.SuppressInitialDiagnosticInformation)
                {
                    argsBuilder.Append(" --silent");
                }

                if (this.enableVerboseLogging)
                {
                    argsBuilder.Append(" --verbose");
                }

                if (!string.IsNullOrEmpty(this.logPath))
                {
                    argsBuilder.AppendFormat(CultureInfo.InvariantCulture, " --log-path={0}", this.logPath);
                }

                return argsBuilder.ToString();
            }
        }

        #endregion

        #region Public Methods and Operators

        /// <summary>
        /// Creates a default instance of the WiniumDriverService using a specified path to the Winium Driver executable with the given name.
        /// </summary>
        /// <param name="driverPath">The directory containing the Winium Driver executable.</param>
        /// <param name="driverExecutableFileName">The name of the Winium Driver executable file.</param>
        /// <returns>A <see cref="WiniumDriverService"/> using a random port.</returns>
        public static WiniumDriverService CreateDefaultService(string driverPath, string driverExecutableFileName)
        {
            return new WiniumDriverService(
                driverPath,
                driverExecutableFileName,
                FindFreePort(),
                WiniumDownloUrl);
        }

        /// <summary>
        /// Creates a default instance of the WiniumDriverService using a specified path to the Winium Desktop Driver.
        /// </summary>
        /// <param name="driverPath">The directory containing the Winium Desktop Driver executable.</param>
        /// <returns>A <see cref="WiniumDriverService"/> using Winium Deaktop and random port.</returns>
        public static WiniumDriverService CreateDesktopService(string driverPath)
        {
            return new WiniumDriverService(
                driverPath,
                DesktopDriverServiceFileName,
                FindFreePort(),
                DesktopDriverDownloadUrl);
        }

        /// <summary>
        /// Creates a default instance of the WiniumDriverService using a specified path to the WindowsPhone Driver.
        /// </summary>
        /// <param name="driverPath">The directory containing the WindowsPhone Driver executable.</param>
        /// <returns>A <see cref="WiniumDriverService"/> using WindowsPhone Driver and random port.</returns>
        public static WiniumDriverService CreateSilverlightService(string driverPath)
        {
            return new WiniumDriverService(
                driverPath,
                SilverlightDriverServiceFileName,
                FindFreePort(),
                SilverlightDriverDownloadUrl);
        }

        /// <summary>
        /// Creates a default instance of the WiniumDriverService using a specified path to the Winium AtoreApps Driver.
        /// </summary>
        /// <param name="driverPath">The directory containing the Winium StoreApps Driver executable.</param>
        /// <returns>A <see cref="WiniumDriverService"/> using Winium StoreApps and random port.</returns>
        public static WiniumDriverService CreateStoreAppsService(string driverPath)
        {
            return new WiniumDriverService(
                driverPath,
                StoreAppsDriverServiceFileName,
                FindFreePort(),
                StoreAppsDriverDownloadUrl);
        }

        #endregion

        private static int FindFreePort()
        {
            var socket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
            try
            {
                socket.Bind(new IPEndPoint(IPAddress.Any, 0));
                return ((IPEndPoint)socket.LocalEndPoint).Port;
            }
            finally
            {
                socket.Close();
            }
        }
    }
}
