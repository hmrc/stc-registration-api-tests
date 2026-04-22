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

import scala.util.Random

object TestDataGenerator {

  private val random = new Random()

  def generateUTR(): String = {
    val firstPart = "99"
    val rest      = (1 to 8).map(_ => random.nextInt(10)).mkString
    firstPart + rest
  }

  def generateUTRLessAndMoreTenDigit(length: Int): String = {
    if (length <= 0) {
      throw new IllegalArgumentException("length must be greater than 0")
    }
    val first = (Random.nextInt(9) + 1).toString
    val rest  = List.fill(length - 1)(Random.nextInt(10)).mkString
    first + rest
  }

}
