(ns serve
  (:require [clojure.core.match :refer [match]]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [htmx.api :as htmx]
            [org.httpkit.server :refer [run-server]]
            [selmer.parser :refer [render-file]]))

(defonce port 4242)
(defonce url (str "http://localhost:" port "/"))

(defn index [& _]
  (render-file "index.html" {}))

(defn mermaid [& _]
  (render-file "mermaid.html" {}))

(defn your-router [req]
  (let [paths (some-> (:uri req) (str/split #"/") rest vec)
        verb (:request-method req)]
    (match [verb paths]
      [:get []] {:body (index)}
      [:get ["mermaid"]] {:body (mermaid)}
      [_ ["htmx" & action]] (htmx/router req action)
      [:get ["css" "htmx.css"]] {:body (slurp (io/resource "htmx/style.css"))}
      [:get ["css" "style.css"]] {:body (slurp (io/resource "style.css"))}
      :else {:status 404 :body "not found"})))


(defn -main [& _]
  (run-server #'your-router {:port port :thread 12})
  (println "[panas] serving" url)
  @(promise))
