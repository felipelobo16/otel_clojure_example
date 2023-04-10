; Copyright 2013 Relevance, Inc.
; Copyright 2014-2022 Cognitect, Inc.

; The use and distribution terms for this software are covered by the
; Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0)
; which can be found in the file epl-v10.html at the root of this distribution.
;
; By using this software in any fashion, you are agreeing to be bound by
; the terms of this license.
;
; You must not remove this notice, or any other, from this software.

(defproject jetty-web-sockets "0.5.5"
  :description "Sample of web sockets with Jetty"
  :url "http://pedestal.io/samples/index"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [io.pedestal/pedestal.service "0.5.10" :exclusions [org.clojure/core.async
                                                                     org.clojure/tools.reader
                                                                     org.clojure/tools.analyzer.jvm]]
                 [org.clojure/core.async "1.5.648"]
                 [io.pedestal/pedestal.jetty "0.5.10"]
                 [ch.qos.logback/logback-classic "1.2.10" :exclusions [org.slf4j/slf4j-api]]
                 [org.slf4j/jul-to-slf4j "1.7.35"]
                 [org.slf4j/jcl-over-slf4j "1.7.35"]
                 [org.slf4j/log4j-over-slf4j "1.7.35"]
                 [org.clojure/tools.trace "0.7.11"]
                 [com.amperity/ken "1.2.0"]
                 [mvxcvi/puget "1.3.4"]
                 [io.pedestal/pedestal.interceptor "0.5.10"]
                 [com.github.steffan-westcott/clj-otel-api "0.2.0"]]

  :min-lein-version "2.0.0"
  :resource-paths ["config", "resources"]
  :jvm-opts ["-javaagent:/usr/src/app/otel/opentelemetry-javaagent.jar"]
  :pedantic? :abort
  :profiles {:dev {:aliases {"run-dev" ["trampoline" "run" "-m" "jetty-web-sockets.server/run-dev"]}}
             :uberjar {:aot [jetty-web-sockets.server]}}
  :main ^{:skip-aot true} jetty-web-sockets.server)
