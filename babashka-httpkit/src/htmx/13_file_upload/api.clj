(ns htmx.13-file-upload.api 
  (:require [selmer.parser :refer [render-file]]))

(defn file-upload-page []
  (render-file "htmx/13_file_upload/file-upload.html" {}))

(defn fake-upload [] "ok")