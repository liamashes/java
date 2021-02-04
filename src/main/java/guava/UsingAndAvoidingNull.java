package guava;

import com.google.common.annotations.Beta;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.io.Closer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * null can be ambiguous, can cause confusing errors, and is sometimes just plain unpleasant. Many
 * Guava utilities reject and fail fast on nulls, rather than accepting them blindly.
 *
 * <p>Optional -> Absent
 *
 * <p>Optional -> Present
 */
public class UsingAndAvoidingNull {
  /**
   * 1.Google底层代码库，我们发现95%的集合类不接受null值作为元素
   *
   * <p>2.Null可以表示失败、成功或几乎任何情况
   *
   * <p>3.如果你想把null作为map中某条目的值，更好的办法是 不把这一条目放到map中，而是单独维护一个”值为null的键集合” (null keys)。
   *
   * <p>4.Guava用Optional<T>表示可能为null的T类型引用: 一个Optional实例可能包含非null的引用（我们称之为引用存在），也可能什么也不包括（称之为引用缺失）。
   * 它从不说包含的是null值，而是用存在或缺失来表示。但Optional从不会包含null值引用。
   *
   * <p>5.Optional无意直接模拟其他编程环境中的”可选” or “可能”语义，但它们的确有相似之处。
   */
  public void example() {
    Optional<Integer> possible = Optional.of(5);
    possible.isPresent();
    possible.get();
  }

  /** 示例1：在iterator中用predicate找，返回optional */
  public final class Iterators {
    public <T> Optional<T> tryFind(Iterator<T> iterator, Predicate<? super T> predicate) {
      checkNotNull(iterator);
      checkNotNull(predicate);
      while (iterator.hasNext()) {
        T t = iterator.next();
        if (predicate.apply(t)) {
          return Optional.of(t);
        }
      }
      return Optional.absent();
    }
  }

  /** 示例2：判断字节源是否为空 */
  public abstract class ByteSource {
    public boolean isEmpty() throws IOException {
      Optional<Long> sizeIfKnown = sizeIfKnown();
      if (sizeIfKnown.isPresent()) {
        return sizeIfKnown.get() == 0L;
      }
      Closer closer = Closer.create();
      try {
        InputStream in = closer.register(openStream());
        return in.read() == -1;
      } catch (Throwable e) {
        throw closer.rethrow(e);
      } finally {
        closer.close();
      }
    }

    @Beta
    public Optional<Long> sizeIfKnown() {
      return Optional.absent();
    }

    public abstract InputStream openStream() throws IOException;
  }
}
