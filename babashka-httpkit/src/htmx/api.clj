(ns htmx.api
  (:require [selmer.parser :refer [render-file]]))

(defn htmx-index []
  {:body (render-file "htmx/index.html" {})})

(defn router [example] 
  (case example
    "main" (htmx-index)
    {:status 404 :body "htmx examples not found here"}))