(ns htmx.07-inline-validation.api
  (:require [common :refer [payload->map]]
            [hiccup2.core :refer [html]]
            [selmer.parser :refer [render-file]]))

(defn inline-page []
  (render-file "htmx/07_inline_validation/inline-page.html" {}))

(defn validate-email [req]
  (let [{:strs [email]} (payload->map req)
        valid? (= email "test@test.com")] 
    (str (html [:div {:class (if valid? "valid" "error") :hx-target "this" :hx-swap "outerHTML"}
                [:label "Email Address (only accept `test@test.com`)"]
                [:input {:name "email" :hx-post "/htmx/contact/email" 
                        ;;  :hx-indicator "#ind" 
                         :value email}]
                [:img#ind.htmx-indicator {:src "/img/loading.svg"}]
                (when-not valid? [:div.error-message "That email is already taken.  Please enter another email."])]))))