(ns datomicexpert.schema.schemata
  (:use
    clojure.pprint))

(def schema [{:db/ident :customer/id,    :db/valueType :db.type/uuid,   :db/cardinality :db.cardinality/one, :db/unique :db.unique/identity}
             {:db/ident :customer/cpf,   :db/valueType :db.type/string, :db/cardinality :db.cardinality/one}
             {:db/ident :customer/name,  :db/valueType :db.type/string, :db/cardinality :db.cardinality/one}
             {:db/ident :customer/limit, :db/valueType :db.type/bigdec, :db/cardinality :db.cardinality/one}
             {:db/ident :customer/group, :db/valueType :db.type/ref,    :db/cardinality :db.cardinality/one}

             {:db/ident :group/id,    :db/valueType :db.type/uuid,   :db/cardinality :db.cardinality/one, :db/unique :db.unique/identity}
             {:db/ident :group/name,  :db/valueType :db.type/string, :db/cardinality :db.cardinality/one}])