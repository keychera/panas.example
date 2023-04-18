(ns htmx.08-infinite-scroll.api
  (:require [clojure.string :as str]
            [common :refer [query->vec]]
            [hiccup2.core :refer [html]]
            [htmx.03-click-to-load.api :as htmx-03]
            [selmer.parser :refer [render-file]]))

(defn gen-infinite-contacts [page]
  (let [contacts (htmx-03/gen-contacts page)
        last-idx (- (count contacts) 1)]
    (->> contacts
         (map-indexed (fn [idx {:keys [name email id]}]
                        (str (html
                              [:tr
                               (when (= idx last-idx) {:hx-get (str "/htmx/contacts-infinite?page=" (inc page))
                                                       :hx-trigger "revealed" :hx-swap "afterend"
                                                       :hx-indicator "#ind"})
                               [:td name] [:td email] [:td id]]))))
         (str/join))))



(defn infinite-contacts [req]
  (Thread/sleep 1000)
  (let [query (->> req :query-string query->vec (into {}))
        page (Integer/parseInt (get query "page"))]
    (gen-infinite-contacts page)))

(defn infinite-page []
  (render-file "htmx/08_infinite_scroll/infinite-page.html" {:contacts (gen-infinite-contacts 1)}))
