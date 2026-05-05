/*
 * Copyright 2026 HM Revenue & Customs
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

package uk.gov.hmrc.api.helpers.builders

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.api.models.SubscriptionRequest

object SubscriptionRequestBuilder {

  private val base = SubscriptionRequest()

  def valid: JsValue =
    Json.toJson(base)

  def missingContactName: JsValue =
    Json.toJson(base.copy(contactName = None))

  def missingSafeId: JsValue =
    Json.toJson(base.copy(safeId = ""))

  def withoutOptionalFields: JsValue =
    Json.toJson(
      base.copy(
        addressLine2 = None,
        addressLine3 = None,
        mobileNumber = None
      )
    )

  def invalidEmail: JsValue =
    Json.toJson(base.copy(email = Some("invalid-email")))

  def invalidPostCode: JsValue =
    Json.toJson(base.copy(postcode = Some("123")))

  def withOverrides(
    safeId: String = base.safeId,
    contactName: Option[String] = base.contactName,
    email: Option[String] = base.email,
    postCode: Option[String] = base.postcode
  ): JsValue =
    Json.toJson(
      base.copy(
        safeId = safeId,
        contactName = contactName,
        email = email,
        postcode = postCode
      )
    )
}
