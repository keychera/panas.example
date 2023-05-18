(ns htmx.14-dialogs.api
  (:require [hiccup2.core :refer [html]]
            [selmer.parser :refer [render-file]]))

(defn dialogs-page []
  (render-file "htmx/14_dialogs/dialogs.html" {}))

(defn submit [req]
  (let [response (get (:headers req) "hx-prompt")]
    (str (html [:div "User entered " [:i response]]))))