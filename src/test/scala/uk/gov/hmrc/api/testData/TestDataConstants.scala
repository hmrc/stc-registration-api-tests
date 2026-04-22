/*
 * Copyright 2025 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.api.testData

object TestDataConstants {

  // ==== Auth
  // --  Confidence level
  final val lowConfidence: Int  = 50
  final val highConfidence: Int = 250

  // -- Affinity Group
  final val affinityIndividual: String   = "Individual"
  final val affinityOrganisation: String = "Organisation"
  final val affinityAgent: String        = "Agent"

  // -- Enrolment Key
  final val enrolmentLegacy: String  = "IR-SA"
  final val enrolmentMTDITID: String = "HMRC-MTD-IT"

  // -- Agent Principle Enrolment Keys
  final val legacyAgentKey: String = "IR-SA-AGENT"
  final val asaAgentKey: String    = "HMRC-AS-AGENT"

  // -- Agent Principle Identifier Keys
  final val legacyAgentIdentifier: String = "IRAgentReference"
  final val asaAgentIdentifier: String    = "AgentReferenceNumber"

  // -- Agent Principle values
  final val legacyAgentValue: String = "a1B2c3" // /^[A-Za-z0-9]{6}$/
  final val asaAgentValue: String    = "abc123" // /^[0-9A-Za-z]{1,11}$/

  // -- Enrolment Identifier
  final val identifierLegacy: String  = "UTR"
  final val identifierMTDITID: String = "MTDITID"

  // -- Auth Rule Identifier
  final val authRuleLegacy: String  = "sa-auth"
  final val authRuleMTDITID: String = "mtd-it-auth"

  final val credId: String = "credId"

  // ==== Test data
  // -- Local and Dev
  final val validNino: String         = "AA055075C"
  final val validMtditid: String      = "XQIT00000000001"
  final val invalidMtditid: String    = "XQIT00000000002"
  final val noNinoFoundForUtr: String = "1100000404"
  final val badUtrMultiple: String    = "1100000500"
  final val badUtrInvalidNino: String = "2200000400"

  final val badUtrHipInvalidCorrelationId: String = "3300000400"
  final val badUtrHipUnauthorised: String         = "3300000401"
  final val badUtrHipForbidden: String            = "3300000403"
  final val badUtrHipUtrNotFound: String          = "3300000404"
  final val badUtrHipUtrInvalid: String           = "3300000422"
  final val badUtrHipServerError: String          = "3300000500"
  final val badUtrHipExternalServiceError: String = "3300000502"
  final val badUtrHipServiceUnavailable: String   = "3300000503"
  final val badUtrHipInternalServiceError: String = "3300000504"
  final val goodUtrHipInternalService: String     = "3300000505"
  final val onlyBalanceDetails: String            = "3333333333"
  final val badUtrNinoServiceUnavailable: String  = "2200000503"
  final val badUtrNinoETMPValidationError: String = "2200000422"
  final val badUtrNinoServerError: String         = "2200000500"
  final val UtrWithoutPaymentReference: String    = "3300000506"

  // -- QA
  final val E2E001UTR: String = "1062958961"
  final val E2E002UTR: String = "4258351765"
  final val E2E003UTR: String = "1328835690"

  final val E2E004UTR: String   = "3384286946"
  final val E2E005UTR: String   = "1992665564"
  final val E2E004MtdID: String = "XXIT00000175992"
  final val E2E005MtdID: String = "XKIT00000175994"

  final val E2E006UTR: String = "1405365362"
  final val E2E007UTR: String = "1136938453"
  final val E2E008UTR: String = "2181477903"

  final val E2E009UTR: String   = "4809635190"
  final val E2E010UTR: String   = "2112635977"
  final val E2E011UTR: String   = "3601373390"
  final val E2E009MtdID: String = "XMIT00000175996"
  final val E2E010MtdID: String = "XSIT00000176377"
  final val E2E011MtdID: String = "XVIT00000176026"
}
