(ns htmx.09-active-search.api
  (:require [clojure.string :as str]
            [common :refer [payload->map]]
            [hiccup2.core :refer [html]]
            [selmer.parser :refer [render-file]]))

(defn active-search-page []
  (render-file "htmx/09_active_search/active-page.html" {}))

;; ChatGPT did the conversion from htmx example js script here! (but it gave up midway 😅)
(def data-store
  [{:FirstName "Venus" :LastName "Grimes" :Email "lectus.rutrum@Duisa.edu" :City "Ankara"}
   {:FirstName "Fletcher" :LastName "Owen" :Email "metus@Aenean.org" :City "Niort"}
   {:FirstName "William" :LastName "Hale" :Email "eu.dolor@risusodio.edu" :City "Te Awamutu"}
   {:FirstName "TaShya" :LastName "Cash" :Email "tincidunt.orci.quis@nuncnullavulputate.co.uk" :City "Titagarh"}
   {:FirstName "Kevyn" :LastName "Hoover" :Email "tristique.pellentesque.tellus@Cumsociis.co.uk" :City "Cuenca"}
   {:FirstName "Jakeem" :LastName "Walker" :Email "Morbi.vehicula.Pellentesque@faucibusorci.org" :City "St. AndrÃ¤"}
   {:FirstName "Malcolm" :LastName "Trujillo" :Email "sagittis@velit.edu" :City "Fort Resolution"}
   {:FirstName "Wynne" :LastName "Rice" :Email "augue.id@felisorciadipiscing.edu" :City "Kinross"}
   {:FirstName "Evangeline" :LastName "Klein" :Email "adipiscing.lobortis@sem.org" :City "San Giovanni in Galdo"}
   {:FirstName "Jennifer" :LastName "Russell" :Email "sapien.Aenean.massa@risus.com" :City "Laives/Leifers"}
   {:FirstName "Rama" :LastName "Freeman" :Email "Proin@quamPellentesquehabitant.net" :City "Flin Flon"}
   {:FirstName "Jena" :LastName "Mathis" :Email "non.cursus.non@Phaselluselit.com" :City "Fort Simpson"}
   {:FirstName "Alexandra" :LastName "Maynard" :Email "porta.elit.a@anequeNullam.ca" :City "Nazilli"}
   {:FirstName "Tallulah" :LastName "Haley" :Email "ligula@id.net" :City "Bay Roberts"}
   {:FirstName "Timon" :LastName "Small" :Email "velit.Quisque.varius@gravidaPraesent.org" :City "Girona"}
   {:FirstName "Randall" :LastName "Pena" :Email "facilisis@Donecconsectetuer.edu" :City "Edam"}
   {:FirstName "Conan" :LastName "Vaughan" :Email "luctus.sit@Classaptenttaciti.edu" :City "Nadiad"}
   {:FirstName "Dora" :LastName "Allen" :Email "est.arcu.ac@Vestibulumante.co.uk" :City "Renfrew"}
   {:FirstName "Aiko" :LastName "Little" :Email "quam.dignissim@convallisest.net" :City "Delitzsch"}
   {:FirstName "Jessamine" :LastName "Bauer" :Email "taciti.sociosqu@nibhvulputatemauris.co"}])

(defn search [req]
  (let [payload (payload->map req)
        term (.toLowerCase (get payload "search"))]
    (Thread/sleep 1000)
    (let [result (->> data-store
                      (filter (fn [{:keys [FirstName LastName Email City]}]
                                (or (some-> FirstName .toLowerCase (str/includes? term))
                                    (some-> LastName .toLowerCase (str/includes? term))
                                    (some-> Email .toLowerCase (str/includes? term))
                                    (some-> City .toLowerCase (str/includes? term))))))]
      (if (= (count result) 0)
        (str (html [:tr [:td.grayed "nothing found!"] [:td ""] [:td ""]]))
        (->> result
             (map (fn [{:keys [FirstName LastName Email]}]
                    (str (html [:tr [:td FirstName] [:td LastName] [:td Email]]))))
             str/join)))))
