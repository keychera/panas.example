(ns htmx.api
  (:require [clojure.core.match :refer [match]]
            [htmx.01-click-to-edit.api :refer [editable-page non-editable-page
                                               put-contact]]
            [htmx.02-bulk-edit.api :refer [bulk-edit-page toggle-active]]
            [htmx.03-click-to-load.api :refer [click-to-load-page
                                               request-contacts]]
            [htmx.04-delete-row.api :refer [delete-contact delete-row-page]]
            [htmx.05-edit-rows.api :refer [edit-rows-page put-row row]]
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

      [:get ["click-to-edit"]] {:body (sidebar> non-editable-page)}
      [:get ["contact" _]]  {:body (non-editable-page)}
      [:put ["contact" id]] {:body (put-contact id req)}
      [:get ["contact" _ "edit"]] {:body (editable-page)}

      [:get ["bulk-edit"]] {:body (sidebar> bulk-edit-page)}
      [:put ["activate"]] {:body (toggle-active req true)}
      [:put ["deactivate"]] {:body (toggle-active req false)}

      [:get ["click-to-load"]] {:body (sidebar> click-to-load-page)}
      [:get ["contacts"]] {:body (request-contacts req)}

      [:get ["delete-row"]] {:body (sidebar> delete-row-page)}
      [:delete ["contact" id]] {:body (delete-contact id)}

      [:get ["edit-rows"]] {:body (sidebar> edit-rows-page)}
      [:get ["ex5" "contact" id]] {:body (row id)}
      [:put ["ex5" "contact" id]] {:body (put-row id req)}
      [:get ["ex5" "contact" id "edit"]] {:body (row id :editable? true)}

      :else {:status 404 :body "htmx example not found here"})))