# testSetups.py
import unittest
from appium import webdriver
from appium.options.android import UiAutomator2Options

class TestSetup(unittest.TestCase):
    def setUp(cls) -> None:
        # Define your app startup settings here
        capabilities = dict(
            platformName='Android',
            automationName='uiautomator2',
            deviceName='emulator-5554',
            appPackage='com.ernestjohndecina.intl3007_diary_application', 
            appActivity='.MainActivity', 
            language='en',
            locale='US'
        )
        appium_server_url = 'http://localhost:4723'
        cls.driver = webdriver.Remote(appium_server_url, options=UiAutomator2Options().load_capabilities(capabilities))
        print("Setup Complete")

    @classmethod
    def tearDownClass(cls):
        cls.driver.quit()
        print("Teardown Complete")
