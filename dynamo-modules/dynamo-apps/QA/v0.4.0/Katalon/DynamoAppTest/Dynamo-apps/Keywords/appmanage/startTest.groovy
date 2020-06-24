package appmanage

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testcase.TestCaseFactory
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords

import internal.GlobalVariable

import MobileBuiltInKeywords as Mobile
import WSBuiltInKeywords as WS
import WebUiBuiltInKeywords as WebUI

public class startTest {
	@Keyword
	public void openbrowser(){
		WebUI.openBrowser('http://localhost:8080/')
		WebUI.sendKeys(findTestObject('Page_Login Page/input_username'), 'user')
		WebUI.sendKeys(findTestObject('Page_Login Page/input_password'), '152feaf3-2269-48b0-b9d0-08ac4fd21f03')
		WebUI.click(findTestObject('Page_Login Page/input_Submit'))
		WebUI.click(findTestObject('Page_dynamo apps/a_ManageApps Artifacts'))
	}

	@Keyword
	public void closebrowser(){
		WebUI.closeBrowser()
	}
}
