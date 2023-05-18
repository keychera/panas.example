(ns htmx.16-modals-bootstrap.api 
  (:require [selmer.parser :refer [render-file]]))

;; bootstrap css is put directly in htmx/index.html
;; apparently it collides with current css and the looks slightly changes 
;; but it's no big deal so I let it be 
(defn modals-bootstrap-page []
  (render-file "htmx/16_modals_bootstrap/main-page.html" {}))

(defn boostrap-modal []
  (render-file "htmx/16_modals_bootstrap/modals-boostrap.html" {}))