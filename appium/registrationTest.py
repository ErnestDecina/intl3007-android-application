import unittest
from appium import webdriver
from appium.options.android import UiAutomator2Options
from appium.webdriver.common.appiumby import AppiumBy
import time
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from appium.options.android import UiAutomator2Options

class TestRegistration(unittest.TestCase):
    @classmethod
    def setUp(cls) -> None:
        # Define your app startup settings here
        capabilities = dict(
            platformName='Android',
            automationName='uiautomator2',
            deviceName='emulator-5554',
            appPackage='com.ernestjohndecina.intl3007_diary_application',
            appActivity='.activities.RegisterActivity',  
            language='en',
            locale='US'
        )
        appium_server_url = 'http://localhost:4723'
        cls.driver = webdriver.Remote(appium_server_url, options=UiAutomator2Options().load_capabilities(capabilities))
        cls.tearDownAtEnd = True
    
    @classmethod
    def tearDown(cls) -> None:
        time.sleep(10)
        if cls.tearDownAtEnd:
            if cls.driver:
                cls.driver.quit()
            else:
                print("Keeping the emulator open for further inspection.")

    def test_user_registration(self) -> None:
        firstname_field = self.driver.find_element(by=AppiumBy.ID, value='com.ernestjohndecina.intl3007_diary_application:id/firstNameEditText')
        email_field = self.driver.find_element(by=AppiumBy.ID, value='com.ernestjohndecina.intl3007_diary_application:id/emailEditText')
        username_field = self.driver.find_element(by=AppiumBy.ID, value='com.ernestjohndecina.intl3007_diary_application:id/usernameEditText')
        password_field = self.driver.find_element(by=AppiumBy.ID, value='com.ernestjohndecina.intl3007_diary_application:id/pinEditNumber')
        register_button = self.driver.find_element(by=AppiumBy.ID, value='com.ernestjohndecina.intl3007_diary_application:id/registerButton')
        time.sleep(2)

        # set test data
        firstname_field.send_keys('John')
        email_field.send_keys('john@example.com')
        username_field.send_keys('john123')
        password_field.send_keys('1234')
        time.sleep(2)
        # Bush register button
        register_button.click()


        # Wait and check for the Toast message using XPath
        toast = WebDriverWait(self.driver, 20).until(
            EC.presence_of_element_located((AppiumBy.XPATH, ".//*[contains(@text, 'Registration successful!')]")),
            "Toast message not found"
        )
        self.assertIsNotNone(toast)  # Check that the toast message is not None


if __name__ == '__main__':
    unittest.main()