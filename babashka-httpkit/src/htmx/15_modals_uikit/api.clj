(ns htmx.15-modals-uikit.api 
  (:require [selmer.parser :refer [render-file]]))

(defn modals-uikit-page []
  (render-file "htmx/15_modals_uikit/main-page.html" {}))

(defn uikit-modal []
  (render-file "htmx/15_modals_uikit/modals-uikit.html" {}))
