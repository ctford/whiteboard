(ns whiteboard.core
  (:require [yada.yada :as yada]))

(defn start [port]
  (yada/listener
    ["/"
     [["hello" (yada/resource {:produces "text/plain"
                               :response "World!"})]
      [true (yada/as-resource nil)]]]
    {:port port}))

(comment

  (def server (start 3000))

  ((:close server))

 )
