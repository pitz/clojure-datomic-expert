(ns datomicexpert.db.bootstrap
  (:use clojure.pprint)
  (:require [datomic.api                   :as d]
            [datomicexpert.schema.schemata :as schemata])
  (:import (java.util UUID)))

(def db-uri "datomic:dev://localhost:4334/score")

(defn connect-to-db []
  (d/create-database db-uri)
  (d/connect db-uri))

(defn apagar-bd []
  (println " @ apagar-bd ")
  (d/delete-database db-uri))

(defn cria-schema []
  (println " @ cria-schema ")
  (d/transact (connect-to-db) schemata/schema))

(defn get-db []
  (d/db (connect-to-db)))

(defn get-id-from-transact
  [fn-transact]
  (:e (second (:tx-data @fn-transact))))

(defn save-group
  [nome]
  (d/transact (connect-to-db) [{:group/id (UUID/randomUUID), :group/name nome}]))

(defn save-customer
  [nome cpf limit group]
  (d/transact (connect-to-db) [{:customer/id    (UUID/randomUUID)
                                :customer/name  nome
                                :customer/cpf   cpf
                                :customer/limit limit
                                :customer/group group}]))

(defn populate-db []
  (println " @ populate-db ")

  (let [upmarket-group   (get-id-from-transact (save-group "Upmarket"))
        massmarket-group (get-id-from-transact (save-group "Massmarket"))]
    (save-customer "Eduardo Pitz" "18961996212" 1000.20M upmarket-group)
    (save-customer "Rewduao Pitz" "28961996212" 10.20M massmarket-group)
    (save-customer "Leduado Pitz" "38961996212" 1.20M massmarket-group)
    (save-customer "Bedurdo Pitz" "48961996212" 1.88M massmarket-group)
    (save-customer "Dedardo Pitz" "58961996212" 12200.28M upmarket-group)
    (save-customer "Rfuardo Pitz" "60696199621" 1020.23M upmarket-group)
    (save-customer "Jsuardo Pitz" "78961996212" 670.11M massmarket-group)))