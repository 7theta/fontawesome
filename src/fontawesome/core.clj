;;   Copyright (c) 7theta. All rights reserved.
;;   The use and distribution terms for this software are covered by the
;;   MIT License (https://opensource.org/licenses/MIT) which can also be
;;   found in the LICENSE file at the root of this distribution.
;;
;;   By using this software in any fashion, you are agreeing to be bound by
;;   the terms of this license.
;;   You must not remove this notice, or any others, from this software.

(ns fontawesome.core
  (:require [clojure.xml :as xml]
            [clojure.java.io :as io]))

(defmacro icon
  [file props]
  (let [svg (xml/parse (io/file (io/resource file)))
        view-box (-> svg :attrs :viewBox)
        contents (map (fn [{:keys [tag attrs]}]
                        [tag (assoc attrs :fill "currentColor")])
                      (:content svg))]
    `[:svg {:class-name (get ~props :class-name)
            :view-box ~view-box}
      ~@contents]))

(defmacro deficon
  [icon-name file]
  `(defn ~icon-name
     [props#]
     (icon ~file props#)))
