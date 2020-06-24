import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory as CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as MobileBuiltInKeywords
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testcase.TestCaseFactory as TestCaseFactory
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WSBuiltInKeywords
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUiBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

CustomKeywords.'appmanage.startTest.openbrowser'()

WebUI.click(findTestObject('Page_Dynamo/button_dropdownMenuButton'))

WebUI.click(findTestObject('Page_Dynamo/a_Edit'))

WebUI.clearText(findTestObject('Page_Edit-App/input_name'), FailureHandling.STOP_ON_FAILURE)

WebUI.clearText(findTestObject('Page_Edit-App/textarea_g'), FailureHandling.STOP_ON_FAILURE)

WebUI.selectOptionByIndex(findTestObject('Page_Edit-App/select_ActiveInActive'), 1)

WebUI.clearText(findTestObject('Page_Edit-App/input_appFeatures0.name'), FailureHandling.STOP_ON_FAILURE)

WebUI.clearText(findTestObject('Page_Edit-App/input_appFeatures0.description'), FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Page_Edit-App/button_Submit'))

WebUI.verifyElementPresent(findTestObject('Page_Create-App/empty_object'), 0)

CustomKeywords.'appmanage.startTest.closebrowser'()

