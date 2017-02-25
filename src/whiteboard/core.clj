(ns whiteboard.core
  (:require [yada.yada :as yada]
            [schema.core :as schema]))

(def fsm
  {"locked" {"unlock" "opened"}
   "closed" {"lock"   "locked"
             "open"   "opened"}
   "opened" {"close"  "closed"}})

(def servers (atom []))

(defn respond [state context]
  (when-let [action (get-in context [:parameters :query :action])]
    (let [allowed-actions (fsm @state)]
      (swap! state (partial get allowed-actions action))))
  @state)

(defn start-one! []
  (let [state (atom "locked")
        server (yada/listener
                 ["/" (yada/resource {:produces "text/plain"
                                      :methods {:get
                                                {:parameters
                                                 {:query {(schema/optional-key :action) String}}}}
                                      :response (partial respond state)})])]
    (swap! servers conj server)))

(defn shut-down! []
  (doseq [{shut-down-one! :close} @servers]
    (shut-down-one!))
  (reset! servers []))

(comment
  (shut-down!)
  (start-one!))
