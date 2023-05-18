(ns htmx.16-modal-bootstrap.api 
  (:require [selmer.parser :refer [render-file]]))

;; bootstrap css is put directly in htmx/index.html
;; apparently it collides with current css and the looks slightly changes 
;; but it's no big deal so I let it be 
(defn modal-bootstrap-page []
  (render-file "htmx/16_modal_bootstrap/main-page.html" {}))

(defn boostrap-modal []
  (render-file "htmx/16_modal_bootstrap/modal-boostrap.html" {}))