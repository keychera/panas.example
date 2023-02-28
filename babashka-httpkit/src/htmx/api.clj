(ns htmx.api
  (:require [clojure.core.match :refer [match]]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [selmer.parser :refer [render-file]]))

(defn htmx-index [main-div]
  (render-file "htmx/index.html" {:render-main main-div}))

(defn payload->map [req]
  (some-> req :body (io/reader :encoding "UTF-8") slurp
          (java.net.URLDecoder/decode) (str/split #"&")
          (->> (map #(str/split % #"=")) vec (into {}))))

(def contact (atom {:first "Eive" :last "Chera"
                    :email "eive.chera@reality.me"}))

(defn non-editable []
  (render-file "htmx/01-click-to-edit/non-editable.html" @contact))

(defn editable []
  (render-file "htmx/01-click-to-edit/editable.html" @contact))

(defn put-contact [id req]
  (let [payload (payload->map req)
        new-contact {:first (get payload "firstName")
                     :last (get payload "lastName")
                     :email (get payload "email")}]
    (println "putting new contact" id "\n:" new-contact)
    (reset! contact new-contact)
    (non-editable)))


(defn part? [req] (get-in req [:headers "hx-request"]))

(defn sidebar-route [partial? handler & args]
  (if partial?
    (apply handler args)
    (htmx-index (apply handler args))))

;; this is router under /htmx
(defn router [req action]
  (let [verb (:request-method req)
        sidebar> (partial sidebar-route (part? req))]
    (match [verb action]
      [:get []] {:body (htmx-index nil)}

      [:get ["click-to-edit"]] {:body (sidebar> non-editable)}
      [:get ["contact" _]]  {:body (non-editable)}
      [:put ["contact" id]] {:body (put-contact id req)}
      [:get ["contact" _ "edit"]] {:body (editable)}


      :else {:status 404 :body "htmx example not found here"})))