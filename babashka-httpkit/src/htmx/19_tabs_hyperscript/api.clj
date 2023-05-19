(ns htmx.19-tabs-hyperscript.api 
  (:require [hiccup2.core :refer [html]]
            [htmx.18-tabs-hateoas.api :as example-18]
            [selmer.parser :refer [render-file]]))

(defn tabs-hyperscript-page []
  (render-file "htmx/19_tabs_hyperscript/main-page.html" {}))

(defn tab-contents [i]
  (str (html [:p.tab-content (get example-18/content (dec i))])))