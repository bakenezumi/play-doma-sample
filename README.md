Doma Play Sample
------------------

[本家のKotlin サンプル](https://github.com/domaframework/kotlin-sample)をScalaに焼き直してPlay2上で動くようにしたものです。

### Build

```sh
./sbt clean dao/copyResources dist
```

### Run

```sh
./sbt run
```

Play開始後
```sh
#DB作成
curl http://localhost:9000/@evolutions/apply/default?redirect=/persons

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
- 注釈処理による自動生成をコンパイル中に実行する必要があるため、3プロジェクト構成になっています。詳細はbuild.sbtを参照。

  |プロジェクト|内容|
  |---|---|
  |domala|Scala用Domaラッパー|
  |dao|注釈処理対象クラス|
  |root (app)|Doma利用アプリケーション|

- ドメインクラス、エンベッダブルクラス、エンティティクラス、及びDaoインターフェース、SQLファイルはdaoプロジェクト内に配置しないと自動生成がうまく働きません。
  - ドメインクラスはcase classで実装できます。（Name.scala参照）
  - エンベッダブルクラスはcase classで実装できます。（Address.scala参照）
  - エンティティクラスはcase classで実装できます。（Person.scala参照)
  - case classで実装したエンティティクラスはscalaコンパイル時にフィールド名が消去されてしまうため各フィールドにはParameterNameアノテーションが必要です。(どうにかしたい。。)
  - Daoインターフェースはtraitで実装できます。(PersonDao.scala参照)
  - Daoインターフェースのメソッドはエンティティクラスと同様、パラメータの名称がコンパイル時に消去されるため、ParameterNameアノテーションが必要です。
