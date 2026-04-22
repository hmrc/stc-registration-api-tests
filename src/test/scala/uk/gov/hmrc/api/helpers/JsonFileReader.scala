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

import play.api.libs.json.JsValue
import scala.io.Source

object JsonFileReader {

  /**
   * Reads a JSON file from the test resources directory and returns it as a String
   *
   * @param fileName - path relative to resources folder (e.g., "request/IndRegSubscription200.json")
   * @return JSON content as String
   */
  def readJsonFile(fileName: String): String = {
    val resourcePath = s"$fileName"
    val source       = Source.fromResource(resourcePath)
    try {
      source.mkString
    } finally {
      source.close()
    }
  }

  /**
   * Reads a JSON file and parses it directly to JsValue
   *
   * @param fileName - path relative to resources folder
   * @return Parsed JSON as JsValue
   */
  def readJsonFileAsJsValue(fileName: String): JsValue = {
    import play.api.libs.json.Json
    val jsonString = readJsonFile(fileName)
    Json.parse(jsonString)
  }
}

