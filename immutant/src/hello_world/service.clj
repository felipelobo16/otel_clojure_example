(ns hello-world.service
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as body-params]
            [ring.util.response :as ring-resp]
            [steffan-westcott.clj-otel.api.trace.http :as trace-http]
            [steffan-westcott.clj-otel.api.trace.span :as span]))

(defn hello-world
  [request]
  (span/with-span! {:name "test"})
  (let [name (get-in request [:params :name] "World")]
    (ring-resp/response (str "Hello " name "!\n"))))

(def routes
  `[[["/"
      ["/hello" {:get hello-world}]]]])

(def service {:env :prod
              ::http/routes routes
              ::http/resource-path "/public"
              ::http/host "0.0.0.0"
              ::http/type :immutant
              ::http/port 8080})
