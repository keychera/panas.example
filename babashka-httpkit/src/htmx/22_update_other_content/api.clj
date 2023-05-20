(ns htmx.22-update-other-content.api
  (:require [common :refer [payload->map]]
            [hiccup2.core :refer [html]]
            [selmer.parser :refer [render-file]]))

(defn main-page []
  (render-file "htmx/22_update_other_content/main-page.html" {:i 1}))

(def contacts (atom []))

(defn solution [partial-req? i]
  (if partial-req?
    (try (render-file (str "htmx/22_update_other_content/solution-" i ".html")
                      (let [current-contacts @contacts]
                        {:length (count current-contacts)
                         :contacts current-contacts}))
         (catch Throwable e
           (println (:cause (Throwable->map e)))
           "file not found"))
    (render-file "htmx/22_update_other_content/main-page.html" {:i i})))

(defmulti add-contact :i)

(defmethod add-contact "solution-1" [{:keys [req]}]
  (let [{:strs [name email]} (payload->map req)]
    (swap! contacts conj {:name name :email email})
    (str (html [:div#table-and-form
                [:h2 "Contacts"]
                [:table.table
                 [:thead [:tr [:th "Name"] [:th "Email"] [:th]]]
                 [:tbody#contacts-table
                  (for [{:keys [name email]} @contacts]
                    [:tr [:td name] [:td email]])]]
                [:h2 "Add A Contact"]
                [:form {:hx-post "/htmx/update-other-content/solution/1/contacts"
                        :hx-target "#table-and-form"}
                 [:label "Name" [:input {:name "name" :type "text" :required true}]]
                 [:label "Email" [:input {:name "email" :type "email" :required true}]]
                 [:input {:type "submit" :value "Submit"}]]]))))
