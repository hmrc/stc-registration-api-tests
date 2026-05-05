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

package uk.gov.hmrc.api.helpers

import org.scalatest.Assertions.*
import play.api.libs.json.*
import uk.gov.hmrc.api.models.{ErrorResponse, SubscriptionResponse}

object PayloadValidator {

  // Generic JSON validation method
  private def validateJson[T](jsonString: String)(implicit reads: Reads[T]): T = {
    val jsValue = Json.parse(jsonString)

    jsValue.validate[T] match {
      case JsSuccess(response, _) =>
        response

      case JsError(errors) =>
        val errorMessages = errors
          .map { case (path, validationErrors) =>
            s"At ${path.toJsonString}: ${validationErrors.map(_.message).mkString(", ")}"
          }
          .mkString("\n")

        fail(
          s"""JSON did not validate against schema:
             |$errorMessages
             |Full JSON:
             |${Json.prettyPrint(jsValue)}
           """.stripMargin
        )
    }
  }

  // --- SubscriptionResponse validation ---
  def validateSubscriptionResponse(responseBody: String): String = {
    val response = validateJson[SubscriptionResponse](responseBody)
    require(response.subscriptionId.nonEmpty, "subscriptionId is empty")
    response.subscriptionId
  }

  // --- Error Response validation ---
  def validateErrorResponse(responseBody: String): ErrorResponse = {
    val response = validateJson[ErrorResponse](responseBody)
    require(response.obj.nonEmpty, "Error response contains no errors")
    response
  }

  // Helper to extract error messages from error response
  def extractErrorMessages(errorResponse: ErrorResponse): Map[String, Seq[String]] =
    errorResponse.obj.map { case (field, errorDetails) =>
      field -> errorDetails.flatMap(_.msg)
    }
}
