(ns htmx.17-modal-custom.api 
  (:require [selmer.parser :refer [render-file]]))

(defn modal-custom-page []
  (render-file "htmx/17_modal_custom/main-page.html" {}))

(defn custom-modal []
  (render-file "htmx/17_modal_custom/modal-custom.html" {}))