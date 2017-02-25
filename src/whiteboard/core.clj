(ns whiteboard.core
  (:require [yada.yada :as yada]))

(def servers (atom []))

(defn start-one! []
  (let [server (yada/listener
                 ["/"
                  [["hello" (yada/resource {:produces "text/plain"
                                            :response "World!"})]
                   [true (yada/as-resource nil)]]])]
    (swap! servers conj server)))

(defn shut-down! []
  (doseq [{shut-down-one! :close} @servers]
    (shut-down-one!))
  (reset! servers {}))

(comment
  (shut-down!)
  (start-one!))
