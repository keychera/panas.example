(ns htmx.19-5-tabs-like-these.api
  (:require [hiccup2.core :refer [html]]
            [htmx.18-tabs-hateoas.api :as example-18]
            [selmer.parser :refer [render-file]]))

(defn tabs-like-these []
  (render-file "htmx/19_5_tabs_like_these/main-page.html" {:i 1}))

(defn tab-like-this [partial-req? i]
  (if partial-req?
    (str (html [:p.tab-content (get example-18/content (dec (Integer/parseInt i)))]))
    (render-file "htmx/19_5_tabs_like_these/main-page.html" {:i i})))