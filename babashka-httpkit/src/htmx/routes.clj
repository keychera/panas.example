(ns htmx.routes
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
            [htmx.10-progress-bar.api :refer [job progress-bar-page
                                              progress-start]]
            [htmx.11-value-select.api :refer [models value-select-page]]
            [htmx.12-animations.api :refer [animations-page fade-in get-color
                                            initial-content new-content]]
            [htmx.13-file-upload.api :refer [fake-upload file-upload-page]]
            [htmx.14-dialogs.api :refer [dialogs-page submit]]
            [htmx.15-modal-uikit.api :refer [modal-uikit-page uikit-modal]]
            [htmx.16-modal-bootstrap.api :refer [boostrap-modal
                                                 modal-bootstrap-page]]
            [htmx.17-modal-custom.api :refer [custom-modal modal-custom-page]]
            [htmx.18-tabs-hateoas.api :refer [tabs tabs-hateoas-page]]
            [htmx.19-tabs-hyperscript.api :refer [tab-contents
                                                  tabs-hyperscript-page]]
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
      [:post ["progress-start"]] {:body (progress-start)}
      [:get ["job"]] {:body (job)}

      [:get ["value-select"]] {:body (sidebar> value-select-page)}
      [:get ["models"]] {:body (models req)}

      [:get ["animations"]] {:body (sidebar> animations-page)}
      [:get ["colors"]] {:body (get-color req)}
      [:delete ["fade_out_demo"]] {:body ""}
      [:post ["fade_in_demo"]] {:body (fade-in req)}
      [:post ["req_in_flight"]] {:body (do (Thread/sleep 2000) "Submitted!")}
      [:get ["new-content"]] {:body (new-content req)}
      [:get ["initial-content"]] {:body (initial-content req)}

      [:get ["file-upload"]] {:body (sidebar> file-upload-page)}
      [:post ["upload"]] {:body (fake-upload)}

      [:get ["dialogs"]] {:body (sidebar> dialogs-page)}
      [:post ["submit"]] {:body (submit req)}

      [:get ["modal-uikit"]] {:body (sidebar> modal-uikit-page)}
      [:get ["uikit-modal.html"]] {:body (uikit-modal)}

      [:get ["modal-boostrap"]] {:body (sidebar> modal-bootstrap-page)}
      [:get ["bootstrap-modal"]] {:body (boostrap-modal)}

      [:get ["modal-custom"]] {:body (sidebar> modal-custom-page)}
      [:get ["custom-modal"]] {:body (custom-modal)}

      [:get ["tabs-hateoas"]] {:body (sidebar> tabs-hateoas-page)}
      [:get ["tab1"]] {:body (tabs 1)}
      [:get ["tab2"]] {:body (tabs 2)}
      [:get ["tab3"]] {:body (tabs 3)}

      [:get ["tabs-hyperscript"]] {:body (sidebar> tabs-hyperscript-page)}

      [:get ["tabc1"]] {:body (tab-contents 1)}
      [:get ["tabc2"]] {:body (tab-contents 2)}
      [:get ["tabc3"]] {:body (tab-contents 3)}

      :else {:status 404 :body "htmx example not found here"})))