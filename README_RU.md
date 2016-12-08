# Winium
<p align="center">
<img src="assets/winium.png" alt="Winium это реализация Selenium Remote WebDriver для автоматизации тестирования под Windows плафтормами">
</p>

Фреймворк автоматизации под Windows платформы. Бесплатно. Open-source. Selenium-based.

## Почему Winium?
Уже есть Selenium WebDriver для тестирования веб приложений, Appium для тестирования iOS и Android приложений. А теперь появился и Selenium-based инструмент для тестирования Windows приложений. Какие он дает преимущества? Цитируя Appium:
> - You can write tests with your favorite dev tools using any WebDriver-compatible language such as Java, Objective-C, JavaScript with Node.js (in promise, callback or generator flavors), PHP, Python, Ruby, C#, Clojure, or Perl with the Selenium WebDriver API and language-specific client libraries.
> - You can use any testing framework.

А по-русски можно?
- Пишите тесты, используя ваши любимые инструменты, любой WebDriver-совместимый язык программирования, например, Java, Objective-C, JavaScript with Node.js, PHP, Python, Ruby, C#, Clojure...
- Используйте любой тестовый фреймворк.

# Поддерживаемые платформы
- Windows Desktop (WPF, WinForms) Apps
- Windows Store or Universal Apps for Windows Phone
- Windows Phone Silverlight Apps

# [Winium for Desktop](https://github.com/2gis/Winium.Desktop)
[![GitHub release](https://img.shields.io/github/release/2gis/Winium.Desktop.svg?style=flat-square)](https://github.com/2gis/Winium.Desktop/releases/)
[![GitHub license](https://img.shields.io/badge/license-MPL 2.0-blue.svg?style=flat-square)](LICENSE)

Winium.Desktop это open-source инструмент для автоматизации тестирования Windows приложений построенных на WinFroms и WPF платформах

### Поддерживаемые платформы
- WinForms
- WPF


# [Winium for Mobile](https://github.com/2gis/Winium.Mobile)
[![Winium.StoreApps.InnerServer Inner Server NuGet version](https://img.shields.io/nuget/v/Winium.StoreApps.InnerServer.svg?style=flat-square&label=nuget%20storeapps)](https://www.nuget.org/packages/Winium.StoreApps.InnerServer/)
[![Winium.Silverlight.InnerServer NuGet version](https://img.shields.io/nuget/v/Winium.Silverlight.InnerServer.svg?style=flat-square&label=nuget%20silverlight)](https://www.nuget.org/packages/Winium.Silverlight.InnerServer/)
[![GitHub release](https://img.shields.io/github/release/2gis/Winium.StoreApps.svg?style=flat-square)](https://github.com/2gis/Winium.StoreApps/releases/)
[![GitHub license](https://img.shields.io/badge/license-MPL 2.0-blue.svg?style=flat-square)](LICENSE)


Winium.Mobile это open-source инструмент для автоматизации Windows Store и Silverlight мобильных приложений, тестируемых на эмуляторах (поддерживаются Windows Phone 8.1 и Windows 10 Mobile приложения).

### Поддерживаемые платформы
- Windows Phone 8.1 Store Apps (or Universal App for Windows Phone)
- Windows Phone 8.1 Silverlight apps
- Windows 10 Mobile (there are some known [issues](https://github.com/2gis/Winium.Mobile/labels/windows10))

# [Winium.Mobile CodedUI Driver](https://github.com/2gis/winium.storeapps.codedui)
Прототип Winium Mobile драйвера на основе CodedUI. Реализует Selenium Remote WebDriver implementation для автоматизации Windows Phone 8.1 и Windows 10 Mobile приложений.
