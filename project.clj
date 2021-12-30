(defproject datomic-expert "0.1.0-SNAPSHOT"
  :description "www.pitz.dev"
  :url "http://wwww.pitz.dev"

  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}

  :dependencies  [[org.clojure/clojure     "1.10.1"]
                  [prismatic/schema        "1.2.0"]
                  [com.datomic/datomic-pro "1.0.6269"]
                  [compojure               "1.6.1"]
                  [org.clojure/data.json   "2.4.0"]
                  [cheshire                "5.6.1"]]

  :repl-options {:init-ns datomicexpert.core})
