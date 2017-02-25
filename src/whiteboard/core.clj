(ns whiteboard.core
  (:require [yada.yada :as yada]))

(def servers (atom []))

(defn start-one! []
  (let [server (yada/listener
                 ["/" (yada/resource {:produces "text/plain"
                                      :response "Hello World!"})])]
    (swap! servers conj server)))

(defn shut-down! []
  (doseq [{shut-down-one! :close} @servers]
    (shut-down-one!))
  (reset! servers {}))

(comment
  (shut-down!)
  (start-one!))
