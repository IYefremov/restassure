package com.cyberiansoft.test.vnext.testcases.r360free.registrationandnavigation;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextVerificationScreen;
import com.cyberiansoft.test.vnext.testcases.r360free.BaseTestCaseWithoutDeviceRegistration;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.utils.VNextAppUtils;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class VNextRegistrationActivationLoginLogoutTestCases extends BaseTestCaseWithoutDeviceRegistration {
	
	@Test(testName= "Test Case 35685:vNext - Key verification for existing device (invalid key), Test Case 35687:vNext - Validate progress is shown for preparing database for download, Test Case 35686:vNext - Validate verification code is not cleared after validaiton, Test Case 35982:vNext - Validate message is shown on DB download success", 
			description = "Key verification for existing device (invalid key)")
	@Parameters({ "backoffice.url", "user.name", "user.psw", "device.license" })	
	public void testKeyVerificationForExistingDeviceIinvalidKey(String deviceofficeurl, String deviceuser, String devicepsw, String licensename) {
        VNextVerificationScreen verificationscreen = new VNextVerificationScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		String charreplace = regcode.substring(1, 2);
		if (charreplace.equals("3")) {
			verificationscreen.setDeviceRegistrationCode(regcode.replace(regcode.substring(1, 2), "2"));
			charreplace = regcode.replace(regcode.substring(1, 2), "2");
		} else {
			verificationscreen.setDeviceRegistrationCode(regcode.replace(regcode.substring(1, 2), "3"));
			charreplace = regcode.replace(regcode.substring(1, 2), "3");
		}		
		verificationscreen.clickVerifyButton();
        VNextInformationDialog errordialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		String msg = errordialog.clickInformationDialogOKButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.REGISTRATION_CODE_IS_NOT_VALID);
		Assert.assertEquals(verificationscreen.getEnteredDeviceRegistrationCodeValue(), charreplace);
		
		verificationscreen.setDeviceRegistrationCode(regcode);
		verificationscreen.clickVerifyButton();
		Assert.assertTrue(verificationscreen.isDownloadDBProgressBarAppears());
		Assert.assertTrue(verificationscreen.isDownloadVINProgressBarAppears());
        VNextInformationDialog informationdlg = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		informationdlg.clickInformationDialogOKButton();
		VNextAppUtils.resetApp();
		regcode = getDeviceRegistrationCode(deviceofficeurl, deviceuser, devicepsw, licensename);
	}
	
	@Test(testName= "Test Case 36149:vNext - Verify interrupted DB download is supported with message, Test Case 36154:vNext - Close message shown for interrupted DB download", 
			description = "Verify interrupted DB download is supported with message, Close message shown for interrupted DB download")
	public void testVerifyInterruptedDBDownloadIsSupportedWithMessage() {
        VNextVerificationScreen verificationscreen = new VNextVerificationScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		verificationscreen.setDeviceRegistrationCode(regcode);
		BaseUtils.waitABit(1000);
		AppiumUtils.setNetworkOff();
		verificationscreen.clickVerifyButton();
        VNextInformationDialog informationdlg = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		String msg = informationdlg.clickInformationDialogOKButtonAndGetMessage();
		AppiumUtils.setAndroidNetworkOn();
		Assert.assertEquals(msg, VNextAlertMessages.CANT_DOWNLOAD_DB);   
	}
	
	@Test(testName= "Test Case 36155:vNext - Validate 'Download Again' option appears after DB download interruption", 
			description = "Validate 'Download Again' option appears after DB download interruption")
	@Parameters({ "backoffice.url", "user.name", "user.psw", "device.license" })
	public void testVerifyDownloadAgainOptionAppearsAfterDBDownloadInterruption(String deviceofficeurl, String deviceuser, String devicepsw, String licensename) {
        VNextVerificationScreen verificationscreen = new VNextVerificationScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		verificationscreen.setDeviceRegistrationCode(regcode);
		//verificationscreen.setNetworkOff();
		verificationscreen.clickVerifyButton();
		Assert.assertTrue(verificationscreen.isDownloadDBProgressBarAppears());
		Assert.assertTrue(verificationscreen.isDownloadVINProgressBarAppears());
		BaseUtils.waitABit(5*1000);
		AppiumUtils.setNetworkOff();
        VNextInformationDialog informationdlg = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		String msg = informationdlg.clickInformationDialogOKButtonAndGetMessage();
		AppiumUtils.setAndroidNetworkOn();
		Assert.assertEquals(msg, VNextAlertMessages.CANT_DOWNLOAD_DB); 
		Assert.assertTrue(verificationscreen.isDownloadAgainButtonAppears());
		VNextAppUtils.resetApp();
		regcode = getDeviceRegistrationCode(deviceofficeurl, deviceuser, devicepsw, licensename);
	}
	
	@Test(testName= "Test Case 36156:vNext - Validate message appears in case of unsuccessful apply of 'Download Again' option", 
			description = "Validate message appears in case of unsuccessful apply of 'Download Again' option")
	@Parameters({ "backoffice.url", "user.name", "user.psw", "device.license" })
	public void testVerifyMessageAppearsInCaseOfUnsuccessfulApplyOfDownloadAgainOption(String deviceofficeurl, String deviceuser, String devicepsw, String licensename) {
        VNextVerificationScreen verificationscreen = new VNextVerificationScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		verificationscreen.setDeviceRegistrationCode(regcode);
		verificationscreen.clickVerifyButton();
		Assert.assertTrue(verificationscreen.isDownloadDBProgressBarAppears());
		Assert.assertTrue(verificationscreen.isDownloadVINProgressBarAppears());
		BaseUtils.waitABit(5*1000);
		AppiumUtils.setNetworkOff();
        VNextInformationDialog informationdlg = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		String msg = informationdlg.clickInformationDialogOKButtonAndGetMessage();
		
		Assert.assertEquals(msg, VNextAlertMessages.CANT_DOWNLOAD_DB); 
		verificationscreen.clickDownloadAgainButton();
        informationdlg = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		msg = informationdlg.clickInformationDialogOKButtonAndGetMessage();
		
		Assert.assertEquals(msg, VNextAlertMessages.CANT_DOWNLOAD_DB); 
		AppiumUtils.setAndroidNetworkOn();
		VNextAppUtils.resetApp();
		regcode = getDeviceRegistrationCode(deviceofficeurl, deviceuser, devicepsw, licensename);
	}
	
	@Test(testName= "Test Case 36158:vNext - Verify user can start DB download again after download fail", 
			description = "Verify user can start DB download again after download fail")
	@Parameters({ "backoffice.url", "user.name", "user.psw", "device.license" })
	public void testVerifyUserCanStartDBDownloadAgainAfterDownloadFail(String deviceofficeurl, String deviceuser, String devicepsw, String licensename) {
        VNextVerificationScreen verificationscreen = new VNextVerificationScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		verificationscreen.setDeviceRegistrationCode(regcode);
		//verificationscreen.setNetworkOff();
		verificationscreen.clickVerifyButton();
		Assert.assertTrue(verificationscreen.isDownloadDBProgressBarAppears());
		Assert.assertTrue(verificationscreen.isDownloadVINProgressBarAppears());
		BaseUtils.waitABit(5*1000);
		AppiumUtils.setNetworkOff();
        VNextInformationDialog informationdlg = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		String msg = informationdlg.clickInformationDialogOKButtonAndGetMessage();
		AppiumUtils.setAndroidNetworkOn();
		Assert.assertEquals(msg, VNextAlertMessages.CANT_DOWNLOAD_DB);
		verificationscreen.clickDownloadAgainButton();
		
		Assert.assertTrue(verificationscreen.isDownloadDBProgressBarAppears());
		Assert.assertTrue(verificationscreen.isDownloadVINProgressBarAppears());
        informationdlg = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		informationdlg.clickInformationDialogOKButton();
		VNextAppUtils.resetApp();
		regcode = getDeviceRegistrationCode(deviceofficeurl, deviceuser, devicepsw, licensename);
	}

}
