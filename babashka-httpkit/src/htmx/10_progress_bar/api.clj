(ns htmx.10-progress-bar.api
  (:require [selmer.parser :refer [render-file]]))

(defn progress-bar-page []
  (render-file "htmx/10_progress_bar/progress-page.html" {}))

(def progress-out-of-5 (atom 0))

(defn progress-start []
  (reset! progress-out-of-5 0)
  (render-file "htmx/10_progress_bar/progress-bar.html" {:percentage 0}))

(defn job []
  (let [cur-progress (swap! progress-out-of-5 inc)]
    (render-file "htmx/10_progress_bar/progress-bar.html" {:percentage (* (/ cur-progress 5) 100)})))
