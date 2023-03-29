

(ns hello-world.server
  (:gen-class) ; for -main method in uberjar
  (:require [io.pedestal.http :as server]
            [hello-world.service :as service]
            [io.pedestal.http.route :as route]))

(defonce runnable-service (server/create-server service/service))

(defn run-dev
  "The entry-point for 'lein run-dev'"
  [& args]
  (println "\nCreating your [DEV] server...")
  (-> service/service ;; start with production configuration
      (merge {:env :dev
              ::server/join? false
              ::server/routes #(route/expand-routes (deref #'service/routes))
              ::server/allowed-origins {:creds true :allowed-origins (constantly true)}})
      server/default-interceptors
      server/dev-interceptors
      server/create-server
      server/start))

(defn -main
  "The entry-point for 'lein run'"
  [& args]
  (println "\nCreating your server...")
  (server/start runnable-service))

