package com.cyberiansoft.test.vnext.testcases.r360pro.workorders;

import com.cyberiansoft.test.dataclasses.QuestionsData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.commonobjects.TopScreenPanelSteps;
import com.cyberiansoft.test.vnext.steps.questionform.QuestionFormSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.NotesValidations;
import com.cyberiansoft.test.vnext.validations.questionforms.QuestionFormValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class VNextQuestionAnswerTextImageComments extends BaseTestClass {

    @BeforeClass(description = "Question Answer Text-Image Comments")
    public void settingUp() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getWorkOrderQuestionAnswerTextImageCommentsTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWorkOrderVerifyUserCanAddCommentToAllQuestionsType(String rowID,
                                                              String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        final String notestText = "Test 1";
        int numberOfPictureNotes = 2;

        HomeScreenSteps.openCreateMyWorkOrder();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.AUTOTEST_QUESTIONS_FORMS, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS, 2);
        QuestionFormSteps.answerTextQuestion(workOrderData.getQuestionScreenData().getQuestionData().getTextQuestionData());
        QuestionFormSteps.answerImageQuestion(workOrderData.getQuestionScreenData().getQuestionData().getImageQuestion());
        QuestionFormSteps.answerDateQuestion(workOrderData.getQuestionScreenData().getQuestionData().getDatePickerQuestion(),
                currentDate.getMonthValue(), currentDate.getDayOfMonth(), currentDate.getYear());
        QuestionFormSteps.answerTimeQuestion(workOrderData.getQuestionScreenData().getQuestionData().getTimePickerQuestion(),
                currentTime.getHour(), currentTime.getMinute(), currentTime.getSecond());
        QuestionFormSteps.answerSignatureQuestion(workOrderData.getQuestionScreenData().getQuestionData().getSignatureQuestion());

        List<QuestionsData> questionsData = workOrderData.getQuestionScreenData().getQuestionsData();
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
        WorkOrderSteps.cancelWorkOrder();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWorkOrderVerifyUserCanRemoveCommentForQuestion(String rowID,
                                                          String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        final String notestText = "Test 1";
        final String quickNote = "Note15";
        int numberOfPictureNotes = 2;

        HomeScreenSteps.openCreateMyWorkOrder();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.AUTOTEST_QUESTIONS_FORMS, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS, 2);

        List<QuestionsData> questionsData = workOrderData.getQuestionScreenData().getQuestionsData();
        questionsData.forEach(question -> {
            QuestionFormSteps.clickQuestionNotes(question.getQuestionName());
            NotesSteps.addQuickNote(quickNote);
            NotesSteps.setNoteText(notestText);
            NotesSteps.addPhotoFromCamera();
            NotesSteps.addPhotoFromCamera();
            TopScreenPanelSteps.goToThePreviousScreen();
        });

        final QuestionsData firstQuestion = workOrderData.getQuestionScreenData().getQuestionsData().get(0);
        QuestionFormSteps.clickQuestionNotes(firstQuestion.getQuestionName());
        NotesValidations.verifyNoteIsPresent(quickNote + notestText);
        NotesValidations.verifyNumberOfPicturesPresent(numberOfPictureNotes);
        NotesSteps.deleteAllPictures();
        NotesSteps.tapNoteTextAndClear();
        TopScreenPanelSteps.goToThePreviousScreen();
        QuestionFormValidations.validateQuestionHasNotes(firstQuestion.getQuestionName(), false);

        final String workOrderId = WorkOrderSteps.saveWorkOrderAsDraft();
        WorkOrderSteps.openMenu(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS, 2);
        final QuestionsData secondQuestion = workOrderData.getQuestionScreenData().getQuestionsData().get(1);
        QuestionFormValidations.validateQuestionHasNotes(firstQuestion.getQuestionName(), false);
        QuestionFormValidations.validateQuestionHasNotes(secondQuestion.getQuestionName(), true);

        QuestionFormSteps.clickQuestionNotes(secondQuestion.getQuestionName());
        NotesValidations.verifyNoteIsPresent(quickNote + notestText);
        NotesValidations.verifyNumberOfPicturesPresent(numberOfPictureNotes);
        NotesSteps.deleteAllPictures();
        NotesSteps.tapNoteTextAndClear();
        TopScreenPanelSteps.goToThePreviousScreen();
        QuestionFormValidations.validateQuestionHasNotes(secondQuestion.getQuestionName(), false);

        WorkOrderSteps.saveWorkOrderAsDraft();
        WorkOrderSteps.openMenu(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS, 2);

        questionsData.forEach(question -> {
            QuestionFormValidations.validateQuestionHasNotes(question.getQuestionName(), false);
        });
        WorkOrderSteps.cancelWorkOrder();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWorkOrderVerifyUserCanChangeCommentForQuestion(String rowID,
                                                          String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        final String notesText = "Test 1";
        final String newNotesText = "Test New";
        int numberOfPictureNotes = 2;
        int deletePictureNotes = 1;

        HomeScreenSteps.openCreateMyWorkOrder();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.AUTOTEST_QUESTIONS_FORMS, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS, 2);

        List<QuestionsData> questionsData = workOrderData.getQuestionScreenData().getQuestionsData();
        questionsData.forEach(question -> {
            QuestionFormSteps.clickQuestionNotes(question.getQuestionName());
            NotesSteps.setNoteText(notesText);
            NotesSteps.addPhotoFromCamera();
            NotesSteps.addPhotoFromCamera();
            TopScreenPanelSteps.goToThePreviousScreen();
        });

        workOrderData.getQuestionScreenData().getQuestionsData().forEach(questionData -> {
            QuestionFormSteps.clickQuestionNotes(questionData.getQuestionName());
            NotesValidations.verifyNoteIsPresent(notesText);
            NotesValidations.verifyNumberOfPicturesPresent(numberOfPictureNotes);
            NotesSteps.deletePictures(deletePictureNotes);
            NotesSteps.tapNoteTextAndClear();
            NotesSteps.setNoteText(newNotesText);
            TopScreenPanelSteps.goToThePreviousScreen();

            final String workOrderId = WorkOrderSteps.saveWorkOrderAsDraft();
            WorkOrderSteps.openMenu(workOrderId);
            MenuSteps.selectMenuItem(MenuItems.EDIT);
            WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS, 2);
            QuestionFormSteps.clickQuestionNotes(questionData.getQuestionName());
            NotesValidations.verifyNoteIsPresent(newNotesText);
            NotesValidations.verifyNumberOfPicturesPresent(deletePictureNotes);
            TopScreenPanelSteps.goToThePreviousScreen();
            WorkOrderSteps.saveWorkOrderAsDraft();
            WorkOrderSteps.openMenu(workOrderId);
            MenuSteps.selectMenuItem(MenuItems.EDIT);
            WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS, 2);
        });

        WorkOrderSteps.cancelWorkOrder();
        ScreenNavigationSteps.pressBackButton();
    }
}
