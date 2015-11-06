namespace OpenQA.Selenium.Winium
{
    #region using

    using System;

    using OpenQA.Selenium.Remote;

    #endregion

    /// <summary>
    ///  Provides a mechanism to write tests using Winium driver.
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
    ///         var options = new DesktopOptions { ApplicationPath = @"‪C:\Windows\System32\notepad.exe" };
    ///         driver = new WiniumDriver(options);
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
    public class WiniumDriver : RemoteWebDriver
    {
        #region Constructors and Destructors

        /// <summary>
        /// Initializes a new instance of the <see cref="WiniumDriver"/> class using the specified path
        /// to the directory containing Winium.Driver executible file and options.
        /// </summary>
        /// <param name="winiumDriverDirectory">
        /// The full path to the directory containing Winium.Driver executible.
        /// </param>
        /// <param name="options">
        /// The <see cref="DesktopOptions"/> to be used with the Winium driver.
        /// </param>
        public WiniumDriver(string winiumDriverDirectory, IWiniumOptions options)
            : this(winiumDriverDirectory, options, RemoteWebDriver.DefaultCommandTimeout)
        {
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="WiniumDriver"/> class using the specified path
        /// to the directory containing Winium.Driver executible file, options, and command timeout.
        /// </summary>
        /// <param name="winiumDriverDirectory">
        /// The full path to the directory containing Winium.Driver executible file.
        /// </param>
        /// <param name="options">
        /// The <see cref="DesktopOptions"/> to be used with the Winium driver.
        /// </param>
        /// <param name="commandTimeout">
        /// The maximum amount of time to wait for each command.
        /// </param>
        public WiniumDriver(string winiumDriverDirectory, IWiniumOptions options, TimeSpan commandTimeout)
            : this(CreateDefaultService(options.GetType(), winiumDriverDirectory), options, commandTimeout)
        {
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="WiniumDriver"/> class using the specified 
        /// <see cref="WiniumDriverService"/> and options.
        /// </summary>
        /// <param name="service">The <see cref="WiniumDriverService"/> to use.</param>
        /// <param name="options">The <see cref="DesktopOptions"/> used to initialize the driver.</param>
        public WiniumDriver(WiniumDriverService service, IWiniumOptions options)
            : this(service, options, RemoteWebDriver.DefaultCommandTimeout)
        {
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="WiniumDriver"/> class using the specified <see cref="WiniumDriverService"/>.
        /// </summary>
        /// <param name="service">The <see cref="WiniumDriverService"/> to use.</param>
        /// <param name="options">The <see cref="IWiniumOptions"/> object to be used with the Winium driver.</param>
        /// <param name="commandTimeout">The maximum amount of time to wait for each command.</param>
        public WiniumDriver(WiniumDriverService service, IWiniumOptions options, TimeSpan commandTimeout)
            : base(new WiniumDriverCommandExecutor(service, commandTimeout), options.ToCapabilities())
        {
            this.InitWiniumDriverCommands();
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="WiniumDriver"/> class using the specified remote address and options.
        /// </summary>
        /// <param name="remoteAddress">URI containing the address of the WiniumDriver remote server (e.g. http://127.0.0.1:4444/wd/hub).</param>
        /// <param name="options">The <see cref="IWiniumOptions"/> object to be used with the Winium driver.</param>
        public WiniumDriver(Uri remoteAddress, IWiniumOptions options)
            : this(remoteAddress, options, RemoteWebDriver.DefaultCommandTimeout)
        {
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="WiniumDriver"/> class using the specified remote address, desired capabilities, and command timeout.
        /// </summary>
        /// <param name="remoteAddress">URI containing the address of the WiniumDriver remote server (e.g. http://127.0.0.1:4444/wd/hub).</param>
        /// <param name="options">The <see cref="IWiniumOptions"/> object to be used with the Winium driver.</param>
        /// <param name="commandTimeout">The maximum amount of time to wait for each command.</param>
        public WiniumDriver(Uri remoteAddress, IWiniumOptions options, TimeSpan commandTimeout)
            : base(CommandExecutorFactory.GetHttpCommandExecutor(remoteAddress, commandTimeout), options.ToCapabilities())
        {
            this.InitWiniumDriverCommands();
        }

        #endregion

        #region Methods

        private static WiniumDriverService CreateDefaultService(Type optionsType, string directory)
        {
            if (optionsType == typeof(DesktopOptions))
            {
                return WiniumDriverService.CreateDesktopService(directory);
            }

            if (optionsType == typeof(StoreAppsOptions))
            {
                return WiniumDriverService.CreateStoreAppsService(directory);
            }

            if (optionsType == typeof(SilverlightOptions))
            {
                return WiniumDriverService.CreateSilverlightService(directory);
            }

            throw new ArgumentException(
                "Option type must be type of DesktopOptions, StoreAppsOptions or SilverlightOptions", 
                "optionsType");
        }

        private void InitWiniumDriverCommands()
        {
            this.CommandExecutor.CommandInfoRepository.TryAddCommand(
                "findDataGridCell", 
                new CommandInfo("POST", "/session/{sessionId}/element/{id}/datagrid/cell/{row}/{column}"));

            this.CommandExecutor.CommandInfoRepository.TryAddCommand(
                "getDataGridColumnCount", 
                new CommandInfo("POST", "/session/{sessionId}/element/{id}/datagrid/column/count"));

            this.CommandExecutor.CommandInfoRepository.TryAddCommand(
                "getDataGridRowCount", 
                new CommandInfo("POST", "/session/{sessionId}/element/{id}/datagrid/row/count"));

            this.CommandExecutor.CommandInfoRepository.TryAddCommand(
                "scrollToDataGridCell", 
                new CommandInfo("POST", "/session/{sessionId}/element/{id}/datagrid/scroll/{row}/{column}"));

            this.CommandExecutor.CommandInfoRepository.TryAddCommand(
                "selectDataGridCell", 
                new CommandInfo("POST", "/session/{sessionId}/element/{id}/datagrid/select/{row}/{column}"));

            this.CommandExecutor.CommandInfoRepository.TryAddCommand(
                "scrollToListBoxItem", 
                new CommandInfo("POST", "/session/{sessionId}/element/{id}/listbox/scroll"));

            this.CommandExecutor.CommandInfoRepository.TryAddCommand(
                "findMenuItem", 
                new CommandInfo("POST", "/session/{sessionId}/element/{id}/menu/item/{path}"));

            this.CommandExecutor.CommandInfoRepository.TryAddCommand(
                "selectMenuItem", 
                new CommandInfo("POST", "/session/{sessionId}/element/{id}/menu/select/{path}"));

            this.CommandExecutor.CommandInfoRepository.TryAddCommand(
                "isComboBoxExpanded", 
                new CommandInfo("POST", "/session/{sessionId}/element/{id}/combobox/expanded"));

            this.CommandExecutor.CommandInfoRepository.TryAddCommand(
                "expandComboBox", 
                new CommandInfo("POST", "/session/{sessionId}/element/{id}/combobox/expand"));

            this.CommandExecutor.CommandInfoRepository.TryAddCommand(
                "collapseComboBox", 
                new CommandInfo("POST", "/session/{sessionId}/element/{id}/combobox/collapse"));

            this.CommandExecutor.CommandInfoRepository.TryAddCommand(
                "findComboBoxSelectedItem", 
                new CommandInfo("POST", "/session/{sessionId}/element/{id}/combobox/items/selected"));

            this.CommandExecutor.CommandInfoRepository.TryAddCommand(
                "scrollToComboBoxItem", 
                new CommandInfo("POST", "/session/{sessionId}/element/{id}/combobox/scroll"));
        }

        #endregion
    }
}
