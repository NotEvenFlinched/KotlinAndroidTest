import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.remote.MobileCapabilityType
import org.openqa.selenium.By
import org.openqa.selenium.remote.DesiredCapabilities
import org.testng.annotations.AfterClass
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test
import java.io.File
import java.net.URL
import java.util.concurrent.TimeUnit
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.testng.Assert

open class GoogleTranslateTest {
    var driver: AndroidDriver<MobileElement>? = null

    @BeforeClass
    fun setUp() {
        val app = File("lib/Translate_v5.24.1.apk")
        val capabilities = DesiredCapabilities()
        val serverAddress = URL("http://127.0.0.1:4723/wd/hub")

        capabilities.setCapability("noReset", false)
        capabilities.setCapability("fullReset", true)
        capabilities.setCapability("unicodeKeyboard", true)
        capabilities.setCapability("resetKeyboard", true)
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

    @Test
    fun testGoogleTranslate() {
        val offlineTranslateCheckbox = driver?.findElement(By.id("com.google.android.apps.translate:id/onboarding_checkbox")) as MobileElement
        val closeInitSetup = driver?.findElement(By.id("com.google.android.apps.translate:id/button_done")) as MobileElement
        offlineTranslateCheckbox.click()
        closeInitSetup.click()
        driver?.pressKey(KeyEvent(AndroidKey.BACK))

        val inputFieldAccess = driver?.findElement(By.id("com.google.android.apps.translate:id/touch_to_type_text")) as MobileElement
        inputFieldAccess.click()
        val inputField = driver?.findElement(By.id("com.google.android.apps.translate:id/edit_input")) as MobileElement
        inputField.sendKeys("Мне нравится Kotlin")
        val doTranslation = driver?.findElement(By.id("com.google.android.apps.translate:id/result_selector")) as MobileElement
        doTranslation.click()

        val translationResult = driver?.findElement(By.xpath("(//*[@resource-id='android:id/text1'])[2]")) as MobileElement
        Assert.assertEquals(translationResult.text,"I like Kotlin")
    }

    @AfterClass
    fun tearDown() {
        driver?.quit()
    }
}