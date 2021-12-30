(ns datomicexpert.core
  (:require
    [datomic.api :as d]
    [datomicexpert.db.bootstrap :as db.boostrap]))

; (db.boostrap/apagar-bd)
(db.boostrap/cria-schema)
(db.boostrap/populate-db)

; Buscar customer pelo ID
(defn get-customer [db id]
  (d/pull db '[*] [:customer/id id]))

; Listar todos os nomes
(defn listar-nomes []
  (println " @ listar-nomes: ")
  (d/q '[:find   ?name
         :where [?customer :customer/name ?name]]
       (db.boostrap/get-db)))

(doseq [name (listar-nomes)]
  (println " @" name))

; Listar todos os nomes para clientes com limite igual a limit-value
(defn listar-nomes-com-limit-eq [limit-value]
  (println " @ listar-nomes-com-filtro-de-limite-fixo: ")
  (d/q '[:find   ?name
         :in    $ ?limit-eq
         :where [?customer :customer/name  ?name]
                [?customer :customer/limit ?limit-eq]]
       (db.boostrap/get-db) limit-value))

(doseq [name (listar-nomes-com-limit-eq 100.20M)]
  (println " @" name))

; Listar todos os nomes para clientes com limite maior ou igual a limit-value
(defn listar-nomes-com-limit-ge [limit-value]
  (println " @ listar-nomes-com-limit-ge: ")
  (d/q '[:find   ?name
         :in    $ ?limit-ge
         :where [?customer :customer/name  ?name]
                [?customer :customer/limit ?limit]
                [(>= ?limit ?limit-ge)]]
       (db.boostrap/get-db) limit-value))

(doseq [name (listar-nomes-com-limit-ge 50.00M)]
  (println " @" name))

; Listar todos os nomes para clientes com limite maior que limit-value-gt
(defn listar-nomes-com-limit-gt [limit-value]
  (println " @ listar-nomes-com-limit-gt: ")
  (d/q '[:find   ?name
         :in    $ ?limit-value-gt
         :where [?customer :customer/name  ?name]
         [?customer :customer/limit ?limit]
         [(> ?limit ?limit-value-gt)]]
       (db.boostrap/get-db) limit-value))

(doseq [name (listar-nomes-com-limit-gt 1000.00M)]
  (println " @" name))

; Listar todos os nomes para clientes com nome iniciando com initial-name-char
(defn listar-nomes-com-inicial [initial-name-str]
  (println " @ listar-nomes-com-inicial" initial-name-str ":")
  (d/q '[:find   ?name
         :in    $ ?initial-name-str
         :where [?customer :customer/name  ?name]
                [(.startsWith ?name ?initial-name-str)]]
       (db.boostrap/get-db) initial-name-str))

(doseq [name (listar-nomes-com-inicial "E")]
  (println " @" name))

; Listar todos os nomes para clientes com nome iniciando com initial-name-char
(defn listar-nomes-com-final [final-name-str]
  (println " @ listar-nomes-com-final" final-name-str ":")
  (d/q '[:find   ?name
         :in    $ ?final-str
         :where [?customer :customer/name  ?name]
         [(.endsWith ?name ?final-str)]]
       (db.boostrap/get-db) final-name-str))

(doseq [name (listar-nomes-com-final "z")]
  (println " @" name))

(defn parse-name [name]
  (str "Parseando o nome: " name))

; Listar todos os nomes com Transformation functions
(defn listar-nomes-with-transformation-function []
  (println " @ listar-nomes: ")
  (d/q '[:find   ?parsed-name
         :where [?customer :customer/name ?name]
                [(datomicexpert.core/parse-name ?name) ?parsed-name]]
       (db.boostrap/get-db)))

(doseq [name (listar-nomes-with-transformation-function)]
  (println " @" name))

(defn listar-nomes-e-grupos []
  (d/q '[:find   ?customer-name ?group-name
         :keys   customer/name group/name
         :where [?customer :customer/name  ?customer-name]
                [?customer :customer/group ?group]
                [?group    :group/name     ?group-name]]
       (db.boostrap/get-db)))

(listar-nomes-e-grupos)

(defn count-customers-from-group []
  (d/q '[:find   ?group-name (count ?customer-name)
         :keys  group/name customers-count
         :where [?customer :customer/name  ?customer-name]
         [?customer :customer/group ?group]
         [?group    :group/name     ?group-name]]
       (db.boostrap/get-db)))

(count-customers-from-group)

(defn top-3-limits []
  (d/q '[:find   (max 3 ?customer-limit)
         :keys  limit
         :where [?customer :customer/name  ?customer-name]
                [?customer :customer/limit ?customer-limit]]
       (db.boostrap/get-db)))

(top-3-limits)



; continuar com http://www.learndatalogtoday.org/chapter/7



