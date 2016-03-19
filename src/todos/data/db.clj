(ns todos.data.db
  (require [clojure.java.io :as io]
           [clojure.java.jdbc :as sql]))

(defn- db-spec
  [settings]
  (let [{:keys [server port db-name username password] :or {port "3306"}} settings]
    {:classname   "com.mysql.jdbc.Driver"
     :subprotocol "mysql"
     :subname     (str "//" server ":" port "/" db-name "?characterEncoding=UTF-8&useUnicode=true")
     :user        username
     :password    password}))

(defn initial-db-setup
  [db-settings]
  (let [db-conn (db-spec db-settings)]
    (sql/execute! db-conn [(slurp (io/resource "migration/create-todo-table.sql"))])))
