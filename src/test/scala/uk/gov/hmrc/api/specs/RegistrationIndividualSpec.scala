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

package uk.gov.hmrc.api.specs

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.prop.TableDrivenPropertyChecks.forAll
import org.scalatest.prop.Tables.Table
import play.api.libs.json.{JsValue, Json}
import play.api.http.Status
import uk.gov.hmrc.api.helpers.BaseSpec
import uk.gov.hmrc.api.helpers.PayloadValidator
import uk.gov.hmrc.api.helpers.builders.SubscriptionRequestBuilder
import uk.gov.hmrc.api.testData.TestDataGenerator.generateNino
import uk.gov.hmrc.apitestrunner.util.ApiLogger.log
import uk.gov.hmrc.http.HttpResponse

import scala.concurrent.Future

class RegistrationIndividualSpec extends BaseSpec {

  Feature("Validate Individual Registration User Conditions") {

    val testCases = Table(
      ("description", "payload", "subscriptionStatus", "enrolmentStatus"),
      (
        "Success - All mandatory fields with correct values",
        SubscriptionRequestBuilder.valid,
        201,
        204
      ),
      (
        "Success - Missing Optional fields",
        SubscriptionRequestBuilder.withoutOptionalFields,
        201,
        204
      ),
      (
        "Error - Missing Name Field",
        SubscriptionRequestBuilder.missingContactName,
        400,
        204
      )
    )

    forAll(testCases) { (description: String, payload: JsValue, subscriptionStatus: Int, enrolmentStatus: Int) =>
      Scenario(description) {

        Given("the STC api is up and running")
        When("user sends a POST request to subscribe")

        val futureResponse: Future[HttpResponse] =
          service.postStcRegistrationApi(payload)

        whenReady(futureResponse) { apiResponse =>
          Then(s"the response status code should be $subscriptionStatus")
          checkResponseStatus(apiResponse.status, subscriptionStatus)

          if (subscriptionStatus == Status.CREATED) {
            And("the response body should contain subscriptionId")
            val subscriptionId = PayloadValidator.validateSubscriptionResponse(apiResponse.body)
            log.info(s"Successfully retrieved subscriptionId: $subscriptionId")

            subscriptionId should not be empty

            // Perform enrolment immediately after successful subscription
            When("User sends a POST request to enrol")
            val enrolPayload = Json.obj(
              "subscriptionId" -> subscriptionId,
              "nino"           -> generateNino()
            )
            log.info(s"Enrolment payload: $enrolPayload")

            val futureEnrolResponse: Future[HttpResponse] =
              service.postStcRegistrationApiWithPayload(enrolPayload)

            whenReady(futureEnrolResponse) { enrolResponse =>
              Then("the response status code should be successful")
              enrolResponse.status shouldBe Status.NO_CONTENT
            }
          } else {
            And("the response body should contain error details")
            val errorResponse =
              PayloadValidator.validateErrorResponse(apiResponse.body)
            val errorMessages =
              PayloadValidator.extractErrorMessages(errorResponse)
            log.info(s"Error response validated - errors: $errorMessages")

            assert(errorResponse.obj.nonEmpty, "Error response should contain errors")
          }
        }
      }
    }
  }
}
