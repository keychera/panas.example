(ns htmx.api
  (:require [clojure.core.match :refer [match]]
            [htmx.01-click-to-edit.api :refer [editable non-editable
                                               put-contact]]
            [htmx.02-bulk-edit.api :refer [bulk-edit toggle-active]]
            [htmx.03-click-to-load.api :refer [click-to-load request-contacts]]
            [selmer.parser :refer [render-file]]))

(defn htmx-index [main-div]
  (render-file "htmx/index.html" {:render-main main-div}))


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

      [:get ["bulk-edit"]] {:body (sidebar> bulk-edit)}
      [:put ["activate"]] {:body (toggle-active req true)}
      [:put ["deactivate"]] {:body (toggle-active req false)}

      [:get ["click-to-load"]] {:body (sidebar> click-to-load)}
      [:get ["contacts"]] {:body (request-contacts req)} 

      :else {:status 404 :body "htmx example not found here"})))