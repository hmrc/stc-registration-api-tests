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

import play.api.libs.json.{Json, OFormat}

import java.time.LocalDate

case class BalanceDetails(
  totalOverdueBalance: Amount,
  totalPayableBalance: Amount,
  earliestPayableDueDate: Option[LocalDate],
  totalPendingBalance: Amount,
  earliestPendingDueDate: Option[LocalDate],
  totalBalance: Amount,
  totalCreditAvailable: Amount,
  codedOutDetail: Option[Seq[CodedOutDetail]]
) {

  def validate: Either[String, BalanceDetails] = {
    val today        = LocalDate.now()
    val windowDays   = 30L
    val boundaryDate = today.plusDays(windowDays)

    def validateDate(balance: Amount, date: Option[LocalDate], name: String): Option[String] =
      (balance.value, date) match {
        case (v, d) if v > 0 && (d.isEmpty || d.exists(dv => !dv.isAfter(today))) =>
          Some(s"$name must be present and after today since balance is positive")
        case (0, Some(_))                                                         =>
          Some(s"$name must be absent when balance is zero")
        case _                                                                    => None
      }

    def validatePayableWindow(balance: Amount, date: Option[LocalDate]): Option[String] =
      (balance.value, date) match {
        case (v, Some(d)) if v > 0 && d.isAfter(boundaryDate) =>
          Some(s"earliestPayableDueDate must be within $windowDays days from today")
        case _                                                => None
      }

    def validatePendingWindow(balance: Amount, date: Option[LocalDate]): Option[String] =
      (balance.value, date) match {
        case (v, Some(d)) if v > 0 && !d.isAfter(boundaryDate) =>
          Some(s"earliestPendingDueDate must be more than $windowDays days from today")
        case _                                                 => None
      }

    val errors = List(
      validateDate(totalPayableBalance, earliestPayableDueDate, "earliestPayableDueDate"),
      validateDate(totalPendingBalance, earliestPendingDueDate, "earliestPendingDueDate"),
      validatePayableWindow(totalPayableBalance, earliestPayableDueDate),
      validatePendingWindow(totalPendingBalance, earliestPendingDueDate)
    ).flatten

    if (errors.isEmpty) Right(this) else Left(errors.mkString("; "))
  }
}

object BalanceDetails {
  implicit val format: OFormat[BalanceDetails] = Json.format[BalanceDetails]
}
