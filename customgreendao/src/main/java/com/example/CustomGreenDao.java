package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class CustomGreenDao {

    public static void main(String[] args) throws Exception {
        Schema schema=new Schema(1,"com.example.greendao");
        //schema.enableKeepSectionsByDefault();// 通过此 schema 创建的实体类都不会覆盖自定义的代码

        addPerson(schema);
        new DaoGenerator().generateAll(schema,"../fuseProgram/app/src/main/java-gen");
    }

    private static void addPerson(Schema schema) {

        Entity person=schema.addEntity("Person");
        //person.setHasKeepSections(true); // 此实体类不会覆盖自定义的代码
        person.addIdProperty().primaryKey();
        person.addStringProperty("name").notNull();//约束此属性不可为空
        //entity.addStringProperty("abstract").codeBeforeField("@SerializableName(_abstract)"); //当变量是java关键字时使用此序列化
        person.addIntProperty("age").columnName("_age");//实体属性名age，数据库列表_age
        //person.implementsSerializable();//序列化
        //person.implementsInterface();//实现接口
        //person.getSuperclass();//继承类
        //Property personProperty = person.addLongProperty("cardId").getProperty();
        //person.addToOne(idCard, personProperty);//一对一
        // 第一个参数为目标实体，第二个参数为目标属性，也就是 person 的主键（order 的外键）,第三个参数为属性名
        //person.addToMany(order, orderProperty, "orders");//一对多

    }


}
