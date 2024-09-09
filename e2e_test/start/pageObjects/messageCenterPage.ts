import { systemSidebar } from './systemSidebar'

const assumeMessageCenterPage = () => {
  return {
    expectMessage(message: string, partner: string) {
      cy.findByText(message).should('be.visible')
      cy.findByText(partner).should('be.visible')
      return this
    },
  }
}

export const navigateToMessageCenter = () => {
  systemSidebar().userOptions().myMessageCenter()
  return assumeMessageCenterPage()
}
