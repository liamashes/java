package guava;

/**
 * 排序器[Ordering]是Guava流畅风格比较器[Comparator]的实现，它可以用来为构建复杂的比较器，以完成集合排序的功能。
 *
 * <p>1.从实现上说，Ordering实例就是一个特殊的Comparator实例。Ordering把很多基于Comparator的静态方法（如Collections.max）
 * 包装为自己的实例方法（非静态方法），并且提供了链式调用方法，来定制和增强现有的比较器。
 *
 * <p>2.创建方式： natural():对可排序类型做自然排序，如数字按大小，日期按先后排序, usingToString():按对象的字符串形式做字典排序[lexicographical
 * ordering], from(Comparator):把给定的Comparator转化为排序器, 继承Ordering: new Ordering<>()
 *
 * <p>3.链式衍生：reverse(), nullsFirst(), nullsLast(), compound(Comparator), lexicographical(),
 * onResultOf(Function), 用compound方法包装排序器时，就不应遵循从后往前读的原则。为了避免理解上的混乱，请不要把compound写在一长串链式调用的中间，
 * 你可以另起一行，在链中最先或最后调用compound。
 */
public class Ordering {}
