(ns htmx.12-animations.api
  (:require [hiccup2.core :refer [html]]
            [selmer.parser :refer [render-file]]))

(defn animations-page []
  (render-file "htmx/12_animations/animations.html" {}))

(def counter (atom 0))
(def colors ["red" "blue" "green"])
(defn get-color [_]
  (let [color (get colors (mod (swap! counter inc) (count colors)))]
    (str (html [:div#color-demo.smooth {:style (str "color:" color)
                                        :hx-get "/htmx/colors" :hx-swap "outerHTML" :hx-trigger "every 1s"}
                "Color Swap Demo"]))))

(defn fade-in [_]
  (str (html [:button#fade-me-in {:hx-post "/fade_in_demo" :hx-swap "outerHTML settle:1s"} "Fade Me In"])))

(defn new-content [_]
  (str (html [:div
              [:h4 "New Content"]
              [:button {:hx-get "/htmx/initial-content"
                        :hx-swap "innerHTML transition:true"
                        :hx-target "closest div"}
               "Restore It!"]])))

(defn initial-content [_]
  (str (html [:div
              [:h4 "Initial Content"]
              [:button {:hx-get "/htmx/new-content"
                        :hx-swap "innerHTML transition:true"
                        :hx-target "closest div"}
               "Swap It!"]])))