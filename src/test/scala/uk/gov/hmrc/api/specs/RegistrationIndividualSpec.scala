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

import org.scalactic.Prettifier.default
import org.scalatest.concurrent.ScalaFutures.*
import org.scalatest.prop.TableDrivenPropertyChecks.forAll
import org.scalatest.prop.Tables.Table
import uk.gov.hmrc.api.helpers.BaseSpec
import uk.gov.hmrc.http.HttpResponse

import scala.concurrent.Future

class RegistrationIndividualSpec extends BaseSpec {

  Feature("Validate Individual Registration User Conditions") {

    val testCases = Table(
      ("description", "input", "expectedStatus"),
      ("Success - All mandatory fields with correct values", "IndRegSubscription200", 200)
//      ("Success - Missing Optional fields", "", 200),
//      ("Error - Missing Name Field", "",400)
    )

    forAll(testCases) { (description, fileName, expectedStatus) =>
      Scenario(description) {
        Given("the STC api is up and running")

        When("user sends a POST request to subscribe")

        val futureResponse: Future[HttpResponse] = for {
          apiResponse <- service.getStcRegistrationApi(fileName)
        } yield apiResponse

        whenReady(futureResponse) { apiResponse =>
          Then(s"the response status code should be $expectedStatus")
//          checkResponseStatus(apiResponse.status, expectedStatus)
          And("the response body should have the expected content")
          checkSALiabilitiesResponse(apiResponse.body, expectedStatus)
        }
      }
    }
  }
}
