(ns jetty-web-sockets.service
  (:require [io.pedestal.http :as http]
            [io.pedestal.log :as log]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as body-params]
            [ring.util.response :as ring-resp]
            [clojure.core.async :as async]
            [io.pedestal.http.jetty.websockets :as ws]
            [steffan-westcott.clj-otel.api.trace.http :as trace-http]
            [steffan-westcott.clj-otel.api.trace.span :as span])
  (:import [org.eclipse.jetty.websocket.api Session]))

(defn about-page
  [request]
  (ring-resp/response (format "Clojure %s - served from %s"
                              (clojure-version)
                              (route/url-for ::about-page))))

(defn home-page
  [request]
  (ring-resp/response "Hello World!"))

(def routes
  `[[["/" {:get home-page}
      ^:interceptors [(body-params/body-params) http/html-body]
      ["/about" {:get about-page}]]]])

(def ws-clients (atom {}))

(defn new-ws-client
  [ws-session send-ch]
  (async/put! send-ch "This will be a text message")
  (swap! ws-clients assoc ws-session send-ch))

(defn send-and-close! []
  (let [[ws-session send-ch] (first @ws-clients)]
    (async/put! send-ch "A message from the server")
    (async/close! send-ch)
    (swap! ws-clients dissoc ws-session)))

(defn send-message-to-all!
  [message]
  (doseq [[^Session session channel] @ws-clients]
    (when (.isOpen session)
      (async/put! channel message))))

(def ws-paths
  {"/ws" {:on-connect (ws/start-ws-connection new-ws-client)
          :on-text (fn [msg] (log/info :msg (str "A client sent - " msg)))
          :on-binary (fn [payload offset length] (log/info :msg "Binary Message!" :bytes payload))
          :on-error (fn [t] (log/error :msg "WS Error happened" :exception t))
          :on-close (fn [num-code reason-text]
                      (log/info :msg "WS Closed:" :reason reason-text))}})

(def service {:env :prod
              ::http/routes routes
              ::http/resource-path "/public"
              ::http/type :jetty
              ::http/container-options {:context-configurator #(ws/add-ws-endpoints % ws-paths)}
              ::http/host "0.0.0.0"
              ::http/port 8080})

