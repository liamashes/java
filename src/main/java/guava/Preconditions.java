package guava;

import com.google.common.base.Optional;
import com.google.common.graph.ElementOrder;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 前置条件：让方法调用的前置条件判断更简单
 *
 * <p>1.没有额外参数：抛出的异常中没有错误消息；
 *
 * <p>2.有一个Object对象作为额外参数：抛出的异常使用Object.toString() 作为错误消息；
 *
 * <p>3.有一个String对象作为额外参数，并且有一组任意数量的附加Object对象：这个变种处理异常消息的方式有点类似printf，但考虑GWT的兼容性和效率，只支持%s指示符。
 */
public class Preconditions {

  abstract class AbstractGraphBuilder<N> {
    final boolean directed;
    boolean allowsSelfLoops = false;
    ElementOrder<N> nodeOrder = ElementOrder.insertion();
    ElementOrder<N> incidentEdgeOrder = ElementOrder.unordered();

    Optional<Integer> expectedNodeCount = Optional.absent();

    AbstractGraphBuilder() {
      directed = false;
    }
    /**
     * Creates a new instance with the specified edge directionality.
     *
     * @param directed if true, creates an instance for graphs whose edges are each directed; if
     *     false, creates an instance for graphs whose edges are each undirected.
     */
    AbstractGraphBuilder(boolean directed) {
      this.directed = directed;
    }
  }

  public class GraphBuilder<N> extends AbstractGraphBuilder<N> {

    public <N1 extends N> GraphBuilder<N1> incidentEdgeOrder(ElementOrder<N1> incidentEdgeOrder) {
      checkArgument(
          incidentEdgeOrder.type() == ElementOrder.Type.UNORDERED
              || incidentEdgeOrder.type() == ElementOrder.Type.STABLE,
          "The given elementOrder (%s) is unsupported. incidentEdgeOrder() only supports"
              + " ElementOrder.unordered() and ElementOrder.stable().",
          incidentEdgeOrder);
      GraphBuilder<N1> newBuilder = cast();
      newBuilder.incidentEdgeOrder = checkNotNull(incidentEdgeOrder);
      return newBuilder;
    }

    private <N1 extends N> Preconditions.GraphBuilder<N1> cast() {
      return (Preconditions.GraphBuilder<N1>) this;
    }
  }
}
