import { DataTable } from "@badeball/cypress-cucumber-preprocessor"
/// <reference types="cypress" />
/// <reference types="../support" />
// @ts-check

import { Given } from "@badeball/cypress-cucumber-preprocessor"
import pageObjects from "page_objects"

Given("my question should not be included in the admin's fine-tuning data", () => {
  pageObjects
    .loginAsAdminAndGoToAdminDashboard()
    .suggestedQuestionsForFineTuning()
    .downloadAIQuestionTrainingData()
    .expectNumberOfRecords(0)
})

Given(
  "an admin edit the question and choices {string} with a different question:",
  (originalQuestionStem: string, newQuestion: DataTable) => {
    pageObjects
      .loginAsAdminAndGoToAdminDashboard()
      .suggestedQuestionsForFineTuning()
      .updateQuestionSuggestionAndChoice(originalQuestionStem, newQuestion.hashes()[0])
  },
)

Given("an admin can duplicate the question {string}", () => {
  pageObjects
    .loginAsAdminAndGoToAdminDashboard()
    .suggestedQuestionsForFineTuning()
    .duplicateNegativeQuestion()
})

Given(
  "an admin can retrieve the training data for question generation containing:",
  (question: DataTable) => {
    pageObjects
      .loginAsAdminAndGoToAdminDashboard()
      .suggestedQuestionsForFineTuning()
      .downloadAIQuestionTrainingData()
      .expectExampleQuestions(question.hashes())
  },
)

Given(
  "an admin can retrieve the training data for question generation containing {int} examples",
  (numOfDownload: number) => {
    pageObjects
      .loginAsAdminAndGoToAdminDashboard()
      .suggestedQuestionsForFineTuning()
      .downloadAIQuestionTrainingData()
      .expectNumberOfRecords(numOfDownload)
  },
)

Given(
  "an admin should be able to download the training data for evaluation containing {int} examples",
  (numOfDownload: number) => {
    pageObjects
      .loginAsAdminAndGoToAdminDashboard()
      .suggestedQuestionsForFineTuning()
      .downloadFeedbackForEvaluationModel()
      .expectNumberOfRecords(numOfDownload)
  },
)

Given("the admin should see {string} in the suggested questions", (expectedComment: string) => {
  pageObjects
    .loginAsAdminAndGoToAdminDashboard()
    .suggestedQuestionsForFineTuning()
    .expectString(1, expectedComment)
})

Given(
  "an admin should not be able to duplicate this feedback to the question {string}",
  (originalQuestionStem: string) => {
    pageObjects
      .loginAsAdminAndGoToAdminDashboard()
      .suggestedQuestionsForFineTuning()
      .expectUnableToDuplicate(originalQuestionStem)
  },
)

Given(
  "an admin should be able to see {int} examples containing {string}",
  (numOfOccurrence: number, expectedString: string) => {
    pageObjects
      .loginAsAdminAndGoToAdminDashboard()
      .suggestedQuestionsForFineTuning()
      .expectString(numOfOccurrence, expectedString)
  },
)

Given("an admin should be able to identify the duplicated record", () => {
  pageObjects
    .loginAsAdminAndGoToAdminDashboard()
    .suggestedQuestionsForFineTuning()
    .identifyDuplicatedRecord()
})
