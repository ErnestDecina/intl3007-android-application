import unittest
from appium import webdriver
from appium.options.android import UiAutomator2Options
from appium.webdriver.common.appiumby import AppiumBy
import time

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
        username_field.send_keys('testUsername')
        password_field.send_keys('1234')

        # Bush login button
        login_button.click()

        # check if login was successful by for example finding a success message element
        success_message = self.driver.find_element(by=AppiumBy.ID, value='com.ernestjohndecina.intl3007_diary_application:id/successMessage')
        self.assertIsNotNone(success_message)

if __name__ == '__main__':
    unittest.main()