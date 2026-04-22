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

import scala.util.matching.Regex

case class Amount(value: BigDecimal) extends AnyVal {
  def +(other: Amount): Amount = Amount(this.value + other.value)
  def -(other: Amount): Amount = Amount(this.value - other.value)
  def *(other: Amount): Amount = Amount(this.value * other.value)
  def /(other: Amount): Amount = Amount(this.value / other.value)

  // for comparisons
  def <(other: Amount): Boolean  = this.value < other.value
  def <=(other: Amount): Boolean = this.value <= other.value
  def >(other: Amount): Boolean  = this.value > other.value
  def >=(other: Amount): Boolean = this.value >= other.value

  override def toString: String = value.toString
}

object Amount {
  private val amountRegex: Regex = """^-?\d{1,13}(\.\d{1,2})?$""".r

  implicit val reads: Reads[Amount] = Reads[Amount] {
    case JsNumber(n) =>
      val str = n.toString()
      if (amountRegex.matches(str)) JsSuccess(Amount(n))
      else JsError(s"Invalid amount format: $str (must be +/- 13 digits, 2 decimals)")

    case JsString(s) =>
      if (amountRegex.matches(s)) JsSuccess(Amount(BigDecimal(s)))
      else JsError(s"Invalid amount format: $s (must be +/- 13 digits, 2 decimals)")

    case other =>
      JsError(s"Expected number or string for amount, got: ${other.getClass.getSimpleName}")
  }

  implicit val writes: Writes[Amount] = Writes[Amount](a => JsNumber(a.value))
  implicit val format: Format[Amount] = Format(reads, writes)
}
