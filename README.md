# Learning Datalog

![image](https://user-images.githubusercontent.com/42384045/147756710-bc4e5973-ec32-4b32-87e6-82e4d1cd215d.png)

Com este repositório eu coloco em prática um pouco de meus estudos sobre o Datalog, linguagem utilizada para efetuar queries em um banco de dados Datomic. Neste projeto teremos duas entidades: `Customer` e `Group`. Um customer pertence a um grupo e um grupo pode conter diversos customers.

Todos os códigos abaixo podem ser encontrados em `datomicexpert.core`.

---

### Buscar customer pelo ID:

```clojure
(defn get-customer [db id]
  (d/pulldb '[*] [:customer/id id]))

; Uma maneira simples de buscar todos os dados de um registro filtrando apelas pelo seu ID.
```

### Listar todos os nomes de Customers:

```clojure
(defn listar-nomes []
  (println " @ listar-nomes: ")
  (d/q '[:find  ?name
         :where [?customer :customer/name ?name]]
       (db.boostrap/get-db)))

; O :find nos permite definir o que cada query deve nos retornar
```

### Listar todos os nomes de Customers com um `limit` específico:

```clojure
(defn listar-nomes-com-limit-eq [limit-value]
  (println " @ listar-nomes-com-filtro-de-limite-fixo: ")
  (d/q '[:find  ?name
         :in    $ ?limit-eq
         :where [?customer :customer/name  ?name]
                [?customer :customer/limit ?limit-eq]]
       (db.boostrap/get-db) limit-value))
```

### Listar todos os nomes de Customer com um `limit` maior ou igual:

```clojure
(defn listar-nomes-com-limit-ge [limit-value]
  (println " @ listar-nomes-com-limit-ge: ")
  (d/q '[:find  ?name
         :in    $ ?limit-ge
	 :where [?customer :customer/name ?name]
                [?customer :customer/limit ?limit]
                [(>= ?limit ?limit-ge)]]
       (db.boostrap/get-db) limit-value))

; É possível usar funções para nos auxiliar nos filtros, aqui eu uso um >= para resolver parte dos meus problemas.
```

### Listar todos os nomes de Customers com nome iniciando em:

```clojure
(defn listar-nomes-com-inicial [initial-name-str]
  (println " @ listar-nomes-com-inicial" initial-name-str ":")
  (d/q'[:find  ?name
        :in    $ ?initial-name-str
        :where [?customer :customer/name ?name]
               [(.startsWith ?name ?initial-name-str)]
       (db.boostrap/get-db) initial-name-str))

; Assim como foi feito no exemplo anterior, usamos uma **função.** 
; Aqui, nosso objetivo é encontrar registros que iniciem com uma String em específico.
```

### Usando transformation-functions para manipular o retorno de uma query:

```clojure
(defn parse-name [name]  (str "Parseando o nome: " name))

(defn listar-nomes-with-transformation-function []
  (println " @ listar-nomes: ")
  (d/q '[:find  ?parsed-name
         :where [?customer :customer/name ?name]
                [(datomicexpert.core/parse-name ?name) ?parsed-name]]
        (db.boostrap/get-db)))

; Através de uma transformation-function, podemos definir como a nossa query deve retornar um valor. 
; No exemplo acima, criamos uma nova String a partir do ?name de cada customer.
```

### Retornando dados de duas tabelas:

```clojure
(defn listar-nomes-e-grupos []
  (d/q '[:find ?customer-name ?group-name
         :where [?customer :customer/name ?customer-name]
                [?customer :customer/group ?group]
                [?group    :group/name ?group-name]]
       (db.boostrap/get-db)))

; da maneira acima, conseguimos retornar o nome do customer e o nome do grupo.. 
; contudo, o output deste método é um vetor... não me parece ser a melhor maneira de lidar com isso. 

; O próximo exemplo fará a nossa vida ser mais fácil.
```

### Retornando dados em Map:

```clojure
(defn listar-nomes-e-grupos []
  (d/q '[:find  ?customer-name ?group-name
         :keys  customer/name group/name
	 :where [?customer :customer/name ?customer-name]
                [?customer :customer/group ?group]
                [?group    :group/name ?group-name]]
       (db.boostrap/get-db)))

; De uma maneira muito mais elegante, podemos retornar nossos dados em um Map.
```

### Retornando a quantidade de Customers de cada Group:

```clojure
(defn count-customers-from-group []
  (d/q '[:find  ?group-name (count ?customer-name)
         :keys  group/name customers-count
	 :where [?customer :customer/name ?customer-name]
	        [?customer :customer/group ?group]
                [?group :group/name ?group-name]]
       (db.boostrap/get-db)))

; O count nos permite retornar a quantidade de Customers de cada Grupo. 
; Similiar a seu uso, temos as seguintes funções: min, max, sum, avg.
```

### Retornar 3 maiores limites:

```clojure
(defn top-3-limits []
  (d/q '[:find  (max 3 ?customer-limit)
         :keys  limit 
         :where [?customer :customer/name  ?customer-name]
                [?customer :customer/limit ?customer-limit]]
       (db.boostrap/get-db)))

; Eu ainda não consegui retornar um max n com outro campo junto.. 
; retornar somente os top 3 valores não me parece o tipo de operação mais útil do mundo :)
```
