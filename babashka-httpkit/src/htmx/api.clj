(ns htmx.api
  (:require [clojure.core.match :refer [match]] 
            [selmer.parser :refer [render-file]]))

(defn htmx-index []
  {:body (render-file "htmx/index.html" {})})

(defn router [req example] 
  (let [verb (:request-method req)]
    (match [verb example] 
      [:get "main"] (htmx-index) 
      :else {:status 404 :body "htmx example not found here"})))