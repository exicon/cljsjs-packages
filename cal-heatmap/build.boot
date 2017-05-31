(set-env!
 :resource-paths #{"resources"}
 :dependencies '[[cljsjs/boot-cljsjs "0.5.2"  :scope "test"]
                 [cljsjs/d3          "3.5.5-0"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +lib-version+ "3.6.2")
(def +version+ (str +lib-version+ "-0"))

(task-options!
 pom  {:project     'cljsjs/cal-heatmap
       :version     +version+
       :description "Cal-Heatmap is a javascript module to create calendar heatmap to visualize time series data"
       :url         "http://cal-heatmap.com/"
       :scm         {:url "https://github.com/cljsjs/packages"}
       :license     {"MIT" "http://opensource.org/licenses/MIT"}})

(deftask package []
  (comp
   (download :url      (str "https://github.com/wa0x6e/cal-heatmap/archive/" +lib-version+  ".zip")
             :checksum "1402F676905ACFAE5492C3D8361BDE9E"
             :unzip    true)
   (sift :move {#"^cal-heatmap-([\d\.]*)/cal-heatmap\.js"      "cljsjs/cal-heatmap/development/cal-heatmap.inc.js"
                #"^cal-heatmap-([\d\.]*)/cal-heatmap\.min\.js" "cljsjs/cal-heatmap/production/cal-heatmap.min.inc.js"
                #"^cal-heatmap-([\d\.]*)/cal-heatmap\.css"     "cljsjs/cal-heatmap/common/cal-heatmap.inc.css"})
   (sift :include #{#"^cljsjs"})
   (deps-cljs :name     "cljsjs.cal-heatmap"
              :requires ["cljsjs.d3"])
   (pom)
   (jar)))
