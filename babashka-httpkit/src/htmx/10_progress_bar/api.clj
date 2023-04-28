(ns htmx.10-progress-bar.api 
  (:require [selmer.parser :refer [render-file]]))

(defn progress-bar-page []
  (render-file "htmx/10_progress_bar/progress-page.html" {}))