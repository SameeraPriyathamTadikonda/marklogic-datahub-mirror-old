(:
  Copyright 2012-2016 MarkLogic Corporation

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
:)
xquery version "1.0-ml";

module namespace debug = "http://marklogic.com/hub-in-a-box/debug-lib";

declare option xdmp:mapping "false";

declare function debug:on() as xs:boolean
{
  fn:true()
};

declare function debug:log($items)
{
  xdmp:log($items)
};

(: TODO: add a switch to turn this on and off :)
declare function debug:dump-env()
{
  xdmp:log((
    "",
    "REQUEST DETAILS:",
    "  " || xdmp:get-request-path(),
    "",
    "  [Headers]",
    for $h in xdmp:get-request-header-names()
    return
      "    " || $h || " => " || xdmp:get-request-header($h),
    "",
    "  [Request Params]",
    for $p in xdmp:get-request-field-names()
    return
      "    " || $p || " => " || xdmp:get-request-field($p),
    let $body := xdmp:get-request-body()
    return
      if (fn:exists($body)) then
      (
        "",
        "  [Body]",
        $body
      )
      else (),
    "",
    ""
  ))
};