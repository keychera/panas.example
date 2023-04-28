(ns htmx.11-value-select.api
  (:require [clojure.string :as str]
            [common :refer [query->vec]]
            [hiccup2.core :refer [html]]
            [selmer.parser :refer [render-file]]))

(defn value-select-page []
  (render-file "htmx/11_value_select/value-page.html" {}))

(def model-data {"audi" ["A1", "A4", "A6"]
                 "toyota" ["Landcruiser", "Tacoma", "Yaris"]
                 "bmw" ["325i", "325ix", "X5"]})

(defn models [req]
  (let [query (->> req :query-string query->vec (into {}))
        make (.toLowerCase (get query "make"))]
    (->> (get model-data make)
         (map (fn [m] (str (html [:option {:value m} m]))))
         (str/join))))

