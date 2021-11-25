# hotel-booking

本教程展示了如何使用 Phoenix 构建酒店预订系统功能。最终用户能够进行酒店房间的选择，并且预订房间生成订单，以及了解酒店房间的流行度。
如下图所示，系统包括三个使用 phoenix 和 Kafka 作为传输机制的服务：HotelAggregate、popularity handle 和 Order Service。

![hotel-bookings](https://user-images.githubusercontent.com/65016157/143403952-c2a5bfed-adba-4233-a6f1-fb0debbd3ebf.png)

HotelAggregate: 每个酒店由一个实体聚合根表示，使用事件源保持酒店状态。当用户进行房间预订时，实体将事件保存在事件日志数据库中。Phoenix 使用命令查询职责分离(CQRS)，将读取和写入职责分开，提供分析服务所需的数据。

popularity handle: 处理所有存储于数据库中的预订事件，分析事件内容，以回答有关房间受欢迎程度的查询。

Order Service: 通过酒店预订服务生成订单，由独立的订单服务管理。

按照教程中的步骤一次一点地构建功能，然后尝试运行它。
