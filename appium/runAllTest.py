import unittest

if __name__ == '__main__':
    loader = unittest.TestLoader()


    # download the test cases from the test files
    registration_tests = loader.loadTestsFromName('registrationTest')
    login_tests = loader.loadTestsFromName('loginTest')
    
    # create a test suite
    test_suite = unittest.TestSuite()
    test_suite.addTests(registration_tests)
    test_suite.addTests(login_tests)
    
    # run the test suite
    runner = unittest.TextTestRunner(verbosity=2)
    runner.run(test_suite)
