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

package uk.gov.hmrc.api.services

import play.api.libs.json.JsValue
import play.api.libs.ws.WSBodyWritables.*
import uk.gov.hmrc.api.conf.TestEnvironment
import uk.gov.hmrc.apitestrunner.util.ApiLogger.log
import uk.gov.hmrc.http.HttpReads.Implicits.*
import uk.gov.hmrc.http.client.HttpClientV2
import uk.gov.hmrc.http.{Authorization, HeaderCarrier, HttpResponse}

import java.net.URI
import java.util.UUID
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class ServiceFactory @Inject() (client: HttpClientV2)(implicit ec: ExecutionContext) {

  // API endpoint URLs
  private val subscriptionUrl: String = TestEnvironment.url("stcSubscription")
  private val enrolmentUrl: String    = TestEnvironment.url("stcEnrolment")

  // Header constants
  private object HeaderKeys {
    val CorrelationId = "correlation-id"
    val ContentType   = "Content-Type"
    val JsonMimeType  = "application/json"
  }

  private def createHeaderCarrier(): HeaderCarrier = {
    val correlationId = UUID.randomUUID().toString

    val customHeaders = Seq(
      HeaderKeys.CorrelationId -> correlationId,
      HeaderKeys.ContentType   -> HeaderKeys.JsonMimeType
    )

    HeaderCarrier(
      authorization = Some(Authorization("")),
      extraHeaders = customHeaders
    )
  }

  def postStcRegistrationApi(requestBody: JsValue): Future[HttpResponse] =
    executeHttpRequest(subscriptionUrl, requestBody, "subscription")

  def postStcRegistrationApiWithPayload(requestBody: JsValue): Future[HttpResponse] =
    executeHttpRequest(enrolmentUrl, requestBody, "enrolment")

  private def executeHttpRequest(url: String, requestBody: JsValue, requestType: String)(implicit
    hc: HeaderCarrier = createHeaderCarrier()
  ): Future[HttpResponse] = {
    log.info(s"Sending $requestType request with payload: $requestBody")

    client.post(URI.create(url).toURL).withBody(requestBody).execute[HttpResponse]
  }
}
