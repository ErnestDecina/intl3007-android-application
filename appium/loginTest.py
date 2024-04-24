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
    def setUp(self) -> None:
        # Define your app startup settings here
        capabilities = dict(
            platformName='Android',
            automationName='uiautomator2',
            deviceName='emulator-5554',
            appPackage='com.ernestjohndecina.intl3007_diary_application', 
            appActivity='.activities.LoginActivity', 
            language='en',
            locale='US'
        )
        appium_server_url = 'http://localhost:4723'
        self.driver = webdriver.Remote(appium_server_url, options=UiAutomator2Options().load_capabilities(capabilities))

    def tearDown(self) -> None:
        time.sleep(10)
        if self.driver:
            self.driver.quit()

    def test_user_login(self) -> None:
        username_field = self.driver.find_element(by=AppiumBy.ID, value='com.ernestjohndecina.intl3007_diary_application:id/usernameLoginEditText')
        password_field = self.driver.find_element(by=AppiumBy.ID, value='com.ernestjohndecina.intl3007_diary_application:id/pinLoginEditText')
        login_button = self.driver.find_element(by=AppiumBy.ID, value='com.ernestjohndecina.intl3007_diary_application:id/loginButton')

        # set test data
        username_field.send_keys('john123')
        password_field.send_keys('1234')

        time.sleep(5)

        # Bush login button
        login_button.click()

if __name__ == '__main__':
    unittest.main()