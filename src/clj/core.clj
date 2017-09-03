(ns clj.core)

(def table [8.2 1.5 2.8 4.3 12.7 2.2 2.0 6.1 7.0 0.2 0.8 4.0 2.4 6.7 7.5 1.9 0.1 6.0 6.3 9.1 2.8 1.0 2.4 0.2 2.0 0.1])

; Q001: haskellのzipと同様の機能の関数を書け
; zip :: [a] -> [b] -> [(a, b)]
(defn zip [& colls]
  (when (seq colls)
    (apply (partial map vector) colls)))

; Q002: haskellのsumと同様の機能の関数を書け。(reduceを用いるパターン、applyを用いるパターン、再帰を用いるパターン)
; sum :: (Num a) => [a] -> a
; sum ns
;     数値のリスト ns の総和を返す。
;     see also: product, foldl
;         sum [1, 2, 3]  = 6
;         sum []         = 0
; A: Using reduce.
;; (defn sum [ns]
;;   (reduce + ns))
(defn sum [ns]
  (reduce + ns))
; A: Using apply.
; (defn sum [ns]
;   (apply + ns))
; A: Using recursion.
;(defn sum [ns]
;  (if-not (seq ns)
;    0
;    (+ (first ns) (sum (rest ns)))))

; Q003: クイックソート関数qsort01を書け
(defn qsort01 [xs]
  (if-not (seq xs)
    []
    (let [x (first xs)
          xs' (rest xs)
          lt (for [x' xs' :when (< (int x') (int x))] x')
          ge (for [x' xs' :when (>= (int x') (int x))] x')]
      (concat (qsort01 lt) [x] (qsort01 ge)))))

; Q004: haskellのproductと同様の機能の関数を書け(reduceを用いるパターン、applyを用いるパターン、再帰を用いるパターン)
; product :: (Num a) => [a] -> a
; product ns
;     数値のリスト ns の全要素の積を返す。
;     see also: sum, foldl
;         product [2, 3, 4]   = 24
;         product [4, 5, 0]   = 0
;         product []          = 1
; A: Using reduce.
; (defn product [ns]
;   (reduce * ns))
; A: Using apply.
; (defn product [ns]
;   (apply * ns))
; A: Using recursion.
(defn product [ns]
  (if-not (seq ns)
    1
    (* (first ns) (product (rest ns)))))

; Q005: リストを逆順に整列する関数rqsortを書け
(defn rqsort [xs]
  (if-not (seq xs)
    []
    (let [x (first xs)
          xs' (rest xs)
          lt (for [x' xs' :when (< (int x') (int x))] x')
          ge (for [x' xs' :when (>= (int x') (int x))] x')]
      (concat (rqsort ge) [x] (rqsort lt)))))


; Q006: haskellのinitと同様の機能の関数my-initを書け(再帰を用いるバージョンも書くこと)
; init :: [a] -> [a]
; リスト xs の最後の要素を除いたリストを返す。
;     init [1,2,3]   = [1,2]
;     init [1]       = []
(defn my-init [xs]
  ((comp reverse rest reverse) xs))
; A: Using recursion.
;(defn my-init [xs]
;  (if-not (seq (rest xs))
;    []
;    (cons (first xs) (my-init (rest xs)))))

; Q007: haskellのlastと同様の機能の関数を書け(再帰を用いるバージョンも書くこと)
; last :: [a] -> a
;     リストの最後の要素を返す。
;         last [1,2,3]   = 3
;         last []        = エラー
(defn my-last [xs]
  ((comp first reverse) xs))
; A: Using recursion.
;(defn my-last [xs]
;  (if-not (seq (rest xs))
;    (first xs)
;    (my-last (rest xs))))

; Q008: 偶数の長さを持つリストを半分ずつに分割する関数halveを書け。
(defn halve [xs]
  (when (seq xs)
    (let [n (quot (count xs) 2)]
      [(take n xs) (drop n xs)])))

; Q009: concatをリスト内包表記で実装したmy-concatを書け。
; concat :: [[a]] -> [a]
; concat xs
;     リストのリスト xs を一つのリストに連結する。
;         concat [[1,2], [3,4], [5,6]]    = [1,2,3,4,5,6]
;         concat ["ab", "cd", "ef"]       = "abcdef"
;         concat [[]]                     = []
;         concat []                       = []
(defn my-concat [& xss]
  (for [xs xss x xs] x))

; Q010: 正の整数に対し、すべての約数を計算する関数factorsを書け
(defn factors [n]
  (for [n' (range 1 (inc n)) :when (zero? (mod n n'))] n'))

; Q011: 対(pair)のリストを探索し、検索キーと等しいキーを持つ対全てを探し出し、対応する値を取り出してリストにする関数my-findをリスト内包表記と分配束縛を用いて書け。
(defn my-find [k ps]
  (for [[k' v] ps :when (= k k')] v))

; Q012: 対のリストから、対の先頭の要素を取り出してリストを生成するfirstsをリスト内包表記と分配束縛を用いて書け。
(defn firsts [ps]
  (for [[k _] ps] k))

; Q013: haskellのlengthを、sumとリスト内包表記で書け。
; length :: [a] -> Int
; length xs
;     リスト xs の長さを返す。
;         length [1,2,3]   = 3
;         length "abcde"   = 5
;         length []        = 0
;         length ""        = 0
(defn length [coll]
  (sum (for [_ coll] 1)))

; Q014: factorsを用いて、整数が素数か判定する関数primeを書け。
(defn prime [n]
  (= [1 n] (factors n)))

; Q015: primeを用いて与えられた上限数までの全ての素数を生成する関数primesを書け。
(defn primes [n]
  (for [n' (range 1 (inc n)) :when (prime n')] n'))

; Q016: リストから隣り合う要素をマップにして返す関数pairsをzipmapを用いて書け。
; ex)
;   [1 2] => {1 2}
;   [1 2 3] => {1 2, 2 3}
;   [1 2 3 4] => {1 2, 2 3, 3 4}
(defn pairs [coll]
  (zipmap coll (rest coll)))

; Q017: 順序クラスに属する任意の型の要素を持つリストが、整列されているか調べる関数sortedをpairs関数を用いて書け。
;    （本来、pairsのような処理を行いたい場合、Clojureではシーケンスライブラリのpartitionを使用する。）
(defn sorted [coll]
  (every? (fn [[n m]] (< n m)) (pairs coll)))

; Q018: 目的とする値がリストのどの位置にあるかを調べて、その位置全てをリストとして返す関数positionsを書け。(indexは0から開始される事)
(defn positions [x coll]
  (for [[i v] (zipmap (iterate inc 0) coll) :when (= x v)] i))

; Q019: 指定した特定の文字がいくつ含まれているか数える関数char-countを書け。
(defn char-count [c cs]
  (count (filter #(= c %) cs)))

; Q020: 文字列から小文字を数える関数lowersを書け。
;; (defn lowers [s]
;;   (count (re-seq #"[a-z]" s)))
(defn lowers [cs]
  (count (filter #(Character/isLowerCase %) cs)))

; Q021: Unicodeコードポイント（整数、'a'が0）を文字に変換する関数int2letを書け。
;(defn int2let [n]
;  (char (+ n (int \a))))
(defn int2let [n]
  (char (+ n (int \a))))

; Q022: 文字をUnicodeのコードポイント（整数）に変換する関数let2intを書け。（'a'が0番とする）
(defn let2int [c]
  (- (int c) (int \a)))

; Q023: 小文字をシフト数だけずらすshiftを書け。
;    (循環すること。)
;     ex) 'z'に対し、1ならば'a'となる）（小文字のみ対象とすること）
(defn shift [n c]
  (let [alph-cnt (range (int \a) (inc (int \z)))
        m (let2int c)]
    (int2let (rem (+ n m) alph-cnt))))

; Q024: 与えられたシフト数で文字列を暗号化する関数my-encodeを書け。
(defn my-encode [n cs]
  (clojure.string/join "" (map #(shift n %) cs)))

; Q025: 百分率を計算し、浮動小数点数として返す関数percentを書け。
(defn percent [n m]
  (float (* (/ n m) 100)))

; Q026: 任意の文字列に対して文字の出現頻度表を返す関数freqsを書け。（lowersとcountとpercentを用いる）
; (defn freqs [cs]
;   (let [alph-cs (map char (range (int \a) (inc (int \z))))]
;     (for [c alph-cs] (percent (count (for [c' cs :when (= c c')] c')) (lowers cs)))))
;(defn freqs [s]
;  (let [cs (map char (range (int \a) (inc (int \z))))
;        cnt (lowers s)]
;    (map (fn [c] (percent (count (filter #((hash-set c) %) s)) cnt)) cs)))
;; (defn freqs [cs]
;;   (let [cs' (map char (range (int \a) (int \z)))
;;         cnt (lowers cs)]
;;     (map (fn [c] (percent (count (filter #(= c %) cs)) cnt)) cs')))
(defn freqs [cs]
  (let [alph (map char (range (int \a) (inc (int \z))))
        length (lowers cs)]
    (map (fn [c] (percent (count (filter #(= c %) cs)) length)) alph)))

; Q027: カイ二乗検定を行う関数chisqrを書け。
(defn chisqr [ob ex]
  (reduce +
          (map (fn [o e] (float (/ (Math/pow (- e o) 2) e)))
               ob
               ex)))

; Q028: 文字リストの要素をnだけ左に回転させる関数rotateを書け。（リストの先頭は末尾に接続していると考える）
(defn rotate [n cs]
  (clojure.string/join (concat (drop n cs) (take n cs))))

; Q029: 1から100までの二乗の和を計算する式をリスト内包表記を用いて書け。
; (reduce + (for [n (range 1 (inc 100))] (Math/pow n 2)))
(reduce + (map #(Math/pow % 2) (range 1 (inc 100))))

; Q030: 二つの生成器を持つリスト内包表記[(x,y) | x <- [1,2,3], y <- [4,5,6]]は(*)、
;    一つの生成器を持つリスト内包表記二つでも表現出来る事を示せ。
; * (for [x [1 2 3] y [4 5 6]] [x y])
(apply concat (for [x [1 2 3]] (for [y [4 5 6]] [x y])))

; Q031: 与えられた上限までに含まれる完全数全てを算出する関数perfectsをリスト内包表記と関数factorsを使って定義せよ。
;    完全数：自分自身をのぞく約数の和が自分自身と等しい整数
(defn perfects [n]
  (for [n' (range 2 n) :when (= n' (- (reduce + (factors n')) n'))] n'))


; Q032: ピタゴラス数のリスト(組み合わせ)を生成する関数pythsをリスト内包表記を使って定義せよ。ただし、ピタゴラス数の要素は与えられた上限n以下であるとする。
;; (defn pyths [n]
;;   (letfn [(range-closed [n] (range 1 (inc n)))]
;;     (for [
;;       x (range-closed n)
;;       y (range-closed n)
;;       z (range-closed n)
;;       :when
;;         (and
;;           (= (Math/pow x 2) (+ (Math/pow y 2) (Math/pow z 2)))
;;           (< y z))]
;;       [x y z])))
(defn pyths [n]
  (let [ns (range 1 (inc n))]
    (for [x ns y ns z ns :when (and (< x y) (= (+ (Math/pow x 2) (Math/pow y 2)) (Math/pow z 2)))] [x y z])))

; Q033: ある要素のみからなるリストを生成する関数my-replicateを書け。(直接の再帰、それを使わないバージョンをそれぞれ書け)
;    ex) >replicate 3 True
;        [True, True, True]
;(defn my-replicate [n x]
;  (if (zero? n)
;    []
;    (cons x (my-replicate (dec n) x))))
(defn my-replicate [n x]
  (letfn [(my-replicate' [n acc]
            (if (zero? n)
              acc
              (my-replicate' (dec n) (cons x acc))))]
    (my-replicate' n [])))

; Q034: 二つの整数のリストの内積を求める関数scalarproductをリスト内包表記を用いて書け。
; A
(defn scalarproduct [ns ms]
  (reduce + (for [[n m] (map vector ns ms)] (* n m))))


; Q035: 要素を逆転する関数myreverseを書け。
; A
(defn myreverse [coll]
  (if-not (seq coll)
    []
    (conj (myreverse (rest coll)) (first coll))))

; Q036: 整列された要素を持つリストに要素を挿入する関数myinsertを書け。
; A
(defn myinsert [x xs]
  (if (not (seq xs))
    [x]
    (if (<= x (first xs))
      (cons x xs)
      (cons (first xs) (myinsert x (rest xs))))))
; Q037: 関数myinsertを用いてリストのソートを行う"挿入ソート"を行う関数isortを書け。
; A
(defn isort [xs]
  (if-not (seq xs)
    []
    (myinsert (first xs) (isort (rest xs)))))

; Q038: dropを再帰を用いて自作せよ。
; A
(defn mydrop [n coll]
  (if (zero? n)
    coll
    (mydrop (dec n) (rest coll))))

; Q039: zipを再帰を用いて自作せよ。
; A
; (defn myzip [xs ys]
;   (if (or (empty? xs) (empty? ys))
;     []
;     (cons (vector (first xs) (first ys)) (myzip (rest xs) (rest ys)))))
;(defn myzip [xs ys]
;  (if (and (seq xs) (seq ys))
;    (cons (vector (first xs) (first ys)) (myzip (rest xs) (rest ys)))
;    []))
(defn myzip [xs ys]
  (cond
    (not (seq xs)) []
    (not (seq ys)) []
    :else (cons [(first xs) (first ys)] (myzip (rest xs) (rest ys)))))

; Q040: evenとoddを相互再帰を用いて自作せよ。(declareを自作してそれを用いよ。)
;    ヒント：0は偶数、-3は奇数
; A
(defmacro mydeclare [& expr]
  `(do ~@(map #(list 'def %) expr)))
(mydeclare myeven? myodd?)
(defn myeven? [n]
  (or (zero? n)
      (myodd? (dec (Math/abs n)))))
(defn myodd? [n]
  (and (not (zero? n))
       (myeven? (dec (Math/abs n)))))

; Q041: 0以上の整数nに対し、n番目のフィボナッチ数を求める関数fibonacciを書け。
; A
(defn fibonacci [n]
  (case n
    0 0
    1 1
    (+ (fibonacci (- n 1)) (fibonacci (- n 2)))))

; Q042: qsortを再帰を用いて書け。
; A
(defn qsort [xs]
  (if-not (seq xs)
    []
    (let [x (first xs)
          xs' (rest xs)
          lt (for [x' xs' :when (< x' x)] x')
          ge (for [x' xs' :when (>= x' x)] x')]
      (concat (qsort lt) [x] (qsort ge)))))

; Q043: リストから偶数の位置の要素を取り出す関数evensと、奇数の位置の要素を取り出す関数oddsを相互再帰を用いて書け。
; A
(declare evens odds)
(defn evens [xs]
  (if-not (seq xs)
    []
    (odds (rest xs))))
(defn odds [xs]
  (if-not (seq xs)
    []
    (cons (first xs) (evens (rest xs)))))

; Q044: initを自作せよ。
; A
; (defn init [xs]
;   (if (or (empty? xs) (= (count xs) 1))
;     []
;     (cons (first xs) (init (rest xs)))))
(defn my-init [coll]
  (if-not (seq (rest coll))
    []
    (cons (first coll) (my-init (rest coll)))))

; Q045: elemを再帰を用いて自作せよ。
;       elem :: Eq a => a -> [a] -> Bool
; A
(defn elem [x xs]
  (if-not (seq xs)
    false
    (or (= x (first xs))
        (elem x (rest xs)))))

; Q046: !!の前�����������記法版のindex関数を再帰を用いて自作せよ。(my-index)
; A
(defn my-index [coll n]
  (if (zero? n)
    (first coll)
    (my-index (rest coll) (dec n))))

; Q047: 整列されたリストを二つとり、一つの整列されたリストにして返す関数mergeを自作せよ。
;    insertやisort等、整列されたリストを処理する関数は用いてはならない。
;    ex) merge [2,5,6] [1,3,4] ==> [1,2,3,4,5,6]
; A
(defn my-merge [xs ys]
  (cond
    (not (seq xs)) ys
    (not (seq ys)) xs
    :default (let [x (first xs)
                   y (first ys)]
               (if (<= x y)
                 (cons x (my-merge (rest xs) ys))
                 (cons y (my-merge xs (rest ys)))))))

; Q048: 関数my-mergeを用いてマージソートを実行する関数msortを再帰を用いて書け。
;    マージソートは、引数のリストを二つに分割し、それぞれを整列した後、再び一つに戻す事で、整列を実現する。
;    最初に、リストを半分に分割する関数halveを書け。
; A
(defn msort [coll]
  (cond
    (not (seq coll)) []
    (= (count coll) 1) coll
    :else
    (let [[xs ys] (halve coll)]
      (my-merge (msort xs) (msort ys)))))

; Q049: replicateを再帰を用いて自作せよ。(my-replicate-rec [n x])
; A
(defn my-replicate-rec [n x]
  (if (zero? n)
    []
    (cons x (my-replicate-rec (dec n) x))))

; Q050: 負でない整数に対する累乗演算を行うmyを定義せよ。
; A
(defn my [n m]
  (if (zero? m)
    1
    (* n (my n (dec m)))))

; Q051: mapをリスト内包表記を用いて自作せよ。
; A
(defn my-map [f coll]
  (for [x coll] (f x)))

; Q052: filterをリスト内包表記を用いて自作せよ。
; A
(defn my-filter [f coll]
  (for [x coll :when (f x)] x))

; Q053: mapを再帰を用いて自作せよ。(my-map-recur)
; A
(defn my-map-recur [f coll]
  (if-not (seq coll)
    []
    (cons (f (first coll)) (my-map-recur f (rest coll)))))

; Q054: リストの先頭から述語を満たす連続した要素を取り除く関数dropWhileを自作せよ。
; A:
(defn my-drop-while [p xs]
  (if (or (empty? xs) (not (p (first xs))))
    xs
    (my-drop-while p (rest xs))))

; Q055: filterを再帰を用いて自作せよ。(my-filter-recur)
; A
;(defn my-filter-recur [p coll]
;  (if-not (seq coll)
;    []
;    (let [x (first coll)
;          xs (rest coll)]
;      (case (p x)
;        true (cons x (my-filter-recur p xs))
;        false (my-filter-recur p xs)))))
(defn my-filter-recur [p xs]
  (if-not (seq xs)
    []
    (let [x (first xs)
          xs' (rest xs)]
      (if (p x)
        (cons x (my-filter-recur p xs'))
        (my-filter-recur p xs')))))

; Q056: リストの先頭から述語を満たす連続した要素を取り出す関数takeWhileを自作せよ。(my-take-while)
; A:
(defn my-take-while [p coll]
  (if (not (and (seq coll) (p (first coll))))
    []
    (cons (first coll) (my-take-while p (rest coll)))))

; Q057: 以下の様に使用できる関数foldrを自作せよ。(my-foldr)
; cons = foldr (:) []
; sum = foldr (+) 0
; product = foldr (*) 1
; or = foldr (||) False
; and = foldr (&&) True
; A:
; myFoldr :: (a -> b -> b) -> b -> [a] -> b
; myFoldr _ x' [] = x'
; myFoldr f x' (x:xs) = f x (myFoldr f x' xs)
(defn my-foldr [f x coll]
  (if-not (seq coll)
    x
    (f (first coll) (my-foldr f x (rest coll)))))

; Q057: ビットのリストで表現される二進表記を整数に変換する関数bit2intを書け。
;    ・iterateを用いること
;    ・二進表記は逆順であること
; type Bit = Int
; bit2int :: [Bit] -> Int
; bit2int bits = sum [b * w | (b, w) <- zip bits weights]
;                 where
;                   weights = iterate (*2) 1
; my answer 2014/05/15
; (defn bit2int [bs]
;   (letfn [(bit2int' [bs i]
;     (if (empty? bs)
;       0
;       (+ (* (first bs) (Math/pow 2 i)) (bit2int' (rest bs) (inc i)))))]
;     (bit2int' bs 0)))
(defn bit2int [bits]
  (reduce + (map #(* %1 %2) bits (map #(Math/pow 2 %) (range)))))

; Q058: 負でない整数を二進表記へ変換する関数int2bitを書け。(0は正の整数ではない)
; A
; (defn int2bit [n]
;   (if (zero? n)
;     [0]
;     (let [ms (reverse (take-while #(<= (Math/pow 2 %) n) (iterate inc 0)))]
;       (letfn [(r' [ms n]
;         (println "ms: " ms ", n:" n)
;         (if (empty? ms)
;           []
;           (cons (if (>= n (Math/pow 2 (first ms))) 1 0) (r' (rest ms) (- n (if (>= n (Math/pow 2 (first ms))) (Math/pow 2 (first ms)) 0))))))]
;         (r' ms n)))))
(defn int2bit [n]
  (if (zero? n)
    []
    (cons (mod n 2) (int2bit (quot n 2)))))

; Q059: 二進表記が必ず8ビットになるように切り詰めたり適切な数の0を詰め込んだりする関数make8を書け。
; A
; my answer 2014/05/19
; (defn make8 [bs]
;   (concat bs (replicate (- 8 (count bs)) 0)))
; (defn make8 [bs]
;   (vec (take 8 (concat bs (replicate 7 0)))))
(defn make8 [bs]
  (take 8 (concat bs (repeat 0))))

; Q060: ビット列を8ビットの二進表記に分割する関数chop8を書け。
; A
(defn chop8 [bs]
  (if (empty? bs)
    []
    (lazy-seq (cons (make8 (take 8 bs)) (chop8 (drop 8 bs))))))

; Q061: ビットのリストを文字列に復号する関数decodeを書け。
;    リストを分割し、二進表記をUnicodeのコードポイント（整数）へ変換し、文字へ直して、全体として文字列にする。
;    関数合成を用いて実装せよ。
; A
(defn decode [bs]
  (apply str (map (comp char bit2int) (chop8 bs))))

; Q062: 文字列をビット列に符号化する関数encodeを書け。
;    それぞれの文字列をunicodeのコードポイント（整数）に変換し、さらに8ビットの二進表記に直して、全体を連結することで、ビットのリストを作る。高階関数mapと関数合成を用いて実装せよ。
; A
(defn encode [cs]
  (apply concat (map (comp make8 int2bit int) cs)))

; Q063: 関数allを自作せよ。(my-all)
; Prelude.all
; all :: (a -> Bool) -> [a] -> Bool
; all f xs
;     xs の要素 x について、f x がすべて True なら True。
;     see also: any, and
;         all (==1) [5,4,3,2,1]   = False
;         all (==1) [1,1,1]       = True
;         all (==1) []            = True
; A
(defn my-all [p coll]
  (if-not (seq coll)
    true
    (and (p (first coll)) (my-all p (rest coll)))))

; Q064: 関数anyを自作せよ。(my-any)
; Prelude.any
; any :: (a -> Bool) -> [a] -> Bool
; any f xs
;     xs のいずれかの要素 x について f x が True ならば True。
;     see also: all, or
;         any (== 1) [5, 4, 3, 2, 1]   = True
;         any (== 1) [5, 4, 1, 2, 3]   = True
;         any (== 1) [5, 4, 3, 2]      = False
; A
(defn my-any [p coll]
  (if-not (seq coll)
    false
    (or (p (first coll)) (my-any p (rest coll)))))

; Q065: 暗号化された文字列は手に入れたが、シフト数は分からないとしよう。暗号文を解読するためにシフト数を推測したい。
;    これは次のように実現できる。すなわち暗号文に対する文字の出現頻度表を作り、この表を左に回転させながら、
;    期待される文字の出現頻度表に対するカイ二乗検定の値を計算する。そして、算出されたカイ二乗検定の値のリストの中で、
;    最小の値の位置をシフト数とする。
;    以上を実行する関数crackを書け。
; (def table [8.2 1.5 2.8 4.3 12.7 2.2 2.0 6.1 7.0 0.2 0.8 4.0 2.4 6.7 7.5 1.9 0.1 6.0 6.3 9.1 2.8 1.0 2.4 0.2 2.0 0.1])

; (defn let2int [c]
;   (- (int c) (int \a)))

; (defn int2let [n]
;   (char (+ n (int \a))))

; (defn percent [n m]
;   (float (* (float (/ n m)) 100)))

; (defn rotate [n cs]
;   (concat (drop n cs) (take n cs)))

; (defn char-count [c cs]
;   (reduce + (for [c' cs :when (= c c')] 1)))

; (defn freqs [cs]
;   (for [c (map int2let (range 26)) :when (Character/isLowerCase c)] (percent (char-count c cs) (count cs))))

; (defn shift [n c]
;   (if (Character/isLowerCase c)
;     (int2let (mod (+ (let2int c) n) 26))
;     c))

; (defn chisqr [os es]
;   (reduce + (for [[o e] (map vector os es)] (/ (Math/pow (- o e) 2) e))))

; (defn indexed [xs]
;   (map vector (iterate inc 0) xs))

; (defn crack [cs]
;   (let [
;     rotated-tables (for [n (range 26)] (rotate n (freqs cs)))
;     indexed-chisqr (for [[idx table'] (indexed rotated-tables)] [idx (chisqr table' table)])
;     n (first (reduce (fn [[i1 c1] [i2 c2]] (if (< c1 c2) [i1 c1] [i2 c2])) indexed-chisqr))]
;     (apply str (map #(shift (- n) %) cs))))

(def alpha-low-case
  (map char (range (int \a) (inc (int \z)))))

(defn shift [n c]
  (if-not ((set alpha-low-case) c)
    c
    (char (+ (rem (+ (- (int c) (int \a)) n) 26) (int \a)))))

(defn shift-string [s n]
  (apply str (map #(shift n %) s)))

(defn round-shift-string [s]
  (map #(shift-string s %) (range 0 26)))

(defn indexed [xs]
  (apply hash-map (interleave (iterate inc 0) xs)))

(defn min-val-index [coll]
  (let [v (apply min (vals coll))]
    (first (first (filter #(= v (val %)) coll)))))

(defn freq-table [s]
  (for [c alpha-low-case] (float (/ (count (filter (hash-set c) s)) (count s)))))

(defn guess-shift-count [obs ex]
  (min-val-index (indexed (map #(chisqr (freq-table %) ex) obs))))

(defn crack [s]
  (shift-string s (guess-shift-count (round-shift-string s) table)))

; Q066: ファイルが過去半時間の間に更新されたかどうか調べる述語recently-modified?を書け。
(defn recently-modified? [f]
  (>= (* 1000 60 30) (- (System/currentTimeMillis) (.lastModified f))))

; # 集合
(def compositions #{
                    {:name "The Art of the Fugue", :composer "J. S. Bach"}
                    {:name "Requiem", :composer "W. A. Mozart"}
                    {:name "Requiem", :composer "Giuseppe Verdi"}
                    {:name "Musical Offering", :composer "J. S. Bach"}})
(def composers #{
                 {:composer "J. S. Bach" :country "Germany"}
                 {:composer "W. A. Mozart" :country "Austria"}
                 {:composer "Giuseppe Verdi" :country "Italy"}})
(def nations #{
               {:nation "Germany" :language "Germany"}
               {:nation "Austria" :language "German"}
               {:nation "Italy" :language "Italian"}})

(use 'clojure.set)
; Q067: compositionsのキーワード:nameの別名として:titleを持つ集合を取得せよ。(set1関数の戻り値として)
(defn set1 []
  (rename compositions {:name :title}))

; Q068: compositionsから:nameが"Requiem"のレコードを抽出せよ（set2関数の戻り値として）
(defn set2 []
  (select #(= "Requiem" (:name %)) compositions))

; Q069: compositionsから:nameキーの値のみを射影せよ。（set3関数の戻り値として）
(defn set3 []
  (project compositions [:name]))

; Q070: compositionsとcomposersを自然結合せよ。（set4関数の戻り値として）
(defn set4 []
  (join compositions composers))

; Q071: composersとnationsを:countryと:nationで結合せよ。（set5関数の戻り値として）
(defn set5 []
  (join composers nations {:country :nation}))

; Q072: compositionsから:nameが"Requiem"のレコードを抽出し、composersと自然結合し、:countryキーで射影せよ。（set6関数の戻り値として）
(defn set6 []
  (project (join (select #(= "Requiem" (:name %)) compositions) composers) [:country]))

; Q073: 最底部にbottomというシンボルを持つ、任意のnレベルまでネストしたリストを作るdeeply-nested関数を書け。
(defn deeply-nested [n]
  (if (zero? n)
    'bottom
    (list (deeply-nested (dec n)))))

; Q074: 以下のコイントスの結果データ（:h 表、:t 裏）について、 表が2回続けて出たケースをカウントする関数count-heads-pairsをloop/recurを用いて書け。
; (count-heads-pairs [:h :t :t :h :h :h])
; ;= 2
(defn count-heads-pairs [coll]
  (loop [acc 0 coll coll]
    (if-not (next coll)
      acc
      (if (every? #(= :h %) (take 2 coll))
        (recur (inc acc) (rest coll))
        (recur acc (rest coll))))))

; Q075: 以下の変換を行う関数by-pairsを、lazy-seqを用いて書け。
;     変換前：[:h :t :t :h :h :h]
;     変換後：((:h :t) (:t :t) (:t :h) (:h :h) (:h :h))
(defn by-pairs [coll]
  (if-not (next coll)
    '()
    (lazy-seq (cons (take 2 coll) (by-pairs (rest coll))))))

; Q076: ホフスタッタの男女シーケンスを書け。(f, m)
;
; F(0) = 1; M(0) = 0
; F(n) = n - M(F(n-1)), n>0
; M(n) = n - F(M(n-1)), n>0
;
; また、メモ化を行い性能を改善せよ。
;
;   シーケンス中の1つの値を計算するために、2つの値を最初から計算せねばならず、そのそれぞれについてまた2つずつの値を最初から計算することになる。
;   メモ化された関数を呼ぶと、それはまず与えられた引数を、過去に計算した入力と出力のマップと比べる。もし引数が過去に与えられていたものであれば、
;   再び計算をしなくても、直ちに結果を返すことが出来る。
;
; また、式の処理時間を計測するマクロを書け（elapsed-time)
;
; また、メモ化はキャッシュが既に作られていれば再帰を途中で止めることができるけれど、
; キャッシュが空の状態で大きな数に対するmやfを計算しようとすると、
; キャッシュが作られる前にスタックが溢れてしまう。
; それを防ぐ為に、関数ではなくシーケンスを見せることでキャッシュが頭から作られるのを保証せよ。(m-seq, f-seq)
(defmacro elapsed-time [expr]
  `(let [start# (System/currentTimeMillis)]
     ~expr
     (- (System/currentTimeMillis) start#)))

(declare f m)
(defn f [n]
  (if (zero? n)
    1
    (- n (m (f (dec n))))))
(defn m [n]
  (if (zero? n)
    0
    (- n (f (m (dec n))))))

(def f (memoize f))
(def m (memoize m))

(def f-seq (map f (range)))
(def m-seq (map m (range)))


; Q077-01: s-list（シンボルとシンボルのリスト両方を要素に出来るリスト）、oldsym、newsymを引数に取り、s-listの中のoldsymをすべてnewsymに置き換える関数replace-symbolを、
; シンボル（と見られる要素）の置換を行うreplace-symbol-expression関数との相互再帰で書け。
; (replace-symbol '((a b) (((b g r) (f r)) c (d e)) b) 'b 'a)
; ;= ((a a) (((a g r) (f r)) c (d e)) a)
; この関数は深くネストした構造をあたえるとスタックを溢れさせる可能性がある。これを避ける為に遅延評価を用いること。
;
; 相互再帰版
; (declare replace-symbol replace-symbol-expression)
;
; (defn replace-symbol [coll oldsym newsym]
;   (if-not (seq coll)
;     []
;     (lazy-seq
;       (cons
;         (replace-symbol-expression (first coll) oldsym newsym)
;         (replace-symbol (rest coll) oldsym newsym)))))
;
; (defn replace-symbol-expression [sym-expr oldsym newsym]
;   (if (symbol? sym-expr)
;     (if (= sym-expr oldsym)
;       newsym
;       sym-expr)
;     (replace-symbol sym-expr oldsym newsym)))

; Q077-02: また、マルチメソッドを用いたバージョンも書け。
; マルチメソッド版
(defn- coll-or-scalar [x & _] (if (coll? x) :collection :scalar))

(defmulti replace-symbol coll-or-scalar)

(defmethod replace-symbol :collection [coll oldsym newsym]
  (if-not (seq coll)
    []
    (lazy-seq
      (cons
        (replace-symbol (first coll) oldsym newsym)
        (replace-symbol (rest coll) oldsym newsym)))))

(defmethod replace-symbol :scalar [sym oldsym newsym]
  (if (= sym oldsym) newsym sym))

; Q078: 名前（username）をパラメータとし、"{greeting-prefix}, {username}"の文字列を返す関数を返す、
;       挨拶の種類（greeting-prefix）をパラメータとする関数make-greeterを書け。
; ((make-greeter "Hello") "Yuji")
; ;= "Hello, Yuji"
; ((make-greeter "Aloha") "Yuji")
; ;= "Aloha, Yuji"
(defn make-greeter [greeting-prefix]
  (fn [username] (str greeting-prefix ", " username)))

0 1 1 2 3 5 8 13 21 34
; Q079: n番目のフィボナッチ数を返す、recurで明示的な再帰を行う関数recur-fiboを書け。
; (recur-fibo 9)
; ;= 34N
; (recur-fibo 1000000)
; ;= 195 ...(中略)... 875N
(defn recur-fibo [n]
  (letfn [(recur-fibo- [n f1 f2]
            (if (zero? n)
              f1
              (recur (dec n) f2 (+' f1 f2))))]
    (recur-fibo- n 0 1)))

; Q080: 遅延評価されるフィボナッチ数列を生成する関数lazy-seq-fiboを書け。
; A:
; my answer 2014/11/03
; (defn lazy-seq-fibo []
;   (map first (iterate (fn [[n m]] [m (+' n m)]) [0 1])))
(defn lazy-seq-fibo
  ([] (lazy-seq-fibo 1 1))
  ([n m] (lazy-seq (cons m (lazy-seq-fibo m (+' n m))))))

; Q081: 以下のように、指定したディレクトリ／ファイル以下のClojureソースファイルの（空行を除いた）行数の合計をカウントする関数clojure-locを書け。
;
; (clojure-loc (java.io.File. "C:/Dropbox/_training/clojure-master/src/clj/clojure"))
; ;= 16606
; (clojure-loc (java.io.File. "C:/Dropbox/_training/clojure-master/src/clj/clojure/core.clj"))
; ;= 6149
; [参考] https://coderwall.com/p/f1a9xa
; WHAT IS SLURP?
;   slurp is technically a fully realized result of a clojure.java.io/reader.
; WHEN SHOULD I USE SLURP?
;   When memory is not a concern.
; WHAT IS READER?
;   reader will attempt to convert its argument to a BufferedReader.
; WHEN SHOULD I USE READER
;   When a lazy sequence of the results are needed or to create a new BufferedReader.
;(use '[clojure.java.io :only [reader]])
;(defn clojure-loc [f]
;  (reduce + (for [f' (file-seq f) :when (re-seq #"\.clj$" (.getName f'))]
;              (with-open [rdr (reader f')]
;                (count (filter #(re-seq #"\S" %) (line-seq rdr)))))))
;(use '[clojure.java.io :only [reader]])
;(defn clojure-loc [f]
;  (for [f' (file-seq f) :when (re-seq #"\.clj$" (.getName f'))]
;    (reduce +
;            (with-open [rdr (reader f')]
;              (count (filter #(re-seq #"\S" %) (line-seq rdr)))))))
(use '[clojure.java.io :only [reader]])
(defn clojure-loc [dir]
  (reduce +
          (for [file (file-seq dir) :when (re-seq #"\.clj$" (.getName file))]
            (with-open [rdr (reader file)]
              (filter #(re-seq #"\S" %) (line-seq rdr))))))

; Q082: 文字列中の文字で、探すべき文字のセットにマッチするもののインデックスを得る関数index-filterを書け。（indexed関数を用いよ。）
; ([pred coll])
; ex)
; (index-filter #{\a \b} "abcdbbb")
; ;= (0 1 4 5 6)
; (index-filter #{\a \b} "xyz")
; ;= ()
(defn index-filter [p coll]
  (for [[k v] (indexed coll) :when (p v)] k))

; 以下の./resources/compositions.xmlから、作曲家（composer）の名前だけを抜き出す関数（get-composer)を書け。
; <compositions>
;   <composition composer="J. S. Bach">
;     <name>The Art of the Fugue</name>
;   </composition>
;   <composition composer="J. S. Bach">
;     <name>Musical Offering</name>
;   </composition>
;   <composition composer="W. A. Mozart">
;     <name>Requiem</name>
;   </composition>
; </compositions>
; A:
(use '[clojure.xml :as xml])
(defn get-composer [xml-file-name]
  (for [a (xml-seq (xml/parse xml-file-name)) :when (= :composition (:tag a))] (:composer (:attrs a))))

; Q: マクロand、orをmy-and、my-orとして自作せよ。
; A
;(defmacro my-and
;  ([] true)
;  ([x] x)
;  ([x & rest]
;   `(let [and# ~x]
;      (if and# (my-and ~@rest) and#))))
;
;(defmacro my-or
;  ([] false)
;  ([x] x)
;  ([x & rest]
;   `(let [or# ~x]
;      (if or# or# (my-or ~@rest)))))
(defmacro my-and
  ([] true)
  ([x] x)
  ([x & rest] `(if ~x (my-and ~@rest) false)))
(defmacro my-or
  ([] false)
  ([x] x)
  ([x & rest] `(if ~x true (my-or ~@rest))))

; Q083: 相互再帰を使って、my-odd?およびmy-even?を定義せよ。(*utのコメントアウト部分でStackOverflowエラー発生課題残*)
; A
(declare my-odd? my-even?)

(defn my-odd? [n]
  (if (zero? n)
    false
    (my-even? (dec n))))

(defn my-even? [n]
  (if (zero? n)
    true
    (my-odd? (dec n))))

; Q084: 任意のディレクトリのファイル、ディレクトリ名をシーケンスとして取得する関数list-filesを書け。
; A
(import 'java.io.File)
(defn list-files [base-dir]
  (map #(.getName %) (.listFiles (File. base-dir))))

; Q: Clojureの..マクロを真似するchainマクロを書け。
; ヒント: <TBD>引数の個数はマッチング出来る
;
; | マクロ呼び出し                     | 展開後                         |
; | ----------------------------- | ----------------------------- |
; | (chain arm getHand)           | (. arm getHand)               |
; | (chain arm getHand getFinger) | (. (. arm getHand) getFinger) |
;
; (macroexpand '(chain arm getHand))
; ;= (. arm getHand)
; (macroexpand '(chain arm getHand getFinger))
; ;= (. (. arm getHand) getFinger)
; ; example
; (chain " abc " trim length)
; ;= 3
(defmacro chain
  ([x form] `(. ~x ~form))
  ([x form & more] `(chain (. ~x ~form) ~@more)))
; my answer 2014/11/15
; (defmacro chain [obj & methods]
;   (case (count methods)
;     0 nil
;     1 `(. ~obj ~(first methods))
;     `(chain (. ~obj ~(first methods)) ~@(rest methods))))

; Q085: n番目のフィボナッチ数を返す、末尾再帰を用いたtail-fibo関数を書け。
; A
; user=> (tail-fibo 1000000)
; StackOverflowError   java.math.BigInteger.add (:-1)
; *末尾再帰で書いたにも関わらず、大きなnを与えるとやはり落ちてしまう。
;  Haskellのような関数型言語は簡単にTCOを行えるのだが、JVMは関数型言語向けには設計されていないので、
;  JVM上で直接走る言語で、自動的にTCOを行える言語は存在しない。TCOが無いことは残念なことではあるが、
;  そのせいで関数型プログラミングが全くできなくなってしまうというわけではない。
;  Clojureは以下のいくつかの現実的な代替方法を用意している。
; - recurを使った明示的な自己再帰
; - 遅延シーケンス
; - trampolineを使った明示的な相互再帰
(defn tail-fibo [n]
  (letfn [(tail-fibo- [n m l]
            (if (zero? l)
              n
              (recur m (+' n m) (dec l))))]
    (tail-fibo- 0 1 n)))

; Q086: *out*を一時的に新たなStringWriterに束縛し、exprsを評価して、評価中に*out*へ出力されたものを文字列にして返すwith-out-strマクロを自作せよ。(my-with-out-str)
; (my-with-out-str (print "hello, ") (print "world"))
; ;= "hello, world"
; refer: [Let vs. Binding in Clojure](http://stackoverflow.com/questions/1523240/let-vs-binding-in-clojure)
; A
;(defmacro my-with-out-str [& exprs]
;  `(binding [*out* (java.io.StringWriter.)]
;     (do ~@exprs)
;     (str *out*)))
(defmacro my-with-out-str [& exprs]
  `(binding [*out* (java.io.StringWriter.)]
    (do ~@exprs)
    (str *out*)))

; Q087: 任意のファイルの空行を除いた行数を表示する関数count-not-empty-lineを書け。
; A
(use '[clojure.java.io :only [reader]])
(defn count-not-empty-line [f]
  (with-open [rdr (reader f)]
    (count (filter #(re-find #"\w" %) (line-seq rdr)))))

; Q088: 以下の動作をする関数count-runsをpartitionを用いて書け。（先立って、filterしてcountするcount-if関数を書け。また、関数合成と部分適用も使用せよ。）
; (count-runs 2 #(= :h %) [:h :t :t :h :h :h])
; ;= 2
; (count-runs 2 #(= :t %) [:h :t :t :h :h :h])
; ;= 1
; (count-runs 3 #(= :h %) [:h :t :t :h :h :h])
; ;= 1
(def count-if (comp count filter))
(defn count-runs [n p coll]
  (count-if #(every? p %) (partition n 1 coll)))

; Q089: シーケンスライブラリの関数であるiterateを用いてフィボナッチ数列を生成する関数fiboを書け。
; この関数は以下のように大きな値に対しても動作する。
; (take 10 (fibo))
; ;= (0 1 1 2 3 5 8 13 21 34)
; (rem (nth (fibo) 1000000) 1000)
; ;= 875N
; A
(defn fibo []
  (map first (iterate (fn [[n m]] [m (+' n m)]) [0 1])))

; Q090: n番目のフィボナッチ数を返す、単純な再帰を使ったstack-consuming-fibo関数を書け。
; (stack-consuming-fibo 9)
; ;= 34
; (stack-consuming-fibo 1000000N)
; ;= StackOverflowError   clojure.lang.Numbers.toBigInt (Numbers.java:249)
; *再帰のせいで、n>1に対するstack-consuming-fibo1回の呼び出しはさらにstack-consuming-fibo2回の呼び出しを引き起こす。
; JVMレベルではこれらの呼び出しはJavaのメソッド呼び出しになり、各呼び出しごとにスタックフレームというデータ構造がアロケートされる。
; stack-consuming-fiboはnに比例したスタックフレームを作り、いずれJVMスタックを使い尽くして先の例で見られたStackOverflowErrorを引き起こす。
; Clojureの関数呼び出しは常にスタックフレームを作りスタック領域を使うのでスタック消費型と呼ばれる。
; Clojureでは、stack-consuming-fiboがやっているようなスタックを消費する再帰はほぼ常に避けるべきだ。
; A
(defn stack-consuming-fibo [n]
  (case n
    0 0
    1 1
    (+ (stack-consuming-fibo (- n 1))
       (stack-consuming-fibo (- n 2)))))

; Q091: 文字列がブランクかどうか調べるblank?関数を書け。
(defn blank? [cs]
  (every? #(Character/isWhitespace %) cs))

; Q092: timeマクロの変種で、何回もの実行結果を後で集めやすいようにしたbenchというマクロを書け。
; ; (bench (str "a" "b"))
; {:result "ab", :elapsed 53026}
; ; は次のとおり展開される
; (let [start (System/nanoTime)
;       result (str "a" "b")]
;   {:result result :elapsed (- (System/nanoTime) start)})
(defmacro bench [expr]
  `(let [start# (System/nanoTime)
         result# ~expr]
     {:result result# :elapsed (- (System/nanoTime) start#)}))

; Q093: Write a function which returns the total number of elements in a sequence.(p22)
; Special Restrictions
; count
; (= (__ '(1 2 3 3 1)) 5)
; (= (__ "Hello World") 11)
; (= (__ [[1 2] [3 4] [5 6]]) 3)
; (= (__ '(13)) 1)
; (= (__ '(:a :b :c)) 3)
(defn p22 [coll]
  (reduce (fn [x _] (inc x)) 0 coll))

; Q094: Write a function which reverses a sequence.(p23)
; Special Restrictions
; reverse
; rseq
; (= (__ [1 2 3 4 5]) [5 4 3 2 1])
; (= (__ (sorted-set 5 7 2 7)) '(7 5 2))
; (= (__ [[1 2][3 4][5 6]]) [[5 6][3 4][1 2]])
; my answer 20161106
;(defn p23 [xs]
;  (if (not (seq xs))
;    []
;    (conj (p23 (vec (rest xs))) (first xs))))
(defn p23 [coll]
  (reduce conj '() coll))

; Q095: Write a function which returns the first X fibonacci numbers.(p26)
; (= (__ 3) '(1 1 2))
; (= (__ 6) '(1 1 2 3 5 8))
; (= (__ 8) '(1 1 2 3 5 8 13 21))
; A:
(defn p26 [n]
  (take n (map first (iterate (fn [[n m]] [m (+ n m)]) [1 1]))))

; Q096: Write a function which returns true if the given sequence is a palindrome.(p27)
; Hint: "racecar" does not equal '(\r \a \c \e \c \a \r)
; (false? (__ '(1 2 3 4 5)))
; (true? (__ "racecar"))
; (true? (__ [:foo :bar :foo]))
; (true? (__ '(1 1 3 3 1 1)))
; (false? (__ '(:a :b :c)))
(defn p27 [coll]
  (= (seq coll) (reverse coll)))

; Q097: Write a function which flattens a sequence.(p28)
; Special Restrictions
; flatten
; (= (__ '((1 2) 3 [4 [5 6]])) '(1 2 3 4 5 6))
; (= (__ ["a" ["b"] "c"]) '("a" "b" "c"))
; (= (__ '((((:a))))) '(:a))
; A:
(defn p28 [coll]
  (when (seq coll)
    (let [x (first coll)
          coll' (rest coll)]
      (if (coll? x)
        (concat (p28 x) (p28 coll'))
        (cons x (p28 coll'))))))

; Q098: Write a function which removes consecutive duplicates from a sequence.(p30)
; (= (apply str (__ "Leeeeeerrroyyy")) "Leroy")
; (= (__ [1 1 2 3 3 2 2 3]) '(1 2 3 2 3))
; (= (__ [[1 2] [1 2] [3 4] [1 2]]) '([1 2] [3 4] [1 2]))
; A:
(defn p30 [coll]
  (reduce (fn [acc x] (if (= (last acc) x) acc (conj acc x))) [] coll))

; Q099: Write a function which packs consecutive duplicates into sub-lists.(p31)
; (= (__ [1 1 2 1 1 1 3 3]) '((1 1) (2) (1 1 1) (3 3)))
; (= (__ [:a :a :b :b :c]) '((:a :a) (:b :b) (:c)))
; (= (__ [[1 2] [1 2] [3 4]]) '(([1 2] [1 2]) ([3 4])))
; A:
;(defn p31 [coll]
;  (partition-by identity coll))
 (defn p31 [coll]
   (when (seq coll)
     (let [x (first coll)]
       (cons (take-while #(= x %) coll) (p31 (drop-while #(= x %) coll))))))

; Q100: Write a function which duplicates each element of a sequence.(p32)
; (= (__ [1 2 3]) '(1 1 2 2 3 3))
; (= (__ [:a :a :b :b]) '(:a :a :a :a :b :b :b :b))
; (= (__ [[1 2] [3 4]]) '([1 2] [1 2] [3 4] [3 4]))
; (= (__ [[1 2] [3 4]]) '([1 2] [1 2] [3 4] [3 4]))
(defn p32 [coll]
  (mapcat #(list % %) coll))

; Q101: Write a function which replicates each element of a sequence a variable number of times.(p33)
; (= (__ [1 2 3] 2) '(1 1 2 2 3 3))
; (= (__ [:a :b] 4) '(:a :a :a :a :b :b :b :b))
; (= (__ [4 5 6] 1) '(4 5 6))
; (= (__ [[1 2] [3 4]] 2) '([1 2] [1 2] [3 4] [3 4]))
; (= (__ [44 33] 2) [44 44 33 33])
(defn p33 [coll n]
  (mapcat #(replicate n %) coll))

; Q102: Write a function which creates a list of all integers in a given range.(p34)
; Special Restrictions
; range
; (= (__ 1 4) '(1 2 3))
; (= (__ -2 2) '(-2 -1 0 1))
; (= (__ 5 8) '(5 6 7))
(defn p34 [n m]
  (when (< n m)
    (cons n (p34 (inc n) m))))

; Q103: Write a function which takes a variable number of parameters and returns the maximum value.(p38)
; (= (__ 1 8 3 4) 8)
; (= (__ 30 20) 30)
; (= (__ 45 67 11) 67)
(defn p38 [& coll]
  (reduce (fn [n m] (if (< n m) m n)) coll))

; Q104: Write a function which takes two sequences and returns the first item from each, then the second item from each, then the third, etc.(p39)
; Special Restrictions
; interleave
; (= (__ [1 2 3] [:a :b :c]) '(1 :a 2 :b 3 :c))
; (= (__ [1 2] [3 4 5 6]) '(1 3 2 4))
; (= (__ [1 2 3 4] [5]) [1 5])
; (= (__ [30 20] [25 15]) [30 25 20 15])
(defn p39 [xs ys]
  (mapcat list xs ys))

; Q105: Write a function which separates the items of a sequence by an arbitrary value.(p40)
; Special Restrictions
; interpose
; (= (__ 0 [1 2 3]) [1 0 2 0 3])
; (= (apply str (__ ", " ["one" "two" "three"])) "one, two, three")
; (= (__ :z [:a :b :c :d]) [:a :z :b :z :c :z :d])
(defn p40 [x coll]
  (if-not (seq (rest coll))
    [(first coll)]
    (cons (first coll) (cons x (p40 x (rest coll))))))
; or
; (defn p40 [x coll]
;   (rest (interleave (repeat x) coll)))
; or
; (defn p40 [x coll]
;   (drop-last (reduce #(concat % [%2 x]) [] coll)))

; Q106: Write a function which drops every Nth item from a sequence.(p41)
; (= (__ [1 2 3 4 5 6 7 8] 3) [1 2 4 5 7 8])
; (= (__ [:a :b :c :d :e :f] 2) [:a :c :e])
; (= (__ [1 2 3 4 5 6] 4) [1 2 3 5 6])
(defn p41 [coll n]
  (when (seq coll)
    (concat (take (dec n) coll) (p41 (drop n coll) n))))

; Q107: Write a function which calculates factorials.(p42)
; (= (__ 1) 1)
; (= (__ 3) 6)
; (= (__ 5) 120)
; (= (__ 8) 40320)
(defn p42 [n]
  (if (= n 1)
    1
    (* n (p42 (dec n)))))

; Q108: Write a function which reverses the interleave process into x number of subsequences.(p43)
; (= (__ [1 2 3 4 5 6] 2) '((1 3 5) (2 4 6)))
; (= (__ (range 9) 3) '((0 3 6) (1 4 7) (2 5 8)))
; (= (__ (range 10) 5) '((0 5) (1 6) (2 7) (3 8) (4 9)))
;(defn p43 [coll n]
;  (apply (partial map list) (partition n coll)))
(defn p43 [xs n]
  (apply (partial map list) (partition n xs)))


; Q109: Write a function which can rotate a sequence in either direction.(p44)
; (= (__ 2 [1 2 3 4 5]) '(3 4 5 1 2))
; (= (__ -2 [1 2 3 4 5]) '(4 5 1 2 3))
; (= (__ 6 [1 2 3 4 5]) '(2 3 4 5 1))
; (= (__ 1 '(:a :b :c)) '(:b :c :a))
; (= (__ -4 '(:a :b :c)) '(:c :a :b))
(defn p44 [n coll]
  (let [n' (mod n (count coll))]
    (concat (drop n' coll) (take n' coll))))

; my naive answer
; (defn p44 [n coll]
;   (cond
;     (= n 0) coll
;     (< n 0) (p44 (inc n) (cons (last coll) (drop-last coll)))
;     :else   (p44 (dec n) (conj (vec (rest coll)) (first coll)))))

; Q110: Write a function which takes a sequence consisting of items with different types
;    and splits them up into a set of homogeneous sub-sequences.
;    The internal order of each sub-sequence should be maintained,
;    but the sub-sequences themselves can be returned in any order
;    (this is why 'set' is used in the test cases).(p50)
; (= (set (__ [1 :a 2 :b 3 :c])) #{[1 2 3] [:a :b :c]})
; (= (set (__ [:a "foo"  "bar" :b])) #{[:a :b] ["foo" "bar"]})
; (= (set (__ [[1 2] :a [3 4] 5 6 :b])) #{[[1 2] [3 4]] [:a :b] [5 6]})
(defn p50 [coll]
  (vals (group-by type coll)))

; Q111: Given a vector of integers, find the longest consecutive sub-sequence of increasing numbers.(p53)
;    If two sub-sequences have the same length, use the one that occurs first.
;    An increasing sub-sequence must have a length of 2 or greater to qualify.
; (= (__ [1 0 1 2 3 0 4 5]) [0 1 2 3])
; (= (__ [5 6 1 3 2 7]) [5 6])
; (= (__ [2 3 3 4 5]) [3 4 5])
; (= (__ [7 6 5 4]) [])
; Rewrite using funcitons "continuous?" and "take-while".
(defn p53 [coll]
  (letfn [(continuous? [[n m]] (= (inc n) m))]
    (loop [coll coll result []]
      (if-not (seq coll)
        result
        (let [ps (partition 2 1 coll)
              result' (vec (set (apply concat (take-while continuous? ps))))]
          (recur (rest coll) (if (< (count result) (count result')) result' result)))))))
; my answer 2016/12/24
;(defn p53 [xs]
;  (letfn [(continuous? [[n m]] (= (inc n) m))
;          (concat-linked-pairs [ps]
;            (if-not (seq (next ps))
;              (first ps)
;              (cons (first (first ps)) (concat-linked-pairs (rest ps)))))
;          (p53' [xs]
;            (if-not (seq xs)
;              []
;              (let [ps' (partition 2 1 xs)
;                    ps'' (drop-while (complement continuous?) ps')]
;                (letfn [(continuous? [[n m]] (= (inc n) m))]
;                  (cons (concat-linked-pairs (take-while continuous? ps'')) (p53' (concat-linked-pairs (drop-while continuous? ps''))))))))]
;    (reduce (fn [xs ys] (if (>= (count xs) (count ys)) xs ys)) '() (p53' xs))))

; Q112: Write a function which returns a sequence of lists of x items each. Lists of less than x items should not be returned.(p54)
; Special Restrictions
; partition
; partition-all
; (= (__ 3 (range 9)) '((0 1 2) (3 4 5) (6 7 8)))
; (= (__ 2 (range 8)) '((0 1) (2 3) (4 5) (6 7)))
; (= (__ 3 (range 8)) '((0 1 2) (3 4 5)))
(defn p54 [n coll]
  (if (< (count coll) n)
    []
    (cons (take n coll) (p54 n (drop n coll)))))

; Q113: Write a function which returns a map containing the number of occurences of each distinct item in a sequence.(p55)
; Special Restrictions
; frequencies
; (= (__ [1 1 2 3 2 1 1]) {1 4, 2 2, 3 1})
; (= (__ [:b :a :b :a :b]) {:a 2, :b 3})
; (= (__ '([1 2] [1 3] [1 3])) {[1 2] 1, [1 3] 2})
(defn p55 [coll]
  (let [coll' (group-by identity coll)]
    (zipmap (keys coll') (map count (vals coll')))))
; my naive solution 2014/12/26
; (defn p55 [coll]
;   (let [coll' (group-by identity coll)
;         ks (keys coll')]
;     (reduce merge (map (fn [n] (hash-map n (count (coll' n)))) ks))))

; Q114: Write a function which removes the duplicates from a sequence. Order of the items must be maintained.(p56)
; (= (__ [1 2 1 3 1 2 4]) [1 2 3 4])
; (= (__ [:a :a :b :b :c :c]) [:a :b :c])
; (= (__ '([2 4] [1 2] [1 3] [1 3])) '([2 4] [1 2] [1 3]))
; (= (__ (range 50)) (range 50))
; Special Restrictions
; distinct
(defn p56 [coll]
  (reduce (fn [acc x] (if (some #(= x %) acc) acc (conj acc x))) [] coll))
; my answer 2016/12/30
;(defn p56 [xs]
;  (if-not (seq xs)
;    []
;    (let [x (first xs)]
;      (cons x (p56 (filter #(not (= x %)) (rest xs)))))))

; Q115: Write a function which allows you to create function compositions.
;    The parameter list should take a variable number of functions,
;    and create a function applies them from right-to-left.(p58)
; Special Restrictions
; comp
; (= [3 2 1] ((__ rest reverse) [1 2 3 4]))
; (= 5 ((__ (partial + 3) second) [1 2 3 4]))
; (= true ((__ zero? #(mod % 8) +) 3 5 7 9))
; (= "HELLO" ((__ #(.toUpperCase %) #(apply str %) take) 5 "hello world"))
(defn p58 [& fs]
  (let [[f & fs] (reverse fs)]
    (fn [& xs]
      (reduce (fn [acc f] (f acc)) (apply f xs) fs))))

; Q116: Take a set of functions and return a new function that takes a variable number of arguments
; and returns a sequence containing the result of applying
; each function left-to-right to the argument list.(p59)
; Special Restrictions
; juxt
; (= [21 6 1] ((__ + max min) 2 3 5 1 6 4))
; (= ["HELLO" 5] ((__ #(.toUpperCase %) count) "hello"))
; (= [2 6 4] ((__ :a :c :b) {:a 2, :b 4, :c 6, :d 8 :e 10}))
(defn p59 [& fs]
  (fn [& args] (for [f fs] (apply f args))))

; Q117: Write a function which behaves like reduce,
; but returns each intermediate value of the reduction.
; Your function must accept either two or three arguments,
; and the return sequence must be lazy.(p60)
; Special Restrictions
; reductions
; (= (take 5 (__ + (range))) [0 1 3 6 10])
; (= (__ conj [1] [2 3 4]) [[1] [1 2] [1 2 3] [1 2 3 4]])
; (= (last (__ * 2 [3 4 5])) (reduce * 2 [3 4 5]) 120)
(defn p60
  ([f init args]
   (if-not (seq args)
     [init]
     (lazy-seq (cons init (p60 f (f init (first args)) (rest args))))))
  ([f args] (p60 f (first args) (rest args))))

; Q118: Write a function which returns the first x number of prime numbers.(p67)
; (= (__ 2) [2 3])
; (= (__ 5) [2 3 5 7 11])
; (= (last (__ 100)) 541)
(defn p67 [n]
  (letfn [(factors [n]
            (filter (fn [m] (= 0 (mod n m))) (range 1 (inc n))))
          (prime? [n]
            (= [1 n] (factors n)))
          (primes []
            (filter prime? (drop 2 (range))))]
    (take n (primes))))


; Q119: Write a function which takes a function f and a variable number of maps.
; Your function should return a map that consists of the rest of the maps conj-ed onto the first.
; If a key occurs in more than one map,
; the mapping(s) from the latter (left-to-right) should be combined with the mapping in the result by calling
; (f val-in-result val-in-latter)(p69)
; Special Restrictions
; merge-with
; (= (__ * {:a 2, :b 3, :c 4} {:a 2} {:b 2} {:c 5})
;    {:a 4, :b 6, :c 20})
; (= (__ - {1 10, 2 20} {1 3, 2 10, 3 15})
;    {1 7, 2 10, 3 15})
; (= (__ concat {:a [3], :b [6]} {:a [4 5], :c [8 9]} {:b [7]})
;    {:a [3 4 5], :b [6 7], :c [8 9]})
(defn p69 [f init & args]
  (if-not (seq args)
    init
    (letfn [(merge' [m1 m2]
              (reduce (fn [acc [k v]] (assoc acc k (if-let [v' (acc k)] (f v' v) v))) m1 m2))]
      (apply p69 f (merge' init (first args)) (rest args)))))
; my answer 2017/02/18
; (defn p69 [f & ms]
;   (letfn [(merge-by-key [m1 m2 k]
;                         {k (f (k m1) (k m2))})
;           (merge-m [m1 m2]
;                   (let [ks (clojure.set/union (keys m1) (kyes m2))]
;                         (map (partial merge-by-key m1 m2) ks)))]
;     (reduce merge-m (first ms) (rest ms))))


; Q120: Given a string of comma separated integers,
; write a function which returns a new comma separated string that
; only contains the numbers which are perfect squares.(p74)
; (= (__ "4,5,6,7,8,9") "4,9")
; (= (__ "15,16,25,36,37") "16,25,36")
(defn p74 [s]
  (->> s
      (re-seq #"\d+")
      (filter (fn [n] (zero? (mod (Math/sqrt (Integer/parseInt n)) 1))))
      (interpose ",")
      (apply str)))

; Q121: Two numbers are coprime if their greatest common divisor equals 1.
; Euler's totient function f(x) is defined as the number of positive integers less than x which are coprime to x.
; The special case f(1) equals 1. Write a function which calculates Euler's totient function.(p75)
; (= (__ 1) 1)
; (= (__ 10) (count '(1 3 7 9)) 4)
; (= (__ 40) 16)
; (= (__ 99) 60)
; refer: http://en.wikipedia.org/wiki/Greatest_common_divisor#Using_Euclid.27s_algorithm
(defn p75 [n]
  (if (= n 1)
    1
    (letfn [(gcd [n m]
              (if (zero? m)
                n
                (gcd m (mod n m))))
            (coprime? [n m] (= 1 (gcd n m)))]
      (count (filter (fn [m] (coprime? n m)) (range 1 n))))))
; my answer 2017/03/12
; (defn p75 [n]
;   (letfn [(gcd [n m]
;               (clojure.set/intersection (set (factors n)) (set (factors m))))
;           (coprime? [n m]
;               (= #{1} (gcd n m)))]
;     (count (filter (fn [m] ((partial coprime? n) m)) (range 1 (inc n))))))

; Q122: Write a function which finds all the anagrams in a vector of words.
; A word x is an anagram of word y if all the letters in x can be rearranged in a different order to form y.
; Your function should return a set of sets, where each sub-set is a group of words which are anagrams of each other.
; Each sub-set should have at least two words. Words without any anagrams should not be included in the result.(p77)
; (= (__ ["meat" "mat" "team" "mate" "eat"])
;    #{#{"meat" "team" "mate"}})
; (= (__ ["veer" "lake" "item" "kale" "mite" "ever"])
;    #{#{"veer" "ever"} #{"lake" "kale"} #{"mite" "item"}})
(defn p77 [coll]
  (->> coll
      (group-by sort)
      (filter (fn [[k v]] (< 1 (count v))))
      vals
      (map set)
      set))

; Q123: Reimplement the function described in "Intro to Trampoline".(p78)
; Special Restrictions
; trampoline
; (= (letfn [(triple [x] #(sub-two (* 3 x)))
;           (sub-two [x] #(stop? (- x 2)))
;           (stop? [x] (if (> x 50) x #(triple x)))]
;     (__ triple 2))
;   82)
; (= (letfn [(my-even? [x] (if (zero? x) true #(my-odd? (dec x))))
;           (my-odd? [x] (if (zero? x) false #(my-even? (dec x))))]
;     (map (partial __ my-even?) (range 6)))
;   [true false true false true false])
(defn p78 [f & args]
  (let [f' (apply f args)]
    (if-not (fn? f')
      f'
      (p78 f'))))


; Q124: A number is "perfect" if the sum of its divisors equal the number itself.
; 6 is a perfect number because 1+2+3=6.
; Write a function which returns true for perfect numbers and false otherwise.(p80)
; (= (__ 6) true)
; (= (__ 7) false)
; (= (__ 496) true)
; (= (__ 500) false)
; (= (__ 8128) true)
(defn p80 [n]
  (let [factors (for [n' (range 1 n) :when (zero? (mod n n'))] n')]
    (= n (reduce + factors))))
; my answer 2017/04/07
; (defn p80 [n]
;   (= n (reduce + (drop-last (factors n)))))

; Q125: Happy numbers are positive integers that follow a particular formula:
;    take each individual digit, square it, and then sum the squares to get a new number.
;    Repeat with the new number and eventually, you might get to a number whose squared sum is 1.
;    This is a happy number. An unhappy number (or sad number) is one that loops endlessly.
;    Write a function that determines if a number is happy or not.(p86)
; (= (__ 7) true)
; (= (__ 986543210) true)
; (= (__ 2) false)
; (= (__ 3) false)
; my answer 2017/04/15
; (defn p86 [n]
;   (letfn [(convert-digits [n]
;                           (map #(Character/digit % 10) (str n)))
;           (sum-sq [ns]
;                   (reduce + (map #(* % %) ns)))
;           (is-happy [ns n]
;             (let [n (sum-sq (convert-digits n))]
;               (or (= n 1)
;                   (and (not (contains? ns n)) (is-happy (conj ns n) n)))))]
;     (is-happy #{} n)))
(defn p86 [n]
  (letfn [(_ [acc n]
            (let [ns (map #(Integer/parseInt (str %)) (str n))
                  n (reduce + (map #(* % %) ns))]
              (if (= 1 n)
                true
                (if (some #(= n %) acc)
                  false
                  (_ (cons n acc) n)))))]
    (_ [] n)))

; Q126: Write a predicate which checks whether or not a given sequence represents a binary tree.
;       (Bottom leaf node's value is always nil or false)
;       Each node in the tree must have a value, a left child, and a right child.(p95)
; (= (__ '(:a (:b nil nil) nil))
;    true)
; (= (__ '(:a (:b nil nil)))
;    false)
; (= (__ [1 nil [2 [3 nil nil] [4 nil nil]]])
;    true)
; (= (__ [1 [2 nil nil] [3 nil nil] [4 nil nil]])
;    false)
; (= (__ [1 [2 [3 [4 nil nil] nil] nil] nil])
;    true)
; (= (__ [1 [2 [3 [4 false nil] nil] nil] nil])
;    false)
; (= (__ '(:a nil ()))
;    false)
(defn p95 [n]
  (or (nil? n)
      (and (coll? n)
          (= 3 (count n))
          (let [[n' n1 n2] n]
            (and
              (p95 n1)
              (p95 n2))))))

; Q127: Let us define a binary tree as "symmetric" if the left half of the tree is the mirror image of the right half of the tree.
;    Write a predicate to determine whether or not a given binary tree is symmetric.
;    (see To Tree, or not to Tree for a reminder on the tree representation we're using).(p96)
; (= (__ '(:a (:b nil nil) (:b nil nil))) true)
; (= (__ '(:a (:b nil nil) nil)) false)
; (= (__ '(:a (:b nil nil) (:c nil nil))) false)
; (= (__ [1 [2 nil [3 [4 [5 nil nil] [6 nil nil]] nil]]
;           [2 [3 nil [4 [6 nil nil] [5 nil nil]]] nil]])
;    true)
; (= (__ [1 [2 nil [3 [4 [5 nil nil] [6 nil nil]] nil]]
;           [2 [3 nil [4 [5 nil nil] [6 nil nil]]] nil]])
;    false)
; (= (__ [1 [2 nil [3 [4 [5 nil nil] [6 nil nil]] nil]]
;           [2 [3 nil [4 [6 nil nil] nil]] nil]])
;    false)
(defn p96 [tr]
  (letfn [(rev-tr [[p l r]]
            [p (when r (rev-tr r))
            (when l (rev-tr l))])]
    (= tr (rev-tr tr))))

; Q128: Pascal's triangle is a triangle of numbers computed using the following rules:
; - The first row is 1.
; - Each successive row is computed by adding together adjacent numbers in the row above, and adding a 1 to the beginning and end of the row.
; Write a function which returns the nth row of Pascal's Triangle.(p97)
; (= (__ 1) [1])
; (= (map __ (range 1 6))
;    [     [1]
;         [1 1]
;        [1 2 1]
;       [1 3 3 1]
;      [1 4 6 4 1]])
; (= (__ 11)
;    [1 10 45 120 210 252 210 120 45 10 1])
; (fn [n]
;   (letfn [(combination [n m] (/ (reduce * (take m (iterate dec n))) (reduce * (take m (iterate dec m)))))]
;                        (map (partial combination (dec n)) (range n))))
(defn p97 [n]
  (letfn [(p-triangle [coll]
            (lazy-seq (cons coll (p-triangle (concat [1] (map (partial reduce +) (partition 2 1 coll)) [1])))))]
    (nth (p-triangle [1]) (dec n))))
; my answer 2017/05/06
; (defn p97 [n]
;   (letfn [(factorial [n m]
;                     (if (zero? m)
;                         1
;                         (* n (factorial (dec n) (dec m)))))
;           (combination [n k]
;                       (let [n' (factorial n k)
;                             k' (factorial k k)]
;                           (if (zero? k')
;                               1
;                               (/ n' k'))))]
;     (map #(combination (dec n) %) (range 0 n))))
;
; my answer 2017/05/14
; (defn p97 [n]
;   (case n
;         1 [1]
;         2 [1 1]
;         (concat [1] (map (fn [[n m]] (+ n m)) (partition 2 1 (p97 (dec n)))) [1])))

; Q129: A function f defined on a domain D induces an equivalence relation on D,as follows:
;    a is equivalent to b with respect to f if and only if (f a) is equal to (f b).
;    Write a function with arguments f and D that computes the equivalence classes of D with respect to f.(p98)
; (= (__ #(* % %) #{-2 -1 0 1 2})
;    #{#{0} #{1 -1} #{2 -2}})
; (= (__ #(rem % 3) #{0 1 2 3 4 5 })
;    #{#{0 3} #{1 4} #{2 5}})
; (= (__ identity #{0 1 2 3 4})
;    #{#{0} #{1} #{2} #{3} #{4}})
; (= (__ (constantly true) #{0 1 2 3 4})
;    #{#{0 1 2 3 4}})
(defn p98 [f coll]
  (set (map set (vals (group-by f coll)))))

; Q130: Write a function which calculates the least common multiple.
;    Your function should accept a variable number of positive integers or ratios.(p100)
; (== (__ 2 3) 6)
; (== (__ 5 3 7) 105)
; (== (__ 1/3 2/5) 2)
; (== (__ 3/4 1/6) 3/2)
; (== (__ 7 5/7 2 3/5) 210)
(defn p100 [& rs]
  (letfn [(gcd [n m]
            (if (= n m)
              n
              (if (> n m)
                (gcd m (- n m))
                (gcd n (- m n)))))
          (lcm [n m]
            (/ (* n m) (gcd n m)))]
    (reduce lcm rs)))

; Q131: When working with java, you often need to create an object with fieldsLikeThis,
;    but you'd rather work with a hashmap that has :keys-like-this until it's time to convert.
;    Write a function which takes lower-case hyphen-separated strings and converts them to camel-case strings.(p101)
; (= (__ "something") "something")
; (= (__ "multi-word-key") "multiWordKey")
; (= (__ "leaveMeAlone") "leaveMeAlone")
(defn p102 [s]
  (letfn [(_p102
            ([c] [c])
            ([c c' & cs] (if (= \- c)
                            (cons (Character/toUpperCase c') (apply _p102 cs))
                            (cons c (apply _p102 (cons c' cs))))))]
    (->> (map identity s)
          (apply _p102)
          (apply str))))
; my answer 2017/06/14
; (defn p102 [s]
;   (letfn [(p102'
;             ([] nil)
;             ([c] c)
;             ([c1 c2 & rest] (if (= c1 \-)
;                                 (str (Character/toUpperCase c2) (apply p102' rest))
;                                 (str c1 (apply p102' (cons c2 rest))))))]
;           (apply p102' (map identity s))))
; my answer 2017/06/10
; (defn p102 [s]
;   (clojure.string/replace s #"-([a-z])" #(clojure.string/upper-case (%1 1))))
; (defn p102 [s]
;   (clojure.string/replace s #"-[a-z]" (comp clojure.string/upper-case last)))

; Q132: A balanced number is one whose component digits have the same sum on the left and right halves of the number.
;    Write a function which accepts an integer n, and returns true iff n is balanced.(p115)
; (= true (__ 11))
; (= true (__ 121))
; (= false (__ 123))
; (= true (__ 0))
; (= false (__ 88099))
; (= true (__ 89098))
; (= true (__ 89089))
; (= (take 20 (filter __ (range)))
;    [0 1 2 3 4 5 6 7 8 9 11 22 33 44 55 66 77 88 99 101])
(defn p115 [n]
  (let [ns (str n)
        m (Math/ceil (/ (count ns) 2))]
    (= (reduce + (map int (take m ns)))
      (reduce + (map int (take m (reverse ns)))))))

(defn map-sum
  [acc tr]
  (when (seq tr)
        (let [n (first tr)
              ns (rest tr)]
            (if (coll? n)
                (cons (map-sum acc n) (map-sum acc ns))
                (cons {(+ acc n) n} (map-sum (+ acc n) ns))))))

(defn filter-sum
  [m tr]
  (println tr)
  (when (seq tr)
        (let [n (first tr)
              ns (rest tr)]
            (println "n: " n)
            (println "ns: " ns)
            (if (map? n)
                (let [acc (first (keys n))
                      v (first (vals n))]
                    (println "acc: " acc)
                    (println "v: " v)
                     (if (<= acc m)
                         (cons v (filter-sum m ns))
                         (filter-sum m ns)))
                (cons (filter-sum m n) (filter-sum m ns))))))

; Q133: Your friend Joe is always whining about Lisps using the prefix notation for math.
;    Show him how you could easily write a function that does math using the infix notation.
;    Is your favorite language that flexible, Joe?
;    Write a function that accepts a variable length mathematical expression consisting of numbers and the operations +, -, *, and /.
;    Assume a simple calculator that does not do precedence and instead just calculates left to right.(p135)
; (= 7  (__ 2 + 5))
; (= 42 (__ 38 + 48 - 2 / 2))
; (= 8  (__ 10 / 2 - 1 * 2))
; (= 72 (__ 20 / 2 + 2 + 4 + 8 - 6 - 10 * 9))
; (defn p135 [& coll]
;   (let [ns (take-nth 2 coll)
;         fs (take-nth 2 (rest coll))]
;     (letfn [(_ [fs ns acc]
;                (if (not (seq fs))
;                  acc
;                  (_ (rest fs) (rest ns) ((first fs) acc (first ns)))))]
;       (_ fs (rest ns) (first ns)))))
(defn p135 [n & coll]
  (reduce (fn [n [f m]] (if f (f n m) n))
          n
          (partition 2 coll)))

; Q134: Because Clojure's for macro allows you to "walk" over multiple sequences in a nested fashion,
;    it is excellent for transforming all sorts of sequences.
;    If you don't want a sequence as your final output (say you want a map),
;    you are often still best-off using for, because you can produce a sequence and feed it into a map, for example.
;    For this problem, your goal is to "flatten" a map of hashmaps.
;    Each key in your output map should be the "path"1 that you would have to take in the original map to get to a value,
;    so for example {1 {2 3}} should result in {[1 2] 3}. You only need to flatten one level of maps: if one of the values is a map,
;    just leave it alone.
;    1 That is, (get-in original [k1 k2]) should be the same as (get result [k1 k2])(p146)
; (= (__ '{a {p 1, q 2}
;          b {m 3, n 4}})
;    '{[a p] 1, [a q] 2
;      [b m] 3, [b n] 4})
; (= (__ '{[1] {a b c d}
;          [2] {q r s t u v w x}})
;    '{[[1] a] b, [[1] c] d,
;      [[2] q] r, [[2] s] t,
;      [[2] u] v, [[2] w] x})
; (= (__ '{m {1 [a b c] 3 nil}})
;    '{[m 1] [a b c], [m 3] nil})
; (defn p146 [coll]
;   (apply hash-map
;     (mapcat
;       (fn [k coll']
;         (mapcat
;           (fn [[k' v]]
;             (cons (vector k k') [v]))
;           coll'))
;       (keys coll)
;       (vals coll))))
(defn p146 [coll]
  (into {}
        (for [[k v] coll
              [k' v] v]
          [[k k'] v])))

; Q135: Write a function that, for any given input vector of numbers, returns an infinite lazy sequence of vectors,
;    where each next one is constructed from the previous following the rules used in Pascal's Triangle. For example,
;    for [3 1 2], the next row is [3 4 3 2].
;    Beware of arithmetic overflow! In clojure (since version 1.3 in 2011),
;    if you use an arithmetic operator like + and the result is too large to fit into a 64-bit integer,
;    an exception is thrown. You can use +' to indicate that you would rather overflow into Clojure's slower, arbitrary-precision bigint.(p147)
; (= (second (__ [2 3 2])) [2 5 5 2])
; (= (take 5 (__ [1])) [[1] [1 1] [1 2 1] [1 3 3 1] [1 4 6 4 1]])
; (= (take 2 (__ [3 1 2])) [[3 1 2] [3 4 3 2]])
; (= (take 100 (__ [2 4 2])) (rest (take 101 (__ [2 2]))))
(defn p147 [coll]
  (lazy-seq
    (cons coll
          (p147 (concat [(first coll)]
                        (map (fn [[n m]] (+' n m)) (partition 2 1 coll))
                        [(last coll)])))))

; Q136: Write a function which generates the power set of a given set.
;    The power set of a set x is the set of all subsets of x, including the empty set and x itself.(p85)
; (= (__ #{1 :a}) #{#{1 :a} #{:a} #{} #{1}})
; (= (__ #{}) #{#{}})
; (= (__ #{1 2 3})
;    #{#{} #{1} #{2} #{3} #{1 2} #{1 3} #{2 3} #{1 2 3}})
; (= (count (__ (intbo #{} (range 10)))) 1024)
(defn p85 [coll]
  (reduce (fn [acc x]
            (into acc
                  (map (fn [xs] (conj xs x)) acc)))
          #{#{}} coll))

; Q137: Given an input sequence of keywords and numbers, create a map such that each key in the map is a keyword,
;    and the value is a sequence of all the numbers (if any) between it and the next keyword in the sequence.(p105)
; (= {} (__ []))
; (= {:a [1]} (__ [:a 1]))
; (= {:a [1], :b [2]} (__ [:a 1, :b 2]))
; (= {:a [1 2 3], :b [], :c [4]} (__ [:a 1 2 3 :b :c 4]))
(defn p105 [[x & xs]]
  (if-not (keyword? x)
    {}
    (assoc (p105 (drop-while #(not (keyword? %)) xs))
      x
      (take-while #(not (keyword? %)) xs))))

; Q138: Write a function which returns a sequence of digits of a non-negative number (first argument) in numerical system
;    with an arbitrary base (second argument). Digits should be represented with their integer values,
;    e.g. 15 would be [1 5] in base 10, [1 1 1 1] in base 2 and [15] in base 16. (p137)
; (= [1 2 3 4 5 0 1] (__ 1234501 10))
; (= [0] (__ 0 11))
; (= [1 0 0 1] (__ 9 2))
; (= [1 0] (let [n (rand-int 100000)](__ n n)))
; (= [16 18 5 24 15 1] (__ Integer/MAX_VALUE 42))
(defn p137 [n m]
  (if (zero? n)
    [0]
    (let [ns (reverse (take-while (fn [k] (<= k n)) (map (fn [l] (Math/pow m l)) (range))))]
      (letfn [(_ [ns n]
                (if-not (seq ns)
                  []
                  (cons (int (quot n (first ns)))
                        (_ (rest ns) (rem n (first ns))))))]
        (_ ns n)))))

; Q139: Write a function that returns a lazy sequence of "pronunciations" of a sequence of numbers.
;    A pronunciation of each element in the sequence consists of the number of repeating identical numbers and the number itself.
;    For example, [1 1] is pronounced as [2 1] ("two ones"), which in turn is pronounced as [1 2 1 1] ("one two, one one").
;    Your function should accept an initial sequence of numbers, and return an infinite lazy sequence of pronunciations,
;    each element being a pronunciation of the previous element.(p110)
; (= [[1 1] [2 1] [1 2 1 1]] (take 3 (__ [1])))
; (= [3 1 2 4] (first (__ [1 1 1 4 4])))
; (= [1 1 1 3 2 1 3 2 1 1] (nth (__ [1]) 6))
; (= 338 (count (nth (__ [3 2]) 15)))
(defn p110 [coll]
  (let [result (mapcat (fn [coll'] [(count coll') (first coll')]) (partition-by identity coll))]
    (lazy-seq (cons result (p110 result)))))

; Q140: Write an oscillating iterate: a function that takes an initial value and a variable number of functions.
;    It should return a lazy sequence of the functions applied to the value in order,
;    restarting from the first function after it hits the end.(p144)
; (= (take 3 (__ 3.14 int double)) [3.14 3 3.0])
; (= (take 5 (__ 3 #(- % 3) #(+ 5 %))) [3 0 5 2 7])
; (= (take 12 (__ 0 inc dec inc dec inc)) [0 1 0 1 0 1 2 1 2 1 2 3])
; (defn p144 [n & fs]
;   (letfn [(_ [n fs]
;             (lazy-seq (cons n (_ ((first fs) n) (rest fs)))))]
;     (_ n (cycle fs))))
(defn p144 [n & fs]
  (reductions (fn [n f] (f n)) n (cycle fs)))

; Q141: Given any number of sequences, each sorted from smallest to largest, find the smallest single number which appears in all of the sequences.
;    The sequences may be infinite, so be careful to search lazily.(p108)
; (= 3 (__ [3 4 5]))
; (= 4 (__ [1 2 3 4 5 6 7] [0.5 3/2 4 19]))
; (= 7 (__ (range) (range 0 100 7/6) [2 3 5 7 11 13]))
; (= 64 (__ (map #(* % % %) (range)) ;; perfect cubes
;           (filter #(zero? (bit-and % (dec %))) (range)) ;; powers of 2
;           (iterate inc 20))) ;; at least as large as 20
(defn p108 [& coll]
  (let [vs (map first coll)]
    (if (= (apply min vs) (apply max vs))
      (first vs)
      (let [v (apply max vs)]
        (apply p108 (map (fn [coll] (if (> v (first coll))
                                      (rest coll)
                                      coll))
                         coll))))))

; Q142: Write a function which flattens any nested combination of sequential things (lists, vectors, etc.),
;    but maintains the lowest level sequential items. The result should be a sequence of sequences with only one level of nesting.(p93)
; (= (__ [["Do"] ["Nothing"]])
;    [["Do"] ["Nothing"]])
; (= (__ [[[[:a :b]]] [[:c :d]] [:e :f]])
;    [[:a :b] [:c :d] [:e :f]])
; (= (__ '((1 2)((3 4)((((5 6)))))))
;    '((1 2)(3 4)(5 6)))
(defn p93 [coll]
  (when (seq coll)
    (letfn [(lowest-seq? [coll] (and (seq coll) (not (coll? (first coll)))))]
      (let [x (first coll)
            xs (rest coll)]
        (if (lowest-seq? x)
          (cons x (p93 xs))
          (concat (p93 x) (p93 xs)))))))

; Q143: Write a function that accepts a curried function of unknown arity n. Return an equivalent function of n arguments.
;    You may wish to read this.(p158)
; (= 10 ((__ (fn [a]
;              (fn [b]
;                (fn [c]
;                  (fn [d]
;                    (+ a b c d))))))
;        1 2 3 4))
; (= 24 ((__ (fn [a]
;              (fn [b]
;                (fn [c]
;                  (fn [d]
;                    (* a b c d))))))
;        1 2 3 4))
; (= 25 ((__ (fn [a]
;              (fn [b]
;                (* a b))))
;        5 5))
; (defn p158 [f]
;   (fn [& coll]
;     (letfn [(_ [f coll]
;               (if-not (seq (rest coll))
;                 (f (first coll))
;                 (_ (f (first coll)) (rest coll))))]
;       (_ f coll))))
(defn p158 [f]
  (fn [& args] (reduce (fn [f' x] (f' x)) f args)))

; Q144: take-while is great for filtering sequences, but it limited: you can only examine a single item of the sequence at a time.
;    What if you need to keep track of some state as you go over the sequence?
;    Write a function which accepts an integer n, a predicate p, and a sequence.
;    It should return a lazy sequence of items in the list up to, but not including, the nth item that satisfies the predicate.(p114)
; (= [2 3 5 7 11 13]
;    (__ 4 #(= 2 (mod % 3))
;          [2 3 5 7 11 13 17 19 23]))
; (= ["this" "is" "a" "sentence"]
;    (__ 3 #(some #{\i} %)
;          ["this" "is" "a" "sentence" "i" "wrote"]))
; (= ["this" "is"]
;    (__ 1 #{"a"}
;          ["this" "is" "a" "sentence" "i" "wrote"]))
(defn p114 [n p coll]
  (letfn [(_ [acc n coll]
            (if (zero? n)
              (reverse (rest acc))
              (let [x (first coll)
                    xs (rest coll)]
                (_ (cons x acc)
                   (if (p x) (dec n) n)
                   xs))))]
    (_ [] n coll)))

; Q145: Write a function that takes a two-argument predicate, a value, and a collection; and returns a new collection
;    where the value is inserted between every two items that satisfy the predicate.(p132)
; (= '(1 :less 6 :less 7 4 3) (__ < :less [1 6 7 4 3]))
; (= '(2) (__ > :more [2]))
; (= [0 1 :x 2 :x 3 :x 4]  (__ #(and (pos? %) (< % %2)) :x (range 5)))
; (empty? (__ > :more ()))
; (= [0 1 :same 1 2 3 :same 5 8 13 :same 21]
;    (take 12 (->> [0 1]
;                  (iterate (fn [[a b]] [b (+ a b)]))
;                  (map first) ; fibonacci numbers
;                  (__ (fn [a b] ; both even or both odd
;                        (= (mod a 2) (mod b 2)))
;                      :same))))
(defn p132 [p v coll]
  (when (seq coll)
    (let [[x y] (take 2 coll)]
      (if (nil? y)
        [x]
        (lazy-seq
          (concat
            (if (p x y) [x v] [x])
            (p132 p v (rest coll))))))))

; Q146: This is the inverse of Problem 92, but much easier. Given an integer smaller than 4000,
;    return the corresponding roman numeral in uppercase, adhering to the subtractive principle.(p104)
;    http://www.numericana.com/answer/roman.htm#valid
; (= "I" (__ 1))
; (= "XXX" (__ 30))
; (= "IV" (__ 4))
; (= "CXL" (__ 140))
; (= "DCCCXXVII" (__ 827))
; (= "MMMCMXCIX" (__ 3999))
; (= "XLVIII" (__ 48))
(defn p104 [n]
  (letfn [(_ [n c1 c2 c3]
            (cond
              (< n 4) (repeat n c1)
              (= n 4) [c1 c2]
              (= n 9) [c1 c3]
              (> n 4) (cons c2 (repeat (- n 5) c1))))]
    (let [th (quot n 1000)
          r (rem n 1000)
          h (quot r 100)
          r (rem r 100)
          t (quot r 10)
          u (rem r 10)]
      (apply str
             (concat
               (repeat th \M)
               (_ h \C \D \M)
               (_ t \X \L \C)
               (_ u \I \V \X))))))


; Q147: You can assume that the input will be well-formed, in upper-case, and follow the subtractive principle.
;    You don't need to handle any numbers greater than MMMCMXCIX (3999), the largest number representable with ordinary letters.(p92)
; (= 14 (__ "XIV"))
; (= 827 (__ "DCCCXXVII"))
; (= 3999 (__ "MMMCMXCIX"))
; (= 48 (__ "XLVIII"))
(defn p92 [rn]
  (letfn [(_ [acc rn]
            (if-not (seq rn)
              acc
              (cond
                (re-seq #"^M+" rn) (_ (+ acc (* 1000 (count (take-while #(= \M %) rn)))) (apply str (drop-while #(= \M %) rn)))
                (re-seq #"^CD" rn) (_ (+ acc 400) (apply str (drop 2 rn)))
                (re-seq #"^CM" rn) (_ (+ acc 900) (apply str (drop 2 rn)))
                (re-seq #"^C+" rn) (_ (+ acc (* 100 (count (take-while #(= \C %) rn)))) (apply str (drop-while #(= \C %) rn)))
                (re-seq #"^D+" rn) (_ (+ acc 500 (* 100 (count (take-while #(= \C %) (rest rn))))) (apply str (drop-while #(= \C %) (rest rn))))
                (re-seq #"^XL" rn) (_ (+ acc 40) (apply str (drop 2 rn)))
                (re-seq #"^XC" rn) (_ (+ acc 90) (apply str (drop 2 rn)))
                (re-seq #"^X+" rn) (_ (+ acc (* 10 (count (take-while #(= \X %) rn)))) (apply str (drop-while #(= \X %) rn)))
                (re-seq #"^L+" rn) (_ (+ acc 50 (* 10 (count (take-while #(= \X %) (rest rn))))) (apply str (drop-while #(= \X %) (rest rn))))
                (re-seq #"^IV" rn) (_ (+ acc 4) (apply str (drop 2 rn)))
                (re-seq #"^IX" rn) (_ (+ acc 9) (apply str (drop 2 rn)))
                (re-seq #"^I+" rn) (_ (+ acc (* 1 (count (take-while #(= \I %) rn)))) (apply str (drop-while #(= \I %) rn)))
                (re-seq #"^V+" rn) (_ (+ acc 5 (* 1 (count (take-while #(= \I %) (rest rn))))) (apply str (drop-while #(= \I %) (rest rn))))
                :else 0)))]
    (_ 0 rn)))

; Q148: Given a sequence S consisting of n elements generate all k-combinations of S,
;    i. e. generate all possible sets consisting of k distinct elements taken from S.
;    The number of k-combinations for a sequence is equal to the binomial coefficient.(p103)
;    [k-combinations](https://en.wikipedia.org/wiki/Combination)
;    [binomial coefficient](https://en.wikipedia.org/wiki/Binomial_coefficient)
; (= (__ 1 #{4 5 6}) #{#{4} #{5} #{6}})
; (= (__ 10 #{4 5 6}) #{})
; (= (__ 2 #{0 1 2}) #{#{0 1} #{0 2} #{1 2}})
; (= (__ 3 #{0 1 2 3 4}) #{#{0 1 2} #{0 1 3} #{0 1 4} #{0 2 3} #{0 2 4}
;                          #{0 3 4} #{1 2 3} #{1 2 4} #{1 3 4} #{2 3 4}})
; (= (__ 4 #{[1 2 3] :a "abc" "efg"}) #{#{[1 2 3] :a "abc" "efg"}})
; (= (__ 2 #{[1 2 3] :a "abc" "efg"}) #{#{[1 2 3] :a} #{[1 2 3] "abc"} #{[1 2 3] "efg"}
;                                     #{:a "abc"} #{:a "efg"} #{"abc" "efg"}})
(defn p103 [n s]
  (letfn [(gen-tree [n s]
            (if (zero? n)
              []
              (map
                (fn [a]
                  (cons a (gen-tree (dec n) (clojure.set/difference s (hash-set a))))) s)))
          (walk-tr-in-depth [tr acc n]
            (if (zero? n)
              [acc]
              (mapcat (fn [cn] (walk-tr-in-depth
                                 (when (seq? cn) (rest cn))
                                 (conj acc (if (seq? cn) (first cn) cn))
                                 (dec n)))
                      tr)))]
    (set (map set (walk-tr-in-depth (gen-tree n s) [] n)))))

; Q149: A balanced prime is a prime number which is also the mean of the primes directly before and after it in the sequence of valid primes.
;    Create a function which takes an integer n, and returns true iff it is a balanced prime.(p116)
; (= false (__ 4))
; (= true (__ 563))
; (= 1103 (nth (filter __ (range)) 15))
(defn factors [n] (for [n' (range 1 (inc n)) :when (zero? (mod n n'))] n'))
(def factors' (memoize factors))
(defn prime? [n] (= [1, n] (factors' n)))
(def prime?' (memoize prime?))
(defn pre-prime [n] (if (or (<= n 0) (prime?' n)) n (pre-prime (dec n))))
(def pre-prime' (memoize pre-prime))
(defn post-prime [n] (if (prime? n) n (post-prime (inc n))))
(def post-prime' (memoize post-prime))
(defn p116 [n]
  ;  (let [factors (memoize (fn [n] (for [n' (range 1 (inc n)) :when (zero? (mod n n'))] n')))
  ;        prime? (memoize (fn [n] (= [1, n] (factors n))))]
  (and (prime?' n)
       ;      (let [pre-prime (memoize (fn [n] (if (or (<= n 0) (prime? n)) n (pre-prime (dec n)))))
       ;            post-prime (memoize (fn [n] (if (prime? n) n (post-prime (inc n)))))]
       (= n (/ (+ (pre-prime' (dec n)) (post-prime' (inc n))) 2))))

; テスト無し(REPLで���接書くこと)
; Q150: 2つの文字列から文字を取り出して交互にはさみこめ。またそれを元に戻せ。
; A:
; (apply str (interleave "abc" "xyz"))
; (apply str (take-nth 2 "axbycz"))
;
; Q151: 以下の数列をloop/recurを用いて生成せよ。
;    [5 4 3 2 1]
; A:
; (loop [acc [] n 5]
;   (if (zero? n)
;     acc
;     (recur (conj acc n) (dec n))))
;
; Q152: declareマクロを自作せよ。
; A:
; (defmacro my-declare [& expr]
;   `(do ~@(map #(list 'def % nil) expr)))

; FIXME
; - 4Clojure
; *To ANKI*
; https://www.4clojure.com/problem/5 <-> https://www.4clojure.com/problem/7
; https://www.4clojure.com/problem/11
; https://www.4clojure.com/problem/47
; - gpsoftのclojure本でわからなかったところをリストして復習->トレーニング化

(def my-number (ref 0))
(def smallest (ref 1))
(def biggest (ref 100))


(defn guess-my-number []
  (dosync (ref-set my-number (quot (+ (deref smallest) (deref biggest)) 2)))
  (println (deref my-number)))

(defn smaller []
  (dosync (ref-set biggest (deref my-number)))
  (guess-my-number))

(defn bigger []
  (dosync (ref-set smallest (deref my-number)))
  (guess-my-number))

(defrecord Planet [name volume])

(defrecord Planet [
                   name
                   moons
                   volume                                   ;; km^3
                   mass                                     ;; kg
                   aphelion                                 ;; km, farthest from sun
                   perihelion                               ;; km, closest to sun
                   ])

;; Positional factory function
(def earth
  (->Planet "Earth" 1 1.08321e12 5.97219e24 152098232 147098290))
;; Map factory function
(def earth
  (map->Planet {
                :name       "Earth"
                :moons      1
                :volume     1.08321e12
                :mass       5.97219e24
                :aphelion   152098232
                :perihelion 147098290}))

; 作業用
(defn is-matched-partial? []
  (let [ss (read-string (slurp "./resources/item-keys.txt"))]
    (spit "./resources/compare-result.txt" (apply str (for [[n s1 s2] ss] (str n "\t" (if (empty? (clojure.set/intersection s1 s2)) "FALSE" "TRUE") "\n"))))))




