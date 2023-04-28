(ns htmx.api
  (:require [clojure.core.match :refer [match]]
            [htmx.01-click-to-edit.api :refer [editable-page non-editable-page
                                               put-contact]]
            [htmx.02-bulk-edit.api :refer [bulk-edit-page toggle-active]]
            [htmx.03-click-to-load.api :refer [click-to-load-page
                                               request-contacts]]
            [htmx.04-delete-row.api :refer [delete-contact delete-row-page]]
            [htmx.05-edit-rows.api :refer [edit-rows-page put-row row]]
            [htmx.06-lazy-loading.api :refer [hw lazy-page]]
            [htmx.07-inline-validation.api :refer [inline-page validate-email]]
            [htmx.08-infinite-scroll.api :refer [infinite-contacts
                                                 infinite-page]]
            [htmx.09-active-search.api :refer [active-search-page search]]
            [htmx.10-progress-bar.api :refer [progress-bar-page]]
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

      [:get ["lazy-loading"]] {:body (sidebar> lazy-page)}
      [:get ["hw"]] {:body (hw)}

      [:get ["inline-validation"]] {:body (sidebar> inline-page)}
      [:post ["contact" "email"]] {:body (validate-email req)}

      [:get ["infinite-scroll"]] {:body (sidebar> infinite-page)}
      [:get ["contacts-infinite"]] {:body (infinite-contacts req)}

      [:get ["active-search"]] {:body (sidebar> active-search-page)}
      [:post ["search"]] {:body (search req)}

      [:get ["progress-bar"]] {:body (sidebar> progress-bar-page)}

      :else {:status 404 :body "htmx example not found here"})))