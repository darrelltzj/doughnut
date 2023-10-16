import {
  findQuestionWithStem,
  expectFeedbackRequiredMessage,
  currentQuestion,
} from "./QuizQuestionPage"
import { goToLastResult, answeredQuestionPage } from "./AnsweredQuestionPage"
import { chatAboutNotePage } from "./chatAboutNotePage"
import { adminDashboardPage } from "./adminPages/adminDashboardPage"
import mock_services from "./mock_services"
import { questionGenerationService } from "./questionGenerationService"
import { higherOrderActions } from "./higherOrderActions"
import { jumpToNotePage } from "./jumpToNotePage"

const chatAboutNote = (noteTopic: string) => {
  jumpToNotePage(noteTopic).chatAboutNote()
}

const loginAsAdminAndGoToAdminDashboard = () => {
  cy.loginAs("admin")
  cy.reload()
  cy.openSidebar()
  cy.findByText("Admin Dashboard").click()
  return adminDashboardPage()
}

const pageObjects = {
  higherOrderActions,
  jumpToNotePage,
  questionGenerationService,
  answeredQuestionPage,
  goToLastResult,
  findQuestionWithStem,
  currentQuestion,
  expectFeedbackRequiredMessage,
  chatAboutNote,
  chatAboutNotePage,
  loginAsAdminAndGoToAdminDashboard,
}
export default pageObjects
export { mock_services }
