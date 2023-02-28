(ns htmx.02-bulk-edit.api
  (:require [selmer.parser :refer [render-file]]))

(def rows (atom [{:name "Eive Chera" :email "eive.chera@reality.me" :active? false}
                 {:name "Joe Smith" :email "joe@smith.org" :active? true}
                 {:name "Angie MacDowell" :email "angie@macdowell.org" :active? true}
                 {:name "Fuqua Tarkenton" :email "fuqua@tarkenton.org" :active? true}
                 {:name "Kim Yee" :email "kim@yee.org" :active? false}]))

(defn with-active-str [{active? :active? :as row}]
  (assoc row :status (if active? "Active" "Inactive")))

(defn rows-body []
  (render-file "htmx/02_bulk_edit/rows.html"
               {:rows (->> @rows (map with-active-str))}))

(defn bulk-edit []
  (render-file "htmx/02_bulk_edit/bulk-edit.html" {:rows-body (rows-body)}))

(defn activate [{:as row}]
  (print row))

(defn deactivate [{:as row}]
  (print row))