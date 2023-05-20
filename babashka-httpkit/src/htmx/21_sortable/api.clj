(ns htmx.21-sortable.api 
  (:require [common :refer [payload->vec]]
            [selmer.parser :refer [render-file]]))

(defn sortable-page []
  (render-file "htmx/21_sortable/sortable-page.html" {}))

(defn items [req] (payload->vec req))