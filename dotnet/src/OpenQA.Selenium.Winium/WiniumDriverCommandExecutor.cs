namespace OpenQA.Selenium.Winium
{
    #region using

    using System;
    using System.Reflection;

    using OpenQA.Selenium.Remote;

    #endregion

    public class WiniumDriverCommandExecutor : ICommandExecutor
    {
        #region Fields

        private ICommandExecutor internalExecutor;

        private WiniumDriverService service;

        #endregion

        #region Constructors and Destructors

        public WiniumDriverCommandExecutor(WiniumDriverService driverService, TimeSpan commandTimeout)
        {
            this.service = driverService;
            this.internalExecutor = GetHttpCommandExecutor(driverService.ServiceUrl, commandTimeout);
        }

        #endregion

        #region Public Properties

        public CommandInfoRepository CommandInfoRepository
        {
            get
            {
                return this.internalExecutor.CommandInfoRepository;
            }
        }

        #endregion

        #region Public Methods and Operators

        public Response Execute(Command commandToExecute)
        {
            if (commandToExecute == null)
            {
                throw new ArgumentNullException("commandToExecute", "Command may not be null");
            }

            if (commandToExecute.Name == DriverCommand.NewSession)
            {
                this.service.Start();
            }

            try
            {
                return this.internalExecutor.Execute(commandToExecute);
            }
            finally
            {
                if (commandToExecute.Name == DriverCommand.Quit)
                {
                    this.service.Dispose();
                }
            }
        }

        #endregion

        #region Methods

        private static ICommandExecutor GetHttpCommandExecutor(Uri remoteAddress, TimeSpan commandTimeout)
        {
            var seleniumAssembly = Assembly.Load("WebDriver");
            var commandType = seleniumAssembly.GetType("OpenQA.Selenium.Remote.HttpCommandExecutor");
            ICommandExecutor commandExecutor = null;

            if (null != commandType)
            {
                commandExecutor =
                    Activator.CreateInstance(commandType, new object[] { remoteAddress, commandTimeout }) as ICommandExecutor;
            }

            return commandExecutor;
        }

        #endregion
    }
}
