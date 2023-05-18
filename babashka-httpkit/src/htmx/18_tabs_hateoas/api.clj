(ns htmx.18-tabs-hateoas.api
  (:require [clojure.string :as str]
            [hiccup2.core :refer [html]]
            [selmer.parser :refer [render-file]]))

(defn tabs-hateoas-page []
  (render-file "htmx/18_tabs_hateoas/main-page.html" {}))

(def content ["Commodo normcore truffaut VHS duis gluten-free keffiyeh iPhone taxidermy godard ramps anim pour-over. \r\n\tPitchfork vegan mollit umami quinoa aute aliquip kinfolk eiusmod live-edge cardigan ipsum locavore. \r\n\tPolaroid duis occaecat narwhal small batch food truck. \r\n\tPBR&B venmo shaman small batch you probably haven't heard of them hot chicken readymade. \r\n\tEnim tousled cliche woke, typewriter single-origin coffee hella culpa. \r\n\tArt party readymade 90's, asymmetrical hell of fingerstache ipsum."
              "Kitsch fanny pack yr, farm-to-table cardigan cillum commodo reprehenderit plaid dolore cronut meditation. Tattooed polaroid veniam, anim id cornhole hashtag sed forage. Microdosing pug kitsch enim, kombucha pour-over sed irony forage live-edge. Vexillologist eu nulla trust fund, street art blue bottle selvage raw denim. Dolore nulla do readymade, est subway tile affogato hammock 8-bit. Godard elit offal pariatur you probably haven't heard of them post-ironic. Prism street art cray salvia."
              "Aute chia marfa echo park tote bag hammock mollit artisan listicle direct trade. Raw denim flexitarian eu godard etsy. Poke tbh la croix put a bird on it fixie polaroid aute cred air plant four loko gastropub swag non brunch. Iceland fanny pack tumeric magna activated charcoal bitters palo santo laboris quis consectetur cupidatat portland aliquip venmo."])

(defn tabs [i]
  (str (html [:div.tab-list
              (for [n [1 2 3]]
                [:a {:class (str/join "" ["one-tab"
                                          (when (= i n) "selected")])
                     :hx-get (str "/htmx/tab" n)}
                 (str "Tab " n)])]
             [:div.tab-content (get content (dec i))])))
