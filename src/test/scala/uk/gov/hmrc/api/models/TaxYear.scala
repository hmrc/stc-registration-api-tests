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

package uk.gov.hmrc.api.models

import play.api.libs.json.*

case class TaxYear(value: String) extends AnyVal

object TaxYear {
  // Regex: "YYYY-YYYY+1", both parts must be 4 digits
  private val taxYearRegex = """^([0-9]{4})-([0-9]{4})$""".r

  implicit val reads: Reads[TaxYear] = Reads {
    case JsString(s) =>
      s match {
        case taxYearRegex(startStr, endStr) =>
          val startYear = startStr.toInt
          val endYear   = endStr.toInt
          if (endYear == startYear + 1) JsSuccess(TaxYear(s))
          else JsError(s"Invalid tax year: $s (second year must be first year + 1)")
        case _                              => JsError(s"Invalid tax year format: $s (must be YYYY-YYYY+1)")
      }
    case other       => JsError(s"Expected string for TaxYear, got: ${other.getClass.getSimpleName}")
  }

  implicit val writes: Writes[TaxYear] = Writes(ty => JsString(ty.value))
  implicit val format: Format[TaxYear] = Format(reads, writes)
}
