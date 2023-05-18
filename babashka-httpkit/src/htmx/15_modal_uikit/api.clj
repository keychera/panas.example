(ns htmx.15-modal-uikit.api 
  (:require [selmer.parser :refer [render-file]]))

;; uikit csss is put directly in htmx/index.html
(defn modal-uikit-page []
  (render-file "htmx/15_modal_uikit/main-page.html" {}))

(defn uikit-modal []
  (render-file "htmx/15_modal_uikit/modal-uikit.html" {}))
