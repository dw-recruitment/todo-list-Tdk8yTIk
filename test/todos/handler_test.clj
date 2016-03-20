(ns todos.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [todos.data.db :as db]
            [todos.handler :refer :all]
            [todos.views.about :as about]
            [todos.views.index :as index]))

(deftest test-app
  (testing "main route"
    (let [{:keys [body status]} (app (mock/request :get "/"))]
      (is (= status 200))
      ;; anti-forgery token part is unbound in (-> (db/get-todolist) (index/contents))
      ;; while there is a valid token in mock request
      ;(is (= body (-> (db/get-todolist) (index/contents))))
      ))

  ;; TODO: Add a test for POST "/"
  ;;       Learn about anti-forgery / POST handling.

  (testing "about route"
    (let [{:keys [body status]} (app (mock/request :get "/about"))]
      (is (= status 200))
      (is (= body (about/contents)))))

  (testing "not-found route"
    (let [{:keys [body status]} (app (mock/request :get "/invalid"))]
      (is (= status 404))
      (is (= body "Not Found")))))
