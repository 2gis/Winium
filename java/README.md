# Winium.WebDriver for Java

[![GitHub license](https://img.shields.io/badge/license-MPL 2.0-blue.svg?style=flat-square)](../LICENSE)

<p align="center">
<img src="https://raw.githubusercontent.com/2gis/Winium.StoreApps/assets/winium.png" alt="Winium.WebDriver is an extension of [WebDriver Java bindings](http://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-remote-driver).">
</p>

This is an extension of [WebDriver Java bindings](http://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-remote-driver).
Provides a mechanism to write tests using [Winium.Desktop](https://github.com/2gis/Winium.Desktop), [Winium.StoreApps](https://github.com/2gis/Winium.StioreApps) or [winphonedriver](https://github.com/2gis/winphonedriver).

Winium.WebDriver retains the functionality of common driver and has specific methods for interaction with the Winium Driver.


## Quick Start
1. Add reference to `winium-web-driver` to your pom.xml file:

    ```xml
    <dependencies>
        <dependency>
            <groupId>com.github.2gis.winium</groupId>
            <artifactId>winium-webdriver</artifactId>
            <version>0.1.0-1</version>
        </dependency>
    </dependencies>
    ```
2. Initialize an instance of specific for Desktop, StoreApps, Silverlight Options class. 
	
	[DesktopOptions] for example:
	```java
	DesktopOptions options = DesktopOptions();
    options.setApplicationPath("C:\\Windows\\System32\\notepad.exe");
	```
3. Create the instance of the [WebDriver] class.

	Use default constructor:
	```java
	WiniumDriver driver = new WiniumDriver(options);
	```
	Use the native WiniumDriver executable:
	```java
	WiniumDriverService service = new WiniumDriverService.Builder()
                                         .usingDriverExecutable("path_to_driver_executable")
                                         .usingAnyFreePort()
                                         .withVerbose(true)
                                         .withSilent(false);
                                         .buildDesktopService();

	WiniumDriver driver = new WiniumDriver(service, options);
	```

## Contributing

Contributions are welcome!

1. Check for open issues or open a fresh issue to start a discussion around a feature idea or a bug.
2. Fork the repository to start making your changes to the master branch (or branch off of it).
3. We recommend to write a test which shows that the bug was fixed or that the feature works as expected.
4. Send a pull request and bug the maintainer until it gets merged and published. :smiley:

## Contact

Have some questions? Found a bug? Create [new issue](https://github.com/2gis/Winium/issues/new) or contact us at zebraxxl@mail.ru

## License

Winium is released under the MPL 2.0 license. See [LICENSE](../LICENSE) for details.
