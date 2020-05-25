package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.QuestionsData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.commonobjects.TopScreenPanelSteps;
import com.cyberiansoft.test.vnext.steps.questionform.QuestionFormSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.SelectedServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.ServiceDetailsScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.NotesValidations;
import com.cyberiansoft.test.vnext.validations.QuestionFormValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class VNextQuestionAnswerTextImageComments extends BaseTestClass {

    @BeforeClass(description = "Question Answer Text-Image Comments")
    public void settingUp() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getQuestionAnswerTextImageCommentsTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanAddCommentToAllQuestionsType(String rowID,
                                                                                   String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        final String notestText = "Test 1";
        int numberOfPictureNotes = 2;

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.AUTOTEST_QUESTIONS_FORMS);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS, 2);
        QuestionFormSteps.answerTextQuestion(inspectionData.getQuestionScreenData().getQuestionData().getTextQuestionData());
        QuestionFormSteps.answerImageQuestion(inspectionData.getQuestionScreenData().getQuestionData().getImageQuestion());
        QuestionFormSteps.answerDateQuestion(inspectionData.getQuestionScreenData().getQuestionData().getDatePickerQuestion(),
                currentDate.getMonthValue(), currentDate.getDayOfMonth(), currentDate.getYear());
        QuestionFormSteps.answerTimeQuestion(inspectionData.getQuestionScreenData().getQuestionData().getTimePickerQuestion(),
                currentTime.getHour(), currentTime.getMinute(), currentTime.getSecond());
        QuestionFormSteps.answerSignatureQuestion(inspectionData.getQuestionScreenData().getQuestionData().getSignatureQuestion());

        List<QuestionsData> questionsData = inspectionData.getQuestionScreenData().getQuestionsData();
        questionsData.forEach(question -> {
            QuestionFormSteps.clickQuestionNotes(question.getQuestionName());
            NotesSteps.setNoteText(notestText);
            NotesSteps.addPhotoFromCamera();
            NotesSteps.addPhotoFromCamera();
            TopScreenPanelSteps.goToThePreviousScreen();
        });

        questionsData.forEach(question -> {
            QuestionFormValidations.validateQuestionHasNotes(question.getQuestionName(), true);
            QuestionFormSteps.clickQuestionNotes(question.getQuestionName());
            NotesValidations.verifyNumberOfPicturesPresent(numberOfPictureNotes);
            NotesValidations.verifyNoteIsPresent(notestText);
            TopScreenPanelSteps.goToThePreviousScreen();
        });
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanRemoveCommentForQuestion(String rowID,
                                                              String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String notestText = "Test 1";
        final String quickNote = "Note15";
        int numberOfPictureNotes = 2;

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.AUTOTEST_QUESTIONS_FORMS);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS, 2);

        List<QuestionsData> questionsData = inspectionData.getQuestionScreenData().getQuestionsData();
        questionsData.forEach(question -> {
            QuestionFormSteps.clickQuestionNotes(question.getQuestionName());
            NotesSteps.addQuickNote(quickNote);
            NotesSteps.setNoteText(notestText);
            NotesSteps.addPhotoFromCamera();
            NotesSteps.addPhotoFromCamera();
            TopScreenPanelSteps.goToThePreviousScreen();
        });

        final QuestionsData firstQuestion = inspectionData.getQuestionScreenData().getQuestionsData().get(0);
        QuestionFormSteps.clickQuestionNotes(firstQuestion.getQuestionName());
        NotesValidations.verifyNoteIsPresent(quickNote + notestText);
        NotesValidations.verifyNumberOfPicturesPresent(numberOfPictureNotes);
        NotesSteps.deleteAllPictures();
        NotesSteps.tapNoteTextAndClear();
        TopScreenPanelSteps.goToThePreviousScreen();
        QuestionFormValidations.validateQuestionHasNotes(firstQuestion.getQuestionName(), false);

        final String inspectionId = InspectionSteps.saveInspectionAsDraft();
        InspectionSteps.openInspectionToEdit(inspectionId);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS, 2);
        final QuestionsData secondQuestion = inspectionData.getQuestionScreenData().getQuestionsData().get(1);
        QuestionFormValidations.validateQuestionHasNotes(firstQuestion.getQuestionName(), false);
        QuestionFormValidations.validateQuestionHasNotes(secondQuestion.getQuestionName(), true);

        QuestionFormSteps.clickQuestionNotes(secondQuestion.getQuestionName());
        NotesValidations.verifyNoteIsPresent(quickNote + notestText);
        NotesValidations.verifyNumberOfPicturesPresent(numberOfPictureNotes);
        NotesSteps.deleteAllPictures();
        NotesSteps.tapNoteTextAndClear();
        TopScreenPanelSteps.goToThePreviousScreen();
        QuestionFormValidations.validateQuestionHasNotes(secondQuestion.getQuestionName(), false);

        InspectionSteps.saveInspectionAsDraft();
        InspectionSteps.openInspectionToEdit(inspectionId);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS, 2);

        questionsData.forEach(question -> {
            QuestionFormValidations.validateQuestionHasNotes(question.getQuestionName(), false);
        });
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanChangeCommentForQuestion(String rowID,
                                                          String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String notesText = "Test 1";
        final String newNotesText = "Test New";
        int numberOfPictureNotes = 2;
        int deletePictureNotes = 1;

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.AUTOTEST_QUESTIONS_FORMS);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS, 2);

        List<QuestionsData> questionsData = inspectionData.getQuestionScreenData().getQuestionsData();
        questionsData.forEach(question -> {
            QuestionFormSteps.clickQuestionNotes(question.getQuestionName());
            NotesSteps.setNoteText(notesText);
            NotesSteps.addPhotoFromCamera();
            NotesSteps.addPhotoFromCamera();
            TopScreenPanelSteps.goToThePreviousScreen();
        });

        inspectionData.getQuestionScreenData().getQuestionsData().forEach(questionData -> {
            QuestionFormSteps.clickQuestionNotes(questionData.getQuestionName());
            NotesValidations.verifyNoteIsPresent(notesText);
            NotesValidations.verifyNumberOfPicturesPresent(numberOfPictureNotes);
            NotesSteps.deletePictures(deletePictureNotes);
            NotesSteps.tapNoteTextAndClear();
            NotesSteps.setNoteText(newNotesText);
            TopScreenPanelSteps.goToThePreviousScreen();

            final String inspectionId = InspectionSteps.saveInspectionAsDraft();
            InspectionSteps.openInspectionToEdit(inspectionId);
            WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS, 2);
            QuestionFormSteps.clickQuestionNotes(questionData.getQuestionName());
            NotesValidations.verifyNoteIsPresent(newNotesText);
            NotesValidations.verifyNumberOfPicturesPresent(deletePictureNotes);
            TopScreenPanelSteps.goToThePreviousScreen();
            InspectionSteps.saveInspectionAsDraft();
            InspectionSteps.openInspectionToEdit(inspectionId);
            WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS, 2);
        });

        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanAddCommentForQuestionInService(String rowID,
                                                              String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String notestText = "Test 1";
        int numberOfPictureNotes = 2;

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.AUTOTEST_QUESTIONS_FORMS);

        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.openServiceDetails(inspectionData.getMoneyServiceData());
        ServiceDetailsScreenSteps.openQuestionForm(inspectionData.getMoneyServiceData().getQuestionData().getQuestionSetionName());
        QuestionFormSteps.answerTextQuestion(inspectionData.getMoneyServiceData().getQuestionData().getTextQuestionData());
        QuestionFormSteps.answerLogicalQuestion(inspectionData.getMoneyServiceData().getQuestionData().getLogicalQuestionData());
        inspectionData.getMoneyServiceData().getQuestionData().getSelectListOptionQuestions().forEach(selectListOptionQuestion -> {
            QuestionFormSteps.answerListOptionQuestion(selectListOptionQuestion);
        });

        List<QuestionsData> questionsData = inspectionData.getQuestionScreenData().getQuestionsData();
        questionsData.forEach(question -> {
            QuestionFormSteps.clickQuestionNotes(question.getQuestionName());
            NotesSteps.setNoteText(notestText);
            NotesSteps.addPhotoFromCamera();
            NotesSteps.addPhotoFromCamera();
            TopScreenPanelSteps.goToThePreviousScreen();
        });


        QuestionFormSteps.saveQuestionForm();
        ServiceDetailsScreenSteps.saveServiceDetails();

        final String inspectionId = InspectionSteps.saveInspectionAsDraft();
        InspectionSteps.openInspectionToEdit(inspectionId);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SelectedServicesScreenSteps.openServiceDetails(inspectionData.getMoneyServiceData().getServiceName());
        ServiceDetailsScreenSteps.openQuestionForm(inspectionData.getMoneyServiceData().getQuestionData().getQuestionSetionName());
        questionsData.forEach(question -> {
            QuestionFormValidations.validateQuestionHasNotes(question.getQuestionName(), true);
            QuestionFormSteps.clickQuestionNotes(question.getQuestionName());
            NotesValidations.verifyNumberOfPicturesPresent(numberOfPictureNotes);
            NotesValidations.verifyNoteIsPresent(notestText);
            TopScreenPanelSteps.goToThePreviousScreen();
        });
        QuestionFormSteps.saveQuestionForm();
        ServiceDetailsScreenSteps.saveServiceDetails();
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }
}
