# 流行度分析
了解更多Phoenix应用场景，我们可以单独实现命令查询职责分离(CQRS)的Q端，通过Q端能够更迅速的查询数据。

![hotel-part-2](https://user-images.githubusercontent.com/65016157/143406262-b853879b-7c24-46f7-b25f-9b1c4a182f15.png)

基于酒店预订服务的示例，我们将增加实现酒店房型关注度排行，被预订最多次的商品被标记为最流行的商品，对流行商品进行排序、分析。

在此页面上，您将学习如何：
- Spring Data JPA的使用
- EventPublish的Handle
- 数据监控

