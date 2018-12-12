import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.remote.MobileCapabilityType
import org.openqa.selenium.remote.DesiredCapabilities
import org.testng.annotations.AfterClass
import org.testng.annotations.BeforeClass
import java.io.File
import java.net.URL
import java.util.concurrent.TimeUnit

open class AppiumTest {

    var driver: AppiumDriver<MobileElement>? = null

    @BeforeClass
    fun setup() {
        val app = File("lib/Translate_v5.24.1.apk")
        val capabilities = DesiredCapabilities()
        val serverAddress = URL("http://127.0.0.1:4723/wd/hub")

        capabilities.setCapability("noReset", false)
        capabilities.setCapability("fullReset", true)
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android")
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "C1905")
        capabilities.setCapability(MobileCapabilityType.APP, app.absolutePath)
        capabilities.setCapability("appPackage", "com.google.android.apps.translate")
        capabilities.setCapability("appActivity", "com.google.android.apps.translate.TranslateActivity")

        driver = AndroidDriver(serverAddress, capabilities)
        driver?.let {
            it.manage()?.timeouts()?.implicitlyWait(20, TimeUnit.SECONDS)
        }
    }

    @AfterClass
    fun tearDown() {
        driver?.quit()
    }
}