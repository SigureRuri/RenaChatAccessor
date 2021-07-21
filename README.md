# Rena Chat Accessor
チャット内容に応じた処理を簡潔に記述するためのライブラリ

## 導入
RenaChatAccessorは、

1. サーバーにjarを導入する
2. 作成するプラグインにshadeする

の二つの方法を用いて扱うことができます。

### サーバーにプラグインを導入する場合
pluginsディレクトリにRenaChatAccessorを配置してください。

### お使いのプラグインにshadeする場合
onEnable内から、
```java
RenaChatAccessor.onEnable(this);
```
を行ってください。

## 使用例
```java
final ChatAccessor accessor = new ChatAccessor(player)
        // 自動的にキャンセルされるまでのtick数
        .expirationTicks(20 * 60)
        // 入力を受け取りお知らせする
        .onResponse(input -> Bukkit.broadcastMessage("お知らせ : " + input))
        // 時間切れ・ログアウト・サーバー閉鎖でキャンセルされた場合
        .onCancel(() -> player.sendMessage("有効期限切れのためキャンセルされました。"));

        // ChatAccessorを登録する。すでに登録されているChatAccessorがある場合はキャンセルして登録する
        RenaChatAccessor.getChatAccessorManager().register(accessor);
```