package drivers;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.network.Network;
import org.openqa.selenium.devtools.v85.network.model.ConnectionType;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class DriverFactory {
    public static WebDriver getNewInstance(String browserName, String isGrid, String os) {
        DesiredCapabilities capabilities;
        ChromeOptions chromeOptions;
        FirefoxOptions firefoxOptions;

        Map<String, Object> prefs;
        ThreadLocal<RemoteWebDriver> driver;
        if (isGrid != null) {
            driver = new ThreadLocal<>();
            capabilities = new DesiredCapabilities();
            capabilities.setCapability("browserName", browserName);
            setProjectPlatform(capabilities, os);
//            capabilities.setPlatform(Platform.LINUX);
            System.out.println("mostafaaaamahmoud " + os);
            try {
                driver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            return driver.get();
        } else {
            switch (browserName.toLowerCase()) {
                case "chrome-headless":
                    chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--headless");
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("window-size=1920,1080");
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    chromeOptions.addArguments("--remote-debugging-port=9222");
                    return new ChromeDriver(chromeOptions);
                case "firefox":
                    capabilities = new DesiredCapabilities();
                    firefoxOptions = new FirefoxOptions();
                    capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions);
                    if (os != null) {
                        setProjectPlatform(capabilities, os);
                    }
                    firefoxOptions.merge(capabilities);
                    return new FirefoxDriver(firefoxOptions);
                case "firefox-headless":
                    capabilities = new DesiredCapabilities();
                    firefoxOptions = getFirefoxOptions(capabilities, os);
                    return new FirefoxDriver(firefoxOptions);
                case "edge":
                    return new EdgeDriver();
                case "localization":
                    chromeOptions = new ChromeOptions();
                    // TODO: handle browsers options
                    prefs = new HashMap<>();
                    prefs.put("credentials_enable_service", false);
                    prefs.put("profile.password_manager_enabled", false);
                    prefs.put("profile.default_content_setting_values.notifications", 2);
                    chromeOptions.addArguments("start-maximized");
                    chromeOptions.addArguments("--incognito");
                    chromeOptions.addArguments("--disable-web-security");
                    chromeOptions.addArguments("--no-proxy-server");
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    chromeOptions.addArguments("--disable-notifications");
                    chromeOptions.addArguments("--lang=fr");
                    chromeOptions.setExperimentalOption("prefs", prefs);
                    chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                    capabilities = new DesiredCapabilities();
                    capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                    if (os != null) {
                        setProjectPlatform(capabilities, os);
                    }
                    chromeOptions.merge(capabilities);
                    return new ChromeDriver(chromeOptions);
                case "mobile-view":
                    chromeOptions = new ChromeOptions();
                    prefs = new HashMap<>();
                    prefs.put("credentials_enable_service", false);
                    prefs.put("profile.password_manager_enabled", false);
                    prefs.put("profile.default_content_setting_values.notifications", 2);
                    prefs.put("download.default_directory", System.getProperty("user.dir") + "\\sources");
                    prefs.put("download.prompt_for_download", false);  // Don't prompt for downloads
                    prefs.put("download.directory_upgrade", true);     // Allow download location upgrades
                    prefs.put("safebrowsing.enabled", true);
                    prefs.put("autofill.profile_enabled", false); // Disable autofill
                    chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                    chromeOptions.setExperimentalOption("prefs", prefs);
                    // Mobile emulation: e.g., emulate an iPhone 6
                    chromeOptions.setExperimentalOption("mobileEmulation", Map.of("deviceName", "iPhone 6"));
                    chromeOptions.addArguments("download.default_directory=" + System.getProperty("user.dir") + "\\sources");
                    chromeOptions.addArguments("--disable-extensions");// Disable safe browsing to allow all downloads
//                chromeOptions.addArguments("--incognito");
                    chromeOptions.addArguments("--disable-features=Autofill,AutofillAddressPrediction,PasswordManager");
                    chromeOptions.addArguments("--disable-save-password-bubble");  // Disable save password bubble
                    chromeOptions.addArguments("--disable-autofill-popup");  // Disable the autofill popup for address
                    chromeOptions.addArguments("start-maximized");
                    chromeOptions.addArguments("--disable-web-security");
                    chromeOptions.addArguments("--no-proxy-server");
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    chromeOptions.addArguments("--disable-notifications");
                    capabilities = new DesiredCapabilities();
                    capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                    capabilities.setCapability(CapabilityType.ENABLE_DOWNLOADS, true);
                    if (os != null) {
                        setProjectPlatform(capabilities, os);
                    }
                    chromeOptions.merge(capabilities);
                    return new ChromeDriver(chromeOptions);
                case "customized-mobile-view":
                    chromeOptions = new ChromeOptions();
                    Map<String, Object> deviceMetrics = new HashMap<>();
                    deviceMetrics.put("width", 360);
                    deviceMetrics.put("height", 640);
                    deviceMetrics.put("pixelRatio", 3.0);
                    Map<String, Object> mobileEmulation = new HashMap<>();
                    mobileEmulation.put("deviceMetrics", deviceMetrics);
                    mobileEmulation.put("userAgent", "Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/126.0.6478.127 Mobile Safari/535.19");

                    // TODO: handle browsers options
                    prefs = new HashMap<>();
                    prefs.put("credentials_enable_service", false);
                    prefs.put("profile.password_manager_enabled", false);
                    prefs.put("profile.default_content_setting_values.notifications", 2);
                    chromeOptions.addArguments("start-maximized");
                    chromeOptions.addArguments("--incognito");
                    chromeOptions.addArguments("--disable-web-security");
                    chromeOptions.addArguments("--no-proxy-server");
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    chromeOptions.addArguments("--disable-notifications");
                    chromeOptions.setExperimentalOption("prefs", prefs);
                    chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
                    chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                    capabilities = new DesiredCapabilities();
                    capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                    if (os != null) {
                        setProjectPlatform(capabilities, os);
                    }
                    chromeOptions.merge(capabilities);
                    return new ChromeDriver(chromeOptions);

                case "low-network":
                    chromeOptions = new ChromeOptions();
                    // TODO: handle browsers options
                    prefs = new HashMap<>();
                    prefs.put("credentials_enable_service", false);
                    prefs.put("profile.password_manager_enabled", false);
                    prefs.put("profile.default_content_setting_values.notifications", 2);
                    chromeOptions.addArguments("start-maximized");
                    chromeOptions.addArguments("--incognito");
                    chromeOptions.addArguments("--disable-web-security");
                    chromeOptions.addArguments("--no-proxy-server");
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    chromeOptions.addArguments("--disable-notifications");
                    chromeOptions.setExperimentalOption("prefs", prefs);
                    chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                    capabilities = new DesiredCapabilities();
                    capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                    if (os != null) {
                        setProjectPlatform(capabilities, os);
                    }
                    chromeOptions.merge(capabilities);
                    ChromeDriver lowNetworkDriver = new ChromeDriver(chromeOptions);
                    DevTools devTools = lowNetworkDriver.getDevTools();
                    devTools.createSession();

                    // Set network conditions: offline, latency, download throughput, upload throughput
                    devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
                    devTools.send(Network.emulateNetworkConditions(false, // offline
                            100,   // latency (ms)
                            50 * 1024, // download throughput (kbps)
                            20 * 1024, // upload throughput (kbps)
                            Optional.of(ConnectionType.CELLULAR4G)));

                    return lowNetworkDriver;
                default:
                    chromeOptions = new ChromeOptions();
                    // TODO: handle browsers options
                    prefs = new HashMap<>();
                    prefs.put("credentials_enable_service", false);
                    prefs.put("profile.password_manager_enabled", false);
                    prefs.put("profile.default_content_setting_values.notifications", 2);
                    chromeOptions.addArguments("start-maximized");
                    chromeOptions.addArguments("--incognito");
                    chromeOptions.addArguments("--disable-web-security");
                    chromeOptions.addArguments("--no-proxy-server");
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    chromeOptions.addArguments("--disable-notifications");
                    chromeOptions.setExperimentalOption("prefs", prefs);
                    chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                    capabilities = new DesiredCapabilities();
                    capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                    if (os != null) {
                        setProjectPlatform(capabilities, os);
                    }
                    chromeOptions.merge(capabilities);
                    return new ChromeDriver(chromeOptions);
            }
        }
    }

    private static FirefoxOptions getFirefoxOptions(DesiredCapabilities caps, String os) {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("--headless");
        firefoxOptions.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
        firefoxOptions.addArguments("--window-size=1920,1080");
        firefoxOptions.addArguments("--no-sandbox");
        firefoxOptions.addArguments("--disable-gpu");
        firefoxOptions.addArguments("--disable-dev-shm-usage");
        caps.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions);
        if (os != null) {
            setProjectPlatform(caps, os);
        }
        firefoxOptions.merge(caps);
        return firefoxOptions;
    }

    private static void setProjectPlatform(DesiredCapabilities capabilities, String os) {
        // Use switch case to determine the platform
        switch (os.toLowerCase()) {
            case "windows":
                capabilities.setPlatform(Platform.WINDOWS);
                break;
            case "mac":
                capabilities.setPlatform(Platform.MAC);
                break;
            case "linux":
                capabilities.setPlatform(Platform.LINUX);
                break;
            default:
                throw new IllegalArgumentException("Unsupported platform: " + os);
        }

    }
}