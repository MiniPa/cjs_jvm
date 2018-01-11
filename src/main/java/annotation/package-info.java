/**
 * package-info: Annotation
 *
 * 1.本质--特殊的interface
 * 2.java提供的一种元程序中元素关联任何信息和元数据(metadata)的途径和方法
 * 3.reflection获取Annotation
 * 4.Annotation不影响程序代码执行
 *
 * 1.metadata 元数据的作用: 编写文档,代码分析,编译检查
 * 2.annotation分类:
 *  ( 标记注解,单值注解,完整注解,jdk内置注解,元注解,自定义注解 )
 *
 * 1.实现自己的依赖注入框架,要注意性能优化。注解在运行期是通过反射方式获取,存在性能消耗。
 * author: Chengjs, version:1.0.0, 2017-09-22
 */
package annotation;