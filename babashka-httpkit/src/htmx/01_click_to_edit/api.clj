(ns htmx.01-click-to-edit.api 
  (:require [common :refer [payload->map]]
            [selmer.parser :refer [render-file]]))


(def contact (atom {:first "Eive" :last "Chera"
                    :email "eive.chera@reality.me"}))

(defn non-editable []
  (render-file "htmx/01_click_to_edit/non-editable.html" @contact))

(defn editable []
  (render-file "htmx/01_click_to_edit/editable.html" @contact))

(defn put-contact [id req]
  (let [payload (payload->map req)
        new-contact {:first (get payload "firstName")
                     :last (get payload "lastName")
                     :email (get payload "email")}]
    (println "putting new contact" id "\n:" new-contact)
    (reset! contact new-contact)
    (non-editable)))
