(ns htmx.09-active-search.api 
  (:require [selmer.parser :refer [render-file]]))

(defn active-search []
  (render-file "htmx/09_active_search/active-page.html" {}))