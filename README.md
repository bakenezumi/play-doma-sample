Doma Play Sample
------------------

### Build

```sh
./bin/activator clean dist
```

### Run

```sh
./bin/activator clean run
```

Play開始後別コンソールにて
```sh
#DB作成
curl http://localhost:9000/@evolutions/apply/default

#検索
curl http://localhost:9000/persons

#検索
curl -X  http://localhost:9000/persons/1

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