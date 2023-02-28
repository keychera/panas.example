(ns htmx.api
  (:require [clojure.core.match :refer [match]]
            [htmx.01-click-to-edit.api :refer [editable non-editable
                                               put-contact]]
            [htmx.02-bulk-edit.api :refer [activate bulk-edit deactivate]]
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
      [:put ["activate"]] {:body (activate req)}
      [:put ["deactivate"]] {:body (deactivate req)} 


      :else {:status 404 :body "htmx example not found here"})))