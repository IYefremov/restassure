package com.cyberiansoft.test.vnext.testcases;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextVerificationScreen;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;

public class VNextRegistrationActivationLoginLogoutTestCases extends BaseTestCaseWithoutDeviceRegistration {
	
	@Test(testName= "Test Case 35685:vNext - Key verification for existing device (invalid key), Test Case 35687:vNext - Validate progress is shown for preparing database for download, Test Case 35686:vNext - Validate verification code is not cleared after validaiton, Test Case 35982:vNext - Validate message is shown on DB download success", 
			description = "Key verification for existing device (invalid key)")
	@Parameters({ "backoffice.url", "user.name", "user.psw", "device.license" })	
	public void testKeyVerificationForExistingDeviceIinvalidKey(String deviceofficeurl, String deviceuser, String devicepsw, String licensename) {
		VNextVerificationScreen verificationscreen = new VNextVerificationScreen(appiumdriver);
		String charreplace = regcode.substring(1, 2);
		if (charreplace.equals("3")) {
			verificationscreen.setDeviceRegistrationCode(regcode.replace(regcode.substring(1, 2), "2"));
			charreplace = regcode.replace(regcode.substring(1, 2), "2");
		} else {
			verificationscreen.setDeviceRegistrationCode(regcode.replace(regcode.substring(1, 2), "3"));
			charreplace = regcode.replace(regcode.substring(1, 2), "3");
		}		
		verificationscreen.clickVerifyButton();
		VNextInformationDialog errordialog = new VNextInformationDialog(appiumdriver);
		String msg = errordialog.clickInformationDialogOKButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.REGISTRATION_CODE_IS_NOT_VALID);
		Assert.assertEquals(verificationscreen.getEnteredDeviceRegistrationCodeValue(), charreplace);
		
		verificationscreen.setDeviceRegistrationCode(regcode);
		verificationscreen.clickVerifyButton();
		Assert.assertTrue(verificationscreen.isDownloadDBProgressBarAppears());
		Assert.assertTrue(verificationscreen.isDownloadVINProgressBarAppears());
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogOKButton();
		restartAppAndGetNewRegCode(deviceofficeurl, deviceuser, devicepsw, licensename);
	}
	
	@Test(testName= "Test Case 36149:vNext - Verify interrupted DB download is supported with message, Test Case 36154:vNext - Close message shown for interrupted DB download", 
			description = "Verify interrupted DB download is supported with message, Close message shown for interrupted DB download")
	public void testVerifyInterruptedDBDownloadIsSupportedWithMessage() {
		VNextVerificationScreen verificationscreen = new VNextVerificationScreen(appiumdriver);
		verificationscreen.setDeviceRegistrationCode(regcode);
		waitABit(1000);
		setNetworkOff();
		verificationscreen.clickVerifyButton();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		String msg = informationdlg.clickInformationDialogOKButtonAndGetMessage();
		setNetworkOn();
		Assert.assertEquals(msg, VNextAlertMessages.CANT_DOWNLOAD_DB);   
	}
	
	@Test(testName= "Test Case 36155:vNext - Validate 'Download Again' option appears after DB download interruption", 
			description = "Validate 'Download Again' option appears after DB download interruption")
	@Parameters({ "backoffice.url", "user.name", "user.psw", "device.license" })
	public void testVerifyDownloadAgainOptionAppearsAfterDBDownloadInterruption(String deviceofficeurl, String deviceuser, String devicepsw, String licensename) {
		VNextVerificationScreen verificationscreen = new VNextVerificationScreen(appiumdriver);
		verificationscreen.setDeviceRegistrationCode(regcode);
		//verificationscreen.setNetworkOff();
		verificationscreen.clickVerifyButton();
		Assert.assertTrue(verificationscreen.isDownloadDBProgressBarAppears());
		Assert.assertTrue(verificationscreen.isDownloadVINProgressBarAppears());
		waitABit(5*1000);
		setNetworkOff();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		String msg = informationdlg.clickInformationDialogOKButtonAndGetMessage();
		setNetworkOn();
		Assert.assertEquals(msg, VNextAlertMessages.CANT_DOWNLOAD_DB); 
		Assert.assertTrue(verificationscreen.isDownloadAgainButtonAppears());
		restartAppAndGetNewRegCode(deviceofficeurl, deviceuser, devicepsw, licensename);
	}
	
	@Test(testName= "Test Case 36156:vNext - Validate message appears in case of unsuccessful apply of 'Download Again' option", 
			description = "Validate message appears in case of unsuccessful apply of 'Download Again' option")
	@Parameters({ "backoffice.url", "user.name", "user.psw", "device.license" })
	public void testVerifyMessageAppearsInCaseOfUnsuccessfulApplyOfDownloadAgainOption(String deviceofficeurl, String deviceuser, String devicepsw, String licensename) {
		VNextVerificationScreen verificationscreen = new VNextVerificationScreen(appiumdriver);
		verificationscreen.setDeviceRegistrationCode(regcode);
		verificationscreen.clickVerifyButton();
		Assert.assertTrue(verificationscreen.isDownloadDBProgressBarAppears());
		Assert.assertTrue(verificationscreen.isDownloadVINProgressBarAppears());
		waitABit(5*1000);
		setNetworkOff();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		String msg = informationdlg.clickInformationDialogOKButtonAndGetMessage();
		
		Assert.assertEquals(msg, VNextAlertMessages.CANT_DOWNLOAD_DB); 
		verificationscreen.clickDownloadAgainButton();
		informationdlg = new VNextInformationDialog(appiumdriver);
		msg = informationdlg.clickInformationDialogOKButtonAndGetMessage();
		
		Assert.assertEquals(msg, VNextAlertMessages.CANT_DOWNLOAD_DB); 
		setNetworkOn();
		restartAppAndGetNewRegCode(deviceofficeurl, deviceuser, devicepsw, licensename);
	}
	
	@Test(testName= "Test Case 36158:vNext - Verify user can start DB download again after download fail", 
			description = "Verify user can start DB download again after download fail")
	@Parameters({ "backoffice.url", "user.name", "user.psw", "device.license" })
	public void testVerifyUserCanStartDBDownloadAgainAfterDownloadFail(String deviceofficeurl, String deviceuser, String devicepsw, String licensename) {
		VNextVerificationScreen verificationscreen = new VNextVerificationScreen(appiumdriver);
		verificationscreen.setDeviceRegistrationCode(regcode);
		//verificationscreen.setNetworkOff();
		verificationscreen.clickVerifyButton();
		Assert.assertTrue(verificationscreen.isDownloadDBProgressBarAppears());
		Assert.assertTrue(verificationscreen.isDownloadVINProgressBarAppears());
		waitABit(5*1000);
		setNetworkOff();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		String msg = informationdlg.clickInformationDialogOKButtonAndGetMessage();
		setNetworkOn();
		Assert.assertEquals(msg, VNextAlertMessages.CANT_DOWNLOAD_DB);
		verificationscreen.clickDownloadAgainButton();
		
		Assert.assertTrue(verificationscreen.isDownloadDBProgressBarAppears());
		Assert.assertTrue(verificationscreen.isDownloadVINProgressBarAppears());
		informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogOKButton();
		restartAppAndGetNewRegCode(deviceofficeurl, deviceuser, devicepsw, licensename);
	}

}
