namespace OpenQA.Selenium.Winium
{
    #region using

    using OpenQA.Selenium;

    #endregion

    /// <summary>
    /// Defines the interface to manage options specific to <see cref="WiniumDriver"/>
    /// </summary>
    public interface IWiniumOptions
    {
        #region Public Methods and Operators

        /// <summary>
        /// Convert options to DesiredCapabilities for one of Winium Drivers 
        /// </summary>
        /// <returns>The DesiredCapabilities for Winium Driver with these options.</returns>
        ICapabilities ToCapabilities();

        #endregion
    }
}
