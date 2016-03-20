(ns todos.views.page
  (require [hiccup.page :refer [html5 include-css include-js]]))

;; For this assignment, only one css file. Therefore, it is not passed as an arg.
(defn page
  "Given title and contents,
   return a html5 page (as String)."
  [title contents]
  (html5
   [:head
    [:title title]
    (include-css "css/style.css")]

   [:body contents]))
