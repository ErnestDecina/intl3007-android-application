import unittest
from appium import webdriver
from appium.options.android import UiAutomator2Options
from appium.webdriver.common.appiumby import AppiumBy
import time

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

    def test_user_registration(self) -> None:
        firstname_field = self.driver.find_element(by=AppiumBy.ID, value='com.ernestjohndecina.intl3007_diary_application:id/firstNameEditText')
        email_field = self.driver.find_element(by=AppiumBy.ID, value='com.ernestjohndecina.intl3007_diary_application:id/emailEditText')
        username_field = self.driver.find_element(by=AppiumBy.ID, value='com.ernestjohndecina.intl3007_diary_application:id/usernameEditText')
        password_field = self.driver.find_element(by=AppiumBy.ID, value='com.ernestjohndecina.intl3007_diary_application:id/pinEditNumber')
        register_button = self.driver.find_element(by=AppiumBy.ID, value='com.ernestjohndecina.intl3007_diary_application:id/registerButton')
        time.sleep(2)

        # set test data
        firstname_field.send_keys('testFirstName')
        email_field.send_keys('test@example.com')
        username_field.send_keys('testUsername')
        password_field.send_keys('1234')

        # Bush register button
        register_button.click()


        # check if registration was successful by for example finding a success message element
        success_message = self.driver.find_element(by=AppiumBy.ID, value='com.ernestjohndecina.intl3007_diary_application:id/successMessage')
        self.assertIsNotNone(success_message)


if __name__ == '__main__':
    unittest.main()