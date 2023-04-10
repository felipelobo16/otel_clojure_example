(ns jetty-web-sockets.server
  (:gen-class) ; for -main method in uberjar
  (:require [io.pedestal.http :as server]
            [jetty-web-sockets.service :as service]
            [io.pedestal.http.route :as route]))

(defonce runnable-service (server/create-server service/service-map))

(defn -main
  "The entry-point for 'lein run'"
  [& args]
  (println "\nCreating your server...")
  (service/server)


