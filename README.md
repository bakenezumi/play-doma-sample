Doma Play Sample
------------------

[本家のKotlin サンプル](https://github.com/domaframework/kotlin-sample)をScalaに焼き直してPlay2上で動くようにしたものです。

### Build

```sh
./sbt clean dist
```

### Run

```sh
./sbt run
```

Play開始後別コンソールにて
```sh
#DB作成
curl http://localhost:9000/@evolutions/apply/default

#全件検索
curl http://localhost:9000/persons

#1件検索
curl http://localhost:9000/persons/1

#登録
curl -X POST -H "Content-Type: application/json" -d '{"name":"WARD","age":20,"address":{"city":"Fukuoka","street":"Gion"}}' http://localhost:9000/persons

#更新
curl -X PUT -H "Content-Type: application/json" -d '{"id":1,"name":"SMITH","age":30,"address":{"city":"Tokyo","street":"Marunouchi"},"version":0}' http://localhost:9000/persons

#削除
curl -X DELETE  http://localhost:9000/persons/2

```


----

### 注意点
  いろいろあるけど後で書く

### ドキュメント

  未作成