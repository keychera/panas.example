(ns htmx.23-confirm.api 
  (:require [selmer.parser :refer [render-file]]))

(defn confirm-page []
  (render-file "htmx/23_confirm/main-page.html" {}))