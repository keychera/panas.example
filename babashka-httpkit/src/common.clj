(ns common
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn query->vec
  "parse str of shape 'a=1&b=2' to [[\"a\" 1] [\"b\" 2]]"
  [q] (->> (str/split q #"&") (map #(str/split % #"=")) vec))

(defn payload->vec
  "use this if the key is not unique, return a shape of [[k v1] [k v2]]"
  [req]
  (some-> req :body (io/reader :encoding "UTF-8") slurp
          (java.net.URLDecoder/decode) query->vec))

(defn payload->map
  "use this if the key is unique, returns normal map"
  [req]
  (into {} (payload->vec req)))
