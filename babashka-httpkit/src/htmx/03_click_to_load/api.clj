(ns htmx.03-click-to-load.api
  (:require [common :refer [query->vec]]
            [selmer.parser :refer [render-file]]))

(defn gen-contacts [page]
  (->> (range 10)
       (map (fn [idx]
              {:name "Agent Smith"
               :email (str "void" (+ (* page 10) idx) "@null.org")
               :id (apply str (repeatedly 15 #(rand-nth "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789")))}))))

(defn contacts [page]
  (render-file "htmx/03_click_to_load/load-agent.html"
               {:contacts (gen-contacts page)
                :next-page (+ page 1)}))

(defn request-contacts [req]
  (let [query (->> req :query-string query->vec (into {}))
        page (Integer/parseInt (get query "page"))]
    (Thread/sleep 1000) ;; so the loading shows
    (contacts page)))

(defn click-to-load []
  (render-file "htmx/03_click_to_load/load-rows.html"
               {:contacts-rows (contacts 1)}))