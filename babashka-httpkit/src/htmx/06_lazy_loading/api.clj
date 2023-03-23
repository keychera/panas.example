(ns htmx.06-lazy-loading.api
  (:require [selmer.parser :refer [render-file]]
            [clojure.string :as str]))

(defn hw []
  (Thread/sleep 2345)
  (render-file "htmx/06_lazy_loading/hw.html" {}))

(defn lazy-page []
  (render-file "htmx/06_lazy_loading/lazy-page.html" {}))

(str/includes? "a" "b")
