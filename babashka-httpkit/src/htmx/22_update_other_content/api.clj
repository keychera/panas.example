(ns htmx.22-update-other-content.api
  (:require [common :refer [payload->map]]
            [hiccup2.core :refer [html]]
            [selmer.parser :refer [render-file]]))

(defn main-page []
  (render-file "htmx/22_update_other_content/main-page.html" {:i 1}))

(defonce contacts (atom []))

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

(defmulti add-contact #(->> % :i (str "solution-") keyword))

(defmethod add-contact :solution-1 [{:keys [req]}]
  (let [{:strs [name email]} (payload->map req)]
    (swap! contacts conj {:name name :email email})
    {:body (str (html [:div#table-and-form
                       [:h2 "Contacts"]
                       [:table.table
                        [:thead [:tr [:th "Name"] [:th "Email"] [:th]]]
                        [:tbody#contacts-table
                         (for [{:keys [name email]} @contacts]
                           [:tr [:td name] [:td email]])]]
                       [:h2 "Add A Contact"]
                       [:form {:hx-post "/htmx/update-other-content/solution/1/contacts"
                               :hx-target "#table-and-form"}
                        [:label "Name" [:input {:name "name" :type "text"}]]
                        [:label "Email" [:input {:name "email" :type "email"}]]
                        [:input {:type "submit" :value "Submit"}]]]))}))

(defmethod add-contact :solution-2 [{:keys [req]}]
  (let [{:strs [name email]} (payload->map req)]
    (swap! contacts conj {:name name :email email})
    {:body (str (html [:tbody {:hx-swap-oob "beforeend:#contacts-table"}
                       [:tr [:td name] [:td email]]])
                (html [:form {:hx-post "/htmx/update-other-content/solution/2/contacts"}
                       [:label "Name" [:input {:name "name" :type "text"}]]
                       [:label "Email" [:input {:name "email" :type "email"}]]
                       [:input {:type "submit" :value "Submit"}]]))}))

(defn contacts-table []
  (let [current-contacts @contacts
        not-empty? (not= (count current-contacts) 0)]
    (if not-empty?
      (apply str (for [{:keys [name email]} current-contacts]
                   (html [:tr [:td name] [:td email]])))
      (str (html [:tr [:td.grayed "No entry yet..."]])))))

(defmethod add-contact :solution-3 [{:keys [req]}]
  (let [{:strs [name email]} (payload->map req)]
    (swap! contacts conj {:name name :email email})
    {:headers {"HX-Trigger" "newContact"}
     :body (str (html [:form {:hx-post "/htmx/update-other-content/solution/2/contacts"}
                       [:label "Name" [:input {:name "name" :type "text"}]]
                       [:label "Email" [:input {:name "email" :type "email"}]]
                       [:input {:type "submit" :value "Submit"}]]))}))