(ns htmx.04-delete-row.api
  (:require [selmer.parser :refer [render-file]]))

(def rows (atom {1 {:name "Eive Chera" :email "eive.chera@reality.me" :active? false}
                 2 {:name "Joe Smith" :email "joe@smith.org" :active? true}
                 3 {:name "Angie MacDowell" :email "angie@macdowell.org" :active? true}
                 4 {:name "Fuqua Tarkenton" :email "fuqua@tarkenton.org" :active? true}
                 5 {:name "Kim Yee" :email "kim@yee.org" :active? false}}))

(defn delete-contact [idx]
  (swap! rows dissoc (Integer/parseInt idx))
  "")

(defn delete-row-page []
  (let [rows-html (->> @rows
                       (map (fn [[id {:keys [name email active?]}]]
                              {:id id :name name :email email
                               :status (if active? "Active" "Inactive")})))]
    (render-file "htmx/04_delete_row/delete-row.html"
                 {:rows rows-html})))