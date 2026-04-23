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
import uk.gov.hmrc.api.helpers.JsonFileReader.readJsonFromTestResources
import uk.gov.hmrc.http.HttpReads.Implicits.*
import uk.gov.hmrc.http.client.HttpClientV2
import uk.gov.hmrc.http.{Authorization, HeaderCarrier, HttpResponse}

import java.net.URI
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class ServiceFactory @Inject() (client: HttpClientV2)(implicit ec: ExecutionContext) {

  val subscriptionUrl: String = TestEnvironment.url("stcSubscription")
  val enrolmentUrl: String    = TestEnvironment.url("stcEnrolment")

  def postStcRegistrationApi(fileName: String): Future[HttpResponse] = {
    val requestBody                = readJsonFromTestResources(fileName)
    implicit val hc: HeaderCarrier =
      HeaderCarrier(authorization = Some(Authorization("")))

    client.post(URI.create(s"$subscriptionUrl").toURL).withBody(requestBody).execute[HttpResponse]
  }

  def postStcRegistrationApiWithPayload(requestBody: JsValue): Future[HttpResponse] = {
    implicit val hc: HeaderCarrier =
      HeaderCarrier(authorization = Some(Authorization("")))

    client.post(URI.create(s"$enrolmentUrl").toURL).withBody(requestBody).execute[HttpResponse]
  }
}
