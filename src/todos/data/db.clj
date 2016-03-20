(ns todos.data.db
  (require [clojure.java.io :as io]
           [clojure.java.jdbc :as sql]
           [todos.business :as business]))

(defn- db-spec
  [settings]
  (let [{:keys [server port db-name username password] :or {port "3306"}} settings]
    {:classname   "com.mysql.jdbc.Driver"
     :subprotocol "mysql"
     :subname     (str "//" server ":" port "/" db-name "?characterEncoding=UTF-8&useUnicode=true")
     :user        username
     :password    password}))

(def db-conn (db-spec business/db-settings))

(defn get-todolist
  "Returs every data from the todo table as a vector (of maps)."
  []
  (-> (sql/query db-conn ["SELECT * FROM `todo` ORDER BY `done` DESC, `id` ASC;"])
      vec))

(defn initial-db-setup
  []
  (do
    (sql/execute! db-conn [(slurp (io/resource "migration/create-todo-table.sql"))])
    (when (empty? (get-todolist))
      (sql/insert! db-conn  :todo
                   {:text "Purchase Elements of Clojure" :done 1}
                   {:text "Reach Chapter 1 of Elements of Clojure"}
                   {:text "Renew Passport"}
                   {:text "Make a dental appointment" :done 1}))))

(defn add-todo
  "Given todo as string, add a new TODO to the table."
  [todo]
  (sql/insert! db-conn :todo {:text todo}))

(defn- get-status
  "Given id, return doneness status of the TODO item."
  [id]
  (->> (sql/query db-conn ["SELECT `done` FROM `todo` WHERE `id` = ?" id])
       first
       :done))

(defn toggle-status
  "Given id, toggle doneness status"
  [id]
  (let [current-status (get-status id)]
    (sql/update! db-conn :todo {:done (not current-status)} ["id=?" id] )))
