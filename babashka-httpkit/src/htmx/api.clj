(ns htmx.api
  (:require [clojure.core.match :refer [match]]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [selmer.parser :refer [render-file]]))

(defn htmx-index []
  (render-file "htmx/index.html" {}))

(defn payload->map [req]
  (some-> req :body (io/reader :encoding "UTF-8") slurp
          (java.net.URLDecoder/decode) (str/split #"&")
          (->> (map #(str/split % #"=")) vec (into {}))))

(def contact (atom {:first "Eive" :last "Chera"
                    :email "eive.chera@reality.me"}))

(defn non-editable []
  (render-file "htmx/click-to-edit/non-editable.html" @contact))

(defn editable []
  (render-file "htmx/click-to-edit/editable.html" @contact))

(defn put-contact [id req]
  (let [payload (payload->map req)
        new-contact {:first (get payload "firstName")
                     :last (get payload "lastName")
                     :email (get payload "email")}]
    (println "putting new contact" id "\n:" new-contact)
    (reset! contact new-contact)
    (non-editable)))


;; this is router under /htmx
(defn router [req action]
  (let [verb (:request-method req)]
    (match [verb action]
      [:get ["examples"]] {:body (htmx-index)}

      [:post ["click-to-edit"]] {:body (non-editable)}
      [:get ["contact" _]]  {:body (non-editable)}
      [:put ["contact" id]] {:body (put-contact id req)}
      [:get ["contact" _ "edit"]] {:body (editable)}


      :else {:status 404 :body "htmx example not found here"})))