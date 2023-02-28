(ns common 
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn payload->map [req]
  (some-> req :body (io/reader :encoding "UTF-8") slurp
          (java.net.URLDecoder/decode) (str/split #"&")
          (->> (map #(str/split % #"=")) vec (into {}))))