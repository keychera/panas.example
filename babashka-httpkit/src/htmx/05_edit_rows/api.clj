(ns htmx.05-edit-rows.api
  (:require [clojure.string :as str]
            [common :refer [payload->map]]
            [htmx.04-delete-row.api :refer [rows]]
            [selmer.parser :refer [render-file]]))

;; using rows from htmx.04

(defn row-html [contact editable?]
  (let [template (if editable?
                   "htmx/05_edit_rows/editable-row.html"
                   "htmx/05_edit_rows/row.html")]
    (render-file template contact)))

(defn row [id & {:keys [editable?] :or {editable? false}}]
  (let [contact (-> @rows (get (Integer/parseInt id))
                    (select-keys [:name :email])
                    (assoc :id id))]
    (row-html contact editable?)))

(defn put-row [id req]
  (let [{:strs [name email] :as edited-contact} (payload->map req)]
    (println "editing a contact to =>" edited-contact)
    (swap! rows update (Integer/parseInt id) assoc :name name :email email)
    (row id)))

(defn edit-rows-page []
  (let [contacts (->> @rows
                      (map (fn [[id {:keys [name email]}]]
                             {:id id :name name :email email}))
                      (map #(row-html % false))
                      str/join)]
    (render-file "htmx/05_edit_rows/edit-rows.html"
                 {:contacts contacts})))

