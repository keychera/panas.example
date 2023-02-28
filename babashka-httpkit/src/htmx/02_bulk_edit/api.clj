(ns htmx.02-bulk-edit.api
  (:require [common :refer [payload->vec]]
            [selmer.parser :refer [render-file]]))

(def rows (atom [{:name "Eive Chera" :email "eive.chera@reality.me" :active? false}
                 {:name "Joe Smith" :email "joe@smith.org" :active? true}
                 {:name "Angie MacDowell" :email "angie@macdowell.org" :active? true}
                 {:name "Fuqua Tarkenton" :email "fuqua@tarkenton.org" :active? true}
                 {:name "Kim Yee" :email "kim@yee.org" :active? false}]))

(defn with-active-str [{active? :active? :as row}]
  (assoc row :status (if active? "Active" "Inactive")))

(defn with-toggle-class-fn [ids activate?]
  (fn [idx row]
    (if-not (some #(= idx %) ids) row
            (assoc row :toggle-class (if activate? "activate" "deactivate")))))

(defn rows-body
  ([] (render-file "htmx/02_bulk_edit/rows.html"
                   {:rows (->> @rows (map with-active-str))}))
  ([ids activate?]
   (render-file "htmx/02_bulk_edit/rows.html"
                {:rows (->> @rows
                            (map with-active-str)
                            (map-indexed (with-toggle-class-fn ids activate?)))})))

(defn bulk-edit []
  (render-file "htmx/02_bulk_edit/bulk-edit.html" {:rows-body (rows-body)}))

(defn toggle-active [req toggle]
  (let [ids (->> (payload->vec req) (map (fn [[_ id]] (Integer/parseInt id))))]
    (doseq [id ids]
      (swap! rows #(update % id (fn [old] (assoc old :active? toggle)))))
    (rows-body ids toggle)))
