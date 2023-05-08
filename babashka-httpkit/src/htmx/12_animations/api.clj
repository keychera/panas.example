(ns htmx.12-animations.api 
  (:require [selmer.parser :refer [render-file]]))

(defn animations-page []
  (render-file "htmx/12_animations/animations.html" {}))