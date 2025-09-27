# Datacom Bugs Form Automation Framework  This project automates testing of the Datacom Bugs Form using ** Java + Playwright + TestNG + ExtentReports**.   It validates input fields, dropdowns, and form behavior against expected rules.

STRATEGY (checkpoints)

    1. Check the inputs/valid characters based on the field
    	- First Name (Alphabet only)
    	- Last Name (Alphabet only)
    	- Phone Number (Numeric only, accepts only + and (), NO other special characters)
    	- Country (Cant type, dropdown option)
    	- Email Address (accepts Alpha-numeric, accepts only @ and . , NO other special characters)
    	- Password (Alpha-numeric, accepts special characters, 6-20 characters)
    
    2. Check the input combinations 
    	- Validate Required(*) and Non-required fields
    	- Empty values in the Required fields should not be accepted and should be prompted an error
    
    3. Checkbox (Terms and Conditions) should only be available when all required fields are filled-out with proper/expected inputs.


DESIGN

    Single Class with multiple @Test per field i.e.
    public void class BugsFormTestName extends BaseTest(){
        @Test public void testValidInputFirstName() { ... }
        @Test public void testEmptyInputFirstName() { ... }
        @Test public void testInvalidInputFirstName() { ... }
    }

    Run via (smoke_suite.xml | regression_suite.xml) or per @Test via TestNG Test

    Reports are generated @ test-output folder > ExtentReport.html


BUGS

    1-2. First Name field accepts Special Characters and Numbers
    
    3-4. Last Name field accepts Special Characters and Numbers
    
    5-6. Phone Number field accepts Special Characters and Alphabet
    
    7. Phone number field is misspelled (Phone nunber)
       
    8. Upon registration if user didnt select any Country, the information that is being displayed is 'Select a country...'
       
    9. Email address accepts input values w/o @ and . (as indicators of a valid email address)
        
    10. Password field doesn't accept max input value of 20
        
    11. Register button prompts only 'password' even-though other fields are not filled-out
        
    12. Terms and conditions checkbox is always disabled
        
    13. Register button accepts input values even without (Terms and conditions) is not accepted/selected.
        
    14-15. Can still register even though mandatory field is empty (Last Name, Email)
        
    16. Upon registration Last Name displays incomplete information (Monkey > Monke)
        
    17. Successfull registration still in red color (#721c240) expects green?
        
    18. Country field displays invalid "Country" > Real Country count = 195 vs //select[contains(@id, 'countries')]/option[@value] = 246 ( A Total of 52 invalid options/Not real country)
     - Bonaire 
     - Channel Islands
     - Christmas Island
     - Cocos Island
     - Cook Islands
     - Wallis & Futana Is
     - Zaire



